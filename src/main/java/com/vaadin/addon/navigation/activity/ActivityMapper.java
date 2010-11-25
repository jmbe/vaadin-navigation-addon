package com.vaadin.addon.navigation.activity;

import com.vaadin.addon.navigation.api.NavigableWindow;
import com.vaadin.addon.navigation.place.Place;

/**
 * Repository for activities. Depending on implementation the activities may be cached, so this is NOT safe to share for
 * multiple windows. Each ActivityManager should have its own ActivityMapper.
 */
public interface ActivityMapper {

    Activity get(Place place, NavigableWindow application);

    /**
     * Register a place to activity mapping.
     * 
     * Optional operation. May be implemented as no-op in mappers that don't need registration.
     */
    void register(Class<? extends Place> place, Class<? extends Activity> activity);
}
