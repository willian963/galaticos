package com.galaticos.randomteam.entity;

/**
 * Entity Player
 * 
 * @author <a href="mailto:willian.ribeiro@gmail.com">Willian Ribeiro</a>
 * 
 */
public class Player implements Comparable<Player> {

	private String name;
	private float grade;
	private Integer position;
	private boolean showPosition = false;

	public static int GOALKEEPER = 1;
	public static int DEFENSE = 2;
	public static int MIDFIELD = 3;
	public static int ATTACK = 4;

	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Player(String name, float grade, int position) {
		this.name = name;
		this.grade = grade;
		this.position = position;
	}

	public Player(String name, float grade, int position, boolean showPosition) {
		this.name = name;
		this.grade = grade;
		this.position = position;
		this.showPosition = showPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return this.position;
	}

	@Override
	public String toString() {
		String sret = this.name;

		if (this.showPosition) {
			sret += " " + toStringPosition();
		}
		return sret;
	}

	public void setShowPosition(boolean showPosition) {
		this.showPosition = showPosition;
	}

	private String toStringPosition() {
		String ret = null;

		if (this.position == Player.ATTACK) {
			ret = "(A)";
		}

		if (this.position == Player.DEFENSE) {
			ret = "(D)";
		}
		
		if (this.position == Player.MIDFIELD) {
			ret = "(M)";
		}

		if (this.position == Player.GOALKEEPER) {
			ret = "(G)";
		}

		return ret;
	}

	public int compareTo(Player p) {
		return this.position.toString().compareTo(p.position.toString());
	}

}
