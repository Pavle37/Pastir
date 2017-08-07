package com.pastir.storage;

import com.pastir.R;
import com.pastir.model.ActionBar;
import com.pastir.model.Event;
import com.pastir.model.MorningVerse;
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
        event.setDate("13.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        event = new MorningVerse();
        event.setId(2);
        event.setTitle("Title 3");
        event.setDate("13.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        event = new MorningVerse();
        event.setId(3);
        event.setTitle("Title 4");
        event.setDate("13.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        event = new MorningVerse();
        event.setId(4);
        event.setTitle("Title 5");
        event.setDate("13.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        event = new MorningVerse();
        event.setId(5);
        event.setTitle("Title 6");
        event.setDate("13.07.2017");
        event.setText(loremIpsum);
        event.setDescription("Morning verse description");
        result.add(event);

        return result;
    }
}
