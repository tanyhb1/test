/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package io.github.bbodin.planner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;





/**
 *
 * @author toky
 */
public class PlannerServer {
    
    private final Planner planner;
    private final int port;
    private ServerSocket serverSocket = null;
    
    PlannerServer(Planner p ) {
        this(p,4444);
    }
    PlannerServer(Planner p , int port ) {
        this.planner = p;
        this.port = port;
    }
    
    boolean startServer () {
        
        assert (serverSocket == null); // This server cannot be started more than once.
        
        // Phase 1, register the port.
        try {
            serverSocket = new ServerSocket(port);
            System.err.println("Listen on port: " + port);
        } catch (IOException e) {
            System.err.println("Listen failed.");
            return false;
        }
        
        // Phase 1, wait for clients
        try {
            
            while (true) {
            
                Socket clientSocket = serverSocket.accept();
                if (clientSocket != null) {
                    System.err.println("New client");
                    Thread t = new Thread(new PlannerService(planner,clientSocket));
                    t.start();
                } else {
                    System.err.println("Error during the client connection.");
                    return false;
                }
         
            }
            
        } catch (IOException e) {
            System.err.println("Unexpected Error whinin the server.");
            return false;
        }
    }
    
    
    
    
    
    
    
}
