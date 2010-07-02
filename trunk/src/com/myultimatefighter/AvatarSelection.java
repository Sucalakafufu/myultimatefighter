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

public class AvatarSelection extends Activity {
	
	private final static String TAG = "AvatarSelection";
	private RelativeLayout mainLayout;
	private LinearLayout leftFighterListLayout, rightFighterListLayout;
	private int counter = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.fighter_list);
		getWindow().setBackgroundDrawableResource(R.drawable.scroll);
		
		Toast.makeText(this, "Select Your Avatar", Toast.LENGTH_SHORT).show();
		mainLayout = (RelativeLayout) findViewById(R.id.fighter_listRelativeLayout);
		leftFighterListLayout = (LinearLayout) findViewById(R.id.leftLinearLayout);
		rightFighterListLayout = (LinearLayout) findViewById(R.id.rightLinearLayout);
		
		for (int i = 0;i<Trainer.opponentAvatars.size();i++)
		{
			addAvatarToScreen(newFighterLayout(Trainer.opponentAvatars.get(i)));
		}
		
		super.onCreate(savedInstanceState);
	}
	
	// adds a new fighterAvatar to screen
	private void addAvatarToScreen(LinearLayout i) {
		if (counter % 2 == 0)
			leftFighterListLayout.addView(i);
		else
			rightFighterListLayout.addView(i);
		counter++;
	}
	
	// creates a new fighter layout ; includes fighter button and text "name"	
	private LinearLayout newFighterLayout(int image) {
		Log.d(TAG, "newFighterLayout started");
		
		LinearLayout newFighterLayout = new LinearLayout(mainLayout.getContext());
		ImageButton newFighterButton = new ImageButton(newFighterLayout.getContext());
		TextView newFighterText = new TextView(newFighterLayout.getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1);
		lp.setMargins(0, 10, 0, 0);
		newFighterButton.setImageResource(image);
		newFighterButton.setAdjustViewBounds(true);
		newFighterButton.setMaxHeight(256); newFighterButton.setMaxWidth(256);	
		newFighterButton.setBackgroundColor(Color.TRANSPARENT); 
		newFighterButton.setId(image);
		newFighterText.setText("");
		newFighterText.setTextColor(Color.BLACK);
		newFighterLayout.addView(newFighterButton);
		newFighterLayout.addView(newFighterText);
		newFighterLayout.setOrientation(LinearLayout.VERTICAL);
		newFighterLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		newFighterLayout.setLayoutParams(lp); //parameters setup for layout	
		
		newFighterButton.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {			
				Bundle selection = new Bundle();
				Intent i = new Intent();
				
				selection.putInt("avatar", v.getId());
				i.putExtras(selection);
				
				setResult(RESULT_OK, i);
				finish();
			}
		});
				
		Log.d(TAG, "end of newFighterLayout");
		return newFighterLayout;
	}
}
