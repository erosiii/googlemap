package com.example.Bus;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class GetProvinceAsyn extends AsyncTask<String, Void, Void> {

	private Context context;
	private List<String> listprovinces;
	private ArrayAdapter<String> adprovince;
	private AutoCompleteTextView autoprovince;

	public GetProvinceAsyn() {

	}

	public GetProvinceAsyn(Context context, AutoCompleteTextView auto) {
		this.context = context;
		this.autoprovince = auto;
		listprovinces = new ArrayList<String>();
	}

	@Override
	protected Void doInBackground(String... params) {
		String url = params[0];
		GetAndPostJson gap = new GetAndPostJson();
		String data = gap.GetOnly(url);
		Readandparsejson(data);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		adprovince = new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, listprovinces);
		autoprovince.setAdapter(adprovince);
	}

	private void Readandparsejson(String data) {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray arrprovince = object.getJSONArray("provinces");
			for (int i = 0; i < arrprovince.length(); i++) {
				listprovinces.add(arrprovince.get(i).toString());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
