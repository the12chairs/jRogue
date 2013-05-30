package tools;

import java.io.File;
import java.nio.file.NoSuchFileException;

import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

import properties.Race;

public class JRubyScripting {
	ScriptingContainer ruby;
	
	public JRubyScripting(){
		ruby = new ScriptingContainer();
	}
	
	
	// Обертки для удобства
	
	public void evalString(String code){
		ruby.runScriptlet(code);
	}
	
	
	
	public void evalFile(String filename) throws NoSuchFileException{
		//File f = new File(filename);
		ruby.runScriptlet(PathType.ABSOLUTE, filename);
	}
	
	/*
	public Race loadRace(String filename){
		Race r = new Race();
		return race;
	}
	
	*/
	public static void main(String args[]){
		JRubyScripting r = new JRubyScripting();
		try {
			r.evalFile("./scripts/test.rb");
			//r.
		} catch (NoSuchFileException e) {

			e.printStackTrace();
		}
	}
	
}
