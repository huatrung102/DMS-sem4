/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import entity.DocumentDetail;
import entity.WorkFlow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import manager.WorkFlowFacadeLocal;

/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("workflow")
public class WorkflowResource {
    WorkFlowFacadeLocal workFlowFacade = lookupWorkFlowFacadeLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WorkflowResource
     */
    public WorkflowResource() {
    }
    @POST
    @Path("getNextStage")    
    @Produces(MediaType.APPLICATION_JSON)
    public WorkFlow getNextStage(String data,@Context HttpServletRequest req ) throws IOException
    { 
        LinkedTreeMap obj = new Gson().fromJson(data, LinkedTreeMap.class);        
        String temp = obj.get("workFlowId").toString();
        String step = obj.get("step").toString();
        WorkFlow workFlow = new Gson().fromJson(temp, WorkFlow.class);  
        return workFlowFacade.getObjectByStep(workFlow,Integer.parseInt(step));
      // return workFlowFacade.getById(workFlowId);
       //return null;    
    }
    @GET
    @Path("getById")
    @Produces("application/json")
    public WorkFlow getId(String workFlowId) {
        //TODO return proper representation object
       return workFlowFacade.getObjectById(workFlowId);
    }
    @POST
    @Path("getByAppId")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)   
    //@Consumes("application/json")   
    public WorkFlow getByAppId(String data) {
       // WorkFlow workflow ;
        Gson gson = new Gson();       
        LinkedTreeMap obj = gson.fromJson(data, LinkedTreeMap.class); 
        //TODO return proper representation object
       return workFlowFacade.getObjectByAppId(obj.get("appId").toString(),(double)obj.get("workFlowStep"));
    }
    /**
     * Retrieves representation of an instance of services.WorkflowResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of WorkflowResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    private WorkFlowFacadeLocal lookupWorkFlowFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (WorkFlowFacadeLocal) c.lookup("java:global/DMS-Sem4/DMS-Sem4-ejb/WorkFlowFacade!manager.WorkFlowFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
