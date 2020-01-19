package team.bebox.pagamento;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import team.bebox.user.Usuario;
import team.bebox.view.View;

@Entity
@Table(name = "pagamento")
public class Pagamento {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@JsonView(View.Pagamento.class)
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column
	private Date dia;
	
	@JsonView(View.Pagamento.class)
	@Column
	private String formaPagamento;
	
	@JsonView(View.Pagamento.class)
	@Column
	private Double valor;
	
	@ManyToOne
	private Usuario usuario;

	public Pagamento() {
		super();
	}

	public Pagamento(Date dia, String forma_pagamento, Double valor, Usuario usuario) {
		super();
		this.dia = dia;
		this.formaPagamento = forma_pagamento;
		this.valor = valor;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public String getForma_pagamento() {
		return formaPagamento;
	}

	public void setForma_pagamento(String forma_pagamento) {
		this.formaPagamento = forma_pagamento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}