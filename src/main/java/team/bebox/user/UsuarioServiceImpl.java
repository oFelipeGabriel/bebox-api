package team.bebox.user;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.bebox.user.UsuarioRepository;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;


	@Override
	public Usuario salvar(Usuario usuario) {

//		Caixa caixa = new Caixa();
//
//		caixa.setValor(1000000000);
//		caixa.setEmpresa_id(empresa);
//
//		caixaRepository.save(caixa);

		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario excluir(Usuario empresa) {
		usuarioRepository.delete(empresa);
		return empresa;
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

//	public Empresa buscaPorTurno(Long turnoId) {
//		Empresa empresa = empresaRepository.findEmpresaByAdminTurnoId(turnoId);
//		return empresa;
//	}


	public Collection<Usuario> todos() {
		Collection<Usuario> usuarios = usuarioRepository.findAllByOrderByDataVencimento();
		return usuarios;
	}
	
	public Collection<Usuario> todosUsuarios(){
		Collection<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios;
	}
	public Collection<Usuario> todosAdmins(){
		Collection<Usuario> usuarios = usuarioRepository.findByAutorizacoes_Nome("ROLE_ADMIN");
		return usuarios;
	}


	@Override
	public Collection<Usuario> getUsuarios(){
		Collection<Usuario> usuarios = usuarioRepository.findAll();
    	return usuarios;
	}

}
