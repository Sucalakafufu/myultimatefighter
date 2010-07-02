/* Main Class of MyUltimateFighter for Android
 */

package com.myultimatefighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class main extends Activity {
//variables
    private Button SelectFighterButton, ClearFightersButton, TrainingButton;
    private FighterData data;
    
    //constants
    private static final int MENU_QUIT = 0, MENU_SETTINGS = 1;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //set window color or background
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        getWindow().setBackgroundDrawableResource(R.drawable.mainbackground);
        //getWindow().setBackgroundDrawableResource(R.drawable.emblem);

        //configure Fighter Selection Button to listen on click
        SelectFighterButton = (Button) findViewById(R.id.toFighterSelectScreenButton);
        SelectFighterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	openFighterSelectionScreen();
            }
        });
        
        TrainingButton = (Button) findViewById(R.id.toTrainerButton);
        TrainingButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(main.this, SelectFighterForTraining.class));
			}
		});
        
        ClearFightersButton = (Button) findViewById(R.id.clearFightersButton);
        ClearFightersButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				data = new FighterData(main.this);
				data.open();
				data.deleteFighters();
				Toast.makeText(main.this, "Cleared Fighters", Toast.LENGTH_SHORT).show();
				
			}
		});
    }
    
    //creating the options Menu	
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_SETTINGS, 0, "Settings");
	    menu.add(0, MENU_QUIT, 1, "Exit");
	    return true;
	}
    
  //listen for options Menu to be clicked
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case MENU_QUIT:
	    	this.finish();
	        return true;
	    }
	    return false;
	}
    
//function to open the Fighter Selection Screen  
    protected void openFighterSelectionScreen() {    	
    	Intent i = new Intent(this, FighterList.class);
    	startActivity(i);
    }   
}    
   