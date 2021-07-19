package com.faisal.basemodule.provider.mqtt;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MqttHandler implements MqttCallback {
    private Context context;
    public static MutableLiveData<Boolean> mqttConnectionStatusLiveData = new MutableLiveData<>();
    public static MutableLiveData<MqttMessageModel> mqttMessageModelLiveData = new MutableLiveData<>();

    public MqttHandler(Context context) {
        this.context = context;
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.d("chk", "connection lost:" + cause);
        mqttConnectionStatusLiveData.postValue(false);
        //EventBus.getDefault().post(new MqttConnectionLostModel(false));
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try{
            Log.d("chk", "arrived message:topic:" + topic + " || message:" + message);
            Log.d("chk", "topic:" + topic);
            Gson gson = new Gson();
            handMessage(topic,message.toString());
        }catch (Exception e)
        {
            Log.e("moduleerror","exception:"+e.getMessage());
        }

    }

    private void handMessage(String topic, String message) {
        MqttMessageModel messageModel = new MqttMessageModel(topic,message);
        mqttMessageModelLiveData.postValue(messageModel);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

        Log.d("chk", "delivery done, token:" + token);
    }
}
