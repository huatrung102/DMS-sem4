/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entity.Action;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import manager.ActionFacadeLocal;

/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("action")
public class ActionResource {
    ActionFacadeLocal actionFacade = lookupActionFacadeLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ActionResource
     */
    public ActionResource() {
    }
    @GET
    @Path("getAll")
    @Produces("application/json")
    public List<Action> getAll() {
        //TODO return proper representation object
        return actionFacade.getAll();
    }
    /**
     * Retrieves representation of an instance of services.ActionResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ActionResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    private ActionFacadeLocal lookupActionFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ActionFacadeLocal) c.lookup("java:global/DMS-Sem4/DMS-Sem4-ejb/ActionFacade!manager.ActionFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
