package com.vaadin.addon.navigation.activity;

import com.vaadin.Application;
import com.vaadin.addon.navigation.place.Place;
import com.vaadin.ui.Component;

public interface Activity {

    /**
     * @param application
     *            a NavigableApplication instance
     */
    void init(Application application);

    void navigateTo(Place place);

    Component getView();

}
