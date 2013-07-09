package com.example.pas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ItemFragment extends ListFragment {
	public final static String ID_EXTRA = "ccom.example.pas._ID";
	Cursor model = null;
	ItemAdapter adapter = null;
	ItemHelper helper = null;
	SharedPreferences prefs = null;
	OnItemListener listener = null;

	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);

		setHasOptionsMenu(true);
	}

	@Override
	public void onResume() {
		super.onResume();

		helper = new ItemHelper(getActivity());
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		initList();
		prefs.registerOnSharedPreferenceChangeListener(prefListener);
	}

	@Override
	public void onPause() {
		helper.close();

		super.onPause();
	}

	@Override
	public void onListItemClick(ListView list, View view, int position, long id) {
		if (listener != null) {
			listener.onItemSelected(id);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.option, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add) {
			startActivity(new Intent(getActivity(), DetailForm.class));

			return (true);
		} else if (item.getItemId() == R.id.prefs) {
			startActivity(new Intent(getActivity(), EditPreferences.class));

			return (true);
		}

		return (super.onOptionsItemSelected(item));
	}

	public void setOnItemListener(OnItemListener listener) {
		this.listener = listener;
	}

	private void initList() {
		if (model != null) {
			model.close();
		}

		model = helper.getAll(prefs.getString("sort_order", "name"));
		adapter = new ItemAdapter(model);
		setListAdapter(adapter);
	}

	private SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
		public void onSharedPreferenceChanged(SharedPreferences sharedPrefs,
				String key) {
			if (key.equals("sort_order")) {
				initList();
			}
		}
	};

	public interface OnItemListener {
		void onItemSelected(long id);
	}

	class ItemAdapter extends CursorAdapter {
		ItemAdapter(Cursor c) {
			super(getActivity(), c);
		}

		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			ItemHolder holder = (ItemHolder) row.getTag();

			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View row = inflater.inflate(R.layout.rows, parent, false);
			ItemHolder holder = new ItemHolder(row);

			row.setTag(holder);

			return (row);
		}
	}

	static class ItemHolder {
		private TextView name = null;
		private TextView address = null;
		private ImageView icon = null;

		ItemHolder(View row) {
			name = (TextView) row.findViewById(R.id.title);
			address = (TextView) row.findViewById(R.id.address);
			icon = (ImageView) row.findViewById(R.id.icon);
		}

		void populateFrom(Cursor c, ItemHelper helper) {
			name.setText(helper.getName(c));
			address.setText(helper.getAddress(c));

			if (helper.getType(c).equals("Work")) {
				icon.setImageResource(R.drawable.work);
			} else if (helper.getType(c).equals("Family")) {
				icon.setImageResource(R.drawable.family);
			} else if (helper.getType(c).equals("Friend")) {
				icon.setImageResource(R.drawable.friend);
			} else if (helper.getType(c).equals("Emergency")) {
				icon.setImageResource(R.drawable.emergency);
			} else {
				icon.setImageResource(R.drawable.others);
			}
		}
	}
}
