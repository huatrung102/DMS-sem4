/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manager;
import com.sun.xml.ws.api.tx.at.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Singleton;
/**
 *
 * @author TrungHTH
 */
@Singleton
public class SessionManager {

    private final List<String> sessions = Collections.synchronizedList(new ArrayList<String>());
    
    public static final int SESSION_TIMEOUT = 15000;
    
    
    public void addSession(String session) {
        sessions.add(session);
        System.out.println("addSession(): Session added. Online: " + sessions.size());
    }

    
    public void removeSession(String session) {
        sessions.remove(session);
        System.out.println("removeSession(): Session removed. Online: " + sessions.size());
    }

    
    public void removeAll() {
        sessions.clear();
        System.out.println("removeAll(): Cleared. Online: " + sessions.size());
    }

    
    @Transactional(value = Transactional.TransactionFlowType.SUPPORTS)
    public List<String> getSessions() {
        System.out.println(">> getSessions() returns " + sessions);
        return sessions;
    }
}
