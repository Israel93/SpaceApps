package gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.orm.SugarRecord;
import com.space.alertaec.MainActivity;
import com.space.alertaec.R;

import java.util.ArrayList;
import java.util.List;

import dto.Notificacion;

/**
 * Created by Luiggi on 01/11/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    static int numNotification = 0;
    static boolean masDeUnaNotificacion;
    static List<String> mensajeNotificaciones = new ArrayList<>();
    private NotificationCompat.InboxStyle inboxStyleGroup= new NotificationCompat.InboxStyle();
    private String GRUPO_NOTIFICACIONES = "notificaciones_similares";
    private static final String TAG = "MyGcmListenerService";
    //public static String urlServidor = RestDAO.endPointWS+"/api/NotificacionWS/";
    @Override
    public void onMessageReceived(String from, Bundle data) {

        /*TODO REEMPLAZAR Notificacion notificacion = new Notificacion();
        notificacion.setCategoria(data.getString("categoria"));
        notificacion.setTitulo(data.getString("titulo"));
        notificacion.setFecha_fin(data.getString("fecha_fin"));
        notificacion.setFecha_inicio(data.getString("fecha_inicio"));
        notificacion.setHora_fin(data.getString("hora_fin"));
        notificacion.setHora_inicio(data.getString("hora_inicio"));
        notificacion.setTexto(data.getString("texto"));
        notificacion.setId(Long.valueOf(data.getString("idMostrar")));

        SugarRecord.save(notificacion);

        ContextoDatos.getInstancia().setNotificacion(notificacion);*/
        String message = data.getString("message");
        String title = data.getString("title");
        String idMostrar = data.getString("idMostrar");

        Log.i(TAG, "From: " + from);
        Log.i(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        //TODO REEMPLAZAR sendNotification(notificacion);
        // [END_EXCLUDE]
        sendNotification();
    }


    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("idMostrar", 1);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code*/ , intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        mensajeNotificaciones.add("Indicanos tu situación");

        notificationBuilder.setSmallIcon(R.drawable.com_facebook_button_icon)
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(),
                        R.mipmap.ic_launcher
                ))
                .setContentTitle("Se ha registrado una emergencia")
                .setContentText("Indicanos tu situación")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .build();
        masDeUnaNotificacion=true;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
