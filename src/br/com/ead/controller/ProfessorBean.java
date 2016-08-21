package br.com.ead.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.ead.dao.ProfessorDao;
import br.com.ead.dao.RoleDao;
import br.com.ead.model.Professor;
import br.com.ead.model.Role;
import br.com.ead.util.FacesUtils;
import br.com.ead.util.Util;

@ManagedBean
public class ProfessorBean {
	private Professor professor = new Professor();
	private List<Professor> professores = new ArrayList<Professor>();
	
	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{professorDao}")
	private ProfessorDao professorDao;
	@ManagedProperty("#{roleDao}")
	private RoleDao roleDao;
	
	public void lista() {
		
		professores = new ArrayList<Professor>();
		if(professor != null && !Util.isNullOrEmpty(professor.getNome())){
			professores = professorDao.pesquisarProfessorPorNome(professor.getNome());
		} else {
			professores = professorDao.listAll();
		}
		
		professor = new Professor();
		setState(ESTADO_DE_PESQUISA);
	}
	
	public void preparaParaAdicionar() {
		professor = new Professor();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}
	
	public void adiciona() {
		if(professorInvalido())
			return;
			
		preencherUsuario();
		professorDao.save(professor);
		facesUtils.adicionaMensagemDeInformacao("Professor adicionado com sucesso!");
		lista();
	}

	private void preencherUsuario() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleDao.load("PROFESSOR"));
		professor.preencherUsuario(roles);
	}

	private boolean professorInvalido() {
		if(Util.isNullOrEmpty(professor.getNome())){
			facesUtils.adicionaMensagemDeErro("Nome Inválido!");
			return true;
		} else if(Util.isNullOrEmpty(professor.getUsuario().getUsername())){
			facesUtils.adicionaMensagemDeErro("Login Inválido!");
			return true;
		}
		return false;
	}

	public void preparaParaAlterar(Professor professor) {
		this.professor = professorDao.load(professor.getId());
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_EDICAO);
	}

	public void altera() {		
		if(professorInvalido())
			return; 
		
		professorDao.update(professor);
		facesUtils.adicionaMensagemDeInformacao("Professor atualizado com sucesso!");
		lista();
	}
	
	public void voltar() {
		this.professor = new Professor();
		facesUtils.cleanSubmittedValues(form);
		lista();
	}

	public boolean isAdicionando() {
		return ESTADO_DE_NOVO.equals(state);
	}

	public boolean isEditando() {
		return ESTADO_DE_EDICAO.equals(state);
	}

	public boolean isPesquisando() {
		return ESTADO_DE_PESQUISA.equals(state);
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UIForm getForm() {
		return form;
	}

	public void setForm(UIForm form) {
		this.form = form;
	}

	public FacesUtils getFacesUtils() {
		return facesUtils;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

	public ProfessorDao getProfessorDao() {
		return professorDao;
	}

	public void setProfessorDao(ProfessorDao professorDao) {
		this.professorDao = professorDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
}
