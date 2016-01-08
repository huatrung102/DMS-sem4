/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entity.Department;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import manager.DepartmentFacadeLocal;

/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("department")
public class DepartmentResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DepartmentResource
     */
    public DepartmentResource() {
    }
    
    
    @EJB
    DepartmentFacadeLocal depLocal;
    
    @GET
    @Path("getAll")
    @Produces("application/json")
    public List<Department> getAll() {     
      
        return depLocal.getAll();

    }

    /**
     * Retrieves representation of an instance of services.DepartmentResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of DepartmentResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
