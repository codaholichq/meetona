package meetona.shared.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TrailingSlashFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        if (path.endsWith("/")) {
            String newPath = path.substring(0, path.length() - 1);
            HttpServletRequest newRequest = new HttpRequestWrapper(httpRequest, newPath);
            chain.doFilter(newRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private static class HttpRequestWrapper extends HttpServletRequestWrapper {
        private final String newPath;
        public HttpRequestWrapper(HttpServletRequest request, String newPath) {
            super(request);
            this.newPath = newPath;
        }
        @Override
        public String getRequestURI() {
            return newPath;
        }
        @Override
        public StringBuffer getRequestURL() {
            StringBuffer url = new StringBuffer();
            url.append(getScheme())
                    .append("://")
                    .append(getServerName())
                    .append(":")
                    .append(getServerPort())
                    .append(newPath);
            return url;
        }
    }
}
