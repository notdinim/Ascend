package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// SAMURAI CLASS
public class Samurai {

	// FIELDS
	protected static double xPos, yPos, hitboxX, hitboxY;
	private Image imgRightRun, imgLeftRun;
	private Image imgRightAttack1, imgLeftAttack1;	
	private Image imgRightAttack2, imgLeftAttack2;
	private Image imgRightJump, imgLeftJump;
	protected static ImageView iv;
	protected static boolean left;
	protected static boolean right;
	
	private double sceneWidth;
	private static double sceneHeight;

	protected static Rectangle leftHitbox, rightHitbox;
	protected static Rectangle redHP, greenHP;
	
	// CONSTRUCTOR
	public Samurai(double sceneWidth, double sceneHeight)
	{
		// INITIALIZING SAMURAI IMAGES
		imgRightRun = new Image("file:images\\maingame\\samurai_right_run.gif");
		imgLeftRun = new Image("file:images\\maingame\\samurai_left_run.gif");
		imgRightAttack1 = new Image("file:images\\maingame\\samurai_right_attack_1.gif");
		imgLeftAttack1 = new Image("file:images\\maingame\\samurai_left_attack_1.gif");
		imgRightAttack2 = new Image("file:images\\maingame\\samurai_right_attack_2.gif");
		imgLeftAttack2 = new Image("file:images\\maingame\\samurai_left_attack_2.gif");
		imgRightJump = new Image("file:images\\maingame\\samurai_right_jump.gif");
		imgLeftJump = new Image("file:images\\maingame\\samurai_left_jump.gif");

		// INITIALIZING IMAGEVIEW
		iv = new ImageView();
		iv.setImage(imgRightRun);
		iv.setFitHeight(80);
		iv.setFitWidth(80);
		iv.setPreserveRatio(true);
		xPos = (600 - iv.getFitWidth()/2);
		yPos = (sceneHeight - 200);
		iv.setX(xPos);
		iv.setY(yPos);

		// SETTING UP RECTANGLE CLASS HITBOXES
		hitboxX = xPos - 583;
		hitboxY = yPos + 35;
		
		leftHitbox = new Rectangle(hitboxX, hitboxY, 50, 30);
		rightHitbox = new Rectangle(hitboxX + 100, hitboxY, 50, 30);
		
		// INITIALIZING HEALTH BAR
		redHP = new Rectangle(xPos - 700, yPos - 20, 100, 10);
		redHP.setFill(Color.RED);
		greenHP = new Rectangle(xPos - 700, yPos - 20, 100, 10);
		greenHP.setFill(Color.GREEN);
		
		// INITIALIZING SCENE HEIGHT AND WIDTH
		this.sceneWidth = sceneWidth;
		this.sceneHeight = sceneHeight;
	}
	
	// METHODS
	// RETURNING IMAGEVIEW
	public static ImageView getNode()
	{
		return iv;
	}

	// METHOD TO MOVE SAMURAI DEPENDING ON KEYS PRESSED
	public void move(boolean jump)
	{
		if (!Levels.plrAttacked)
		{
			if (left && jump)
			{
				iv.setImage(imgLeftJump);
				iv.setFitHeight(100);
				iv.setFitWidth(100);
				iv.setPreserveRatio(true);
			}
			else if (right && jump)
			{
				iv.setImage(imgRightJump);
				iv.setFitHeight(100);
				iv.setFitWidth(100);
				iv.setPreserveRatio(true);
			}
			else if (left)
			{
				iv.setImage(imgLeftRun);
				iv.setFitHeight(80);
				iv.setFitWidth(80);
				iv.setPreserveRatio(true);
			}
			else if (right)
			{
				iv.setImage(imgRightRun);
				iv.setFitHeight(80);
				iv.setFitWidth(80);
				iv.setPreserveRatio(true);
			}
		}
	}
	
	// CHANGING IMAGE WHEN ATTACK 1 IS CALLED
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
		iv.setFitHeight(100);
		iv.setFitWidth(100);
		iv.setPreserveRatio(true);
	}
	
	// CHANGING IMAGE WHEN ATTACK 2 IS CALLED
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
	
	// SETTING X POSITION OF SAMURAI
	public static void setX(double xPos)
	{
		Samurai.xPos = xPos;
		iv.setX(Samurai.xPos);
		
		// MOVING HITBOX AND HEALTH BAR WITH PLAYER
		leftHitbox.setX(hitboxX + Samurai.xPos);
		rightHitbox.setX(hitboxX + Samurai.xPos + 100);
		
		redHP.setX(Samurai.xPos - 10);
		greenHP.setX(Samurai.xPos - 10);
	}

	// SETTING Y POSITION OF SAMURAI
	public static void setY(double yPos)
	{
		Samurai.yPos = yPos;
		iv.setY(Samurai.yPos);
		
		// MOVING HITBOX AND HEALTH BAR WITH PLAYER
		leftHitbox.setY(Samurai.yPos + 35);
		rightHitbox.setY(Samurai.yPos + 35);
		
		redHP.setY(Samurai.yPos - 20);
		greenHP.setY(Samurai.yPos - 20);
	}
	
	// RETURNING X POSITION OF SAMRUAIU
	public double getX()
	{
		return xPos;
	}

	// RETURNING Y POSITION OF SAMURAI
	public double getY()
	{
		return yPos;
	}
	
	// RESETTING SAMURAI POSITION
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
