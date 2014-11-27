package com.example.demogooglemap;

import com.example.model.Cemetery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

public class ShowDetailCemeteryActivity extends ActionBarActivity {

	public GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_cemetery_layout);
		// Bundle bd = new Bundle();
		// bd = getIntent().getExtras();
		// Cemetery cemetery = (Cemetery) bd.getSerializable("cemetery");

		Cemetery cemetery = FindCemeteryActivity.ce;
		((TextView) findViewById(R.id.TVTENNGHIATRANG)).setText(cemetery
				.getName());
		((TextView) findViewById(R.id.TVQUOCGIA)).setText(cemetery.getCountry());
		((TextView) findViewById(R.id.TVTINHTHANH)).setText(cemetery
				.getProvince());
		((TextView) findViewById(R.id.TVQUANHUYEN)).setText(cemetery
				.getDistrict());
		((TextView) findViewById(R.id.TVXAPHUONG)).setText(cemetery.getWard());
		((TextView) findViewById(R.id.TVTHONXOM))
				.setText(cemetery.getVillage());
		((TextView) findViewById(R.id.TVLOAINGHIATRANG)).setText(cemetery
				.getTypecemetery());
		((TextView) findViewById(R.id.TVDIENTICH)).setText(cemetery.getArea());
		((TextView) findViewById(R.id.TVPHIABAC)).setText(cemetery
				.getNorth_near());
		((TextView) findViewById(R.id.TVPHIADONG)).setText(cemetery
				.getEast_near());
		((TextView) findViewById(R.id.TVPHIANAM)).setText(cemetery
				.getSouth_near());
		((TextView) findViewById(R.id.TVPHIATAY)).setText(cemetery
				.getWest_near());
		if (cemetery.getGps() != null)
			((TextView) findViewById(R.id.TVGPS)).setText(String
					.valueOf(cemetery.getGps().latitude + "-"
							+ cemetery.getGps().longitude));

		// map
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragmentMapscemetery);

		map = fm.getMap();// bi loi khuc nay

		// Enable MyLocation Button in the Map
		map.setMyLocationEnabled(true);

		LatLng latlong = null;
		if (cemetery.getGooglemap() != null)
			latlong = cemetery.getGooglemap();
		else {
			latlong = new LatLng(0, 0);
		}

		// Log.e("vi do", String.valueOf(latlong.latitude));
		// Log.e("kinh do", String.valueOf(latlong.longitude));
		// Totalpoint.add(pointorigin);
		Bitmap bm = BitmapFactory
				.decodeResource(getResources(), R.drawable.rip);
		map.addMarker(new MarkerOptions().position(latlong)
				.title(cemetery.getName())
				.icon(BitmapDescriptorFactory.fromBitmap(bm)));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15));
	}
}
