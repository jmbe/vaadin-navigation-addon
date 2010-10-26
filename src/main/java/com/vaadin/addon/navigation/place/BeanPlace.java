package com.vaadin.addon.navigation.place;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.addon.navigation.api.HashParameterPair;
import com.vaadin.addon.navigation.api.UriState;

public class BeanPlace implements Place {

    /** Logger for this class. */
    private static final Logger log = LoggerFactory.getLogger(BeanPlace.class);

    public UriState serializeState() {

        try {
            UriState result = new UriState();
            @SuppressWarnings("unchecked")
            Map<String, String> properties = BeanUtils.describe(this);
            for (Entry<String, String> entry : properties.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if ("class".equals(key)) {
                    continue;
                }

                result.put(key, value);
            }
            return result;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    public void bind(final UriState state) {

        List<HashParameterPair> parameters = state.getParameters();
        for (HashParameterPair pair : parameters) {
            try {
                BeanUtils.setProperty(this, pair.getKey(), pair.getValue());
            } catch (IllegalAccessException e) {
                log.error("Could not set property", e);
            } catch (InvocationTargetException e) {
                log.error("Could not set property", e);
            }
        }

    }

}
