package br.com.ead.dao;

import java.util.ArrayList;
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

import br.com.ead.model.Aluno;
import br.com.ead.model.Usuario;

@Repository("alunoDao")
@Transactional
public class AlunoDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Aluno aluno){
		entityManager.persist(aluno);
	}
	
	public Aluno update(Aluno aluno){
		return entityManager.merge(aluno);
	}
	
	public void delete(Aluno aluno){
		entityManager.remove(entityManager.merge(aluno)); 
	}
	
	public List<Aluno> listAll(){
		return entityManager.createQuery("from Aluno where 1 = 1 order by nome ", Aluno.class).getResultList();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Aluno load(Long id) {
		return entityManager.find(Aluno.class, id);
	}
	
	public Aluno pesquisarAlunoPorMatricula(String matricula){
		String jpql = "SELECT a FROM Aluno a where a.matricula = :matricula ";
		TypedQuery<Aluno> query = entityManager.createQuery(jpql, Aluno.class);
		query.setParameter("matricula", matricula);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Aluno pesquisarAlunoPorCartao(String cartao){
		String jpql = "SELECT a FROM Aluno a where a.cartao = :cartao ";
		TypedQuery<Aluno> query = entityManager.createQuery(jpql, Aluno.class);
		query.setParameter("cartao", cartao);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Aluno> pesquisarAlunoPorNome(String nome) {
		return getCriteria().add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE)).list();
	}
	
	public List<Aluno> pesquisarAlunosPorId(Long[] ids) {
		return getCriteria().add(Restrictions.in("id", ids)).list();
	}
	
	public Aluno obterAlunoPeloLogin(Usuario usuario) {
		return (Aluno) getCriteria().add(Restrictions.eq("usuario", usuario)).uniqueResult();
	}
	public Criteria getCriteria(){
		Session session = (Session) entityManager.getDelegate();
		return session.createCriteria(Aluno.class);
	}


}
