package client.searchGUI.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class UrlButton extends JButton
{
	String imageUrl;
	public UrlButton(String url)
	{

		super(url);
		imageUrl = url;
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		this.setLayout(boxLayout);
		
		this.addActionListener(new ActionListener(){
			
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e)
			{
				String path = imageUrl;
                URL url;
				try
				{
					 url = new URL(path);
	                 BufferedImage image = ImageIO.read(url);
	                 JLabel label = new JLabel(new ImageIcon(image));
	                 JFrame f = new JFrame();
	                 f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
	                 f.getContentPane().add(label);
	                 f.pack();
	                 f.setLocation(200, 200);
	                 f.setVisible(true);
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
	
