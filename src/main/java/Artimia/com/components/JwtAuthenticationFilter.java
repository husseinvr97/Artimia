package Artimia.com.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import Artimia.com.services.CustomUserDetailsServices;
import Artimia.com.services.TokenServices;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter 
{
    
    private final CustomUserDetailsServices customUserDetailsService;
    private final TokenServices tokenServices;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException 
    {
        String token = extractTokenFromHeader(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) 
        {
            String username = tokenServices.extractEmail(token); 
            if (username != null) 
            {
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
                if (tokenServices.isTokenValid(token, userDetails)) 
                {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                        );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    private String extractTokenFromHeader(HttpServletRequest request) 
    {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) 
        {
            return header.substring(7); 
        }
        return null;
    }
}