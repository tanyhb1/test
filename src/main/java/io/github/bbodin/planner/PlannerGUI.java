/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.bbodin.planner;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author toky
 */
public class PlannerGUI extends javax.swing.JFrame {

    private final Controller controller;

    private PlannerGUI(Controller c) {
        this.controller = c;
        initComponents();
    }

    private JPanel generateSectionPanel (Section s) {
        JPanel sectionPanel = new JPanel(new BorderLayout());
                
        sectionPanel.setBackground(s.getColor());
        JLabel sectionLabel = new javax.swing.JLabel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionLabel.setText(s.getName());
        sectionPanel.add(sectionLabel);
                
                
        for (Task t : s.getTasks()) {
                
            JButton taskButton = new javax.swing.JButton();
            taskButton.setText(t.getName() + "\n" + t.getDescription());
            sectionPanel.add(taskButton);
        }
            return sectionPanel;    
    }
    
    private JPanel generateBoardPanel (Board b) {
           JPanel boardPanel = new JPanel(new BorderLayout());
            boardPanel.setName(b.getName());
            boardPanel.setLayout(new GridLayout(1,b.getSections().size()));
            for (Section s : b.getSections()) {
                JPanel sectionPanel = generateSectionPanel(s);
                boardPanel.add(sectionPanel);
            }
            return boardPanel;
    }
    
    
    private JTabbedPane generatePlannerPanel (Planner p) {
        JTabbedPane boardsPanel = new javax.swing.JTabbedPane();


        
        for (Board b : p.getBoards()) {
            JPanel boardPanel = generateBoardPanel (b);
            boardsPanel.add(boardPanel);
        }
        
        return boardsPanel;
    }
    
    private void initComponents() {
        
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(0,1));

        JTabbedPane boardsPanel =  generatePlannerPanel (this.controller.getPlanner()) ;
        getContentPane().add(boardsPanel);
             
        
        JButton scheduleButton = new javax.swing.JButton();
        scheduleButton.setText("Schedule");
        scheduleButton.addActionListener(this.controller);
        getContentPane().add(scheduleButton);
           
        pack();
        
    }
    
                    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlannerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlannerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlannerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlannerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Planner p = Planner.demo();
                Controller c = new Controller (p);
                new PlannerGUI(c).setVisible(true);
            }
        });
        
        
        
    }                
}
