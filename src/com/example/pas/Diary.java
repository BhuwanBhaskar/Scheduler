package com.example.pas;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class Diary extends ListActivity {

	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private DiaryDbAdapter mDbHelper;
	private Cursor mDiaryCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diary_home);
		mDbHelper = new DiaryDbAdapter(this);
		mDbHelper.open();
		renderListView();
	}

	private void renderListView() {
		// TODO Auto-generated method stub

		mDiaryCursor = mDbHelper.getAllNotes();
		startManagingCursor(mDiaryCursor);

		String[] from = new String[] { DiaryDbAdapter.KEY_TITLE,
				DiaryDbAdapter.KEY_CREATED };
		int[] to = new int[] { R.id.text, R.id.created };
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this, R.layout.row,
				mDiaryCursor, from, to);
		setListAdapter(notes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
		// getMenuInflater().inflate(R.menu.diary, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case INSERT_ID:
			create_diary();
			return true;
		case DELETE_ID:
			mDbHelper.deleteDiary(getListView().getSelectedItemId());
			renderListView();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void create_diary() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this, DiaryEdit.class);
		startActivityForResult(intent, ACTIVITY_CREATE);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Cursor c = mDiaryCursor;
		c.moveToPosition(position);
		Intent intent = new Intent(this, DiaryEdit.class);

		intent.putExtra(DiaryDbAdapter.KEY_ROWID, id);
		intent.putExtra(DiaryDbAdapter.KEY_TITLE,
				c.getString(c.getColumnIndexOrThrow(DiaryDbAdapter.KEY_TITLE)));

		intent.putExtra(DiaryDbAdapter.KEY_BODY,
				c.getString(c.getColumnIndexOrThrow(DiaryDbAdapter.KEY_BODY)));

		startActivityForResult(intent, ACTIVITY_EDIT);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		renderListView();
	}

}