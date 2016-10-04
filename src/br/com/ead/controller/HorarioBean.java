package br.com.ead.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIForm;

import br.com.ead.dao.HorarioDao;

import br.com.ead.model.Horario;
import br.com.ead.util.FacesUtils;


@ManagedBean
public class HorarioBean {
	private Horario horario = new Horario();
	private List<Horario> horarios = new ArrayList<Horario>();
	
	private UIForm form;
	
	@ManagedProperty("#{facesUtils}")
	private FacesUtils facesUtils;
	@ManagedProperty("#{horarioDao}")
	private HorarioDao horarioDao;
	
public void lista() {
		horarios = horarioDao.listAll();
		horario = new Horario();
	}



}
