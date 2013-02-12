package be.kdg.groeph.util;

import be.kdg.groeph.bean.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * To change this template use File | Settings | File Templates.
 */
public class LoginFilterUser {//implements Filter {
    /*
    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (loginBean.getUser() != null) {     //User.INVALID_USER()

            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect("pages/index.xhtml");
        }
    }

    @Override
    public void destroy() {
    }
    */
}
