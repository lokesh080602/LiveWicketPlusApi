package com.ta.livewicketplus.service;

import java.util.List;

import com.ta.livewicketplus.dao.MatchDAO;
import com.ta.livewicketplus.dto.Match;
import com.ta.livewicketplus.dto.PlayerDetails;

public class MatchService {

    private MatchDAO matchDAO;
    public MatchService() {
        matchDAO = new MatchDAO();
    }

    public Match getMatchDetails(long matchId) {
        Match match = matchDAO.getMatchById(matchId);
        if (match != null) {
            return match;
        } else {
            return null;
        }
    }

    public void saveMatch(Match match) {
        matchDAO.saveMatch(match);
    }

    public void updateMatch(Match match) {
        matchDAO.updateMatch(match);
    }


    public void deleteMatch(long matchId) {
        matchDAO.deleteMatch(matchId);
    }


    public List<Match> getAllMatches() {
        return matchDAO.getAllMatches();
    }


    public long getMatchCount() {
        return getAllMatches().size();
    }
    public List<PlayerDetails> getPlayersByMatchId(Long long1){
       return matchDAO.getPlayersByMatchId(long1);
    }

    
}

