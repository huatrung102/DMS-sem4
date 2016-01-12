package servlet;

import entity.Users;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import json.entity.JsonResponse;
import json.utility.ResponseConstants;
import json.utility.ResponseWrapper;
import manager.DocumentDetailFacadeLocal;
import manager.DocumentFacadeLocal;
import manager.UsersFacadeLocal;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import session.SessionListener;
import session.SessionUtils;
import utility.FileUtils;
import utility.StringUtils;
import utility.UploadedFile;

/*
import ru.easybet.core.jpa.entity.Attachment;
import ru.easybet.core.jpa.entity.User;
import ru.easybet.core.utils.FileUtils;
import ru.easybet.core.utils.StringUtils;
import ru.easybet.core.utils.UploadedFile;
import ru.easybet.json.entity.JsonResponse;
import ru.easybet.json.utils.ResponseConstants;
import ru.easybet.json.utils.ResponseWrapper;
import ru.easybet.web.session.SessionListener;
*/
/**
 *
 * @author trunghth
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
public class FileUploadServlet extends HttpServlet {
    @EJB
    private DocumentFacadeLocal documentFacade;
    @EJB
    private DocumentDetailFacadeLocal documentDetailFacade;
    @EJB
    private UsersFacadeLocal usersFacade;

    private static final Logger log = Logger.getLogger(FileUploadServlet.class);
    
    private static final String SERVLET_ENCODING = "ISO-8859-1";
    
    public static final String FILES_PARAM = "file";
    public static final String COMPRESS_PARAM = "compress";
    
    
    
    
    
   
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     * <p/>
     * @param request  servlet request
     * @param response servlet response
     * <p/>
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  Long userId = (Long) SessionListener.getSessionAttribute(request.getSession(), "userId");
     //   String userId = SessionUtils.getCurrentUserId(request).toString();
       String userId = "0bee30cf-ca7d-4e9a-b504-38d4ceae3327"; 
        Users u = usersFacade.getUserById(userId);

         if(u == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            System.out.println("doPost(): unauthorized attempt to upload file(s).");
            return;
        }
        Object o = request.getAttribute(FILES_PARAM);
        System.out.println("map = " + request.getParameterMap());
//        System.out.println("files[] = " + o.toString());
        
        if((o == null) || !(o instanceof FileItem) && !(o instanceof List)) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("doPost(): failed to upload files or no files selected.");
//            log.debug("doPost(): failed to upload files or no files selected.");
            return;
        }
          
        ServletOutputStream out = response.getOutputStream();
        try {
            response.setContentType("application/xml");
        //    processFiles(u.getId(), fileItems, compress, out);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println("");
//            log.error("doPost(): unable to process uploaded files.", ex);
        } finally {
            out.close();
        }
    }
    
    private String getValidParameter(HttpServletRequest request, String name) {
        return StringUtils.decode(StringUtils.getValidString(request.getParameter(name)), SERVLET_ENCODING);
    }
    
    private String getValidParameter(HttpServletRequest request, String name, String defualtValue) {
        final String param = getValidParameter(request, name);
        return param.isEmpty() ? defualtValue : param; 
    }    

    /**
     * Returns a short description of the servlet.
     * <p/>
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for uploading files.";
    }// </editor-fold>

    private void processFiles(String userId, List<FileItem> fileItems, boolean compress, ServletOutputStream out) throws Exception {
        Users u = usersFacade.getUserById(userId);
        if(u == null) {
            throw new IllegalArgumentException("processFiles(): there is no user with ID="+userId);
        }
        List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>(fileItems.size());
        for(FileItem fi : fileItems) {
            File tmpFile = File.createTempFile("upload_", ".tmp"+FileUtils.extractExtention(fi.getName()));
            fi.write(tmpFile);
            String contentType = fi.getContentType();
            if(StringUtils.isEmpty(contentType) || "application/octet-stream".equalsIgnoreCase(contentType))
                contentType = new MimetypesFileTypeMap().getContentType(fi.getName().toLowerCase());
            System.err.println("uploaded: "+contentType+" "+tmpFile.getName());
            uploadedFiles.add(new UploadedFile(fi.getName(), contentType, tmpFile));
        }
        System.out.println("uploadedFiles = " + uploadedFiles);
        
//        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//        out.println("<files size=\""+attachments.size()+"\">");
       
        
        
        JsonResponse<String> js = new JsonResponse<String>(ResponseConstants.OK, null, "");
        
       
        System.out.println(ResponseWrapper.getJsonResponse(js));
//        out.println("</files>");
    }
}
