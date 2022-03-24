package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private Image img;
	
	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}
	
	public ImagePanel(Image img) {
		this.img = img;
        setPreferredSize(new Dimension(100,70));
//        setMinimumSize(size);
//        setMaximumSize(size);
//        setSize(size);
        setLayout(null);
                
	}
	
	public void update_img(String img)
	{
		this.img = new ImageIcon(img).getImage();
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawImage(img, 0, 0, 100, 70, null);
    }
}
