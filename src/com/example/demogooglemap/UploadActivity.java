package com.example.demogooglemap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Bus.GetAndPostJson;
import com.example.Bus.MultipartEntity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadActivity extends Activity {

	private Button btnupload;
	private ImageView imgcapture;
	private String imagefilename, mCurrentPhotoPath;
	private Bitmap imgbitmap;
	Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		SetUI();
		SetData();
	}

	private void SetUI() {
		btnupload = (Button) findViewById(R.id.btn_upload);
		imgcapture = (ImageView) findViewById(R.id.imgcapture);
		btnupload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DoUpload();
			}
		});

		imgcapture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, 2014);
			}
		});
	}

	private void SetData() {

	}

	private void DoUpload() {
		location = MainActivity.map.getMyLocation();
		// upload location to database
		GetAndPostJson gap=new GetAndPostJson();
		JSONObject object=new JSONObject();
		try {
			object.put("loaction", "");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// upload image to server
		if (!imgbitmap.equals(null))
			new MyUploadTask().execute(imgbitmap);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == 2014) {
				// data.getExtras()
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				imgcapture.setImageBitmap(thumbnail);
				// 3
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				// 4
				File testDirectory1 = new File(
						Environment.getExternalStorageDirectory() + "/DO");
				if (!testDirectory1.exists()) {
					testDirectory1.mkdir();
				}
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date());
				imagefilename = "JPEG_" + timeStamp + ".jpg";
				File file = new File(testDirectory1 + "/" + imagefilename);
				mCurrentPhotoPath = file.getAbsolutePath();
				imgbitmap = thumbnail;
				try {
					file.createNewFile();
					FileOutputStream fo = new FileOutputStream(file);
					// 5
					fo.write(bytes.toByteArray());
					fo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Toast.makeText(UploadActivity.this, "Chưa có ảnh",
						Toast.LENGTH_LONG);
			}
			super.onActivityResult(requestCode, resultCode, data);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// upload image to server
	private class MyUploadTask extends AsyncTask<Bitmap, Void, Void> {

		@Override
		protected Void doInBackground(Bitmap... bitmaps) {
			if (bitmaps[0] == null)
				return null;
			setProgress(0);

			Bitmap bitmap = bitmaps[0];
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert
																		// Bitmap
																		// to
																		// ByteArrayOutputStream
			InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert
																				// ByteArrayOutputStream
																				// to
																				// ByteArrayInputStream

			DefaultHttpClient httpclient = new DefaultHttpClient();
			try {
				HttpPost httppost = new HttpPost(
						"http://117.6.131.222:6789/pos/wspos/DOTRACKING/savetofile.php"); // server

				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("myFile", imagefilename, in);
				httppost.setEntity(reqEntity);

				Log.i("TAG", "request " + httppost.getRequestLine());
				HttpResponse response = null;
				try {
					response = httpclient.execute(httppost);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if (response != null)
						Log.i("TAG", "response "
								+ response.getStatusLine().toString());
				} finally {

				}
			} finally {

			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Toast.makeText(UploadActivity.this, "upload thanh cong",
					Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}

	}

}
