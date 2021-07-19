package com.faisal.basemodule.service;

public interface IMqttService {
    void publishMessage(String topics,String message);
    void stopService();
    void subscribeToTopic(String topics);
    void unSubscribeToTopic(String topics);
    void startConnectionThread();
}
