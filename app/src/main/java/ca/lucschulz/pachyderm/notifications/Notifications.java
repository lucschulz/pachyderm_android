package ca.lucschulz.pachyderm.notifications;

import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Date;

import ca.lucschulz.pachyderm.MainActivity;
import ca.lucschulz.pachyderm.R;

public class Notifications extends MainActivity {

    private Context context;
    private NotificationChannel channel;
    private NotificationCompat.Builder mBuilder;
    private NotificationManagerCompat nmc;

    public Notifications(Context context, NotificationChannel channel, NotificationManagerCompat nmc) {
        this.context = context;
        this.channel = channel;
        this.nmc = nmc;
    }

    public void createNewNotification(final String title, final String text, Date dueDate, final int dbId) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                configureNotificationProperties(title, text, dbId);
            }
        };

        long msUntilDue = dueDate.getTime() - System.currentTimeMillis();
        handler.postDelayed(runnable, msUntilDue);
    }

    private void configureNotificationProperties(String title, String text, int dbId) {
        mBuilder = new NotificationCompat.Builder(context, channel.getId())
                .setSmallIcon(R.mipmap.ic_cmc)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent(dbId));

        nmc = NotificationManagerCompat.from(context);
        nmc.notify(dbId, mBuilder.build());
    }

    // What happens when the notification is clicked on from the notifications menu.
    private PendingIntent getPendingIntent(int dbId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        nmc = NotificationManagerCompat.from(context);
        nmc.cancel(dbId);
        return pendingIntent;
    }
}
