package com.example.pas;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(1000);
				}

				catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent intent = new Intent("com.example.pas.MENUS");
					startActivity(intent);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
