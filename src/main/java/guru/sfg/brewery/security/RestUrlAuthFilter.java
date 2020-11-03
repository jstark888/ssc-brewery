package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jeff Stark on 11/2/2020
 */

@Slf4j
public class RestUrlAuthFilter extends AbstractRestAuthFilter {

    public RestUrlAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getUsername(HttpServletRequest request) {
        return request.getParameter("apiKey");
    }

    @Override
    protected String getPassword(HttpServletRequest request) {
        return request.getParameter("apiSecret");
    }
}
