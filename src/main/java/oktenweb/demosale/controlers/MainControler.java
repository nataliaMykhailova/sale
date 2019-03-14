package oktenweb.demosale.controlers;

import oktenweb.demosale.components.Response;
import oktenweb.demosale.components.UnauthorizedException;
import oktenweb.demosale.components.UserDTO;
import oktenweb.demosale.config.JwtUtil;
import oktenweb.demosale.models.JwtUser;
import oktenweb.demosale.models.User;
import oktenweb.demosale.services.UserServiсe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MainControler {
    @Autowired
    private UserServiсe userServiсe;

    @PostMapping(value = "/registration")
    public ResponseEntity<Response> registration(@RequestBody User user){
        User sUser = userServiсe.save(user);
        if (sUser!=null){
            return new ResponseEntity<Response>(new Response("User is saved"), HttpStatus.OK);
        }
        return null;
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody User user, HttpServletResponse response, HttpServletRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            final JwtUser userDetails = (JwtUser)authentication.getPrincipal();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtUtil.generateToken(userDetails);
            response.setHeader("Token", token);
            return new ResponseEntity<UserDTO>(new UserDTO(userDetails.getUser(), token), HttpStatus.OK);

        }catch (Exception e){
            throw new UnauthorizedException(e.getMessage());
        }
    }
}
