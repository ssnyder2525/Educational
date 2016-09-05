package client.recordIndexerGUI.components.imagePanelClasses;


import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

import shared.models.*;

import javax.swing.*;

import client.recordIndexerGUI.Session;
import client.recordIndexerGUI.dataStorageClasses.BatchListener;

import java.util.*;


@SuppressWarnings("serial")
public class DrawingComponent extends JComponent implements BatchListener
{
	Session session;
	private BufferedImage image;
	
	private int w_translateX;
	private int w_translateY;
	private double scale;
	
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartTranslateX;
	private int w_dragStartTranslateY;
	private AffineTransform dragTransform;

	private ArrayList<DrawingShape> shapes;
	
	private DrawingRect[][] selectionBoxes;
	
	private ArrayList<DrawingListener> listeners;
	
	public DrawingComponent(Session session, BufferedImage image) 
	{
		this.session = session;
		this.image = image;
		
		w_translateX = session.getXTranslation();
		w_translateY = session.getYTranslation();
		scale = session.getScale();
		
		initDrag();

		shapes = new ArrayList<DrawingShape>();
		
		listeners = new ArrayList<DrawingListener>();
		
		this.setBackground(new Color(255, 255, 255));
		this.setPreferredSize(new Dimension(1250, 800));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		
		shapes.add(new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null))));
		selectionBoxes = new DrawingRect[session.getNumOfRows()][session.getNumOfColumns()];
		buildSelectionBoxes();
		
		session.addListener(this);
		
		session.cellSelected(session.getCurrentRow(), session.getCurrentColumn());
	}
	
	private void buildSelectionBoxes()
	{
		int firstRowY = Integer.parseInt(session.getProject().getFieldY());
		int rowHeight = Integer.parseInt(session.getProject().getRecordHeight());
		
		int col = 0;
		for (Field field : session.getBatch().getFields())
		{
			for(int row = 0; row < session.getNumOfRows(); row++)
			{
				int currentBoxY = firstRowY + (rowHeight * row);
				int currentBoxX = Integer.parseInt(field.getFieldX());
				int currentBoxWidth = Integer.parseInt(field.getFieldWidth());
				selectionBoxes[row][col] = new DrawingRect(new Rectangle2D.Double(currentBoxX, currentBoxY, currentBoxWidth, rowHeight), new Color(255,255,255,0), row, col);
				shapes.add(selectionBoxes[row][col]);
			}
			col++;
		}
		session.cellSelected(0, 0);
	}
	
	@Override
	public void cellSelected(int row, int col, int prevRowSelected, int prevColumnSelected)
	{
		getSelection(row, col, prevRowSelected, prevColumnSelected);
	}
	
	public void removeSelection(int prevRow, int prevCol)
	{
		DrawingRect prevBox = selectionBoxes[prevRow][prevCol];
		prevBox.setColor(new Color(255,255,255,0));
		this.repaint();
	}
	
	public void getSelection(int row, int col, int prevRow, int prevCol)
	{
		if(session.isHighlighted())
		{
			removeSelection(prevRow, prevCol);
			selectionBoxes[row][col].setColor(new Color(25, 35, 200, 128));
			this.repaint();
		}
	}

	private void initDrag() 
	{
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartTranslateX = 0;
		w_dragStartTranslateY = 0;
		dragTransform = null;
	}
	
	public void invertImage()
	{
		RescaleOp op = new RescaleOp(-1.0f, 255f, null);
		image = op.filter(image, null);
		shapes.remove(0);
		shapes.add(0, new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null))));
		repaint();
	}
	
	public void setScale(double newScale) {
		scale = newScale;
		this.repaint();
	}
	
	public void setTranslation(int w_newTranslateX, int w_newTranslateY) {
		session.setXTranslate(w_newTranslateX);
		session.setYTranslate(w_newTranslateY);
		w_translateX = w_newTranslateX;
		w_translateY = w_newTranslateY;
		this.repaint();
	}
	
	public void addDrawingListener(DrawingListener listener) {
		listeners.add(listener);
	}
	
	private void notifyTranslationChanged(int w_newTranslateX, int w_newTranslateY) {
		for (DrawingListener listener : listeners) {
			listener.translationChanged(w_newTranslateX, w_newTranslateY);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);

		g2.translate(this.getWidth() / 2.0, this.getHeight() / 2.0);
		g2.scale(scale, scale);
		g2.translate(-this.getWidth() / 2.0 + w_translateX, -this.getHeight() / 2.0 + w_translateY);

		drawShapes(g2);
	}
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
	
	public void saveTranslation()
	{
		session.setXTranslate(w_translateX);
		session.setYTranslate(w_translateY);
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth()/2, getHeight()/2);
			transform.scale(scale, scale);
			transform.translate(-getWidth()/2 + w_translateX, -getHeight()/2 + w_translateY);
			
			Point2D d_Pt = new Point2D.Double(x, y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int pointX = (int)w_Pt.getX();
			int pointY = (int)w_Pt.getY();
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) 
			{
				if (shape.contains(g2, pointX, pointY)) 
				{
					if(shape.getClass().toString().equals("class client.recordIndexerGUI.components.imagePanelClasses.DrawingComponent$DrawingRect"))
					{
						DrawingRect shapec = (DrawingRect) shape;
						int row = shapec.getRowNum();
						int col = shapec.getColNum();
						session.cellSelected(row, col);
						break;
					}
				}
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth()/2, getHeight()/2);
			transform.scale(scale, scale);
			transform.translate(-getWidth()/2 + w_translateX, -getHeight()/2 + w_translateY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartTranslateX = w_translateX;
				w_dragStartTranslateY = w_translateY;
				dragTransform = transform;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					dragTransform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_translateX = w_dragStartTranslateX + w_deltaX;
				w_translateY = w_dragStartTranslateY + w_deltaY;
				
				notifyTranslationChanged(w_translateX, w_translateY);
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) 
		{
			int wheelMovement = e.getWheelRotation();
			if(wheelMovement == -1)
			{
				session.decrementScale();
				setScale(session.getScale());
			}
			else if(wheelMovement == 1)
			{
				session.incrementScale();
				setScale(session.getScale());
			}
		}	
	};	
	
	interface DrawingShape 
	{
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}


	class DrawingRect implements DrawingShape 
	{

		private Rectangle2D rect;
		private Color color;
		private int rowNum;
		private int colNum;
		
		public DrawingRect(Rectangle2D rect, Color color, int row, int col) 
		{
			this.rect = rect;
			this.color = color;
			this.rowNum = row;
			this.colNum = col;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.fill(rect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
		
		public void setColor(Color newColor)
		{
			this.color = newColor;
		}
		
		public Color getColor()
		{
			return this.color;
		}

		public int getRowNum()
		{
			return rowNum;
		}

		public void setRowNum(int rowNum)
		{
			this.rowNum = rowNum;
		}

		public int getColNum()
		{
			return colNum;
		}

		public void setColNum(int colNum)
		{
			this.colNum = colNum;
		}
	}

	class DrawingImage implements DrawingShape {

		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}

	public void clear()
	{
		shapes.clear();
		repaint();
	}
}




