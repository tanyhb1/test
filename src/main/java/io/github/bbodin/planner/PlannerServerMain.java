/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.bbodin.planner;

/**
 *
 * @author toky
 */
public class PlannerServerMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Planner p = Planner.demo();
        PlannerServer server = new PlannerServer(p,5555);
        boolean res = server.startServer();
        System.out.println("Server is finished, returned " + res);
    }
    
    
    
    
}
