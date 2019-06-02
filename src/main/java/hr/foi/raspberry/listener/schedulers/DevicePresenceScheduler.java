package hr.foi.raspberry.listener.schedulers;

import hr.foi.raspberry.listener.exceptions.BadDeviceDataException;
import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.mqtt.MqttHolder;
import hr.foi.raspberry.listener.service.device.DeviceService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DevicePresenceScheduler {

    private static final Logger log = LoggerFactory.getLogger(DevicePresenceScheduler.class);

    private final MqttHolder mqttHolder;
    private final DeviceService deviceService;

    public DevicePresenceScheduler(MqttHolder mqttHolder, DeviceService deviceService) {
        this.mqttHolder = mqttHolder;
        this.deviceService = deviceService;
    }

    @Scheduled(fixedRate = 300000,  initialDelay = 20000)
    public void sendPresenceProof() {
        try {
            log.info("Sending presence proof to MQTT server.");
            Device device = deviceService.findDeviceData()
                    .orElseThrow(() -> new BadDeviceDataException("Device not present"));

            String message = String.format("DEVICE_ID %s;ACTIVITY_PROOF;", device.getDeviceId());

            sendMqttMessage(message, device.getMqttTopicTitle());
        } catch (Exception e) {
            log.info("Sending message to mqtt topic error: ", e);
        }
    }

    private void sendMqttMessage(String messageData, String mqttTopicTitle) throws MqttException {
        if (mqttHolder.getMqttClient().isConnected()) {
            MqttMessage message = new MqttMessage();
            message.setPayload(messageData.getBytes());
            mqttHolder.getMqttClient().publish(mqttTopicTitle, message);

            log.info("Sending message [ {} ] to mqtt topic", message);
        } else {
            log.warn("Mqtt client is not connected!");
        }
    }

}
