package kpfu.itis.group11_801.kilin.workingClass.database.services;

import kpfu.itis.group11_801.kilin.workingClass.database.DAO.UserDAO;
import kpfu.itis.group11_801.kilin.workingClass.database.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.security.MessageDigest;

public class UserService {
    public User getByEmail(String email) {
        UserDAO userDAO = UserDAO.getUserDAO();
        return userDAO.getByEmail(email);
    }

    public User registrate(User u) {
        UserDAO userDAO = UserDAO.getUserDAO();
        if (getByEmail(u.getLogin()) != null || u.getDate().getYear() > 2001) {return null;}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(u.getPassword().getBytes());
            String result = new String(messageDigest.digest());
            u.setPassword(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return userDAO.create(u);
    }

    public User authentication(String login, String password) {
        User user = getByEmail(login);
        if (user == null) {return null;}
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            String result = new String(messageDigest.digest());
            if (result.equals(user.getPassword())) {
                return user;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(int id) {
        UserDAO userDAO = UserDAO.getUserDAO();
        return userDAO.getById(id);
    }

    public void update(User user) {
        UserDAO userDAO = UserDAO.getUserDAO();
        userDAO.update(user);
    }

    public List<User> getAllUsers() {
        return UserDAO.getUserDAO().getAll();
    }

    public User getBoss(User u) {
        if (u.getBoss() == null) {
            return null;
        }
        return UserDAO.getUserDAO().getById(u.getBoss().getId());
    }

    public List<User> getEmployees(User u) {
        return UserDAO.getUserDAO().getEmployees(u);
    }

    public boolean isEmployeeOf(User boss, User user) {
        if (user.getBoss() != null) {
            return user.getBoss().equals(boss);
        }
        return false;
    }

}