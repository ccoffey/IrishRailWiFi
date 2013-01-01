package ie.cathalcoffey.android.irishrailwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getDataString().contains("ie.cathalcoffey.android.irishrailwifi"))
        {
            Intent serviceIntent = new Intent(
                    ForegroundService.ACTION_BACKGROUND);
            serviceIntent.setClass(context, MyService.class);
            context.startService(serviceIntent);
        }
    }

}
