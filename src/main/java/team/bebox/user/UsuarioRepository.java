package team.bebox.user;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import team.bebox.user.Usuario;

@Repository
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
    Usuario findByNome(String nome);

	public Usuario findByCpf(String username);
    
    //@EntityGraph(value = "adminTurno", type = EntityGraphType.FETCH)
	//public Empresa findEmpresaByAdminTurnoId(Long id);
	Collection<Usuario> findByAutorizacoes_Nome(String nome);
	Usuario findFirstByCpf(String login);
	
	Collection<Usuario> findByStatus(Boolean status);
	Page<Usuario> findAll(Pageable pageable);
}