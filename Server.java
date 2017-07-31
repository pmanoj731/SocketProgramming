
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
  public static void main(String[] args) throws Exception {
    HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
    HandlerInfo hi=new HandlerInfo();
    httpServer.createContext("/info", hi);
    HandlerGet hg=new HandlerGet();
    httpServer.createContext("/get", hg);
    httpServer.setExecutor(null); 
    System.out.println("Server started");
    httpServer.start();
  }
}