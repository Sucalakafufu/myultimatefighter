package com.myultimatefighter;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;

public class FighterBattle extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fighter_battle);
	}
    
    public static final int Damage(int att, int def, int dif)//Attack, Defense, Difference in level
    {
    	int mod = 1;		//Hit-Miss modifier, default 1
    	int pwr = 100;		//Power modifier, default 100
    	int crit = 10;		//Critical Hit Percentage
    	int acc = 90;		//Accuracy
    	Random generator = new Random();//Random generator constructor
    	int randNum;//random int
    	
    	/*if(dif<0)
    		crit-=dif;//decrease by a negative, increase in Critical Hit Percentage
    	else if(dif>0)
    		acc-=dif;//decrease by a positive, decrease in Accuracy
*/    	
    	randNum = generator.nextInt();//generate random int
    	
    	int r = 1+randNum%100;
    	if (r<=crit)//if within Critical Hit Percentage
    		mod = 2;//hit modifier is 2 (2x damage)
    	else if(r>=acc)
    		mod = 0;
    	
    	randNum = generator.nextInt();//generate random int
    	
    	if (dif > -16)//power of move
    		pwr =(85-dif)+ randNum%(16+dif);
    	
    	int hit =((att*pwr)/(def*10)+2)*mod;
    	return hit;
    }
}

//still in C++, needs translation.
/*int damage(int atk, int def, int lDif){
	int mod = 1;		//Hit-Miss modifier, default 1
	int pwr = 100;		//Power modifier, default 100
	int crit = 10;		//Critical Hit Percentage
	int acc = 90;		//Accuracy
	if (lDif < 0)
		crit-=lDif;		//decrease by a negative, increase in Critical Hit Percentage
	else if (lDif > 0)
		acc-=lDif;		//decrease by a positive, decrease in Accuracy
	int r = 1 + rand() % 100;
	if (r <= crit)		//if within Critical Hit Percentage
		mod = 2;		//hit modifier is 2 (2x damage)
	else if (r >= acc)	//if beyond Accuracy Percentage
		mod = 0;		//hit modifier is 0 (miss)
	if (lDif > -16)		//power of move
		pwr = (85-lDif) + rand() % (16+lDif);
	int hit = ((atk*pwr)/(def*10)+2)*mod;
	return hit;}*/