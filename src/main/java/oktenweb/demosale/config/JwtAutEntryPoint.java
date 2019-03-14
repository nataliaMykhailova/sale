package oktenweb.demosale.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAutEntryPoint extends BasicAuthenticationEntryPoint {
                @Override
                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                    //if auth failed send error response
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.addHeader("WWW-Authenticate" , "XXXXXXXXX");
                    PrintWriter writer = response.getWriter();
                    writer.println("HTTP Status 401 : " + authException.getMessage());
                }


            //    In short, pages in the same realm should share credentials. If your credentials work for a page with the realm "My Realm", it should be assumed that the same username and password combination should work for another page with the same realm.
            /*
            A realm can be seen as an area (not a particular page, it could be a group of pages) for which the credentials are used; this is also the string that will be shown when the browser pops up the login window, e.g.

                Please enter your username and password for <realm name>:

                When the realm changes, the browser may show another popup window if it doesn't have credentials for that particular realm.
            */
                @Override
                public void afterPropertiesSet() throws Exception {
                    setRealmName("MY_TEST_REALM");
                    super.afterPropertiesSet();

                }
            }



