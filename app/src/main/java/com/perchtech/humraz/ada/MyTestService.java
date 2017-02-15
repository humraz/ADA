package com.perchtech.humraz.ada;

import android.Manifest;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by humra on 2/14/2017.
 */
public class MyTestService extends IntentService implements SensorEventListener {
    //public int notificationFlag = 0;

    public MyTestService() {
        super("MyTestService");

    }
    private static final int REQUEST_CODE_PERMISSION = 2;
    String a;
    SensorManager sensorManager;

    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
//sendNotification("hi bitch");

but();

    }

    public void but( )
    {

        gps = new GPSTracker(MyTestService.this);
        final String URL = "http://192.168.1.56:3000/scriptMachineLearning";
// Post params to be sent to the server

        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("lat", Double.toString(latitude));
        params.put("long", Double.toString(longitude));

        JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MyTestService.this, "ddd", Toast.LENGTH_LONG).show();
                        System.out.println("Adfffffffffffffffffffffffffffffffffff");
                        try {
                            //Process os success response



                            String lat = response.getString("value");
                            Toast.makeText(MyTestService.this, lat, Toast.LENGTH_LONG).show();
                            if(lat.equals("1"))
                            {
send2("hi");
                            }
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MyTestService.this, "error", Toast.LENGTH_LONG).show();
                            System.out.println(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

// add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(request_json);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / 9.8f;
        float gY = y / 9.8f;
        float gZ = z / 9.8f;

        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);
        //tv.setText(Float.toString(gForce));
        if (gForce>6)
        {
          //  sendNotification("hi bitch");
            final Context c = this;
            gps = new GPSTracker(MyTestService.this);

            // check if GPS enabled
            if (gps.canGetLocation()) {
               // Toast.makeText(MyTestService.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
                MainActivity.notificationFlag =1;
                sendNotification("Should We Send Help??");
                sensorManager.unregisterListener(MyTestService.this);

                new CountDownTimer(10000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        if(MainActivity.notificationFlag==1) {
                            Toast.makeText(getApplicationContext(), "Help is Being Sent To Your Location\nLat: "
                                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


                            SmsManager sms = SmsManager.getDefault();
                            String phoneNumber = "8594014280";
                            String lat = Double.toString(latitude);
                            String lng = Double.toString(longitude);

                            String message = "http://maps.google.com/?q=" + lat + "," + lng;
                              sms.sendTextMessage(phoneNumber, null, message, null, null);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "You Canceled ", Toast.LENGTH_LONG).show();
                        }
                    }

                }.start();






              //  but(lat, lng);
              /*  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                        + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gps.showSettingsAlert();
            }
            System.out.println("force" + gForce);
        }

    }
    public void but(String lat,String lng)
    {



            final String URL = "http://192.168.1.38:3000/accident";
// Post params to be sent to the server
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("lat", lat);
            params.put("long", lng);

            JsonObjectRequest request_json = new JsonObjectRequest(URL, new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //Process os success response
                            }
                            catch (Exception e)
                            {

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            });

// add the request object to the queue to be executed
            AppController.getInstance().addToRequestQueue(request_json);
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;


    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Are You OK?")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg)
                                .setSound(Uri.parse("android.resource://"
                                       + getApplicationContext().getPackageName() + "/" + R.raw.s))
                                        //  .setSound(R.raq)
                        .setSmallIcon(R.drawable.question);



       AudioManager am;
        am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        int volume = am.getStreamVolume(AudioManager.STREAM_ALARM);
        am.setStreamVolume(AudioManager.STREAM_ALARM, 3,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void send2(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Please Be Carefull!")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(""))
                        .setContentText("You Are In An Accident Prone Area.")
                                    .setSound(Uri.parse("android.resource://"
                                         + getApplicationContext().getPackageName() + "/" + R.raw.aaaaaaaaa))
                                  //.setSound(R.raq)
                        .setSmallIcon(R.drawable.caracc);



        //   AudioManager am;
        // am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        //am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        //int volume = am.getStreamVolume(AudioManager.STREAM_ALARM);
        // am.setStreamVolume(AudioManager.STREAM_ALARM, 6,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}
