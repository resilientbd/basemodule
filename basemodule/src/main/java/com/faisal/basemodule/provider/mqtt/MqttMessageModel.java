package com.faisal.basemodule.provider.mqtt;

public class MqttMessageModel {
    private String topics;
    private String message;

    public MqttMessageModel() {
    }

    public MqttMessageModel(String topics, String message) {
        this.topics = topics;
        this.message = message;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
