package com.pastir.model;

import java.util.List;

/**
 * Used for handling callbacks when HomeListItems are loaded
 */

public interface OnListItemsLoadedListener {
    void onListItemsLoaded(List<? extends ListItem> events);
}
