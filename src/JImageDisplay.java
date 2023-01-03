import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
public class JImageDisplay extends JComponent{
    public BufferedImage img;
    public JImageDisplay(int width,int height){
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Dimension dimension = new Dimension(width, height);
        setPreferredSize(dimension);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage (img, 0, 0, img.getWidth(), img.getHeight(), null);
    }
    public void clearImage(){   //отчистка изображения
        int[] arr = new int[getHeight() * getWidth()];
        img.setRGB(0,0,getWidth(), getHeight(), arr,0,1);
    }
    public void drawPixel(int x, int y, int rgbColor){
        img.setRGB(x,y,rgbColor);
    }
}
