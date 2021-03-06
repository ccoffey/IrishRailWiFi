package ie.cathalcoffey.android.irishrailwifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MyActivity extends Activity
{
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    
    EditText editTextUsername;
    CheckBox checkBox1;
    TextView textView;
    Button button;
    
    String username;
    Boolean checked;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);
        
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        spe = sp.edit();
        
        username = sp.getString("@string/username", "");
        checked = sp.getBoolean("@string/checked", false);
        
        editTextUsername = (EditText) findViewById(R.id.editText1);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        textView = (TextView)findViewById(R.id.textView1);
        button = (Button) findViewById(R.id.button1);
        
        editTextUsername.setText(username);
        checkBox1.setChecked(checked);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        button.setEnabled(sp.getBoolean("@string/checked", false));
        
        ScrollView sView = (ScrollView) findViewById(R.id.scrollview);
        sView.setVerticalScrollBarEnabled(false);
        sView.setHorizontalScrollBarEnabled(false);

        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox1.setOnCheckedChangeListener(
    		new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean checked) {
					button.setEnabled(checked);
					
					if(!checked)
					{
						spe.putBoolean("@string/checked", checked);
						spe.commit();
					}
				} 
			}
        );
        
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                username = editTextUsername.getText().toString();
                checked = checkBox1.isChecked();

                if (username.equalsIgnoreCase("cathal coffey"))
                {
                    ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                    imageView.setImageResource(R.drawable.irishrailwifi_ccoffey);
                }

                else
                {
                    spe.putString("@string/username", username);
                    spe.putBoolean("@string/checked", checked);
                    spe.commit();

                    Intent intent = new Intent(ForegroundService.ACTION_BACKGROUND);
                    intent.setClass(getApplicationContext(), MyService.class);
                    startService(intent);

                    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                    String ssid = wifiInfo.getSSID();
                    String irishrailwifi = getString(R.string.ssid);
                    
                    if (ssid != null && ssid.toLowerCase().contains(irishrailwifi.toLowerCase()))
                    {
                    	if(sp.getBoolean("@string/checked", false))
                            new IrishRailWiFi().execute(username, checked.toString().toLowerCase());
                    }

                    finish();
                }
            }
        });
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	
    	checkBox1.setChecked(sp.getBoolean("@string/checked", false));
    	button.setEnabled(sp.getBoolean("@string/checked", false));
    }
}