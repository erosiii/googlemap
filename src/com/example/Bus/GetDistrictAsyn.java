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

public class GetDistrictAsyn extends AsyncTask<String, Void, Void> {

	private Context context;
	private List<String> listdistrict;
	private ArrayAdapter<String> addistrict;
	private AutoCompleteTextView autodistrict;
	private String provincename;

	public GetDistrictAsyn() {

	}

	public GetDistrictAsyn(Context context, AutoCompleteTextView auto,
			String provincename) {
		this.context = context;
		this.autodistrict = auto;
		listdistrict = new ArrayList<String>();
		this.provincename = provincename;
	}

	@Override
	protected Void doInBackground(String... params) {
		String url = params[0];
		GetAndPostJson gap = new GetAndPostJson();
		JSONObject object = new JSONObject();
		try {
			object.put("provincename", provincename);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = gap.PostAndGet2(url, object, "provincename");
		Log.e("bao", data);
		Readandparsejson(data);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		addistrict = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, listdistrict);
		autodistrict.setAdapter(addistrict);
	}

	private void Readandparsejson(String data) {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray arrdistrict = object.getJSONArray("district");
			for (int i = 0; i < arrdistrict.length(); i++) {
				listdistrict.add(arrdistrict.get(i).toString());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
