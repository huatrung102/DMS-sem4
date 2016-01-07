/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import manager.UserFacade;
import manager.UserFacadeLocal;

/**
 *
 * @author TrungHTH
 */
@ManagedBean
@SessionScoped
    public class WebSession implements Serializable {

        private static final String LOGIN_REDIRECT_URL = "login.html";
        private transient HttpSession session = null;
        private boolean registered;
        @EJB
        UserFacadeLocal userMan;

        public boolean isSignedIn() {
            boolean b = SessionUtils.isSignedIn();
            System.out.println("isSignedId = " + b);
            return b;
        }

        @PostConstruct
        private void init() {
            registered = (userMan.find(getUserId()) != null);
        }

        public void loginRedirect() throws IOException {
            FacesContext fc = FacesContext.getCurrentInstance();
            if (!isSignedIn()) {
                ExternalContext ext = fc.getExternalContext();
                ext.redirect(LOGIN_REDIRECT_URL);
            }
        }

        public boolean isRegistered() {
            return registered;
        }

        public void setRegistered(boolean registered) {
            this.registered = registered;
        }

        public Long getUserId() {
            return SessionUtils.getUserId();
        }

        public void resetSession() {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession mysession = (HttpSession) fc.getExternalContext().getSession(true);
            mysession.invalidate();
            fc.getExternalContext().getSession(true);
        }
        
        public void logout(){
        FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession mysession = (HttpSession) fc.getExternalContext().getSession(true);
            Enumeration<String> sessionAttr = mysession.getAttributeNames();
            while(sessionAttr.hasMoreElements()){
            String attrName = sessionAttr.nextElement();
                if (attrName.indexOf("'__'") != 0) {
                    session.removeAttribute(attrName);
                }
            }
            
        }
}
