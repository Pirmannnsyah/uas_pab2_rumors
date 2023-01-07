package com.if5a.rumors.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.if5a.rumors.R;
import com.if5a.rumors.activities.ChatActivity;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private static final String CHANNEl_NAME = "FCM_RUMORS";
    private static final String CHANNEL_DESCRIPTION = "FCM RUMORS DESCRIPTION";
    private int numMessages = 0;
    private String userId;

    @Override
    public void onCreate() {
        super.onCreate();

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From : " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData());
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed Token: " + s);

        sendRegistrationToServer(s);
    }

    private void sendRegistrationToServer(String s) {
    }

    private void sendNotification(Map<String, String> data) {
        Bundle bundle = new Bundle();
        bundle.putString("title", data.get("title"));
        bundle.putString("body", data.get("body"));
        bundle.putString("sender", data.get("sender"));
        bundle.putString("imageUrl", data.get("imageUrl"));

        if (!bundle.getString("sender").equals(userId)) {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtras(bundle);

            int pendingIntentFlag = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntentFlag = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
            }
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntentFlag);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                    .setContentTitle(bundle.getString("title"))
                    .setContentText(bundle.getString("body"))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setContentIntent(pendingIntent)
                    .setContentInfo(getString(R.string.app_name))
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setColor(ContextCompat.getColor(this, R.color.teal_200))
                    .setLights(Color.RED, 1000, 300)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setNumber(++numMessages)
                    .setSmallIcon(R.drawable.ic_notifications);

            try {
                String picture = bundle.getString("imageUrl");
                if (picture != null && !"".equals(picture)) {
                    URL url = new URL(picture);
                    Bitmap bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(bundle.getString("imageUrl")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id), CHANNEl_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription(CHANNEL_DESCRIPTION);
                channel.setShowBadge(true);
                channel.canShowBadge();
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }

            assert notificationManager != null;
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
