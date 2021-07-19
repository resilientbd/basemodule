package com.faisal.basemodule.base;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.core.content.ContextCompat;

import com.faisal.basemodule.service.IMqttService;
import com.faisal.basemodule.service.MqttService;
import com.faisal.basemodule.service.MqttServiceBinder;
import com.faisal.basemodule.util.SharedPrefUtil;

public abstract class BaseServiceActivity extends BaseActivity {
    private IMqttService mqttService;
    private Intent serviceIntent;

    @Override
    protected void onResume() {
        super.onResume();
        if (serviceIntent != null) {
            bindService(serviceIntent, connection, 0);
        }
        onResumeBaseActivity();
    }

    public IMqttService getMqttService() {
        return mqttService;
    }

    public abstract void onResumeBaseActivity();

    public void startMqttService(String url, String uniqueId) {
        serviceIntent = new Intent(this, MqttService.class);
        SharedPrefUtil.ADD_PREFERENCE(getBaseContext(),MqttService.CONNECTION_URL,url,true);
        SharedPrefUtil.ADD_PREFERENCE(getBaseContext(),MqttService.UNIQUE_ID,uniqueId,true);
       // startService(serviceIntent);
        boolean isServiceRunning = MqttService.isMyServiceRunning(MqttService.class, this);


        if (!isServiceRunning) {

            // serviceIntent.putExtra("inputExtra", input);
            startService(serviceIntent);

        }

        bindService(serviceIntent, connection, 0);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mqttService = ((MqttServiceBinder) service).getMqttService();
            onBaseServiceConnected(mqttService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            onBaseServiceDisconnected(null);
        }
    };
    public abstract void onBaseServiceConnected(IMqttService mqttService);
    public abstract void onBaseServiceDisconnected(IMqttService mqttService);
}
