package co.allza.deverdad.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.allza.deverdad.CargarDatos;
import co.allza.mararewards.R;
import co.allza.deverdad.activities.SplashActivity;
import co.allza.deverdad.items.NotificacionItem;
import co.allza.deverdad.items.SeguroItem;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by Tavo on 13/07/2016.
 */
public class PushNotificationService extends FirebaseMessagingService
{
    String url="http://verdad.herokuapp.com/campaigns/conta?idcampaigns=";
    int id;
    public PushNotificationService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
                notificacionSimple(remoteMessage);
    }

    @Override
    public void onDeletedMessages() {
        Log.e("Notification Service","onDeletedMEssages");
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    public void notificacionSimple(RemoteMessage remote) {
        NotificationManager mNotifyMgr= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        SimpleDateFormat parserFormal= new SimpleDateFormat("dd MMM yyyy");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.perfil_whitebg)
                        .setContentTitle(remote.getData().get("title"))
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logoandroid))
                        .setColor(4)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentText(remote.getData().get("message"));
        mBuilder.setAutoCancel(true);
        Intent resultIntent = new Intent(this, SplashActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        if(Integer.parseInt(remote.getData().get("type"))==2){
            id=Integer.parseInt(remote.getData().get("idcampaigns"));
            resultIntent.putExtra("actualizar", true);
            CargarDatos.notificationIsUp(remote.getData().get("title"),this,mNotifyMgr,true);
            CargarDatos.clearArraySeguros();
            RealmConfiguration config = new RealmConfiguration
                    .Builder(this)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<SeguroItem> segurosAnteriores = realm.where(SeguroItem.class).findAll();
            segurosAnteriores.deleteAllFromRealm();
            realm.commitTransaction();
        }
        else{
             id=Integer.parseInt(remote.getData().get("idcampaigns"))+100;
             CargarDatos.makePetition(PushNotificationService.this,url+(id-100));
             CargarDatos.notificationIsUp(remote.getData().get("title"),this,mNotifyMgr,false);
        }

        resultIntent.putExtra("goTo",getInsurancePosition(id));
        NotificacionItem item=new NotificacionItem(R.drawable.logoandroid,remote.getData().get("title"),remote.getData().get("message"),parserFormal.format(Calendar.getInstance().getTime()));
        item.setId(id);
        push(item);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mNotifyMgr.notify(id, mBuilder.build());

    }

    public void push(NotificacionItem item)
    {
        RealmConfiguration config = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
         Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();
        realm.close();
    }

    public int getInsurancePosition(int id)
    {
        RealmConfiguration config = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();
        RealmResults<SeguroItem> result = realm.where(SeguroItem.class)
                .findAll();
        result.sort("id", Sort.DESCENDING);
        if(result.size()>0)
        {
           for(int i=0;i<result.size();i++)
           {
               if(result.get(i).getId()==id)
                   return i;
           }
        }
        realm.close();
        realm=null;
        return id;
    }

}
