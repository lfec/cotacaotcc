package com.lfec.controller;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.lfec.domain.Entidade;

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

	public <T extends Entidade> QueryReturn<T> query(Query<T> query){
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
}
