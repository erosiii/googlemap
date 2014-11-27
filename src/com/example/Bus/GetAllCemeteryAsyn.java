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

public class GetAllCemeteryAsyn extends AsyncTask<String, Void, Void> {

	private Context context;
	private List<String> listcemetery;
	private ArrayAdapter<String> addcemetery;
	private AutoCompleteTextView autoCemetery;
	private String cemetery;

	public GetAllCemeteryAsyn() {

	}

	public GetAllCemeteryAsyn(Context context, AutoCompleteTextView auto,
			String cemetery) {
		this.context = context;
		this.autoCemetery = auto;
		listcemetery = new ArrayList<String>();
		this.cemetery = cemetery;
	}

	@Override
	protected Void doInBackground(String... params) {
		String url = params[0];
		GetAndPostJson gap = new GetAndPostJson();
		JSONObject object = new JSONObject();
		try {
			object.put("cemetery", cemetery);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = gap.PostAndGet2(url, object, "cemetery");
		Readandparsejson(data);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		addcemetery = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, listcemetery);
		autoCemetery.setAdapter(addcemetery);
	}

	private void Readandparsejson(String data) {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray arrdistrict = object.getJSONArray("name");
			for (int i = 0; i < arrdistrict.length(); i++) {
				listcemetery.add(arrdistrict.get(i).toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
