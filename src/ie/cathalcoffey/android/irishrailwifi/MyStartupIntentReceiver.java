package ie.cathalcoffey.android.irishrailwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyStartupIntentReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent serviceIntent = new Intent(ForegroundService.ACTION_BACKGROUND);
        serviceIntent.setClass(context, MyService.class);
        context.startService(serviceIntent);
    }
}
