package br.com.ead.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ead.dao.RoleDao;
import br.com.ead.dao.UsuarioDao;
import br.com.ead.model.Role;
import br.com.ead.model.Usuario;

@Service("bootstrap")
public class Bootstrap {

	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private RoleDao perfilDao;

	public void setup() {

		carregaUsuario();

	}

	private void carregaUsuario() {	

		Role user = perfilDao.load("USUARIO");
		if (user == null) {
			LogUtil.logInfo("Criando perfil USUÁRIO");
			user = new Role();
			user.setName("USUARIO");
			perfilDao.save(user);
			LogUtil.logInfo("Perfil USUÁRIO criado");
		}

		Role especial = perfilDao.load("PROFESSOR");
		if (especial == null) {
			LogUtil.logInfo("Criando perfil PROFESSOR");
			especial = new Role();
			especial.setName("PROFESSOR");
			perfilDao.save(especial);
			LogUtil.logInfo("Perfil PROFESSOR criado");
		}

		Role admin = perfilDao.load("ADMINISTRADOR");
		if (admin == null) {
			LogUtil.logInfo("Criando perfil AMINISTRADOR");
			admin = new Role();
			admin.setName("ADMINISTRADOR");
			perfilDao.save(admin);
			LogUtil.logInfo("Perfil AMINISTRADOR criado");
		}

		List<Role> roles = new ArrayList<Role>();
		roles.add(admin);

		LogUtil.logInfo("Criando usuário SUPER USER");
		Usuario usuario = new Usuario();
		usuario.setNome("USUÁRIO SUPER USER");
		usuario.setUsername("root");
		usuario.setPassword("sysadmin");
		usuario.setEnabled(true);
		usuario.setMainUser(true);
		usuario.setRoles(roles);

		usuarioDao.save(usuario);
		LogUtil.logInfo("Usuário SUPER ADMIN criado");
		
		LogUtil.logInfo("Criando usuário ADMIN");
		usuario = new Usuario();
		usuario.setNome("USUÁRIO ADMIN");
		usuario.setUsername("admin");
		usuario.setPassword("admin");
		usuario.setEnabled(true);
		usuario.setRoles(roles);

		usuarioDao.save(usuario);
		LogUtil.logInfo("Usuário ADMIN criado");
	}

}
