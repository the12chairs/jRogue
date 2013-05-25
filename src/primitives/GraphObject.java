package primitives;

public class GraphObject {
	
	protected String face;
	
	protected long x;
	protected long y;
	protected boolean visible;
	
	
	public String getFace(){
		return this.face;
	}
	
	
	public GraphObject(){
		
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
