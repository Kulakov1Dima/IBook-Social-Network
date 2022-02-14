package com.example.ibook_social_network;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class WifiFriendsActivity extends AppCompatActivity {

    String tag = "Wifi";

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChanel;
    BroadcastReceiver mReceiver;

    IntentFilter mIntentFilter = new IntentFilter();

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviseNameArray;
    WifiP2pDevice[] deviceArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int PERMISSION_REQUEST = 1;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
        }else{
            setContentView(R.layout.activity_wifi_friends);
        }


        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChanel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChanel, this);


        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)) {
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviseNameArray = new String [peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];
                int index = 0;

                for(WifiP2pDevice device : peerList.getDeviceList()){
                    deviseNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    index++;
                }

                Toast.makeText(getApplicationContext(),"Devise Found", Toast.LENGTH_SHORT).show();

                ListView devisesList = findViewById(R.id.devisesList);
                ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, deviseNameArray);
                devisesList.setAdapter(adapter);

            }

            if(peers.size()==0){
                Toast.makeText(getApplicationContext(),"No Devise Found", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Wifi","Start ");
        registerReceiver(mReceiver, mIntentFilter);
        new Thread(){
            private int count=0;
            public void run()
            {
                mManager.discoverPeers(mChanel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(tag,"SUCCESS - started discovering peers");
                    }
                    @Override
                    public void onFailure(int reason) {
                        count++;
                        String err=new String();
                        if(reason==WifiP2pManager.BUSY) err="BUSY";
                        if(reason==WifiP2pManager.ERROR)err="ERROR";
                        if(reason==WifiP2pManager.P2P_UNSUPPORTED) err="P2P_UNSUPPORTED";
                        Log.d(tag,"FAIL - couldnt start to discover peers code: "+err+" ("+count+")");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if(count>=20)return;
                        mManager.discoverPeers(mChanel, this);
                    }
                });
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}