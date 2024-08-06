package application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// MAGE PROJECTILE
public class MageProjectile{

	// FIELDS
	private Image imgLeft, imgRight;
	private ImageView iv;
	private double xPos, yPos, width, height;
	public Rectangle rect;
	private int dir = 1;
	
	// CONSTRUCTOR
	public MageProjectile()
	{
		// INITIALIZING IMAGE OBJECTS
		imgLeft = new Image("file:images\\maingame\\mage_left_attack_projectile.gif");
		imgRight = new Image("file:images\\maingame\\mage_right_attack_projectile.gif");
		
		// INITIALIZING IMAGEVIEW
		iv = new ImageView();
		iv.setImage(imgRight);
		
		// SETTING X POSITION
		xPos = 0;
		yPos = 0;
		width = iv.getImage().getWidth();
		height = iv.getImage().getHeight();
		rect = new Rectangle(xPos, yPos, width, height);
	}
	
	// METHODS
	
	// GETTING PROJECTILE HEIGHT
	public double getHeight()
	{
		return height;
	}
	
	// GETTING PROJECTILE WIDTH
	public double getWidth()
	{
		return width;
	}
	
	// GETTING PROJECTILE XPOS
	public double getX()
	{
		return xPos;
	}
	
	// GETTING PROJECTILE YPOS
	public double getY()
	{
		return yPos;
	}

	// MOVING PROJECTILE DEPENDING ON DIRECTION OF PLAYER
	public void move()
	{
		if (dir == 1)
		{
			xPos += 30;
			iv.setImage(imgRight);
		}
		else if(dir == 2)
		{
			xPos -= 30;
			iv.setImage(imgLeft);
		}
		iv.setX(xPos);
		rect.setX(xPos);
	}

	// SETTING POSITION OF PROJECTILE
	public void setPosition(double playerX, double playerY)
	{
		xPos = playerX + 75;
		yPos = playerY - 5;
		
		iv.setX(xPos);
		iv.setY(yPos);

		// SETTING DIRECTION OF PROJECTILE
		if (Mage. right)
		{
			dir = 1;
		}
		else if (Mage. left)
		{
			dir = 2;
		}
	}
	
	// SETTING X OF PROJECTILE
	public void setX(int x)
	{
		xPos = x;		
		iv.setX(xPos);
		rect.setX(xPos);
	}
	
	// SETTING Y OF PROJECTILE
	public void setY(int y)
	{
		yPos = y;
		iv.setY(yPos);
		rect.setY(yPos);
	}
	
	// RETURNING IMAGEVIEW
	public ImageView getNode()
	{
		return iv;
	}
}