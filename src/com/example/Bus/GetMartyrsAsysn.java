package com.example.Bus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.Adapter.MartyrsAdapter;
import com.example.demogooglemap.R;
import com.example.demogooglemap.TombDetailActivity;
import com.example.model.Martyrs;

public class GetMartyrsAsysn extends AsyncTask<String, Void, Void> {

	private Context context;
	private List<Martyrs> listmartyrs;
	private ArrayAdapter<Martyrs> admartyrs;
	private ListView lv;
	private String districtname;
	private String provincename;
	private String villagename;
	private String name;
	private ProgressDialog mProgress;

	public GetMartyrsAsysn() {

	}

	public GetMartyrsAsysn(Context mcontext, ListView lv, String provincename,
			String districtname, String villagename, String nammemartyrs) {
		this.context = mcontext;
		this.lv = lv;
		listmartyrs = new ArrayList<Martyrs>();
		this.districtname = districtname;
		this.villagename = villagename;
		this.provincename = provincename;
		this.name = nammemartyrs;
		mProgress = new ProgressDialog(context);
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgress.setMessage( context.getResources().getString(R.string.loading) );
		mProgress.setCancelable(false);
		mProgress.show();
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String data = gap.PostAndGet2(url, object, "martyrs");
		Readandparsejson(data);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (mProgress.isShowing())
			mProgress.dismiss();
		admartyrs = new MartyrsAdapter(context, R.layout.item_listview_layout,
				listmartyrs);
		lv.setAdapter(admartyrs);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Martyrs martyrs = listmartyrs.get(position);
				Intent activity = new Intent(context,TombDetailActivity.class);
				activity.putExtra("martyrs", martyrs);
				context.startActivity(activity);
				
			}
		});
	}

	private void Readandparsejson(String data) {
		try {
			JSONObject object = new JSONObject(data);
			JSONArray arrmartyrs = object.getJSONArray("martyrs");
			for (int i = 0; i < arrmartyrs.length(); i++) {
				JSONObject jsonmartyrs = arrmartyrs.getJSONObject(i);
				Martyrs martyrs = new Martyrs();
				martyrs.setName(jsonmartyrs.getString("namemartyrs"));
				martyrs.setTombSessionTitle(jsonmartyrs.getString("position_at_sacrifice"));
				String bd = jsonmartyrs.getString("birth_of_day");
				if (!bd.equalsIgnoreCase("0"))
					bd = bd.substring(0, 4);
				martyrs.setYearofbirth(bd);
				
				martyrs.setCalendarbirth(converDate(jsonmartyrs.getString("birth_of_day")));
				martyrs.setLunarBirthday(converDate(jsonmartyrs.getString("birth_lunar")));
				martyrs.setCountry(jsonmartyrs.getString("national"));
				martyrs.setCity(jsonmartyrs.getString("city"));
				martyrs.setDistrict(jsonmartyrs.getString("district"));
				martyrs.setWards(jsonmartyrs.getString("wards"));
				martyrs.setVillage(jsonmartyrs.getString("village"));
				martyrs.setTypeTomb(jsonmartyrs.getString("tombtype"));
				martyrs.setGroupTomb(jsonmartyrs.getString("tombgroup"));
				martyrs.setDateOfEnlistment(jsonmartyrs.getString("day_of_enlistment"));
				martyrs.setBattalion(jsonmartyrs.getString("battalion"));
				martyrs.setRegiment(jsonmartyrs.getString("regiment"));
				martyrs.setDivision(jsonmartyrs.getString("division"));
				martyrs.setLevel(jsonmartyrs.getString("position"));
				martyrs.setBattlefieldSacrifice(jsonmartyrs.getString("battlefield_sacrifice"));
				martyrs.setAreaSacrifice(jsonmartyrs.getString("area_sacrifice"));
				martyrs.setPostedBy(jsonmartyrs.getString("postby"));
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
	
	private String converDate(String date){
		DateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
		DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
		String inputDateStr = date;
		String outputDateStr = "";
		try {
			Date dateout = inputFormat.parse(inputDateStr);
			outputDateStr = outputFormat.format(dateout);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outputDateStr;
		
	}
}
