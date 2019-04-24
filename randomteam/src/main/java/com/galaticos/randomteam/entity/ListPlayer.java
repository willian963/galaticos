package com.galaticos.randomteam.entity;

import java.util.ArrayList;

/**
 * Class to list Players
 * 
 * @author <a href="mailto:willian.ribeiro@gmail.com">Willian Ribeiro</a>
 * 
 */

public class ListPlayer {
	private float grade;
	private ArrayList<Player> aDefense;
	private ArrayList<Player> aAtack;
	private ArrayList<Player> aGoal;
	private ArrayList<Player> aMidfield;

	public ListPlayer(float nota) {
		this.grade = nota;
		aDefense = new ArrayList<Player>();
		aAtack = new ArrayList<Player>();
		aGoal = new ArrayList<Player>();
		aMidfield = new ArrayList<Player>();
	}

	public float getListGrade() {
		return this.grade;
	}

	public void addPlayer(Player p) {
		if (p.getPosition() == Player.ATTACK) {
			aAtack.add(p);
		} else if (p.getPosition() == Player.DEFENSE) {
			aDefense.add(p);
		} else if (p.getPosition() == Player.GOALKEEPER) {
			aGoal.add(p);
		} else if (p.getPosition() == Player.MIDFIELD) {
			aMidfield.add(p);
		}
	}

	public void addPlayer(ArrayList<Player> p) {
		for (int i = 0; i < p.size(); i++) {
			addPlayer(p.get(i));
		}
	}

	public ArrayList<Player> getAttackList() {
		return this.aAtack;
	}

	public ArrayList<Player> getDefenseList() {
		return this.aDefense;
	}

	public ArrayList<Player> getGoalList() {
		return this.aGoal;
	}

	public ArrayList<Player> getMidfieldList() {
		return this.aMidfield;
	}

	public void clearLists() {
		this.aAtack.clear();
		this.aDefense.clear();
		this.aGoal.clear();
		this.aMidfield.clear();
	}

	public float getAttackGrade() {
		float result = 0;
		for (int i = 0; i < aAtack.size(); i++) {
			result += aAtack.get(i).getGrade();
		}
		return result;
	}

	public float getDefenseGrade() {
		float result = 0;
		for (int i = 0; i < aDefense.size(); i++) {
			result += aDefense.get(i).getGrade();
		}
		return result;
	}

	public float getGoalGrade() {
		float result = 0;
		for (int i = 0; i < aGoal.size(); i++) {
			result += aGoal.get(i).getGrade();
		}
		return result;
	}

	public float getMidfieldGrade() {
		float result = 0;
		for (int i = 0; i < aMidfield.size(); i++) {
			result += aMidfield.get(i).getGrade();
		}
		return result;
	}

	public float getTotalGrade() {
		return getAttackGrade() + getDefenseGrade() + getGoalGrade() + getMidfieldGrade();
	}

	public int getQtdPlayer() {
		return aAtack.size() + aDefense.size() + aGoal.size() + aMidfield.size();
	}
}
