import javax.swing.*;

import java.awt.*;


public class RType extends JFrame {

    public RType() {

    	
    	
        add(new Board());
        
        java.net.URL imgURL = RType.class.getResource("craft.png");

        setIconImage(new ImageIcon(imgURL).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setTitle("Shut the blobs - by UnK1nD");
        setResizable(false);
        setVisible(true);
        
    }

    public static void main(String[] args) {
        new RType();
    }
}
