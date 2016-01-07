/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import additional.DMSException;
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

    public static Long getUserId() {
        Long uId = ((Long) SessionListener.getSessionAttribute("userId", true));
        return uId;
    }

    public static Long getCurrentUserId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        return userId;
    }

    public static Long getCurrentUserIdThrowingException(HttpServletRequest req) 
            throws DMSException {
        HttpSession session = req.getSession(false);
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new DMSException("You are not logged in");
        }
        return userId;
    }
}
