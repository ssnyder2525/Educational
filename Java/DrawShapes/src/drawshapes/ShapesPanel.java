//***********************************************************************************************
//  ShapesPanel.java							Michael Gardiner
//***********************************************************************************************
package drawshapes;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.border.*;

public class ShapesPanel extends JPanel
{
	
	private ShapeList list;

	private boolean filled, dCoordinates, translation;
	
	private JPanel control;
	
//***********************************************************************************************
// Control Panel Components
//***********************************************************************************************

	private JPanel draw, style, manipulate;
	
	private JButton drawRect, drawSquare, drawOval, drawCircle;
	
	private JCheckBox fill, coordinates;
	
	private JRadioButton translate, resize;
	
//***********************************************************************************************
// 	Constructor
//***********************************************************************************************
	
	public ShapesPanel()
	{
		list = new ShapeList();
		
		filled = false;
		
		dCoordinates = false;
		
		translation = true;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setPreferredSize (new Dimension (700, 700));
		
		setBackground(Color.black);
		
		control = new JPanel();
		
		ControlPanel();
		
		add(control);
		
		ShapesListener listener = new ShapesListener();
		
		addMouseListener(listener);
		
		addMouseMotionListener(listener);
	
	}
	
//***********************************************************************************************
// 	Sets up the control panel with its components.
//***********************************************************************************************
	
	private void ControlPanel()
	{
		Color grayish = Color.getHSBColor((float)0, (float)20, (float)255);

		
		control.setLayout(new BoxLayout(control, BoxLayout.X_AXIS) );
		
		control.setPreferredSize (new Dimension (700, 90));
		
		control.setBackground(grayish);

		
		// Draw Panel and components
	
		draw = new JPanel();
		
		draw.setLayout(new BoxLayout(draw, BoxLayout.X_AXIS) );
		
		control.setBackground(grayish);
		
		draw.setBorder( BorderFactory.createTitledBorder("Draw") );
		
		ImageIcon rectIcon = new ImageIcon("myRectangle.gif");
		
		ImageIcon squareIcon = new ImageIcon("mySquare.gif");
		
		ImageIcon ovalIcon = new ImageIcon("myOval.gif");
		
		ImageIcon circleIcon = new ImageIcon("myCircle.gif");
		
		drawRect = new JButton(rectIcon);
		
		drawRect.addActionListener(new RectangleListener() );
		
		
		drawSquare = new JButton(squareIcon);
		
		drawSquare.addActionListener(new SquareListener() );
		
		
		drawOval = new JButton(ovalIcon);
	
		drawOval.addActionListener(new OvalListener() );
		
		
		drawCircle = new JButton(circleIcon);
		
		drawCircle.addActionListener(new CircleListener() );
		
		
		draw.add(drawCircle);
		
		draw.add(drawOval);
		
		draw.add(drawSquare);
		
		draw.add(drawRect);
		
		
		// Style Panel and its components
		
		style = new JPanel();
		
		style.setLayout(new BoxLayout(style, BoxLayout.Y_AXIS) );
		
		style.setBackground(grayish);

		style.setBorder(BorderFactory.createRaisedBevelBorder());
		
		fill = new JCheckBox("Filled");
		
		fill.addItemListener(new FilledListener() );
		
		coordinates = new JCheckBox("Coordinates");
		
		coordinates.addItemListener(new CoordinatesListener() );
		
		style.add(fill);
		
		style.add(coordinates);
		
		
		
		//  Manipulate Panel and its components
		
		manipulate = new JPanel();
		
		manipulate.setLayout(new BoxLayout(manipulate, BoxLayout.Y_AXIS) );
		
		manipulate.setBackground(grayish);

		Border b2 = BorderFactory.createLoweredBevelBorder();
	
		TitledBorder b1 = BorderFactory.createTitledBorder("Manipulate");
	
		b1.setTitleJustification(3);
		
		manipulate.setBorder(BorderFactory.createCompoundBorder(b1, b2));
		
		translate = new JRadioButton("Translate", true);
		
		RadioListener radio = new RadioListener();
		
		resize = new JRadioButton("Resize");
		
		ButtonGroup group = new ButtonGroup();
		
		group.add(translate);
		
		group.add(resize);
		
		resize.addActionListener(radio);
		
		translate.addActionListener(radio);
		
		manipulate.add(translate);
		
		manipulate.add(resize);
		
		
		
		control.add(Box.createHorizontalGlue());
		
		control.add(draw);
		
		control.add(style);
		
		control.add(manipulate);
		
		control.add(Box.createHorizontalGlue());
	}
	

	
//***********************************************************************************************
// Draws all shapes stored in the array list.
//***********************************************************************************************	
	
	public void paintComponent (Graphics graphic)
	{
		super.paintComponent(graphic);
	
		
		if(filled == false)
		{
		
			if(dCoordinates == false)
			{
				for(int count = 0; count < list.getNumShapes(); count++)
				{
					graphic.setColor( list.getShape(count).getColor() );
			
					list.getShape(count).Draw(graphic);
				}
			}
			
			else
			{
				for(int count = 0; count < list.getNumShapes(); count++)
				{
					graphic.setColor( list.getShape(count).getColor() );
			
					list.getShape(count).Draw(graphic);
				
					graphic.setColor(Color.white);
					
					graphic.drawString(list.getShape(count).toString(), 
						list.getShape(count).getX(), list.getShape(count).getY());
				}
			}
			
		}
	
		else
		{
			if(dCoordinates == false)
			{
			
				for(int count = 0; count < list.getNumShapes(); count++)
				{
					graphic.setColor( list.getShape(count).getColor() );
			
					list.getShape(count).DrawFilled(graphic);
				}
			}
		
			else
			{
				for(int count = 0; count < list.getNumShapes(); count++)
				{
					graphic.setColor( list.getShape(count).getColor() );
			
					list.getShape(count).DrawFilled(graphic);
				
					graphic.setColor(Color.white);
					
					graphic.drawString(list.getShape(count).toString(), 
							list.getShape(count).getX(), list.getShape(count).getY());
				}
			}
			
			
			
		}
		
		
	}
		
	
	
	
//***********************************************************************************************
// Represents a listener for the circle drawing button.
//***********************************************************************************************

	private class CircleListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			
			Circle circle = new Circle();
			
			while ( ( (circle.getCornerX() + 0.5 * circle.getWidth() ) >= 700 ) 
					|| (circle.getCornerY()+ 0.5 * circle.getHeight() >= 700  ) ||(circle.getCornerY()+ 0.5 * circle.getHeight() <= 90 ))
			{
				circle = new Circle();
			}
				
			list.AddShape(circle);
			
			
			list.insertionSort();
			
			repaint();
		}
		
	}	


//***********************************************************************************************
// Represents a listener for the oval drawing button.
//***********************************************************************************************

	private class OvalListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			Oval oval = new Oval();
				
			while ( ( (oval.getCornerX() + 0.5 * oval.getWidth() ) >= 700 ) 
					|| (oval.getCornerY()+ 0.5 * oval.getHeight() >= 700  ) || (oval.getCornerY()+ 0.5 * oval.getHeight() <= 90 ))
			{
				oval = new Circle();
			}
					
			list.AddShape(oval);
				
			
			list.insertionSort();
			
			repaint();
		}
			
	}	

//***********************************************************************************************
// Represents a listener for the square drawing button.
//***********************************************************************************************

	private class SquareListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			Square square = new Square();
						
			while ( ( (square.getCornerX() + 0.5 * square.getWidth() ) >= 700 ) 
					|| (square.getCornerY()+ 0.5 * square.getHeight() >= 700  ) || (square.getCornerY()+ 0.5 * square.getHeight() <= 90 ))
			{
				square = new Square();
			}
							
			list.AddShape(square);
					
			
			list.insertionSort();
			
			repaint();
		}
					
	}	

//***********************************************************************************************
// Represents a listener for the rectangle drawing button.
//***********************************************************************************************

		private class RectangleListener implements ActionListener
		{
			public void actionPerformed (ActionEvent event)
			{
				Rectangle rectangle = new Rectangle();
							
				while ( ( (rectangle.getCornerX() + 0.5 * rectangle.getWidth() ) >= 700 ) 
						|| (rectangle.getCornerY()+ 0.5 * rectangle.getHeight() >= 700  ) || (rectangle.getCornerY()+ 0.5 * rectangle.getHeight() <= 90 ))
				{
					rectangle = new Rectangle();
				}
								
				list.AddShape(rectangle);
					
				
				list.insertionSort();
				
				repaint();
			}
						
		}	

//***********************************************************************************************
// Represents a listener for the "Filled" checkbox.
//***********************************************************************************************	
		
	private class FilledListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent event)
		{
			if(fill.isSelected())
			{
				filled = true;
					
				repaint();
			}
			
			else
			{
				filled = false;
					
				repaint();
			}
		}
	}
		

		
//***********************************************************************************************
// Represents a listener for the "Filled" checkbox.
//***********************************************************************************************	
				
	private class CoordinatesListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent event)
		{
			if(coordinates.isSelected())
			{
				dCoordinates = true;
							
				repaint();
			}
					
			else
			{
				dCoordinates = false;
							
				repaint();
			}
		}
	}
						
//***********************************************************************************************
// Represents a listener for the radio buttons.
//***********************************************************************************************	
	
	private class RadioListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			Object source = event.getSource();
			
			if (source == translate)
			{
				translation = true;
			}
		
			else
			{
				translation = false;
			}
		
			repaint();
		}
	}
	
	
	
	
//***********************************************************************************************
// Represents the mouse listener for manipulation of shapes.
//***********************************************************************************************	
					
	private class ShapesListener implements MouseListener, MouseMotionListener
	{	
		public Shape temp;
//***********************************************************************************************
// 	If the mouse is pressed inside a shape, returns the shape (only the top shape, which
//  will be the first in the array list from sorting).
//***********************************************************************************************	
		
		public void mousePressed(MouseEvent event)
		{
			Point p1 = event.getPoint();
			
			temp = list.FindShape(p1);
		}
		
		public void mouseDragged(MouseEvent event)
		{
			Point p2 = event.getPoint();
			
			if(temp != null)
			{
				if(translation == true)
				{
					temp.Translate(p2);
				}
				
				else
				{
					temp.Resize(p2);
				}
				
			list.insertionSort();
				
			repaint();
			}
		}
		
		
		//  Empty method definitions.	
		
		public void mouseMoved (MouseEvent event) {}
		public void mouseClicked (MouseEvent event) {}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		public void mouseExited (MouseEvent event) {}
		
		
	}

}
