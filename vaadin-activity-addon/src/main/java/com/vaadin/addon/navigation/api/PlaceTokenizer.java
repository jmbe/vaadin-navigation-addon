package com.vaadin.addon.navigation.api;

import com.vaadin.addon.navigation.place.Place;

public interface PlaceTokenizer<P extends Place> {

    UriState toUriParameters(P place);

    P toPlace(UriState parameters);

}
