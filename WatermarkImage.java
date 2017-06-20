import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.net.*;

public class WatermarkImage {

  public static void addWatermark(String localImagePath, BufferedImage iconPath, boolean autoScale) {

    try {
        BufferedImage image = ImageIO.read(new File(localImagePath));
		BufferedImage overlay = iconPath;

        Filename file = new Filename(localImagePath, '\\', '.');

        // create the new image, canvas size is the max. image size

        int w = image.getWidth();
        int h = image.getHeight();


        if (w != 388 || h != 388) { //if not already scaled, display a warning
			JFrame frame = new JFrame();
			String warning = "";
			if(!autoScale) //Warns for distortion after upload
				warning = "Images should be 388x388 exactly! Selected image dimensions: " + w + "x" + h + ". (" + file.filename() + "." + file.extension() + ") \nThe file has still been created. Expect distortion after upload.";
			else {
				if(w == h) //Should scale nicely message
					warning = "Selected image dimensions: " + w + "x" + h + ". (" + file.filename() + "." + file.extension() + ") The file still has been created. \nThe image was auto-scaled, however no distortion should occur due to equal image width and height.";
				else	//Distortion on creation due to scaling
					warning = "Images should be 388x388 exactly! Selected image dimensions: " + w + "x" + h + ". (" + file.filename() + "." + file.extension() + ") \nThe file still has been created. Expect distortion due to auto-scaling.";
			}
			JOptionPane.showMessageDialog(frame,warning,"Notice!",JOptionPane.WARNING_MESSAGE);
		}

		Image scaledImage = image.getScaledInstance((int)388,(int)388, Image.SCALE_SMOOTH);
		if(autoScale) {
			w = 388;
			h = 388;
		}


        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        if(autoScale)
        	g.drawImage(scaledImage, 0, 0, null);
        else
        	g.drawImage(image, 0, 0, null);

		// Draw LED icon to bottom right.
        g.drawImage(overlay, w-60, h-60, null);
		
		// Draw other icons 17px out


        ImageIO.write(combined, "PNG", new File(file.path() + "\\" + file.filename()  + "_i.png"));
        System.out.println("Success! File " + file.filename() + "_i.png" + " created.");

    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}