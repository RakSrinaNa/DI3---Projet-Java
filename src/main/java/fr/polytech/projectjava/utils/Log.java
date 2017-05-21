package fr.polytech.projectjava.utils;

import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility to use logger.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class Log
{
	private static final ArrayList<LogListener> listeners = new ArrayList<>();
	private static Logger logger;
	
	/**
	 * Interface to listen new messages.
	 */
	public interface LogListener
	{
		/**
		 * Used when a new message is logged.
		 *
		 * @param level   The level of the logging.
		 * @param message The message sent.
		 */
		void onLogMessage(Level level, String message);
		
		/**
		 * Used when a new message with a throwable is logged.
		 *
		 * @param level     The level of the logging.
		 * @param message   The message sent.
		 * @param throwable The throwable sent.
		 */
		void onLogMessage(Level level, String message, Throwable throwable);
	}
	
	/**
	 * Add a listener to the logger.
	 *
	 * @param listener The listener to add.
	 */
	public static void addListener(LogListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Initialize the current logger with the given name.
	 *
	 * @param name The name of the logger.
	 *
	 * @return The new logger object.
	 */
	public static Logger setAppName(@SuppressWarnings("SameParameterValue") String name)
	{
		logger = Logger.getLogger(name);
		logger.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new LoggerFormatter());
		logger.addHandler(handler);
		return logger;
	}
	
	/**
	 * Send a warning message.
	 *
	 * @param message The message to send.
	 */
	public static void warning(String message)
	{
		log(Level.WARNING, message);
	}
	
	/**
	 * Send a warning message with a throwable.
	 *
	 * @param message   The message to send.
	 * @param throwable The throwable to send with.
	 */
	public static void warning(String message, Throwable throwable)
	{
		log(Level.WARNING, message, throwable);
	}
	
	/**
	 * Send an info message.
	 *
	 * @param message The message to send.
	 */
	public static void info(String message)
	{
		log(Level.INFO, message);
	}
	
	/**
	 * Send an error message.
	 *
	 * @param message The message to send.
	 */
	public static void error(String message)
	{
		log(Level.SEVERE, message);
	}
	
	/**
	 * Send an error message with a throwable.
	 *
	 * @param message   The message to send.
	 * @param throwable The throwable to send with.
	 */
	public static void error(String message, Throwable throwable)
	{
		log(Level.SEVERE, message, throwable);
	}
	
	/**
	 * Send a message with a custom level and a throwable.
	 *
	 * @param level   The level to log at.
	 * @param message The message to send.
	 */
	public static void log(Level level, String message)
	{
		getInstance().log(level, message);
		listeners.forEach(logListener -> logListener.onLogMessage(level, message));
	}
	
	/**
	 * Send a message with a custom level and a throwable.
	 *
	 * @param level     The level to log at.
	 * @param message   The message to send.
	 * @param throwable The throwable to send with.
	 */
	public static void log(Level level, String message, Throwable throwable)
	{
		getInstance().log(level, message, throwable);
		listeners.forEach(logListener -> logListener.onLogMessage(level, message, throwable));
	}
	
	/**
	 * Get the instance of the logger.
	 *
	 * @return The logger object.
	 *
	 * @see Logger
	 */
	public static Logger getInstance()
	{
		return logger != null ? logger : setAppName("JavaProject");
	}
}
