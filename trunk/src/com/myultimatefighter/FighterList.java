/* Fighter Selection Class of MyUltimateFighter for Android
 */

package com.myultimatefighter;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FighterList extends Activity {
	// variables
	private final String TAG = "FighterList";

	// private
	private FighterData data;
	private RelativeLayout mainLayout;
	private LinearLayout leftFighterListLayout, rightFighterListLayout;
	private int counter = 2, newAvatar;
	//private Vector<Fighter> fighterVector = new Vector<Fighter>();
	//private Vector<LinearLayout> fighterLayoutVector = new Vector<LinearLayout>();
	//private Vector<Integer> usedButtonIDs = new Vector<Integer>(); 
	//private Fighter fighter = new Fighter();
	//private boolean fromSQL = true;
	private Random randomGenerator = new Random();

	// constants
	private static final int MENU_CREATE_NEW = 0;
	private static final int MENU_DELETE_ALL = 1;	

	// on the creation of this activity
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "FighterList onCreate");
		setContentView(R.layout.fighter_list);
		
		getWindow().setBackgroundDrawableResource(R.drawable.scroll);
		
		data = new FighterData(this);
		data.open();
		initialLayoutSetup();
		//fromSQL = false;
		Log.d(TAG, "Initial Setup Complete");
		// Toast.makeText(FighterList.this, "Select Your Fighter!", Toast.LENGTH_SHORT).show();
	}

//	@Override
//	protected void onResume() {
//		Log.d(TAG, "trying to resume");
//		leftFighterListLayout.removeAllViews();
//		rightFighterListLayout.removeAllViews();
//		counter = 2;
//		
//		addFightersToScreen();
//		super.onResume();
//	}
	
	@Override
	protected void onRestart() {
		Log.d(TAG, "trying to resume");
		leftFighterListLayout.removeAllViews();
		rightFighterListLayout.removeAllViews();
		counter = 2;
		
		addFightersToScreen();
		super.onRestart();
	}
	
	// create options Menu
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_CREATE_NEW, 0, "Create New Fighter");
		menu.add(0, MENU_DELETE_ALL, 1, "Delete All Fighters");
		return true;
	}

	// listen for options Menu to be clicked
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_CREATE_NEW:
			//createNewFighter();
			Intent i = new Intent(this, AvatarSelection.class);
			startActivityForResult(i, 0);
			
			Log.d(TAG, "newAvatar: "+newAvatar);
			/*data.newFighter(newAvatar);
			Cursor c = data.getFighters();
			c.moveToLast();
			Log.d(TAG, "count: "+Integer.toString(c.getCount())+"last: "+c.getInt(c.getColumnIndex("_id")));
			addAvatarToScreen(newFighterLayout(c.getInt(c.getColumnIndex("_id"))));*/
			Log.d(TAG, "end of menu create");
			//addAvatarToScreen(fighterLayoutVector.lastElement());			
			return true;
		case MENU_DELETE_ALL:
			deleteAllFighters();
			return true;
		}
		return false;
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		if (resultCode == RESULT_OK)
		{
			if (requestCode == 0)
			{
				Bundle extras = i.getExtras();
				newAvatar = extras.getInt("avatar");
				Log.d(TAG, "newAvatar in result: "+newAvatar);
				
				data.newFighter(newAvatar);
				Cursor c = data.getFighters();
				c.moveToLast();
				Log.d(TAG, "count: "+Integer.toString(c.getCount())+"last: "+c.getInt(c.getColumnIndex("_id")));
				addAvatarToScreen(newFighterLayout(c.getInt(c.getColumnIndex("_id"))));
			}
		}
		else
			Toast.makeText(this, "No Avatar Chosen.", Toast.LENGTH_SHORT).show();
		
		super.onActivityResult(requestCode, resultCode, i);
	}

	//deprecated
	public void addNewFighterToDatabase(Fighter fighter) {
		data.addFighter(fighter);
		//fighterVector.lastElement().setFighterID(data.getLastFighterID());
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
				Intent i = new Intent(FighterList.this, FighterEdit.class);
				i.putExtra("buttonID", v.getId());
				Log.d(TAG, "buttonID sent through click: "+v.getId());
				startActivity(i);
			}
		});
		
		Log.d(TAG, "end of newFighterLayout");
		return newFighterLayout;
	}

	// adds a new fighterAvatar to screen
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
		//Integer intCompare = new Integer(c.getCount());
		if (c.getCount() == 0)
		{
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
			
//			for (int i = c.getCount(); i > 0; i--) {
//				//data.newFighter();
//				//createNewFighter();
//				//fighterVector.lastElement().setFighterID(c.getInt(c.getColumnIndex("_id")));
//				Log.d(TAG, "fighter id set: "+fighterVector.lastElement().getFighterID());
//				//fighterVector.lastElement().setFighterName(c.getString(c.getColumnIndex(FighterData.COL_FIGHTER_NAME)));
//				//fighterVector.lastElement().setFighterAvatar(c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_AVATAR)));	
//				//fighterVector.lastElement().setButtonID(c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_BUTTON_ID)));
//				//fighterVector.lastElement().setDisplayNameID(c.getInt(c.getColumnIndex(FighterData.COL_FIGHTER_DISPLAY_NAME_ID)));
//				//addAvatarToScreen(newFighterLayout(c.getInt(c.getColumnIndex("_id"))));
//				addAvatarToScreen(newFighterLayout(c.getInt(c.getColumnIndex("_id"))));
//				c.moveToNext();
//			}
		}
		Log.d(TAG, "end of initialLayout");
	}

	private void deleteAllFighters() {
//		fighterLayoutVector.clear();
//		fighterVector.clear();
		//usedButtonIDs.clear();
		leftFighterListLayout.removeAllViews();
		rightFighterListLayout.removeAllViews();
		data.deleteFighters();
		counter = 2;
	}
	
	//adds the fighters in the database to the screen
	private void addFightersToScreen() {
		Cursor c = data.getFighters();
		c.moveToFirst();
		Log.d(TAG, "addFightersToScreen Cursor created successfully");
			
		if (c.getCount() != 0)
		{
			while (!c.isLast())
			{
				Log.d(TAG, "on fighter: "+c.getInt(c.getColumnIndex("_id")));
				addAvatarToScreen(newFighterLayout(c.getInt(c.getColumnIndex("_id"))));
				c.moveToNext();
			}
			Log.d(TAG, "on fighter: "+c.getInt(c.getColumnIndex("_id")));
			addAvatarToScreen(newFighterLayout(c.getInt(c.getColumnIndex("_id"))));		
			
			Log.d(TAG, "added fighters successfully");
		}
		Log.d(TAG, "no fighters found");
	}
}

//	private void openFighterEditScreen(int ButtonID) {
//		//Cursor c = data.getFighters();	
//		int fighterNumber = getFighterNumberByButtonID(ButtonID);
//		if (fighterNumber == -1)
//		{
//			Toast.makeText(this, "Error finding fighter", Toast.LENGTH_SHORT).show();
//			Log.e(TAG, "Error finding fighter in openFighterEditScreen using getFighterNumberByButtonID");
//			return;
//		}
//		Intent i = new Intent(this, FighterEdit.class);
//		i.putExtra("fighter_id", fighterNumber);
//		i.putExtra("fighter_name", fighterVector.get(fighterNumber).getFighterName());
//		i.putExtra("fighter_avatar", fighterVector.get(fighterNumber).getFighterAvatar());
//		startActivity(i);
//	}
	
//	private void openFighterEditScreen(Fighter fighter) {
//		//Cursor c = data.getFighters();	
//		//int fighterNumber = getFighterNumberByButtonID(ButtonID);
////		if (fighterNumber == -1)
////		{
////			Toast.makeText(this, "Error finding fighter", Toast.LENGTH_SHORT).show();
////			Log.e(TAG, "Error finding fighter in openFighterEditScreen using getFighterNumberByButtonID");
////			return;
////		}
//		Intent i = new Intent(this, FighterEdit.class);
//		//i.putExtra("fighter_id", fighterNumber);
////		i.putExtra("fighter_name", fighterVector.get(fighterNumber).getFighterName());
////		i.putExtra("fighter_avatar", fighterVector.get(fighterNumber).getFighterAvatar());
//		startActivity(i);
//	}
//}
//	private int getFighterNumberByButtonID(int ButtonID) {
//		for (int i = 0; i < fighterVector.size(); i++)
//		{
//			if (ButtonID == fighterVector.get(i).getButtonID())
//				return i;
//		}
//		return -1;
//	}
//	
//	private int generateUniqueViewID(int currentButtonID, int fighterID, int check) {
//		Log.d(TAG, "Generating ID");
//		int newViewID = randomGenerator.nextInt();
//		boolean ranLastCheck = false;
//		int i = 0, checkThis;
//		
//		for(int count = 0; count < fighterVector.size(); count++)
//		{
//			Log.d(TAG, "Vector Size: "+fighterVector.size());	
//			Log.d(TAG, Integer.toString(fighterVector.get(count).getButtonID()));
//		}
//		
//		while (i < fighterVector.size()-1)
//		{
//			checkThis = fighterVector.get(i).getButtonID();
//			if (newViewID == checkThis)
//			{
////				ranLastCheck = false;
////				newViewID+=2;
//				
//				newViewID = randomGenerator.nextInt();
//				i = 0;
//			}
//			else
//				i++;
////			if (!ranLastCheck && i == fighterVector.size()-2)
////			{
////				ranLastCheck = true;
////				i = 0;
////			}
//		}			
//		
//		return newViewID;
//	}
//}
	
//	private boolean fighterIDExists (int idToTest) {
//		for (int i = 0; i < fighterVector.size(); i++)
//		{
//			if (idToTest == fighterVector.get(i).getFighterID())
//				return true;
//		}		
//		return false;
//	}
	
//	private boolean viewIDExists(int idToTest, int fighterID, int check) {
//		for (int i = 0; i < fighterVector.size()-1; i++)
//		{
//			if (check == 0)
//				return false;
//			else if (check == 1) {
//				if ((idToTest == fighterVector.get(i).getButtonID()	&&
//						fighterID != fighterVector.get(i).getFighterID()) ||
//						idToTest == fighterVector.get(i).getDisplayNameID())
//					return true;
//			}
//			else if (check == 2) {
//				if ((idToTest == fighterVector.get(i).getDisplayNameID() &&
//						fighterID != fighterVector.get(i).getFighterID()) ||
//						idToTest == fighterVector.get(i).getButtonID())
//					return true;
//			}
//			else if (check == 3) {	
//				if ((idToTest == fighterVector.get(i).getButtonID() ||
//						idToTest == fighterVector.get(i).getDisplayNameID()) &&
//						fighterID != fighterVector.get(i).getFighterID())
//					return true;
//			}
//			else if (check ==4) {
//				if (idToTest == fighterVector.get(i).getButtonID() || idToTest == fighterVector.get(i).getDisplayNameID())
//					return true;
//			}
//		}
//		Log.d(TAG, "viewIDExists returned false");
//		return false;
//	}
	
	// on destruction
//	protected void finalize() throws Throwable {
//		super.finalize();
//	}
//}