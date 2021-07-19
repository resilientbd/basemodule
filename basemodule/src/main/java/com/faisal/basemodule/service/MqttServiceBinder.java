package com.faisal.basemodule.service;

import android.os.Binder;
import android.os.IBinder;

public class MqttServiceBinder extends Binder {
    private MqttService mqttService;
    public MqttServiceBinder(MqttService mqttService)
    {
        this.mqttService = mqttService;
    }

    public MqttService getMqttService() {
        return mqttService;
    }
}
