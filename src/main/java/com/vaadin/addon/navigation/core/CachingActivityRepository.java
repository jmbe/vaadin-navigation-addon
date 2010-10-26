package com.vaadin.addon.navigation.core;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.vaadin.Application;
import com.vaadin.addon.navigation.activity.Activity;
import com.vaadin.addon.navigation.activity.ActivityMapper;
import com.vaadin.addon.navigation.place.Place;

/**
 * ActivityMapper implementation which caches the created activities. Useful if the activities are used as combined
 * presenters and views (as is common in Vaadin).
 */
public class CachingActivityRepository implements ActivityMapper {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(CachingActivityRepository.class);

    private Map<Class<? extends Place>, Activity> classToActivity;

    private ActivityMapper activityMapper;

    public CachingActivityRepository(final ActivityMapper activityMapper) {
        this.classToActivity = Maps.newHashMap();
        this.activityMapper = activityMapper;
    }

    @Override
    public Activity get(final Place place, final Application application) {
        log.debug("Looking up activity for place {}", place);

        if (!classToActivity.containsKey(place.getClass())) {
            Activity activity = activityMapper.get(place, application);
            classToActivity.put(place.getClass(), activity);
        }

        return classToActivity.get(place.getClass());
    }

    @Override
    public void register(final Class<? extends Place> place, final Class<? extends Activity> activity) {
        this.activityMapper.register(place, activity);
    }

}
