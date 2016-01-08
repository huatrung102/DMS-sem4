/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import additional.DMSException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TrungHTH
 */
public class SessionUtils {

    public static boolean isSignedIn() {
        return SessionListener.getSessionAttribute("userId", false) != null;
    }

    public static UUID getUserId() {
        UUID uId = ((UUID) SessionListener.getSessionAttribute("userId", true));        
        return uId;
    }

    public static UUID getCurrentUserId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        UUID userId = (UUID) session.getAttribute("userId");
        return userId;
    }

    public static UUID getCurrentUserIdThrowingException(HttpServletRequest req) 
            throws DMSException {
        HttpSession session = req.getSession(false);
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) {
            throw new DMSException("You are not logged in");
        }
        
        return userId;
    }
}
