package team.bebox;

import java.util.List;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;

import team.bebox.autorizacao.Autorizacao;
import team.bebox.autorizacao.AutorizacaoServiceImpl;
import team.bebox.user.Usuario;
import team.bebox.user.UsuarioController;
import team.bebox.user.UsuarioRepository;
import team.bebox.user.UsuarioServiceImpl;

@SpringBootApplication
@CrossOrigin
public class SpringRestApplication {
	
	@Autowired
	private AutorizacaoServiceImpl autorizacaoService;
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	@Autowired
	private UsuarioRepository usuarioRespo;

	public static void main(String[] args) {
		SpringApplication.run(SpringRestApplication.class, args);		
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws NoSuchAlgorithmException, UnsupportedEncodingException {
	    
	    Autorizacao aut1 = new Autorizacao();
	    aut1.setNome("ROLE_ADMIN");
	    Autorizacao aut2 = new Autorizacao();
	    aut2.setNome("ROLE_USER");
	    
	    Autorizacao admin = autorizacaoService.buscarPorNome(aut1.getNome());
	    Autorizacao user = autorizacaoService.buscarPorNome(aut2.getNome());
	    if(admin == null) {
	    	autorizacaoService.salvar(aut1);
	    }
	    if(user == null) {
	    	autorizacaoService.salvar(aut2);
	    }
	    
	    String senha = UsuarioController.md5("admin");
	    String login = "0000";
	    String nome = "admin";
	    Usuario userAdmin = usuarioRespo.findFirstByCpf(login);
	    if(userAdmin == null) {
	    	userAdmin = new Usuario();
	    	userAdmin.setCpf(login);
		    userAdmin.setSenha(senha);
		    userAdmin.setIs_admin(true);
		    userAdmin.setNome(nome);
		    List<Autorizacao> auts = new ArrayList<Autorizacao>();
		    auts.add(aut1);
		    userAdmin.setAutorizacoes(auts);
		    usuarioService.salvar(userAdmin);
		    
	    }
	    
	    System.out.println("Aplicação iniciada com sucesso");
	}
	

}
