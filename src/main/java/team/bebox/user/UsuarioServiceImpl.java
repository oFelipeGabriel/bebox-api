package team.bebox.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.bebox.aula.Aula;
import team.bebox.aula.AulaServiceImpl;
import team.bebox.user.UsuarioRepository;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AulaServiceImpl aulaService;


	@Override
	public Usuario salvar(Usuario usuario) {

		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario excluir(Usuario usuario) {
		usuario.setStatus(false);
		List<Aula> aulas = aulaService.buscaPorAlunoId(usuario.getId());
		Date hoje = new Date();
		for(Aula a : aulas) {
			if(a.getDia().compareTo(hoje) < 0) {
				a.getAlunos().remove(usuario);
				aulaService.salvar(a);
			}			
		}
		usuarioRepository.save(usuario);
		return usuario;
	}
	
	@Override
	public Usuario editar(Usuario usuario) {
		if(usuarioRepository.findById(usuario.getId()) != null){
			return usuarioRepository.save(usuario);
		}
		return null;
	}

	@Override
	public Usuario buscarPorId(Integer id) {
		return usuarioRepository.findById(id).get();
	}


	public Collection<Usuario> todos() {
		Collection<Usuario> usuarios = usuarioRepository.findByStatus(true);
		return usuarios;
	}
	
	public Collection<Usuario> todosUsuarios(){
		Collection<Usuario> usuarios = usuarioRepository.findByStatus(true);
		return usuarios;
	}
	public Collection<Usuario> todosAdmins(){
		Collection<Usuario> usuarios = usuarioRepository.findByAutorizacoes_Nome("ROLE_ADMIN");
		return usuarios;
	}

	public void checkAulaToUser(Usuario user, Aula aula) {
		String diaHora = aula.getDia().toString()+"="+aula.getHora();
		user.setAulaChecked(diaHora);
		usuarioRepository.save(user);
	}
	public void uncheckAulaToUser(Usuario user) {
		user.setAulaChecked(null);
		usuarioRepository.save(user);
	}
	
	@Override
	public Collection<Usuario> getUsuarios(){
		Collection<Usuario> usuarios = usuarioRepository.findAll();
    	return usuarios;
	}

}
