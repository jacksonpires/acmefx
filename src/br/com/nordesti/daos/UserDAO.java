package br.com.nordesti.daos;

import java.util.List;

import br.com.nordesti.models.User;

public interface UserDAO {
	public void create(User user);
	public void update(User user);
	public void delete(User user);
	public List<User> listAll();
	public User searchByUsername(String username);
	public User searchByUser(User user);
	public List<User> listByUsername(String username);
}
