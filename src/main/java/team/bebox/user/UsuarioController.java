package team.bebox.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import team.bebox.autorizacao.Autorizacao;
import team.bebox.autorizacao.AutorizacaoServiceImpl;
import team.bebox.view.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value="/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private AutorizacaoServiceImpl autorizacaoService;
	
	//@PreAuthorize("ROLE_ADMIN")
	@CrossOrigin
	@RequestMapping(value = "/novoUsuario", method = RequestMethod.POST)
	public ResponseEntity<Usuario> salvar(@RequestBody ObjectNode json) {
		
		String nome = json.get("nome").asText();
		String email = json.get("email").asText();
		String cpf = json.get("cpf").asText();
		String senha = json.get("cpf").asText();		
		//String arquivo = json.get("logo").asText();
		Boolean idAutorizacao = json.get("autorizacao").asBoolean();
		String endereco = json.get("endereco").asText();
		String telefone = json.get("telefone").asText();
		Long dataNasc = json.get("data_nasc").asLong();
		
		System.out.println(idAutorizacao);
		
		Date dataNascimento = new Date(dataNasc);
		Date date = new Date(System.currentTimeMillis());
		Usuario usuario = new Usuario();

		usuario.setNome(nome);
		usuario.setCpf(cpf);
		usuario.setSenha(md5(senha));
		usuario.setEmail(email);
		usuario.setDataDeCriacao(date);
		usuario.setIs_admin(idAutorizacao);
		usuario.setEndereco(endereco);
		usuario.setTelefone(telefone);
		usuario.setData_nascimento(dataNascimento);
		
		Autorizacao a;
		if(idAutorizacao) {
			a = autorizacaoService.buscarPorNome("ROLE_ADMIN");			
		}else {
			a = autorizacaoService.buscarPorNome("ROLE_USER");	
		}			
		List<Autorizacao> listaAut = new ArrayList<Autorizacao>();
		listaAut.add(a);
		usuario.setAutorizacoes(listaAut);
		
		usuarioService.salvar(usuario);
				
		return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(value = "/addFoto", method = RequestMethod.POST)
	ResponseEntity<String> receiveData(MultipartFile foto) throws IOException{
		System.out.println(foto.getOriginalFilename());
		System.out.println(foto.toString());
		return ResponseEntity.ok("Deu certo!");
	}
	@CrossOrigin
	@RequestMapping(value="/atualizaFoto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Usuario> atualizaFoto(@PathVariable int id, @RequestBody ObjectNode json){
		Usuario e = usuarioService.buscarPorId(id);
		String logo = json.get("logo").asText();
		e.setFoto(logo);
		usuarioService.salvar(e);
		return new ResponseEntity<Usuario>(e, HttpStatus.OK);
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/getById/{id}", method=RequestMethod.GET)
	public ResponseEntity<Usuario> getempresaById(@PathVariable("id") String id){
		Usuario empresa = usuarioService.buscarPorId(Integer.parseInt(id));
		if(empresa == null) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Usuario>(empresa, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/editar", method=RequestMethod.PUT)
	public ResponseEntity<Usuario> editarEmpresa(@RequestBody Usuario e){
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Usuario>(e, responseHeaders, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/editarSenha{id}", method=RequestMethod.PUT)
	public ResponseEntity<Usuario> editarSenhaEmpresa(@PathVariable("id") Integer id){
		Usuario e = usuarioService.buscarPorId(id);
		e.setSenha(md5(e.getSenha()));
		usuarioService.salvar(e);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Usuario>(e, responseHeaders, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/deleteEmpresa/{id}/", method = RequestMethod.DELETE)
	public Boolean deleteEmpresa(@PathVariable Integer id){
		Usuario empresa = usuarioService.buscarPorId(id);
		usuarioService.excluir(empresa);
		return true;
	}

	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public ResponseEntity<Collection<Usuario>> getAll() {
		return new ResponseEntity<Collection<Usuario>>(usuarioService.todosUsuarios(), HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	//@JsonView(View.UsuarioResumo.class)
	@CrossOrigin
	@RequestMapping(value = "/getAllAdmin", method = RequestMethod.GET)
	public ResponseEntity<Collection<Usuario>> getAllAdmin() {
		return new ResponseEntity<Collection<Usuario>>(usuarioService.todosAdmins(), HttpStatus.OK);
	}

	
	public static String md5(String senha) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			hexString.append("{MD5}");
			for (byte b : messageDigest) {
				hexString.append(String.format("%02x", 0xFF & b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException exception) {
			exception.printStackTrace();
			// Unexpected - do nothing
		} catch (UnsupportedEncodingException exception) {
			exception.printStackTrace();
			// Unexpected - do nothing			
		}
		return senha;
	}


}



