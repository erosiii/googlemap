package com.example.Adapter;

import java.util.List;
import com.example.demogooglemap.R;
import com.example.model.Cemetery;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CemeteryAdapter extends ArrayAdapter<Cemetery> {

	private Context context;
	private List<Cemetery> listcemetery;

	public CemeteryAdapter(Context context, int resource, List<Cemetery> objects) {
		super(context, resource, objects);
		this.context = context;
		this.listcemetery = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inlater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item = inlater.inflate(R.layout.item_listview_cemetery_layout,
				null);
		Cemetery object = listcemetery.get(position);
		TextView tvname = (TextView) item.findViewById(R.id.tvnamecemetery);
		TextView tvtype = (TextView) item.findViewById(R.id.tvtypecemetery);
		TextView tvpos = (TextView) item.findViewById(R.id.tvposition);

		tvname.setText(object.getName());
		tvtype.setText(object.getTypecemetery());
		tvpos.setText(object.getVillage() + "-" + object.getDistrict() + "-"
				+ object.getProvince());
		return item;
	}
}
