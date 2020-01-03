package team.bebox.user;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
	public Usuario salvar(Usuario usuario);
	public Usuario excluir(Usuario usuario);
	public Usuario editar(Usuario usuario);
	public Collection<Usuario> getUsuarios();
	public Usuario buscarPorId(Integer id);
}
