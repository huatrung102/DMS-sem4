/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservice;

import additional.DMSException;
import com.google.gson.Gson;
import entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import json.entity.JsonResponse;
import json.utility.DMSExceptionWrapper;
import json.utility.ResponseConstants;
import json.utility.ResponseWrapper;
import session.SessionListener;
import session.SessionUtils;
import session.WebSession;
import test.TestClass;

/**
 *
 * @author TrungHTH
 */
@Stateless
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {
    @PersistenceContext(unitName = "DMS-Sem4-warPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("json")
    public String getJson() {
        Gson gson = new Gson();

        return gson.toJson("123");
    }
    
    
    
    @POST
    @Override
    @Path("#create/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public void create(User entity) {
        super.create(entity);
    }
    
    @POST
    @Path("logout")
    
    public String logout(@Context HttpServletRequest req) {
        new WebSession().logout();
        try {
            Long userId = SessionUtils.getCurrentUserIdThrowingException(req);
            SessionListener.setSessionAttribute(req.getSession(true), "userId", null);
            JsonResponse<String> jr = new JsonResponse<String>(ResponseConstants.OK, null, ResponseConstants.YES);
            return ResponseWrapper.getJsonResponse(jr);
        } catch (DMSException e) {
            return DMSExceptionWrapper.wrapException(e);
        }
    }
    
    @PUT
    @Path("#edit/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public void edit(@PathParam("id") String id, User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public User find(@PathParam("id") String id) {
        return super.find(id);
    }
    
    @GET
    @Path("isAuthenticated")
    @Produces("application/json")
    public String isAuthenticated() {
        User userSession = TestClass.getTestUser();
        return new Gson().toJson(userSession);
    }
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
