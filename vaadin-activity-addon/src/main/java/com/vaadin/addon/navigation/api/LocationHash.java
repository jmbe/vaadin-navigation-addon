package com.vaadin.addon.navigation.api;

import com.google.common.base.Strings;

/**
 * Value object for the hash part of a browser location, i.e. the part after "#".
 */
public class LocationHash {

    /**
     * Separates the place and parameter part of the hash.
     */
    public static final String DEFAULT_PLACE_DELIMITER = "/";

    /**
     * Separates parameter pairs in the parameter list.
     */
    public static final String DEFAULT_PARAMETER_LIST_DELIMITER = "&";

    private String hash;

    public LocationHash() {
        this("");
    }

    public LocationHash(final String hash) {
        this.hash = Strings.nullToEmpty(hash).trim().replaceAll("^#", "");
    }

    public LocationHash(final PlaceIdentifier placeName, final UriState state) {
        this(placeName + DEFAULT_PLACE_DELIMITER + state);
    }

    public String getHash() {
        return this.hash;
    }

    public PlaceIdentifier getPlacePart() {
        return getPlacePart(DEFAULT_PLACE_DELIMITER);
    }

    private PlaceIdentifier getPlacePart(final String delimiter) {
        if (hash.contains(delimiter)) {
            return new PlaceIdentifier(hash.substring(0, hash.indexOf(delimiter)));
        } else {
            return new PlaceIdentifier(hash);
        }
    }

    public UriState getState() {
        String delimiter = DEFAULT_PLACE_DELIMITER;
        String parameterPart = hash.substring(hash.indexOf(delimiter) + 1);
        return new UriState(parameterPart);
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(this.hash);
    }

    @Override
    public String toString() {
        return getHash();
    }

}
