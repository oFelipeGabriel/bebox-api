package team.bebox.user;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import team.bebox.autorizacao.Autorizacao;
import team.bebox.view.View;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Transactional
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@JsonView(View.UsuarioMinimo.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column
	private Integer id;

	@JsonView(View.UsuarioBase.class)
	@Column
	private String nome;

	@JsonView(View.UsuarioResumo.class)
	@Column
	private String email;

	@Column
	private String senha;

	@JsonView(View.UsuarioResumo.class)
	@JsonFormat(pattern="dd-MM-yyyy")
	@Column
	private Date dataDeCriacao;

	@JsonView(View.UsuarioResumo.class)
	@Column
	private String foto;
	
	@Column
	private Boolean is_admin;
	
	@Column
	private String endereco;
	
	@Column
	private String telefone;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column
	private Date data_nascimento;

    @Column(name = "cpf")
    private String cpf;
    
    @Column
    private Double valor_mensalidade;
    
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column
    private Date data_pagamento;
    
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column 
    private Date dataVencimento;
    
    @Column
    private Boolean status = true;
    
	@JsonView(View.UsuarioResumo.class)
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Autorizacao> autorizacoes;
	

	public Usuario() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(Date dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Boolean getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(Boolean is_admin) {
		this.is_admin = is_admin;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getValor_mensalidade() {
		return valor_mensalidade;
	}

	public void setValor_mensalidade(Double valor_mensalidade) {
		this.valor_mensalidade = valor_mensalidade;
	}

	public Date getData_pagamento() {
		return data_pagamento;
	}

	public void setData_pagamento(Date data_pagamento) {
		this.data_pagamento = data_pagamento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date data_vencimento) {
		this.dataVencimento = data_vencimento;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(List<Autorizacao> lista) {
		this.autorizacoes = lista;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return senha;
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return this.cpf;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {		
		return this.autorizacoes;
	}

}
