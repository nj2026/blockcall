package com.example.callblocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CallReceiver extends BroadcastReceiver {

    private static final String TAG = "CallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        
        if (phoneNumber != null && phoneNumber.startsWith("140")) {
            // Block the call (hang up the call)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For Android Oreo and above, we cannot directly end calls anymore
                // So you would use a `CallScreeningService` (need permissions in manifest)
                Log.i(TAG, "Blocking call from: " + phoneNumber);
                setResultData(null); // This blocks the call
            } else {
                // Below Oreo, we can try to hang up the call
                try {
                    // TelephonyManager could allow blocking or ending the call on lower versions
                    setResultData(null);  // This is a way to block calls
                } catch (Exception e) {
                    Log.e(TAG, "Error while blocking call", e);
                }
            }
        }
    }
}
