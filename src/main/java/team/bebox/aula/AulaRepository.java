package team.bebox.aula;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import team.bebox.aula.Aula;

public interface AulaRepository extends CrudRepository<Aula, Integer>{

	Collection<Aula> findAllByOrderByDia();

}
