package by.application.Twitter.config.security;

import by.application.Twitter.model.LoginDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.sessionTime}")
    private long sessionTime;

    // генерация токена (кладем в него имя пользователя и authorities)
    public String generateToken(LoginDetails loginDetails) {
        //Map<String, Object> claims = new HashMap<>();
        //String commaSeparatedListOfAuthorities=  loginDetails.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(","));
        //claims.put("authorities", commaSeparatedListOfAuthorities);
        return createToken(/*claims,*/ loginDetails.getUsername());
    }

    //извлечение имени пользователя из токена (внутри валидация токена)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //извлечение authorities (внутри валидация токена)
    public String extractAuthorities(String token) {
        Function<Claims, String> claimsListFunction = claims -> {
            return (String) claims.get("authorities");
        };
        return extractClaim(token, claimsListFunction);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    private String createToken(/*Map<String, Object> claims,*/ String subject) {

        return Jwts.builder()/*.setClaims(claims)*/
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireTimeFromNow())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime);
    }
}