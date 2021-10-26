package controller;

import dao.IUserDAO;
import dao.UserDAOImpl;
import model.User;

import java.util.List;

public class UserController {
    private IUserDAO dao = new UserDAOImpl();

    private static UserController instance;

    public static UserController getInstance()
    {
        if(instance == null)
        {
            instance = new UserController();
        }
        return instance;
    }

    private UserController() {}

    public void add(User user)
    {
        dao.add(user);
    }

    public List<User> getUsers()
    {
        return dao.getUsers();
    }
}
