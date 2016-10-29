package examples;

import se.albin.jscribble.IScriptHandler;
import se.albin.jscribble.ScriptExecutor;
import se.albin.jscribble.ScriptFunction;

import java.io.File;

public class TestScriptHandler implements IScriptHandler
{
	@ScriptFunction(name = "helloWorld")
	public void helloWorld()
	{
		System.out.println("Hello, world!");
	}
	
	@ScriptFunction(name = "hi")
	public void thisIsARandomName(String message)
	{
		System.out.printf("Hi, %s\n", message);
	}
	
	@ScriptFunction(name = "add")
	public void addition(int a, int b)
	{
		System.out.printf("%d + %d = %d\n", a, b, a + b);
	}
	
	@ScriptFunction(name = "abc")
	public void abc(String a, String b, String c)
	{
		System.out.printf("%s|%s|%s\n", a, b, c);
	}
	
	public static void main(String[] args)
	{
		new ScriptExecutor(new File("test.txt"), new TestScriptHandler()).execute();
	}
}
