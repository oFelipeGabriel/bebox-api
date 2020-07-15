package team.bebox.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import team.bebox.user.Usuario;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager auth;
    
    //@JsonView(View.UsuarioResumo.class)
    @CrossOrigin
    @RequestMapping(path = "/logando", method = RequestMethod.POST)   
    public ResponseEntity<Usuario> login(@RequestBody Login login, HttpServletResponse response) throws JsonProcessingException {
        Authentication credentials = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Usuario usuario = (Usuario) auth.authenticate(credentials).getPrincipal();
        response.setHeader("token", JwtUtils.generateToken(usuario));
        response.setHeader("Access-Control-Expose-Headers", "token");
        response.setHeader("Access-Control-Allow-Headers", 
        		"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, class-name, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Expose-Headers");
        
        
        return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);
    }


}
