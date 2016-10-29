package se.albin.jscribble;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a method in your script handler as a script function. If you do not annotate your methods, they will not be
 * recognized by the script executor.<br>
 * The name is what the script needs to use to execute the method. It does not have to be the same name as the method.
 * Using the same name twice will not work, even if the arguments are different, as it is not supported yet.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ScriptFunction
{
	String name();
}
