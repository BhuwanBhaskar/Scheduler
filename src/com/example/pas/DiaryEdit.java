package com.example.pas;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DiaryEdit extends Activity{
	
	private EditText mtitle;
	private EditText mbody;
	private Long mrowId ;
	private DiaryDbAdapter mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mDbHelper = new DiaryDbAdapter(this);
		mDbHelper.open();
		setContentView(R.layout.diary_edit);
		
		mtitle = (EditText)findViewById(R.id.title);
		mbody = (EditText)findViewById(R.id.body);
		Button confirm = (Button)findViewById(R.id.confirm);
		
		mrowId = null;
		
		Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			String title = extras.getString(DiaryDbAdapter.KEY_TITLE);
			String body  = extras.getString(DiaryDbAdapter.KEY_BODY);
			mrowId        = extras.getLong(DiaryDbAdapter.KEY_ROWID);
			
			if(title != null)
				mtitle.setText(title);
			if(body != null)
				mbody.setText(body);
		}			
		confirm.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String title = mtitle.getText().toString();
				String body = mbody.getText().toString();
				
				if(mrowId != null)
					mDbHelper.updateDiary(mrowId, title, body);
				else
					mDbHelper.createDiary(title, body);
				
				Intent mintent = new Intent();
				setResult(RESULT_OK , mintent);
				finish();				
			}
		});		
	}
}
