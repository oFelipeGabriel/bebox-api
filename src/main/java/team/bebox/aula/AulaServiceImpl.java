package team.bebox.aula;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import team.bebox.user.Usuario;

@Service
@Transactional
public class AulaServiceImpl implements AulaService{

	@Autowired
	private AulaRepository aulaRepo;
	
	@Override
	public Aula salvar(Aula aula) {
		return aulaRepo.save(aula);
	}
	@Override
	public Collection<Aula> buscarTodas(){
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
		Collection<Aula> aulas = (Collection<Aula>) aulaRepo.findAllByOrderByDiaAsc();
		Collection<Aula> remover = new ArrayList<Aula>();
		for(Aula a: aulas) {
			if(simpleDateFormat.format(date).compareTo(a.getDia().toString()) > 0) {
				remover.add(a);
			}else if(simpleDateFormat.format(date).compareTo(a.getDia().toString()) == 0 &&
					hourFormat.format(date).compareTo(a.getHora())>0){
				remover.add(a);
			}
		}
		aulas.removeAll(remover);
		return aulas;
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
	public List<Aula> buscaPorAlunoId(Integer id){
		return aulaRepo.findByAlunos_Id(id);
	}
	public Collection<Aula> buscarTodasDone() {
		Collection<Aula> aulas = (Collection<Aula>) aulaRepo.findAllByOrderByDiaDesc();
		return aulas;
	}
}
