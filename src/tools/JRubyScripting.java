package tools;

import java.io.File;
import java.nio.file.NoSuchFileException;

import org.jruby.embed.PathType;
import org.jruby.embed.ScriptingContainer;

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
	
	
	public static void main(String args[]){
		JRubyScripting r = new JRubyScripting();
		try {
			r.evalFile("./scripts/test.rb");
		} catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
