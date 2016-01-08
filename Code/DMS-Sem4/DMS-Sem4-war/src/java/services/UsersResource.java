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
import javax.ejb.EJB;
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

    @Context
    private UriInfo context;
    @EJB
    UsersFacadeLocal userLocal;
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
        return userLocal.getUserById(userId.toString());
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
        user = obj != null ? userLocal.login(obj.get("username").toString(),obj.get("password").toString()) : null;        
        WebSession.addUserSession(req.getSession(true),user);
       
       return user;
    }
    
    @GET
    @Produces("application/json")    
    @Path("logout")
    public void logout(@Context HttpServletRequest req) {
       //call remove all session 
        WebSession.logout(req.getSession());
        //call remove userid test it
        SessionListener.setSessionAttribute(req.getSession(true), "userId", null);
    }
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")    
    @Path("login")
    //add to session web
    public Users isAuthenticate(String data ,@Context HttpServletRequest req) {
       return WebSession.getUserSession(req.getSession());
       
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
}
