/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import entity.DocumentDetail;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import manager.DocumentDetailFacadeLocal;
import manager.DocumentFacadeLocal;

/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("document")
public class DocumentResource {
    DocumentDetailFacadeLocal documentDetailFacade = lookupDocumentDetailFacadeLocal();
    DocumentFacadeLocal documentFacade = lookupDocumentFacadeLocal();

    @Context
    private UriInfo context;
   
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String data,@Context HttpServletRequest req ) throws IOException
    {       
       // DocumentDetail docDetail = 
        
        documentDetailFacade.create(null);
            return "";
    }
    
    @POST
    @Path("getDefault")    
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentDetail getDefault(@Context HttpServletRequest req ) throws IOException
    {       
       // DocumentDetail docDetail = 
        
       return documentDetailFacade.getDefault();
           
    }
    /**
     * Creates a new instance of DocumentResource
     * @param uploadStream
     * @param fileDetail
     * @return 
     * @throws java.io.IOException
     */
    /*
    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    public String save(String document,String user,String detail,@Context HttpServletRequest req ) throws IOException
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
    /*
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    
    public String  uploadStream(@FormDataParam("file") InputStream uploadStream
            ,@FormDataParam("file") FormDataContentDisposition fileDetail
             ) throws IOException
    {
       String fileLocation = "";
            
                    //saving file  
            try {  
                fileLocation = "d://Temp//" + fileDetail.getFileName();  
                FileOutputStream out = new FileOutputStream(new File(fileLocation));  
                int read = 0;  
                byte[] bytes = new byte[1024];  
                out = new FileOutputStream(new File(fileLocation));  
                while ((read = uploadStream.read(bytes)) != -1) {  
                    out.write(bytes, 0, read);  
                }  
                out.flush();  
                out.close();  
            } catch (IOException e) {e.printStackTrace();}  
            String output = "File successfully uploaded to : " + fileLocation;  
            return "ok";  
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

    private DocumentFacadeLocal lookupDocumentFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (DocumentFacadeLocal) c.lookup("java:global/DMS-Sem4/DMS-Sem4-ejb/DocumentFacade!manager.DocumentFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private DocumentDetailFacadeLocal lookupDocumentDetailFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (DocumentDetailFacadeLocal) c.lookup("java:global/DMS-Sem4/DMS-Sem4-ejb/DocumentDetailFacade!manager.DocumentDetailFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
