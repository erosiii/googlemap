package com.example.demogooglemap;

import com.example.Bus.GetAllCemeteryAsyn;
import com.example.Bus.GetDistrictAsyn;
import com.example.Bus.GetMartyrsAsysn;
import com.example.Bus.GetProvinceAsyn;
import com.example.Bus.GetVillageAsysn;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FindActivity extends Activity {
	private EditText edtnativeland;
	private EditText edtnamemartyrs;
	private Button btnfind;
	private ListView lvmartyrs;
	private AutoCompleteTextView autoprovince;
	private AutoCompleteTextView autodistrict;
	private AutoCompleteTextView autovillage, autonghiatrang;
	private static final String URL = "http://117.6.131.222:8888/Tamlinh/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		SetUI();
		SetData();
	}

	private void SetUI() {
		edtnamemartyrs = (EditText) findViewById(R.id.edtten);
		lvmartyrs = (ListView) findViewById(R.id.lvinfo);
		btnfind = (Button) findViewById(R.id.btnfind);
		autoprovince = (AutoCompleteTextView) findViewById(R.id.autotinhthanh);
		autoprovince.setThreshold(1);
		autoprovince.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = URL + "wsgetdistrict.php";
				new GetDistrictAsyn(FindActivity.this, autodistrict,
						autoprovince.getText().toString()).execute(url);
			}
		});
		
		autonghiatrang = (AutoCompleteTextView) findViewById(R.id.autonghiatrang);
		autonghiatrang.setThreshold(1);
		autonghiatrang.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = URL + "wsgetallcemetery.php";
				new GetAllCemeteryAsyn(FindActivity.this, autonghiatrang,
						autonghiatrang.getText().toString()).execute(url);
			}
		});

		autodistrict = (AutoCompleteTextView) findViewById(R.id.autoquanhuyen);
		autodistrict.setThreshold(0);
		autodistrict.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = URL + "wsgetvillage.php";
				new GetVillageAsysn(FindActivity.this, autovillage,
						autodistrict.getText().toString()).execute(url);
			}
		});

		autovillage = (AutoCompleteTextView) findViewById(R.id.autothonxa);
		autovillage.setThreshold(0);

		btnfind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doSearch();
			}
		});
		
	}

	private void SetData() {
		String url = URL + "wsgetprovince.php";
		new GetProvinceAsyn(FindActivity.this, autoprovince).execute(url);
	}

	private void doSearch() {
//		if (!edtnamemartyrs.getText().toString().equals("")
//				&& !edtnativeland.getText().toString().equals("")) {
			String url = URL + "wsgetinfomartyrs.php";
			try {
				new GetMartyrsAsysn(FindActivity.this, lvmartyrs, autoprovince
						.getText().toString(), autodistrict.getText().toString(),
						autovillage.getText().toString(), edtnamemartyrs.getText()
								.toString()).execute(url);
			} catch (Exception e) {
				e.printStackTrace();
			}

//		} else {
//			Toast.makeText(getApplicationContext(), "Chưa nhập đủ thông tin",
//					Toast.LENGTH_SHORT).show();
//		}
	}
	

}
