/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package filter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author TrungHTH
 */
public class SessionFilter implements Filter {

    public static final String GUEST_URI = "login.html";
    
    @Override
    public void init(FilterConfig filterCorenfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        System.out.println("path = " + req.getRequestURI());
        
        if (session == null) session = req.getSession(true);
        System.out.println("session = " + session);
        if ((!(req.getRequestURI().indexOf(GUEST_URI) > -1 )) && session.getAttribute("userId") == null){
           // resp.sendRedirect(GUEST_URI);
        }
        
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
