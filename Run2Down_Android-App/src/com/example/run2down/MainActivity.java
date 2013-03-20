
package com.example.run2down;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.hacku1.model.GeoCodeResult;
//import com.example.hacku1.services.GeoCoder;

public class MainActivity extends Activity {

    private TextView text;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    
    //private GeoCoder geoCoder = new GeoCoder();
    
    protected LocationManager locationManager;
    protected Location currentLocation;

    /*protected Button retrieveLocationButton;
    protected Button reverseGeocodingButton;
    public double oldlat=0.00;
    public double oldlong=0.00;*/
    //DatabaseHandler dbh=new DatabaseHandler(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textView1);

        //retrieveLocationButton = (Button) findViewById(R.id.retrieve_location_button);
        //reverseGeocodingButton = (Button) findViewById(R.id.reverse_geocoding_button);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
        
        /*
        retrieveLocationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentLocation();
            }
        });
        
        reverseGeocodingButton.setOnClickListener(new OnClickListener() {            
            @Override
            public 	void onClick(View v) {                
                performReverseGeocodingInBackground();
            }
        });
        */
    }  
    public void onClick(View view)
	{
    	text.setText("working");
	}
    /*
    protected void performReverseGeocodingInBackground() {
        showCurrentLocation();
        //new ReverseGeocodeLookupTask().execute((Void[])null);
    }*/

    protected void showCurrentLocation() {
    	
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (currentLocation != null ) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    currentLocation.getLongitude(), currentLocation.getLatitude()
            );
            Context context = getApplicationContext();
            Toast.makeText(context, message,
                    Toast.LENGTH_LONG).show();
        }
        
        
        	
    }   

    private class MyLocationListener implements LocationListener {

    	public float old_speed=0,speed=0,sum=0,count=0,av=0,lastGPSPointX=0,lastGPSPointY=0,distance=0,time=0,seconds=0,lasttime=0;
        public void onLocationChanged(Location location) {
        	
        	String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s \n",
                    location.getLongitude(), location.getLatitude()
            );
        	Calendar c = Calendar.getInstance();
        	float currentGPSPointX=(float) location.getLongitude()*10000000;
        	float currentGPSPointY=(float) location.getLatitude()*10000000;
        	seconds = c.get(Calendar.SECOND);
        	if(lasttime!=0)
        	{
        		distance=(FloatMath.sqrt((currentGPSPointX - lastGPSPointX)*(currentGPSPointX - lastGPSPointX) + (currentGPSPointY - lastGPSPointY)*(currentGPSPointY - lastGPSPointY)));
        		if(seconds<lasttime)
        			lasttime=lasttime-60;
        		time=seconds-lasttime;
        		speed=distance/time;
        	}
        	String msg1 = String.format("Speed : %1$s \n ", speed);
        	Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        	Toast.makeText(MainActivity.this, msg1, Toast.LENGTH_LONG).show();
        	old_speed=location.getSpeed();
        	av=(count*av+speed)/(count+1);
        	sum = sum+speed;
        	count++;
        	String msg2 = String.format("Average : %1$s \n Count : %2$s \n ", av,count);
        	Toast.makeText(MainActivity.this, msg2, Toast.LENGTH_LONG).show();
            lasttime=seconds;
            lastGPSPointX=currentGPSPointX;
            lastGPSPointY=currentGPSPointY;
            store(av);
            /*
            //if(Math.abs(oldlat-location.getLatitude())+Math.abs(oldlong-location.getLongitude()) >0);
            //oldlat=location.getLatitude();
            //oldlong=location.getLongitude();
            /*
            dbh.addPlace(oldlat,oldlong,2.2);
            Toast.makeText(MainActivity.this, "Newer", Toast.LENGTH_LONG).show();
            List<location> contacts = dbh.getAllplaces();
            location  newc=new location();
            newc.setlong("6.9");
            newc.setlat("9.6");
            newc.ID=5;
            newc.radius=2.0;
            dbh.deleteplace(newc);
            for (location cn : contacts) {
            	Toast.makeText(MainActivity.this, ""+cn.getlat(), Toast.LENGTH_LONG).show();
        }*/
      }
        public void store(float av)
        {
        	String str = String.valueOf(av);
        	DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://home.iitk.ac.in/~vijaykes/start.php");
            try {
            List < NameValuePair > nameValuePairs = new ArrayList < NameValuePair > ();
            nameValuePairs.add(new BasicNameValuePair("type", str));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            /*try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                text.setText("works till here. 2");
                try {
              */      HttpResponse response = httpclient.execute(httppost);
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
                /*    String responseBody = EntityUtils.toString(response.getEntity());
                    text.setText(responseBody);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/ 
        }
        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MainActivity.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(MainActivity.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }
    
    /*public class ReverseGeocodeLookupTask extends AsyncTask <Void, Void, GeoCodeResult> {
        
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            this.progressDialog = ProgressDialog.show(
                    MainActivity.this,
                    "Please wait...contacting Yahoo!", // title
                    "Requesting reverse geocode lookup", // message
                    true // indeterminate
            );
        }*/

       /* @Override
        protected GeoCodeResult doInBackground(Void... params) {
            return geoCoder.reverseGeoCode(currentLocation.getLatitude(), currentLocation.getLongitude());
        }

        @Override
        protected void onPostExecute(GeoCodeResult result) {
            this.progressDialog.cancel();
            Toast.makeText(MainActivity.this, result.toString(), Toast.LENGTH_LONG).show();            
        }*/
        
    }
    
 