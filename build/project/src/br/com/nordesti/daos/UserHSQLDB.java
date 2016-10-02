package br.com.nordesti.daos;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import br.com.nordesti.models.User;
import br.com.nordesti.utils.db.ConfigurationHSQLDB;
import br.com.nordesti.utils.db.ConfigurationHSQLDB.Environment;
import br.com.nordesti.utils.db.DBConnection;

public class UserHSQLDB implements UserDAO {
	
	private Session session;
	
	public UserHSQLDB() {
		try {
			this.session = DBConnection.getHibernateSession(new ConfigurationHSQLDB());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public UserHSQLDB(Environment environment) {
		try {
			this.session = DBConnection.getHibernateSession(new ConfigurationHSQLDB(environment));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void create(User user) {		
		this.session.getTransaction().begin();    
		this.session.persist(user);
		this.session.getTransaction().commit();  
	}

	@Override
	public List<User> listAll(){
		Criteria criteria = this.session.createCriteria(User.class);
		List<User> users =  criteria.list();
		return users;
	}
	
	@Override
	public User searchByUsername(String username) {
		Criteria criteria = this.session.createCriteria(User.class);
		Criterion filtro = Restrictions.eq("username", username).ignoreCase();
		
		criteria.add(filtro);
		
		return ((User) criteria.uniqueResult());
	}
	
	@Override
	public User searchByUser(User user) {
		Criteria criteria = this.session.createCriteria(User.class);
		Criterion filtro = Restrictions.eq("username", user.getUsername()).ignoreCase();
		
		criteria.add(filtro);
		
		return ((User) criteria.uniqueResult());
	}
	
	@Override
	public List<User> listByUsername(String username) {
		Criteria criteria = this.session.createCriteria(User.class);
		Criterion filtro = Restrictions.like("username", "%"+username+"%").ignoreCase();
		
		criteria.add(filtro);
		List<User> users =  criteria.list();
		
		return users;
	}

	@Override
	public void update(User user) {
		this.session.getTransaction().begin();    
		this.session.update(user);
		this.session.getTransaction().commit();  
	}

	@Override
	public void delete(User user) {
		this.session.getTransaction().begin();    
		this.session.delete(user);
		this.session.getTransaction().commit();  
	}
}
