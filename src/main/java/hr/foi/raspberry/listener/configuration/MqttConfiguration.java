package hr.foi.raspberry.listener.configuration;

import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.mqtt.ListenerMqttCallBack;
import hr.foi.raspberry.listener.mqtt.MqttHolder;
import hr.foi.raspberry.listener.service.device.DeviceService;
import hr.foi.raspberry.listener.service.sender.SenderService;
import hr.foi.raspberry.listener.threads.BeaconDataPurgeThread;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class MqttConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BeaconDataPurgeThread.class);

    private final DeviceService deviceService;
    private final SenderService senderService;

    public MqttConfiguration(DeviceService deviceService, SenderService senderService) {
        this.deviceService = deviceService;
        this.senderService = senderService;
    }

    @Bean
    public MqttHolder createMqttHolder() {
        Device device = deviceService.findDeviceData();
        MqttHolder mqttHolder = null;
        if (device != null) {
            ListenerMqttCallBack listenerMqttCallBack = new ListenerMqttCallBack(deviceService, senderService);

            try {
                MqttClient client = new MqttClient(device.getMqttTopicUrl(), MqttClient.generateClientId());
                client.setCallback(listenerMqttCallBack);
                client.connect();
                client.subscribe(device.getMqttTopicTitle());
                mqttHolder = new MqttHolder(client);
            } catch (MqttException e) {
                logger.error("Error while creating MqttHolder: ", e);
            }
        }

        return mqttHolder;
    }
}
