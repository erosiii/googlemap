package com.example.demogooglemap;

import com.example.Bus.GetCemeteryAsyn;
import com.example.Bus.GetDistrictAsyn;
import com.example.Bus.GetProvinceAsyn;
import com.example.model.Cemetery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FindCemeteryActivity extends Activity {

	private EditText edtnamecemetery;
	private AutoCompleteTextView autoprovince;
	private AutoCompleteTextView autodistrict;
	private Button btnfindcemetery;
	private ListView lvcemetery;
	public static final String URL = "http://117.6.131.222:8888/nghiatrangtt/Tamlinh/";
	public static Cemetery ce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_cemetery);
		SetUI();
		SetData();
	}

	private void SetUI() {
		edtnamecemetery = (EditText) findViewById(R.id.edtnamecemetery);
		autoprovince = (AutoCompleteTextView) findViewById(R.id.autoprovine);
		autodistrict = (AutoCompleteTextView) findViewById(R.id.autodistrict);
		btnfindcemetery = (Button) findViewById(R.id.btn_findcemetery);
		lvcemetery = (ListView) findViewById(R.id.lvcemetery);
		autoprovince.setThreshold(0);
		autodistrict.setThreshold(0);

		// event
		autoprovince.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = URL + "wsgetdistrict.php";
				new GetDistrictAsyn(FindCemeteryActivity.this, autodistrict,
						autoprovince.getText().toString()).execute(url);
			}
		});

		autoprovince.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				autoprovince.showDropDown();
			}
		});

		autodistrict.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				autodistrict.showDropDown();
			}
		});

		btnfindcemetery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dosearch();
			}
		});

		lvcemetery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cemetery cemetery = (Cemetery) lvcemetery
						.getItemAtPosition(position);
				ce=cemetery;
				Intent intent = new Intent(FindCemeteryActivity.this,
						ShowDetailCemeteryActivity.class);
				
				startActivity(intent);
			}
		});
	}

	private void SetData() {
		String url = URL + "wsgetprovince.php";
		new GetProvinceAsyn(FindCemeteryActivity.this, autoprovince)
				.execute(url);
	}

	private void Dosearch() {
		String name = edtnamecemetery.getText().toString();
		if (!name.equals("")) {
			String url = URL + "wsgetcemetery.php";
			new GetCemeteryAsyn(FindCemeteryActivity.this, lvcemetery, name,
					autoprovince.getText().toString(), autodistrict.getText()
							.toString()).execute(url);

		} else {
			Toast.makeText(getApplicationContext(), "Chưa nhập đủ thông tin",
					Toast.LENGTH_SHORT).show();
		}

	}
}
