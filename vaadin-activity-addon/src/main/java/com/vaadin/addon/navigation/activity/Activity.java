package com.vaadin.addon.navigation.activity;

import com.vaadin.addon.navigation.api.NavigableWindow;
import com.vaadin.addon.navigation.place.Place;
import com.vaadin.ui.Component;

public interface Activity {

    void init(NavigableWindow window);

    void navigateTo(Place place);

    Component getView();

}
