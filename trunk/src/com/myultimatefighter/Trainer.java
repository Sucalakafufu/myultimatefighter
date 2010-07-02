package com.myultimatefighter;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Trainer extends Activity {
	public static final Vector<Integer> opponentAvatars = new Vector<Integer>(Arrays.asList(R.drawable.bot, R.drawable.emblem,
			R.drawable.spartan, R.drawable.ninja, R.drawable.kraken));

	private final static String TAG = "Trainer";
	private FighterData data;
	private int opponentAvatar, opponentPDefense, opponentPAttack, opponentSDefense, opponentSAttack, opponentCurrentHP, opponentMaxHP, 
	opponentCurrentLVL, fighterCurrentHP, fighterMaxHP;	
	private LinearLayout fighterLayout, opponentLayout;
	private ImageButton fighterImageButton, opponentImageButton;
	private TextView fighterName, fighterLVL, fighterHP, fighterPA, fighterPD, fighterSA, fighterSD, opponentName, opponentLVL,
		opponentHP, opponentPA, opponentPD, opponentSA, opponentSD, console;
	private Button PAttackButton, SAttackButton;
	private int fighterID;
	private boolean isOpponentTurn = false, opponentAttacking = false;
	private Random randomGenerator = new Random();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trainer);
		getWindow().setBackgroundDrawableResource(R.drawable.scroll);
		
		Bundle b = getIntent().getExtras();
        fighterID = b.getInt("buttonID");
		
		data = new FighterData(this);
		data.open();
		
		Cursor c = data.getFighters();
        c.moveToPosition(fighterID-1);    
        
        opponentCurrentHP = 100; opponentMaxHP = 100; opponentPAttack = 10; opponentPDefense = 10; opponentSAttack = 10; opponentSDefense = 10;
        
        fighterImageButton = (ImageButton) findViewById(R.id.yourFighterImageButton);
        fighterImageButton.setImageResource(c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_AVATAR)));
        fighterImageButton.setAdjustViewBounds(true);
        fighterImageButton.setMaxHeight(256); fighterImageButton.setMaxWidth(256);
        fighterImageButton.setBackgroundColor(Color.TRANSPARENT);
        
        fighterName = (TextView) findViewById(R.id.yourFighterNameTextView);
        fighterName.setText(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_NAME)));
        
        fighterLVL = (TextView) findViewById(R.id.yourLVLTextView);
        fighterLVL.setText("LVL: "+c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_LVL)));
        
        fighterHP = (TextView) findViewById(R.id.yourHPTextView);
        fighterCurrentHP = fighterMaxHP = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_HP));
        fighterHP.setText("HP: "+fighterCurrentHP+"/"+fighterCurrentHP);
        
        fighterPA = (TextView) findViewById(R.id.yourPATextView);
        fighterPA.setText("PA: "+c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_PATTACK)));
        
        fighterPD = (TextView) findViewById(R.id.yourPDTextView);
        fighterPD.setText("PD: "+c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_PDEFENSE)));
        
        fighterSA = (TextView) findViewById(R.id.yourSATextView);
        fighterSA.setText("SA: "+c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_SATTACK)));
        
        fighterSD = (TextView) findViewById(R.id.yourSDTextView);
        fighterSD.setText("SD: "+c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_SDEFENSE)));

        opponentAvatar = opponentAvatars.get(randomGenerator.nextInt(opponentAvatars.size()));
		opponentImageButton = (ImageButton) findViewById(R.id.opponentImageButton);
        opponentImageButton.setImageResource(opponentAvatar);
        opponentImageButton.setAdjustViewBounds(true);
        opponentImageButton.setMaxHeight(256); opponentImageButton.setMaxWidth(256);
        opponentImageButton.setBackgroundColor(Color.TRANSPARENT);   
        
        opponentName = (TextView) findViewById(R.id.opponentNameTextView);
        if (opponentAvatar == R.drawable.bot)
        	opponentName.setText("TriGolem");
        else if (opponentAvatar == R.drawable.emblem)
        	opponentName.setText("Super Emblem");
        else if (opponentAvatar == R.drawable.spartan)
        	opponentName.setText("Leonidas");
        else if (opponentAvatar == R.drawable.kraken)
        	opponentName.setText("The Kraken");
        else if (opponentAvatar == R.drawable.ninja)
        	opponentName.setText("Pirate Killer");
        
        opponentLVL = (TextView) findViewById(R.id.opponentLVLTextView);
        opponentLVL.setText("LVL: "+c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_LVL)));
        
        opponentHP = (TextView) findViewById(R.id.opponentHPTextView);
        opponentHP.setText("HP: "+opponentCurrentHP+"/"+opponentMaxHP);
        
        PAttackButton = (Button) findViewById(R.id.usePAButton); 
        PAttackButton.setId(fighterID);
        PAttackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				Cursor c = data.getFighters();
		        c.moveToPosition(fighterID-1);
				
		        opponentAttacking = false;
				int damage = FighterBattle.Damage(c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_PATTACK)), opponentPDefense, 
						c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_LVL))-opponentCurrentLVL);
				opponentCurrentHP = opponentCurrentHP-damage;
				opponentHP.setText("HP: "+opponentCurrentHP+"/"+opponentMaxHP);
				
				if (!opponentAttacking && isSomeoneDead(false))
		    	{
					addExp(fighterID);
		    		//Toast.makeText(Trainer.this, "Victory!", Toast.LENGTH_SHORT).show();
					Bundle selection = new Bundle();
					Intent i = new Intent();
					
					selection.putBoolean("won", true);
					i.putExtras(selection);
					
					setResult(RESULT_OK, i);
		    		finish();
		    	}
				
				opponentAttack(fighterID);
				opponentAttacking = true;
				if (opponentAttacking && isSomeoneDead(true))
	        	{
	        		//Toast.makeText(Trainer.this, "Defeat!", Toast.LENGTH_SHORT).show();
					Bundle selection = new Bundle();
					Intent i = new Intent();
					
					selection.putBoolean("won", false);
					i.putExtras(selection);
					
					setResult(RESULT_OK, i);
	        		finish();
	        	}
			}
		});
        
        SAttackButton = (Button) findViewById(R.id.useSAButton);
        SAttackButton.setId(fighterID);
        SAttackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {			
				Cursor c = data.getFighters();
		        c.moveToPosition(fighterID-1);
				
		        opponentAttacking = false;
				int damage = FighterBattle.Damage(c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_SATTACK)), opponentSDefense, 
						c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_LVL))-opponentCurrentLVL);
				opponentCurrentHP = opponentCurrentHP-damage;
				opponentHP.setText("HP: "+opponentCurrentHP+"/"+opponentMaxHP);
				
				if (!opponentAttacking && isSomeoneDead(false))
		    	{
					addExp(fighterID);
		    		//Toast.makeText(Trainer.this, "Victory!", Toast.LENGTH_SHORT).show();
					Bundle selection = new Bundle();
					Intent i = new Intent();
					
					selection.putBoolean("won", true);
					i.putExtras(selection);
					
					setResult(RESULT_OK, i);
		    		finish();
		    	}
				
				opponentAttack(fighterID);
				opponentAttacking = true;
				if (opponentAttacking && isSomeoneDead(true))
	        	{
	        		//Toast.makeText(Trainer.this, "Defeat!", Toast.LENGTH_SHORT).show();
					Bundle selection = new Bundle();
					Intent i = new Intent();
					
					selection.putBoolean("won", false);
					i.putExtras(selection);
					
					setResult(RESULT_OK, i);
	        		finish();
	        	}
			}
		});
        
        isOpponentTurn = randomGenerator.nextBoolean();
        if (isOpponentTurn)
        {
        	opponentAttacking = true;
        	opponentAttack(fighterID);
        	if (isSomeoneDead(true))
        	{
        		//Toast.makeText(Trainer.this, "Defeat!", Toast.LENGTH_SHORT).show();
        		Bundle selection = new Bundle();
				Intent i = new Intent();
				
				selection.putBoolean("won", false);
				i.putExtras(selection);
				
				setResult(RESULT_OK, i);
        		finish();
        	}
        	isOpponentTurn = false;
        }
	}
	
	private void opponentAttack(int fighterID)
	{
		Log.d(TAG, "opponentAttacking");
		Cursor c = data.getFighters();
        c.moveToPosition(fighterID-1);
        
		int damage;
    	boolean isPAttack = randomGenerator.nextBoolean();
    	if (isPAttack)
    	{
    		damage = FighterBattle.Damage(opponentPAttack, c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_PDEFENSE)), 
    				c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_LVL))-opponentCurrentLVL);
    	}
    	else
    	{
    		damage = FighterBattle.Damage(opponentSAttack, c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_SDEFENSE)), 
					c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_LVL))-opponentCurrentLVL);
    	}
    	
    	fighterCurrentHP = fighterCurrentHP-damage;
    	fighterHP.setText("HP: "+fighterCurrentHP+"/"+fighterMaxHP);    	    	
	}
	
	private boolean isSomeoneDead(boolean opponentAttacking)
	{   
		if (opponentAttacking && fighterCurrentHP <= 0)
			return true;
		else if (opponentCurrentHP <= 0)
			return true;
		
		return false;
	}
	
	private void addExp(int fighterID)
	{
		Cursor c = data.getFighters();
        c.moveToPosition(fighterID-1);
        
        int currentExp = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_EXP));
        currentExp += 5;
        
        int level = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_LVL)), avatarID = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_AVATAR)),
    	pAttack = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_PATTACK)), pDefense = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_PDEFENSE)), 
    			sAttack = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_SATTACK)), sDefense = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_SDEFENSE)),
    			SP = c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_SKILL_POINTS));

        
        if (currentExp == 100)
        {
        	level++;
        	currentExp = 0;
        	SP += 10;        	        	
        }
        
        data.saveFighter(fighterID, avatarID, fighterName.getText().toString(),
				level, fighterMaxHP,
				pAttack, pDefense,
				sAttack, sDefense,
				SP, currentExp);
	}
}
