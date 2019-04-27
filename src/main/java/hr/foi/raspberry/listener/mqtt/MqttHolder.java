package hr.foi.raspberry.listener.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.annotation.PreDestroy;

public class MqttHolder {

    private MqttClient mqttClient;

    public MqttHolder(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    @PreDestroy
    public void disconnect() {
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
