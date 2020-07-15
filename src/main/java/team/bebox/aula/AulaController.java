package team.bebox.aula;

import java.sql.Date;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
	

	//@PreAuthorize("hasRole('ROLE_USER')")
	@CrossOrigin
	@GetMapping("/getAll/{aluno}")
	@ResponseBody
	public AulaResponse buscar(@PathVariable("aluno") int idAluno){
		Collection<Aula> aulas = aulaServiceImpl.buscarTodas(idAluno);
		Usuario aluno = usuarioServiceImpl.buscarPorId(idAluno);
//		if(aluno.getAulaChecked() != null) {
//        	String data[] = aluno.getAulaChecked().split("=");
//            String diaMesAno[] = data[0].split("-");
//            String horaMin[] = data[1].split(":");
//            int dia = Integer.parseInt(diaMesAno[2]);
//            int mes = Integer.parseInt(diaMesAno[1]);
//            int ano = Integer.parseInt(diaMesAno[0]);
//            int hora = Integer.parseInt(horaMin[0]);
//            int min = Integer.parseInt(horaMin[1]);
////            TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
////    		TimeZone.setDefault(tz);
////            GregorianCalendar gc = new GregorianCalendar( ano, mes, dia, hora, min );
//////            gc.setTimeZone(tz);
////            GregorianCalendar gc2 = new GregorianCalendar();
////            if(gc2.after(gc)) {
////            	usuarioServiceImpl.uncheckAulaToUser(aluno);
////            }
//            aulas = aulaServiceImpl.buscaPorAlunoId(aluno.getId());
//        }
		return new AulaResponse(aluno, aulas);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@JsonView(View.UsuarioBase.class)
	@RequestMapping(value = "/getAllDone", method = RequestMethod.GET)
	public ResponseEntity<Collection<Aula>> buscarFiltrada(){
		return new ResponseEntity<Collection<Aula>> (aulaServiceImpl.buscarTodasDone(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@JsonView(View.UsuarioBase.class)
	@PostMapping("/removeAula/{id}")
	public ResponseEntity<Collection<Aula>> remove(@PathVariable("id") int aula){
		return new ResponseEntity<Collection<Aula>>(aulaServiceImpl.removeAula(aula), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@RequestMapping(value = "/novaAula", method = RequestMethod.POST)
	public ResponseEntity<Aula> nova(@RequestBody ObjectNode json){
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(tz);
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

	@PreAuthorize("isAuthenticated()")
	@CrossOrigin
	@PostMapping("/addAluno/{aula}/{aluno}/")
	@ResponseBody
	public AulaResponse addAluno(@PathVariable("aula") int aula, @PathVariable("aluno") int idAluno){
		Usuario user = usuarioServiceImpl.buscarPorId(idAluno);		
		Aula a = aulaServiceImpl.addAluno(aulaServiceImpl.buscarPorId(aula).get(), user);
		usuarioServiceImpl.checkAulaToUser(user, a);
		
		Collection<Aula> aulas = aulaServiceImpl.buscaPorAlunoId(idAluno);
		Usuario aluno = usuarioServiceImpl.buscarPorId(idAluno);
		return new AulaResponse(aluno, aulas);
	}

	@PreAuthorize("isAuthenticated()")
	@CrossOrigin
	@PostMapping("/removeAluno/{aula}/{aluno}/")
	@ResponseBody
	public AulaResponse removeAluno(@PathVariable("aula") int aula, @PathVariable("aluno") int aluno){
		
		Usuario u = usuarioServiceImpl.buscarPorId(aluno);
		usuarioServiceImpl.uncheckAulaToUser(u);
		
		Aula a = aulaServiceImpl.buscarPorId(aula).get();
		aulaServiceImpl.removeAluno(a, u);
		Collection<Aula> aulas = aulaServiceImpl.buscarTodas(aluno);
		return new AulaResponse(u, aulas);
	}
}
