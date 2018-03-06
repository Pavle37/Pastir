package com.pastir.storage;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pastir.model.ActionBar;
import com.pastir.model.Book;
import com.pastir.model.Event;
import com.pastir.model.Lesson;
import com.pastir.model.ListItem;
import com.pastir.model.MorningVerse;
import com.pastir.model.MotivationalSticker;
import com.pastir.model.OnListItemsLoadedListener;

import java.util.ArrayList;
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
    private List<MorningVerse> mVerses;
    private List<Lesson> mLessons;
    private List<Book> mBible;

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


    public void getMotivationalStickers(OnListItemsLoadedListener listener) {
        getItemsFromFirebase(listener, mStickers, "stickers", MotivationalSticker.class);
    }

    public void getEvents(OnListItemsLoadedListener listener) {
        getItemsFromFirebase(listener, mEvents, "events", Event.class);
    }

    public void getMorningVerses(OnListItemsLoadedListener listener) {
        getItemsFromFirebase(listener, mVerses, "morning_verse", MorningVerse.class);
    }

    public void getLessons(OnListItemsLoadedListener listener) {
        //TODO: Because morning verses and lessons have almost the same structure(change when firebase database is updated)
        getItemsFromFirebase(listener, mLessons, "lesson", Lesson.class);
    }
    public void getBible(OnListItemsLoadedListener listener) {
        getItemsFromFirebase(listener, mLessons, "Bible", Book.class);
    }

    private <T extends ListItem> void getItemsFromFirebase(final OnListItemsLoadedListener listener, List<T> items, String path, final Class aClass) {
        if (items != null && items.size() > 0) { //Exit case
            listener.onListItemsLoaded(items);
            return;
        }

        mDatabase.child(path).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //For every child
                    if (aClass == Event.class) {
                        getEventList().add(Event.parse(snapshot));//Parse that child into event and add it to the list
                    } else if (aClass == MotivationalSticker.class) {
                        getStickerList().add(MotivationalSticker.parse(snapshot));
                    } else if (aClass == MorningVerse.class) {
                        getMorningVerses().add(MorningVerse.parse(snapshot));
                    } else if (aClass == Lesson.class) {
                        getLessons().add(Lesson.parse(snapshot));
                    } else if (aClass == Book.class) {
                        getBible().add(Book.parse(snapshot));
                    }
                }
                if (aClass == Event.class) {
                    listener.onListItemsLoaded(mEvents);
                } else if (aClass == MotivationalSticker.class) {
                    listener.onListItemsLoaded(mStickers);
                } else if (aClass == MorningVerse.class) {
                    listener.onListItemsLoaded(mVerses);
                }else if (aClass == Lesson.class) {
                    listener.onListItemsLoaded(mLessons);
                }else if (aClass == Book.class) {
                    listener.onListItemsLoaded(mBible);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<MorningVerse> getMorningVerses() {
        if (mVerses == null)
            mVerses = new ArrayList<>();
        return mVerses;
    }

    public List<Lesson> getLessons() {
        if (mLessons == null)
            mLessons = new ArrayList<>();
        return mLessons;
    }

    public List<Book> getBible() {
        if (mBible == null)
            mBible = new ArrayList<>();
        return mBible;
    }

    private List<Event> getEventList() {
        if (mEvents == null)
            mEvents = new ArrayList<>();
        return mEvents;
    }

    private List<MotivationalSticker> getStickerList() {
        if (mStickers == null)
            mStickers = new ArrayList<>();
        return mStickers;
    }
}
