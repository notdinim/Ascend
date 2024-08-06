package application;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

// FIREBALL PROJECTILE CLASS
public class Projectile{

	// FIELDS
	private Image img1, img2, img3, img4, img5, currentImg;
	private ImageView iv;
	private double xPos, yPos, width, height;
	private Rectangle rect;
	protected int projectileHitCount = 0, maxHit;
	protected int damage;
	
	// CONSTRUCTOR
	public Projectile()
	{
		// INITIATING IMAGES FOR FIREBALL PROJECTILE
		img1 = new Image("file:images\\misc\\fireball1.png");
		img2 = new Image("file:images\\misc\\fireball2.png");
		img3 = new Image("file:images\\misc\\fireball3.png");
		img4 = new Image("file:images\\misc\\fireball4.png");
		img5 = new Image("file:images\\misc\\fireball5.png");
		
		// FIREBALL 1
		if (Main.plrData.get(4).equals("fireball1"))
		{
			currentImg = img1;
			maxHit = 2;
			damage = 7;
		}
		// FIREBALL 2
		else if (Main.plrData.get(4).equals("fireball2"))
		{
			currentImg = img2;
			maxHit = 3;
			damage = 14;
		}
		// FIREBALL 3
		else if (Main.plrData.get(4).equals("fireball3"))
		{
			currentImg = img3;
			maxHit = 4;
			damage = 21;
		}
		// FIREBALL 4
		else if (Main.plrData.get(4).equals("fireball4"))
		{
			currentImg = img4;
			maxHit = 5;
			damage = 28;
		}
		// FIREBALL 5
		else if (Main.plrData.get(4).equals("fireball5"))
		{
			currentImg = img5;
			maxHit = 6;
			damage = 35;
		}
		
		// INTIATING IMAGEVIEW OBJECT
		iv = new ImageView();
		iv.setImage(currentImg);
		iv.setFitHeight(60);
		iv.setFitWidth(60);
		iv.setPreserveRatio(true);
		
		// SETTING DEFAILT X AND Y VALUE
		xPos = 0;
		yPos = 0;
		
		// INITIALIZING WIDTH AND HEIGHT FIELDS
		width = iv.getFitWidth();
		height = iv.getFitHeight();
		rect = new Rectangle(xPos, yPos, width, height);
	}
	
	// METHODS
	// RETURNING HEIGHT OF PROJECTILE
	public double getHeight()
	{
		return height;
	}
	
	// RETURNING WIDTH OF PROJECTILE
	public double getWidth()
	{
		return width;
	}
	
	// RETURNING X POSITION OF PROJECTILE
	public double getX()
	{
		return xPos;
	}
	
	// RETURNIGN Y POSITION OF PROJECTILE
	public double getY()
	{
		return yPos;
	}

	// MOVING PROJECTILE
	public void move(double velocityX, double velocityY)
	{
		xPos += velocityX;
		yPos += velocityY;
		
		iv.setX(xPos);
		iv.setY(yPos);
		
		rect.setX(xPos);
		rect.setY(yPos);
	}

	// SETTING POSITION OF PROJECTILE
	public void setPosition(double playerX, double playerY)
	{
		xPos = playerX + 75;
		yPos = playerY - 5;
		
		iv.setX(xPos);
		iv.setY(yPos);
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
	
	// RETURNING IMAGEVIEW OF PROJECTILE
	public ImageView getNode()
	{
		return iv;
	}
}