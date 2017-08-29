package com.pastir.storage;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pastir.model.ActionBar;
import com.pastir.model.Event;
import com.pastir.model.HomeListItem;
import com.pastir.model.MorningVerse;
import com.pastir.model.MotivationalSticker;
import com.pastir.model.OnHomeListItemsLoadedListener;
import com.pastir.presenter.HomeListItemPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This is a singleton class that is used by the app to communicate between memory and maintain
 * some objects throught the app
 */

public class DataSource {

    private static DataSource sInstance;
    private ActionBar mActionBar;
    private DatabaseReference mDatabase;

    private List<Event> mEvents;
    private List<MotivationalSticker> mStickers;

    /**
     * @return Current action bar that's linked with the Activity's toolbar or initializes one if
     * there isn't a current action bar
     */
    public ActionBar getActionBar() {
        if (mActionBar == null)
            mActionBar = new ActionBar();
        return mActionBar;
    }

    private DataSource() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static DataSource getInstance() {
        if (sInstance == null)
            sInstance = new DataSource();
        return sInstance;
    }


    public void getMotivationalStickers(OnHomeListItemsLoadedListener listener) {
        getItemsFromFirebase(listener, mStickers, "stickers", MotivationalSticker.class);
    }

    public void getEvents(OnHomeListItemsLoadedListener listener) {
        getItemsFromFirebase(listener, mEvents, "events", Event.class);
    }

    private <T extends HomeListItem> void getItemsFromFirebase(final OnHomeListItemsLoadedListener listener, List<T> items, String path, final Class aClass) {
        if (items != null && items.size() > 0) { //Exit case
            listener.onHomeListItemsLoaded(items);
            return;
        }

        mDatabase.child(path).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //For every child
                    if (aClass == Event.class) {
                        getEventList().add(Event.parse(snapshot));//Parse that child into event and add it to the list
                    } else {
                        getStickerList().add(MotivationalSticker.parse(snapshot));
                    }
                }
                listener.onHomeListItemsLoaded(aClass == Event.class ? mEvents : mStickers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private List<Event> getEventList() {
        if(mEvents == null)
            mEvents = new ArrayList<>();
        return mEvents;
    }

    private List<MotivationalSticker> getStickerList() {
        if(mStickers == null)
            mStickers = new ArrayList<>();
        return mStickers;
    }


    public List<MorningVerse> getMorningVerses() {
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut molestie scelerisque commodo. Vivamus eget arcu aliquet turpis sodales pharetra non ac dui. Sed nec ultrices nisl, et hendrerit lectus. Pellentesque gravida neque vel quam efficitur posuere. Donec at vestibulum ligula, eu tempus dui. Nullam at lacinia dui. Praesent tincidunt fermentum est non hendrerit. Praesent ullamcorper enim mi, sit amet mollis diam accumsan non. Nulla turpis purus, consectetur nec felis a, consectetur interdum lectus. In eu augue vitae tellus suscipit lobortis.\n" +
                "\n" +
                "Morbi sodales urna vitae neque pulvinar, eleifend dapibus ante vestibulum. Fusce eget odio blandit quam elementum vehicula vitae in nibh. Curabitur gravida consequat ante et aliquam. Integer accumsan enim sed velit bibendum, quis tristique arcu blandit. Sed egestas lobortis interdum. Donec hendrerit tortor pellentesque tellus volutpat porttitor. Phasellus tristique urna vitae sem pharetra, vel scelerisque velit consequat. Pellentesque pellentesque turpis ornare justo luctus venenatis quis sit amet tellus. Suspendisse velit elit, gravida ut sodales vitae, efficitur at justo. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n" +
                "\n" +
                "Nullam dignissim elit nibh. Vestibulum euismod sapien et blandit consequat. Nullam vel ex luctus est laoreet laoreet scelerisque nec lacus. Vestibulum nisi dui, aliquet id nibh id, sodales laoreet ligula. Phasellus maximus nunc vitae lorem viverra finibus. Duis ut commodo enim. In sollicitudin, ipsum et pulvinar congue, urna nunc egestas ipsum, in tincidunt massa ligula et risus. Duis ut molestie nunc.\n" +
                "\n" +
                "In pellentesque velit in libero consequat, vel rhoncus turpis rhoncus. Nunc et nisi elit. Etiam ut pretium dui. Pellentesque lorem erat, ultricies sed magna vel, scelerisque vestibulum tortor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec vestibulum iaculis nisl laoreet tristique. In dignissim mattis pharetra. Sed ultricies ante sed gravida molestie. Phasellus vel scelerisque turpis, vel euismod tortor. Integer sollicitudin nibh maximus, ornare ligula vitae, molestie turpis. Duis dictum dui ut arcu semper, eget tempor massa congue.\n" +
                "\n" +
                "Aenean dui eros, suscipit sit amet mi eget, tincidunt efficitur massa. Aenean finibus justo pellentesque ipsum blandit, eu scelerisque nibh fermentum. Aliquam euismod, massa non placerat cursus, orci ipsum sagittis metus, sed lobortis erat mi sit amet lorem. Sed at porta tortor. Donec a sapien ac ante posuere egestas. Phasellus ut lectus sit amet sem scelerisque faucibus sit amet quis quam. Vivamus convallis faucibus purus, nec auctor felis fermentum eu. Integer quis dapibus elit.";
        List<MorningVerse> result = new ArrayList<>();
        MorningVerse event = new MorningVerse();
        event.setId(0);
        event.setTitle("Title 1");
        event.setDate("13.07.2017");
        event.setDescription("Morning verse description");
        event.setText(loremIpsum);
        result.add(event);

        event = new MorningVerse();
        event.setId(1);
        event.setTitle("Title 2");
        event.setDate("14.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        event = new MorningVerse();
        event.setId(2);
        event.setTitle("Title 3");
        event.setDate("15.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        event = new MorningVerse();
        event.setId(3);
        event.setTitle("Title 4");
        event.setDate("16.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        event = new MorningVerse();
        event.setId(4);
        event.setTitle("Title 5");
        event.setDate("17.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        event = new MorningVerse();
        event.setId(5);
        event.setTitle("Title 6");
        event.setDate("15.01.2015");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        return result;
    }
}
