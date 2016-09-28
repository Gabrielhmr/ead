package br.com.ead.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ead.model.Horario;


@Repository("horarioDao")
@Transactional
public class HorarioDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Horario horario){
		entityManager.persist(horario);
	}
	
	public Horario update(Horario horario){
		return entityManager.merge(horario);
	}
	
	public void delete(Horario horario){
		entityManager.remove(entityManager.merge(horario)); 
	}
	
	public List<Horario> listAll(){
		return entityManager.createQuery("from Horario where 1 = 1 order by nome ", Horario.class).getResultList();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Horario load(Long id) {
		return entityManager.find(Horario.class, id);
	}
	
	public Criteria getCriteria(){
		Session session = (Session) entityManager.getDelegate();
		return session.createCriteria(Horario.class);
	}
	

}
