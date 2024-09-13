package com.ta.livewicketplus.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teamA")
    private String teamA;

    @Column(name = "teamB")
    private String teamB;

    @Column(name = "scoreTeamA")
    private int scoreTeamA;

    @Column(name = "scoreTeamB")
    private int scoreTeamB;

    @OneToMany
    @JoinTable(
        name = "match_playerdetails",
        joinColumns = @JoinColumn(name = "match_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<PlayerDetails> playerDetailsList;

    public Long getMatchId() {
        return id;
    }

    public void setMatchId(Long matchId) {
        this.id = matchId;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public Integer getScoreTeamA() {
        return scoreTeamA;
    }

    public void setScoreTeamA(Integer scoreTeamA) {
        this.scoreTeamA = scoreTeamA;
    }

    public Integer getScoreTeamB() {
        return scoreTeamB;
    }

    public void setScoreTeamB(Integer scoreTeamB) {
        this.scoreTeamB = scoreTeamB;
    }

    public List<PlayerDetails> getPlayerDetailsList() {
        return playerDetailsList;
    }

    public void setPlayerDetailsList(List<PlayerDetails> playerDetailsList) {
        this.playerDetailsList = playerDetailsList;
    }

    public Match() {}

    public Match(Long matchId, String teamA, String teamB, Integer scoreTeamA, Integer scoreTeamB, List<PlayerDetails> playerDetailsList) {
        this.id = matchId;
        this.teamA = teamA;
        this.teamB = teamB;
        this.scoreTeamA = scoreTeamA;
        this.scoreTeamB = scoreTeamB;
        this.playerDetailsList = playerDetailsList;
    }
}
