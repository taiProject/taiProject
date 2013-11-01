package pl.edu.agh.dfs.daos;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class GenericDaoImpl<T, V extends Serializable> implements GenericDao<T, V> {

	@Autowired
	protected SessionFactory sessionFactory;

	protected Class classType;

	@Override
	public void create(T t) {
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void delete(V id) {
		sessionFactory.getCurrentSession().delete(id);
	}

	@Override
	public T find(V id) {
		return (T) sessionFactory.getCurrentSession().get(classType, id);
	}

	@Override
	public void update(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

}
