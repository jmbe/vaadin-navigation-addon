package com.vaadin.addon.navigation.core;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.vaadin.Application;
import com.vaadin.addon.navigation.activity.Activity;
import com.vaadin.addon.navigation.activity.ActivityMapper;
import com.vaadin.addon.navigation.activity.ActivityNotFoundException;
import com.vaadin.addon.navigation.place.Place;

public class ActivityFactory implements ActivityMapper {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(ActivityFactory.class);

    private Map<Class<? extends Place>, Class<? extends Activity>> placeToActivity;

    public ActivityFactory() {
        this.placeToActivity = Maps.newHashMap();
    }

    @Override
    public Activity get(final Place place, final Application application) {
        log.debug("Creating activity for place {}", place);
        Class<? extends Activity> newActivityClass = getActivityClass(place);

        if (newActivityClass == null) {
            throw new ActivityNotFoundException("Could not find activity for " + place + ". Current known places "
                    + placeToActivity.keySet());
        }

        try {
            Activity activity = newActivityClass.newInstance();
            activity.init(application);
            return activity;
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not instantiate activity for place " + place, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not instantiate activity for place " + place, e);
        }
    }

    @Override
    public void register(final Class<? extends Place> place, final Class<? extends Activity> activity) {
        this.placeToActivity.put(place, activity);

    }

    protected <P extends Place> Class<? extends Activity> getActivityClass(final P place) {
        Class<? extends Activity> newActivityClass = this.placeToActivity.get(place.getClass());
        return newActivityClass;
    }

}
