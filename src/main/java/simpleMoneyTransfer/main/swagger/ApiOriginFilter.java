package simpleMoneyTransfer.main.swagger;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import com.google.inject.Singleton;
import simpleMoneyTransfer.constants.ConfigConstants;

@Singleton
final class ApiOriginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader(ConfigConstants.HEADER_ALLOW_ORIGIN, ConfigConstants.HEADER_ALLOW_ORIGIN_VALUE);
        res.addHeader(ConfigConstants.HEADER_ALLOW_HEADERS, ConfigConstants.HEADER_ALLOW_HEADERS_VALUE);
        res.addHeader(ConfigConstants.HEADER_ALLOW_METHODS, ConfigConstants.HEADER_ALLOW_METHODS_VALUE);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
