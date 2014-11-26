package com.example.Bus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class GetVillageAsysn extends AsyncTask<String, Void, Void> {

	private Context context;
	private List<String> listvillage;
	private ArrayAdapter<String> advillage;
	private AutoCompleteTextView autovillage;
	private String districtname;

	public GetVillageAsysn() {

	}

	public GetVillageAsysn(Context context, AutoCompleteTextView auto,
			String districtname) {
		this.context = context;
		this.autovillage = auto;
		listvillage = new ArrayList<String>();
		this.districtname = districtname;
	}

	@Override
	protected Void doInBackground(String... params) {
		String url = params[0];
		GetAndPostJson gap = new GetAndPostJson();
		JSONObject object = new JSONObject();
		try {
			object.put("districtname", districtname);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = gap.PostAndGet2(url, object, "districtname");
		Log.e("bao", data);
		Readandparsejson(data);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		advillage = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, listvillage);
		autovillage.setAdapter(advillage);
	}

	private void Readandparsejson(String data) {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray arrvillage = object.getJSONArray("village");
			for (int i = 0; i < arrvillage.length(); i++) {
				listvillage.add(arrvillage.get(i).toString());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
