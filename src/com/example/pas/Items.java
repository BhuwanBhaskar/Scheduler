package com.example.pas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class Items extends FragmentActivity implements
		ItemFragment.OnItemListener {
	public final static String ID_EXTRA = "com.example.pas._ID";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ItemFragment itemF = (ItemFragment) getSupportFragmentManager()
				.findFragmentById(R.id.itemF);

		itemF.setOnItemListener(this);
	}

	public void onItemSelected(long id) {
		if (findViewById(R.id.details) == null) {
			Intent i = new Intent(this, DetailForm.class);

			i.putExtra(ID_EXTRA, String.valueOf(id));
			startActivity(i);
		} else {
			FragmentManager fragMgr = getSupportFragmentManager();
			DetailFragment details = (DetailFragment) fragMgr
					.findFragmentById(R.id.details);

			if (details == null) {
				details = DetailFragment.newInstance(id);

				FragmentTransaction xaction = fragMgr.beginTransaction();

				xaction.add(R.id.details, details)
						.setTransition(
								FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.addToBackStack(null).commit();
			} else {
				details.loadItem(String.valueOf(id));
			}
		}
	}
}
