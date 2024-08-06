package application;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// MAGE CLASS
public class Mage{

	// FIELDS
	protected static double xPos, yPos, hitboxX, hitboxY;
	private static Image imgRightRun;
	private static Image imgLeftRun;
	private Image imgRightAttack1, imgLeftAttack1;	
	private Image imgRightAttack2, imgLeftAttack2;
	private static Image imgRightJump;
	private static Image imgLeftJump;
	protected static ImageView iv;
	protected static boolean left, right;
	
	private double sceneWidth;
	private static double sceneHeight;

	protected static Rectangle leftHitbox, rightHitbox;
	protected static Rectangle redHP, greenHP;
	
	// PROJECTILE FOR ATTACKS
	protected ArrayList<MageProjectile> mageProjectile;
	
	public Mage(double sceneWidth, double sceneHeight) { 
		
		// INITIALIZING IMAGES
		imgRightRun = new Image("file:images\\maingame\\mage_right_run.gif");
		imgLeftRun = new Image("file:images\\maingame\\mage_left_run.gif");
		imgRightAttack1 = new Image("file:images\\maingame\\mage_right_attack_1.gif");
		imgLeftAttack1 = new Image("file:images\\maingame\\mage_left_attack_1.gif");
		imgRightAttack2 = new Image("file:images\\maingame\\mage_right_attack_2.gif");
		imgLeftAttack2 = new Image("file:images\\maingame\\mage_left_attack_2.gif");
		imgRightJump = new Image("file:images\\maingame\\mage_right_jump.gif");
		imgLeftJump = new Image("file:images\\maingame\\mage_left_jump.gif");

		// INITIALIZING ARRAYLIST OF MAGE PROJECTILE OBJECT
		mageProjectile = new ArrayList<MageProjectile>();
		
		// INITIALIZING IMAGEVIEW
		iv = new ImageView();
		iv.setImage(imgRightRun);
		iv.setFitHeight(110);
		iv.setFitWidth(110);
		iv.setPreserveRatio(true);
		xPos = (600 - iv.getFitWidth()/2);
		yPos = (sceneHeight - 200);
		iv.setX(xPos);
		iv.setY(yPos);

		// SETTING UP HITBOXES
		hitboxX = xPos - 569;
		hitboxY = yPos + 35;
		
		leftHitbox = new Rectangle(hitboxX, hitboxY, 50, 30);
		rightHitbox = new Rectangle(hitboxX + 100, hitboxY, 50, 30);
		
		// CREATING HEALTH BAR
		redHP = new Rectangle(xPos - 700, yPos - 20, 100, 10);
		redHP.setFill(Color.RED);
		greenHP = new Rectangle(xPos - 700, yPos - 20, 100, 10);
		greenHP.setFill(Color.GREEN);
		
		// INITIALIZING SCENE DIMENISONS
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
	}
	// METHODS
	public static ImageView getNode()
	{
		return iv;
	}

	// MOVE METHOD FOR MAGE
	// CHANGING IMAGES DEPENDING ON WHERE PLAYER IS MOVING
	public static void move(boolean jump)
	{
		if (!Levels.plrAttacked)
		{
			if (left && jump)
			{
				iv.setImage(imgLeftJump);
				iv.setFitHeight(110);
				iv.setFitWidth(110);
				iv.setPreserveRatio(true);
			}
			else if (right && jump)
			{
				iv.setImage(imgRightJump);
				iv.setFitHeight(110);
				iv.setFitWidth(110);
				iv.setPreserveRatio(true);
			}
			else if (left)
			{
				iv.setImage(imgLeftRun);
				iv.setFitHeight(110);
				iv.setFitWidth(110);
				iv.setPreserveRatio(true);
			}
			else if (right)
			{
				iv.setImage(imgRightRun);
				iv.setFitHeight(110);
				iv.setFitWidth(110);
				iv.setPreserveRatio(true);
			}
		}
	}
	
	// CHANGING IMAGE FOR ATTACK 1
	public void attack1()
	{
		if (left)
		{
			iv.setImage(imgLeftAttack1);
		}
		else if (right)
		{
			iv.setImage(imgRightAttack1);
		}
		iv.setFitHeight(110);
		iv.setFitWidth(110);
		iv.setPreserveRatio(true);
	}
	
	// CHANGING IMAGE FOR ATTAACK 2
	public void attack2()
	{
		if (left)
		{
			iv.setImage(imgLeftAttack2);
		}
		else if (right)
		{
			iv.setImage(imgRightAttack2);
		}
		iv.setFitHeight(110);
		iv.setFitWidth(110);
		iv.setPreserveRatio(true);
	}
	
	// SETTING X POSITION AND ALSO MOVING HITBOX AND HEALTH BAR WITH PLAYER
	public static void setX(double xPos)
	{
		Mage.xPos = xPos;
		iv.setX(Mage.xPos);
		leftHitbox.setX(hitboxX + Mage.xPos);
		rightHitbox.setX(hitboxX + Mage.xPos + 100);
		
		redHP.setX(Mage.xPos - 10);
		greenHP.setX(Mage.xPos - 10);
	}
	
	// SETTING Y POSITION AND ALSO MOVING HITBOX AND HEALTH BAR WITH PLAYER
	public static void setY(double yPos)
	{
		Mage.yPos = yPos;
		iv.setY(Mage.yPos);
		leftHitbox.setY(Mage.yPos + 35);
		rightHitbox.setY(Mage.yPos + 35);
		
		redHP.setY(Mage.yPos - 20);
		greenHP.setY(Mage.yPos - 20);
	}
	
	// GETTIONG PLAYER X POS
	public static double getX()
	{
		return xPos;
	}

	// GETTING PLAYER Y POS
	public static double getY()
	{
		return yPos;
	}
	
	// RESETTING PLAYER POSITION
	public static void resetPos()
	{
		xPos = (600 - iv.getFitWidth()/2);
		yPos = (sceneHeight - 200);
		iv.setX(xPos);
		iv.setY(yPos);
		
		leftHitbox.setX(hitboxX + xPos);
		rightHitbox.setX(hitboxX + xPos + 100);
		
		redHP.setX(xPos - 10);
		greenHP.setX(xPos - 10);
		
		leftHitbox.setY(yPos + 35);
		rightHitbox.setY(yPos + 35);
		
		redHP.setY(yPos - 20);
		greenHP.setY(yPos - 20);
	}
}
