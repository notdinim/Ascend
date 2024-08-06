package application;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public abstract class Levels {

	// FIELDS
	static Stage primaryStage;
	static Scene scene;
	static Pane root;
	
	// ENEMY OBJECTS
	static Boss1 boss1;
	static ArrayList<Boss1> minion1;
	
	static Boss2 boss2;
	static ArrayList<Boss2> minion2;
	
	static Boss3 boss3;
	static ArrayList<Boss3> minion3;
	
	static Boss4 boss4;
	static ArrayList<Boss4> minion4;
	
	Image bg;
	ImageView iv;
	static AnimationTimer plrTimer;
	static AnimationTimer bossTimer;
	static AnimationTimer minionTimer;
	static KeyFrame minionSpawn;
	static Timeline tlMinionSpawn;
	static AnimationTimer mageProjectileTimer;
	
	static KeyFrame jumpTimer;
	static Timeline tlJump;
	static KeyFrame accelTimer;
	static Timeline tlAccel;
	static KeyFrame attackTimer;
	static Timeline tlAttack;
	static KeyFrame attackCooldown;
	static Timeline tlAttackCooldown;
	static KeyFrame projectileCooldown;
	static Timeline tlProjectileCooldown;
	
	static Rectangle plrTop, plrBottom;
	static Rectangle[] platformBottomArray, platformTopArray;
	
	static Rectangle roamInitiate;
	
	static final double gravity = 8;
	static double accFactor = 0.5;
	static boolean left;
	static boolean right;
	static boolean jump;
	static boolean projectileFired;
	static boolean special;
	static boolean topTouched, bottomTouched, bossPlatformTouched;
	static boolean jumpDebounce;
	static double xPos = 600, yPos = 575.5, xPos2 = 600, yPos2 = yPos + 70;
	static boolean gravityActive = true;
	static boolean plrAttacked = false;
	static boolean boolAttackCooldown = false;
	
	// RECTANGLE PLATFORMS
	
	// METHODS
	public abstract Rectangle[] getPlatformTop();
	
	public abstract Rectangle[] getPlatformBottom();
	
}
