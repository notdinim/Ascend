package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

// TOWN CLASS
public class Town {

	// FIELDS
	protected static boolean UP = false, DOWN = false, LEFT = false, RIGHT = false;
	
	// PLAYER VALUES
	protected static String name;
	protected static int coins;
	protected static String character;
	protected static int health;
	protected static int speed, xPos, yPos;
		
	// IMAGES
	private static Image imgBg;
	private static Image samuraiIcon;
	private static Image mageIcon;
	private static Image plrIcon;
	private static Image saveIcon;
	protected static Image imgEndlessNpc, imgLevelNpc, imgShopNpc;
	
	// IMAGEVIEW
	private static ImageView iv;
	private static ImageView ivAlert;
	protected static ImageView ivPlayer;
	protected static ImageView ivNpc;
	
	// LABEL FOR COINS
	protected static Label lbl, lbl2;
	
	// TIMER FOR PLAYER MOVEMENT
	protected static AnimationTimer plrTimer;
	
	// PRIMARY STAGE OBJECT
	protected static Stage primaryStage;
	private static Pane root;
	protected static Scene scene;
	
	// TOWN BOUNDARIES
	protected static Rectangle[] townLeftBounds;
	protected static Rectangle[] townRightBounds;
	protected static Rectangle[] townUpBounds;
	protected static Rectangle[] townDownBounds;
	
	// CHANGE ALL TOWN BOUNDS TO 2D ARRAY
	protected static Rectangle[][] townBounds;
	
	// RECTANGLE BOUNDS
	private static Rectangle leftBound1;
	private static Rectangle leftBound2;
	private static Rectangle leftBound3;
	private static Rectangle leftBound4;
	
	private static Rectangle rightBound1;
	private static Rectangle rightBound2;
	private static Rectangle rightBound3;
	private static Rectangle rightBound4;
	private static Rectangle rightBound5;
	private static Rectangle rightBound6;
	
	private static Rectangle upBound1;
	private static Rectangle upBound2;	
	private static Rectangle upBound3;
	private static Rectangle upBound4;
	
	private static Rectangle downBound1;
	private static Rectangle downBound2;
	private static Rectangle downBound3;
	private static Rectangle downBound4;
	private static Rectangle downBound5;
	
	// NPC RECTANGLE BOUNDS
	protected static Rectangle endlessBound;
	protected static Rectangle levelBound;
	protected static Rectangle shopBound;
	
	// LEVEL BACKGROUNDS
	protected static Image level1, level2, level3, level4;
	
	// FIREBALL IMAGES
	protected static Image fireballImg1, fireballImg2, fireballImg3, fireballImg4, fireballImg5;
	
	// MEDIAPLAYER
	protected static MediaPlayer townMusicPlayer;

	// CONSTRUCTOR
	public Town(Stage primaryStage, ArrayList<String> plrData)
	{			
		// PLAYING BACKGROUND MUSIC
		MenuScreen.menuMusicPlayer.stop();
		File bgMusic = new File("sounds\\Town.mp3");
		Media media = new Media(bgMusic.toURI().toString());
		townMusicPlayer = new MediaPlayer(media);
		townMusicPlayer.setOnEndOfMedia(new Runnable()
		{
			public void run() {
				townMusicPlayer.seek(Duration.ZERO);
			}
		});
		townMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		townMusicPlayer.play();
		
		// INTANTIATING IMAGES
		imgBg = new Image("file:images\\maingame\\townMap.gif");
		samuraiIcon = new Image("file:images\\maingame\\samuraiIcon.png");
		mageIcon = new Image("file:images\\maingame\\mageIcon.png");
		saveIcon = new Image("file:images\\maingame\\saveIcon.png");
		
		// INITIALIZING NPC MAGES
		imgEndlessNpc = new Image("file:images\\maingame\\endlessNpc.png");
		imgLevelNpc = new Image("file:images\\maingame\\levelNpc.png");
		imgShopNpc = new Image("file:images\\maingame\\shopNpc.png");
		
		// INITIALIZES FIREBALL IMAGES
		fireballImg1 = new Image("file:images\\misc\\fireball1.png");
		fireballImg2 = new Image("file:images\\misc\\fireball2.png");
		fireballImg3 = new Image("file:images\\misc\\fireball3.png");
		fireballImg4 = new Image("file:images\\misc\\fireball4.png");
		fireballImg5 = new Image("file:images\\misc\\fireball5.png");
		
		// LEVELS
		level1 = new Image("file:images\\maingame\\level1.png");
		level2 = new Image("file:images\\maingame\\level2.png");
		level3 = new Image("file:images\\maingame\\level3.png");
		level4 = new Image("file:images\\maingame\\level4.png");
		
		// INITIALIZING BACKGROUND IMAGEVIEW
		iv = new ImageView();
		iv.setImage(imgBg);
		
		// INITIALIZING ALERT IMAGEVIEW
		ivAlert = new ImageView();
		ivAlert.setImage(saveIcon);
		ivAlert.setFitHeight(80);
		ivAlert.setPreserveRatio(true);
		
		// INITIALIZING PLAYER IMAGEVIEW
		ivPlayer = new ImageView();
		ivPlayer.setFitHeight(50);
		ivPlayer.setFitWidth(50);
		ivPlayer.setPreserveRatio(true);
		
		// INITIALIZE IMAGEVIEW FOR NPC
		ivNpc = new ImageView();
		ivNpc.setFitHeight(80);
		ivNpc.setPreserveRatio(true);
		
		// INITIALIZING PANE AND SCENE
		root = new Pane();
		scene = new Scene(root, imgBg.getWidth(), imgBg.getHeight());
		
		// INITIALIZING STAGE FOR TOWN
		Town.primaryStage = MenuScreen.primaryStage;
		
		// TOWN BOUNDS
		// CREATING LEFT BOUNDS
		leftBound1 = new Rectangle(295.5, 0, 35, 800);
		leftBound2 = new Rectangle(959.4, 0, 25.2, 200.6);
		leftBound3 = new Rectangle(865.5, 299.7, 25.2, 260.1);
		leftBound4 = new Rectangle(865.5, 683.2, 12.6, 116.8);
		
		// CREATING RIGHT BOUNDS
		rightBound1 = new Rectangle(440.4, 0, 35, 200.6);
		rightBound2 = new Rectangle(424.6, 299.7, 25.2, 260.1);
		rightBound3 = new Rectangle(440.4, 683.2, 9.5, 116.8);
		rightBound4 = new Rectangle(1082.2, 91.4, 25.2, 192.7);
		rightBound5 = new Rectangle(972, 307.8, 12.6, 150);
		rightBound6 = new Rectangle(984.6, 599.4, 25.2, 200.6);
		
		// CREATING UP BOUNDS
		upBound1 = new Rectangle(475.3, 175.1, 484.1, 25.4);
		upBound2 = new Rectangle(449.8, 539.9, 415.7, 19.9);	
		upBound3 = new Rectangle(972, 457.9, 228, 22.9);
		upBound4 = new Rectangle(333.7, 51, 101.4, 25.4);
		
		// CREATING DOWN BOUNDS
		downBound1 = new Rectangle(449.8, 297.9, 415.7, 19.9);
		downBound2 = new Rectangle(440.4, 670.5, 437.8, 12.7);
		downBound3 = new Rectangle(1107.4, 91.4, 92.6, 22.9);
		downBound4 = new Rectangle(972, 284.1, 110.2, 22.9);
		downBound5 = new Rectangle(984.6, 580.6, 215.4, 19.9);
		
		// CREATING NEW RECTANGLES FOR NPC
		endlessBound = new Rectangle(353, 5.3, 60.7, 96.1);
		endlessBound.setVisible(false);
		levelBound = new Rectangle(706.9, 163.3, 64.5, 51.4);
		levelBound.setVisible(false);
		shopBound = new Rectangle(1040.8, 445.1, 40.5, 56);
		shopBound.setVisible(false);
		
		// TRANSFER ALL BOUNDS IN GAME TO 2D ARRAY
		townBounds = new Rectangle[6][4];
		
		// CREATING ARRAY OF BOUND RECTANGLES
		townLeftBounds = new Rectangle[4];
		townRightBounds = new Rectangle[6];
		townUpBounds = new Rectangle[4];
		townDownBounds = new Rectangle[5];

		// LEFT BOUNDS
		townLeftBounds[0] = leftBound1;
		townLeftBounds[1] = leftBound2;
		townLeftBounds[2] = leftBound3;
		townLeftBounds[3] = leftBound4;
		
		// RIGHT BOUNDS
		townRightBounds[0] = rightBound1;
		townRightBounds[1] = rightBound2;
		townRightBounds[2] = rightBound3;
		townRightBounds[3] = rightBound4;
		townRightBounds[4] = rightBound5;
		townRightBounds[5] = rightBound6;
		
		// UP BOUNDS
		townUpBounds[0] = upBound1;
		townUpBounds[1] = upBound2;
		townUpBounds[2] = upBound3;
		townUpBounds[3] = upBound4;
		
		// DOWN BOUNDS
		townDownBounds[0] = downBound1;
		townDownBounds[1] = downBound2;
		townDownBounds[2] = downBound3;
		townDownBounds[3] = downBound4;
		townDownBounds[4] = downBound5;
		
		// ADDINT INDIVIDUAL ARRAYS TO 2D ARRAY
		townBounds[0] = townLeftBounds;
		townBounds[1] = townRightBounds;
		townBounds[2] = townUpBounds;
		townBounds[3] = townDownBounds;

		// GIVING PLAYER THEIR VALUES
		name = plrData.get(0);
		character = plrData.get(2);
		
		// SAMURAI CHARACTER
		if (character.equals("samurai"))
		{
			speed = 7;
			health = 210;
			plrIcon = samuraiIcon;
		}
		// MAGE CHARACTER
		else if (character.equals("mage"))
		{
			speed = 10;
			health = 140;
			plrIcon = mageIcon;
		}
		// INCASE PLAYER DIDN'T PRESS PLAY AND QUIT THE GAME (THEY DON'T HAVE A CLASS) SO DEFAULT TO SAMURAI
		// SET VALUE IN LIST TO DEFAULT (SAMURAI)
		else
		{
			speed = 11;
			health = 210;
			plrIcon = samuraiIcon;
			Main.plrData.remove(2);
			Main.plrData.add(2, "samurai");
		}

		// INITIALIZING DEFAULT VALUES
		xPos = 360;
		yPos = 740;
		ivPlayer.setX(xPos);
		ivPlayer.setY(yPos);
		ivPlayer.setImage(plrIcon);
		coins = Integer.parseInt(plrData.get(3));
		
		// LABEL FOR COINS
		lbl = new Label();
		lbl.setText(Integer.toString(coins));
		lbl.setPrefSize(92.6, 32);
		lbl.setAlignment(Pos.TOP_CENTER);
		lbl.setFont(Main.f);
		lbl.setTextFill(Color.LIGHTGOLDENRODYELLOW);
		lbl.setLayoutX(70.8);
		lbl.setLayoutY(64);
		
		// LABEL FOR PLAYER NAME
		lbl2 = new Label();
		lbl2.setText(name);
		lbl2.setPrefSize(160.1, 32);
		lbl2.setAlignment(Pos.TOP_CENTER);
		lbl2.setFont(Main.f);
		lbl2.setTextFill(Color.LIGHTGOLDENRODYELLOW);
		lbl2.setLayoutX(11.8);
		lbl2.setLayoutY(19);
		
		// ADDING BACKGROUND AND PLAYER TO PANE
		root.getChildren().addAll(iv, ivPlayer, lbl, lbl2, endlessBound, levelBound, shopBound);

		// ADDING BOUNDS TO PANE
		// ADDING LEFT BOUNDS TO SCENE
		for (int i = 0; i < townBounds[0].length; i++)
		{
			if (townBounds[0][i] != null)
			{
				root.getChildren().add(townBounds[0][i]);
				townBounds[0][i].setVisible(false);
			}
		}
		
		// ADDING RIGHT BOUNDS TO SCENE
		for (int i = 0; i < townBounds[1].length; i++)
		{
			if (townBounds[1][i] != null)
			{
				root.getChildren().add(townBounds[1][i]);
				townBounds[1][i].setVisible(false);
			}
		}
		
		// ADDING UP BOUNDS TO SCENE
		for (int i = 0; i < townBounds[2].length; i++)
		{
			if (townBounds[2][i] != null)
			{
				root.getChildren().add(townBounds[2][i]);
				townBounds[2][i].setVisible(false);
			}
		}
		
		// ADDING DOWN BOUNDS TO SCENE
		for (int i = 0; i < townBounds[3].length; i++)
		{
			if (townBounds[3][i] != null)
			{
				root.getChildren().add(townBounds[3][i]);
				townBounds[3][i].setVisible(false);
			}
		}
		
		// START MAIN GAME HANDLERS
		UntitledHandler.mainGame();
		UntitledHandler.townMove();
		plrTimer.start();
		
		// SETTING SCENE TO STAGE AND MAKING IT VISIBLE
		Town.primaryStage.setScene(scene);
		Town.primaryStage.setTitle("TOWN");
		Town.primaryStage.setResizable(false);
		Town.primaryStage.setFullScreen(false);
		Town.primaryStage.centerOnScreen();
		Town.primaryStage.show();
		
		// EVENT HANDLER TO HANDLE "X" CLICKED TO CLOSE WINDOW
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			// METHOD TO HANDLE CLOSE (X)
			public void handle(WindowEvent e)
			{
				// CONFIRMATION ALERT TO CONFIRM USERS CHOISE
				Alert alert = new Alert(AlertType.NONE);
				alert.setContentText("Are you sure you want to exit?\n(Your data will be saved)");
				alert.setTitle("Exit [UNTITLED]");
				alert.setHeaderText(null);
				alert.setGraphic(ivAlert);
				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

				// OPTIONAL LIST TO RECORD ANSWER
				Optional<ButtonType> result = alert.showAndWait();

				// CHECK IF USER PRESSED NO (CHANGED THEIR MIND)
				if (result.get() == ButtonType.NO)
				{
					// CONSUME THE REQUEST AND CLOSE THE ALERT
					e.consume();
				}
				if (result.get() == ButtonType.YES)
				{
					// SAVE PLAYER DATA
					Database.save();
				}
			}
		});
	}
	
	// CONSTRUCTOR FOR LATE GAME THAT GIVES PLAYER REWARD
	public Town(Stage primaryStage, ArrayList<String> plrData, int reward)
	{		
		// PLAYING TOWN BACKGROUND MUSIC
		File bgMusic = new File("sounds\\Town.mp3");
		Media media = new Media(bgMusic.toURI().toString());
		townMusicPlayer = new MediaPlayer(media);
		townMusicPlayer.setOnEndOfMedia(new Runnable()
		{
			public void run() {
				townMusicPlayer.seek(Duration.ZERO);
			}
		});
		townMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		townMusicPlayer.play();
		
		// INTANTIATING IMAGES
		imgBg = new Image("file:images\\maingame\\townMap.gif");
		samuraiIcon = new Image("file:images\\maingame\\samuraiIcon.png");
		mageIcon = new Image("file:images\\maingame\\mageIcon.png");
		saveIcon = new Image("file:images\\maingame\\saveIcon.png");

		imgEndlessNpc = new Image("file:images\\maingame\\endlessNpc.png");
		imgLevelNpc = new Image("file:images\\maingame\\levelNpc.png");
		imgShopNpc = new Image("file:images\\maingame\\shopNpc.png");

		// LEVELS
		level1 = new Image("file:images\\maingame\\level1.png");
		level2 = new Image("file:images\\maingame\\level2.png");
		level3 = new Image("file:images\\maingame\\level3.png");
		level4 = new Image("file:images\\maingame\\level4.png");

		// INITIALIZING IMAGEVIEW FOR BACKGROUND
		iv = new ImageView();
		iv.setImage(imgBg);

		// INITIALIZING ALERT IMAGEVIEW
		ivAlert = new ImageView();
		ivAlert.setImage(saveIcon);
		ivAlert.setFitHeight(80);
		ivAlert.setPreserveRatio(true);

		// INITIALIZING PLAYER IMAGEVIEW
		ivPlayer = new ImageView();
		ivPlayer.setFitHeight(50);
		ivPlayer.setFitWidth(50);
		ivPlayer.setPreserveRatio(true);

		// INITIALIZING NPC IMAGEVIEW
		ivNpc = new ImageView();
		ivNpc.setFitHeight(80);
		ivNpc.setPreserveRatio(true);

		// INITIALIZING PANE AND SCENE
		root = new Pane();
		scene = new Scene(root, imgBg.getWidth(), imgBg.getHeight());

		// INITIALIZING TOWN STAGE
		Town.primaryStage = MenuScreen.primaryStage;

		// CREATING TOWN BOUNDS
		// CREATING RECTANGLE FOR LEFT BOUNDS
		leftBound1 = new Rectangle(295.5, 0, 35, 800);
		leftBound2 = new Rectangle(959.4, 0, 25.2, 200.6);
		leftBound3 = new Rectangle(865.5, 299.7, 25.2, 260.1);
		leftBound4 = new Rectangle(865.5, 683.2, 12.6, 116.8);

		// CREATING RECTANGLE FOR RIGHT BOUNDS
		rightBound1 = new Rectangle(440.4, 0, 35, 200.6);
		rightBound2 = new Rectangle(424.6, 299.7, 25.2, 260.1);
		rightBound3 = new Rectangle(440.4, 683.2, 9.5, 116.8);
		rightBound4 = new Rectangle(1082.2, 91.4, 25.2, 192.7);
		rightBound5 = new Rectangle(972, 307.8, 12.6, 150);
		rightBound6 = new Rectangle(984.6, 599.4, 25.2, 200.6);

		// CREATING RECTANGLE FOR UP BOUNDS
		upBound1 = new Rectangle(475.3, 175.1, 484.1, 25.4);
		upBound2 = new Rectangle(449.8, 539.9, 415.7, 19.9);	
		upBound3 = new Rectangle(972, 457.9, 228, 22.9);
		upBound4 = new Rectangle(333.7, 51, 101.4, 25.4);

		// CREATING RECTANGLE FOR DOWN BOUNDS
		downBound1 = new Rectangle(449.8, 297.9, 415.7, 19.9);
		downBound2 = new Rectangle(440.4, 670.5, 437.8, 12.7);
		downBound3 = new Rectangle(1107.4, 91.4, 92.6, 22.9);
		downBound4 = new Rectangle(972, 284.1, 110.2, 22.9);
		downBound5 = new Rectangle(984.6, 580.6, 215.4, 19.9);

		// CREATING NPC BOUNDS
		endlessBound = new Rectangle(353, 5.3, 60.7, 96.1);
		endlessBound.setVisible(false);
		levelBound = new Rectangle(706.9, 163.3, 64.5, 51.4);
		levelBound.setVisible(false);
		shopBound = new Rectangle(1040.8, 445.1, 40.5, 56);
		shopBound.setVisible(false);

		// TRANSFER ALL BOUNDS IN GAME TO 2D ARRAY
		townBounds = new Rectangle[6][4];

		// CREATING ARRAYS OF BOUNDS
		townLeftBounds = new Rectangle[4];
		townRightBounds = new Rectangle[6];
		townUpBounds = new Rectangle[4];
		townDownBounds = new Rectangle[5];

		// LEFT BOUNDS
		townLeftBounds[0] = leftBound1;
		townLeftBounds[1] = leftBound2;
		townLeftBounds[2] = leftBound3;
		townLeftBounds[3] = leftBound4;

		// RIGHT BOUNDS
		townRightBounds[0] = rightBound1;
		townRightBounds[1] = rightBound2;
		townRightBounds[2] = rightBound3;
		townRightBounds[3] = rightBound4;
		townRightBounds[4] = rightBound5;
		townRightBounds[5] = rightBound6;

		// UP BOUNDS
		townUpBounds[0] = upBound1;
		townUpBounds[1] = upBound2;
		townUpBounds[2] = upBound3;
		townUpBounds[3] = upBound4;

		// DOWN BOUNDS
		townDownBounds[0] = downBound1;
		townDownBounds[1] = downBound2;
		townDownBounds[2] = downBound3;
		townDownBounds[3] = downBound4;
		townDownBounds[4] = downBound5;

		// ADDINT INDIVIDUAL ARRAYS TO 2D ARRAY
		townBounds[0] = townLeftBounds;
		townBounds[1] = townRightBounds;
		townBounds[2] = townUpBounds;
		townBounds[3] = townDownBounds;

		// GIVING PLAYER THEIR VALUES
		name = plrData.get(0);
		character = plrData.get(2);

		// SAMURAI CHARACTER
		if (character.equals("samurai"))
		{
			speed = 7;
			health = 210;
			plrIcon = samuraiIcon;
		}
		// MAGE CHARACTER
		else if (character.equals("mage"))
		{
			speed = 10;
			health = 140;
			plrIcon = mageIcon;
		}
		// INCASE PLAYER DIDN'T PRESS PLAY AND QUIT THE GAME (THEY DON'T HAVE A CLASS) SO DEFAULT TO SAMURAI
		// SET VALUE IN LIST TO DEFAULT (SAMURAI)
		else
		{
			speed = 7;
			health = 210;
			plrIcon = samuraiIcon;
			Main.plrData.remove(2);
			Main.plrData.add(2, "samurai");
		}

		// SETTING PLAYER ICON
		ivPlayer.setImage(plrIcon);
		
		// GETTING COINS FROM PLAYER DATA AND ADDING REWARD
		coins = Integer.parseInt(plrData.get(3));
		coins += reward;

		// ADDING COINS TO PLAYER DATA
		Main.plrData.remove(3);
		Main.plrData.add(3, Integer.toString(coins));

		// LABEL FOR COINS
		lbl = new Label();
		lbl.setText(Integer.toString(coins));
		lbl.setPrefSize(92.6, 32);
		lbl.setAlignment(Pos.TOP_CENTER);
		lbl.setFont(Main.f);
		lbl.setTextFill(Color.LIGHTGOLDENRODYELLOW);
		lbl.setLayoutX(70.8);
		lbl.setLayoutY(64);

		// LABEL FOR PLAYER NAME
		lbl2 = new Label();
		lbl2.setText(name);
		lbl2.setPrefSize(160.1, 32);
		lbl2.setAlignment(Pos.TOP_CENTER);
		lbl2.setFont(Main.f);
		lbl2.setTextFill(Color.LIGHTGOLDENRODYELLOW);
		lbl2.setLayoutX(11.8);
		lbl2.setLayoutY(19);

		// ADDING BACKGROUND AND PLAYER TO PANE
		root.getChildren().addAll(iv, ivPlayer, lbl, lbl2, endlessBound, levelBound, shopBound);

		// ADDING BOUNDS TO PANE
		// ADDING LEFT BOUNDS TO SCENE
		for (int i = 0; i < townBounds[0].length; i++)
		{
			if (townBounds[0][i] != null)
			{
				root.getChildren().add(townBounds[0][i]);
				townBounds[0][i].setVisible(false);
			}
		}

		// ADDING RIGHT BOUNDS TO SCENE
		for (int i = 0; i < townBounds[1].length; i++)
		{
			if (townBounds[1][i] != null)
			{
				root.getChildren().add(townBounds[1][i]);
				townBounds[1][i].setVisible(false);
			}
		}

		// ADDING UP BOUNDS TO SCENE
		for (int i = 0; i < townBounds[2].length; i++)
		{
			if (townBounds[2][i] != null)
			{
				root.getChildren().add(townBounds[2][i]);
				townBounds[2][i].setVisible(false);
			}
		}

		// ADDING DOWN BOUNDS TO SCENE
		for (int i = 0; i < townBounds[3].length; i++)
		{
			if (townBounds[3][i] != null)
			{
				root.getChildren().add(townBounds[3][i]);
				townBounds[3][i].setVisible(false);
			}
		}

		// SETTING SCENE TO STAGE AND MAKING IT VISIBLE
		Town.primaryStage.setScene(scene);
		Town.primaryStage.setTitle("TOWN");
		Town.primaryStage.setResizable(false);
		Town.primaryStage.setFullScreen(false);
		Town.primaryStage.centerOnScreen();
		Town.primaryStage.show();
	}
	
	//METHODS
	// SETTING XPOS OF PLAYER
	public static void setX()
	{
		ivPlayer.setX(xPos);
	}
	
	// SETTING Y POS OF PLAYER
	public static void setY()
	{
		ivPlayer.setY(yPos);
	}
	
	// MAKE METHOD TO HOLD ALL BOUNDS IN TOWN AND RETURN THEM IN ARRAY
	// CHECKING IF PLAYER IS TOUCHING LEFT BOUNDS
	public static boolean getLeftBounds()
	{		
		for (int i = 0; i < townBounds[0].length; i++)
		{
			if (ivPlayer.getBoundsInParent().intersects(townBounds[0][i].getBoundsInParent()) || xPos <= 1)
			{
				return false;
			}
		}
		return true;
	}
	
	// CHECKING IF PLAYER IS TOUCHING RIGHT BOUNDS
	public static boolean getRightBounds()
	{
		for (int i = 0; i < townBounds[1].length; i++)
		{
			if (ivPlayer.getBoundsInParent().intersects(townBounds[1][i].getBoundsInParent()) || xPos + ivPlayer.getFitWidth() >= scene.getWidth())
			{
				return false;
			}
		}
		return true;		
	}
	
	// CHECKING IF PLAYER IS TOUCHING UP BOUNDS
	public static boolean getUpBounds()
	{
		for (int i = 0; i < townBounds[2].length; i++)
		{
			if (ivPlayer.getBoundsInParent().intersects(townBounds[2][i].getBoundsInParent()) || yPos <= 1)
			{
				return false;
			}
		}
		return true;
	}
	
	// CHECKING IF PLAYER IS TOUCHING DOWN BOUNDS
	public static boolean getDownBounds()
	{
		for (int i = 0; i < townBounds[3].length; i++)
		{
			if (ivPlayer.getBoundsInParent().intersects(townBounds[3][i].getBoundsInParent()) || yPos + ivPlayer.getFitHeight() >= scene.getHeight())
			{
				return false;
			}
		}
		return true;
	}
	
	// CHECKING IF PLAYER IS TOUCHING ENDLESS RUN NPC
	public static boolean getEndlessBounds()
	{
		if (ivPlayer.getBoundsInParent().intersects(endlessBound.getBoundsInParent()))
		{
			return false;
		}
		return true;
	}
	
	// CHECKING IF PLAYER IS TOUCHING LEVELS NPC (QUESTION MARK)
	public static boolean getLevelBounds()
	{
		if (ivPlayer.getBoundsInParent().intersects(levelBound.getBoundsInParent()))
		{
			return false;
		}
		return true;
	}
	
	// CHECKING IF PLAYER IS TOUCHING SHOP NPC (OLD MAN)
	public static boolean getShopBounds()
	{
		if (ivPlayer.getBoundsInParent().intersects(shopBound.getBoundsInParent()))
		{
			return false;
		}
		return true;
	}
}
