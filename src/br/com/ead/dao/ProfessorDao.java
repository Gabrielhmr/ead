package br.com.ead.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ead.model.Professor;

@Repository("professorDao")
@Transactional
public class ProfessorDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Professor professor){
		entityManager.persist(professor);
	}
	
	public Professor update(Professor professor){
		return entityManager.merge(professor);
	}
	
	public void delete(Professor professor){
		entityManager.remove(professor); 
	}
	
	public List<Professor> listAll(){
		return entityManager.createQuery("from Professor where 1 = 1 order by nome ", Professor.class).getResultList();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Professor load(Long id) {
		return entityManager.find(Professor.class, id);
	}
	
	public List<Professor> pesquisarProfessorPorNome(String nome) {
		return getCriteria().add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE)).list();
	}
	
	public Criteria getCriteria(){
		Session session = (Session) entityManager.getDelegate();
		return session.createCriteria(Professor.class);
	}

	
	
}
