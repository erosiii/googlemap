package com.example.Bus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.util.Log;

public class GetAndPostJson {
	private static final int TIMEOUT_MILLISEC = 10000;

	public String GetOnly(String url) {
		try {

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(
							(InputStream) new URL(url).getContent()));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			String datajson = stringBuilder.toString();
			return datajson;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String PostAndGet(String url, String namepost, JSONObject Valuepost) {
		HttpClient httpclient = new DefaultHttpClient();
		String stringresponse = "";

		try {
			HttpPost httppost = new HttpPost(url);

			List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair(namepost, Valuepost.toString()));
			// httppost.setHeader("Content-type", "application/json");
			httppost.setEntity(new UrlEncodedFormEntity(nvp));
			// HttpResponse response = httpclient.execute(httppost);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			stringresponse = httpclient.execute(httppost, responseHandler);
			Log.e("dsfd", stringresponse);

		} catch (Exception e) {
			// e.printStackTrace();
			Log.e("asdas", e.toString());
		}
		return stringresponse;
	}

	public String PostAndGet2(String url, JSONObject json, String namepost) {
		String datajson = null;
		try {
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
			HttpClient client = new DefaultHttpClient(httpParams);
			HttpPost request = new HttpPost(url);
			// cach khac de thay vao entity __________________________________
			StringEntity se = new StringEntity(json.toString(), "UTF-8");
			// InputStreamEntity ise = new InputStreamEntity(
			// new ByteArrayInputStream(json.toString().getBytes("UTF-8")),
			// -1);
			// ---------------------------------------------------------------------
			request.setEntity(se);
			// Log.d("fuck", "fuck");
			request.setHeader(namepost, json.toString());
			// ResponseHandler<String> responseHandler = new
			// BasicResponseHandler();
			// HttpResponse response = client.execute(request);
			// String res = client.execute(request,responseHandler);
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(instream));
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			datajson = stringBuilder.toString();
			for (int i = 0; i < datajson.length(); i++) {
				if ("{".equalsIgnoreCase(String.valueOf(datajson.charAt(i)))) {
					datajson = datajson.substring(i, datajson.length());
					break;
				}
			}
			Log.e("dta", datajson);
			return datajson;
		} catch (Exception t) {
			Log.e("loi ay", t.toString());
		}
		return datajson;
	}
}
