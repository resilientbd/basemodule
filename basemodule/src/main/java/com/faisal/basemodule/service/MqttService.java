package com.faisal.basemodule.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.faisal.basemodule.provider.mqtt.MqttHandler;
import com.faisal.basemodule.util.ConnectionUtil;
import com.faisal.basemodule.util.SharedPrefUtil;
import com.faisal.basemodule.util.Toaster;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Objects;

public class MqttService extends Service implements IMqttActionListener, ConnectionUtil.StatusUpdateListener, IMqttService {
    MqttAndroidClient mqttClient;
    private String connectionUrl;
    private String uniqueId;
    public static String CONNECTION_URL = "connection_url";
    public static String UNIQUE_ID = "unique_id";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MqttServiceBinder(this);
    }

    @Override
    public void onUpdateStatus(Boolean isConnected) {
        if (isConnected)//internet connected
        {
            Log.d("chkconnection", "connected internet . reconnect mqtt..");
            connectMqtto(connectionUrl, uniqueId);
        }
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        Log.d("chkmodule", "mqtt connection success!");
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        Log.e("chkmodule", "mqtt failure!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            connectionUrl = SharedPrefUtil.GET_STRING_PREFERENCE(getBaseContext(),MqttService.CONNECTION_URL);
            uniqueId = SharedPrefUtil.GET_STRING_PREFERENCE(getBaseContext(),MqttService.UNIQUE_ID);
            if (connectionUrl.isEmpty() || uniqueId.isEmpty()) {
                Toaster.ShowText("mqtt connection url and unique id must be provided!");
            } else {
                connectMqtto(connectionUrl, uniqueId);
            }

        } catch (Exception e) {
            Log.e("chkmodule", "exception:" + e.getMessage());
        }
        return super.onStartCommand(intent, flags, startId);

    }

    private void connectMqtto(String mqttHostUrl, String appId) {
        Log.d("chkmodule", "connecting..");
        mqttClient = new MqttAndroidClient(getBaseContext(), mqttHostUrl, appId);
        //Set call back class
        mqttClient.setCallback(new MqttHandler(getBaseContext()));
        MqttConnectOptions connOpts = new MqttConnectOptions();
        try {
            IMqttToken token = mqttClient.connect(connOpts);
            token.setActionCallback(this);
            Log.d("chkmodule", "connection success!");
        } catch (Exception e) {
            Log.d("chkmodule", "unable to connection mqtt, exception:" + e.getMessage());
            ConnectionUtil.startConnectionListeningTask(getBaseContext(), this::onUpdateStatus);
        }
    }

    private void publish(String topic, String message) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttMessage.setQos(2);
        mqttMessage.setRetained(false);

        try {

            mqttClient.publish(topic, mqttMessage);


        } catch (MqttException e)
        {
            ConnectionUtil.startConnectionListeningTask(getBaseContext(), this::onUpdateStatus);
        }
        catch (Exception e) {

            // e.printStackTrace();
            Log.d("chkmodule", "error broadcast:" + e.getMessage());
        }
    }

    private void subscribe(String topic) {
        // mqttClient.subscribe(topics,null,this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mqttClient != null) {

                        mqttClient.subscribe(topic, 2, null, MqttService.this);
                        Log.d("chkmodule", "topic:" + topic);

                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                    Log.e("chkmodule", "exception subscription: " + e.getMessage());
                }
            }
        }).start();
    }

    private void unSubscribe(String topic) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mqttClient != null) {
                    try {
                        mqttClient.unsubscribe(topic);
                        Log.d("chkmodule", "unsubscribed: " + topic);
                    } catch (MqttException e) {
                        Log.e("chkmodule", "exception unsubscription: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    @Override
    public void publishMessage(String topics, String message) {
        publish(topics, message);
    }

    @Override
    public void stopService() {
        stopSelf();
    }

    @Override
    public void subscribeToTopic(String topics) {
        subscribe(topics);
    }

    @Override
    public void unSubscribeToTopic(String topics) {
        unSubscribe(topics);
    }

    @Override
    public void startConnectionThread() {
        ConnectionUtil.startConnectionListeningTask(getBaseContext(), this::onUpdateStatus);
    }

    public static boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : Objects.requireNonNull(manager).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
