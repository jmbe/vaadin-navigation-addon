package com.vaadin.addon.navigation.core;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.vaadin.addon.navigation.activity.Activity;
import com.vaadin.addon.navigation.api.NavigableWindow;
import com.vaadin.addon.navigation.place.Place;

/**
 * ActivityMapper implementation which caches the created activities. Useful if the activities are used as combined
 * presenters and views (as is common in Vaadin).
 */
public class CachingActivityRepository extends ActivityFactory {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(CachingActivityRepository.class);

    private Map<Class<? extends Activity>, Activity> classToInstance;

    public CachingActivityRepository() {
        this.classToInstance = Maps.newHashMap();
    }

    @Override
    public Activity get(final Place place, final NavigableWindow application) {
        log.debug("Looking up activity for place {}", place);

        Class<? extends Activity> activityClass = getActivityClass(place);

        if (!classToInstance.containsKey(activityClass)) {
            Activity activity = super.get(place, application);
            classToInstance.put(activityClass, activity);
        }

        return classToInstance.get(activityClass);
    }

}
