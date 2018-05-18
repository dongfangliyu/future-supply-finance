package cn.fintecher.authorization.server.controller;

import cn.fintecher.authorization.server.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@Controller
public class OAuth2Controller {

    @Autowired
    private OAuth2Service oauth2Service;

    @RequestMapping(value = "/oauth/v1", method = RequestMethod.GET)
    @ResponseBody
    public String v1() throws Exception {

        return "v1";
    }


    @RequestMapping(value = "/oauth/v2", method = RequestMethod.GET)
    @ResponseBody
    public String v2() throws Exception {

        return "v2";
    }

    @RequestMapping(value = "/oauth/v3", method = RequestMethod.GET)
    @ResponseBody
    public String v3() throws Exception {

        return "v3";
    }

    @RequestMapping("/oauth/cache_approvals")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void startCaching() throws Exception {
        oauth2Service.startCaching();
    }

    @RequestMapping("/oauth/uncache_approvals")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stopCaching() throws Exception {
        oauth2Service.stopCaching();
    }

    @RequestMapping(value = "/oauth/revoke_token", method = RequestMethod.DELETE)
    public ResponseEntity<Void> revokeToken(HttpServletRequest request)
            throws Exception {
        return oauth2Service.revokeToken(request);
    }

    @RequestMapping(value = "/oauth/users/{user}/tokens/{token}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> revokeToken(@PathVariable String user, @PathVariable String token, Principal principal)
            throws Exception {
        return oauth2Service.revokeToken(user, token, principal);
    }

    @RequestMapping("/oauth/clients/{client}/tokens")
    @ResponseBody
    public Collection<OAuth2AccessToken> listTokensForClient(@PathVariable String client)
            throws Exception {
        return oauth2Service.listTokensForClient(client);
    }

    @RequestMapping("/oauth/clients/{client}/users/{user}/tokens")
    @ResponseBody
    public Collection<OAuth2AccessToken> listTokensForUser(@PathVariable String client, @PathVariable String user,
                                                           Principal principal) throws Exception {
        return oauth2Service.listTokensForUser(client, user, principal);
    }
}
