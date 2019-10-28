package com.lfec.sql.controller;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.lfec.sql.domain.Entidade;
import com.lfec.sql.domain.Negociacao;

public class Dao {

	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
			.createEntityManagerFactory("cotacao");

	private static EntityManager manager;

	private static Dao INSTANCE;

	// Singleton
	private Dao() {

	}

	public static Dao getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Dao();
		}

		return INSTANCE;
	}

	public EntityManager getManager() {
		if (manager == null || !manager.isOpen()) {
			manager = ENTITY_MANAGER_FACTORY.createEntityManager();
		}
		return manager;
	}

	private EntityTransaction openTransaction() {
		EntityTransaction transaction = getManager().getTransaction();
		transaction.begin();

		return transaction;
	}

	public <T extends Entidade> T persist(T obj) {

		EntityTransaction transaction = openTransaction();
		EntityManager manager = getManager();
		try {
			if (obj.isNew()) {
				manager.persist(obj);

			} else {
				manager.merge(obj);
			}
			manager.flush();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			manager.close();
		}
		return obj;
	}

	public <T extends Entidade> List<Object[]> list(Query<T> query) {

		EntityTransaction transaction = openTransaction();
		EntityManager manager = getManager();
		List<Object[]> ret = null;
		try {
			javax.persistence.Query listQuery = manager.createQuery(query.getSelectQuery());
			for (Map.Entry<String, Object> param : query.getParamMap().entrySet()) {
				listQuery.setParameter(param.getKey(), param.getValue());
			}
			if (query.getOffSet() != 0) {
				listQuery.setFirstResult(query.getOffSet());
			}
			if (query.getLimit() != 0) {
				listQuery.setMaxResults(query.getLimit());
			}

			ret = listQuery.getResultList();

			manager.flush();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			manager.close();
		}
		return ret;
	}

	public <T extends Entidade> QueryReturn<T> query(Query<T> query) {
		QueryReturn<T> ret = query.getQueryReturn();

		EntityTransaction transaction = openTransaction();
		EntityManager manager = getManager();
		try {

			TypedQuery<T> typedQuery = manager.createQuery(query.getSelectQuery(), query.getEntidadeClass());

			for (Map.Entry<String, Object> param : query.getParamMap().entrySet()) {
				typedQuery.setParameter(param.getKey(), param.getValue());
			}
			if (query.getOffSet() != 0) {
				typedQuery.setFirstResult(query.getOffSet());
			}
			if (query.getLimit() != 0) {
				typedQuery.setMaxResults(query.getLimit());
			}
			ret.setListaRetorno(typedQuery.getResultList());

			manager.flush();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			manager.close();
		}

		return ret;
	}

	public <T extends Entidade> List<T> persist(List<T> objList) {

		
		for (T t : objList) {
			persist(t);
		}
//		EntityTransaction transaction = openTransaction();
//		EntityManager manager = getManager();
//		try {
//
//			for (T obj : objList) {
//
//				if (obj.isNew()) {
//					manager.persist(obj);
//
//				} else {
//					manager.merge(obj);
//				}
//
//			}
//
//			manager.flush();
//			transaction.commit();
//		} catch (Exception e) {
//			if (transaction != null) {
//				transaction.rollback();
//			}
//		} finally {
//			manager.close();
//		}
		return objList;
	}

}
