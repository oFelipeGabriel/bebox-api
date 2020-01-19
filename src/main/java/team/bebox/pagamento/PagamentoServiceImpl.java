package team.bebox.pagamento;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.bebox.user.Usuario;


@Service
@Transactional
public class PagamentoServiceImpl implements PagamentoService{
	
	@Autowired
	private PagamentoRepository pagamentoRepo;

	@Override
	public Pagamento salvar(Pagamento pagamento) {
		return pagamentoRepo.save(pagamento);
	}

	@Override
	public Pagamento excluir(Pagamento pagamento) {
		pagamentoRepo.delete(pagamento);
		return null;
	}

	@Override
	public Pagamento editar(Pagamento pagamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Pagamento> getPagamentos() {
		return pagamentoRepo.findAll();
	}

	@Override
	public Pagamento buscarPorId(Integer id) {
		return pagamentoRepo.findById(id).get();
	}
	
	@Override
	public List<Pagamento> buscaPorUsuario(Usuario u){
		return pagamentoRepo.findByUsuario(u);
	}
	
	@Override
	public List<Pagamento> buscaPorForma(String forma, Usuario u){
		return pagamentoRepo.findByFormaPagamentoAndUsuario(forma, u);
	}

}
