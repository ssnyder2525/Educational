import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;



public class request 
{
	private static final String HTTP_POST = "POST";
	private static String urlPath = "http://open.api.ebay.com/shopping?callname=FindPopularItems&responseencoding=XML&appid=YourAppId&siteid=0&QueryKeywords=dog&version=713";
	
	public request() 
	{
	}
	public static void main(String[] args)
	{
		XStream xmlStream = new XStream(new DomDriver());
		try 
		{
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			//maybe code request to bytes.
//			connection.getOutputStream().write(11221);
//			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object result = xmlStream.fromXML(connection.getInputStream());
				connection.getInputStream().close();
				System.out.println(result);
			}
			else 
			{
				
			}
		}
		catch (IOException e) 
		{
			
		}
	}

}
