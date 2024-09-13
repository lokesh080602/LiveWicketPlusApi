package com.ta.livewicketplus.dao;

import java.util.List;
import javax.persistence.EntityManager;

import com.ta.livewicketplus.dto.Match;
import com.ta.livewicketplus.dto.PlayerDetails;
import com.ta.livewicketplus.util.JPAUtil;

public class MatchDAO {
	private void executeInsideTransaction(EntityManager em, Runnable action) {
		var tx = em.getTransaction();
		try {
			tx.begin();
			action.run();
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public void saveMatch(Match match) {
		EntityManager em = JPAUtil.getEntityManager();
		executeInsideTransaction(em, () -> em.persist(match));
	}

	public Match getMatchById(long matchId) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.find(Match.class, matchId);
		} finally {
			em.close();
		}
	}


	public List<Match> getAllMatches() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery("SELECT m FROM Match m", Match.class).getResultList();
		} finally {
			em.close();
		}
	}

	public void updateMatch(Match match) {
		EntityManager em = JPAUtil.getEntityManager();
		executeInsideTransaction(em, () -> em.merge(match));
	}

	public void deleteMatch(long matchId) {
	    EntityManager em = JPAUtil.getEntityManager();
	    executeInsideTransaction(em, () -> {
	        Match match = em.find(Match.class, matchId);     
	        if (match != null) {
	            match.getPlayerDetailsList().clear();
	            em.merge(match);
	            em.remove(match);
	        }
	    });
	}
	


	public List<PlayerDetails> getPlayersByMatchId(long matchId) {
	    EntityManager entityManager = JPAUtil.getEntityManager();
	    List<PlayerDetails> players = null;
	    
	    try {
	        String jpql = "SELECT p FROM Match m JOIN m.playerDetailsList p WHERE m.id = :matchId";
	        players = entityManager.createQuery(jpql, PlayerDetails.class)
	                               .setParameter("matchId", matchId)
	                               .getResultList();
	    } catch (Exception e) {
	        e.printStackTrace(); // Handle exceptions appropriately
	    } finally {
	        entityManager.close();
	    }
	    
	    return players;
	}







}
