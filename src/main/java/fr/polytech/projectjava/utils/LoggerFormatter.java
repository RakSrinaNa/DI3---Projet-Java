package fr.polytech.projectjava.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Formatter for the logger.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 21/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-21
 */
public class LoggerFormatter extends Formatter
{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSSS");
	
	@Override
	public String format(LogRecord record)
	{
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(record.getLevel().getLocalizedName());
		stringBuilder.append(": \t");
		stringBuilder.append(dateFormat.format(new Date(record.getMillis())));
		stringBuilder.append(" \t");
		int stackIndex;
		for(stackIndex = 0; stackIndex < Thread.currentThread().getStackTrace().length; stackIndex++)
		{
			StackTraceElement trace = Thread.currentThread().getStackTrace()[stackIndex];
			if(trace.getClassName().startsWith("fr.polytech.projectjava") && !trace.getClassName().startsWith("fr.polytech.projectjava.utils.LoggerFormatter") && !trace.getClassName().startsWith("fr.polytech.projectjava.utils.Log"))
				break;
		}
		stringBuilder.append(Thread.currentThread().getStackTrace()[stackIndex]);
		
		stringBuilder.append(" -> ");
		
		stringBuilder.append(record.getMessage());
		if(record.getThrown() != null)
		{
			stringBuilder.append(System.getProperty("line.separator"));
			stringBuilder.append(record.getThrown().toString());
		}
		
		return stringBuilder.append("\n").toString();
	}
}
