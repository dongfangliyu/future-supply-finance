package cn.fintecher.authorization.conf.provider;

import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class BearerAndCookieTokenExtractor extends BearerTokenExtractor {

    private static final String ACCESS_TOKEN = "access_token";

    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    protected String extractHeaderToken(HttpServletRequest request) {
        String token = super.extractHeaderToken(request);

        if (null != token && !token.equals("")) {
            return token;
        }
        try {
            Map<String, Cookie> cookieMap = ReadCookieMap(request);
            if (cookieMap.containsKey(ACCESS_TOKEN)) {
                Cookie cookie = (Cookie) cookieMap.get(ACCESS_TOKEN);
                token = URLDecoder.decode(cookie.getValue(), "UTF-8");
                token = token.replace("\"", "").replace("\"", "");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            token = null;
        }
        return token;
    }
}
