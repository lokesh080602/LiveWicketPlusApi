package com.ta.livewicketplus.service;

import java.util.List;

import com.ta.livewicketplus.dao.PlayerDetailsDAO;
import com.ta.livewicketplus.dto.PlayerDetails;


public class PlayerService {

    private PlayerDetailsDAO playerDetailsDAO = new PlayerDetailsDAO();

    public PlayerDetails getPlayerDetails(long id) {
        return playerDetailsDAO.findById(id);
    }
    public List<PlayerDetails> getAllPlayerDetails(){
    	return playerDetailsDAO.findAll();
    }

    public void addPlayerDetails(PlayerDetails playerDetails) {
        playerDetailsDAO.save(playerDetails);
    }
    
    public void updatePlayerDetails(PlayerDetails playerDetails) {
        playerDetailsDAO.update(playerDetails);
    }
   public PlayerDetails getPlayerByNameAndTeamAndRole(String name, String team, String role) {
	   
	        return playerDetailsDAO.getPlayerByNameAndTeamAndRole(name, team, role);
	    }
    public void deletePlayerDetails(long id) {
        playerDetailsDAO.delete(id);
    }
}
