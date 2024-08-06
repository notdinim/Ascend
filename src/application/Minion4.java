// IMPORTS

package application;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

// MINION 4 CLASS - POLYMORPHED BOSS 4 CLASS
public class Minion4 extends Boss4{

	// FIELDS
	public double xPos, yPos, xSpeed;
	private int minionWidth, minionHeight;
	protected static  Image imgMinionLeft, imgMinionRight;
	private Image imgDead, imgExplode;
	private ImageView iv;
	private Random rnd;
	private int minionDir;
	// CHANCE FOR MINION TO EXPLODE
	protected int explodeChance;
	
	// MINION GRAVITY
	protected final int minionGravity = 10;
	protected boolean minionGravityActive = false;
	protected boolean minionPlatformTouched = false;
	
	// HANDLERS
	protected boolean isDead = false;
	protected boolean detonate = false;
	
	// KEY FRAME OBJECT FOR MINION ATTACKS
	protected KeyFrame minionAttack;
	protected Timeline tlMinionAttack;
	
	// KEYFRAME OBJECT FOR MINION DEATH - NOT USED
	protected KeyFrame minionDead;
	protected Timeline tlMinionDead;
	
	// CONSTRUCTOR
	public Minion4(double bossX, double bossY) {
		
		// INHERITING SUPER CLASS
		super(bossX, bossY);
		
		// INITIALIZING MINION FIELDS
		rnd = new Random();
		xPos = bossX;
		yPos = bossY;
		xSpeed = rnd.nextInt(6) + 6;
		minionDir = rnd.nextInt(2) + 1;
		explodeChance = 1;
		imgMinionLeft = new Image("file:images\\enemies\\minion_4_left.png");
		imgMinionRight = new Image("file:images\\enemies\\minion_4_right.png");
		imgDead = new Image("file:images\\enemies\\smoke.gif");
		imgExplode = new Image("file:images\\enemies\\explosion.gif");
		
		// INITIALIZING IMAGEVIEW OBJECT
		iv = new ImageView();
		
		if (minionDir == 1)
		{
			iv.setImage(imgMinionRight);
		}
		else if (minionDir == 2)
		{
			iv.setImage(imgMinionLeft);
		}
		
		iv.setFitHeight(60);
		iv.setFitWidth(60);
		iv.setPreserveRatio(true);
		
		// GETTING MINION WIDTH AND HEIGHT
		minionWidth = (int) iv.getFitWidth();
		minionHeight = (int) iv.getFitHeight();
		
		// SETTING LOCATION OF MINION TO XPOS AND YPOS
		iv.setX(xPos);
		iv.setY(yPos);	
	}

	// METHODS
	// GETTING HEIGHT OF MINION NODE
	public int getHeight()
	{
		return minionHeight;
	}

	// GETTING WIDTH OF MINION NODE
	public int getWidth()
	{
		return minionWidth;
	}

	// GETTING NODE OF MINION
	public ImageView getNode()
	{
		return iv;
	}

	// GETTING XPOS OF MINION
	public double getX()
	{
		return xPos;
	}

	// GETTING YPOS OF MINION
	public double getY()
	{
		return yPos;
	}

	// MOVE METHOD TO MOVE MINION
	public void move()
	{
		// CHECKING IF MINION HASN'T DETONATED AND MOVING ACCORDINGLY
		if (!detonate)
		{
			if (minionDir == 1)
			{
				xPos += xSpeed;
			}
			else if (minionDir == 2)
			{
				xPos -= xSpeed;
			}

			// SETTING IV TO XPOS AND YPOS
			iv.setX(xPos); 
			iv.setY(yPos);
		}
	}

	// DEAD METHOD THAT SETS IMAGEVIEW TO EXPLOSION IMAGE
	public void dead()
	{
		iv.setImage(imgExplode);
	}

	// SETTING XPOS TO X VALUE PASSED IN PARAMETERS
	public void setX(double x)
	{
		xPos = x;
		iv.setX(xPos);
	}

	// SETTING YPOS TO Y VALUE PASSING IN PARAMETERS
	public void setY(double y)
	{
		yPos = y;
		iv.setY(yPos);
	}

	// SCENE BOUNDS METHOD TO CHECK IF MINION COLLIDES WITH EDGE
	public void sceneBounds(double sceneWidth, Rectangle e)
	{
		if (!isDead)
		{
			if (xPos <= 0)
			{
				xSpeed = -xSpeed;
				iv.setImage(imgMinionRight);
			}
			else if (xPos + getWidth() >= sceneWidth)
			{
				xSpeed = -xSpeed;
				iv.setImage(imgMinionLeft);
			}
		}
	}
}
