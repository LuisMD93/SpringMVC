package com.datajpa.app.auth.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

/**
 *
 * @author LuisMD
 */

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {

        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
        FlashMap f = new FlashMap();

        f.put("success", "Ha iniciado sesion con exito!");

        flashMapManager.saveOutputFlashMap(f, req, res);
        if (auth != null) {

            logger.info("El usuario '" + auth.getName() + "' ha iniciado sesion con exito");
        }

        super.onAuthenticationSuccess(req, res, auth);
    }



}
