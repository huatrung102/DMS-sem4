/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.ws.rs.core.MediaType;
import manager.DocumentFacadeLocal;

/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("document")
public class DocumentResource {

    @Context
    private UriInfo context;
    @EJB
    DocumentFacadeLocal docLocal;
    /**
     * Creates a new instance of DocumentResource
     */
    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public String save(String document,String user,String detail,@Context HttpServletRequest req ) throws IOException
    {
       /*
            try {
                 DataInputStream dis = new DataInputStream(uploadStream);
                System.out.println(dis.readByte());
                System.out.println("Payload size="+uploadStream.available());
        return "Payload size="+uploadStream.available();
            } catch (Exception e) {
               // break;
            }
        */
        return "";
    }
    /*
    @POST
    @Path("upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public String uploadStream(@FormDataParam("file") InputStream uploadStream,@FormDataParam("file") FormDataContentDisposition fileDetail,@Context HttpServletRequest req ) throws IOException
    {
       
            try {
                 DataInputStream dis = new DataInputStream(uploadStream);
                System.out.println(dis.readByte());
                System.out.println("Payload size="+uploadStream.available());
        return "Payload size="+uploadStream.available();
            } catch (Exception e) {
               // break;
            }
        
        return "";
    }
    */
    public DocumentResource() {
    }

    /**
     * Retrieves representation of an instance of services.DocumentResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of DocumentResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
