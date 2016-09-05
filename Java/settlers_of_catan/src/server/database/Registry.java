package server.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * @author Bo Pace
 *
 */

public class Registry {
	
	/**
	 * The plugin currently being used by the server.
	 */
	IPersistencePlugin plugin;
	
	/**
	 * Loads the correct .jar file for the persistence plugin.
	 * @param plugin The location of the plugin we want to load.
	 * @throws IOException 
	 */
	public void loadConfig(String pluginClass, int delta)  {
		
		Class c = null;
		try {
			c = Class.forName("server.database.plugin." + pluginClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		IPersistencePlugin p = null;
		try {
			p = (IPersistencePlugin)c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		p.setDelta(delta);
		this.plugin = p;
	}
	
	/**
	 * Gets the plugin (SQLite3 or Mongo) as specified by the user.
	 * @return
	 */
	public IPersistencePlugin getPlugin() {
		return plugin;
	}

}
