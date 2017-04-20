package controller.filter;

import com.sun.deploy.net.HttpRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vlad on 20.04.17.
 */
public class RequestFilted implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest=(HttpServletRequest)servletRequest;

        String uri = httpRequest.getRequestURI();

        if(checkRedirectableUris(uri)){
            HttpServletResponse httpResponse=(HttpServletResponse)servletResponse;
            httpResponse.sendRedirect(httpRequest.getContextPath()+"/wav");
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean checkRedirectableUris(String uri) {
        return uri.equals("/");
    }
}
