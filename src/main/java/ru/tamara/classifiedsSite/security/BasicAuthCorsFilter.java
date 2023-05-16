package ru.tamara.classifiedsSite.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс является компонентом и фильтром для обработки запросов.
 * Он наследуется от класса OncePerRequestFilter и реализует один метод doFilterInternal().
 * Он используется для обеспечения доступа к ресурсам с других источников (CORS) и авторизации.
 *
 * @see OncePerRequestFilter
 */
@Component
public class BasicAuthCorsFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse,
                                 FilterChain filterChain)
            throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
