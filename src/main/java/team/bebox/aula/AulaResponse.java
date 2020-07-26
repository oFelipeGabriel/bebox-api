package team.bebox.aula;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import team.bebox.user.Usuario;


public class AulaResponse {
	Collection<Aula> aulasCollection;
	String message;
	String classe;
	String checked;
	ArrayList<AulaResumo> aulas = new ArrayList<>();
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@SuppressWarnings("deprecation")
	public AulaResponse(Usuario aluno, Collection<Aula> aulasColl) {
		super();
		String inputFormat = "yyyy-MM-dd HH:mm";
		SimpleDateFormat hourFormat = new SimpleDateFormat(inputFormat, new Locale("pt", "BR"));		
		
		this.checked = aluno.getAulaChecked();		
		
		aulas = new ArrayList<>();
		if(aluno.getAulaChecked()!=null) {
			if(aulasColl!=null && aulasColl.toArray().length>0) {
				Aula a = aulasColl.iterator().next();
				Date horaLimite = null;
				try {
					horaLimite = hourFormat.parse(a.getDia()+" "+a.getHora());
					horaLimite.setMinutes(horaLimite.getMinutes()-30);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
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
				if(horaLimite != null) {
					ar.setHoraLimite(horaLimite.getTime());
				}				
				aulas.add(ar);
			}
			
		}
		if(aulasColl.isEmpty()==false && aluno.getAulaChecked()==null) {
			
			for(Aula a : aulasColl) {
				ArrayList<Integer> idAlunos = new ArrayList<>();
				for(Usuario al : a.getAlunos()) {
					idAlunos.add(al.getId());
				}
				Date horaLimite = null;
				try {
					horaLimite = hourFormat.parse(a.getDia()+" "+a.getHora());
					horaLimite.setMinutes(horaLimite.getMinutes()-30);
				} catch (ParseException e) {
					e.printStackTrace();
				}				
				AulaResumo ar = new AulaResumo(
						a.getId(), 
						a.getDia(), 
						a.getHora(),
						a.getQuantidade(),
						a.getChecked(),
						idAlunos
						);
				if(horaLimite!=null) {
					ar.setHoraLimite(horaLimite.getTime());
				}				
				aulas.add(ar);
			}
		}
		this.comparaDatas(aluno.getDataVencimento());
	}
	
	


	public AulaResponse() {
		super();
		this.comparaDatas(new Date());
	}



	@SuppressWarnings("deprecation")
	private void comparaDatas(Date vencimento) {
		Calendar dataAtual = Calendar.getInstance();
		vencimento.setHours(vencimento.getHours()+3);
		Date venc = new Date(vencimento.getTime());
		dataAtual.add(Calendar.DAY_OF_MONTH, -1);
		Date anteontem = new Date();
		anteontem.setDate(anteontem.getDate()-2);		

		
		Date ontem = new Date();
		ontem.setDate(anteontem.getDate()+1);
		if(venc.before(anteontem)) {
			this.classe = "negative";
			this.message = "Você possui uma mensalidade pendente. Procure a administração Bebox para mais informações";
			aulas = new ArrayList<>();
			
		}
		Date dAtual = new Date();
		if(dAtual.getYear()==venc.getYear() &&
				dAtual.getMonth()==venc.getMonth()){
			if(dAtual.getDate()-1 == venc.getDate()) {
				this.classe = "negative";
				this.message = "Você possui uma mensalidade pendente.";
			}
			if(dAtual.getDate()==venc.getDate()) {
				this.classe = "warning";
				this.message = "Sua mensalidade vence hoje.";
			}if(dAtual.getDate()+1 == venc.getDate()) {
				this.classe = "info";
				this.message = "Sua mensalidade vence amanhã.";
			}
			
		}
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
		private Long horaLimite;
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
		public Long getHoraLimite() {
			return horaLimite;
		}
		public void setHoraLimite(Long horaLimite) {
			this.horaLimite = horaLimite;
		}
		
	}
	
}

