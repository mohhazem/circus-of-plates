package oop.game.myfirstgame;

import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.GameEngine.GameController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import oop.game.world.Circus;

public class MyFirstGame {

    public static void main(String[] args) {
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("options");
        JMenuItem easyMenuItem = new JMenuItem("Easy Game");
        JMenuItem mediumMenuItem = new JMenuItem("Medium Game");
        JMenuItem hardMenuItem = new JMenuItem("Hard Game");
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        
        menu.add(easyMenuItem);
        menu.add(mediumMenuItem);
        menu.add(hardMenuItem);
        menu.addSeparator();
        menu.add(pauseMenuItem);
        menu.add(resumeMenuItem);
        menuBar.add(menu);
        GameController gameController = GameEngine.start("Circus Of Plates", new Circus(1), menuBar, Color.BLACK);
//        GameController gameController = new GameController(() -> new Circus(1));
//        gameController.start();
        easyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.changeWorld(new Circus(0));
//                gameController.start();
            }
        });
        mediumMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.changeWorld(new Circus(1));
            }
        });
        hardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.changeWorld(new Circus(2));
            }
        });
        pauseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.pause();
                Circus.pause();
            }
        });
        resumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resume();
                Circus.resume();
            }
        });
    }

}
