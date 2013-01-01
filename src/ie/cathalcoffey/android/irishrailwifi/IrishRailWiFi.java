package ie.cathalcoffey.android.irishrailwifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class IrishRailWiFi extends AsyncTask<String, Void, Void>
{

    @Override
    protected Void doInBackground(String... params)
    {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter("http.protocol.expect-continue",
                false);

        HttpPost httppost = new HttpPost("http://irishrail.on.icomera.com/register.php");
        try
        {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", "coffey.cathal@gmail.com"));
            nameValuePairs.add(new BasicNameValuePair("term_condition", "true"));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            httpclient.execute(httppost);
            
            HttpResponse response = httpclient.execute(httppost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();
            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            
            String html = s.toString();
        }

        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
        }

        catch (IOException e)
        {
            // TODO Auto-generated catch block
        }
        return null;
    }
}
