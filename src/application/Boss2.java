// IMPORTS
package application;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// BOSS 2 CLASS
public class Boss2{

	// FIELDS
	public double xPos, yPos, xSpeed;
	private int bossWidth, bossHeight;
	protected static Image imgBoss, imgBossLeft, imgBossRight;
	private ImageView iv;
	private Random rnd;
	private int bossDir;
	private boolean freeRoam = false;
	
	// BOSS' GRAVITY
	protected final int bossGravity = 10;
	protected boolean bossGravityActive = false;
	
	// RECTANGLE OBJECTS FOR BOSS HEALTH 
	protected Rectangle redHP, greenHP;
	
	// MINION FIELDS TO BE OVERIDDEN
	protected int minionGravity = 10;
	protected boolean minionGravityActive = false;
	protected boolean minionPlatformTouched = false;
	protected boolean isDead = false;
	protected boolean detonate = false;
	protected int explodeChance;
	protected KeyFrame minionDead;
	public static Timeline tlMinionDead;
	
	// CONSTRUCTOR
	public Boss2(double bossX, double bossY)
	{
		// INITIALIZING RANDOM AND OTHER BOSS FIELDS
		rnd = new Random();
		xPos = bossX;
		yPos = bossY;
		xSpeed = rnd.nextInt(4) + 4;
		bossDir = rnd.nextInt(2) + 1;
		explodeChance = rnd.nextInt(2) + 1;
		imgBoss = new Image("file:images\\enemies\\boss_2_right.png");
		imgBossLeft = new Image("file:images\\enemies\\boss_2_left.png");
		imgBossRight = new Image("file:images\\enemies\\boss_2_right.png");
		
		// INITIALIZING IMAGE VIEW OBJECT
		iv = new ImageView();
		iv.setImage(imgBoss);
		iv.setFitHeight(85);
		iv.setFitWidth(85);
		iv.setPreserveRatio(true);
		
		// SETTING BOSS WIDTH AND HEIGHT
		bossWidth = (int) iv.getFitWidth();
		bossHeight = (int) iv.getFitHeight();
		
		// SETTING BOSS POSTION TO XPOS AND YPOS
		iv.setX(xPos);
		iv.setY(yPos);
		
		// CREATING BOSS HEALTH BAR
		redHP = new Rectangle(xPos - 20, yPos - 20, 100, 10);
		redHP.setFill(Color.RED);
		greenHP = new Rectangle(xPos - 20, yPos - 20, 100, 10);
		greenHP.setFill(Color.GREEN);
	}
	
	// METHODS
	// GETTING HEIGHT OF BOSS IMAGE
	public int getHeight()
	{
		return bossHeight;
	}
	
	// GETTING WIDTH OF BOSS IMAGE
	public int getWidth()
	{
		return bossWidth;
	}
	
	// GETTING NODE OF BOSS
	public ImageView getNode()
	{
		return iv;
	}
	
	// GETTING X POSITION OF BOSS
	public double getX()
	{
		return xPos;
	}
	
	// GETTING Y POSITION OF BOSS
	public double getY()
	{
		return yPos;
	}
	
	// MOVE METHOD TO MOVE BOSS
	public void move()
	{
		// MOVING BOSS
		if (bossDir == 1)
		{
			xPos += xSpeed;
		}
		else if (bossDir == 2)
		{
			xPos -= xSpeed;
		}

		iv.setX(xPos); 
		iv.setY(yPos);
		
		// MOVING HEALTH BAR WITH BOSS
		redHP.setX(xPos - 20);
		greenHP.setX(xPos - 20);
		redHP.setY(yPos - 20);
		greenHP.setY(yPos - 20);
	}
	
	// SETTING BOSS X
	public void setX(double x)
	{
		xPos = x;
		iv.setX(xPos);
		
		// SETTING HEALTH BAR LOCATION
		redHP.setX(xPos - 20);
		greenHP.setX(xPos - 20);
	}
	
	// SETTING BOSS Y
	public void setY(double y)
	{
		yPos = y;
		iv.setY(yPos);
		
		// SETTING HEALTH BAR LOCATION
		redHP.setY(yPos - 20);
		greenHP.setY(yPos - 20);
	}
	
	// SCENE BOUNDS METHOD TO CHECK IF DEMON COLLIDES WITH EDGE
	public void sceneBounds(double sceneWidth, Rectangle bossPlatform)
	{
		if (xPos <= 0)
		{
			xSpeed = -xSpeed;
			iv.setImage(imgBossRight);
		}
		else if (xPos + getWidth() >= sceneWidth)
		{
			xSpeed = -xSpeed;
			iv.setImage(imgBossLeft);
		}

		// LIMITING BOSS MOVEMENT TO TOP PLATFORM
		if (!freeRoam)
		{
			if (xPos <= bossPlatform.getX())
			{
				xSpeed = -xSpeed;
			}

			else if (xPos + getWidth() >= bossPlatform.getX() + bossPlatform.getWidth())
			{
				xSpeed = -xSpeed;
			}
		}
	}
	
	// METHOD TO ALLOW FOR BOSS FREE MOVEMENT
	public void enableFreeRoam()
	{
		if (!freeRoam)
		{			
			if (Town.character.equals("samurai"))
			{
				if (xPos < Samurai.xPos)
				{
					bossDir = 2;
					iv.setImage(imgBossLeft);
				}
				else if (xPos > Samurai.xPos)
				{
					bossDir = 1;
					iv.setImage(imgBossRight);
				}
			}
			else if (Town.character.equals("mage"))
			{
				if (xPos < Mage.xPos)
				{
					bossDir = 2;
					iv.setImage(imgBossLeft);
				}
				else if (xPos > Mage.xPos)
				{
					bossDir = 1;
					iv.setImage(imgBossRight);
				}
			}
			freeRoam = true;
		}
	}
	
	// METHOD TO BE OVERRIDDEN BY MINION CLASS
	public void dead() {
		
	}
}