package com.example.demogooglemap;

import com.example.model.Martyrs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TombDetailActivity extends Activity{
	private TextView tvTombName, tvTombSessionTitle, tvBirthdaycalendar, tvLunarBirthday, tvCountry;
	private TextView tvWards, tvVillage, tvCemetery, tvTypeTomb, tvCity, tvDistrict;
	private TextView tvGroupTomb, tvDateOfEnlistment, tvUnit, tvBattalion;
	private TextView tvRegiment, tvDivision, tvLevel, tvBattlefieldSacrifice, tvAreaSacrifice, tvPostedBy;
	
	private Martyrs martyrs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tombdetail);
		setUI();
		setData();
	}

	private void setUI() {
		tvTombName = (TextView) findViewById(R.id.tvTombName);
		tvTombSessionTitle = (TextView) findViewById(R.id.tvTombSessionTitle);
		tvBirthdaycalendar = (TextView) findViewById(R.id.tvBirthdaycalendar);
		tvLunarBirthday = (TextView) findViewById(R.id.tvLunarBirthday);
		tvCountry = (TextView) findViewById(R.id.tvCountry);
		tvWards = (TextView) findViewById(R.id.tvWards);
		tvVillage = (TextView) findViewById(R.id.tvVillage);
		tvCemetery = (TextView) findViewById(R.id.tvCemetery);
		tvTypeTomb = (TextView) findViewById(R.id.tvTypeTomb);
		tvCity = (TextView) findViewById(R.id.tvCity);
		tvDistrict = (TextView) findViewById(R.id.tvDistrict);
		tvGroupTomb = (TextView) findViewById(R.id.tvGroupTomb);
		tvDateOfEnlistment = (TextView) findViewById(R.id.tvDateOfEnlistment);
		tvUnit = (TextView) findViewById(R.id.tvUnit);
		tvBattalion = (TextView) findViewById(R.id.tvBattalion);
		tvRegiment = (TextView) findViewById(R.id.tvRegiment);
		tvDivision = (TextView) findViewById(R.id.tvDivision);
		tvLevel = (TextView) findViewById(R.id.tvLevel);
		tvBattlefieldSacrifice = (TextView) findViewById(R.id.tvBattlefieldSacrifice);
		tvAreaSacrifice = (TextView) findViewById(R.id.tvAreaSacrifice);
		tvPostedBy = (TextView) findViewById(R.id.tvPostedBy);
		
		martyrs = new Martyrs();
		
	}
	
	private void setData(){
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		   martyrs = (Martyrs) extras.getSerializable("martyrs");
		}
		
		tvTombName.setText(martyrs.getName().toString());
		tvTombSessionTitle.setText(martyrs.getTombSessionTitle());
		tvBirthdaycalendar.setText(martyrs.getCalendarbirth());
		tvLunarBirthday.setText(martyrs.getLunarBirthday());
		tvCountry.setText(martyrs.getCountry());
		tvWards.setText(martyrs.getWards());
		tvVillage.setText(martyrs.getVillage());
		tvCemetery.setText(martyrs.getCemetery());
		tvTypeTomb.setText(martyrs.getTypeTomb());
		tvCity.setText(martyrs.getCity());
		tvDistrict.setText(martyrs.getDistrict());
		tvGroupTomb.setText(martyrs.getGroupTomb());
		tvDateOfEnlistment.setText(martyrs.getDateOfEnlistment());
		tvUnit.setText(martyrs.getUnit());
		tvBattalion.setText(martyrs.getBattalion());
		tvRegiment.setText(martyrs.getRegiment());
		tvDivision.setText(martyrs.getDivision());
		tvLevel.setText(martyrs.getLevel());
		tvBattlefieldSacrifice.setText(martyrs.getBattlefieldSacrifice());
		tvAreaSacrifice.setText(martyrs.getAreaSacrifice());
		tvPostedBy.setText(martyrs.getPostedBy());
		
	}

	

}
