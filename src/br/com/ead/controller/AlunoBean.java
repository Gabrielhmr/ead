package br.com.ead.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.ead.dao.AlunoDao;
import br.com.ead.dao.RoleDao;
import br.com.ead.model.Aluno;
import br.com.ead.model.Role;
import br.com.ead.util.FacesUtils;
import br.com.ead.util.Util;

@ManagedBean
public class AlunoBean {
	private Aluno aluno = new Aluno();
	private List<Aluno> alunos = new ArrayList<Aluno>();
	
	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{alunoDao}")
	private AlunoDao alunoDao;
	@ManagedProperty("#{roleDao}")
	private RoleDao roleDao;
	
	@PostConstruct
	public void init(){
		alunos = new ArrayList<Aluno>();
		alunos = alunoDao.listAll();
	}
	
	public void lista() {
		
		alunos = new ArrayList<Aluno>();
		if(aluno != null && !Util.isNullOrEmpty(aluno.getMatricula())){
			Aluno alunoRetorno = alunoDao.pesquisarAlunoPorMatricula(aluno.getMatricula());
			if(alunoRetorno != null)
				alunos.add(alunoRetorno);
			
		} else if(aluno != null && !Util.isNullOrEmpty(aluno.getNome())) {
			alunos = alunoDao.pesquisarAlunoPorNome(aluno.getNome());
		} else {
			alunos = alunoDao.listAll();
		}
		
		aluno = new Aluno();
		setState(ESTADO_DE_PESQUISA);
	}
	
	public void preparaParaAdicionar() {
		aluno = new Aluno();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}
	
	public void adiciona() {
		if(alunoInvalido())
			return;
		
		verificaAluno();	
		//preencherUsuario();
		alunoDao.save(aluno);
		facesUtils.adicionaMensagemDeInformacao("Aluno adicionado com sucesso!");
		lista();
	}
	
	private void verificaAluno() {
		Aluno alunoRetornoMatricula = alunoDao.pesquisarAlunoPorMatricula(aluno.getMatricula());
		Aluno alunoRetornoCartao = alunoDao.pesquisarAlunoPorCartao(aluno.getCartao());
		if(alunoRetornoMatricula != null){
			facesUtils.adicionaMensagemDeErro("Matricula já cadastrada!");
			return;
		}else if (alunoRetornoCartao != null) {
			facesUtils.adicionaMensagemDeErro("Cartao já cadastrado!");
			return;
		}
	}

//	private void preencherUsuario() {
//		List<Role> roles = new ArrayList<Role>();
//		roles.add(roleDao.load("USUARIO"));
//		aluno.preencherUsuario(roles);
//	}

	private boolean alunoInvalido() {
		if(Util.isNullOrEmpty(aluno.getMatricula())){
			facesUtils.adicionaMensagemDeErro("Matricula Inválida!");
			return true;
		} else if(Util.isNullOrEmpty(aluno.getNome())){
			facesUtils.adicionaMensagemDeErro("Nome Inválido!");
			return true;
		} else if(Util.isNullOrEmpty(aluno.getCartao())){
			facesUtils.adicionaMensagemDeErro("Cartao Inválido!");
			return true;
//		} else if(Util.isNullOrEmpty(aluno.getUsuario().getUsername())){
//			facesUtils.adicionaMensagemDeErro("Login Inválido!");
//			return true;
		}
		return false;
	}

	public void preparaParaAlterar(Aluno aluno) {
		this.aluno = alunoDao.load(aluno.getId());
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_EDICAO);
	}

	public void altera() {		
		if(alunoInvalido())
			return; 
		
		verificaAluno();
		alunoDao.update(aluno);
		facesUtils.adicionaMensagemDeInformacao("Aluno atualizado com sucesso!");
		lista();
	}
	
	public void remove() {
		aluno.setHabilitado(false);
		alunoDao.update(aluno);
		facesUtils.adicionaMensagemDeInformacao("Aluno removido com sucesso!");
		lista();
	}
	
	public void voltar() {
		this.aluno = new Aluno();
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

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
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

	public AlunoDao getAlunoDao() {
		return alunoDao;
	}

	public void setAlunoDao(AlunoDao alunoDao) {
		this.alunoDao = alunoDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
}
