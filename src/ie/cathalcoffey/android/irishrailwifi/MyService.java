package ie.cathalcoffey.android.irishrailwifi;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;

public class MyService extends ForegroundService
{

    MyBroadcastReceiver bc;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.bc = new MyBroadcastReceiver();
        registerReceiver(bc, new IntentFilter(
                WifiManager.NETWORK_STATE_CHANGED_ACTION));

        // Causes the operating system to weight this service as more important
        // as it is doing constant work.
        new BusyWork().execute();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if (bc != null)
            unregisterReceiver(bc);
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);
    }
}
