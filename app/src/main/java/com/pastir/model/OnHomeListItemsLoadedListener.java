package com.pastir.model;

import java.util.List;

/**
 * Used for handling callbacks when HomeListItems are loaded
 */

public interface OnHomeListItemsLoadedListener {
    void onHomeListItemsLoaded(List<? extends HomeListItem> events);
}
