import java.awt.Image;


import javax.swing.ImageIcon;


public class world {
	
	  private String planet = "planet.png";

	    private int x;
	    private int y;
	    private int width;
	    private int height;
	    private Image image;

	    public world() {
	        ImageIcon ii = new ImageIcon(this.getClass().getResource(planet));
	        image = ii.getImage();
	        width = image.getWidth(null);
	        height = image.getHeight(null);
	        x = 0;
	        y = 0;
	        
	    }
	    
	    public Image getImage() {
	        return image;
	    }
	    
	    public int getX() {
	        return x;
	    }

	    public int getY() {
	        return y;
	    }
}
