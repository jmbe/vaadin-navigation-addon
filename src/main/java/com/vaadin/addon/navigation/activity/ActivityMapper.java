package com.vaadin.addon.navigation.activity;

import com.vaadin.Application;
import com.vaadin.addon.navigation.place.Place;

public interface ActivityMapper {

    Activity get(Place place, Application application);

    /**
     * Register a place to activity mapping.
     * 
     * Optional operation. May be implemented as no-op in mappers that don't need registration.
     */
    void register(Class<? extends Place> place, Class<? extends Activity> activity);
}
