package com.example.Bus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.CemeteryAdapter;
import com.example.demogooglemap.R;
import com.example.model.Cemetery;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GetCemeteryAsyn extends AsyncTask<String, Void, Void> {

	private Context context;
	private List<Cemetery> listcemetery;
	private ArrayAdapter<Cemetery> adcemetery;
	private ListView lv;
	private String cemeteryname, provincename, districtname;

	public GetCemeteryAsyn() {

	}

	public GetCemeteryAsyn(Context context, ListView lv, String cemeteryname,
			String provincename, String districtname) {
		this.context = context;
		this.lv = lv;
		listcemetery = new ArrayList<Cemetery>();
		this.cemeteryname = cemeteryname;
		this.districtname = districtname;
		this.provincename = provincename;
	}

	@Override
	protected Void doInBackground(String... params) {
		String url = params[0];
		GetAndPostJson gap = new GetAndPostJson();
		JSONObject object = new JSONObject();
		try {
			object.put("province", provincename);
			object.put("district", districtname);
			object.put("cemetery", cemeteryname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = gap.PostAndGet2(url, object, "cemetery");
		Log.e("bao", data);
		Readandparsejson(data);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		adcemetery = new CemeteryAdapter(context,
				R.layout.item_listview_cemetery_layout, listcemetery);
		lv.setAdapter(adcemetery);
	}

	private void Readandparsejson(String data) {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray arrcemetery = object.getJSONArray("cemetery");
			for (int i = 0; i < arrcemetery.length(); i++) {
				JSONObject jsoncemetery = arrcemetery.getJSONObject(i);
				Cemetery cemetery = new Cemetery();
				cemetery.setName(jsoncemetery.getString("namecemtery"));
				cemetery.setTypecemetery(jsoncemetery.getString("type"));
				cemetery.setVillage(jsoncemetery.getString("namevillage"));
				cemetery.setDistrict(jsoncemetery.getString("namedistrict"));
				cemetery.setProvince(jsoncemetery.getString("nameprovince"));

				cemetery.setWard(jsoncemetery.getString("nameward"));
				cemetery.setCountry(jsoncemetery.getString("namenational"));
				cemetery.setArea(jsoncemetery.getString("area"));
				cemetery.setNorth_near(jsoncemetery.getString("north_near"));
				cemetery.setWest_near(jsoncemetery.getString("west_near"));
				cemetery.setSouth_near(jsoncemetery.getString("south_near"));
				cemetery.setEast_near(jsoncemetery.getString("east_near"));
				String[] gps = jsoncemetery.getString("gps").split(",");
				String[] google = jsoncemetery.getString("google_map").split(
						",");
				try {
					LatLng latlonggps = new LatLng(Double.parseDouble(gps[0]),
							Double.parseDouble(gps[1]));
					LatLng latlonggoogle = new LatLng(
							Double.parseDouble(google[0]),
							Double.parseDouble(google[1]));

					cemetery.setGps(latlonggps);
					cemetery.setGooglemap(latlonggoogle);
				} catch (Exception e) {
				}
				listcemetery.add(cemetery);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
