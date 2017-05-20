package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/*
import org.jruby.Ruby;
import org.jruby.RubyRuntimeAdapter;
import org.jruby.javasupport.JavaEmbedUtils; 
*/


import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.ScriptContext;


public class JRubyScripting {

	
	private ScriptEngine ruby = new ScriptEngineManager().getEngineByName("jruby");

	public void eval(String expression) throws ScriptException{
		ruby.eval(expression);
	}
	
	public void evalFile(String filename) throws FileNotFoundException, ScriptException{
		String content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
		eval(content);
	}
	
	public static void main(String args[]){
		JRubyScripting r = new JRubyScripting();
		try {
			try {
				r.evalFile("./scripts/weapon.rb");
				r.ruby.put("ololo", 1);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
