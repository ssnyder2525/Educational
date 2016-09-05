package client.recordIndexerGUI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.*;
import shared.models.*;
import client.ClientException;
import client.communication.ClientCommunicator;
import client.recordIndexerGUI.components.*;
import client.recordIndexerGUI.dataStorageClasses.BatchListener;
import client.recordIndexerGUI.dataStorageClasses.InputData;
import client.recordIndexerGUI.dataStorageClasses.UserData;
/**
 * This must keep track of all components for communication
 * @author ssnyder
 */
public class Session
{
	//keeps track of saved data
	UserData userData;
	
	//keeps track of the program's components.
	MainFrame mainFrame;
	MainMenu menu;
	TopToolBar toolBar;
	CenterPanes centerPanes;
	LeftPane leftPane;
	RightPane rightPane;
	
	//keeps track of server communication
	String host;
	String port;
	ClientCommunicator cc;
	
	public Session(MainFrame mainFrame, String host, String port, String username, String password)
	{
		this.mainFrame = mainFrame;
		this.host = host;
		this.port = port;
		cc = new ClientCommunicator(host, port);
		this.userData = new UserData(username, password);
	}
	
	//sets all the component variables to their corresponding components 
	public void initialize()
	{
		this.menu = mainFrame.getMainMenu();
		this.toolBar = mainFrame.getTopToolBar();
		this.centerPanes = mainFrame.getCenterPanes();
		this.leftPane = mainFrame.getCenterPanes().getBottomPanes().getLeftPane();
		this.rightPane = mainFrame.getCenterPanes().getBottomPanes().getRightPane();
	}
	
//Getters
	public MainFrame getMainFrame()
	{
		return mainFrame;
	}

	public UserData getUserData()
	{
		return userData;
	}

	public MainMenu getMenu()
	{
		return menu;
	}

	public TopToolBar getToolBar()
	{
		return toolBar;
	}

	public CenterPanes getCenterPanes()
	{
		return centerPanes;
	}

	public LeftPane getLeftPane()
	{
		return leftPane;
	}

	public RightPane getRightPane()
	{
		return rightPane;
	}

	public Project getProject()
	{
		return userData.getProject();
	}
	
	public Batch getBatch()
	{
		return userData.getBatch();
	}	
	
	public URL getImageUrl()
	{
		return userData.getImageUrl();
	}
	
	public int getNumOfColumns()
	{
		return getBatch().getFields().size();
	}
	
	public int getNumOfRows()
	{
		return Integer.parseInt(getProject().getRecordsPerBatch());
	}
	
	public int getCurrentRow()
	{
		return userData.getInputData().getRowSelected();
	}
	
	public int getCurrentColumn()
	{
		return userData.getInputData().getColumnSelected();
	}
	
	public int getPrevRow()
	{
		return userData.getInputData().getPrevRowSelected();
	}
	
	public int getPrevColumn()
	{
		return userData.getInputData().getPrevColumnSelected();
	}
	
	public String[][] getInputData()
	{
		return userData.getInputData().getInputData();
	}
	
//Main Frame Methods
	public ValidateUserOutput ValidateUser(ValidateUserInput validateInput) throws ClientException
	{
		 ValidateUserOutput validateOutput = (ValidateUserOutput) cc.ValidateUser(validateInput);
		 return validateOutput; 
	}
	
//MainMenu Methods
	public GetProjectsOutput GetProjects() throws ClientException
	{
		GetProjectsInput in = new GetProjectsInput(userData.getUsername(), userData.getPassword());
		GetProjectsOutput out = (GetProjectsOutput) cc.GetProjects(in);
		return out;
	}
	
	public DownloadBatchOutput downloadBatch() throws ClientException
	{		
		DownloadBatchInput in = new DownloadBatchInput(userData.getUsername(), userData.getPassword(), userData.getProject().getProjectID());
		DownloadBatchOutput out = (DownloadBatchOutput) cc.DownloadBatch(in);
		return out;
	}
	
	public void logout()
	{
		save();
		clearComponents();
		mainFrame.logIn();
	}
	
//ToolBar Methods
	public void zoomIn()
	{
		centerPanes.zoomIn();
	}
	
	public void zoomOut()
	{
		centerPanes.zoomOut();
	}
	
	public void invertImage()
	{
		centerPanes.invertImage();
		userData.getWindowData().invertImage();
	}
	
	public void toggleHighlight()
	{
		userData.getWindowData().toggleHighlights();
		centerPanes.toggleHighlights();
		centerPanes.repaintImage();
	}
	
	public SubmitBatchOutput submitBatch(String fieldValues) throws ClientException
	{
		SubmitBatchInput in = new SubmitBatchInput(userData.getUsername(), userData.getPassword(), userData.getBatch().getBatchID(), fieldValues);
		SubmitBatchOutput out = (SubmitBatchOutput) cc.SubmitBatch(in);
		return out;
	}
	
//Image Pane Methods
	public URL getSampleImage(String projectID)
	{
		GetSampleImageInput in = new GetSampleImageInput(userData.getUsername(), userData.getPassword(), projectID);
		GetSampleImageOutput out;
		try
		{
			out = (GetSampleImageOutput) cc.GetSampleImage(in);
			URL url = new URL(out.getIMAGE_URL());
			return url;
		} 
		catch (ClientException | IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void incrementScale()
	{
		userData.getWindowData().incrementScale();
	}
	
	public void decrementScale()
	{
		userData.getWindowData().decrementScale();
	}
	
	public double getScale()
	{
		return userData.getWindowData().getScale();
	}
	
	public void setXTranslate(int w_newTranslateX)
	{
		userData.getWindowData().setxTransition(w_newTranslateX);
	}

	public void setYTranslate(int w_newTranslateY)
	{
		userData.getWindowData().setyTransition(w_newTranslateY);
	}	
	
	public int getXTranslation()
	{
		return userData.getWindowData().getxTransition();
	}

	public int getYTranslation()
	{
		return userData.getWindowData().getyTransition();
	}
	
//	public boolean[][] getHighlightedValues()
//	{
//		return userData.getHighlightedValues();
//	}	
	
	public boolean isHighlighted()
	{
		return userData.getWindowData().isHighlighted();
	}
	
//Left Panel Methods
	public String[] loadColumns()
	{
		return userData.getInputData().getColumns();
	}
	
	public void createTable()
	{
		leftPane.createTable(userData.getBatch(), userData.getProject());
		refresh();
	}
	
	public void createFieldPane()
	{
		leftPane.setUpFieldInputPane(loadColumns(), getInputData());
	}
	
	public void updateValues()
	{
		leftPane.updateValues();
	}
	
//Right Panel Methods
	public void createHelpPane()
	{
		rightPane.createHelpPane();
	}
	
	public void createImageNav()
	{
		rightPane.createImageNav();
	}
	
//Data Methods

	//Universal method that adds information to the UserData Class.
	public void addToUserData(Map<String,Object> itemsToAdd) throws IOException
	{
		Set<String> keys = itemsToAdd.keySet();
		for(String key : keys)
		{
			Object itemToAdd = itemsToAdd.get(key);
			
			if(key.equals("username"))
			{
				userData.setUsername((String) itemToAdd);
			}
			else if(key.equals("password"))
			{
				userData.setPassword((String) itemToAdd);
			}
			else if(key.equals("project"))
			{
				userData.setProject((Project) itemToAdd);
			}
			else if(key.equals("batch"))
			{
				Batch batch = (Batch) itemToAdd;
				userData.setBatch(batch);
				URL url = new URL(batch.getSourceURL());
				userData.setImageUrl(url);
			}
			else if(key.equals("InputData"))
			{
				if(userData.getInputData() == null)
				{
					int rowNum = Integer.parseInt(getProject().getRecordsPerBatch());
					int colNum = getBatch().getFields().size();
					InputData inputData = new InputData(rowNum, colNum);
					inputData.setColumns(getColumns(colNum, getBatch()));
					inputData.setInputData(getRows(rowNum, colNum));
					userData.setInputData(inputData);
				}
			}
		}
	}
	
	public String[] getColumns(int colNum, Batch batch)
	{
		String[] columns = new String[colNum];
		int i = 0;
		for (Field field : batch.getFields())
		{
			columns[i] = field.getColumnHeader();
			i++;
		}
		return columns;
	}
	
	public String[][] getRows(int rowNum, int colNum)
	{
		String[][] data = new String[rowNum][colNum];
		for(int i = 0; i < rowNum; i++)
		{
			for(int j = 0; j < colNum; j++)
			{
				data[i][j] = "";
			}
		}
		return data;
	}
	
	public void setCellData(String value)
	{
		userData.getInputData().setCellData(value);
	}
	
	public void addListener(BatchListener l)
	{
		userData.getInputData().addListener(l);
	}

	public void cellSelected(int row, int col)
	{
		userData.getInputData().cellSelected(row, col);
	}
	
	public void initializeQualityChecker()
	{
		userData.getInputData().initiateQualityChecker(getBatch());
	}
	
	public boolean isMispelled(String value, int column)
	{
		return userData.isMispelled(value, column);
	}
	
	public Set<String> getSuggestions(String value, int column)
	{
		return userData.getInputData().getSuggestions(value, column);
	}
	
	public void markCellAsMispelled(int row, int column)
	{
		userData.getInputData().markCellAsMispelled(row, column);
	}
	
	public void markCellAsNotMispelled(int row, int column)
	{
		userData.getInputData().markCellAsNotMispelled(row, column);
	}
	
	public void replaceData(int row, int column, String value)
	{
		userData.getInputData().replaceData(row, column, value);
	}
	
	public void save()
	{		
		//get window positions stored.
		if(getBatch() != null)
		{
			centerPanes.saveTranslation();
		}
		userData.getWindowData().setFrameXPosition(mainFrame.getX());
		userData.getWindowData().setFrameYPosition(mainFrame.getY());
		userData.getWindowData().setFrameSize(mainFrame.getSize());
		
		centerPanes.getSliderPositions();
		
		//save the file
		String filename = "FileStorage/UserData/" + userData.getUsername();
		XStream x = new XStream(new DomDriver());
		File file = new File(filename);
		try
		{
			FileWriter fW = new FileWriter(file);
			fW.write(x.toXML(userData));
			fW.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		};
	}

	public void load()
	{
		XStream x = new XStream(new DomDriver());
		String filename = "FileStorage/UserData/" + userData.getUsername();
		File file = new File(filename);
		
		UserData userData = (UserData) x.fromXML(file);
		
		if(userData != null)
		{
			this.userData = userData;
			
			//set frame position and size
			mainFrame.setLocation(userData.getWindowData().getFrameXPosition(), userData.getWindowData().getFrameYPosition());
			mainFrame.setSize(userData.getWindowData().getFrameSize());
			
			//set slider positions
			centerPanes.setSliderPositions();
			
			if(this.userData.getInputData() != null)
			{
				this.userData.getInputData().setListener();
			}
			if(userData.getBatch() != null)
			{
				createTable();
				createFieldPane();
				createHelpPane();
				createImageNav();
				refresh();
			}
		}
	}

	public void refresh()
	{
		if(userData.getBatch() != null)
		{
			centerPanes.getImage(userData.getBatch().getSourceURL());
		}
		mainFrame.refresh();
	}

	public void clearComponents()
	{
		this.userData.setBatch(null);
		this.userData.setProject(null);
		this.userData.clearInputData();
		this.centerPanes.clearImage();
		this.leftPane.removeLeftContent();
		this.rightPane.removeRightContent();
		refresh();
	}

	public boolean isInverted()
	{
		return userData.getWindowData().isInverted();
	}
}
