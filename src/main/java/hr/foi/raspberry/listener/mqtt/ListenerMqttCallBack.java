package hr.foi.raspberry.listener.mqtt;

import hr.foi.raspberry.listener.mqtt.cain.AbstractCommandChain;
import hr.foi.raspberry.listener.mqtt.cain.DeviceNameCommandChain;
import hr.foi.raspberry.listener.mqtt.cain.IntervalCommandChain;
import hr.foi.raspberry.listener.service.device.DeviceService;
import hr.foi.raspberry.listener.service.sender.SenderService;
import hr.foi.raspberry.listener.utils.CommonUtils;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListenerMqttCallBack implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(ListenerMqttCallBack.class);

    private final DeviceService deviceService;
    private final SenderService senderService;
    private AbstractCommandChain commandChain;

    public ListenerMqttCallBack(DeviceService deviceService, SenderService senderService) {
        this.deviceService = deviceService;
        this.senderService = senderService;
        this.commandChain = createAbstractCommandChain();
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.warn("Mqtt connection Lost: {}", throwable);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) {
        try {
            if (!CommonUtils.isSyntaxValid(CommonUtils.DEVICE_ACTIVITY_PROOF_MESSAGE, mqttMessage.toString())) {
                logger.info("MQTT message arrived: {}", mqttMessage.toString());
                commandChain.handleCommand(mqttMessage.toString());
            }
        } catch (Exception e) {
            logger.error("Error while handling mqtt message: {}; {}", e.getMessage(), e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        logger.info("Mqtt: deliveryComplete");
    }

    private AbstractCommandChain createAbstractCommandChain() {
        AbstractCommandChain abstractCommandChain = new DeviceNameCommandChain(senderService, deviceService, null);
        abstractCommandChain = new IntervalCommandChain(senderService, deviceService, abstractCommandChain);

        return abstractCommandChain;
    }

}
