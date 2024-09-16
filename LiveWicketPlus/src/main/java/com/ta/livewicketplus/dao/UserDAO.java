package com.ta.livewicketplus.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;

import com.ta.livewicketplus.dto.User;
import com.ta.livewicketplus.util.JPAUtil;

public class UserDAO {
    
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
    public User getUserByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void saveUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            executeInsideTransaction(em, () -> em.persist(user));
        } catch (PersistenceException e) {
        	e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public User getUserById(long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        User user = em.find(User.class, userId); // Find the user with the provided ID
        try {
            if (user != null) {             
                Hibernate.initialize(user.getFavoriteTeams());
                Hibernate.initialize(user.getFavoritePlayers());
            }
            return user; 
        } finally {
            em.close(); 
        }
    }

    public List<User> getAllUsers() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void updateUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        executeInsideTransaction(em, () -> em.merge(user));
    }
    public User getUserByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
    public void deleteUser(long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        executeInsideTransaction(em, () -> {
            User user = em.find(User.class, userId);
            if (user != null) {
                em.remove(user);
            }
        });
    }
    public boolean alreadyExistsUser(String username, String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username OR u.email = :email", User.class);
            query.setParameter("username", username);
            query.setParameter("email", email);
            List<User> users = query.getResultList();
            return !users.isEmpty();
        } finally {
            em.close();
        }
    }

}
