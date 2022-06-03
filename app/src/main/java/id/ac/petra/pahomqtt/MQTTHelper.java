package id.ac.petra.pahomqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTHelper {
   public MqttAndroidClient mqttAndroidClient;

   final String broker = "tcp://broker.hivemq.com:1883";
   final String clientId = "AndroidClient";
   final String subTopic = "sensor/+";

   public MQTTHelper(Context c) {
      mqttAndroidClient = new MqttAndroidClient(c,broker,clientId);
      mqttAndroidClient.setCallback(new MqttCallbackExtended() {
         @Override
         public void connectComplete(boolean reconnect, String serverURI) {
            Log.w("MQTT",serverURI);
         }

         @Override
         public void connectionLost(Throwable cause) {

         }

         @Override
         public void messageArrived(String topic, MqttMessage message) throws Exception {
            Log.w("MQTT",message.toString());
         }

         @Override
         public void deliveryComplete(IMqttDeliveryToken token) {

         }
      });
      connect();
   }

   public void setCallBack(MqttCallbackExtended callback) {
      mqttAndroidClient.setCallback(callback);
   }

   private void connect() {
      MqttConnectOptions o = new MqttConnectOptions();
      o.setAutomaticReconnect(true);
      o.setCleanSession(false);
      //o.setUserName(...);
      //o.setPassword(...);


      try {
         mqttAndroidClient.connect(o, null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
               subcribeTopic();
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
               Log.d("MQTT","Gagal terhubung ke "+broker+"\n"+exception.toString());
            }
         });
      } catch (MqttException e) {
         e.printStackTrace();
      }
   }

   private void subcribeTopic() {
      try {
         mqttAndroidClient.subscribe(subTopic, 0, null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
               Log.w("MQTT","Subscribe berhasil");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
               Log.w("MQTT","Subscribe gagal!");
            }
         });
      } catch (MqttException e) {
         e.printStackTrace();
      }
   }


}
