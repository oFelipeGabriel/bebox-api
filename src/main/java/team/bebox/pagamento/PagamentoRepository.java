package team.bebox.pagamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team.bebox.user.Usuario;


public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

	public List<Pagamento> findByUsuario(Usuario u);
	public List<Pagamento> findByFormaPagamentoAndUsuario(String forma_pagamento, Usuario u);

}
