/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import entity.Users;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import manager.UsersFacadeLocal;
import session.SessionListener;
import session.WebSession;

/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("users")
public class UsersResource {
    UsersFacadeLocal usersFacade = lookupUsersFacadeLocal();

    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of UsersResource
     */
    public UsersResource() {
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")    
    @Path("getUserById")
    public Users getUserById(@Context HttpServletRequest req) {
        UUID userId = WebSession.getUserId();
        return usersFacade.getUserById(userId.toString());
    }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")    
    @Path("login")
    public Users login(String data ,@Context HttpServletRequest req) {
        //TODO return proper representation object
        Users user = null;
        Gson gson = new Gson();       
        LinkedTreeMap obj = gson.fromJson(data, LinkedTreeMap.class);        
        user = obj != null ? usersFacade.login(obj.get("username").toString(),obj.get("password").toString()) : null;        
        WebSession.addUserSession(req.getSession(true),user);
       
       return user;
    }
    
    @GET
    @Produces("application/json")    
    @Path("logout")
    public String logout(@Context HttpServletRequest req) {
       //call remove all session 
        WebSession.logout(req.getSession());
        //call remove userid test it
        SessionListener.setSessionAttribute(req.getSession(true), "userId", null);
        return "#login";
    }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")    
    @Path("isAuthenticate")
    //add to session web
    public Users isAuthenticate(String data ,@Context HttpServletRequest req) {
       Users user = WebSession.getUserSession(req.getSession());
       user = user == null || "".equals(user.getUserId()) ? usersFacade.getUserById("0BEE30CF-CA7D-4E9A-B504-38D4CEAE3327") : user;
       return user;
       
    }
    
    
    
    
    /**
     * Retrieves representation of an instance of services.UsersResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of UsersResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    private UsersFacadeLocal lookupUsersFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (UsersFacadeLocal) c.lookup("java:global/DMS-Sem4/DMS-Sem4-ejb/UsersFacade!manager.UsersFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
