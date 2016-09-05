package shared.communication;

import java.util.ArrayList;

import shared.models.Project;

public class GetProjectsOutput 
{

	/**
	 * The project id for the getProject function's output
	 */
	private ArrayList<Project> PROJECTS;
	
	public GetProjectsOutput() 
	{}
	
	/**
	 * This object stores the data for the output of the getProject function
	 * @param projectID
	 * @param projectTitle
	 */
	public GetProjectsOutput(ArrayList<Project> projects)
	{
		this.setPROJECTS(projects);
	}

	/**
	 * @return the pROJECTS
	 */
	public ArrayList<Project> getPROJECTS()
	{
		return PROJECTS;
	}

	/**
	 * @param pROJECTS the pROJECTS to set
	 */
	public void setPROJECTS(ArrayList<Project> pROJECTS)
	{
		PROJECTS = pROJECTS;
	}
	
	@Override
	public String toString()
	{
		StringBuilder st = new StringBuilder();
		int i = 0;
		while (i != this.PROJECTS.size())
		{
			st.append(this.PROJECTS.get(i).getProjectID() + "\n");
			st.append(this.PROJECTS.get(i).getTitle() + "\n");
			i++;
		}
		return st.toString();
	}

}
