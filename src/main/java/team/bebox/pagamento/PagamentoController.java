package team.bebox.pagamento;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="/pagamento")
public class PagamentoController {
	
	@Autowired
	private PagamentoServiceImpl pagamentoService;
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@JsonView(View.Pagamento.class)
	@RequestMapping(value = "/get/{idUsuario}", method = RequestMethod.GET)
	public ResponseEntity<List<Pagamento>> buscar(@PathVariable Integer idUsuario){
		Usuario u = usuarioService.buscarPorId(idUsuario);		
		return new ResponseEntity<List<Pagamento>> (pagamentoService.buscaPorUsuario(u), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/novo/{idUsuario}", method = RequestMethod.POST)
	public ResponseEntity<List<Pagamento>> novo(@PathVariable Integer idUsuario, @RequestBody ObjectNode json){
		Usuario u = usuarioService.buscarPorId(idUsuario);
		Long dia = json.get("data").asLong();
		String formaPagamento = json.get("forma").asText();
		Double valor = json.get("valor").asDouble();
		Date d = new Date(dia);		
		Pagamento pag = new Pagamento(d, formaPagamento, valor, u);
		pagamentoService.salvar(pag);
		return new ResponseEntity<List<Pagamento>> (pagamentoService.buscaPorUsuario(u), HttpStatus.CREATED);
	}
		
	@JsonView(View.Pagamento.class)
	@RequestMapping(value="/{forma}/{idUsuario}", method=RequestMethod.GET)
	public ResponseEntity<List<Pagamento>> buscaPorForma(@PathVariable String forma, @PathVariable Integer idUsuario){
		Usuario u = usuarioService.buscarPorId(idUsuario);
		List<Pagamento> pagamentos = pagamentoService.buscaPorForma(forma, u);
		return new ResponseEntity<List<Pagamento>> (pagamentos, HttpStatus.OK);
	}

}
