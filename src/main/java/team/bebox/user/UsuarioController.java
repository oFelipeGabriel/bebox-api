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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value="/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private AutorizacaoServiceImpl autorizacaoService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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
		Double valor_mensalidade = json.get("mensalidade").asDouble();
		Long data_vcto = json.get("data_vencimento").asLong();
		
						
		Date dataNascimento = new Date(dataNasc);
		Date date = new Date(System.currentTimeMillis());
		Date data_vencimento = new Date(data_vcto);
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
		usuario.setValor_mensalidade(valor_mensalidade);
		usuario.setDataVencimento(data_vencimento);
		
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@RequestMapping(value="/editar/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Usuario> editarEmpresa(@PathVariable("id") int id, @RequestBody ObjectNode json){
		String nome = json.get("nome").asText();
		String email = json.get("email").asText();
		String cpf = json.get("cpf").asText();
		String endereco = json.get("endereco").asText();
		String telefone = json.get("telefone").asText();
		Long dataNasc = json.get("data_nasc").asLong();
		Double valor_mensalidade = json.get("mensalidade").asDouble();
		Long data_vcto = json.get("data_vencimento").asLong();
		Boolean idAutorizacao = json.get("autorizacao").asBoolean();
		
		Date dataNascimento = new Date(dataNasc);
		Date data_vencimento = new Date(data_vcto);
		Usuario usuario = usuarioService.buscarPorId(id);
		usuario.setNome(nome);
		usuario.setCpf(cpf);
		usuario.setEmail(email);
		usuario.setEndereco(endereco);
		usuario.setTelefone(telefone);
		usuario.setData_nascimento(dataNascimento);
		usuario.setValor_mensalidade(valor_mensalidade);
		usuario.setDataVencimento(data_vencimento);
		usuario.setIs_admin(idAutorizacao);
		
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
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Usuario>(usuario, responseHeaders, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/editarSenha/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Usuario> editarSenhaEmpresa(@PathVariable("id") Integer id, @RequestBody ObjectNode json){
		Usuario e = usuarioService.buscarPorId(id);
		String senha = json.get("senha").asText();
		e.setSenha(md5(senha));
		usuarioService.salvar(e);
		HttpHeaders responseHeaders = new HttpHeaders();
		return new ResponseEntity<Usuario>(e, responseHeaders, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delete/{id}/", method = RequestMethod.DELETE)
	public Boolean deleteEmpresa(@PathVariable Integer id){
		Usuario usuario = usuarioService.buscarPorId(id);
		usuarioService.excluir(usuario);
		return true;
	}

	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@RequestMapping(value = "/getAll/{pageNo}/{pageSize}", method = RequestMethod.GET)
	public UsuariosResponse getAll(@PathVariable int pageNo, 
            @PathVariable int pageSize, @RequestParam(required = false) String ativo) {
		return usuarioService.getUsuariosPaginated(pageNo, pageSize, ativo);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	//@JsonView(View.UsuarioResumo.class)
	@CrossOrigin
	@RequestMapping(value = "/getAllAdmin", method = RequestMethod.GET)
	public ResponseEntity<Collection<Usuario>> getAllAdmin() {
		return new ResponseEntity<Collection<Usuario>>(usuarioService.todosAdmins(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@RequestMapping(value = "/buscaAluno", method = RequestMethod.POST)
	public UsuariosResponse getByNome(@RequestBody ObjectNode json, @RequestParam(required = false) String ativo){
		String aluno = json.get("nome").asText();
		return usuarioService.buscaPorNome(aluno, ativo);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin
	@RequestMapping(value = "/atualizaStatus", method = RequestMethod.POST)
	public ResponseEntity<Boolean> atualizaStatus(@RequestBody ObjectNode json){
		int id = json.get("id").asInt();
		Boolean status = json.get("status").asBoolean();
		Boolean deuCerto = usuarioService.atualizaStatus(id, status);
		return new ResponseEntity<Boolean>(deuCerto, HttpStatus.OK);
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



