package team.bebox.aula;

import java.util.Collection;
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
		Collection<Aula> aulas = (Collection<Aula>) aulaRepo.findAll();
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
}
