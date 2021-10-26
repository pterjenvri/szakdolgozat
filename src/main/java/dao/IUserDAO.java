package dao;

import model.User;

import java.util.List;

public interface IUserDAO {
    public void add(User user);
    public List<User> getUsers();
}
