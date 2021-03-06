package com.revature.qc.data;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.revature.qc.beans.Trainee;

@Component(value="facade")
public class DataFacade implements ApplicationContextAware{

	private SessionFactory sessionFactory;
	private ApplicationContext ctxt;
	
	public void setApplicationContext(ApplicationContext ctxt) throws BeansException {
		this.ctxt = ctxt;
	}
	public DataFacade() {
		sessionFactory = new Configuration()
				.configure().buildSessionFactory();
	}
	
	public Trainee getDan(int id){
		Session session = sessionFactory.openSession();
		// TX here
		DAO dao = ctxt.getBean("dao", DAO.class);
		dao.setSession(session);
		
		// do DAO stuff
		Trainee t = dao.getDanRecord(id);
		
		// always close session then return datas
		// commit/rollback
		session.close();
		return t;
	}

	public List<Trainee> getAll(){
		Session session = sessionFactory.openSession();
		// TX here
		DAO dao = ctxt.getBean("dao", DAO.class);
		dao.setSession(session);
		
		// do DAO stuff
		List<Trainee> t = dao.getAll();
		
		// always close session then return datas
		// commit/rollback
		session.close();
		return t;
	}
	public void insert(Trainee obj) {
		Session session = sessionFactory.openSession();
		DAO dao = ctxt.getBean("dao", DAO.class);
		dao.setSession(session);
		Transaction tx = session.beginTransaction();
		try{
			dao.insert(obj);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
		session.close();
	}
	
}






