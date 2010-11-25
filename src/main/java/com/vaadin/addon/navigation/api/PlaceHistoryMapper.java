package com.vaadin.addon.navigation.api;

import com.vaadin.addon.navigation.place.Place;

/**
 * Maps Place to LocationHash and vice versa.
 * 
 * Place and LocationHash are value objects, so it is usually safe to share a single PlaceHistoryMapper in multiple
 * windows.
 */
public interface PlaceHistoryMapper {

    Place getPlace(LocationHash hash);

    /**
     * @return <code>true</code> if the mapper has a matching place for the given location.
     */
    boolean hasPlaceFor(LocationHash hash);

    LocationHash toUriFragment(Place place);

    /**
     * Add a place that will be managed by this mapper.
     * 
     * Optional operation, may be implemented as no-op in mappers that don't need it.
     */
    void register(Class<? extends Place> place);

}
