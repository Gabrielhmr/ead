package br.com.ead.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.ead.model.Professor;
import br.com.ead.model.Usuario;

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
		return entityManager.createQuery("from Professor where 1 = 1 and habilitado != false order by nome ", Professor.class).getResultList();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Professor load(Long id) {
		return entityManager.find(Professor.class, id);
	}
	
	public List<Professor> pesquisarProfessorPorNome(String nome) {
		return getCriteria().add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE)).list();
	}
	
	public Professor obterProfessorPorNome(String nome) {
		List<Professor> lista = getCriteria().add(Restrictions.eq("nome", nome)).list();
		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}
		return null; 
	}
	
	public Professor obterProfessorPeloLogin(Usuario usuario) {
		return (Professor) getCriteria().add(Restrictions.eq("usuario", usuario)).uniqueResult();
	}
	
	public Professor pesquisarProfessorPorCartao(String cartao){
		String jpql = "SELECT a FROM Professor a where a.cartao = :cartao ";
		TypedQuery<Professor> query = entityManager.createQuery(jpql, Professor.class);
		query.setParameter("cartao", cartao);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Criteria getCriteria(){
		Session session = (Session) entityManager.getDelegate();
		return session.createCriteria(Professor.class);
	}


	
	
}
