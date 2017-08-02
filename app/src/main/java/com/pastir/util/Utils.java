package com.pastir.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

/**
 * Class that encapsulates some UTIL methods
 */
public final class Utils {

    /**
     * Util class used when dealing with View's
     **/
    public static class View {

        /**
         * @param spinnerLayoutId what spinner should look like when item is selected
         * @param itemList        array of objects that is shown ( method toString is called )
         * @return ArrayAdapter that is set to the spinner with dropdown menu android.R.layout.simple_spinner_dropdown_item;
         */
        public static ArrayAdapter configureSpinner(int spinnerLayoutId, Context context, Object[] itemList) {
            int dropdownLayoutId = android.R.layout.simple_spinner_dropdown_item;
            return configureSpinner(spinnerLayoutId, dropdownLayoutId, context, itemList);
        }

        /**
         * @param spinnerLayoutId  what spinner should look like when item is selected
         * @param dropdownLayoutId what dropdown menu should look like
         * @param itemList         array of objects that is shown ( method toString is called )
         * @return ArrayAdapter that is set to the spinner
         */
        public static ArrayAdapter configureSpinner(int spinnerLayoutId, int dropdownLayoutId, Context context, Object[] itemList) {
            ArrayAdapter adapter = new ArrayAdapter<>(context, spinnerLayoutId, itemList);
            adapter.setDropDownViewResource(dropdownLayoutId);
            return adapter;
        }

        /**
         * Disables the view completely
         **/
        public static void disableView(android.view.View view) {
            view.setEnabled(false);
            view.setClickable(false);
            view.setFocusable(false);
            view.setLongClickable(false);
        }

        /**
         * Sets html content to a TextView takes care of supporting below and above Nougat
         *
         * @param tv   - Target TextView
         * @param html - HTML content that is loaded to that TextView
         */
        public static void setHtmlToView(TextView tv, String html) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tv.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));
            } else tv.setText(Html.fromHtml(html));
        }

        /**
         * Hides the keyboard
         */
        public static void hideKeyboard(Context context, android.view.View view) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * General things used in utility class
     **/
    public static class General {

        /**
         * Checks to see if classes from both fragments are the same
         *
         * @param currentFragment fragment 1
         * @param fragmentToLoad  fragment 2
         * @return true if fragments have the same class, false otherwise
         */
        public static boolean compareClasses(Fragment currentFragment, Fragment fragmentToLoad) {
            return currentFragment.getClass().getName().equals(fragmentToLoad.getClass().getName());
        }

        /**
         * Serialize object with GSON
         **/
        public static String serializeToJson(Object someClass) {
            Gson gson = new Gson();
            return gson.toJson(someClass);
        }

        /**
         * Deserialize object from String
         **/
        public static <T> T deserializeFromJson(String jsonString, Class<T> classPar) {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, classPar);
        }

        public static <T> T deserializeFromJson(String jsonString, Type classPar) {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, classPar);
        }

        /**
         * Checks for Internet Connection
         *
         * @return <code>true</code>if there is Internet Connection, <code>false</code> otherwise
         */
        public static boolean hasInternetConnection(Context context) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        }

        /**
         * Resizes the bitmap
         *
         * @param bitmap    bitmap to be resized
         * @param maxWidth  maximum width of the provided bitmap
         * @param maxHeight maximum height of the provided bitmap
         * @return Bitmap with new dimensions rezied from the provided one
         */
        public static Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {

            float scale = Math.min(((float) maxHeight / bitmap.getWidth()), ((float) maxWidth / bitmap.getHeight()));

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            return resizedBitmap;
        }

        /**
         * Scales down the bitmap so that the bitmap's longer side is equal to maxSize
         *
         * @param bitmap  bitmap to be compressed
         * @param maxSize maximum size of the supplied bitmap
         * @return Compressed bitmap
         */
        public static Bitmap compressBitmap(Bitmap bitmap, int maxSize) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 0) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        }

        /**
         * Gets the color from resources
         *
         * @param context Context from which the resource is pulled
         * @param id      id of resource that we wabt ti load
         * @return Color loaded from resource
         */
        public static int getColorWrapper(Context context, int id) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return ContextCompat.getColor(context, id);
            } else {
                //noinspection deprecation
                return context.getResources().getColor(id);
            }
        }

        /**
         * Makes Activity fill the whole screen
         *
         * @param activity that needs to be full screen
         */
        public static void fullScreenActivity(AppCompatActivity activity) {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        /**
         * Convers pixel value to density pixels
         *
         * @param dp      density pixels
         * @param context context used to get the screen's width
         * @return size of pixels according to dp
         */
        public static float convertToDp(float dp, Context context) {
            Resources r = context.getResources();
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        }
    }

    /**
     * Class that is used to validate some common strings
     **/
    public static class Validator {
        /**
         * Validates the email address passed as the first parametar
         *
         * @param email - Email address that needs to be validated
         * @return <code>true</code> if email is valid, <code>false</code> otherwise
         */
        public static boolean isValidEmail(CharSequence email) {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

        /**
         * Validates phone number
         *
         * @param phone - Phone number that needs to be validated
         * @return <code>true</code> if phone is valid, <code>false</code> otherwise
         */
        public static boolean isValidPhone(String phone) {
            return !TextUtils.isEmpty(phone) && android.util.Patterns.PHONE.matcher(phone).matches();
        }

        /**
         * Validates age of a person. Age must be in [0-150] years and must only contain chars
         *
         * @param ageString - Age that is checked
         * @return <code>true</code> if age is valid, <code>false</code> otherwise
         */
        public static boolean isValidAge(String ageString) {
            try {
                int age = Integer.parseInt(ageString);

                return age >= 0 && age < 150;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        /**
         * Validates string to check if it is a positive number
         *
         * @param number - string that is being validated
         * @return <code>true</code> if number is positive, <code>false</code> otherwise
         */
        public static boolean isValidPositiveNumber(String number) {
            return number.length() > 0 && number.matches("[0-9]+[.,]?[0-9]*");
        }

        /**
         * Name is valid if it consists of one or more characters
         *
         * @param name - string that is being validated
         * @return <code>true</code> if name consists of 1+ characters, <code>false</code> otherwise
         */
        public static boolean isValidName(String name) {
            return name != null && name.matches("[A-Z][a-z]+");
        }

        /**
         * This method checks if the last name is valid. Last name is valid in most cases("Smith", "D'Arras", "Doe-Smith", "Doe Smith","d'Arras", "del Bosque")
         *
         * @param lastName - string that is being validated
         * @return <code>true</code> if lastName is valid , <code>false</code> otherwise
         */
        public static boolean isValidLastName(String lastName) {
            return lastName != null && lastName.matches("([a-zA-z]+'?-?)?[A-z][a-z]+\\s?([A-Z][a-z]+)?");
        }
    }


    /**
     * Methods used with files
     */
    public static class File {

        public static String getPath(Context context, Uri uri) throws URISyntaxException {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = {"_data"};
                Cursor cursor = null;

                try {
                    cursor = context.getContentResolver().query(uri, projection, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow("_data");
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index);
                    }
                } catch (Exception e) {
                    // Eat it
                }
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }

        /**
         * Converts Bitmap image to Base64 encoded string
         **/
        public static String bitmapToBase64(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        /**
         * Creates a bitmap from Base64 encoded string
         **/
        public static Bitmap base64ToBitmap(String b64) {
            byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        }

        /**
         * Converts audio file to Base64 encoded string
         *
         * @param audioFileLocation Recorded audio file location
         * @return base64
         */
        public static String audioToBase64(String audioFileLocation) {
            java.io.File file = new java.io.File(audioFileLocation);
            byte[] bytes = new byte[0];
            try {
                bytes = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String encoded = Base64.encodeToString(bytes, 0);
            return encoded;
        }

        /**
         * Creates audio file from base64 encoded string
         *
         * @param b64               base64 format
         * @param fileNameExtension extension for audio file name
         * @return
         */
        public static String base64ToAudio(String b64, String fileNameExtension) {
            String filePath = Environment.getExternalStorageDirectory() + "/chatRecord" + fileNameExtension + ".aac";
            byte[] audioAsBytes = Base64.decode(b64, 0);
            java.io.File audioFile = new java.io.File(filePath);
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(audioFile, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                os.write(audioAsBytes);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return filePath;
        }

        public static Bitmap decodeUri(Uri selectedImage, ContentResolver contentResolver) throws FileNotFoundException {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 200;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImage), null, o2);

        }

        public static String fileToBase64(java.io.File file) {
            try {
                byte[] data = org.apache.commons.io.FileUtils.readFileToByteArray(file);
                return Base64.encodeToString(data, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class SingleToast {

        private static Toast mToast;

        public static void show(Context context, String text) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.show();
        }

        public static void show(Context context, int text) {
            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }


}
