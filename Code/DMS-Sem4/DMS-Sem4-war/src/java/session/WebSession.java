/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import entity.Users;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import manager.UsersFacade;
import manager.UsersFacadeLocal;

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
        private static final String userObject = "userObject";
        private static final String userId = "userId";
        
        @EJB
        UsersFacadeLocal userMan;

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

        public static UUID getUserId() {
            return SessionUtils.getUserId();
        }

        public void resetSession() {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession mysession = (HttpSession) fc.getExternalContext().getSession(true);
            mysession.invalidate();
            fc.getExternalContext().getSession(true);
        }
        
        public static void addEachWebSession(HttpSession mysession,String data){
            String key ="";
            Object value;
            Gson gson = new Gson();
            LinkedTreeMap obj = gson.fromJson(data, LinkedTreeMap.class);
            for(Object e : obj.keySet()){
                SessionListener.setSessionAttribute(mysession,String.valueOf(e),obj.get(e));
            }
        }
        
         public static void addUserSession(HttpSession mysession,Users user){
           SessionListener.setSessionAttribute(mysession, userObject, user);
           if(user != null)
            SessionListener.setSessionAttribute(mysession,userId, user.getUserId());
        }
        public static Users getUserSession(HttpSession mysession){
            return (Users) mysession.getAttribute(userObject);
        }
         
         
        public static void logout(HttpSession mysession){
      //  FacesContext fc = FacesContext.getCurrentInstance();
         //   HttpSession mysession = (HttpSession) fc.getExternalContext().getSession(true);
            Enumeration<String> sessionAttr = mysession.getAttributeNames();
            while(sessionAttr.hasMoreElements()){
            String attrName = sessionAttr.nextElement();
                if (attrName.indexOf("'__'") != 0) {
                    mysession.removeAttribute(attrName);
                }
            }
            
        }
}
