package by.application.Twitter.config.security;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        System.out.println("FUCKING 1");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            System.out.println("FUCKING 1.1");

            jwt = authorizationHeader.substring(7);
            //если подпись не совпадает с вычисленной, то SignatureException
            //если подпись некорректная (не парсится), то MalformedJwtException
            //если время подписи истекло, то ExpiredJwtException
            System.out.println("FUCKING 1.2");
            //try {
                username = jwtUtil.extractUsername(jwt);
            //} catch (MalformedJwtException e) {
                //System.out.println("Unable to parse JWT Token");
                //response.setStatus(401);
                //return new ResponseEntity<>("asdsa", HttpStatus.UNAUTHORIZED);
                //    response.sendError(HttpServletResponse.SC_NO_CONTENT, "Unauthorized fucking");
                    //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found");
            //}
            System.out.println("FUCKING 2");
        }

        System.out.println("FUCKING 3");

        if (username != null /*&& SecurityContextHolder.getContext().getAuthentication() == null*/) {
            System.out.println("FUCKING 4");

            String commaSeparatedListOfAuthorities = jwtUtil.extractAuthorities(jwt);
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(commaSeparatedListOfAuthorities);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            username, null, authorities);
            System.out.println("FUCKING 5");

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            System.out.println("FUCKING 6");

        }
        System.out.println("FUCKING 7");
        chain.doFilter(request, response);
    }

}
