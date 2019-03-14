package oktenweb.demosale.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import oktenweb.demosale.models.JwtUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil implements Serializable {

    static final String CLAM_KEY_USERNAME = "sub";
    static final String CLAM_KEY_AUDIENCE = "audience";
    static final String CLAM_KEY_CREATED = "created";

        static final long EXPIRATIONTIME = 30000;
        static final String SECRET = "ThisIsASecret";

    public String getUsernameFromToken(String token) {
            String username = null;
            try {
                final Claims claims = getClaimsFromToken(token);
                        username = claims.getSubject();
            }catch (Exception e) {
                    username = null;
            }


        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJwt(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser)userDetails;
        String username = getUsernameFromToken(token);

        return (username.equals(user.getUsername())&&!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiratoin = getExpirationDateFromToken(token);
        return expiratoin.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration = null;
        try{
            Claims claims = getClaimsFromToken(token);
            if (claims!=null){
                expiration = claims.getExpiration();
            }else {
                expiration = null;
            }
        }catch (Exception e){
            expiration = null;
        }
        return expiration;
    }
    public String generateToken(JwtUser userDetails){
        Map<String, Object> clamis = new HashMap<String, Object>();
        clamis.put(CLAM_KEY_USERNAME, userDetails.getUsername());
        clamis.put(CLAM_KEY_CREATED, new Date());
        return generateToken(clamis);
    }

    private String generateToken(Map<String, Object> clamis) {
        return Jwts.builder().setClaims(clamis).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.ES512, SECRET).compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+ EXPIRATIONTIME *1000);
    }
}










