package rendering;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;

public class Camera
{
    private float x, y, scale;

    public Camera()
    {
        x = y = 0;
        scale = 1;
    }

    public void use()
    {
    	//glTranslatef(Display.getWidth() / 2, Display.getHeight() / 2, 0);
        //glScalef(scale, scale, 1);
        glTranslatef(y*TileRenderer.TILE_SIZE, x*TileRenderer.TILE_SIZE, 0);
        this.x = this.y = 0;
    }

    public void move(float x, float y)
    {
        //x /= scale;
        //y /= scale;
        this.x += x;
        this.y += y;
        
        
    }

    public void zoom(float scale)
    {
        this.scale += scale * this.scale;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public float getScale()
    {
        return scale;
    }

    public void setX(float x)
    {
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
}