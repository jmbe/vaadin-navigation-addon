package com.vaadin.addon.navigation.api;

import com.vaadin.addon.navigation.core.ActivityManager;
import com.vaadin.addon.navigation.place.Place;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

import java.util.Iterator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NavigableWindow extends Window {

    private ActivityManager activityManager;

    /**
     * Creates a new unnamed window with a default layout.
     */
    public NavigableWindow() {
        this("", null);
    }

    /**
     * Creates a new unnamed window with a default layout and given title.
     * 
     * @param caption
     *            the title of the window.
     */
    public NavigableWindow(final String caption) {
        this(caption, null);
    }

    /**
     * Creates a new unnamed window with the given content and title.
     * 
     * @param caption
     *            the title of the window.
     * @param content
     *            the contents of the window
     */
    public NavigableWindow(final String caption, final ComponentContainer content) {
        super(caption, content);
    }

    @Nullable
    private ActivityManager findActivityManager(final ComponentContainer container) {

        for (Iterator<Component> iterator = container.getComponentIterator(); iterator.hasNext();) {
            Component component = iterator.next();

            if (component instanceof ActivityManager) {
                return (ActivityManager) component;
            } else if (component instanceof ComponentContainer) {
                ActivityManager am = findActivityManager((ComponentContainer) component);
                if (am != null) {
                    return am;
                }
            }
        }

        return null;
    }

    @Nonnull
    private ActivityManager getActivityManager() {

        this.activityManager = findActivityManager(getContent());

        if (this.activityManager == null) {
            throw new IllegalStateException("No ActivityManager has been added to this NavigableWindow");
        }

        return activityManager;
    }

    public void goTo(final Place place) {
        getActivityManager().goTo(place);
    }
}
