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
import br.com.ead.model.Turma;

@Repository("turmaDao")
@Transactional
public class TurmaDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Turma turma){
		entityManager.persist(turma);
	}
	
	public Turma update(Turma turma){
		return entityManager.merge(turma);
	}
	
	public void delete(Turma turma){
		entityManager.remove(turma); 
	}
	
	public List<Turma> listAll(){
		return entityManager.createQuery("from Turma where 1 = 1 order by descricao ", Turma.class).getResultList();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Turma load(Long id) {
		return entityManager.find(Turma.class, id);
	}
	
	public List<Turma> pesquisarTurmaPorDescricao(String descricao) {
		return getCriteria().add(Restrictions.ilike("descricao", descricao, MatchMode.ANYWHERE)).list();
	}
	
	public List<Turma> pesquisarTurmasPorProfessor(Professor professor) {
		return getCriteria().add(Restrictions.eq("professor", professor)).list();
	}
	
	public Criteria getCriteria(){
		Session session = (Session) entityManager.getDelegate();
		return session.createCriteria(Turma.class);
	}


	
	
}
