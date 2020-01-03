package team.bebox.user;

import java.util.Collection;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

import team.bebox.user.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
    Usuario findByNome(String nome);

	public Usuario findByCpf(String username);
    
    //@EntityGraph(value = "adminTurno", type = EntityGraphType.FETCH)
	//public Empresa findEmpresaByAdminTurnoId(Long id);
	Collection<Usuario> findByAutorizacoes_Nome(String nome);
	Usuario findFirstByCpf(String login);
}