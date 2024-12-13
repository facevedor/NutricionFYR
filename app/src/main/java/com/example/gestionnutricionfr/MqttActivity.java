package com.example.gestionnutricionfr;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//librerias mqtt y formulario
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
public class MqttActivity extends AppCompatActivity {

    private static String mqttHOst="tcp://rippletouch839.cloud.shiftr.io:1883";
    private static String IdUsuario="AppAndroid";

    private static String Topic="Mensaje";
    private static String User="rippletouch839";
    private static String Pass="dtiqq3uXXM3atfl4";

    private TextView textView;
    private EditText editTextMessage;
    private Button buttonEnvio;
    private MqttClient mqttClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);

        textView = findViewById(R.id.textView);
        editTextMessage = findViewById(R.id.txtMensaje);
        buttonEnvio = findViewById(R.id.btnEnviarMensaje);
        try {
            mqttClient = new MqttClient(mqttHOst, IdUsuario, null);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(User);
            options.setPassword(Pass.toCharArray());

            mqttClient.connect(options);
            Log.d("MQTT", "Conexión establecida correctamente");
            Toast.makeText(this, "Conectado al servidor MQTT", Toast.LENGTH_SHORT).show();

            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("MQTT", "Conexión perdida: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    String payload = new String(message.getPayload());
                    runOnUiThread(() -> textView.setText(payload));
                    Log.d("MQTT", "Mensaje recibido: " + payload);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d("MQTT", "Mensaje entregado: " );
                }
            });
        } catch (MqttException e) {
            Log.d("MQTT", "Error al conectar al servidor MQTT: " + e.getMessage());
            e.printStackTrace();

        }

        buttonEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editTextMessage.getText().toString();
                try {
                    if (mqttClient != null && mqttClient.isConnected()) {
                        mqttClient.publish(Topic, message.getBytes(), 0, false);
                        textView.append("\n-" + message);
                        Toast.makeText(MqttActivity.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MqttActivity.this, "No se pudo enviar el mensaje", Toast.LENGTH_SHORT).show();
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });



    }
}
