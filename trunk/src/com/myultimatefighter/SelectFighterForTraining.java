package com.myultimatefighter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectFighterForTraining extends Activity {
	
	private final String TAG = "SelectFighterForTraining";
	private FighterData data;
	private RelativeLayout mainLayout;
	private LinearLayout leftFighterListLayout, rightFighterListLayout;
	private int counter = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fighter_list);
		
		getWindow().setBackgroundDrawableResource(R.drawable.scroll);
		
		data = new FighterData(this);
		data.open();
		
		initialLayoutSetup();
	}
	
	// creates a new fighter layout ; includes fighter button and text "name"	
	private LinearLayout newFighterLayout(int fighterID) {
		Log.d(TAG, "newFighterLayout started");
		
		Cursor c = data.getFighters();
		c.moveToPosition(fighterID-1);
		Log.d(TAG, Integer.toString(c.getInt(c.getColumnIndex("_id"))));
		LinearLayout newFighterLayout = new LinearLayout(mainLayout.getContext());
		ImageButton newFighterButton = new ImageButton(newFighterLayout.getContext());
		TextView newFighterText = new TextView(newFighterLayout.getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1);
		lp.setMargins(0, 10, 0, 0);
		newFighterButton.setImageResource(c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_AVATAR)));
		newFighterButton.setAdjustViewBounds(true);
		newFighterButton.setMaxHeight(256); newFighterButton.setMaxWidth(256);	
		newFighterButton.setBackgroundColor(Color.TRANSPARENT); //testing
		newFighterText.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_NAME)));
		newFighterText.setTextColor(Color.BLACK);
		newFighterLayout.addView(newFighterButton);
		newFighterLayout.addView(newFighterText);
		newFighterLayout.setOrientation(LinearLayout.VERTICAL);
		newFighterLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		newFighterLayout.setLayoutParams(lp); //parameters setup for layout
		
		newFighterButton.setId(fighterID);
		Log.d(TAG, "fighterButtonID: "+newFighterButton.getId());				
		
		newFighterButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Cursor c = data.getFighters();
				c.moveToPosition(v.getId()-1);
				Intent i = new Intent(SelectFighterForTraining.this, Trainer.class);
				i.putExtra("buttonID", v.getId());
				Log.d(TAG, "buttonID sent through click: "+v.getId());
				startActivityForResult(i, 0);
				//startActivity(i);
			}
		});
		
		Log.d(TAG, "end of newFighterLayout");
		return newFighterLayout;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		if (resultCode == RESULT_OK)
		{
			if (requestCode == 0)
			{
				Bundle extras = i.getExtras();
				boolean didWin = extras.getBoolean("won");

				if (didWin)
					Toast.makeText(this, "Victory!", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(this, "Defeat!", Toast.LENGTH_SHORT).show();
			}
		}		
		
		super.onActivityResult(requestCode, resultCode, i);
	}
	
	private void addAvatarToScreen(LinearLayout i) {
		if (counter % 2 == 0)
			leftFighterListLayout.addView(i);
		else
			rightFighterListLayout.addView(i);
		counter++;
	}

	// get references to the xml layouts
	private void initialLayoutSetup() {
		mainLayout = (RelativeLayout) findViewById(R.id.fighter_listRelativeLayout);
		leftFighterListLayout = (LinearLayout) findViewById(R.id.leftLinearLayout);
		rightFighterListLayout = (LinearLayout) findViewById(R.id.rightLinearLayout);
		//fighterVector.setSize(0);
		Log.d(TAG, "in initialLayout");

		Cursor c = data.getFighters();
		Log.d(TAG, Integer.toString(c.getCount()));
		Integer intCompare = new Integer(c.getCount());
		if (intCompare.equals(0))
		{
			Toast.makeText(this, "There Are No Fighters To Train", Toast.LENGTH_SHORT).show();
			Log.d(TAG, "no initial Fighters. Returned");
			return;
		}
		else {
			Log.d(TAG, "fighters found");
			c.moveToFirst();
			
			while (!c.isLast())
			{
				Log.d(TAG, "on fighter: "+c.getInt(c.getColumnIndex("_id")));
				addAvatarToScreen(newFighterLayout(c.getInt(c.getColumnIndex("_id"))));
				c.moveToNext();
			}
			Log.d(TAG, "on fighter: "+c.getInt(c.getColumnIndex("_id")));
			addAvatarToScreen(newFighterLayout(c.getInt(c.getColumnIndex("_id"))));
		}
	}
}
