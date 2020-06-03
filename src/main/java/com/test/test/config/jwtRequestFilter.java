//package com.test.test.config;
//
//import com.test.test.services.UserService;
//import io.jsonwebtoken.ExpiredJwtException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Order(2)
//public class jwtRequestFilter extends OncePerRequestFilter {
//    @Autowired
//    private UserService userService;
//
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        String userName = null;
//        String jwtToken = null;
//        String id = null;
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        final String requestTokenHeader = request.getHeader("Authorization");
//        String url = httpServletRequest.getRequestURI();
//        if (url.contains("add")) {
//
//            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//                jwtToken = requestTokenHeader.substring(7);
//                try {
//                    userName = userService.decodeJWT(jwtToken).getIssuer();
//                    id = userService.decodeJWT(jwtToken).getId();
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Unable to get JWT Token");
//                } catch (ExpiredJwtException e) {
//                    System.out.println("JWT Token has expired");
//                }
//            } else {
//                logger.warn("JWT Token does not begin with Bearer String");
//            }
//
//            if (userName != null) {
//
//                // authentication
//                if (userService.validateToken(userName, id, jwtToken)) {
//                    chain.doFilter(request, response);
//                }
//            }
//
//        }
//        else {chain.doFilter(request, response);}
//    }
//
//
//}
