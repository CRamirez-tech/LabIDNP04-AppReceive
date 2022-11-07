package org.idnp.idnp2022lab04;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private IntentFilter filter;
    private MyMessageBroadcast receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new MyMessageBroadcast();
        filter = new IntentFilter(MyMessageBroadcast.BROADCASTING_ACTION);

        Button btnMensaje = findViewById(R.id.btnMensaje);
        EditText editMensaje = findViewById(R.id.editMensaje);


        btnMensaje.setOnClickListener(v -> {
            Intent intent = new Intent(MyMessageBroadcast.BROADCASTING_ACTION);
            intent.putExtra(MyMessageBroadcast.EXTRA_MESSAGE, editMensaje.getText().toString());
            sendBroadcast(intent);
        });
        updateMessage();
    }

    private void updateMessage() {
        TextView textViewMessage = findViewById(R.id.textViewMessage);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                textViewMessage.setText(receiver.getContMensaje());
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(myBroadcast);
    }
}