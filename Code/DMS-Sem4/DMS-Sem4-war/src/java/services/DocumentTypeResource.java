/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import entity.DocumentType;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
import manager.DocumentTypeFacadeLocal;

/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("documentType")
public class DocumentTypeResource {
    DocumentTypeFacadeLocal documentTypeFacade = lookupDocumentTypeFacadeLocal();
    
    
    @GET
    @Path("getAll")
    @Produces("application/json")
    public List<DocumentType> getAll() {
     //   new ObjectMapper().writeValueAsString(item);
        return documentTypeFacade.getAll();

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

    private DocumentTypeFacadeLocal lookupDocumentTypeFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (DocumentTypeFacadeLocal) c.lookup("java:global/DMS-Sem4/DMS-Sem4-ejb/DocumentTypeFacade!manager.DocumentTypeFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
