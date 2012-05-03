import java.applet.AudioClip;
import java.awt.Color;
import sun.audio.*;
import sun.*;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Craft craft;
    private world world;
    private ArrayList aliens;
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
    
    
    
    

    private int[][] pos = { 
        {2380, 29}, {2500, 59}, {1380, 89},
        {780, 109}, {580, 139}, {680, 239}, 
        {790, 259}, {760, 50}, {790, 150},
        {980, 209}, {560, 45}, {510, 70},
        {930, 159}, {590, 80}, {530, 60},
        {940, 59}, {990, 30}, {920, 200},
        {900, 259}, {660, 50}, {540, 90},
        {810, 220}, {860, 20}, {740, 180},
        {820, 128}, {490, 170}, {700, 30}
     };

    
    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        //setBackground(Color.BLACK);

        ingame = true;

        setSize(1000, 400);

        
        craft = new Craft();
        world = new world();

        initAliens();
        

        timer = new Timer(8, this);
        timer.start();
    }

    
    

	public void addNotify() {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();   
    }

    public void initAliens() {
        aliens = new ArrayList();

        for (int i=0; i<20; i++ ) {
            aliens.add(new Alien(pos[i][0], pos[i][1]));
        }
    }


    public void paint(Graphics g) {

    	
    	
    	super.paint(g);
    	g.drawImage(world.getImage(), 0, 0, this);

        if (ingame) {

            Graphics2D g2d = (Graphics2D)g;

            if (craft.isVisible())
                g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),this);
            	
            	
            ArrayList ms = craft.getMissiles();

            for (int i = 0; i < ms.size(); i++) {
                Missile m = (Missile)ms.get(i);
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            for (int i = 0; i < aliens.size(); i++) {
                Alien a = (Alien)aliens.get(i);
                if (a.isVisible())
                    g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
            g2d.setColor(Color.WHITE);
            g2d.drawString("Blobs left: " + aliens.size(), 5, 15);
            
            


        } else {
            String msg = "Game Over";
            String left = " Blobs Left: " + aliens.size();
            Font small = new Font("Helvetica", Font.BOLD, 20);
            FontMetrics metr = this.getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                         B_HEIGHT / 2);
            g.drawString(left, (B_WIDTH - metr.stringWidth(left)) / 2, B_HEIGHT /4 );
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        
    }


    public void actionPerformed(ActionEvent e) {

        if (aliens.size()==0) {
            ingame = false;
        }

        ArrayList ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);
            if (m.isVisible()) 
                m.move();
            else ms.remove(i);
        }

        for (int i = 0; i < aliens.size(); i++) {
            Alien a = (Alien) aliens.get(i);
            if (a.isVisible()) 
                a.move();
            else aliens.remove(i);
        }

        craft.move();
        checkCollisions();
        repaint();  
    }

    public void checkCollisions() {

        Rectangle r3 = craft.getBounds();

        for (int j = 0; j<aliens.size(); j++) {
            Alien a = (Alien) aliens.get(j);
            Rectangle r2 = a.getBounds();

            if (r3.intersects(r2)) {
                craft.setVisible(false);
                a.setVisible(false);
                ingame = false;
            }
        }

        ArrayList ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);

            Rectangle r1 = m.getBounds();

            for (int j = 0; j<aliens.size(); j++) {
                Alien a = (Alien) aliens.get(j);
                Rectangle r2 = a.getBounds();

                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    a.setVisible(false);
                }
            }
        }
    }


    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }
}