package com.pastir.model;

/**
 * Interface implemented by all models on the home screen
 */

public interface HomeListItem extends ListItem {
    String getImageUrl();
    String getTitle();
    String getPlace();
    String getDate();
}
