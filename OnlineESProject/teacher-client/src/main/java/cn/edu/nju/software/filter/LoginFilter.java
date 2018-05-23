package cn.edu.nju.software.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName="loginfilter",urlPatterns="/*")
public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub  
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	//先判断是不是注册或登录动作
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String type = request.getParameter("type");
    	if(type != null) {
			if(type.equals("register") || type.equals("login")) {
				chain.doFilter(request, response);
				return;
			}
		}
    	HttpSession session = ((HttpServletRequest) request).getSession(false);
		if(session == null || session.getAttribute("tid") == null) {
			//session已失效或者非法访问，跳到登陆界面
			String uri = ((HttpServletRequest) request).getRequestURI();
			if(uri.endsWith("login") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg")) {
				chain.doFilter(request, response);
				return;
			}
			((HttpServletResponse) response).sendRedirect(((HttpServletRequest)request).getContextPath()+"/login");
		} else {
			chain.doFilter(request, response);
		}
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
}