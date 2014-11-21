package com.example.demogooglemap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.model.Cemetery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends ActionBarActivity {

	private GoogleMap map;
	// private ArrayList<LatLng> Totalpoint;
	private LatLng pointorigin = null;
	private LatLng pointdes = null;
	private String datadistance;
	private String stringresponse = null;
	private AutoCompleteTextView autocomplete;
	private List<Cemetery> listcemetery;
	private List<String> arr_name_cemetery;
	private ArrayAdapter<String> ad;
	ImageView imv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View imv = (ImageView) findViewById(R.id.imageView1);
		autocomplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		arr_name_cemetery = new ArrayList<String>();
		ad = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr_name_cemetery);
		autocomplete.setAdapter(ad);
		listcemetery = new ArrayList<Cemetery>();

		// Getting reference to SupportMapFragment of the activity_main
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragmentMaps);

		// Getting reference to Button
		Button btnDraw = (Button) findViewById(R.id.btn_draw);

		// Getting Map for the SupportMapFragment
		map = fm.getMap();// bi loi khuc nay

		// Enable MyLocation Button in the Map
		map.setMyLocationEnabled(true);

		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub
				Log.e("vi do", String.valueOf(point.latitude));
				Log.e("kinh do", String.valueOf(point.longitude));
			}
		});
		// Setting onclick event listener for the map

		// The map will be cleared on long click
		map.setOnMapLongClickListener(new OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng point) {
				// Removes all the points from Google Map
				map.clear();

				// Removes all the points in the ArrayList
				listcemetery.clear();
			}
		});

		// Click event handler for Button btn_draw
		btnDraw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Checks, whether start and end locations are captured

				// LatLng origin = markerPoints.get(0);
				// LatLng dest = markerPoints.get(1);
				String namecemetery = autocomplete.getText().toString();
				if (!namecemetery.equalsIgnoreCase("")) {

					// find latitude and longitude in list
					for (int i = 0; i < listcemetery.size(); i++) {
						if (listcemetery.get(i).getName()
								.equalsIgnoreCase(namecemetery)) {
							pointdes = listcemetery.get(i).getLatLng();
							// Getting URL to the Google Directions API
							String url = getDirectionsUrl(pointorigin, pointdes);
							Log.e("duong dan", url);
							DownloadTask downloadTask = new DownloadTask();

							// Start downloading json data from Google
							// Directions API
							downloadTask.execute(url);
							break;
						}
					}

				} else {
					Toast.makeText(getApplicationContext(),
							"No Destination Or Origin", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		findViewById(R.id.btnmylocation).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						map.clear();
						Getmylocation();
					}
				});

		findViewById(R.id.btntim).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listcemetery.clear();
				arr_name_cemetery.clear();
				new getlocationasyn().execute();
			}
		});

		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				// pointdes = marker.getPosition();
				// Cemetery cm = new Cemetery();
				// cm.setLatLng(pointdes);
				// cm.setName(marker.getTitle());
				// listcemetery.add(cm);
				autocomplete.setText(marker.getTitle());
				// Log.e("vi do:", String.valueOf(pointdes.latitude));
				// Log.e("kinh do:", String.valueOf(pointdes.longitude));
			}
		});

		map.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {

			@Override
			public void onMyLocationChange(Location location) {
				// thuc hien xoay mui ten va load len map
				Showarrow(location);
			}
		});

	}

	protected void Showarrow(Location location) {
		if (pointdes != null) {
			float degree = (float) Math.toDegrees(Math.atan2(
					-(location.getLatitude() - pointdes.latitude),
					location.getLongitude() - pointdes.longitude));

			degree += 180;
			Bitmap bmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.arrowdirection);
			Matrix mtx = new Matrix();
			mtx.postRotate(degree);

			Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, 34, 50, mtx, true);

			imv.setImageBitmap(bm);
		}
	}

	private class getlocationasyn extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			Getlocationcemetery();
			publishProgress();
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			ParseJsonandloadmap(stringresponse);
		}

	}

	protected void Getlocationcemetery() {
		String point = pointorigin.latitude + "@" + pointorigin.longitude;

		HttpClient httpclient = new DefaultHttpClient();
		String URL = "http://117.6.131.222:6789/pos/wspos/TAMLINH/"
				+ "wsgetlocation.php";

		try {
			HttpPost httppost = new HttpPost(URL);

			List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("point", point));
			// httppost.setHeader("Content-type", "application/json");
			httppost.setEntity(new UrlEncodedFormEntity(nvp));
			HttpResponse response = httpclient.execute(httppost);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			stringresponse = httpclient.execute(httppost, responseHandler);
			Log.e("dsfd", stringresponse);

		} catch (Exception e) {
			// e.printStackTrace();
			Log.e("asdas", e.toString());
		}

	}

	private void ParseJsonandloadmap(String json) {

		try {
			JSONObject object = new JSONObject(json);
			JSONArray jsonlocation = object.getJSONArray("location");
			for (int i = 0; i < jsonlocation.length(); i++) {
				JSONObject location = jsonlocation.getJSONObject(i);
				LatLng latlong = new LatLng(
						(double) Double.parseDouble(location
								.getString("latitude")),
						(double) Double.parseDouble(location
								.getString("longitude")));

				Log.e("ve point moi", "ok");
				MarkerOptions options = new MarkerOptions();
				options.position(latlong);
				options.title(location.getString("name_cemetery"));
				Bitmap bm = BitmapFactory.decodeResource(getResources(),
						R.drawable.rip);
				options.icon(BitmapDescriptorFactory.fromBitmap(bm));

				map.addMarker(options).showInfoWindow();
				// add name cemetery in listnamecemetery

				String name = location.getString("name_cemetery");
				Cemetery cm = new Cemetery();
				cm.setLatLng(latlong);
				cm.setName(name);
				listcemetery.add(cm);
				// add list name show autocomplete text
				arr_name_cemetery.add(name);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ad.notifyDataSetChanged();
	}

	protected void Getmylocation() {
		Location mylocation = map.getMyLocation();
		if (mylocation != null) {
			Log.e("vi do", String.valueOf(mylocation.getLatitude()));
			Log.e("kinh do", String.valueOf(mylocation.getLongitude()));
			LatLng latlong = new LatLng(mylocation.getLatitude(),
					mylocation.getLongitude());

			pointorigin = latlong;
			// Totalpoint.add(pointorigin);
			map.addMarker(new MarkerOptions()
					.position(latlong)
					.title("My Location")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15));

		} else {
			Toast.makeText(this, "Unable tos fetch the current location",
					Toast.LENGTH_SHORT).show();
		}

	}

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		// String sensor = "sensor=false";

		// Waypoints
		// String waypoints = "";
		// for (int i = 2; i < listcemetery.size(); i++) {
		// LatLng point = listcemetery.get(i).getLatLng();
		// if (i == 2)
		// waypoints = "waypoints=";
		// waypoints += point.latitude + "," + point.longitude + "|";
		// }

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest; // + "&" + sensor + "&"
															// + waypoints;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service

			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);

				// get distance,duration...
				datadistance = Getjsonfromweb("http://maps.google.com/maps/api/directions/json?origin="
						+ pointorigin.latitude
						+ ","
						+ pointorigin.longitude
						+ "&destination="
						+ pointdes.latitude
						+ ","
						+ pointdes.longitude + "&sensor=false&units=metric");

			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			Log.e("sau khi download url", data);
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			Readandparsejson(datadistance);
			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.e("after parser", routes.toString());
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {

			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(2);
				lineOptions.color(Color.RED);
			}

			// Drawing polyline in the Google Map for the i-th route

			map.addPolyline(lineOptions);
		}

	}

	private String Getjsonfromweb(String url) {
		try {

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
							(InputStream) new URL(url).getContent()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}

			return stringBuilder.toString();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e("loi client protocol", e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("loi ioexception", e.toString());
		}
		return null;
	}

	private void Readandparsejson(String data) {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONArray("routes");

			JSONObject jsonlegs = array.getJSONObject(0).getJSONArray("legs")
					.getJSONObject(0);
			// JSONArray arraylegs = object0.getJSONArray("legs");
			//
			// JSONObject jsonlegs = arraylegs.getJSONObject(0);

			JSONObject distance = jsonlegs.getJSONObject("distance");
			JSONObject duration = jsonlegs.getJSONObject("duration");
			String start_address = jsonlegs.getString("start_address");
			String end_address = jsonlegs.getString("end_address");
			Log.e("distance", distance.getString("text"));
			Log.e("duration", duration.getString("text"));
			Log.e("start", start_address);
			Log.e("end", end_address);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
