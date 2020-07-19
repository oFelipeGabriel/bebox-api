package team.bebox.aula;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
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
	
	public static final String inputFormat = "HH:mm";
	SimpleDateFormat hourFormat = new SimpleDateFormat(inputFormat, new Locale("pt", "BR"));
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt", "BR"));
	

	@PreAuthorize("isAuthenticated()")
	@CrossOrigin
	@GetMapping("/getAll/{aluno}")
	@ResponseBody
	public AulaResponse buscar(@PathVariable("aluno") int idAluno){
		Usuario aluno = usuarioServiceImpl.buscarPorId(idAluno);
		if(aluno.getAulaChecked()!=null) {
			Date hoje = new Date();
			String dataCheck[] = aluno.getAulaChecked().split(" ");
	    	String diaCheck = dataCheck[0];
	    	String horaString = hourFormat.format(hoje);
	    	try {
	    		Date horaDate = hourFormat.parse(horaString);
				Date horaCheck = hourFormat.parse(dataCheck[1]);
				if(hoje.after(sdformat.parse(diaCheck)) && horaDate.after(horaCheck)) {
					usuarioServiceImpl.uncheckAulaToUser(aluno);
				}	
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		AulaResponse aulas = aulaServiceImpl.buscarTodas(idAluno);
		
		return aulas;
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
	@RequestMapping(value = "/addAlunoExperimental/{aula}", method = RequestMethod.POST)
	public ResponseEntity<Collection<Aula>> addAlunoExperimental(@PathVariable("aula") int aula){
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
		java.sql.Date d = new java.sql.Date(dia);
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
		
		AulaResponse aulas = aulaServiceImpl.buscarTodas(idAluno);
		return aulas;
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
		return aulaServiceImpl.buscarTodas(aluno);
	}
	
}
