/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.bbodin.planner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author toky
 */
public class Controller implements ActionListener {

    private final Planner planner;
    
    Controller(Planner p) {
    this.planner = p;
    }
    
    Planner getPlanner() {
       return planner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Scheduler scheduler = new Scheduler(this.planner);
        System.out.println("Scheduling done.");
    }
    
    
    
}
