/* Fighter Class of MyUltimateFighter for Android
 */

package com.myultimatefighter;

public class Fighter {
	// variables
	private String fighterName;
	private int fighterAvatar, level, healthPoints, pAttack, pDefense, mAttack,
			mDefense, freePoints, exPoints, wins, buttonID, displayNameID, fighterID;

	public Fighter() {
		fighterID = 0;
		level = 1;
		healthPoints = 100;
		pAttack = 10;
		pDefense = 10;
		mAttack = 10;
		mDefense = 10;
		freePoints = 10;
		exPoints = level;
		wins = 0;
		fighterName = "Fighter";
		fighterAvatar = R.drawable.bot;
		buttonID = 0;
		displayNameID = 1;
	}

	public String getFighterName() {
		return fighterName;
	}

	public void setFighterName(String name) {
		fighterName = name;
	}

	public int getFighterAvatar() {
		return fighterAvatar;
	}

	public void setFighterAvatar(int avatar) {
		fighterAvatar = avatar;
	}

	public int getFighterHP() {
		return healthPoints;
	}

	public void setFighterHP(int HP) {
		healthPoints = HP;
	}

	public int getFighterPAttack() {
		return pAttack;
	}

	public void setFighterPAttack(int newPAttack) {
		pAttack = newPAttack;
	}

	public int getFighterPDefense() {
		return pDefense;
	}

	public void setFighterPDefense(int newPDefense) {
		pDefense = newPDefense;
	}

	public int getFighterMAttack() {
		return mAttack;
	}

	public void setFighterMAttack(int newMAttack) {
		mAttack = newMAttack;
	}

	public int getFighterMDefense() {
		return mDefense;
	}

	public void setFighterMDefense(int newMDefense) {
		mDefense = newMDefense;
	}
	
	public int getFighterFreePoints() {
		return freePoints;
	}
	
	public void setFighterFreePoints(int newFreePoints) {
		freePoints = newFreePoints;
	}
	
	public int getFighterExPoints() {
		return exPoints;
	}
	
	public void setFighterExPoints(int newExPoints) {
		exPoints = newExPoints;
	}
	
	public int getFighterWins() {
		return wins;
	}
	
	public void setFighterWins(int newWins) {
		wins = newWins;
	}
	
	public int getButtonID() {
		return buttonID;
	}
	
	public void setButtonID(int newButtonID) {
		buttonID = newButtonID;
	}
	
	public int getDisplayNameID() {
		return displayNameID;
	}
	
	public void setDisplayNameID(int newDisplayNameID) {
		displayNameID = newDisplayNameID;
	}
	
	public int getFighterID() {
		return fighterID;
	}
	
	public void setFighterID(int newFighterID) {
		fighterID = newFighterID;
	}
}
