package rendering;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;

public class Camera
{
    private float x, y, scale, cent_x, cent_y;

    
    public Camera(int x, int y){
    	this.x = x;
    	this.y = y;
    	scale = 0;
    }
    public Camera()
    {
        x = y = 0;
        scale = 1;
    }

    public void use()
    {

        glTranslatef(y*TileRenderer.TILE_SIZE, x*TileRenderer.TILE_SIZE, 0);
        this.x = this.y = 0;
    }

    public void move(float x, float y)
    {

        this.x += x;
        this.y += y;
        setCent_x(x);
        setCent_y(y);
        
    }
    
    public void zoom(float scale) {
        this.scale += scale * this.scale;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getScale() {
        return scale;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }
	public float getCent_x() {
		return cent_x;
	}
	public void setCent_x(float cent_x) {
		this.cent_x = cent_x;
	}
	public float getCent_y() {
		return cent_y;
	}
	public void setCent_y(float cent_y) {
		this.cent_y = cent_y;
	}
}