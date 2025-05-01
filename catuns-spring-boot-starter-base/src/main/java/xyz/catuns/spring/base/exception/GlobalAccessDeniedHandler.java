package xyz.catuns.spring.base.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class GlobalAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      if an input or output error occurs while the servlet is handling the request
     * @throws ServletException if the request could not be handled
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String path = request.getRequestURI();
        String message = (accessDeniedException.getMessage() != null) ? accessDeniedException.getMessage() : "Unauthorized";
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("x-denied-reason", "Authorization Failed");
        String json = new ErrorMessage(path, message, HttpStatus.FORBIDDEN).toJson();
        response.getWriter().write(json);
    }
}
