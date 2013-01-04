package ie.cathalcoffey.android.irishrailwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

public class MyBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        final String action = intent.getAction();
        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION))
        {
            NetworkInfo ni = (NetworkInfo) intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (ni.getState() == State.CONNECTED)
            {
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();

                SharedPreferences sp = PreferenceManager
                        .getDefaultSharedPreferences(context);
                String username = sp.getString("@string/username", "");
                Boolean checked = sp.getBoolean("@string/checked", false);

                String irishrailwifi = context.getString(R.string.ssid);
                if (ssid != null && ssid.toLowerCase().contains(irishrailwifi.toLowerCase()))
                {
                	if(sp.getBoolean("@string/checked", false))
                        new IrishRailWiFi().execute(username, checked.toString().toLowerCase());
                }
            }
        }
    }
}
