/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.bbodin.planner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author toky
 */

public class PlannerService implements Runnable {
    
    private final Socket  clientSocket;
    private final Planner planner;
    
    PlannerService(Planner planner, Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.planner = planner;
    }
    
   
    
    /**
     * 
     * processInput process an input string (inputLine) following the Planner Protocol (PP!)
     * and return an answer to this input given the result of the operation.
     * 
     * What is wrong with this function ?
     * What is the limitation of this protocol ?
     * 
     * @param inputLine
     * @return
     * @throws Exception 
     */
    private String processInput(String inputLine) throws Exception {
        String[] fields = inputLine.split(";"); 
        switch (fields[0]) {
            case "EXIT" : return "";
            case "GET" : return this.planner.toXML();
            case "ADD" : 
                      switch (fields[1]) {
                           case "BOARD" : 
                               String board_name = fields[2];
                               System.err.println(this.hashCode() + ": Create board " + board_name );
                               this.planner.addBoard(new Board(board_name)); 
                               return "DONE";
                           case "SECTION" : 
                               String section_board_name = fields[2];
                               String section_name = fields[2];
                               System.err.println(this.hashCode() + ": Create section " + section_name + " in the board " + section_board_name );
                               Board sb = this.planner.getBoard(section_board_name);
                               
                               if (sb == null) {
                                   return "ERROR BOARD NOT FOUND.";
                               }
                               Section s = new Section (section_name);
                               sb.addSection(s);
                               return "DONE";
                           case "TASK" : 
                               Board tb = this.planner.getBoard(fields[2]);
                               if (tb == null) {
                                   return "ERROR BOARD NOT FOUND.";
                               }
                               Section ts = tb.getSection(fields[3]);
                               if (ts == null) {
                                   return "ERROR SECTION NOT FOUND.";
                               }
                               String name =  fields[4];
                               String description =  fields[5];
                               
                               Date start =  Date.from(Instant.ofEpochSecond(Long.parseLong(fields[6])));
                               
                               if (start == null) {
                                   return "ERROR INVALID start.";
                               }

                               Duration duration = Duration.ofSeconds(Long.parseLong(fields[7]));
                               
                               if (duration == null) {
                                   return "ERROR INVALID start.";
                               }
                               
                               Date deadline =  Date.from(Instant.ofEpochSecond(Long.parseLong(fields[8])));
                               
                               if (deadline == null) {
                                   return "ERROR INVALID start.";
                               }
                               
                               System.err.println(this.hashCode() + ": Create the task " + name  + ", " + description + ", " + start + ", " + duration + ", " + deadline);
                               Task tt = new Task(name, description , start , duration, deadline);
                               ts.addTask(tt);
                               return "DONE";
                           default :
                               return "ERROR";
                      }
            default    : return "ERROR";
        }

    }
    @Override
    public void run() {
        try {
            System.err.println(this.hashCode() + ": Start communication with " + clientSocket.getInetAddress());
            PrintWriter   out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
            System.err.println(this.hashCode() + ": Received '" + inputLine + "'");
                try {
                    outputLine = this.processInput(inputLine);
                    out.println(outputLine);
                    if (outputLine.equals("")) {
                        out.println("GOOD BYE");
                        break;
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(PlannerService.class.getName()).log(Level.SEVERE, null, ex);
                    out.println("ERROR");
                }
            }
            
            
            System.err.println(this.hashCode() + ": End of communication.");
            
            in.close();
            out.close();
            
            clientSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(PlannerServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}