package com.ta.livewicketplus.dto;

import javax.persistence.*;

@Entity
public class PlayerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private Long playerId;
    private String name;
    private int age;
    private String nationality;
    private String team;
    private String role;
    private String battingStyle;
    private String bowlingStyle;
    private String currentMatchStatus;


    public PlayerDetails() {}

    public PlayerDetails(String name, int age, String nationality, String team, String role, String battingStyle, String bowlingStyle, String currentMatchStatus) {
        this.name = name;
        this.age = age;
        this.nationality = nationality;
        this.team = team;
        this.role = role;
        this.battingStyle = battingStyle;
        this.bowlingStyle = bowlingStyle;
        this.currentMatchStatus = currentMatchStatus;
    }

   

    public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public void setBattingStyle(String battingStyle) {
        this.battingStyle = battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public void setBowlingStyle(String bowlingStyle) {
        this.bowlingStyle = bowlingStyle;
    }

    public String getCurrentMatchStatus() {
        return currentMatchStatus;
    }

    public void setCurrentMatchStatus(String currentMatchStatus) {
        this.currentMatchStatus = currentMatchStatus;
    }


}
