package hr.foi.raspberry.listener.configuration;

import hr.foi.raspberry.listener.model.device.Device;
import hr.foi.raspberry.listener.mqtt.ListenerMqttCallBack;
import hr.foi.raspberry.listener.mqtt.MqttHolder;
import hr.foi.raspberry.listener.service.DeviceService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfiguration {

    private final DeviceService deviceService;

    public MqttConfiguration(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Bean
    public MqttHolder createMqttHolder() {
        Device device = deviceService.findDeviceData();
        MqttHolder mqttHolder = null;
        if (device != null) {
            ListenerMqttCallBack listenerMqttCallBack = new ListenerMqttCallBack(deviceService);

            try {
                MqttClient client = new MqttClient(device.getMqttTopicUrl(), MqttClient.generateClientId());
                client.setCallback(listenerMqttCallBack);
                client.connect();
                client.subscribe(device.getMqttTopicTitle());
                mqttHolder = new MqttHolder(client);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        return mqttHolder;
    }
}
