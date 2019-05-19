package hr.foi.raspberry.listener.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

public class MqttHolder {

    private static final Logger logger = LoggerFactory.getLogger(MqttHolder.class);

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
            logger.error("Error while disconnecting from MQTT: ", e);
        }
    }
}
