package com.vaadin.addon.navigation.api;

import com.vaadin.addon.navigation.activity.Activity;

/**
 * Interface for activities which needs stop-handling, typically unsaved forms or activities which need some cleanup.
 */
public interface StoppableActivity extends Activity {

    /**
     * @return non-empty String to show message to show in confirmation dialog.
     */
    String confirmStop();

    /**
     * Signals that the activity will be stopped now.
     */
    void stop();

}
