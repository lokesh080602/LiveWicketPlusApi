package com.ta.livewicketplus.dao;

import com.ta.livewicketplus.dto.PlayerDetails;
import com.ta.livewicketplus.util.JPAUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

public class PlayerDetailsDAO {
	public void save(PlayerDetails playerDetails) {
		executeInsideTransaction(em -> em.persist(playerDetails));
	}

	public void update(PlayerDetails playerDetails) {
		executeInsideTransaction(em -> em.merge(playerDetails));
	}

	public void delete(long id) {
		executeInsideTransaction(em -> {
			PlayerDetails playerDetails = em.find(PlayerDetails.class, id);
			if (playerDetails != null) {
				em.remove(playerDetails);
			}
		});
	}

	public PlayerDetails findById(long id) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.find(PlayerDetails.class, id);
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<PlayerDetails> findAll() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			Query query = em.createQuery("SELECT p FROM PlayerDetails p");
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	private void executeInsideTransaction(EntityManagerAction action) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			action.execute(em);
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
	public PlayerDetails getPlayerByNameAndTeamAndRole(String name, String team, String role) {
	    EntityManager em = JPAUtil.getEntityManager();
	    try {
	        // Assuming a JPQL query to find the player
	        String query = "SELECT p FROM PlayerDetails p WHERE p.name = :name AND p.team = :team AND p.role = :role";
	        TypedQuery<PlayerDetails> typedQuery = em.createQuery(query, PlayerDetails.class);
	        typedQuery.setParameter("name", name);
	        typedQuery.setParameter("team", team);
	        typedQuery.setParameter("role", role);
	        List<PlayerDetails> results = typedQuery.getResultList();
	        return results.isEmpty() ? null : results.get(0); // Return the first matching player, if any
	    } finally {
	        em.close();
	    }
	}

	@FunctionalInterface
	private interface EntityManagerAction {
		void execute(EntityManager em);
	}
}
