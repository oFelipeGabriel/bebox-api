package team.bebox.user;

public interface UsuarioService {
	public Usuario salvar(Usuario usuario);
	public Usuario excluir(Usuario usuario);
	public Usuario editar(Usuario usuario);
	public UsuariosResponse getUsuariosPaginated(int pageNum, int pageSize, String ativo);
	public Usuario buscarPorId(Integer id);
	public UsuariosResponse buscaPorNome(String nome, String ativo);
	public Boolean atualizaStatus(int id, Boolean status);
}
