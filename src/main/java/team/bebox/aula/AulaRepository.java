package team.bebox.aula;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import team.bebox.aula.Aula;

public interface AulaRepository extends PagingAndSortingRepository<Aula, Integer>{
	Collection<Aula> findAllByOrderByDiaAsc();
	Collection<Aula> findAllByOrderByDiaDesc();
	List<Aula> findByAlunos_IdOrderByDiaDesc(Integer id);
	Collection<Aula> findAll(Sort by);
	List<Aula> findAllByOrderByIdAsc();

}
