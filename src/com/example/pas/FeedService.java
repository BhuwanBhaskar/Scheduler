package com.example.pas;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;

public class FeedService extends IntentService {
	public static final String EXTRA_URL = "com.example.pas.EXTRA_URL";
	public static final String EXTRA_MESSENGER = "com.example.pas.EXTRA_MESSENGER";

	public FeedService() {
		super("FeedService");
	}

	@Override
	public void onHandleIntent(Intent i) {
		RSSReader reader = new RSSReader();
		Messenger messenger = (Messenger) i.getExtras().get(EXTRA_MESSENGER);
		Message msg = Message.obtain();

		try {
			RSSFeed result = reader.load(i.getStringExtra(EXTRA_URL));

			msg.arg1 = Activity.RESULT_OK;
			msg.obj = result;
		} catch (Exception e) {
			Log.e("Items", "Exception parsing feed", e);
			msg.arg1 = Activity.RESULT_CANCELED;
			msg.obj = e;
		}

		try {
			messenger.send(msg);
		} catch (Exception e) {
			Log.w("Items", "Exception sending results to activity", e);
		}
	}
}