//Programmed by Aaron Weiss 7/29/15

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.net.*;
import javax.swing.ImageIcon;
import java.util.*;

import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class IconifierGUI {

	BufferedImage img = null;

	JFrame window;
	URL url;
	Toolkit kit;
	BufferedImage icon;

	JFileChooser chooser;

	JRadioButton radioWhite;
	JRadioButton radioGray;
	JRadioButton radioRGB;
	JRadioButton radioRGBW;
	JRadioButton radioBlank;

	JRadioButton topLeft;
	JRadioButton topRight;
	JRadioButton bottomLeft;
	JRadioButton bottomRight;

	JLabel previewIconLabel;
	ImageIcon previewIcon;

	JPanel previewPanel;

	public void paint(Graphics g) {
	        g.drawImage(img, 0, 0, null);
    }

	public IconifierGUI() {

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select Image");
		chooser.setMultiSelectionEnabled(true);

		window = new JFrame("LED Iconifier");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		url = getClass().getResource("/res/Icon.png");
		kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		window.setIconImage(img);


		//radioPanel
		JPanel radioPanel = new JPanel();
		RadioListener listener = new RadioListener();

		topLeft = new JRadioButton();
		topLeft.addActionListener(listener);
		topRight = new JRadioButton();
		topRight.addActionListener(listener);
		bottomLeft = new JRadioButton();
		bottomLeft.addActionListener(listener);
		bottomRight = new JRadioButton();
		bottomRight.addActionListener(listener);

		JLabel spaceLabel = new JLabel("Test");
		radioPanel.setLayout(new GridLayout(2,2,50,50));
		radioPanel.setBorder(BorderFactory.createTitledBorder("Icon Location"));
		radioPanel.add(topLeft);
		radioPanel.add(topRight);
		radioPanel.add(bottomLeft);
		radioPanel.add(bottomRight);

		ButtonGroup group = new ButtonGroup();
		group.add(topLeft);
		group.add(topRight);
		group.add(bottomLeft);
		group.add(bottomRight);

		bottomRight.setSelected(true);

		//iconPanel
		JPanel iconPanel = new JPanel();

		url = getClass().getResource("/res/LED STATIC2.png");

		Image iconWhiteImg = kit.createImage(url);
		ImageIcon iconWhite = new ImageIcon(iconWhiteImg);
		JLabel iconLabelWhite = new JLabel(iconWhite);

		radioWhite = new JRadioButton();
		radioWhite.addActionListener(listener);
		radioWhite.setSelected(true);

		JPanel innerIconPanel = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics grphcs) {
				super.paintComponent(grphcs);
				Graphics2D g2d = (Graphics2D) grphcs;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0,
				getBackground().darker().darker(), 0, getHeight()*10/36,
				getBackground().darker().brighter());
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight()*10/35);
			}
    	};
    	innerIconPanel.setLayout(new GridLayout(0,2));
		iconPanel.setBorder(BorderFactory.createTitledBorder("Icon Selection"));

		innerIconPanel.add(radioWhite);
		innerIconPanel.add(iconLabelWhite);


		url = getClass().getResource("/res/LED STATIC1.png");

		Image iconGrayImg = kit.createImage(url);
		ImageIcon iconGray = new ImageIcon(iconGrayImg);
		JLabel iconLabelGray = new JLabel(iconGray);

		radioGray = new JRadioButton();
		radioGray.addActionListener(listener);

		innerIconPanel.add(radioGray);
		innerIconPanel.add(iconLabelGray);

		url = getClass().getResource("/res/LED RGB.png");

		Image iconRGBImg = kit.createImage(url);
		ImageIcon iconRGB = new ImageIcon(iconRGBImg);
		JLabel iconLabelRGB = new JLabel(iconRGB);

		radioRGB = new JRadioButton();
		radioRGB.addActionListener(listener);

		innerIconPanel.add(radioRGB);
		innerIconPanel.add(iconLabelRGB);

		url = getClass().getResource("/res/LED RGBW.png");

		Image iconRGBWImg = kit.createImage(url);
		ImageIcon iconRGBW = new ImageIcon(iconRGBWImg);
		JLabel iconLabelRGBW = new JLabel(iconRGBW);
		radioRGBW = new JRadioButton();
		radioRGBW.addActionListener(listener);

		innerIconPanel.add(radioRGBW);
		innerIconPanel.add(iconLabelRGBW);

		radioBlank = new JRadioButton();
		radioBlank.addActionListener(listener);

		innerIconPanel.add(radioBlank);
		JLabel iconLabelBlank = new JLabel("No Icon.");

		innerIconPanel.add(radioBlank);
		innerIconPanel.add(iconLabelBlank);

		ButtonGroup iconGroup = new ButtonGroup();
		iconGroup.add(radioWhite);
		iconGroup.add(radioGray);
		iconGroup.add(radioRGB);
		iconGroup.add(radioRGBW);
		iconGroup.add(radioBlank);

		iconPanel.add(innerIconPanel);

		//Preview Panel
		previewPanel = new JPanel();


		url = getClass().getResource("/res/Preview.png");

		Image previewIconImg = kit.createImage(url);
		previewIcon = new ImageIcon(previewIconImg);
		previewIconLabel = new JLabel(previewIcon);

		previewPanel.add(previewIconLabel);
		previewPanel.setBorder(BorderFactory.createTitledBorder("Image Preview"));

		//Directory Panel
		JPanel directoryPanel = new JPanel();
		JLabel directoryLabel0 = new JLabel("Select a file to be iconified!");
		JLabel directoryLabel = new JLabel("Use this after you have chosen your icon type & location.");
		JLabel directoryLabel2 = new JLabel("The file will be saved as '<file_name>_i.jpg'.");

		JCheckBox directoryBox = new JCheckBox("Auto-scale image.",false);

		JButton directoryButton = new JButton("Select File");

		directoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = "";

				if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {

				    String iconPath = "";
				    int iconLocation = 0;
				    //icon choice

					if (radioWhite.isSelected())
				    	iconPath = "/res/LED STATIC2.png";
					if (radioGray.isSelected())
				    	iconPath = "/res/LED STATIC1.png";
				    if (radioRGB.isSelected())
				      	iconPath = "/res/LED RGB.png";
				    if (radioRGBW.isSelected())
				    	iconPath = "/res/LED RGBW.png";
				    if (radioBlank.isSelected())
				    	iconPath = "/res/LED BLANK.png";

				    //load icon
				    try {
						icon = ImageIO.read(IconifierGUI.class.getResourceAsStream(iconPath));
					} catch (IOException e2) {
					    e2.printStackTrace();
    				}

					//location choice
					if (topLeft.isSelected())
					  	iconLocation = 0;
					if (topRight.isSelected())
					  	iconLocation = 1;
					if (bottomLeft.isSelected())
					  	iconLocation = 2;
					if (bottomRight.isSelected())
						iconLocation = 3;

					//Auto-scale
					boolean autoScale = false;
					if (directoryBox.isSelected())
						autoScale = true;
					else
						autoScale = false;

					WatermarkImage selection = new WatermarkImage();
					File files[] = chooser.getSelectedFiles();
					for(File file : files) {
						filePath = ("" + file.getPath());
				      	selection.addWatermark(filePath, icon, iconLocation, autoScale);
					}
				}
				else {
					System.out.println("No Selection");
     			}
			}
		});


		directoryPanel.add(directoryLabel0);
		directoryPanel.add(directoryLabel);
		directoryPanel.add(directoryLabel2);

		JPanel directorySubPanel = new JPanel();
		directorySubPanel.setLayout(new GridLayout(0,2));
		directorySubPanel.add(directoryButton);
		directorySubPanel.add(directoryBox);

		directoryPanel.add(directorySubPanel);

		directoryPanel.setLayout(new GridLayout(0,1));
		directoryPanel.setBorder(BorderFactory.createTitledBorder("Iconify"));

		//build scene
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		window.add(radioPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		window.add(iconPanel, c);

		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		window.add(directoryPanel, c);

		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		window.add(previewPanel, c);

		updatePreview();
		window.pack();
		window.setVisible(true);
	}
	public static void main(String[] args) {
		try {
				// Set System L&F
				UIManager.setLookAndFeel(
				UIManager.getSystemLookAndFeelClassName());
			}
			catch (UnsupportedLookAndFeelException e) {
				// handle exception
			}
			catch (ClassNotFoundException e) {
				// handle exception
			}
			catch (InstantiationException e) {
				// handle exception
			}
			catch (IllegalAccessException e) {
				// handle exception
    	}
		new IconifierGUI();
	}

	public void updatePreview() {
		String type = "";
		int loc = 0;
		//String result = "";
		//icon choice
		if (radioWhite.isSelected())
			type = "white_";
		if (radioGray.isSelected())
			type = "gray_";
		if (radioRGB.isSelected())
			type = "rgb_";
		if (radioRGBW.isSelected())
			type = "rgbw_";
		//location choice
		if (topLeft.isSelected())
			loc = 2;
		if (topRight.isSelected())
			loc = 1;
		if (bottomLeft.isSelected())
			loc = 3;
		if (bottomRight.isSelected())
			loc = 0;

		if(radioBlank.isSelected())
			url = getClass().getResource("/res/Preview.png");
		else
			url = getClass().getResource("/res/previews/"+type+"preview_"+loc+".png");

		Image result = kit.createImage(url);
		previewIcon = new ImageIcon(result, "Image Preview");
		previewIconLabel.setIcon(previewIcon);
	}

	private class RadioListener implements ActionListener{
	    public void actionPerformed(ActionEvent e){
	        JRadioButton button = (JRadioButton) e.getSource();
	        updatePreview();
	    }
	}
}
