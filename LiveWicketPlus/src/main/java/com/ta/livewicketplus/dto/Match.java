package com.ta.livewicketplus.dto;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "matchs")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @Column(name = "teamA")
    private String teamA;

    @Column(name = "teamB")
    private String teamB;

    @Column(name = "scoreTeamA")
    private int scoreTeamA;

    @Column(name = "scoreTeamB")
    private int scoreTeamB;

    @Column(name = "toss_winner")
    private String tossWinner;

    @Column(name = "opted_to")
    private String optedTo;

    @Column(name = "overs")
    private int overs;

    @Column(name = "venue")
    private String venue;

    @Column(name = "match_date")
    @Temporal(TemporalType.DATE)
    private Date matchDate;

    @Column(name = "umpires")
    private String umpires;

    @OneToMany
    @JoinTable(
        name = "matchs_playerdetails",
        joinColumns = @JoinColumn(name = "match_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<PlayerDetails> playerDetailsList;

    // Getters and Setters
    public Long getId() {
        return matchId;
    }

    public void setId(Long matchId) {
        this.matchId = matchId;
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

    public int getScoreTeamA() {
        return scoreTeamA;
    }

    public void setScoreTeamA(int scoreTeamA) {
        this.scoreTeamA = scoreTeamA;
    }

    public int getScoreTeamB() {
        return scoreTeamB;
    }

    public void setScoreTeamB(int scoreTeamB) {
        this.scoreTeamB = scoreTeamB;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getOptedTo() {
        return optedTo;
    }

    public void setOptedTo(String optedTo) {
        this.optedTo = optedTo;
    }

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getUmpires() {
        return umpires;
    }

    public void setUmpires(String umpires) {
        this.umpires = umpires;
    }

    public List<PlayerDetails> getPlayerDetailsList() {
        return playerDetailsList;
    }

    public void setPlayerDetailsList(List<PlayerDetails> playerDetailsList) {
        this.playerDetailsList = playerDetailsList;
    }
}
