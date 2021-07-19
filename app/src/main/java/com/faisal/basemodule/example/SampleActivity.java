package com.faisal.basemodule.example;

import android.util.Log;

import com.faisal.basemodule.base.BaseServiceActivity;
import com.faisal.basemodule.provider.mqtt.MqttHandler;
import com.faisal.basemodule.service.IMqttService;
import com.faisal.basemodule.service.MqttService;
import com.faisal.basemodule.util.ConnectionUtil;
import com.faisal.basemodule.util.RandomTextGenerator;
import com.faisal.basemodule.util.Toaster;
import com.faisal.basemodule.example.databinding.ActivitySampleBinding;


public class SampleActivity extends BaseServiceActivity {
    ActivitySampleBinding mBinding;
    SampleViewModel viewModel;
    IMqttService mqttService;
    @Override
    public int setLayoutId() {
        return R.layout.activity_sample;
    }

    @Override
    public void startUI() {
        mBinding = (ActivitySampleBinding) getViewDataBinding();
        viewModel = (SampleViewModel) getViewModel(SampleViewModel.class);
       // Toaster.ShowText("Toaster worked with broadcast receiver");
        mBinding.textView.setOnClickListener(v -> {
           // Toaster.ShowText("Clicked!!");
            if(mqttService!=null)
            {
                mqttService.publishMessage("test/org","hello!");
            }
            else {
                Toaster.ShowText("service not connected!");
            }
        });
        String id  = RandomTextGenerator.generateRandomText(16);
        startMqttService("tcp://3.129.251.210:1883",id );
        Log.d("chk","id:"+id);
        MqttHandler.mqttConnectionStatusLiveData.observe(this,status -> {
            Toaster.ShowText("Status:"+status);
           if(!status)
           {
               mqttService.startConnectionThread();
           }
        });

    }



    @Override
    public void onResumeBaseActivity() {

    }

    @Override
    public void onBaseServiceConnected(IMqttService mqttService) {
       this.mqttService = mqttService;
       Log.d("chkmudule","service connected!");
    }

    @Override
    public void onBaseServiceDisconnected(IMqttService mqttService) {
        this.mqttService = mqttService;

    }
}
