package fr.polytech.projectjava.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Class to attach a configuration file.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public class Configuration
{
	private static final Properties properties = new Properties();
	
	/**
	 * Get an int from the config.
	 *
	 * @param key The key of the value to retrieve.
	 *
	 * @return The int.
	 */
	public static int getInt(String key)
	{
		return Integer.parseInt(getString(key));
	}
	
	/**
	 * Get a string from the config.
	 *
	 * @param key The key of the value to retrieve.
	 * @return The string.
	 */
	public static String getString(String key)
	{
		return properties.getProperty(key);
	}
	
	static
	{
		try
		{
			properties.load(Configuration.class.getResourceAsStream("/settings.properties"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
