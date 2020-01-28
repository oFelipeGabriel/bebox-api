package team.bebox.aula;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import team.bebox.user.Usuario;
import team.bebox.view.View;

@Entity
@Table(name = "aula")
public class Aula {
	@JsonView(View.UsuarioBase.class)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column
	@JsonView(View.UsuarioBase.class)
	private Date dia;
	
	@Column
	@JsonView(View.UsuarioBase.class)
	private String hora;
	
	@Column
	@JsonView(View.UsuarioBase.class)
	private Integer quantidade;
	
	@Column
	@JsonView(View.UsuarioBase.class)
	private Integer checked;
	
	@JsonView(View.UsuarioBase.class)
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<Usuario> alunos;

	public Aula() {
		super();
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

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public List<Usuario> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Usuario> alunos) {
		this.alunos = alunos;
	}
	
	
	
}
