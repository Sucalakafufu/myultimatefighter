package com.myultimatefighter;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FighterEdit extends Activity {
	
	private final static String TAG = "FighterEdit";
	
	private FighterData data;
	private int fighterID, avatarID;
	//avatarID;
	private EditText fighterName;
	private ImageButton fighterAvatar;
	private TextView lvlTextView, hpTextView, paTextView, pdTextView, saTextView, sdTextView, remainingSkillPoints, exp;
	private Button hpButton, paButton, pdButton, saButton, sdButton, saveButton;
	private int remainingSP, expToLevel;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "onCreate of edit page");
        
        setContentView(R.layout.fighter_edit);
        getWindow().setBackgroundDrawableResource(R.drawable.scroll);
        
        Bundle b = getIntent().getExtras();
        fighterID = b.getInt("buttonID");
        
        data = new FighterData(this);
        data.open();
        
        Cursor c = data.getFighters();
        c.moveToPosition(fighterID-1);               
        
        fighterName = (EditText) findViewById(R.id.fighterNameEditText);
        fighterName.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_NAME)));        
        
        fighterAvatar = (ImageButton) findViewById(R.id.avatarSelectImageButton);
        fighterAvatar.setImageResource(c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_AVATAR)));
        fighterAvatar.setAdjustViewBounds(true);
        fighterAvatar.setMaxHeight(300); fighterAvatar.setMaxWidth(300);
        fighterAvatar.setBackgroundColor(Color.TRANSPARENT);
        avatarID = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_AVATAR));
        
        lvlTextView = (TextView) findViewById(R.id.LevelTextView);
        lvlTextView.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_LVL)));
        lvlTextView.setTextColor(Color.BLACK);
               
        hpTextView = (TextView) findViewById(R.id.HPTextView);
        hpTextView.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_HP)));
        hpTextView.setTextColor(Color.BLACK);
        hpButton = (Button) findViewById(R.id.HPButton);   
        
        hpButton.setOnClickListener(new View.OnClickListener() {
        	
    		@Override
    		public void onClick(View v) {			
    			int editableHP = Integer.parseInt(hpTextView.getText().toString());    				
    				
    			editableHP += 5; remainingSP--;
    				
    			hpTextView.setText(Integer.toString(editableHP));
    			remainingSkillPoints.setText(Integer.toString(remainingSP));
    				
    			if (remainingSP == 0)
    				disableButtons();
    		}
    	});
        
        paTextView = (TextView) findViewById(R.id.PATextView);
        paTextView.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_PATTACK)));
        paTextView.setTextColor(Color.BLACK);
        paButton = (Button) findViewById(R.id.PAButton);

        paButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int editablePA = Integer.parseInt(paTextView.getText().toString());
				
				editablePA++; remainingSP--;
				
				paTextView.setText(Integer.toString(editablePA));
				remainingSkillPoints.setText(Integer.toString(remainingSP));
				
				if (remainingSP == 0)
					disableButtons();
			}
		});
        
        pdTextView = (TextView) findViewById(R.id.PDTextView);
        pdTextView.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_PDEFENSE)));
        pdTextView.setTextColor(Color.BLACK);
        pdButton = (Button) findViewById(R.id.PDButton);
        
        pdButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {					
				int editablePD = Integer.parseInt(pdTextView.getText().toString());
				
				editablePD++; remainingSP--;
				
				pdTextView.setText(Integer.toString(editablePD));
				remainingSkillPoints.setText(Integer.toString(remainingSP));
				
				if (remainingSP == 0)
					disableButtons();
			}
		});
        
        saTextView = (TextView) findViewById(R.id.SATextView);
        saTextView.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_SATTACK)));
        saTextView.setTextColor(Color.BLACK);
        saButton = (Button) findViewById(R.id.SAButton);
        
        saButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {			
				int editableSA = Integer.parseInt(saTextView.getText().toString());
				
				editableSA++; remainingSP--;
				
				saTextView.setText(Integer.toString(editableSA));
				remainingSkillPoints.setText(Integer.toString(remainingSP));
				
				if (remainingSP == 0)
					disableButtons();
			}
		});
        
        sdTextView = (TextView) findViewById(R.id.SDTextView);
        sdTextView.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_SDEFENSE)));
        sdTextView.setTextColor(Color.BLACK);
        sdButton = (Button) findViewById(R.id.SDButton);
        
        sdButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {							
				int editableSD = Integer.parseInt(sdTextView.getText().toString());
				
				editableSD++; remainingSP--;
				
				sdTextView.setText(Integer.toString(editableSD));
				remainingSkillPoints.setText(Integer.toString(remainingSP));
				
				if (remainingSP ==0)
					disableButtons();
			}
		});
        
        remainingSkillPoints = (TextView) findViewById(R.id.SPTextView);
        remainingSkillPoints.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_SKILL_POINTS)));
        remainingSkillPoints.setTextColor(Color.BLACK);
        remainingSP = Integer.parseInt(remainingSkillPoints.getText().toString());
        
        if (Integer.parseInt(remainingSkillPoints.getText().toString()) == 0)
        	disableButtons();
        
        exp = (TextView) findViewById(R.id.EXPTextView);
        expToLevel = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_EXP));
        exp.setText("Exp To Level: "+expToLevel+"/100");
        exp.setTextColor(Color.BLACK);
        
        saveButton = (Button) findViewById(R.id.SaveFighterEditButton);
        
        saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				saveFighter();
				finish();
			}
		});
	}
	
	private void saveFighter() {
		data.saveFighter(fighterID, avatarID, fighterName.getText().toString(),
				Integer.parseInt(lvlTextView.getText().toString()), Integer.parseInt(hpTextView.getText().toString()),
				Integer.parseInt(paTextView.getText().toString()), Integer.parseInt(pdTextView.getText().toString()),
				Integer.parseInt(saTextView.getText().toString()), Integer.parseInt(sdTextView.getText().toString()),
				Integer.parseInt(remainingSkillPoints.getText().toString()), expToLevel);
	}
	
	private void disableButtons() {
		hpButton.setEnabled(false);
		paButton.setEnabled(false);
		pdButton.setEnabled(false);
		saButton.setEnabled(false);
		sdButton.setEnabled(false);		
	}
}
