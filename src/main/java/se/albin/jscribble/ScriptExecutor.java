package se.albin.jscribble;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The script executor takes a script file and a script handler to execute a script. It will log errors if the script
 * file has an incorrect syntax.
 */
public final class ScriptExecutor
{
	private static final Logger LOG = LoggerFactory.getLogger(ScriptExecutor.class);
	
	private final String[] lines;
	private final IScriptHandler handler;
	private final Map<String, Method> functions;
	
	/**
	 * Creates a new script executor, which needs a file and a handler. If the handler contains two functions of the
	 * same name, only the first one will be accepted, and a warning will show up in the console.
	 * @param scriptFile File to read as script.
	 * @param handler Script handler, contains the script functions.
	 */
	public ScriptExecutor(File scriptFile, IScriptHandler handler)
	{
		List<String> lineList;
		
		try
		{
			lineList = Files.readAllLines(Paths.get(scriptFile.getAbsolutePath()));
		}
		catch(IOException e)
		{
			lineList = Collections.emptyList();
			e.printStackTrace();
		}
		
		lines = lineList.toArray(new String[1]);
		this.handler = handler;
		functions = new HashMap<>();
		
		for(Method method : handler.getClass().getDeclaredMethods())
		{
			if(method.isAnnotationPresent(ScriptFunction.class))
			{
				ScriptFunction function = method.getAnnotation(ScriptFunction.class);
				if(functions.containsKey(function.name()))
					LOG.error("Script handler contains two functions of the same name \"{}\"", function.name());
				else
					functions.put(function.name(), method);
			}
		}
	}
	
	/**
	 * Executes the script. The script is read line by line, and executes functions in the script handler. Pay attention
	 * to any errors in the console, as they can appear if the script file contains a syntax error.
	 */
	public void execute()
	{
		for(int i = 0; i < lines.length; i++)
		{
			String[] split = lines[i].split(" ", 2);
			
			if(functions.containsKey(split[0]))
			{
				Method method = functions.get(split[0]);
				Object[] execArgs;
				
				if(split.length > 1)
				{
					String[] scriptArgs = split[1].split(",");
					execArgs = new Object[scriptArgs.length];
					Class[] parameterTypes = method.getParameterTypes();
					
					if(scriptArgs.length == method.getParameterCount())
						for(int j = 0; j < method.getParameterCount(); j++)
						{
							Class parameterType = parameterTypes[j];
							String argument = scriptArgs[j].trim();
							
							if(parameterType == String.class)
								execArgs[j] = argument;
							else if(parameterType == int.class)
								try
								{
									execArgs[j] = Integer.parseInt(argument);
								}
								catch(NumberFormatException e)
								{
									LOG.error("Provided argument is not an integer, argument #{} (line {})", j + 1,
									          i + 1);
									return;
								}
							else if(parameterType == boolean.class)
							{
								if(argument.equalsIgnoreCase("true"))
									execArgs[j] = true;
								else if(argument.equalsIgnoreCase("false"))
									execArgs[j] = false;
								else
								{
									LOG.error("Provided argument is not a boolean, argument #{} (line {})", j + 1,
									          i + 1);
									return;
								}
							}
						}
					else
					{
						LOG.error("Invalid amount of arguments, {} needed, {} provided (line {})",
						          method.getParameterCount(), scriptArgs.length, i + 1);
						return;
					}
				}
				else
					execArgs = new Object[0];
				
				try
				{
					method.invoke(handler, execArgs);
				}
				catch(IllegalAccessException | InvocationTargetException e)
				{
					e.printStackTrace();
				}
			}
			else
				LOG.error("Found no function \"{}\" (line {})", split[0], i + 1);
		}
	}
}
