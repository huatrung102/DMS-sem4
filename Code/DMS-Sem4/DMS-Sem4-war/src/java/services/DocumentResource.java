/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import entity.Department;
import entity.DocumentDetail;
import entity.Users;
import entity.WorkFlow;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import json.entity.JsonResponse;
import json.utility.ResponseConstants;
import json.utility.ResponseWrapper;
import manager.ApplicationFacadeLocal;
import manager.DocumentDetailFacadeLocal;
import manager.DocumentFacadeLocal;
import manager.WorkFlowFacadeLocal;


/**
 * REST Web Service
 *
 * @author TrungHTH
 */
@Path("document")
public class DocumentResource {
    ApplicationFacadeLocal applicationFacade = lookupApplicationFacadeLocal();
    WorkFlowFacadeLocal workFlowFacade = lookupWorkFlowFacadeLocal();
  
    
    DocumentDetailFacadeLocal documentDetailFacade = lookupDocumentDetailFacadeLocal();
    DocumentFacadeLocal documentFacade = lookupDocumentFacadeLocal();
    
    
    @Context
    private UriInfo context;
    
    public DocumentResource() {
       
    }
    @POST
    @Path("getNextStageDetail")    
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentDetail getNextStageDetail(String data,@Context HttpServletRequest req ) throws IOException
    { 
        WorkFlow workFlowNext = null;
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();
        LinkedTreeMap obj = gson.fromJson(data, LinkedTreeMap.class);        
        String dataJson = obj.get("data").toString();
        String nextStep = obj.get("nextStep").toString();
        DocumentDetail docDetail = gson.fromJson(dataJson, DocumentDetail.class);
        if(docDetail != null){
            workFlowNext = workFlowFacade.getObjectByStep(docDetail.getWorkFlowId(),Double.valueOf(nextStep).intValue());
            docDetail.setWorkFlowId(workFlowNext);
        }
     //   return null;
       // boolean flag = documentFacade.removeDocument(id);
     //    JsonResponse<String> jr = new JsonResponse<String>(ResponseConstants.OK, null, String.valueOf(flag));
     //    return ResponseWrapper.getJsonResponse(jr);
           return docDetail;
    }
    
    @POST
    @Path("remove/{id}")    
    @Produces(MediaType.APPLICATION_JSON)
    public String remove(@PathParam("id") String id,@Context HttpServletRequest req ) throws IOException
    { 
       // LinkedTreeMap obj = new Gson().fromJson(id, LinkedTreeMap.class);        
       // String temp = obj.get("data").toString();
        boolean flag = documentFacade.removeDocument(id);
         JsonResponse<String> jr = new JsonResponse<String>(ResponseConstants.OK, null, String.valueOf(flag));
         return ResponseWrapper.getJsonResponse(jr);
           
    }
    
    @GET
    @Path("getById/{id}")    
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentDetail getById(@PathParam("id") String id,@Context HttpServletRequest req ) throws IOException
    { 
       // LinkedTreeMap obj = new Gson().fromJson(id, LinkedTreeMap.class);        
       // String temp = obj.get("data").toString();
        DocumentDetail docDetail = documentDetailFacade.getById(id);
        if(docDetail.getDocDetailUserReceive()== null)
            docDetail.setDocDetailUserReceive(new Users(1));
        if(docDetail.getDocDetailDepReceive() == null)
            docDetail.setDocDetailDepReceive(new Department(1));
       return docDetail;
           
    }
    
    
    
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String create(String data,@Context HttpServletRequest req ) throws IOException
    {       
        //2016-01-13T21:10:08.696
               
        //Gson gson1 = Converters.registerDateTime(new GsonBuilder()).create();
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();
      //  Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new JsonDateDeserializer()).create();
       /*
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create();
        */
        LinkedTreeMap obj = gson.fromJson(data, LinkedTreeMap.class);        
        String temp = obj.get("data").toString();
      //  obj = gson.fromJson(temp, LinkedTreeMap.class);
        
        DocumentDetail docDetail = gson.fromJson(temp, DocumentDetail.class);
        boolean flag = true;
        try {
           documentFacade.createDocument(docDetail.getDocId());
        } 
        catch (Exception e) {
           flag = false;
        } 
        if(flag){
            try {
                docDetail.setDocDetailDepReceive(null);
                docDetail.setDocDetailUserReceive(null);
                documentDetailFacade.createDocument(docDetail);
            } catch (Exception e) {
                
            }
        }
        
        
        /*
         DocumentDetail defaultTest = new DocumentDetail(1);
        
        JsonReader reader = new JsonReader(new StringReader(data));
        reader.setLenient(true);

         
        String json = gson.toJson(defaultTest);
        DocumentDetail revert = gson.fromJson(json, DocumentDetail.class); 
        LinkedTreeMap docDetail = gson.fromJson(data, LinkedTreeMap.class); 
        DocumentDetail detail = gson.fromJson(data, DocumentDetail.class);
        if(obj != null){
        }
        if(docDetail != null){
        }
        */
     //   documentDetailFacade.create(null);
             JsonResponse<String> jr = new JsonResponse<String>(ResponseConstants.OK, null, docDetail.getDocDetailId());
            return ResponseWrapper.getJsonResponse(jr);
    }
    
    @POST
    @Path("getDefault")    
    @Produces(MediaType.APPLICATION_JSON)
    public DocumentDetail getDefault(@Context HttpServletRequest req ) throws IOException
    {       
       // DocumentDetail docDetail = 
        DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        
        Date formattedDate;
        try {
             formattedDate = dateFormat.parse(dateFormat.format(new Date()));
           // Date day = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).parse(new Date().toString());
            
        } catch (Exception e) {
        }
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

    private WorkFlowFacadeLocal lookupWorkFlowFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (WorkFlowFacadeLocal) c.lookup("java:global/DMS-Sem4/DMS-Sem4-ejb/WorkFlowFacade!manager.WorkFlowFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ApplicationFacadeLocal lookupApplicationFacadeLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ApplicationFacadeLocal) c.lookup("java:global/DMS-Sem4/DMS-Sem4-ejb/ApplicationFacade!manager.ApplicationFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
