import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Servlet to handle File upload request from Client
 * @author Javin Paul
 */
public class FileUploadHandler extends HttpServlet {
	
	//System.getProperty("user.dir");
    //private final String UPLOAD_DIRECTORY = "C:/ApacheTomcat9/webapps/upload2/data"; // use forward slash, by Andy
	
//File currentDirFile = new File(".");
//String helper = currentDirFile.getAbsolutePath();
//		String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
	private final String lastFolder="customer_data";
	private final String UPLOAD_DIRECTORY =System.getProperty("user.dir")+"\\"+lastFolder;
	//private final String UPLOAD_DIRECTORY =System.getProperty("user.dir")+"\\customer_datax";

			/**public static String getURLWithContextPath(HttpServletRequest request) {
   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
}*/


/**	if (pathing.exists() && pathing.isDirectory())
            System.out.println("Directory already exists");
        } else {
                        Files.createDirectory(pathing);
            System.out.println("Directory created");
        }*/
		
	//	File file = new File("/data/test.txt");
	//String relativePath="/data/test.txt";
	//String absoluteDiskPath = getServletContext().getRealPath(relativePath);

		//	ServletContext servletContext = getServletContext();
		//String contextPath = servletContext.getRealPath(File.separator);
		//PrintWriter out = response.getWriter();
		
		
//private final String UPLOAD_DIRECTORY=contextPath;
	
  //private final String UPLOAD_DIRECTORY = file.getAbsolutePath();
  
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
	  response.setContentType("text/html");
	response.setBufferSize(8192);
	PrintWriter out = response.getWriter();
	
	
	//if we put next 9 lines outside of doPost function, it will fail.
//String UPLOAD_DIRECTORY2 =System.getProperty("user.dir")+"\\customer_datax";
	String UPLOAD_DIRECTORY_real =UPLOAD_DIRECTORY;
	String	 UPLOAD_DIRECTORY_real_path=UPLOAD_DIRECTORY_real.replace("\\", "\\\\");
	Path pathing = Paths.get(UPLOAD_DIRECTORY_real_path);
	//String bib001=request.getParameter("marc001list"); //this syntax should not be used in this file upload  handling.
	
	
	
        if (!Files.exists(pathing)) {
            Files.createDirectory(pathing);
            //System.out.println("Directory created");
        } else {
           // System.out.println("Directory already exists");
        }
	
	
	 // UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request); 
//long longValue = ParamUtil.getLong(uploadRequest , param); 
	
        //process only if its multipart content
		String pp="";  //pp variable is outside of try catch and if below.
	    String listFileName="";
		
        if(ServletFileUpload.isMultipartContent(request)){
		
            try {
				
				String mm=""; //without this line, mname variable will not be available outside of for loop
				
				String nn="";
				
			
				
				
                List<FileItem> multiparts = new ServletFileUpload(
                                         new DiskFileItemFactory()).parseRequest(request);
              
                for(FileItem item : multiparts){
                    if(item.isFormField())
					{
						String fieldName = item.getFieldName();
						String fieldValue = item.getString();
						nn=fieldValue; //this should be the field of bib001list value if my guess is correct.
						pp=fieldValue; //when pp variable is defined in outside of if clause, it works and see below.
						
						if (fieldName.equals("list_FileName")){
							listFileName=fieldValue;
						}
					}
					else
					{
                        String mname = new File(item.getName()).getName();
							//out.println("<html>"+mname+"</html>"); //added by Adny
							mm=mname;

                        item.write( new File(UPLOAD_DIRECTORY + File.separator + mname));
                    }
                }
			 String uring=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			 request.setAttribute("URI", uring);

			 
           //String xname=name.toString();
               //File uploaded successfully
               request.setAttribute("message", "File Uploaded Successfully");
			   request.setAttribute("filename", mm);
			   String pfolderandname=UPLOAD_DIRECTORY+"\\";
			    String pfolderandnamex=pfolderandname.replace("\\", "/");
				
			   request.setAttribute("pfolderandname", pfolderandnamex);
			   request.setAttribute("folder", UPLOAD_DIRECTORY);
			  String folderandname=UPLOAD_DIRECTORY+"\\"+mm;
			  
			  
			  String folderandnamex=folderandname.replace("\\", "/");
			   request.setAttribute("folderandname", folderandnamex);
			   request.setAttribute("lastfolder", lastFolder); //this is like customer_datax
			  
			   
			   request.setAttribute("UPLOAD_DIRECTORY_name", UPLOAD_DIRECTORY);
			   request.setAttribute("UPLOAD_DIRECTORY_real_name", UPLOAD_DIRECTORY_real);
            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }          
         
        }else{
            request.setAttribute("message",
                                 "Sorry this Servlet only handles file upload request");
        }
    
	 
	//request.setAttribute("bib001field", bib001); //no working in this httpservlet request handling procedure.
	     if (pp.equals("yes"))
		 {
			  request.getRequestDispatcher("/resultProcessing.jsp").forward(request, response);
		 }else{
			 	String listFileNamex=UPLOAD_DIRECTORY+"\\"+listFileName;//if not adding directory,it will fail later.
				String listFileNamex_modified=listFileNamex.replace("\\", "/");
				request.setAttribute("listfilename", listFileNamex_modified);
			 request.getRequestDispatcher("/result.jsp").forward(request, response);
		 }
        
     
    }
  
}