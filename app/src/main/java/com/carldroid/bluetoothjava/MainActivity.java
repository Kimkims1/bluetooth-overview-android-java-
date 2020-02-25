package com.carldroid.bluetoothjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 1;


    TextView statusIv, pairedIv;
    Button turnonbtn, turnoffbtn, discoverbtn, pairedbtn;

    ImageView btIv;

    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusIv = findViewById(R.id.bluetoothstatusTv);
        pairedbtn = findViewById(R.id.pairedbtn);
        pairedIv = findViewById(R.id.pairedtv);
        turnoffbtn = findViewById(R.id.turnoffbtn);
        turnonbtn = findViewById(R.id.turnonbtn);
        discoverbtn = findViewById(R.id.discoverablebtn);
        btIv = findViewById(R.id.bluetoothstatusIv);

        //create bt adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            statusIv.setText("Bluetooth isn't available");
        } else {
            statusIv.setText("Bluetooth is available");
        }

        if (bluetoothAdapter.isEnabled()) {
            btIv.setImageResource(R.drawable.ic_bluetooth_on);
        } else {
            btIv.setImageResource(R.drawable.ic_bluetooth_disabled);
        }

        turnonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Toast.makeText(MainActivity.this, "Turning on BlueTooth", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                } else {
                    Toast.makeText(MainActivity.this, "Bluetooth is already turned on", Toast.LENGTH_SHORT).show();
                }
            }
        });

        turnoffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                    Toast.makeText(MainActivity.this, "Turning Bluetooth off", Toast.LENGTH_SHORT).show();
                    btIv.setImageResource(R.drawable.ic_bluetooth_disabled);
                }

            }
        });

        discoverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isDiscovering()) {
                    Toast.makeText(MainActivity.this, "Making your device discoverable", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVERABLE_BT);
                }

            }
        });

        pairedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    Toast.makeText(MainActivity.this, "Paired Devices", Toast.LENGTH_SHORT).show();
                    Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address

                        pairedIv.setText("\n Device: " + deviceName + " ,Address: " + deviceHardwareAddress);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Turn on Bluetooth to get paired devices", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (requestCode == RESULT_OK) {
                    btIv.setImageResource(R.drawable.ic_bluetooth_on);
                    Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Couldn't trun on Bluetooth", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
