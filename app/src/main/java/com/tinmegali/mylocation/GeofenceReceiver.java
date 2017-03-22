package com.tinmegali.mylocation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by lansweeper on 17/03/17.
 */
 public class GeofenceReceiver extends BroadcastReceiver {
    Context context;
    Intent broadcastIntent = new Intent();

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofencingEvent.hasError()) {

            return;
        }


        // Get the transition type.

        // //int transition = .getGeofenceTransition(intent);

        // Test that a valid transition was reported
        if ((geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
                || (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT)) {

            int transition = geofencingEvent.getGeofenceTransition();

            // Post a notification
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            String[] geofenceIds = new String[geofences.size()];

//            String ids = TextUtils.join(GeofenceUtils.GEOFENCE_ID_DELIMITER,
//                    geofenceIds);
//            String transitionType = GeofenceUtils.getTransitionString(transition, context);
//
//            for (int index = 0; index < geofences.size(); index++) {
//                Geofence geofence = geofences.get(index);
//                geofenceIds[index] = geofences.get(index).getRequestId();
//            }
//            showNotification(transitionType, ids);

//            // Create an Intent to broadcast to the app
//            broadcastIntent
//                    .setAction("com.example.sonu.desti.ACTION_GEOFENCE_TRANSITION")
//                    .addCategory("com.example.sonu.desti.CATEGORY_LOCATION_SERVICES")
//                    .putExtra("com.example.android.geofence.EXTRA_GEOFENCE_ID", geofenceIds)
//                    .putExtra("com.example.android.geofence.KEY_TRANSITION_TYPE",
//                            transitionType);

//            LocalBroadcastManager.getInstance(context).sendBroadcast(
//                    broadcastIntent);
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                Toast.makeText(context, "is within location buddy", Toast.LENGTH_SHORT).show();
                showNotification("Entered", "Entered the Location");
            } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                Toast.makeText(context, "exited location buddy", Toast.LENGTH_SHORT).show();
                showNotification("Exited", "Exited the Location");

            } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                showNotification("Entered", "In the location");
            } else {
                showNotification("Error", "Error");
            }
            return;


        } else {
            // Always log as an error
            //Log.e(TAG, "TRANSITION = " + transition);
        }


    }

    private void showNotification(String entered, String s) {

        // 1. Create a NotificationManager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // 2. Create a PendingIntent for AllGeofencesActivity
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 3. Create and send a notification
        CharSequence bigText = null;
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(entered)
                .setContentText(s)
                .setContentIntent(pendingNotificationIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0, notification);
    }
}
