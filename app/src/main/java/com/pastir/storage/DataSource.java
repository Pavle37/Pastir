package com.pastir.storage;

import com.pastir.model.ActionBar;
import com.pastir.model.Event;
import com.pastir.model.MotivationalSticker;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a singleton class that is used by the app to communicate between memory and maintain
 * some objects throught the app
 */

public class DataSource {

    private static DataSource sInstance;
    private ActionBar mActionBar;

    /**
     * @return Current action bar that's linked with the Activity's toolbar or initializes one if
     * there isn't a current action bar
     */
    public ActionBar getActionBar() {
        if (mActionBar == null)
            mActionBar = new ActionBar();
        return mActionBar;
    }


    public static DataSource getInstance() {
        if (sInstance == null)
            sInstance = new DataSource();
        return sInstance;
    }

    public List<MotivationalSticker> getMotivationalStickers() {
        List<MotivationalSticker> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            MotivationalSticker sticker = new MotivationalSticker();
            sticker.setImageUrl("http://wallpapercanyon.com/wp-content/uploads/2016/01/Motivational-Wallpapers-6-800x600.jpg");
            result.add(sticker);

            sticker = new MotivationalSticker();
            sticker.setImageUrl("http://andersabrahamsson.info/images/ordinary-weight-loss-motivational-quotes-2-25-best-weight-loss-motivation-quotes-on-pinterest-motivational-quotes-for-weight-loss-diet-motivation-weight-quotes-and-losing-weight-quotes-600-x-800.jpg");
            result.add(sticker);

            sticker = new MotivationalSticker();
            sticker.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/77/df/3a/77df3afdddb1b2d9ffff920b24c4c3c6--american-football-football-players.jpg");
            result.add(sticker);

            sticker = new MotivationalSticker();
            sticker.setImageUrl("https://s-media-cache-ak0.pinimg.com/originals/64/b7/1f/64b71fbc64d3b8ac91cef9c8d909e9fb.jpg");
            result.add(sticker);

            sticker = new MotivationalSticker();
            sticker.setImageUrl("https://www.inspiredabundance.com/wp-content/gallery/success-wallpapers-with-quotes-gallery-11/037-Belief-800x600.jpg");
            result.add(sticker);

            sticker = new MotivationalSticker();
            sticker.setImageUrl("https://s-media-cache-ak0.pinimg.com/736x/44/83/56/4483566a612ad3e420ecc84cd7727f9d--running-motivation-health-motivation.jpg");
            result.add(sticker);
        }
        return result;
    }

    public List<Event> getEvents() {
        List<Event> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Event event = new Event();
            event.setImageUrl("http://www.dreams.metroeve.com/wp-content/uploads/2017/04/dreams.metroeve_cathedral-dreams-meaning.jpg");
            event.setTitle("Title 1");
            event.setPlace("Beograd");
            event.setTime("22:30");
            event.setDate("13.07.2017");
            event.setDescription("Lorem ispsujem \nLorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus convallis arcu vitae sem sagittis placerat. In vitae tristique elit. Nulla congue tempus diam, blandit venenatis erat.\n" +
                    "\n" +
                    "Generated 1 paragraph, 26 words, 185 bytes of Lorem Ipsum");
            result.add(event);

            event = new Event();
            event.setImageUrl("http://www.wondermondo.com/Images/Europe/Italy/Tuscany/SienaCathedral1.jpg");
            event.setTitle("Title 2");
            event.setPlace("Beograd");
            event.setTime("22:30");
            event.setDate("13.07.2017");
            event.setDescription("Lorem ispsujem \n");
            result.add(event);

            event = new Event();
            event.setImageUrl("https://images6.alphacoders.com/338/338986.jpg");
            event.setTitle("Title 3");
            event.setPlace("Beograd");
            event.setTime("22:30");
            event.setDate("13.07.2017");
            event.setDescription("Lorem ispsujem \n");
            result.add(event);

            event = new Event();
            event.setImageUrl("http://cathedral.org/wp-content/uploads/2016/04/DSC0038.jpg");
            event.setTitle("Title 4");
            event.setPlace("Beograd");
            event.setTime("22:30");
            event.setDate("13.07.2017");
            event.setDescription("Lorem ispsujem \n");
            result.add(event);

            event = new Event();
            event.setImageUrl("http://www.koeln.de/bilder/data/pictures/2009-09-09_der_koelner_dom/normal/koelner-dom_hl-12.jpg");
            event.setTitle("Title 14");
            event.setPlace("Beograd");
            event.setTime("22:30");
            event.setDate("13.07.2017");
            event.setDescription("Lorem ispsujem \n");
            result.add(event);

            event = new Event();
            event.setImageUrl("http://cdn.images.express.co.uk/img/dynamic/1/590x/secondary/Gloucester-Cathedral-795898.jpg");
            event.setTitle("Title 5");
            event.setPlace("Beograd");
            event.setTime("22:30");
            event.setDate("13.07.2017");
            event.setDescription("Lorem ispsujem \n");
            result.add(event);
        }
        return result;

    }
}
