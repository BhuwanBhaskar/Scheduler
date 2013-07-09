package com.example.pas;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class ListViewsFactory implements RemoteViewsService.RemoteViewsFactory {
	private Context ctxt = null;
	private ItemHelper helper = null;
	private Cursor item = null;

	public ListViewsFactory(Context ctxt, Intent intent) {
		this.ctxt = ctxt;
	}

	@Override
	public void onCreate() {
		helper = new ItemHelper(ctxt);
		item = helper.getReadableDatabase().rawQuery("SELECT _ID, name FROM items", null);
	}

	@Override
	public void onDestroy() {
		item.close();
		helper.close();
	}

	@Override
	public int getCount() {
		return (item.getCount());
	}

	@Override
	public RemoteViews getViewAt(int position) {
		RemoteViews row = new RemoteViews(ctxt.getPackageName(), R.layout.widget_row);

		item.moveToPosition(position);
		row.setTextViewText(android.R.id.text1, item.getString(1));

		Intent i = new Intent();
		Bundle extras = new Bundle();

		extras.putString(Items.ID_EXTRA, String.valueOf(item.getInt(0)));
		i.putExtras(extras);
		row.setOnClickFillInIntent(android.R.id.text1, i);

		return (row);
	}

	@Override
	public RemoteViews getLoadingView() {
		return (null);
	}

	@Override
	public int getViewTypeCount() {
		return (1);
	}

	@Override
	public long getItemId(int position) {
		item.moveToPosition(position);

		return (item.getInt(0));
	}

	@Override
	public boolean hasStableIds() {
		return (true);
	}

	@Override
	public void onDataSetChanged() {
		// no-op
	}
}
