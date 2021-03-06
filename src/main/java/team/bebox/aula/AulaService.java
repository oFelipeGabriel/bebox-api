package team.bebox.aula;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import team.bebox.user.Usuario;


public interface AulaService {

	Aula salvar(Aula aula);

	Collection<Aula> buscarTodas();

	Optional<Aula> buscarPorId(int id);

	Aula addAluno(Aula aula, Usuario usuario);

	Aula removeAluno(Aula aula, Usuario usuario);
	
	Collection<Aula> buscaPorAlunoId(Integer id);

	AulaResponse buscarTodas(int idAluno);

}
