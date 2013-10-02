package primitives;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GraphObject {
	
	protected String face;
	
	protected long x;
	protected long y;
	protected boolean visible;
	
	protected Texture texture; 
	
	public String getFace(){
		return this.face;
	}
	
	
	
	public void loadTexture(){
		Texture t = null;
		try {
			texture = TextureLoader.getTexture
					("PNG", ResourceLoader.getResourceAsStream(face));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	
	
	public GraphObject(){
		
	}
	
	public GraphObject(String face, long x, long y){
		this.face = face;
		this.x = x;
		this.y = y;
	}
	
	public GraphObject(String face){
		this.face = face;
	}
	
	public void setFace(String face){
		this.face = face;
	}
	
	public long getX(){
		return this.x;
	}
	
	public long getY(){
		return this.y;
	}
	
	public void setX(long x) {
		this.x = x;
	}
	public void setY(long y) {
		this.y = y;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public boolean getVisible(){
		return visible;
	}
}
