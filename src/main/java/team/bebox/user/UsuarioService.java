package team.bebox.user;

public interface UsuarioService {
	public Usuario salvar(Usuario usuario);
	public Usuario excluir(Usuario usuario);
	public Usuario editar(Usuario usuario);
	public UsuariosResponse getUsuariosPaginated(int pageNum, int pageSize);
	public Usuario buscarPorId(Integer id);
}
