package com.vaadin.addon.navigation.core;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.vaadin.addon.navigation.api.LocationHash;
import com.vaadin.addon.navigation.api.PlaceHistoryMapper;
import com.vaadin.addon.navigation.api.PlaceIdentifier;
import com.vaadin.addon.navigation.api.UriBinding;
import com.vaadin.addon.navigation.place.Place;

public class AnnotatedPlaceHistoryMapper implements PlaceHistoryMapper {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(AnnotatedPlaceHistoryMapper.class);

    private Map<PlaceIdentifier, Class<? extends Place>> uriToPlace;
    private Map<Class<? extends Place>, PlaceIdentifier> placeToUri;

    public AnnotatedPlaceHistoryMapper() {
        this.uriToPlace = Maps.newHashMap();
        this.placeToUri = Maps.newHashMap();
    }

    @Override
    public Place getPlace(final LocationHash hash) {
        try {
            Place place = uriToPlace.get(hash.getPlacePart()).newInstance();
            place.bind(hash.getState());
            return place;
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot instantiate place class for " + hash, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate place class for " + hash, e);
        }
    }

    @Override
    public boolean hasPlaceFor(final LocationHash hash) {
        PlaceIdentifier name = hash.getPlacePart();
        boolean containsKey = uriToPlace.containsKey(name);
        return containsKey;
    }

    public void register(final Class<? extends Place>... places) {
        for (Class<? extends Place> place : places) {
            register(place);
        }
    }

    public void register(final Class<? extends Place> place) {
        String uri = null;

        UriBinding binding = place.getAnnotation(UriBinding.class);
        if (binding == null) {
            String message = place.getSimpleName() + " does not have a " + UriBinding.class.getSimpleName()
                    + ". Using simple classname instead.";
            log.info(message);
            uri = place.getSimpleName();
        } else {
            uri = binding.value();
        }

        PlaceIdentifier name = new PlaceIdentifier(uri);

        this.uriToPlace.put(name, place);
        this.placeToUri.put(place, name);
    }

    @Override
    public LocationHash toUriFragment(final Place place) {
        if (!this.placeToUri.containsKey(place.getClass())) {
            log.warn("Registering previously unknown place {} automatically.", place);
        }

        PlaceIdentifier name = this.placeToUri.get(place.getClass());
        return new LocationHash(name, place.serializeState());
    }
}
