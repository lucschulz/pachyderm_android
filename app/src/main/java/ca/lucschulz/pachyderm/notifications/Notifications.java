package ca.lucschulz.pachyderm.notifications;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import ca.lucschulz.pachyderm.MainActivity;
import ca.lucschulz.pachyderm.R;

public class Notifications extends Activity {

    private final String PACHYDERM_NOTIFICATION_ID = "pachyderm_notification_id";
    private NotificationChannel channel;

    private Context context;

    public Notifications(Context context) {
        this.context = context;
    }


    public void configureNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.notif_ChannelName);
//            String description = getString(R.string.notif_ChannelDescription);

            CharSequence name = "Test notif";
            String description = "This is a test notification.";


            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(PACHYDERM_NOTIFICATION_ID, name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
        }

        channel = new NotificationChannel(PACHYDERM_NOTIFICATION_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
    }

    public void configureNotifications() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channel.getId())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Test notification")
                .setContentText("Test content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent());

        NotificationManagerCompat notif = NotificationManagerCompat.from(context);
        notif.notify(0, mBuilder.build());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        return pendingIntent;
    }
}
