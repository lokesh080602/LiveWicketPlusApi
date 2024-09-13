package com.ta.livewicketplus.service;

import java.util.List;
import com.ta.livewicketplus.dao.UserDAO;
import com.ta.livewicketplus.dto.User;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void saveUser(User user) {
        userDAO.saveUser(user);
    }

    public User getUserById(long userId) {
        return userDAO.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(long userId) {
        userDAO.deleteUser(userId);
    }
    public User authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public boolean alreadExitUser(User user)  {
    	return userDAO.alreadyExistsUser(user.getUsername(),user.getEmail());
}
}