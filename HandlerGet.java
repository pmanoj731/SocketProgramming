

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class HandlerGet implements HttpHandler {
	String serverFolder="C:\\temp\\serverfolder";  
	
    public String extractFilePath(String parameters){
		    Map<String, String> params = new HashMap<String, String>();
		    for (String values : parameters.split("&")) {
		        String keyValue[] = values.split("=");
		        if (keyValue.length>1) {
		            params.put(keyValue[0], keyValue[1]);
		        }else{
		            params.put(keyValue[0], "");
		        }
		    }
		    String filePath;
		    if(params.keySet().contains("file"))
		    {
		    	filePath=params.get("file").trim();
		    	File file=new File(serverFolder+"\\"+filePath);
		    	if(file.exists())
		    		return filePath;
		    }
			return "404";
		}

    private String getCType(String name)
    {
    	switch(name){
    	case ".htm":
    	case ".html":return "text/html";
    	case ".gif":return "image/gif";
    	case ".jpg":
    	case ".jpeg": return "image/jpeg";
    	case ".pdf": return "application/pdf";
    	case ".rar": return "application/x-rar-compressed";
    	case ".zip": return "application/zip";
    	case ".txt": return "text/plain";
    	default:return "404";
    	}
     /* if (name.endsWith(".htm") ||name.endsWith(".html"))
        return "text/html";
      else if (name.endsWith(".gif"))
        return "image/gif";
      else if (name.endsWith(".jpg") ||name.endsWith(".jpeg"))
        return "image/jpeg";
      else if(name.endsWith(".pdf"))
    	  return "application/pdf";
      else if(name.endsWith(".txt"))
    	  return "text/plain";
      else if(name.endsWith(".rar"))
    	  return "application/x-rar-compressed";
      else if(name.endsWith(".zip"))
    	  return "application/zip";
      return "404";*/
    }
    
    public void handle(HttpExchange t) throws IOException {
     
      Headers h = t.getResponseHeaders();
      String name=extractFilePath(t.getRequestURI().getQuery());
      boolean check=false;
      if(!name.equals("404"))
      {
    	  String cType=name.substring(name.lastIndexOf(".")).trim().toLowerCase();
    	  cType=getCType(cType);
    	  if(!cType.equals("404"))
    	  {
    		  h.add("Content-Type",cType);
    		  File file = new File(serverFolder+"\\"+name);
    		  byte [] bytearray  = new byte [(int)file.length()];
    		  FileInputStream fis = new FileInputStream(file);
    	      BufferedInputStream bis = new BufferedInputStream(fis);
    	      bis.read(bytearray, 0, bytearray.length);
    	      t.sendResponseHeaders(200, file.length());
    	      OutputStream os = t.getResponseBody();
    	      os.write(bytearray,0,bytearray.length);
    	      os.close();
    	  }
    	  else
    		  check=true;
      }
      else
    	  check=true;
      
      if(check)
      {
    	  String response = "<H1>404:File not Found</H1>";
          t.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, response.length());
          OutputStream os = t.getResponseBody();
    	  os.write(response.getBytes());
    	  os.close();
      }
      // ok, we are ready to send the response.
      /*String response = "<b>File not Found<b>";
      t.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, response.length());
      OutputStream os = t.getResponseBody();
	  os.write(response.getBytes());
	  os.close();*/
    }
}