package com.vaadin.example.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.Application;
import com.vaadin.addon.navigation.api.NavigableApplication;
import com.vaadin.addon.navigation.api.NavigableWindow;
import com.vaadin.addon.navigation.core.ActivityManager;
import com.vaadin.addon.navigation.core.CachingActivityRepository;
import com.vaadin.addon.navigation.windows.MultipleWindowsSupport;
import com.vaadin.ui.Window;

public class NavigationExampleApplication extends Application implements NavigableApplication {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(NavigationExampleApplication.class);

    public void init() {
        log.info("Init application.");
        setMainWindow(createNewWindow());
    }

    private void addActivities(final ActivityManager activityManager) {

        activityManager.register(WelcomePlace.class, WelcomeView.class);
        activityManager.register(ProductPlace.class, ProductView.class);

        activityManager.setDefaultPlace(new WelcomePlace());
    }

    public NavigableWindow createNewWindow() {
        ActivityManager activityManager = new ActivityManager();
        activityManager.setActivityMapper(new CachingActivityRepository());

        addActivities(activityManager);

        NavigableWindow window = new VaadinNavigationExampleMainWindow(activityManager);

        return window;
    }

    public Window getWindow(final String name) {
        /* Manage multiple browser windows */
        return MultipleWindowsSupport.getWindow(this, name, super.getWindow(name));
    }
}
