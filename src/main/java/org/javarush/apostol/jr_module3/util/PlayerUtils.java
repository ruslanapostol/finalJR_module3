package org.javarush.apostol.jr_module3.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import static org.javarush.apostol.jr_module3.util.WebConstants.*;


public class PlayerUtils {

    public static boolean ensurePlayerName(HttpServletRequest request, HttpServletResponse response,
                                           HttpSession session) throws IOException {
        String playerName = (String) session.getAttribute(PLAYER_NAME);
        if (playerName == null) {
            playerName = request.getParameter(PLAYER_NAME);
            if (playerName != null && !playerName.trim().isEmpty()) {
                session.setAttribute(PLAYER_NAME, playerName);
            } else {
                response.sendRedirect(request.getContextPath() + INDEX_JSP);
                return false;
            }
        }
        return true;
    }
}
