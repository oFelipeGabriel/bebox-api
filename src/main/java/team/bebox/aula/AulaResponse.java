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
	String checked;
	ArrayList<AulaResumo> aulas = new ArrayList<>();
	
	public AulaResponse(Usuario aluno, Collection<Aula> aulasColl) {
		super();
		this.aulasCollection = aulasColl;
		Date dataCheck = new Date();
		if(aluno.getAulaChecked()!=null && aluno.getAulaChecked()!="") {
			this.checked = aluno.getAulaChecked();
			String[] dataCheckStr = aluno.getAulaChecked().split("=")[0].split("-");
			String[] horasCheckStr = aluno.getAulaChecked().split("=")[1].split(":");
			dataCheck = new Date(Integer.parseInt(dataCheckStr[0]), 
					Integer.parseInt(dataCheckStr[1])-1, 
					Integer.parseInt(dataCheckStr[2]),
					Integer.parseInt(horasCheckStr[0]),
					Integer.parseInt(horasCheckStr[1]));
		}
		this.checked = aluno.getAulaChecked();
		aulas = new ArrayList<>();
		if(aulasColl.isEmpty()==false) {
			for(Aula a : aulasColl) {
				ArrayList<Integer> idAlunos = new ArrayList<>();
				for(Usuario al : a.getAlunos()) {
					idAlunos.add(al.getId());
				}
				
				AulaResumo ar = new AulaResumo(
						a.getId(), 
						a.getDia(), 
						a.getHora(),
						a.getQuantidade(),
						a.getChecked(),
						idAlunos
						);
//				if(this.checked!="") {
//					this.comparaDataChecada(dataCheck, a.getDia(), a.getHora());
//				}
				aulas.add(ar);
			}
		}
		
		this.comparaDatas(aluno.getDataVencimento());
	}
	
	
	
	private boolean comparaDataChecada(Date dataCheck, java.sql.Date dia, String hora) {
		// TODO Auto-generated method stub
		return false;
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

	public String getChecked() {
		return checked;
	}



	public void setChecked(String checked) {
		this.checked = checked;
	}

	class AulaResumo{
		private Integer id;
		private Date dia;
		private String hora;
		private Integer quantidade;
		private Integer checked;
		private List<Integer> alunos;
		private Boolean isChecked;
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
		public Boolean getIsChecked() {
			return isChecked;
		}
		public void setIsChecked(Boolean isChecked) {
			this.isChecked = isChecked;
		}
		
	}
	
}

