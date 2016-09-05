package servertester.controllers;

import java.util.*;

import client.ClientException;
import client.communication.ClientCommunicator;
import servertester.views.*;
import shared.communication.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser()
	{		
		String[] values = getView().getParameterValues();
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		ValidateUserInput vUI = new ValidateUserInput(values[0], values[1]);
		Object obj;
		try
		{
			obj = cc.ValidateUser(vUI);
			ValidateUserOutput vUO = (ValidateUserOutput) obj;
			getView().setRequest("ValidateUser");
			getView().setResponse(vUO.toString());
		} catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getProjects() 
	{
		String[] values = getView().getParameterValues();
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		GetProjectsInput in = new GetProjectsInput(values[0], values[1]);
		Object obj;
		try
		{
			obj = cc.GetProjects(in);
			GetProjectsOutput out = (GetProjectsOutput) obj;
			getView().setRequest("GetProjects");
			getView().setResponse(out.toString());
		} catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getSampleImage() 
	{
		String[] values = getView().getParameterValues();
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		GetSampleImageInput in = new GetSampleImageInput(values[0], values[1], values[2]);
		Object obj;
		try
		{
			obj = cc.GetSampleImage(in);
			GetSampleImageOutput out = (GetSampleImageOutput) obj;
			getView().setRequest("GetSampleImage");
			getView().setResponse(out.toString());
		} catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch() 
	{
		String[] values = getView().getParameterValues();
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		DownloadBatchInput in = new DownloadBatchInput(values[0], values[1], values[2]);
		Object obj;
		try
		{
			obj = cc.DownloadBatch(in);
			DownloadBatchOutput out = (DownloadBatchOutput) obj;
			getView().setRequest("DownloadBatch");
			getView().setResponse(out.toString());
		} catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getFields() 
	{
		String[] values = getView().getParameterValues();
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		GetFieldsInput in = new GetFieldsInput(values[0], values[1], values[2]);
		Object obj;
		try
		{
			obj = cc.GetFields(in);
			GetFieldsOutput out = (GetFieldsOutput) obj;
			getView().setRequest("GetFields");
			getView().setResponse(out.toString());
		} catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void submitBatch() 
	{
		String[] values = getView().getParameterValues();
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		SubmitBatchInput in = new SubmitBatchInput(values[0], values[1], values[2], values[3]);
		Object obj;
		try
		{
			obj = cc.SubmitBatch(in);
			SubmitBatchOutput out = (SubmitBatchOutput) obj;
			getView().setRequest("SubmitBatch");
			getView().setResponse(out.toString());
		} catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void search() 
	{
		String[] values = getView().getParameterValues();
		ClientCommunicator cc = new ClientCommunicator(getView().getHost(), getView().getPort());
		SearchInput in = new SearchInput(values[0], values[1], values[2], values[3]);
		Object obj;
		try
		{
			obj = cc.Search(in);
			SearchOutput out = (SearchOutput) obj;
			getView().setRequest("Search");
			getView().setResponse(out.toString());
		} catch (ClientException e)
		{
			getView().setResponse("FAILED\n");
		}
	}

}

