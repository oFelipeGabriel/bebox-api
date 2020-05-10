package team.bebox.aula;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import team.bebox.user.Usuario;


public class AulaResponse {
	Collection<Aula> aulasCollection;
	String message;
	String classe;
	ArrayList<AulaResumo> aulas = new ArrayList<>();
	
	public AulaResponse(Usuario aluno, Collection<Aula> aulasColl) {
		super();
		this.aulasCollection = aulasColl;
		aulas = new ArrayList<>();
		for(Aula a : aulasColl) {
			ArrayList<Integer> idAlunos = new ArrayList<>();
			for(Usuario al : a.getAlunos()) {
				idAlunos.add(al.getId());
			}
			//Integer id, Date dia, String hora, Integer quantidade, Integer checked,
			//List<Integer> alunos
			AulaResumo ar = new AulaResumo(
					a.getId(), 
					a.getDia(), 
					a.getHora(),
					a.getQuantidade(),
					a.getChecked(),
					idAlunos
					);
			aulas.add(ar);
		}
		this.comparaDatas(aluno.getDataVencimento());
	}
	
	
	
	public AulaResponse() {
		super();
		this.comparaDatas(new Date());
	}



	private void comparaDatas(Date vencimento) {
		Calendar dataAtual = Calendar.getInstance();
		vencimento.setHours(vencimento.getHours()+3);
		dataAtual.add(Calendar.DAY_OF_MONTH, -1);
		Date anteontem = new Date();
		anteontem.setDate(anteontem.getDate()-2);		

		Date ontem = new Date();
		ontem.setDate(anteontem.getDate()+1);
		System.out.println(anteontem);
		System.out.println(vencimento);
		System.out.println(ontem);
		if(vencimento.getDate() <= anteontem.getDate() && 
			vencimento.getMonth() <= anteontem.getMonth() && 
			vencimento.getYear() <= anteontem.getYear()) {
			this.classe = "negative";
			this.message = "Você possui uma mensalidade pendente. Procure a administração Bebox para mais informações";
			aulas = new ArrayList<>();
			
		}
		else if(vencimento.getDate() <= ontem.getDate() && 
			vencimento.getMonth() <= ontem.getMonth() && 
			vencimento.getYear() <= ontem.getYear()) {
			this.classe = "negative";
			this.message = "Você possui uma mensalidade pendente.";
		}
		Date dAtual = new Date();
		
		if(dAtual.getDate() == vencimento.getDate() && 
			dAtual.getMonth() == vencimento.getMonth() && 
			dAtual.getYear() == vencimento.getYear()) {
			this.classe = "warning";
			this.message = "Sua mensalidade vence hoje.";
		}
		
		Date d = new Date();
		d.setDate(d.getDate()+1);
		if(d.getDate() == vencimento.getDate() && 
			d.getMonth() == vencimento.getMonth() && 
			d.getYear() == vencimento.getYear()) {
			this.classe = "info";
			this.message = "Sua mensalidade vence amanhã.";
		}
		
//		if(dataAtual.getTime().compareTo(vencimento)<0) {
//			this.classe = "positive";
//		}
		
	}





	public ArrayList<AulaResumo> getAulas() {
		return this.aulas;
	}



	public void setAulas(Collection<Aula> aulas) {
		this.aulasCollection = aulas;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getClasse() {
		return classe;
	}



	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public void setAulas(ArrayList<AulaResumo> aulas) {
		this.aulas = aulas;
	}

	class AulaResumo{
		private Integer id;
		private Date dia;
		private String hora;
		private Integer quantidade;
		private Integer checked;
		private List<Integer> alunos;
		public AulaResumo(Integer id, Date dia, String hora, Integer quantidade, Integer checked,
				List<Integer> alunos) {
			super();
			this.id = id;
			this.dia = dia;
			this.hora = hora;
			this.quantidade = quantidade;
			this.checked = checked;
			this.alunos = alunos;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getDia() {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return simpleDateFormat.format(dia);
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
		public List<Integer> getAlunos() {
			return alunos;
		}
		public void setAlunos(List<Integer> alunos) {
			this.alunos = alunos;
		}
		
		
	}
	
}

