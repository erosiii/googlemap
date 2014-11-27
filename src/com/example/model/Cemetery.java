package com.example.model;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class Cemetery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String country;
	private LatLng googlemap;
	private LatLng gps;
	private String typecemetery;
	private String village;
	private String ward;
	private String district;
	private String province;
	private String north_near;
	private String east_near;
	private String south_near;
	private String west_near;
	private String area;

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public LatLng getGooglemap() {
		return googlemap;
	}

	public void setGooglemap(LatLng googlemap) {
		this.googlemap = googlemap;
	}

	public LatLng getGps() {
		return gps;
	}

	public void setGps(LatLng gps) {
		this.gps = gps;
	}

	public String getNorth_near() {
		return north_near;
	}

	public void setNorth_near(String north_near) {
		this.north_near = north_near;
	}

	public String getEast_near() {
		return east_near;
	}

	public void setEast_near(String east_near) {
		this.east_near = east_near;
	}

	public String getSouth_near() {
		return south_near;
	}

	public void setSouth_near(String south_near) {
		this.south_near = south_near;
	}

	public String getWest_near() {
		return west_near;
	}

	public void setWest_near(String west_near) {
		this.west_near = west_near;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTypecemetery() {
		return typecemetery;
	}

	public void setTypecemetery(String typecemetery) {
		this.typecemetery = typecemetery;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
