package ie.cathalcoffey.android.irishrailwifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;
import android.util.Log;

public class IrishRailWiFi extends AsyncTask<Object, Void, Void>
{
	public HttpClient getNewHttpClient() 
	{
	    try 
	    {
	        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        trustStore.load(null, null);

	        SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

	        HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

	        SchemeRegistry registry = new SchemeRegistry();
	        registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	        registry.register(new Scheme("https", sf, 443));

	        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

	        return new DefaultHttpClient(ccm, params);
	    } 
	    
	    catch (Exception e) {
	        return new DefaultHttpClient();
	    }
	}
	
    @Override
    protected Void doInBackground(Object... params)
    {
    	HttpClient httpclient = getNewHttpClient();
        httpclient.getParams().setParameter("http.protocol.expect-continue", false);

        try
        {	        	
	        HttpPost httppost = new HttpPost("http://irishrail.on.icomera.com/register.php");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", (String) params[0]));
            nameValuePairs.add(new BasicNameValuePair("term_condition", (String) params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);
            
            /*BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder s = new StringBuilder();
            String sResponse;
            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            
            String html = s.toString();
            */
        	Log.i("cathal", "IrishRailWiFi: Connected");
        }

        catch (ClientProtocolException e)
        {
        	String msg = e.getMessage();
        	Log.e("cathal", msg);
        }

        catch (IOException e)
        {
        	String msg = e.getMessage();
        	Log.e("cathal", msg);
        }
        return null;
    }
}
