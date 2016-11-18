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
import br.com.ead.util.FacesUtils;
import br.com.ead.util.Util;

@ManagedBean
public class TurmaBean {
	private Turma turma = new Turma();
	private List<Turma> turmas = new ArrayList<Turma>();
	private long idProfessor;
	private List<Professor> professores = new ArrayList<Professor>();
	private Long[] selectedAlunos;
	private List<Aluno> alunos = new ArrayList<Aluno>();
	private List<Aluno> alunosBeforeChange = new ArrayList<Aluno>();
	private Aluno aluno = new Aluno();

	private static final String ESTADO_DE_NOVO = "_novo";
	private static final String ESTADO_DE_EDICAO = "_edicao";
	private static final String ESTADO_DE_PESQUISA = "_pesquisa";

	private String state = ESTADO_DE_PESQUISA;

	private UIForm form;

	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{turmaDao}")
	private TurmaDao turmaDao;
	@ManagedProperty("#{professorDao}")
	private ProfessorDao professorDao;
	@ManagedProperty("#{alunoDao}")
	private AlunoDao alunoDao;
	
	@PostConstruct
	public void init(){
		turmas = new ArrayList<Turma>();
		turmas = turmaDao.listAll();
	}

	public void lista() {

		turmas = new ArrayList<Turma>();
		if (turma.isHabilitado() == true && !Util.isNullOrEmpty(turma.getDescricao())) {
			turmas = turmaDao.pesquisarTurmaPorDescricao(turma.getDescricao());
		} else {
			turmas = turmaDao.listAll();
		}

		turma = new Turma();
		setState(ESTADO_DE_PESQUISA);
	}

	public void preparaParaAdicionar() {
		turma = new Turma();
		alunos = new ArrayList<Aluno>();
		turma.setAlunos(alunos);
		professores = professorDao.listAll();
		alunos = alunoDao.listAll();
		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_NOVO);
	}

	public void adiciona() {
		if (turmaInvalida())
			return;

		adicionaProfessor();
		adicionaAlunos();
		
		turmaDao.save(turma);
		facesUtils.adicionaMensagemDeInformacao("Turma adicionada com sucesso!");
		lista();
	}

	private void adicionaProfessor() {
		turma.setProfessor(professorDao.load(idProfessor));
	}

	private void adicionaAlunos() {		
		if(selectedAlunos != null){
			alunosBeforeChange = alunoDao.pesquisarAlunosPorId(selectedAlunos);
		}
		 
		preencherListaDeIds(turma.getAlunos());
		alunos = alunoDao.pesquisarAlunosPorId(selectedAlunos);
		for (Aluno aluno : alunos) {
			aluno.setTurma(turma);
		}

	}
	
	private void removerAlunos() {
		alunosBeforeChange.removeAll(alunos);
		for (Aluno aluno : alunosBeforeChange) {
			aluno.setTurma(null);
		}
		
	}

	private boolean turmaInvalida() {
		if (Util.isNullOrEmpty(turma.getDescricao())) {
			facesUtils.adicionaMensagemDeErro("Descrição Inválida!");
			return true;
		} else if (Util.isNullOrEmpty(turma.getTurno())) {
			facesUtils.adicionaMensagemDeErro("Selecione o turno");
			return true;
		} else if(turma.getAlunos().size() == 0){
			facesUtils.adicionaMensagemDeErro("Adicione pelo menos um aluno");
			return true;
		 }
		return false;
	}

	public void preparaParaAlterar(Turma turma) {
		idProfessor = 0;
		selectedAlunos = null;
		professores = professorDao.listAll();
		alunos = alunoDao.listAll();
		this.turma = turmaDao.load(turma.getId());

		if (this.turma.getProfessor() != null)
			idProfessor = this.turma.getProfessor().getId();

		if (this.turma.getAlunos() != null && this.turma.getAlunos().size() > 0)
			preencherListaDeIds(this.turma.getAlunos());

		facesUtils.cleanSubmittedValues(form);
		setState(ESTADO_DE_EDICAO);
	}

	private void preencherListaDeIds(List<Aluno> alunos) {
		selectedAlunos = new Long[alunos.size()];
		for (int i = 0; i < selectedAlunos.length; i++) {
			selectedAlunos[i] = alunos.get(i).getId();
		}
	}

	public void altera() {

		if (turmaInvalida())
			return;
		adicionaProfessor();
		adicionaAlunos();
		removerAlunos();
		turmaDao.update(turma);
		facesUtils.adicionaMensagemDeInformacao("Turma atualizada com sucesso!");
		lista();
	}

	public List<Aluno> complete(String nome) {
		System.out.println(nome);

		List<Aluno> queryResult = new ArrayList<Aluno>();

		if (alunos == null) {
			alunos = alunoDao.listAll();
		}

		alunos.removeAll(turma.getAlunos());

		for (Aluno aluno : alunos) {
			if (aluno.getNome().toLowerCase().contains(nome.toLowerCase())) {
				queryResult.add(aluno);
			}
		}

		return queryResult;
	}

	public void removerAluno(Aluno aluno) {
		turma.getAlunos().remove(aluno); 
		facesUtils.adicionaMensagemDeInformacao("Aluno removido");
	}

	public void addAluno() {
		List<Long> turmaAlunosId = new ArrayList<Long>();
		for (Aluno turmaAlunos : turma.getAlunos()) {
			turmaAlunosId.add(turmaAlunos.getId());
		}
		if (turmaAlunosId.contains(aluno.getId())) {
			facesUtils.adicionaMensagemDeInformacao("Aluno ja foi inserido");
		} else {
			turma.getAlunos().add(aluno);
			facesUtils.adicionaMensagemDeInformacao("Aluno adicionado com sucesso");
		}

	}
	
	public void remove(Turma turma) {
		turma = turmaDao.load(turma.getId());
		turma.setHabilitado(false);
		for (Aluno aluno : turma.getAlunos()) {
			aluno.setTurma(null);
		}
		turmaDao.update(turma);
		facesUtils.adicionaMensagemDeInformacao("Turma removida com sucesso!");
		lista();
	}

	public void voltar() {
		this.turma = new Turma();
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

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public long getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(long idProfessor) {
		this.idProfessor = idProfessor;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public Long[] getSelectedAlunos() {
		return selectedAlunos;
	}

	public void setSelectedAlunos(Long[] selectedAlunos) {
		this.selectedAlunos = selectedAlunos;
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

	public TurmaDao getTurmaDao() {
		return turmaDao;
	}

	public void setTurmaDao(TurmaDao turmaDao) {
		this.turmaDao = turmaDao;
	}

	public ProfessorDao getProfessorDao() {
		return professorDao;
	}

	public void setProfessorDao(ProfessorDao professorDao) {
		this.professorDao = professorDao;
	}

	public AlunoDao getAlunoDao() {
		return alunoDao;
	}

	public void setAlunoDao(AlunoDao alunoDao) {
		this.alunoDao = alunoDao;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
