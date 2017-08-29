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
        List<MorningVerse> result = new ArrayList<>();
        MorningVerse event = new MorningVerse();
        event.setId(0);
        event.setTitle("Evanđelje po Luki 15:7 SB");
        event.setDate("DANAS");
        event.setDescription("Nosite bremena jedan drugoga i tako ćete ispuniti Hristov zakon");
        event.setText("Nosite bremena jedan drugoga i tako ćete ispuniti Hristov zakon");
        result.add(event);

        event = new MorningVerse();
        event.setId(1);
        event.setTitle("Poslanica Galaćanima 6:2");
        event.setDate("JUČE");
        event.setText("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        event.setDescription("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        result.add(event);

        event = new MorningVerse();
        event.setId(2);
        event.setTitle("Evanđelje po Jovanu 15:7 SB");
        event.setDate("PREKJUČE");
        event.setText("A druga padoše na kamenita mjesta, gdje ne bijaše mnogo zemlje, i odmah iznikošre, jer ne bijaše u dubinu zemlje. Jezek. 11, 19.");
        event.setDescription("A druga padoše na kamenita mjesta, gdje ne bijaše mnogo zemlje, i odmah iznikošre, jer ne bijaše u dubinu zemlje. Jezek. 11, 19.");
        result.add(event);

        event = new MorningVerse();
        event.setId(3);
        event.setTitle("1. Jovanova 4:1");
        event.setDate("12.07.2017");
        event.setText("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        event.setDescription("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        result.add(event);

        event = new MorningVerse();
        event.setId(4);
        event.setTitle("Evanđelje po Luki 15:7 SB");
        event.setDate("11.07.2017");
        event.setText("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        event.setDescription("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        result.add(event);

        event = new MorningVerse();
        event.setId(5);
        event.setTitle("Evanđelje po Luki 15:7 SB");
        event.setDate("10.07.2017");
        event.setText("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        event.setDescription("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        result.add(event);

        event = new MorningVerse();
        event.setId(5);
        event.setTitle("SEKCIJA 2");
        event.setDate("9.07.2017");
        event.setText("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        event.setDescription("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        result.add(event);

        event = new MorningVerse();
        event.setId(5);
        event.setTitle("SEKCIJA 3");
        event.setDate("8.07.2017");
        event.setText("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        event.setDescription("I onaj dan izišavši Isus iz kuće sjeđaše kraj mora. I sabraše se oko njega ljudi mnogi, i tako da mora ući u lađu i sjesti, a narod sav stajaše po brijegu.");
        result.add(event);

        return result;
    }
}
