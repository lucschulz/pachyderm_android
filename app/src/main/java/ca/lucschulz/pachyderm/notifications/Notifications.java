package ca.lucschulz.pachyderm.notifications;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Date;

import ca.lucschulz.pachyderm.MainActivity;
import ca.lucschulz.pachyderm.R;

public class Notifications extends Activity {

    private final String PACHYDERM_NOTIFICATION_ID = "pachyderm_notification_id";
    private NotificationChannel channel;

    private Context context;
    private NotificationCompat.Builder mBuilder;

    private static int notificationId;

    public Notifications(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationId = 0;
        configureNotificationChannel(notificationManager);
    }


    public void configureNotificationChannel(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.notif_ChannelName);
//            String description = getString(R.string.notif_ChannelDescription);

            CharSequence name = "Due date notification";
            String description = "Pachyderm due date notification.";


            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(PACHYDERM_NOTIFICATION_ID, name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
        }

        channel = new NotificationChannel(PACHYDERM_NOTIFICATION_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
    }


    public void createNewNotification(final String title, final String text, Date dueDate) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                configureNotificationProperties(title, text);
            }
        };

        handler.postAtTime(runnable, dueDate.getTime());
        handler.postDelayed(runnable, 1000);
    }

    private void configureNotificationProperties(String title, String text) {
        mBuilder = new NotificationCompat.Builder(context, channel.getId())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent());

        NotificationManagerCompat notif = NotificationManagerCompat.from(context);
        notif.notify(notificationId++, mBuilder.build());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        return pendingIntent;
    }
}
