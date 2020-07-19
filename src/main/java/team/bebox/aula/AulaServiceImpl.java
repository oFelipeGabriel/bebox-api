package team.bebox.aula;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.bebox.user.Usuario;
import team.bebox.user.UsuarioServiceImpl;

@Service
@Transactional
public class AulaServiceImpl implements AulaService{

	@Autowired
	private AulaRepository aulaRepo;
	
	@Autowired
	private UsuarioServiceImpl alunoService;
	
	@Override
	public Aula salvar(Aula aula) {
		return aulaRepo.save(aula);
	}
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Collection<Aula> buscarTodas() {
		return null;
	}


	@SuppressWarnings("deprecation")
	@Override
	public AulaResponse buscarTodas(int idAluno){
		Date hoje = new Date();
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(tz);
		String inputFormat = "yyyy-MM-dd HH:mm";
		SimpleDateFormat dateHourFormat = new SimpleDateFormat(inputFormat, new Locale("pt", "BR"));
		Usuario aluno = alunoService.buscarPorId(idAluno);
		Boolean temCheckin = false;
		
		String diaCheck = "";
		String horaCheck = "";
		Collection<Aula> aulas = aulaRepo.findAllByOrderByDiaDesc();
//		Collection<Aula> aulas = (Collection<Aula>) aulaRepo
//				.findAll(Sort.by(Sort.Direction.ASC, "dia")
//						.and(Sort.by(Sort.Direction.ASC, "hora")));
		Collection<Aula> remover = new ArrayList<Aula>();
		if(aluno.getAulaChecked()!=null) {			
			String dataCheck[] = aluno.getAulaChecked().split(" ");
			diaCheck = dataCheck[0];
			horaCheck = dataCheck[1];
			try {
				hoje.before(sdformat.parse(diaCheck));
				temCheckin = true;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		for(Aula a: aulas) {
			Date horaLimite = null;
			try {
				horaLimite = dateHourFormat.parse(a.getDia()+" "+a.getHora());
				horaLimite.setMinutes(horaLimite.getMinutes()-30);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
//			if(temCheckin) {
//				try {
//					if(a.getDia().before(hoje)) {
//						remover.add(a);
//					}else if(a.getDia().compareTo(sdformat.parse(diaCheck)) != 0)  {
//						remover.add(a);
//					}else if(a.getDia().compareTo(sdformat.parse(diaCheck)) == 0 &&
//							(!a.getHora().equals(horaCheck))) {
//						remover.add(a);
//					}
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			}
			if(hoje.after(a.getDia())) {
				remover.add(a);
			}else if(hoje.compareTo(a.getDia()) == 0 && horaLimite!=null &&
					hoje.after(horaLimite)){
				remover.add(a);
			}
		}
		aulas.removeAll(remover);	
		
		AulaResponse response = new AulaResponse(aluno, aulas);
		return response;
	}
	@Override
	public Optional<Aula> buscarPorId(int id){
		Optional<Aula> aula = aulaRepo.findById(id);
		return aula;
	}
	@Override
	public Aula addAluno(Aula aula, Usuario usuario){
		aula.getAlunos().add(usuario);
		aula.setChecked(aula.getChecked()+1);
		return aulaRepo.save(aula);
	}
	@Override
	public Aula removeAluno(Aula aula, Usuario usuario) {
		List<Usuario> usuarios = aula.getAlunos();
		for(int i=0;i<usuarios.size();i++) {
			if(usuarios.get(i).getId() == usuario.getId()) {
				usuarios.remove(usuarios.get(i));
			}
		}
		aula.setAlunos(usuarios);
		aula.setChecked(aula.getChecked()-1);
		return aulaRepo.save(aula);
	}
	
	@Override
	public Collection<Aula> buscaPorAlunoId(Integer id){
		return aulaRepo.findAll(Sort.by("alunos_id"));
	}
	public Collection<Aula> buscarTodasDone() {
		Collection<Aula> aulas = (Collection<Aula>) aulaRepo.findAll(Sort.by("dia").and(Sort.by("hora")));
		return aulas;
	}


	public Collection<Aula> removeAula(int idAula) {
		Aula aula = aulaRepo.findById(idAula).get();
		ArrayList<Usuario> alunos = new ArrayList<>();
		aula.setAlunos(alunos);
		aulaRepo.save(aula);
		aulaRepo.delete(aula);
		return aulaRepo.findAllByOrderByDiaDesc();
	}


	public Collection<Aula> findAll() {
		// TODO Auto-generated method stub
		return (Collection<Aula>) aulaRepo.findAll();
	}
}
