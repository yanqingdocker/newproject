package cn.com.caogen.filter;

import cn.com.caogen.entity.Muser;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@WebFilter(urlPatterns = "/*")
public class SystemFilter implements Filter {

    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();

    private  Pattern pattern1=Pattern.compile("login");

    private  Pattern pattern2=Pattern.compile(".*[(\\.js)||(\\.css)||(\\.png)||(\\.jpg)]");
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        patterns.add(pattern1);
        patterns.add(pattern2);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest=(HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
            if (url.startsWith("/") && url.length() > 1) {
                url = url.substring(1);
            }
            if(isInclude(url)){

                chain.doFilter(request, response);
                return;

            }else{
                Muser currentUser=(Muser) httpServletRequest.getSession().getAttribute("currentUser");
                if(!StringUtils.isEmpty(currentUser)){
                    if(httpServletRequest.getSession(true)==null){
                        httpServletRequest.getRequestDispatcher("/login").forward(request,response);
                        return;
                    }
                    chain.doFilter(request,response);
                }else{
                    httpServletRequest.getRequestDispatcher("/login").forward(request,response);

                }
            }

    }

    @Override
    public void destroy() {

    }

    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
