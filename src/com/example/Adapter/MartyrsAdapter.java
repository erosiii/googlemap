package com.example.Adapter;

import java.util.List;

import com.example.demogooglemap.R;
import com.example.model.Martyrs;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MartyrsAdapter extends ArrayAdapter<Martyrs> {

	private Context context;
	private List<Martyrs> listmartyrs;

	public MartyrsAdapter(Context context, int resource, List<Martyrs> objects) {
		super(context, resource, objects);
		this.context = context;
		this.listmartyrs = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inlater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item = inlater.inflate(R.layout.item_listview_martyrs_layout, null);
		Martyrs object = listmartyrs.get(position);
		TextView tvname = (TextView) item.findViewById(R.id.tvten);
		TextView tvyearofbirth = (TextView) item.findViewById(R.id.tvnamsinh);
		TextView tvunit = (TextView) item.findViewById(R.id.tvdonvi);
		TextView tvnativeland = (TextView) item.findViewById(R.id.tvquequan);
		TextView tvcemetery = (TextView) item.findViewById(R.id.tvnghiatrang);

		tvname.setText(object.getName());
		tvyearofbirth.setText(object.getYearofbirth());
		tvunit.setText(object.getUnit());
		tvnativeland.setText(object.getNativeland());
		tvcemetery.setText(object.getCemetery());
		return item;
	}

}
