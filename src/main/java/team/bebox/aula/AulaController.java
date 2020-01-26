package team.bebox.aula;

import java.sql.Date;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

import team.bebox.user.Usuario;
import team.bebox.user.UsuarioServiceImpl;
import team.bebox.view.View;


@RestController
@CrossOrigin
@RequestMapping(value="/aula")
public class AulaController {

	@Autowired
	private AulaServiceImpl aulaServiceImpl;
	
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
		
	@CrossOrigin
	@JsonView(View.UsuarioBase.class)
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public ResponseEntity<Collection<Aula>> buscar(){
		return new ResponseEntity<Collection<Aula>> (aulaServiceImpl.buscarTodas(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/novaAula", method = RequestMethod.POST)
	public ResponseEntity<Aula> nova(@RequestBody ObjectNode json) throws ParseException{
		Long dia = json.get("dia").asLong();
		String hora = json.get("hora").asText();
		String quantidade = json.get("quantidade").asText();
		Date d = new Date(dia);
		Aula a = new Aula();
		a.setDia(d);
		a.setHora(hora);
		a.setQuantidade(Integer.parseInt(quantidade));
		a.setChecked(0);
		aulaServiceImpl.salvar(a);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Aula>(a, responseHeaders, HttpStatus.CREATED);
	}
	/* get id of current user
	 * Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  String username = ((UserDetails)principal).getUsername();
		} else {
		  String username = principal.toString();
		}
	 * 
	 */
	@CrossOrigin
	@RequestMapping(value = "/addAluno/{aula}/{aluno}/", method = RequestMethod.POST)
	public ResponseEntity<Collection<Aula>> addAluno(@PathVariable("aula") int aula, @PathVariable("aluno") int aluno){
		Aula a = aulaServiceImpl.buscarPorId(aula).get();
		Usuario u = usuarioServiceImpl.buscarPorId(aluno);
		aulaServiceImpl.addAluno(a, u);
		HttpHeaders responseHeaders = new HttpHeaders();
		Collection<Aula> aulas = aulaServiceImpl.buscarTodas();
		return new ResponseEntity<Collection<Aula>>(aulas, responseHeaders, HttpStatus.CREATED);
	}

	@JsonView(View.UsuarioBase.class)
	@CrossOrigin
	@RequestMapping(value = "/removeAluno/{aula}/{aluno}/", method = RequestMethod.POST)
	public ResponseEntity<Collection<Aula>> removeAluno(@PathVariable("aula") int aula, @PathVariable("aluno") int aluno){
		Aula a = aulaServiceImpl.buscarPorId(aula).get();
		Usuario u = usuarioServiceImpl.buscarPorId(aluno);		
		aulaServiceImpl.removeAluno(a, u);
		HttpHeaders responseHeaders = new HttpHeaders();
		Collection<Aula> aulas = aulaServiceImpl.buscarTodas();
		return new ResponseEntity<Collection<Aula>>(aulas, responseHeaders, HttpStatus.CREATED);
	}
}
