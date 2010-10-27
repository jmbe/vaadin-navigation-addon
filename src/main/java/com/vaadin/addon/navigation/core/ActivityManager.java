package com.vaadin.addon.navigation.core;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.vaadin.addon.navigation.activity.Activity;
import com.vaadin.addon.navigation.activity.ActivityMapper;
import com.vaadin.addon.navigation.activity.stoppable.ConfirmationDialogHandler;
import com.vaadin.addon.navigation.activity.stoppable.DefaultConfirmationDialogFactory;
import com.vaadin.addon.navigation.activity.stoppable.NavigationConfirmationDialog;
import com.vaadin.addon.navigation.activity.stoppable.NavigationConfirmationDialogFactory;
import com.vaadin.addon.navigation.api.LocationHash;
import com.vaadin.addon.navigation.api.NavigableApplication;
import com.vaadin.addon.navigation.api.PlaceController;
import com.vaadin.addon.navigation.api.PlaceHistoryMapper;
import com.vaadin.addon.navigation.api.StoppableActivity;
import com.vaadin.addon.navigation.place.Place;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import edu.umd.cs.findbugs.annotations.Nullable;

public class ActivityManager extends CustomComponent implements FragmentChangedListener, PlaceController {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(ActivityManager.class);

    private VerticalLayout layout = new VerticalLayout();

    private UriFragmentUtility uriFragmentUtil = new UriFragmentUtility();

    private PlaceHistoryMapper placeHistoryMapper;
    private ActivityMapper activityMapper;

    private NavigationConfirmationDialogFactory confirmationDialogFactory;

    /**
     * Starting place when no other place is given.
     */
    private Place defaultPlace;

    /**
     * Currently running activity.
     */
    private Activity currentActivity;

    /**
     * Used to return to old location if a new location is invalid.
     */
    private LocationHash currentLocation;

    public ActivityManager() {
        this(new ActivityFactory(), new AnnotatedPlaceHistoryMapper());
    }

    public ActivityManager(final ActivityMapper activityMapper, final PlaceHistoryMapper placeHistoryMapper) {
        layout.setSizeFull();
        setSizeFull();
        layout.addComponent(uriFragmentUtil);
        setCompositionRoot(layout);

        uriFragmentUtil.addListener(this);

        this.activityMapper = activityMapper;
        this.placeHistoryMapper = placeHistoryMapper;

        this.confirmationDialogFactory = new DefaultConfirmationDialogFactory();

    }

    public void setActivityMapper(final ActivityMapper activityMapper) {
        this.activityMapper = activityMapper;
    }

    public ActivityMapper getActivityMapper() {
        return activityMapper;
    }

    public void setPlaceHistoryMapper(final PlaceHistoryMapper placeHistoryMapper) {
        this.placeHistoryMapper = placeHistoryMapper;
    }

    public PlaceHistoryMapper getPlaceHistoryMapper() {
        return placeHistoryMapper;
    }

    public void setConfirmationDialogFactory(final NavigationConfirmationDialogFactory confirmationDialogFactory) {
        this.confirmationDialogFactory = confirmationDialogFactory;
    }

    public void fragmentChanged(final FragmentChangedEvent source) {
        String fragment = uriFragmentUtil.getFragment();
        log.debug("Fragment changed to {}", fragment);

        LocationHash newFragment = new LocationHash(fragment);

        /**
         * Choose default place if no fragment is given.
         */
        if (newFragment.isEmpty()) {
            log.info("Navigating to default activity");
            newFragment = this.placeHistoryMapper.toUriFragment(getDefaultPlace());
        }

        if (placeHistoryMapper.hasPlaceFor(newFragment)) {

            Place place = placeHistoryMapper.getPlace(newFragment);

            if (shouldShowWarning()) {
                confirmedGoTo(place);
            } else {
                goTo(place, false);
            }

        } else {
            /* Invalid fragment. Return to old location. */
            uriFragmentUtil.setFragment(currentLocation.getHash(), false);
        }

    }

    public void goTo(final Place place) {
        goTo(place, true);
    }

    private <P extends Place> void goTo(final P place, final boolean setFragment) {
        final Activity newactivity = activityMapper.get(place, (NavigableApplication) getApplication());

        LocationHash newLocation = this.placeHistoryMapper.toUriFragment(place);
        String newfragment = newLocation.getHash();
        if (setFragment && !newfragment.equals(uriFragmentUtil.getFragment())) {
            uriFragmentUtil.setFragment(newfragment, false);
        }

        Component removeMe = null;
        for (Iterator<Component> i = layout.getComponentIterator(); i.hasNext();) {
            Component c = i.next();
            if (c != uriFragmentUtil) {
                removeMe = c;
            }
        }

        if (removeMe != null) {
            layout.removeComponent(removeMe);
        }

        Component view = newactivity.getView();

        if (view != null) {
            layout.addComponent(view);
            layout.setExpandRatio(view, 1.0F);
        } else {
            try {
                view = (Component) newactivity;
                log.warn("View was null for activity {}. Using activity as view.", newactivity);
            } catch (ClassCastException e) {
                throw new RuntimeException("The activity " + newactivity.getClass().getSimpleName()
                        + " returned null view");
            }
        }

        newactivity.navigateTo(place);
        currentActivity = newactivity;
        currentLocation = newLocation;
    }

    private void confirmedGoTo(final Place place) {
        Preconditions.checkNotNull(this.confirmationDialogFactory,
                "No NavigationConfirmationDialogFactory has been set for ActivityManager. Cannot create dialog");

        String message = getWarningMessage();

        final NavigationConfirmationDialog dialog = this.confirmationDialogFactory.create(message);

        ConfirmationDialogHandler handler = new ConfirmationDialogHandler() {

            @Override
            public void onContinue() {
                getWindow().removeWindow((Window) dialog);
                goTo(place);
            }

            @Override
            public void onCancel() {
                getWindow().removeWindow((Window) dialog);
                uriFragmentUtil.setFragment(currentLocation.getHash(), false);
            }

        };

        dialog.setConfirmationDialogHandler(handler);

        getWindow().addWindow((Window) dialog);

    }

    private boolean shouldShowWarning() {
        String warningMessage = getWarningMessage();
        return !Strings.isNullOrEmpty(warningMessage);
    }

    @Nullable
    private String getWarningMessage() {
        if (currentActivity == null) {
            return null;
        }

        if (!StoppableActivity.class.isAssignableFrom(currentActivity.getClass())) {
            return null;
        }

        StoppableActivity stoppable = (StoppableActivity) currentActivity;

        String warningMessage = stoppable.confirmStop();
        return warningMessage;
    }

    private Place getDefaultPlace() {
        return this.defaultPlace;
    }

    public void setDefaultPlace(final Place defaultPlace) {
        Activity activity = activityMapper.get(defaultPlace, (NavigableApplication) getApplication());

        if (activity == null) {
            throw new IllegalArgumentException("Cannot find activity for place " + defaultPlace
                    + " when setting default place. Register the place and activity first.");
        }

        this.defaultPlace = defaultPlace;

        if (currentActivity == null) {
            goTo(defaultPlace);
        }
    }

    public void register(final Class<? extends Place> place, final Class<? extends Activity> activity) {
        this.placeHistoryMapper.register(place);
        this.activityMapper.register(place, activity);
    }

}
