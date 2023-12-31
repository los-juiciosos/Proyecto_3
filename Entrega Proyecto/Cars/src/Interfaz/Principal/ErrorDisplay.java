package Interfaz.Principal;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ErrorDisplay extends JDialog implements MetodosAuxiliares{

	private GridBagConstraints gbc;
	private ImageIcon error1;
	
	public ErrorDisplay(String mensaje) {
		
		setLocation(350,300);
		
		setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();
		
		gbc.gridx=0;
		gbc.gridy = 0;
		

		JLabel advertencia = new JLabel (mensaje);
		error1 = new ImageIcon("./imagenes/error.png");
		
		ImageIcon error = resizeImage("./imagenes/error.png", 50, 50);
		JLabel imagen = new JLabel(error);
		
		setTitle("ERROR");
		add(advertencia);
		add(imagen);
		
		setModal(true);
		setSize(650,100);
		setVisible(true);
		

	}
	
	private static ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
}
}



