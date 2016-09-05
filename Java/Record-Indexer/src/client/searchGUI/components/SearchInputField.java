package client.searchGUI.components;

import javax.swing.*;

@SuppressWarnings("serial")
public class SearchInputField extends JPanel
{
	JLabel l;

	JTextField t = new JTextField(20);
	
	public SearchInputField(String label)
	{
		super();
		l = new JLabel(label);
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		this.setLayout(boxLayout);
		this.add(l);
		this.add(t);
		
	}

	public JTextField getT()
	{
		return t;
	}

	public void setT(JTextField t)
	{
		this.t = t;
	}

}
