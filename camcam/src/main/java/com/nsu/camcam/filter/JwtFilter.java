package com.nsu.camcam.filter;

import com.nsu.camcam.model.UserCredential;
import com.nsu.camcam.provider.JwtProvider;
import com.nsu.camcam.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {

    public static final String AUTH_HEADER = "Authorization";
    private static final String JWT_AUTH_HEADER = "Bearer ";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CredentialsService service;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("do filter...");
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);

        if (token != null && jwtProvider.validateAuthJWTToken(token)) {
            UserCredential tokenCredentials = jwtProvider.getCredentialsFromToken(token);
           // UserCredential real = service.findByUsername(tokenCredentials.getUsername());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenCredentials, null, tokenCredentials.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTH_HEADER);
        if (hasText(bearer) && bearer.startsWith(JWT_AUTH_HEADER)) {
            return bearer.substring(JWT_AUTH_HEADER.length());
        }
        return null;
    }
}