package com.example.Bus;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.MartyrsAdapter;
import com.example.demogooglemap.R;
import com.example.model.Martyrs;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GetMartyrsAsysn extends AsyncTask<String, Void, Void> {

	private Context context;
	private List<Martyrs> listmartyrs;
	private ArrayAdapter<Martyrs> admartyrs;
	private ListView lv;
	private String districtname;
	private String provincename;
	private String villagename;
	private String name;
	private String nativeland;

	public GetMartyrsAsysn() {

	}

	public GetMartyrsAsysn(Context context, ListView lv, String provincename,
			String districtname, String villagename, String nammemartyrs,
			String nativeland) {
		this.context = context;
		this.lv = lv;
		listmartyrs = new ArrayList<Martyrs>();
		this.districtname = districtname;
		this.villagename = villagename;
		this.provincename = provincename;
		this.name = nammemartyrs;
		this.nativeland = nativeland;
	}

	@Override
	protected Void doInBackground(String... params) {
		String url = params[0];
		GetAndPostJson gap = new GetAndPostJson();
		JSONObject object = new JSONObject();
		try {
			object.put("province", provincename);
			object.put("district", districtname);
			object.put("village", villagename);
			object.put("name", name);
			object.put("nativeland", nativeland);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = gap.PostAndGet2(url, object, "martyrs");
		Log.e("bao", data);
		Readandparsejson(data);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		admartyrs = new MartyrsAdapter(context, R.layout.item_listview_martyrs_layout,
				listmartyrs);
		lv.setAdapter(admartyrs);
	}

	private void Readandparsejson(String data) {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray arrmartyrs = object.getJSONArray("martyrs");
			for (int i = 0; i < arrmartyrs.length(); i++) {
				JSONObject jsonmartyrs = arrmartyrs.getJSONObject(i);
				Martyrs martyrs = new Martyrs();
				martyrs.setName(jsonmartyrs.getString("namemartyrs"));
				String bd = jsonmartyrs.getString("birth_of_day");
				if (!bd.equalsIgnoreCase("0"))
					bd = bd.substring(0, 3);
				martyrs.setYearofbirth(bd);
				martyrs.setNativeland(jsonmartyrs.getString("place_of_birth"));
				martyrs.setUnit(jsonmartyrs.getString("army_units"));
				martyrs.setCemetery(jsonmartyrs.getString("namecemetery"));
				listmartyrs.add(martyrs);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
