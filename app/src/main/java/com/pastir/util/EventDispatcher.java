package com.pastir.util;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import io.reactivex.subjects.PublishSubject;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Listens to different system events and dispatches according events to the app.
 */

public class EventDispatcher {

    public static final PublishSubject<Integer> sPhoneCallListener = PublishSubject.create();

    private Context mContext;

    public EventDispatcher(Context context){
        mContext = context;
    }

    public void registerPhoneCallListener(){
       telephonyStateListenTo(PhoneStateListener.LISTEN_CALL_STATE);
    }

    public void unregisterPhoneCallListener(){
       telephonyStateListenTo(PhoneStateListener.LISTEN_NONE);
    }

    private void telephonyStateListenTo(final int STATE){
        TelephonyManager mgr = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, STATE);
        }
    }

    /**
     * Used for listening to calls and pausing the music when appropreate
     */
    private PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            sPhoneCallListener.onNext(state);
            super.onCallStateChanged(state, incomingNumber);
        }
    };
}
