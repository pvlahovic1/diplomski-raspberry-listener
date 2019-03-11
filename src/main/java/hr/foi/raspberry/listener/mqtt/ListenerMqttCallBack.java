package hr.foi.raspberry.listener.mqtt;

import hr.foi.raspberry.listener.exceptions.BadCommandException;
import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.mqtt.cain.AbstractCommandChain;
import hr.foi.raspberry.listener.mqtt.cain.DeviceNameCommandChain;
import hr.foi.raspberry.listener.mqtt.cain.PuringCommandChain;
import hr.foi.raspberry.listener.service.DeviceService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListenerMqttCallBack implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(ListenerMqttCallBack.class);

    private final DeviceService deviceService;
    private AbstractCommandChain commandChain;

    public ListenerMqttCallBack(DeviceService deviceService) {
        this.deviceService = deviceService;
        this.commandChain = createAbstractCommandChain();
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.warn("Mqtt connection Lost: {}", throwable);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        logger.info("MQTT message arrived: {}", mqttMessage.toString());
        try {
            commandChain.handleCommand(mqttMessage.toString());
        } catch (BadCommandException | BadDeviceDataException e) {
            logger.error("Error while handling mqtt message: {}; {}", e.getMessage(), e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("Mqtt: deliveryComplete");
    }

    private AbstractCommandChain createAbstractCommandChain() {
        AbstractCommandChain abstractCommandChain = new DeviceNameCommandChain(deviceService, null);
        abstractCommandChain = new PuringCommandChain(deviceService, abstractCommandChain);

        return abstractCommandChain;
    }

}