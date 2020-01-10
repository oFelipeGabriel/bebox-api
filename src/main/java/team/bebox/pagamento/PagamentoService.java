package team.bebox.pagamento;

import java.util.Collection;
import java.util.List;

import team.bebox.user.Usuario;

public interface PagamentoService {
	public Pagamento salvar(Pagamento pagamento);
	public Pagamento excluir(Pagamento pagamento);
	public Pagamento editar(Pagamento pagamento);
	public Collection<Pagamento> getPagamentos();
	public Pagamento buscarPorId(Integer id);
	public List<Pagamento> buscaPorUsuario(Usuario u);

}
