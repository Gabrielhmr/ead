package br.com.ead.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.ead.dao.AlunoDao;
import br.com.ead.dao.ProfessorDao;
import br.com.ead.dao.TurmaDao;
import br.com.ead.model.Aluno;
import br.com.ead.model.Professor;
import br.com.ead.model.Turma;
import br.com.ead.model.Usuario;
import br.com.ead.util.FacesUtils;

@ManagedBean
public class HomeBean {
	private Professor professor = new Professor();
	private Aluno aluno = new Aluno();
	
	private List<Turma> turmas = new ArrayList<Turma>();
	private List<Aluno> alunos = new ArrayList<Aluno>();
	
	private UIForm form;

	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{alunoDao}")
	private AlunoDao alunoDao;
	@ManagedProperty("#{professorDao}")
	private ProfessorDao professorDao;
	@ManagedProperty("#{turmaDao}")
	private TurmaDao turmaDao;
	@ManagedProperty("#{usuarioWeb}")
    private UsuarioWeb usuarioWeb;
	
	@PostConstruct
	public void init(){
		if(usuarioWeb.isUsuarioAluno())
			processarAluno(usuarioWeb.getUsuario());
		else if(usuarioWeb.isUsuarioProfessor())
			processarProfessor(usuarioWeb.getUsuario());
		else
			processarAdmin();
		
	}
	
	private void processarAdmin() {
		turmas = turmaDao.listAll();
	}

	private void processarAluno(Usuario usuario) {
		aluno = alunoDao.obterAlunoPeloLogin(usuario);
		turmas = aluno.getTurmas();
	}
	
	private void processarProfessor(Usuario usuario) {
		professor = professorDao.obterProfessorPeloLogin(usuario);
		turmas = turmaDao.pesquisarTurmasPorProfessor(professor);
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
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

	public ProfessorDao getProfessorDao() {
		return professorDao;
	}

	public void setProfessorDao(ProfessorDao professorDao) {
		this.professorDao = professorDao;
	}

	public TurmaDao getTurmaDao() {
		return turmaDao;
	}

	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public UsuarioWeb getUsuarioWeb() {
		return usuarioWeb;
	}

	public void setUsuarioWeb(UsuarioWeb usuarioWeb) {
		this.usuarioWeb = usuarioWeb;
	}
	
}
