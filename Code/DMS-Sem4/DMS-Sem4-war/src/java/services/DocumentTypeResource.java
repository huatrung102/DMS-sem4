/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entity.DocumentType;
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
import manager.DocumentTypeFacadeLocal;

/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("documentType")
public class DocumentTypeResource {
    @EJB
    DocumentTypeFacadeLocal docTypeLocal;
    
    @GET
    @Path("getAll")
    @Produces("application/json")
    public List<DocumentType> getAll() {
     //   new ObjectMapper().writeValueAsString(item);
        return docTypeLocal.getAll();

    }
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DocumentTypeResource
     */
    public DocumentTypeResource() {
    }

    /**
     * Retrieves representation of an instance of services.DocumentTypeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of DocumentTypeResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
