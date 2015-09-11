package primitives;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import java.io.FileInputStream;
import java.io.File;
public class GraphObject {
	
	protected String face;
	
	protected long x;
	protected long y;
	protected boolean visible;
	protected boolean visited;
	protected Texture texture; 
	

	public void visit(){
		visited = true;
	}
	
	public boolean isVisited(){
		return visited;
	}
	public void destroyTexture(){
		if (texture != null)
			GL11.glDeleteTextures(texture.getTextureID());
	}
	public void loadTexture(){

		Texture t = null;
		FileInputStream fi = null;
		try {
			fi = new FileInputStream(new File(face));
			texture = TextureLoader.getTexture("PNG", fi);//ResourceLoader.getResourceAsStream(face));
			fi.close();

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

	public String getFace(){
		return this.face;
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
