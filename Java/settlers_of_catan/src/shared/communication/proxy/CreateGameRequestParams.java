package shared.communication.proxy;

public class CreateGameRequestParams
{

	/**
	 * The username of the user
	 */
	public String name;
	/**
	 * Whether tiles are random or not
	 */
	public Boolean randomTiles;
	
	/**
	 * Whether numbers are random or not
	 */
	public Boolean randomNumbers;
	
	/**
	 * Whether ports are random or not
	 */
	public Boolean randomPorts;
	
	
	
	public CreateGameRequestParams() 
	{}


	
	public CreateGameRequestParams(String name, Boolean randomTiles, Boolean randomNumbers, Boolean randomPorts) { 
		this.name = name;
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
	}
}
