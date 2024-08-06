package application;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

// GAME HANDLER CLASS
public class UntitledHandler extends Main{

	// FIELDS
	private Pane root;
	private static Scene scene;
	private Stage primaryStage;
	
	// BOOL HANDLERS
	protected static boolean boolMenuScreen = true;
	protected static boolean boolTownScreen = false;
	protected static boolean boolLevelScreen = false;
	
	protected static boolean firstPlay = true;
	
	// MENU SCREEN CLASS
	private MenuScreen ms;

	// TOWN OBJECT
	private static Town town;
	
	// ABSTRACT LEVELS OBJECT
	protected static Levels level;
	protected static String levelChoice;

	// INJITIATE PLAYER OBJECTS
	protected static Samurai samurai;
	protected static Mage mage;
	
	// PLAYER PROJECTILE
	protected static ArrayList<Projectile> projectile;
	protected static double mouseX, mouseY, velocityX, velocityY;
	
	// SOUND EFFECTS
	protected static AudioClip attackSlash = new AudioClip("file:sounds/attackSlash.mp3");
	protected static AudioClip bossDead = new AudioClip("file:sounds/bossDead.mp3");
	protected static AudioClip playerDead = new AudioClip("file:sounds/playerDead.mp3");
	protected static AudioClip projectileShot = new AudioClip("file:sounds/projectile.mp3");
	protected static AudioClip minionExplode = new AudioClip("file:sounds/minionExplode.mp3");
	
	// METHODS
	// INITIALIZE HANDLER
	public void init(Stage primaryStage, Scene scene)
	{
		// INITIALIZING MENU SCREEN OBJECT
		ms = new MenuScreen();
		
		// INITIALIZING STAGE OBJECT
		this.primaryStage = primaryStage;
		root = new Pane();
		this.scene = scene;
	
		// PRIMARY STAGE
		primaryStage.setResizable(false);
		primaryStage.setFullScreen(false);
		primaryStage.centerOnScreen();
	}
	
	// METHOD TO CHECK IF MENU SCREEN IS INTERACTED WITH
	public void menuInteract()
	{
		UntitledHandler.scene.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent e) {

				if (UntitledHandler.boolMenuScreen)
				{
					// CHECKING FOR PLAY BUTTON CLICKED
					if (e.getX() >= 551.9 && e.getX() < 511.9 + 258.5)
					{
						if (e.getY() > 123.8 && e.getY() < 123.8 + 63.7)
						{
							// INITIATING MAIN GAME
							ms.play();
						}
					}

					// CHECKING FOR TUTORIAL BUTTON CLICKED
					if (e.getX() >= 551.9 && e.getX() < 511.9 + 258.5)
					{
						if (e.getY() > 187.6 && e.getY() < 187.6 + 63.7)
						{
							// PROMPTING TUTORIAL ALERT
							ms.tutorial();
						}
					}

					// CHECKING FOR CREDITS BUTTON CLICKED
					if (e.getX() >= 551.9 && e.getX() < 511.9 + 258.5)
					{
						if (e.getY() > 251.2 && e.getY() < 251.2 + 63.7)
						{
							// PROMPTING CREDITS ALERT
							ms.credits();
						}
					}

					// CHECKING FOR QUIT BUTTON CLICKED
					if (e.getX() >= 551.9 && e.getX() < 511.9 + 258.5)
					{
						if (e.getY() > 314.9 && e.getY() < 314.9 + 63.7)
						{
							// EXITING THE GAME
							ms.exit();
						}
					}
				}
			}
		});
	}

	// MAIN GAME METHOD HANDLER
	public static void mainGame()
	{
		// INITIATING SAMURAI OBJECT IF PLAYERS CLASS IS SAMURAI
		if (Town.character.equals("samurai"))
		{
			samurai = new Samurai(Town.scene.getWidth(), Town.scene.getHeight());
		}
		
		// INITIATING MAGE OBJECT IF PLAYERS CLASS IS MAGE
		else if (Town.character.equals("mage"))
		{
			mage = new Mage(Town.scene.getWidth(), Town.scene.getHeight());
		}
		
		// CHECKING FOR MOVEMENT IN TOWN AREA
		if (UntitledHandler.boolTownScreen)
		{
			// KEY PRESSED
			Town.scene.setOnKeyPressed(new EventHandler<KeyEvent>()
			{
				public void handle(KeyEvent e) {

					// WASD TURNING BOOL HANDLERS TO TRUE
					if (e.getCode() == KeyCode.W)
					{
						Town.UP = true;
					}
					if (e.getCode() == KeyCode.S)
					{
						Town.DOWN = true;
					}
					if (e.getCode() == KeyCode.A)
					{
						Town.LEFT = true;
					}
					if (e.getCode() == KeyCode.D)
					{
						Town.RIGHT = true;
					}		
				}
			});

			// KEY RELEASED
			Town.scene.setOnKeyReleased(new EventHandler<KeyEvent>()
			{
				public void handle(KeyEvent e) {

					// WASD TURNING BOOL HANDLERS TO FALSE
					if (UntitledHandler.boolTownScreen)
					{
						if (e.getCode() == KeyCode.W)
						{
							Town.UP = false;
						}
						if (e.getCode() == KeyCode.S)
						{
							Town.DOWN = false;
						}
						if (e.getCode() == KeyCode.A)
						{
							Town.LEFT = false;
						}
						if (e.getCode() == KeyCode.D)
						{
							Town.RIGHT = false;
						}		
					}
				}
			});

			// MOUSE CLICKED EVENT FOR DHECKING NPC COLLISION AND CLICKED
			Town.scene.setOnMouseClicked(new EventHandler<MouseEvent>()
			{
				public void handle(MouseEvent e) {

					// CHECKING FOR ENDLESS RUN NPC CLICKED - NOT USED
					if (!Town.getEndlessBounds() && e.getX() > Town.endlessBound.getX() && e.getX() < Town.endlessBound.getX() + Town.endlessBound.getWidth())
					{
						if (!Town.getEndlessBounds() && e.getY() > Town.endlessBound.getY() && e.getY() < Town.endlessBound.getY() + Town.endlessBound.getHeight())
						{
							Alert alert = new Alert(AlertType.NONE);
							//alert.setContentText("Would You Like To Enter The Endless Run Mode?\n(Disclaimer: You Don't Get Coins From This Mode)");
							alert.setContentText("Endless Run Mode Coming Soon!");
							alert.setTitle("Malachi");
							alert.setHeaderText(null);
							Town.ivNpc.setImage(Town.imgEndlessNpc);
							alert.setGraphic(Town.ivNpc);
							alert.getButtonTypes().clear();
							alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

							Optional<ButtonType> result = alert.showAndWait();

							if (result.isPresent())
							{
								if (result.get() == ButtonType.OK)
								{
									// OK CLOSED ALERT. ENLESS RUN MODE COMING SOON!
								}
							}
						}
					}

					// CHECKING FOR QUESTION MARK CLICKED - LEVEL NPC
					if (!Town.getLevelBounds() && e.getX() > Town.levelBound.getX() && e.getX() < Town.levelBound.getX() + Town.levelBound.getWidth())
					{
						if (!Town.getLevelBounds() && e.getY() > Town.levelBound.getY() && e.getY() < Town.levelBound.getY() + Town.levelBound.getHeight())
						{	
							// ARRAYLIST FOR CHOICE DIALOG OF LEVEL CHOICES
							ArrayList<String> levels = new ArrayList<String>();
							// ADDING LEVELS TO LIST
							levels.add("1 - Beginner");
							levels.add("2 - Easy");
							levels.add("3 - Medium");
							levels.add("4 - Hard");

							// CHOICE DIALOG FOR LEVEL
							ChoiceDialog<String> dialog = new ChoiceDialog<String>(levels.get(0), levels);
							dialog.setTitle("Level Select");
							dialog.setHeaderText("Welcome To The Gateway\nYou Will Fight Monsters And Collect Coins For Beating Them\nGoodluck Stranger");
							dialog.setContentText("Reward:\n25 Coins * Level Multiplier");
							Town.ivNpc.setImage(Town.imgLevelNpc);
							dialog.setGraphic(Town.ivNpc);

							// SHOWING CHOICE DIALOG
							Optional<String> result = dialog.showAndWait();

							// CHECKING IF RESULT IS PRESENT
							if (result.isPresent())
							{		
								// RESSETTING LOCATION OF PLAYER
								// SAMURAI CHARACTER
								if (Town.character.equals("samurai"))
								{
									samurai = new Samurai(Town.scene.getWidth(), Town.scene.getHeight());
									Samurai.resetPos();
								}
								
								// MAGE CHARACTER
								else if (Town.character.equals("mage"))
								{
									mage = new Mage(Town.scene.getWidth(), Town.scene.getHeight());
									Mage.resetPos();
								}
								
								// GETTING INPUT
								String input = result.get();

								// CHECKING FOR WHICH LEVEL WAS SELECTED
								// PROMPTING THE SPECIFIED LEVEL AND CREATING A NEW OBJECT OF IT
								// LEVEL 1
								if (input.equals("1 - Beginner"))
								{
									levelChoice = "1 - Beginner";
									UntitledHandler.boolTownScreen = false;
									UntitledHandler.boolLevelScreen = true;
									Town.plrTimer.stop();
									level = new Level1(Town.primaryStage, Town.level1); 
								}
								// LEVEL 2
								else if (input.equals("2 - Easy"))
								{
									levelChoice = "2 - Easy";
									UntitledHandler.boolTownScreen = false;
									UntitledHandler.boolLevelScreen = true;
									Town.plrTimer.stop();
									level = new Level2(Town.primaryStage, Town.level2); 
								}	
								// LEVEL 3
								else if (input.equals("3 - Medium"))
								{
									levelChoice = "3 - Medium";
									UntitledHandler.boolTownScreen = false;
									UntitledHandler.boolLevelScreen = true;
									Town.plrTimer.stop();
									level = new Level3(Town.primaryStage, Town.level3); 
								}
								// LEVEL 4
								else if (input.equals("4 - Hard"))
								{
									levelChoice = "4 - Hard";
									UntitledHandler.boolTownScreen = false;
									UntitledHandler.boolLevelScreen = true;
									Town.plrTimer.stop();
									level = new Level4(Town.primaryStage, Town.level4); 
									
								}
								// SETTING TITLE OF STAGE
								Town.primaryStage.setTitle(levelChoice);
							}
						}
					}

					// CHECKING FOR SHOP NPC CLICKED
					if (!Town.getShopBounds() && e.getX() > Town.shopBound.getX() && e.getX() < Town.shopBound.getX() + Town.shopBound.getWidth())
					{
						if (!Town.getShopBounds() && e.getY() > Town.shopBound.getY() && e.getY() < Town.shopBound.getY() + Town.shopBound.getHeight())
						{
							// CHECKING PLAYER'S CURRENT PROJECTILE AND PROMPTING BUY FOR NEXT UPGRADE
							if (Main.plrData.get(4) == null || Main.plrData.get(4).equals("null"))
							{
								// ALERT
								Alert alert = new Alert(AlertType.NONE);
								alert.setContentText("Current Projectile: NONE\nNext Upgrade: Fireball 1\nCost: 75 Coins");
								alert.setTitle("SHOP");
								alert.setHeaderText(null);
								Town.ivNpc.setImage(Town.fireballImg1);
								alert.setGraphic(Town.ivNpc);
								alert.getButtonTypes().clear();
								alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

								// SHOWING ALERT
								Optional<ButtonType> result = alert.showAndWait();

								// CHECKING IF RESULT IS PRESENT
								if (result.isPresent())
								{
									if (result.get() == ButtonType.OK)
									{
										if (Town.coins >= 75)
										{
											// BUYING PROJECTILE AND STORING PLAYER VALUES TO PLR DATA ARRAY
											Town.coins -= 75;
											Town.lbl.setText(Integer.toString(Town.coins));
											
											// CHANGING COINS IN PLAYER DATA AND SETTING PROJECTILE IN DATABSASE TO FIREBALL BOUGHT
											Main.plrData.remove(3);
											Main.plrData.add(3, Integer.toString(Town.coins));
											
											Main.plrData.remove(4);
											Main.plrData.add(4, "fireball1");
											
											alert.setContentText("Fireball 1 Purchased!");
											alert.showAndWait();
										}
										else
										{	// NOT ENOUGH COINS TO PURCHASE FIREBALL
											alert.setContentText("Not Enough Coins");
											alert.showAndWait();
										}
									}
								}
							}
							
							// FIREBALL 1
							else if (Main.plrData.get(4).equals("fireball1"))
							{
								// ALERT
								Alert alert = new Alert(AlertType.NONE);
								alert.setContentText("Current Projectile: Fireball 1\nNext Upgrade: Fireball 2\nCost: 150 Coins");
								alert.setTitle("SHOP");
								alert.setHeaderText(null);
								Town.ivNpc.setImage(Town.fireballImg2);
								alert.setGraphic(Town.ivNpc);
								alert.getButtonTypes().clear();
								alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

								// CHECKING IF RESULT IS PRESENT
								Optional<ButtonType> result = alert.showAndWait();

								if (result.isPresent())
								{
									if (result.get() == ButtonType.OK)
									{
										if (Town.coins >= 150)
										{
											// BUYING PROJECTILE AND STORING PLAYER VALUES TO PLR DATA ARRAY
											Town.coins -= 150;
											Town.lbl.setText(Integer.toString(Town.coins));
											
											// CHANGING COINS IN PLAYER DATA AND SETTING PROJECTILE IN DATABSASE TO FIREBALL BOUGHT
											Main.plrData.remove(3);
											Main.plrData.add(3, Integer.toString(Town.coins));
											
											Main.plrData.remove(4);
											Main.plrData.add(4, "fireball2");
											
											alert.setContentText("Fireball 2 Purchased!");
											alert.showAndWait();
										}
										else
										{
											// NOT ENOUGH COINS
											alert.setContentText("Not Enough Coins");
											alert.showAndWait();
										}
									}
								}
							}
							else if (Main.plrData.get(4).equals("fireball2"))
							{
								// ALERT
								Alert alert = new Alert(AlertType.NONE);
								alert.setContentText("Current Projectile: Fireball 2\nNext Upgrade: Fireball 3\nCost: 225 Coins");
								alert.setTitle("SHOP");
								alert.setHeaderText(null);
								Town.ivNpc.setImage(Town.fireballImg3);
								alert.setGraphic(Town.ivNpc);
								alert.getButtonTypes().clear();
								alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

								// SHOWING ALERT
								Optional<ButtonType> result = alert.showAndWait();

								// CHECKING IF RESULT IS PRESENT
								if (result.isPresent())
								{
									if (result.get() == ButtonType.OK)
									{
										if (Town.coins >= 225)
										{
											// BUYING PROJECTILE AND STORING PLAYER VALUES TO PLR DATA ARRAY
											Town.coins -= 225;
											Town.lbl.setText(Integer.toString(Town.coins));
											
											// CHANGING COINS IN PLAYER DATA AND SETTING PROJECTILE IN DATABSASE TO FIREBALL BOUGHT
											Main.plrData.remove(3);
											Main.plrData.add(3, Integer.toString(Town.coins));
											
											Main.plrData.remove(4);
											Main.plrData.add(4, "fireball3");
											
											alert.setContentText("Fireball 3 Purchased!");
											alert.showAndWait();
										}
										else
										{
											// NOT ENOUH COINS TO PURCHASE
											alert.setContentText("Not Enough Coins");
											alert.showAndWait();
										}
									}
								}
							}
							
							// FIREBALL 3
							else if (Main.plrData.get(4).equals("fireball3"))
							{
								// ALERT
								Alert alert = new Alert(AlertType.NONE);
								alert.setContentText("Current Projectile: Fireball 3\nNext Upgrade: Fireball 4\nCost: 300 Coins");
								alert.setTitle("SHOP");
								alert.setHeaderText(null);
								Town.ivNpc.setImage(Town.fireballImg4);
								alert.setGraphic(Town.ivNpc);
								alert.getButtonTypes().clear();
								alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

								// SHOWING ALERT
								Optional<ButtonType> result = alert.showAndWait();

								if (result.isPresent())
								{
									if (result.get() == ButtonType.OK)
									{
										if (Town.coins >= 300)
										{
											// BUYING PROJECTILE AND STORING PLAYER VALUES TO PLR DATA ARRAY
											Town.coins -= 300;
											Town.lbl.setText(Integer.toString(Town.coins));
											
											// CHANGING COINS IN PLAYER DATA AND SETTING PROJECTILE IN DATABSASE TO FIREBALL BOUGHT
											Main.plrData.remove(3);
											Main.plrData.add(3, Integer.toString(Town.coins));
											
											Main.plrData.remove(4);
											Main.plrData.add(4, "fireball4");
											
											alert.setContentText("Fireball 4 Purchased!");
											alert.showAndWait();
										}
										else
										{
											alert.setContentText("Not Enough Coins");
											alert.showAndWait();
										}
									}
								}
							}
							else if (Main.plrData.get(4).equals("fireball4"))
							{
								Alert alert = new Alert(AlertType.NONE);
								alert.setContentText("Current Projectile: Fireball 4\nNext Upgrade: Fireball 5\nCost: 450 Coins");
								alert.setTitle("SHOP");
								alert.setHeaderText(null);
								Town.ivNpc.setImage(Town.fireballImg5);
								alert.setGraphic(Town.ivNpc);
								alert.getButtonTypes().clear();
								alert.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

								Optional<ButtonType> result = alert.showAndWait();

								// CHECKING IF RESULT IS PRESENT
								if (result.isPresent())
								{
									if (result.get() == ButtonType.OK)
									{
										if (Town.coins >= 450)
										{
											// BUYING PROJECTILE AND STORING PLAYER VALUES TO PLR DATA ARRAY
											Town.coins -= 450;
											Town.lbl.setText(Integer.toString(Town.coins));
											
											// CHANGING COINS IN PLAYER DATA AND SETTING PROJECTILE IN DATABSASE TO FIREBALL BOUGHT
											Main.plrData.remove(3);
											Main.plrData.add(3, Integer.toString(Town.coins));
											
											Main.plrData.remove(4);
											Main.plrData.add(4, "fireball5");
											
											alert.setContentText("Fireball 5 Purchased!");
											alert.showAndWait();
										}
										else
										{
											alert.setContentText("Not Enough Coins");
											alert.showAndWait();
										}
									}
								}
							}
							else if (Main.plrData.get(4).equals("fireball5"))
							{
								// FINAL UPGRADE
								// SHOWING ALERT FOR NO MORE UPGRADES AVAILABLE
								Alert alert = new Alert(AlertType.NONE);
								alert.setContentText("Current Projectile: Fireball 5\nNext Upgrade: NONE");
								alert.setTitle("SHOP");
								alert.setHeaderText(null);
								Town.ivNpc.setImage(Town.fireballImg5);
								alert.setGraphic(Town.ivNpc);
								alert.getButtonTypes().clear();
								alert.getButtonTypes().addAll(ButtonType.OK);
								alert.showAndWait();
							}	
						}
					}
				}
			}
					);
		}
	}
	
	// TOWN MOVEMENT HANDLER
	public static void townMove()
	{
		// ANIMATION TIMER TO HANDLE PLAYER MOVEMENT
		Town.plrTimer = new AnimationTimer()
		{
			public void handle(long e)
			{
				if (Town.UP)
				{
					// MOVING PLAYER UP AND ROTATING
					Town.ivPlayer.setRotate(180);
					if (Town.getUpBounds())
					{
						Town.yPos -= Town.speed;
					}
				}
				if (Town.DOWN)
				{
					// MOVING PLAYER DOWN AND ROTATING
					Town.ivPlayer.setRotate(0);
					if (Town.getDownBounds())
					{
						Town.yPos += Town.speed;
					}
				}
				if (Town.LEFT)
				{
					// MOVING PLAYER LEFT AND ROTATING
					Town.ivPlayer.setRotate(90);
					if (Town.getLeftBounds())
					{
						Town.xPos -= Town.speed;
					}
				}
				if (Town.RIGHT)
				{
					// MOVING PLAYER RIGHT AND ROTATING
					Town.ivPlayer.setRotate(270);
					if (Town.getRightBounds())
					{
						Town.xPos += Town.speed;
					}
				}
				// SETTING TOWN X AND Y TO UPDATED POSITIONS
				Town.setX();
				Town.setY();
			}
		};
	}
	
	// METHOD TO HANDLE LEVELS 
	public static void levelHandler()
	{	
		// INITIALIZE PLAYER PROJECTILE
		if (Main.plrData.get(4) != null)
		{
			projectile = new ArrayList<Projectile>();
		}
		
		// CHECKING IF BOOL LEVEL HANDLER IS TRUE
		if (UntitledHandler.boolLevelScreen)
		{
			// KEY PRESSED FOR PLAYER MOVEMENT
			Levels.scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e) {
					
					// CHECKING IF PLAYER CHARACTER IS SAMURAI
					if (Town.character.equals("samurai"))
					{
						// LEFT MOVEMENT
						if (e.getCode() == KeyCode.A)
						{
							Levels.left = true;
							Samurai.right = false;
							Samurai.left = true;
						}
						// RIGHT MOVEMENT
						if (e.getCode() == KeyCode.D)
						{
							Levels.right = true;
							Samurai.left = false;
							Samurai.right = true;
						}
						// JUMP
						if (e.getCode() == KeyCode.SPACE)
						{
							if (!Levels.jumpDebounce)
							{
								Levels.jump = true;
								Levels.gravityActive = false;
								Levels.tlJump.setCycleCount(Timeline.INDEFINITE);
								Levels.tlJump.play();
								Levels.jumpDebounce = true;
							}
						}
						// PROJECTILE
						if (Main.plrData.get(4) != null)
						{
							if (e.getCode() == KeyCode.E)
							{
								if (!Levels.projectileFired)
								{
									Levels.projectileFired = true;
									UntitledHandler.projectileShot.play();
									
									// MAKING NEW PROJECTILE
									projectile.add(new Projectile());
									
									// E KEY PROJECTILE
									Levels.root.getChildren().add(projectile.get(projectile.size() - 1).getNode());
									projectile.get(projectile.size() - 1).setPosition(samurai.getX(), samurai.getY());

									double startX = projectile.get(projectile.size() - 1).getX();
									double startY = projectile.get(projectile.size() - 1).getY();

									double angle = Math.atan2(mouseY - startY, mouseX - startX);

									velocityX = 30 * Math.cos(angle);
									velocityY = 30 * Math.sin(angle);
									
									Levels.tlProjectileCooldown.playFromStart();
								}
							}
						}
					}
					
					// CHECKING IF PLAYER'S CHARACTER IS MAGE
					else if (Town.character.equals("mage"))
					{
						// LEFT MOVEMENT
						if (e.getCode() == KeyCode.A)
						{
							Levels.left = true;
							Mage.right = false;
							Mage.left = true;
						}
						// RIGHT MOVEMENT
						if (e.getCode() == KeyCode.D)
						{
							Levels.right = true;
							Mage.left = false;
							Mage.right = true;
						}
						// JUMP
						if (e.getCode() == KeyCode.SPACE)
						{
							if (!Levels.jumpDebounce)
							{
								Levels.jump = true;
								Levels.gravityActive = false;
								Levels.tlJump.setCycleCount(Timeline.INDEFINITE);
								Levels.tlJump.play();
								Levels.jumpDebounce = true;
							}
						}
						// PROJECTILE
						if (Main.plrData.get(4) != null)
						{
							if (e.getCode() == KeyCode.E)
							{
								if (!Levels.projectileFired)
								{
									Levels.projectileFired = true;
									UntitledHandler.projectileShot.play();
									
									// MAKING NEW PROJECTILE
									projectile.add(new Projectile());

									// E KEY PROJECTILE
									Levels.root.getChildren().add(projectile.get(projectile.size() - 1).getNode());
									projectile.get(projectile.size() - 1).setPosition(mage.getX(), mage.getY());

									double startX = projectile.get(projectile.size() - 1).getX();
									double startY = projectile.get(projectile.size() - 1).getY();

									double angle = Math.atan2(mouseY - startY, mouseX - startX);

									velocityX = 30 * Math.cos(angle);
									velocityY = 30 * Math.sin(angle);
									
									Levels.tlProjectileCooldown.playFromStart();
								}
							}
						}
						// SPECIAL MOVE - NOT ADDED
						if (e.getCode() == KeyCode.F)
						{
							Levels.special = true;
						}
					}
				}
			});
			
			// KEY RELEASED TO TURN MOVEMENT HANDLERS TO FALSE
			Levels.scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
				public void handle(KeyEvent e) {

					// LEFT MOVEMENT
					if (e.getCode() == KeyCode.A)
					{
						Levels.left = false;
					}
					// RIGHT MOVEMENT
					if (e.getCode() == KeyCode.D)
					{
						Levels.right = false;
					}
				}
			});
		
			// SCENE ON MOVED EVENT TO KEEP TRACK OF MOUSE POSITION
			Levels.scene.setOnMouseMoved(new EventHandler<MouseEvent>()
			{
				public void handle(MouseEvent e) {
					
					// GETTING MOUSE CURSOR COORDINATES
					mouseX = e.getX();
					mouseY = e.getY();
				}
			});
			
			// SCENE MOUSE CLICKED EVENT TO HANDLE ATTACKS
			Levels.scene.setOnMousePressed(new EventHandler<MouseEvent>()
			{
				public void handle(MouseEvent e) {

					// CHECKING FOR LEFT MOUSE CLICKED
					if (e.getButton() == MouseButton.PRIMARY)
					{
						if (!Levels.boolAttackCooldown)
						{
							// LEVEL 1
							if (levelChoice.equals("1 - Beginner"))
							{
								// PLAYING ATTACK ANIM
								UntitledHandler.attackSlash.play();
								if (Town.character.equals("samurai"))
								{
									// SETTING HANDLERS TO TRUE
									Level1.plrAttacked = true;
									Level1.boolAttackCooldown = true;
									samurai.attack1();
									Level1.tlAttack.play();
									Level1.tlAttackCooldown.playFromStart();

									for (int i = 0; i < Levels.minion1.size(); i ++)
									{
										if (!Levels.minion1.get(i).detonate)
										{
											// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
											if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.minion1.get(i).getNode().getBoundsInParent()) && Samurai.left||
													Samurai.rightHitbox.getBoundsInParent().intersects(Levels.minion1.get(i).getNode().getBoundsInParent()) && Samurai.right)
											{
												Levels.minion1.get(i).isDead = true;

												Levels.minion1.get(i).setX(40000);
												Levels.minion1.get(i).setY(40000);
												Levels.root.getChildren().remove(Levels.minion1.get(i).getNode());
												Levels.minion1.remove(i);
											}
										}
									}		
									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.boss1.getNode().getBoundsInParent()) ||
											Samurai.rightHitbox.getBoundsInParent().intersects(Levels.boss1.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss1.greenHP.setWidth(Levels.boss1.greenHP.getWidth() - 15);
									}
								}
								else if (Town.character.equals("mage"))
								{
									// PLAYING ATTACK ANIM
									Level1.plrAttacked = true;
									Level1.boolAttackCooldown = true;
									mage.attack1();
									Level1.tlAttack.play();
									Level1.tlAttackCooldown.playFromStart();

									for (int i = 0; i < Levels.minion1.size(); i ++)
									{
										// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
										if (Mage.leftHitbox.getBoundsInParent().intersects(Levels.minion1.get(i).getNode().getBoundsInParent()) && Mage.left||
												Mage.rightHitbox.getBoundsInParent().intersects(Levels.minion1.get(i).getNode().getBoundsInParent()) && Mage.right)
										{
											Levels.minion1.get(i).isDead = true;

											Levels.minion1.get(i).setX(40000);
											Levels.minion1.get(i).setY(40000);
											Levels.root.getChildren().remove(Levels.minion1.get(i).getNode());
											Levels.minion1.remove(i);
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Mage.leftHitbox.getBoundsInParent().intersects(Levels.boss1.getNode().getBoundsInParent()) ||
											Mage.rightHitbox.getBoundsInParent().intersects(Levels.boss1.getNode().getBoundsInParent()))
									{
										Levels.boss1.greenHP.setWidth(Levels.boss1.greenHP.getWidth() - 15);
									}
								}

							}

							else if (levelChoice.equals("2 - Easy"))
							{
								// PLAYING AUDIO CLIP
								UntitledHandler.attackSlash.play();
								if (Town.character.equals("samurai"))
								{
									// PLAYING ANIM
									Level2.plrAttacked = true;
									Level2.boolAttackCooldown = true;
									samurai.attack1();
									Level2.tlAttack.play();
									Level2.tlAttackCooldown.playFromStart();

									for (int i = 0; i < Levels.minion2.size(); i ++)
									{
										if (!Levels.minion2.get(i).detonate)
										{
											// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
											if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.minion2.get(i).getNode().getBoundsInParent()) && Samurai.left||
													Samurai.rightHitbox.getBoundsInParent().intersects(Levels.minion2.get(i).getNode().getBoundsInParent()) && Samurai.right)
											{
												Levels.minion2.get(i).isDead = true;

												Levels.minion2.get(i).setX(40000);
												Levels.minion2.get(i).setY(40000);
												Levels.root.getChildren().remove(Levels.minion2.get(i).getNode());
												Levels.minion2.remove(i);
											}
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.boss2.getNode().getBoundsInParent()) ||
											Samurai.rightHitbox.getBoundsInParent().intersects(Levels.boss2.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss2.greenHP.setWidth(Levels.boss2.greenHP.getWidth() - 12);
									}
								}
								else if (Town.character.equals("mage"))
								{
									// PLAYING ANIM
									Level2.plrAttacked = true;
									Level2.boolAttackCooldown = true;
									mage.attack1();
									Level2.tlAttack.play();
									Level2.tlAttackCooldown.playFromStart();

									for (int i = 0; i < Levels.minion2.size(); i ++)
									{
										// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
										if (Mage.leftHitbox.getBoundsInParent().intersects(Levels.minion2.get(i).getNode().getBoundsInParent()) && Mage.left||
												Mage.rightHitbox.getBoundsInParent().intersects(Levels.minion2.get(i).getNode().getBoundsInParent()) && Mage.right)
										{
											Levels.minion2.get(i).isDead = true;

											Levels.minion2.get(i).setX(40000);
											Levels.minion2.get(i).setY(40000);
											Levels.root.getChildren().remove(Levels.minion2.get(i).getNode());
											Levels.minion2.remove(i);
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Mage.leftHitbox.getBoundsInParent().intersects(Levels.boss2.getNode().getBoundsInParent()) ||
											Mage.rightHitbox.getBoundsInParent().intersects(Levels.boss2.getNode().getBoundsInParent()))
									{
										Levels.boss2.greenHP.setWidth(Levels.boss2.greenHP.getWidth() - 12);
									}
								}
							}
							else if (levelChoice.equals("3 - Medium"))
							{
								// PLAYING AUDIO CLIP
								UntitledHandler.attackSlash.play();
								if (Town.character.equals("samurai"))
								{
									
									// PLAYING ANIM
									Level3.plrAttacked = true;
									Level3.boolAttackCooldown = true;
									samurai.attack1();
									Level3.tlAttack.play();
									Level3.tlAttackCooldown.playFromStart();

									for (int i = 0; i < Levels.minion3.size(); i ++)
									{
										if (!Levels.minion3.get(i).detonate)
										{
											// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
											if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.minion3.get(i).getNode().getBoundsInParent()) && Samurai.left||
													Samurai.rightHitbox.getBoundsInParent().intersects(Levels.minion3.get(i).getNode().getBoundsInParent()) && Samurai.right)
											{
												Levels.minion3.get(i).isDead = true;

												Levels.minion3.get(i).setX(40000);
												Levels.minion3.get(i).setY(40000);
												Levels.root.getChildren().remove(Levels.minion3.get(i).getNode());
												Levels.minion3.remove(i);
											}
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.boss3.getNode().getBoundsInParent()) ||
											Samurai.rightHitbox.getBoundsInParent().intersects(Levels.boss3.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss3.greenHP.setWidth(Levels.boss3.greenHP.getWidth() - 9);
									}
								}
								else if (Town.character.equals("mage"))
								{
									// PLAYING ANIM
									Level3.plrAttacked = true;
									Level3.boolAttackCooldown = true;
									mage.attack1();
									Level3.tlAttack.play();
									Level3.tlAttackCooldown.playFromStart();

									for (int i = 0; i < Levels.minion3.size(); i ++)
									{
										// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
										if (Mage.leftHitbox.getBoundsInParent().intersects(Levels.minion3.get(i).getNode().getBoundsInParent()) && Mage.left||
												Mage.rightHitbox.getBoundsInParent().intersects(Levels.minion3.get(i).getNode().getBoundsInParent()) && Mage.right)
										{
											Levels.minion3.get(i).isDead = true;

											Levels.minion3.get(i).setX(40000);
											Levels.minion3.get(i).setY(40000);
											Levels.root.getChildren().remove(Levels.minion3.get(i).getNode());
											Levels.minion3.remove(i);
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Mage.leftHitbox.getBoundsInParent().intersects(Levels.boss3.getNode().getBoundsInParent()) ||
											Mage.rightHitbox.getBoundsInParent().intersects(Levels.boss3.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss3.greenHP.setWidth(Levels.boss3.greenHP.getWidth() - 9);
									}
								}
							}
							else if (levelChoice.equals("4 - Hard"))
							{
								// PLAYING AUDIO CLIP
								UntitledHandler.attackSlash.play();
								if (Town.character.equals("samurai"))
								{
									// PLAYING ANIM
									Level4.plrAttacked = true;
									Level4.boolAttackCooldown = true;
									samurai.attack1();
									Level4.tlAttack.play();
									Level4.tlAttackCooldown.playFromStart();

									for (int i = 0; i < Levels.minion4.size(); i ++)
									{
										if (!Levels.minion4.get(i).detonate)
										{
											// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
											if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.minion4.get(i).getNode().getBoundsInParent()) && Samurai.left||
													Samurai.rightHitbox.getBoundsInParent().intersects(Levels.minion4.get(i).getNode().getBoundsInParent()) && Samurai.right)
											{
												Levels.minion4.get(i).isDead = true;

												Levels.minion4.get(i).setX(40000);
												Levels.minion4.get(i).setY(40000);
												Levels.root.getChildren().remove(Levels.minion4.get(i).getNode());
												Levels.minion4.remove(i);
											}
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.boss4.getNode().getBoundsInParent()) ||
											Samurai.rightHitbox.getBoundsInParent().intersects(Levels.boss4.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss4.greenHP.setWidth(Levels.boss4.greenHP.getWidth() - 6);
									}
								}
								else if (Town.character.equals("mage"))
								{
									// PLAYING ANIM
									Level4.plrAttacked = true;
									Level4.boolAttackCooldown = true;
									mage.attack1();
									Level4.tlAttack.play();
									Level4.tlAttackCooldown.playFromStart();

									for (int i = 0; i < Levels.minion4.size(); i ++)
									{
										// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
										if (Mage.leftHitbox.getBoundsInParent().intersects(Levels.minion4.get(i).getNode().getBoundsInParent()) && Mage.left||
												Mage.rightHitbox.getBoundsInParent().intersects(Levels.minion4.get(i).getNode().getBoundsInParent()) && Mage.right)
										{
											Levels.minion4.get(i).isDead = true;

											Levels.minion4.get(i).setX(40000);
											Levels.minion4.get(i).setY(40000);
											Levels.root.getChildren().remove(Levels.minion4.get(i).getNode());
											Levels.minion4.remove(i);
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Mage.leftHitbox.getBoundsInParent().intersects(Levels.boss4.getNode().getBoundsInParent()) ||
											Mage.rightHitbox.getBoundsInParent().intersects(Levels.boss4.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss4.greenHP.setWidth(Levels.boss4.greenHP.getWidth() - 6);
									}
								}
							}
						}
					}
					
					// RIGHT CLICK ATTACK
					else if (e.getButton() == MouseButton.SECONDARY)
					{
						if (!Levels.boolAttackCooldown)
						{	
							if (Town.character.equals("samurai"))
							{
								// PLAYING ANIM
								Levels.plrAttacked = true;
								Levels.boolAttackCooldown = true;
								samurai.attack2();
								Levels.tlAttack.play();
								Levels.tlAttackCooldown.playFromStart();

								if (levelChoice.equals("1 - Beginner"))
								{
									// PLAYING AUDIO CLIP
									UntitledHandler.attackSlash.play();
									for (int i = 0; i < Levels.minion1.size(); i ++)
									{
										// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
										if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.minion1.get(i).getNode().getBoundsInParent()) && Samurai.left||
												Samurai.rightHitbox.getBoundsInParent().intersects(Levels.minion1.get(i).getNode().getBoundsInParent()) && Samurai.right)
										{
											Levels.minion1.get(i).isDead = true;

											Levels.minion1.get(i).setX(40000);
											Levels.minion1.get(i).setY(40000);
											Levels.root.getChildren().remove(Levels.minion1.get(i).getNode());
											Levels.minion1.remove(i);
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.boss1.getNode().getBoundsInParent()) ||
											Samurai.rightHitbox.getBoundsInParent().intersects(Levels.boss1.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss1.greenHP.setWidth(Levels.boss1.greenHP.getWidth() - 15);
									}
								}
								else if (levelChoice.equals("2 - Easy"))
								{
									// PLAYING AUDIO CLIP
									UntitledHandler.attackSlash.play();
									for (int i = 0; i < Levels.minion2.size(); i ++)
									{
										// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
										if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.minion2.get(i).getNode().getBoundsInParent()) && Samurai.left||
												Samurai.rightHitbox.getBoundsInParent().intersects(Levels.minion2.get(i).getNode().getBoundsInParent()) && Samurai.right)
										{
											Levels.minion2.get(i).isDead = true;

											Levels.minion2.get(i).setX(40000);
											Levels.minion2.get(i).setY(40000);
											Levels.root.getChildren().remove(Levels.minion2.get(i).getNode());
											Levels.minion2.remove(i);
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.boss2.getNode().getBoundsInParent()) ||
											Samurai.rightHitbox.getBoundsInParent().intersects(Levels.boss2.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss2.greenHP.setWidth(Levels.boss2.greenHP.getWidth() - 12);
									}
								}
								else if (levelChoice.equals("3 - Medium"))
								{
									// PLAYING AUDIO CLIP
									UntitledHandler.attackSlash.play();
									for (int i = 0; i < Levels.minion3.size(); i ++)
									{
										// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
										if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.minion3.get(i).getNode().getBoundsInParent()) && Samurai.left||
												Samurai.rightHitbox.getBoundsInParent().intersects(Levels.minion3.get(i).getNode().getBoundsInParent()) && Samurai.right)
										{
											Levels.minion3.get(i).isDead = true;

											Levels.minion3.get(i).setX(40000);
											Levels.minion3.get(i).setY(40000);
											Levels.root.getChildren().remove(Levels.minion3.get(i).getNode());
											Levels.minion3.remove(i);
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.boss3.getNode().getBoundsInParent()) ||
											Samurai.rightHitbox.getBoundsInParent().intersects(Levels.boss3.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss3.greenHP.setWidth(Levels.boss3.greenHP.getWidth() - 9);
									}
								}
								else if (levelChoice.equals("4 - Hard"))
								{
									// PLAYING AUDIO CLIP
									UntitledHandler.attackSlash.play();
									for (int i = 0; i < Levels.minion4.size(); i ++)
									{
										// CHECKING IF HITBOXES ARE TOUCHING MINION WHEN CLICKED
										if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.minion4.get(i).getNode().getBoundsInParent()) && Samurai.left||
												Samurai.rightHitbox.getBoundsInParent().intersects(Levels.minion4.get(i).getNode().getBoundsInParent()) && Samurai.right)
										{
											Levels.minion4.get(i).isDead = true;

											Levels.minion4.get(i).setX(40000);
											Levels.minion4.get(i).setY(40000);
											Levels.root.getChildren().remove(Levels.minion4.get(i).getNode());
											Levels.minion4.remove(i);
										}
									}		

									// CHECKING IF HITBOXES ARE TOUCHING BOSS WHEN CLICKED
									if (Samurai.leftHitbox.getBoundsInParent().intersects(Levels.boss4.getNode().getBoundsInParent()) ||
											Samurai.rightHitbox.getBoundsInParent().intersects(Levels.boss4.getNode().getBoundsInParent()))
									{
										// REDUCING BOSS HEALTH
										Levels.boss4.greenHP.setWidth(Levels.boss4.greenHP.getWidth() - 6);
									}
								}
							}
							else if (Town.character.equals("mage"))
							{
								// PLAYING AUDIO CLIP
								UntitledHandler.projectileShot.play();
								
								// SETTING HANDLERS TO TRUE
								Levels.plrAttacked = true;
								Levels.boolAttackCooldown = true;
								mage.attack2();
								Levels.tlAttack.play();
								Levels.tlAttackCooldown.playFromStart();

								// MAKING NEW MAGE PROJECTILE
								mage.mageProjectile.add(new MageProjectile());

								// MAGE PROJECTILE
								Levels.root.getChildren().add(mage.mageProjectile.get(mage.mageProjectile.size() - 1).getNode());
								mage.mageProjectile.get(mage.mageProjectile.size() - 1).setPosition(Mage.getX(), Mage.yPos);
							}						
						}
					}
				}
			});
		}
	}
	
	// LEVEL TIMERS
	public static void levelTimers()
	{
		
		// DEBOUNCE FOR PLAYER PROJECTILE
		Levels.projectileCooldown = new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e) 
			{
				Levels.projectileFired = false;
			}
		});
		Levels.tlProjectileCooldown = new Timeline(Levels.projectileCooldown);
		
		// ATTACK TIMER
		Levels.attackTimer = new KeyFrame(Duration.millis(350), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				Levels.plrAttacked = false;
				Levels.tlAttack.pause();
			}
		});	
		Levels.tlAttack = new Timeline(Levels.attackTimer);
		Levels.tlAttack.setCycleCount(Timeline.INDEFINITE);
		
		// ATTACK COOLDOWN
		Levels.attackCooldown = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				Levels.boolAttackCooldown = false;
			}
		});	
		Levels.tlAttackCooldown = new Timeline(Levels.attackCooldown);
		
		// JUMP TIMER
		Levels.	jumpTimer = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				Levels.jump = false;
				Levels.gravityActive = true;
				Levels.tlJump.pause();
			}
		});	
		Levels.tlJump = new Timeline(Levels.jumpTimer);
		
		// ACCELERATION TIMER FOR GRAVITY
		Levels.accelTimer = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				Levels.accFactor += 4;
			}
		});	
		Levels.tlAccel = new Timeline(Levels.accelTimer);
		Levels.tlAccel.setCycleCount(Timeline.INDEFINITE);
		Levels.tlAccel.play();
		
		// PLAYER TIMER
		Levels.plrTimer = new AnimationTimer()
		{
			public void handle(long e) {
		
				// SAMURAI
				if (Town.character.equals("samurai"))
				{
					for (int i = 0; i < level.getPlatformBottom().length; i++)
					{
						// CHECKING IF PLAYER IS TOUCHING BOTTOM OF PLATFORM OR PLAYER ISN'T AT GROUND PLATFORM
						if (Levels.yPos2 + Levels.plrBottom.getHeight() < level.getPlatformTop()[13].getY() && Levels.gravityActive || 
								Levels.plrTop.getBoundsInParent().intersects(Levels.platformBottomArray[i].getBoundsInParent()) || 
								samurai.getY() + Samurai.getNode().getImage().getHeight() < Levels.plrBottom.getY() && samurai.getY() < 5)
						{
							Levels.bottomTouched = true;
							Levels.topTouched = false;
						}
					}
					platformBottomIntersected();
					
					// MOVING PLAYER LEFT IF LEFT BOOLEAN IS TRUE
					if (Levels.left && samurai.getX() > 0)
					{
						Levels.xPos -= Town.speed;
						Levels.xPos2 -= Town.speed;
					}
					
					// MOVING PLAYER RIGHT IF RIGHT BOOLEAN IS TRUE
					if (Levels.right && samurai.getX() + Samurai.getNode().getFitWidth() < Levels.scene.getWidth())
					{
						Levels.xPos += Town.speed;
						Levels.xPos2 += Town.speed;
					}
					
					// CHECKING IF PLAYER HAS JUMPED AND DECREASING Y POSITION
					if (Levels.jump)
					{
						Levels.yPos -= 17;
						Levels.yPos2 -= 17;
					}
			
					// MOVING PLAYER
					Levels.plrTop.setX(Levels.xPos);
					Levels.plrTop.setY(Levels.yPos);
					
					samurai.move(Levels.jump);
					Samurai.setX(Levels.xPos - 35);
					Samurai.setY(Levels.yPos);
					
					Levels.plrBottom.setX(Levels.xPos2);
					Levels.plrBottom.setY(Levels.yPos2);
					
					Levels.topTouched = false;
					
					// CHECKING IF PLAYER IS ON THE TOP OF A PLATFORM
					for (int i = 0; i < level.getPlatformTop().length; i++)
					{
						if (Levels.plrBottom.getBoundsInParent().intersects(Levels.platformTopArray[i].getBoundsInParent()))
						{
							Levels.topTouched = true;
							Levels.bottomTouched = false;
						}
					}
					platformTopIntersected();
				}
				
				// MAGE
				else if (Town.character.equals("mage"))
				{
					// CHECKING IF PLAYER IS TOUCHING BOTTOM OF PLATFORM OR PLAYER ISN'T AT GROUND PLATFORM
					for (int i = 0; i < level.getPlatformBottom().length; i++)
					{
						if (Levels.yPos2 + Levels.plrBottom.getHeight() < level.getPlatformTop()[13].getY() && Levels.gravityActive || 
								Levels.plrTop.getBoundsInParent().intersects(Levels.platformBottomArray[i].getBoundsInParent()) || 
								mage.getY() + Mage.getNode().getImage().getHeight() < Levels.plrBottom.getY() && mage.getY() < 5)
						{
							Levels.bottomTouched = true;
							Levels.topTouched = false;
						}
					}
					platformBottomIntersected();
					
					// MOVING MAGE LEFT IF LEFT BOOLEAN IS TRUE
					if (Levels.left && mage.getX() > 0)
					{
						Levels.xPos -= Town.speed;
						Levels.xPos2 -= Town.speed;
					}
					// MOVING MAGE RIGHT IF RIGHT BOOLEAN IS TRUE
					if (Levels.right && mage.getX() + Mage.getNode().getFitWidth() < Levels.scene.getWidth())
					{
						Levels.xPos += Town.speed;
						Levels.xPos2 += Town.speed;
					}
					
					// DECREASING Y POSITION IF JUMP BOOLEAN IS TRUE
					if (Levels.jump)
					{
						Levels.yPos -= 17;
						Levels.yPos2 -= 17;
					}
			
					// MOVING PLAYER
					Levels.plrTop.setX(Levels.xPos);
					Levels.plrTop.setY(Levels.yPos);
					
					Mage.move(Levels.jump);
					Mage.setX(Levels.xPos - 35);
					Mage.setY(Levels.yPos);
					
					Levels.plrBottom.setX(Levels.xPos2);
					Levels.plrBottom.setY(Levels.yPos2);
					
					Levels.topTouched = false;
					
					// CHECKING IF PLAYER IS ON THE TOP OF A PLATFORM
					for (int i = 0; i < level.getPlatformTop().length; i++)
					{
						if (Levels.plrBottom.getBoundsInParent().intersects(Levels.platformTopArray[i].getBoundsInParent()))
						{
							Levels.topTouched = true;
							Levels.bottomTouched = false;
						}
					}
					platformTopIntersected();
				}
				
				// GENERAL PROJECTILE
				// MOVING PROJECTILE WHEN "E" KEY PRESSED
				// CHECKING IF PROJECTILE ISN'T NULL (IF PLAYER HAS BOUGHT PROJECTILE)
				if (Main.plrData.get(4) != null)
				{
					for (int i = 0; i < projectile.size(); i++)
					{
						// MOVING PROJECTILE
						projectile.get(i).move(velocityX, velocityY);

						// LEVEL 1
						if (levelChoice.equals("1 - Beginner"))
						{
							for (int j = 0; j < Levels.minion1.size(); j++)
							{
								if (projectile.size() >= 1)
								{
									// CHECKING IF PROJECTILE HAS COLLIDED WITH MINION
									if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.minion1.get(j).getNode().getBoundsInParent()))
									{
										projectile.get(i).projectileHitCount += 1;

										// REMOVING MINION FROM SCREEN
										Levels.minion1.get(j).isDead = true;
										Levels.minion1.get(j).setX(40000);
										Levels.minion1.get(j).setY(40000);
										Levels.root.getChildren().remove(Levels.minion1.get(j).getNode());
										Levels.minion1.remove(j);
										j--;

										// REMOVING PROJECTILE IF IT HAS REACHED IT'S MAX HIT
										if (projectile.get(i).projectileHitCount == projectile.get(i).maxHit)
										{
											projectile.get(i).setX(50000);
											projectile.get(i).setY(50000);
											Levels.root.getChildren().remove(projectile.get(i).getNode());
											projectile.remove(i);
											i--;
										}
									}
								}
							}
						}
						
						// LEVEL 2
						else if (levelChoice.equals("2 - Easy"))
						{
							for (int j = 0; j < Levels.minion2.size(); j++)
							{
								if (projectile.size() >= 1)
								{
									// CHECKING IF PROJECTILE HAS COLLIDED WITH MINION
									if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.minion2.get(j).getNode().getBoundsInParent()))
									{
										projectile.get(i).projectileHitCount += 1;

										// REMOVING MINION FROM SCREEN
										Levels.minion2.get(j).isDead = true;
										Levels.minion2.get(j).setX(40000);
										Levels.minion2.get(j).setY(40000);
										Levels.root.getChildren().remove(Levels.minion2.get(j).getNode());
										Levels.minion2.remove(j);
										j--;

										// REMOVING PROJECTILE IF IT HAS REACHED IT'S MAX HIT
										if (projectile.get(i).projectileHitCount == projectile.get(i).maxHit)
										{
											projectile.get(i).setX(50000);
											projectile.get(i).setY(50000);
											Levels.root.getChildren().remove(projectile.get(i).getNode());
											projectile.remove(i);
											i--;
										}
									}
								}
							}
						}
						
						// LEVEL 3
						else if (levelChoice.equals("3 - Medium"))
						{
							for (int j = 0; j < Levels.minion3.size(); j++)
							{
								if (projectile.size() >= 1)
								{
									// CHECKING IF PROJECTILE HAS COLLIDED WITH MINION
									if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.minion3.get(j).getNode().getBoundsInParent()))
									{
										projectile.get(i).projectileHitCount += 1;

										// REMOVING MINION FROM SCREEN
										Levels.minion3.get(j).isDead = true;
										Levels.minion3.get(j).setX(40000);
										Levels.minion3.get(j).setY(40000);
										Levels.root.getChildren().remove(Levels.minion3.get(j).getNode());
										Levels.minion3.remove(j);
										j--;

										// REMOVING PROJECTILE IF IT HAS REACHED IT'S MAX HIT
										if (projectile.get(i).projectileHitCount == projectile.get(i).maxHit)
										{
											projectile.get(i).setX(50000);
											projectile.get(i).setY(50000);
											Levels.root.getChildren().remove(projectile.get(i).getNode());
											projectile.remove(i);
											i--;
										}
									}
								}
							}
						}
						// LEVEL 4
						else if (levelChoice.equals("4 - Hard"))
						{
							for (int j = 0; j < Levels.minion4.size(); j++)
							{
								if (projectile.size() >= 1)
								{
									// CHECKING IF PROJECTILE HAS COLLIDED WITH MINION
									if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.minion4.get(j).getNode().getBoundsInParent()))
									{
										projectile.get(i).projectileHitCount += 1;

										// REMOVING MINION FROM SCREEN
										Levels.minion4.get(j).isDead = true;
										Levels.minion4.get(j).setX(40000);
										Levels.minion4.get(j).setY(40000);
										Levels.root.getChildren().remove(Levels.minion4.get(j).getNode());
										Levels.minion4.remove(j);
										j--;

										// REMOVING PROJECTILE IF IT HAS REACHED IT'S MAX HIT
										if (projectile.get(i).projectileHitCount == projectile.get(i).maxHit)
										{
											projectile.get(i).setX(50000);
											projectile.get(i).setY(50000);
											Levels.root.getChildren().remove(projectile.get(i).getNode());
											projectile.remove(i);
											i--;
										}
									}
								}
							}
						}
					
						// CHECKING IF PROJECTILE COLLIDES WITH BOSS
						if (projectile.size() >= 1)
						{
							if (levelChoice.equals("1 - Beginner"))
							{
								// CHECKING IF PROJECTIEL COLLIDES WITH BOSS
								if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.boss1.getNode().getBoundsInParent()))
								{
									// REDUCE LEVEL 1 BOSS HEALTH IF PROJECTILE HITS
									Levels.boss1.greenHP.setWidth(Levels.boss1.greenHP.getWidth() - projectile.get(i).damage);
									
									// REMOVE PROJECTILE FROM SCENE IF COLLIDE WITH BOSS
									projectile.get(i).setX(50000);
									projectile.get(i).setY(50000);
									Levels.root.getChildren().remove(projectile.get(i).getNode());
									projectile.remove(i);
									i--;
								}
							}
							else if (levelChoice.equals("2 - Easy"))
							{
								// CHECKING IF PROJECTIEL COLLIDES WITH BOSS
								if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.boss2.getNode().getBoundsInParent()))
								{
									// REDUCE LEVEL 1 BOSS HEALTH IF PROJECTILE HITS
									Levels.boss2.greenHP.setWidth(Levels.boss2.greenHP.getWidth() - projectile.get(i).damage);
									
									// REMOVE PROJECTILE FROM SCENE IF COLLIDE WITH BOSS
									projectile.get(i).setX(50000);
									projectile.get(i).setY(50000);
									Levels.root.getChildren().remove(projectile.get(i).getNode());
									projectile.remove(i);
									i--;
								}
							}
							else if (levelChoice.equals("3 - Medium"))
							{
								// CHECKING IF PROJECTIEL COLLIDES WITH BOSS
								if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.boss3.getNode().getBoundsInParent()))
								{
									// REDUCE LEVEL 1 BOSS HEALTH IF PROJECTILE HITS
									Levels.boss3.greenHP.setWidth(Levels.boss3.greenHP.getWidth() - projectile.get(i).damage);
									
									// REMOVE PROJECTILE FROM SCENE IF COLLIDE WITH BOSS
									projectile.get(i).setX(50000);
									projectile.get(i).setY(50000);
									Levels.root.getChildren().remove(projectile.get(i).getNode());
									projectile.remove(i);
									i--;
								}
							}
							else if (levelChoice.equals("4 - Hard"))
							{
								// CHECKING IF PROJECTIEL COLLIDES WITH BOSS
								if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.boss4.getNode().getBoundsInParent()))
								{
									// REDUCE LEVEL 1 BOSS HEALTH IF PROJECTILE HITS
									Levels.boss4.greenHP.setWidth(Levels.boss4.greenHP.getWidth() - projectile.get(i).damage);
									
									// REMOVE PROJECTILE FROM SCENE IF COLLIDE WITH BOSS
									projectile.get(i).setX(50000);
									projectile.get(i).setY(50000);
									Levels.root.getChildren().remove(projectile.get(i).getNode());
									projectile.remove(i);
									i--;
								}
							}
						}
						
						// CHECKING IF COLLIDE WITH TOP PLATFORM
						if (projectile.size() >= 1)
						{
							// CHECKING IF PROJECTIEL COLLIDES WITH BOSS
							if (projectile.get(i).getNode().getBoundsInParent().intersects(Levels.platformTopArray[0].getBoundsInParent()) ||
									projectile.get(i).getNode().getBoundsInParent().intersects(Levels.platformBottomArray[0].getBoundsInParent()))
							{									
								// REMOVE PROJECTILE FROM SCENE IF COLLIDE WITH BOSS
								projectile.get(i).setX(50000);
								projectile.get(i).setY(50000);
								Levels.root.getChildren().remove(projectile.get(i).getNode());
								projectile.remove(i);
								i--;
							}
						}
						
						if (projectile.size() >= 1)
						{
							// CHECKING IF PROJECTIEL COLLIDES WITH BOSS
							if (projectile.get(i).getX() < 100 || projectile.get(i).getX() > Levels.scene.getWidth() ||
									projectile.get(i).getY() < 100 || projectile.get(i).getY() > Levels.scene.getHeight())
							{									
								// REMOVE PROJECTILE FROM SCENE IF COLLIDE WITH BOSS
								projectile.get(i).setX(50000);
								projectile.get(i).setY(50000);
								Levels.root.getChildren().remove(projectile.get(i).getNode());
								projectile.remove(i);
								i--;
							}
						}
					}
				}
			}			
		};
		// STARTING PLAYER TIMER
		Levels.plrTimer.start();
		
		// MAGE PROJECTILE TIMER
		Levels.mageProjectileTimer = new AnimationTimer()
		{
			public void handle(long e) {

				// MAGE
				if (Town.character.equals("mage"))
				{		
					// LEVEL 1
					if (levelChoice.equals("1 - Beginner"))
					{
						if (mage.mageProjectile.size() > 0 )
							if (Levels.minion1.size() > 0)		
							{
								// MOVING MAGE PROJECTILE
								for (int i = 0; i < mage.mageProjectile.size(); i++)
								{
									// MOVING MAGE PROJECTILE
									mage.mageProjectile.get(i).move();

									for (int j = 0; j < Levels.minion1.size(); j++)
									{
										if (mage.mageProjectile.size() > 0 )
											if (Levels.minion1.size() > 0)		
											{
												if (mage.mageProjectile.get(i).getNode().getBoundsInParent().intersects(Levels.minion1.get(j).getNode().getBoundsInParent()))
												{
													// REMOVING PROJECTILE
													if (mage.mageProjectile.size() > 0 )
													{
														mage.mageProjectile.get(i).setX(1);
														mage.mageProjectile.get(i).setY(1);
														Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
														mage.mageProjectile.remove(i);
														i--;
													}

													// REMOVING MINION
													if (Levels.minion1.size() > 0)
													{
														Levels.minion1.get(j).isDead = true;
														Levels.minion1.get(j).setX(40000);
														Levels.minion1.get(j).setY(40000);
														Levels.root.getChildren().remove(Levels.minion1.get(j).getNode());
														Levels.minion1.remove(j);
														j--;
													}
												}
											}
									}

									if (mage.mageProjectile.size() > 0)
									{
										// CHECKING IF MAGE PROGECTILE INTERSECTED BOSS
										if (mage.mageProjectile.get(i).getNode().getBoundsInParent().intersects(Levels.boss1.getNode().getBoundsInParent()))
										{
											// REDUCING BOSS HP
											Levels.boss1.greenHP.setWidth(Levels.boss1.greenHP.getWidth() - 15);

											// REMOVING MAGE PROJECTILE
											mage.mageProjectile.get(i).setX(1);
											mage.mageProjectile.get(i).setY(1);
											Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
											mage.mageProjectile.remove(i);
											i--;
										}
									}

									if (mage.mageProjectile.size() > 0)
									{
										// CHECKING IF MAGE PROJECTILE GREATER THAN SCENE DIMENSIONS
										if (mage.mageProjectile.get(i).getX() + mage.mageProjectile.get(i).getWidth() <= 0 ||
												mage.mageProjectile.get(i).getX() >= 1200)
										{
											// REMOVING MAGE PROJECTILE
											mage.mageProjectile.get(i).setX(1);
											mage.mageProjectile.get(i).setY(1);
											Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
											mage.mageProjectile.remove(i);
											i--;
										}
									}
								}
							}
					}
					
					// LEVEL 2
					else if (levelChoice.equals("2 - Easy"))
					{
						if (mage.mageProjectile.size() > 0 )
							if (Levels.minion2.size() > 0)		
							{
								// MOVING MAGE PROJECTILE
								for (int i = 0; i < mage.mageProjectile.size(); i++)
								{
									// MOVING MAGE PROJECTILE
									mage.mageProjectile.get(i).move();

									for (int j = 0; j < Levels.minion2.size(); j++)
									{
										if (mage.mageProjectile.size() > 0 )
											if (Levels.minion2.size() > 0)		
											{
												// CHECKING IF MAGE PROJECTILE TOUCHED MINIONS
												if (mage.mageProjectile.get(i).getNode().getBoundsInParent().intersects(Levels.minion2.get(j).getNode().getBoundsInParent()))
												{
													// REMOVING PROJECTILE
													if (mage.mageProjectile.size() > 0 )
													{
														// REMOVING MAGE PROJECTILE
														mage.mageProjectile.get(i).setX(1);
														mage.mageProjectile.get(i).setY(1);
														Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
														mage.mageProjectile.remove(i);
														i--;
													}

													// REMOVING MINION
													if (Levels.minion2.size() > 0)
													{
														// REMOVING MINION
														Levels.minion2.get(j).isDead = true;
														Levels.minion2.get(j).setX(40000);
														Levels.minion2.get(j).setY(40000);
														Levels.root.getChildren().remove(Levels.minion2.get(j).getNode());
														Levels.minion2.remove(j);
														j--;
													}
												}
											}
									}

									if (mage.mageProjectile.size() > 0)
									{
										// CHECKING IF MAGE PROJECTILE TOUCHES BOSS
										if (mage.mageProjectile.get(i).getNode().getBoundsInParent().intersects(Levels.boss2.getNode().getBoundsInParent()))
										{
											// REDUCING BOSS HP
											Levels.boss2.greenHP.setWidth(Levels.boss2.greenHP.getWidth() - 12);

											// REMOVING MAGE PROJECTILE
											mage.mageProjectile.get(i).setX(1);
											mage.mageProjectile.get(i).setY(1);
											Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
											mage.mageProjectile.remove(i);
											i--;
										}
									}

									if (mage.mageProjectile.size() > 0)
									{
										// CHECKING IF MAGE PROJECTILE GREATER THAN SCENE DIMENSIONS
										if (mage.mageProjectile.get(i).getX() + mage.mageProjectile.get(i).getWidth() <= 0 ||
												mage.mageProjectile.get(i).getX() >= 1200)
										{
											
											// REMOVING MAGE PROJECTILE
											mage.mageProjectile.get(i).setX(1);
											mage.mageProjectile.get(i).setY(1);
											Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
											mage.mageProjectile.remove(i);
											i--;
										}
									}
								}
							}
					}
					
					// LEVEL 3
					else if (levelChoice.equals("3 - Medium"))
					{
						if (mage.mageProjectile.size() > 0 )
							if (Levels.minion3.size() > 0)		
							{
								// MOVING MAGE PROJECTILE
								for (int i = 0; i < mage.mageProjectile.size(); i++)
								{
									// MOVING MAGE PROJECTILE
									mage.mageProjectile.get(i).move();

									for (int j = 0; j < Levels.minion3.size(); j++)
									{
										if (mage.mageProjectile.size() > 0 )
											if (Levels.minion3.size() > 0)		
											{
												// CHECKING IF MAGE PROJECTILE INTERSECTS MINION
												if (mage.mageProjectile.get(i).getNode().getBoundsInParent().intersects(Levels.minion3.get(j).getNode().getBoundsInParent()))
												{
													// REMOVING PROJECTILE
													if (mage.mageProjectile.size() > 0 )
													{
														// REMOVING MAGE PROJECTILE
														mage.mageProjectile.get(i).setX(1);
														mage.mageProjectile.get(i).setY(1);
														Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
														mage.mageProjectile.remove(i);
														i--;
													}

													// REMOVING MINION
													if (Levels.minion3.size() > 0)
													{
														Levels.minion3.get(j).isDead = true;
														Levels.minion3.get(j).setX(40000);
														Levels.minion3.get(j).setY(40000);
														Levels.root.getChildren().remove(Levels.minion3.get(j).getNode());
														Levels.minion3.remove(j);
														j--;
													}
												}
											}
									}

									if (mage.mageProjectile.size() > 0)
									{
										// CHECKING IF PROJECTILE TOUCHES BOSS
										if (mage.mageProjectile.get(i).getNode().getBoundsInParent().intersects(Levels.boss3.getNode().getBoundsInParent()))
										{
											// REDUCING BOSS HP
											Levels.boss3.greenHP.setWidth(Levels.boss3.greenHP.getWidth() - 9);

											// REMOVING MAGE PROJECTILE
											mage.mageProjectile.get(i).setX(1);
											mage.mageProjectile.get(i).setY(1);
											Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
											mage.mageProjectile.remove(i);
											i--;
										}
									}

									if (mage.mageProjectile.size() > 0)
									{
										// CHECKING IF PROJECTILE GREATER THAN SCENE DIMENSIONS
										if (mage.mageProjectile.get(i).getX() + mage.mageProjectile.get(i).getWidth() <= 0 ||
												mage.mageProjectile.get(i).getX() >= 1200)
										{
											// REMOVING MAGE PROJECTILE
											mage.mageProjectile.get(i).setX(1);
											mage.mageProjectile.get(i).setY(1);
											Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
											mage.mageProjectile.remove(i);
											i--;
										}
									}
								}
							}
					}
					// LEVEL 4
					else if (levelChoice.equals("4 - Hard"))
					{
						if (mage.mageProjectile.size() > 0 )
							if (Levels.minion4.size() > 0)		
							{
								// MOVING MAGE PROJECTILE
								for (int i = 0; i < mage.mageProjectile.size(); i++)
								{
									// MOVING MAGE PROJECTILE
									mage.mageProjectile.get(i).move();

									for (int j = 0; j < Levels.minion4.size(); j++)
									{
										if (mage.mageProjectile.size() > 0 )
											if (Levels.minion4.size() > 0)		
											{
												// CHECKING IF PROJECTILE COLLIDES WITH MINION
												if (mage.mageProjectile.get(i).getNode().getBoundsInParent().intersects(Levels.minion4.get(j).getNode().getBoundsInParent()))
												{
													// REMOVING PROJECTILE
													if (mage.mageProjectile.size() > 0 )
													{
														// REMOVING PROJECTILE
														mage.mageProjectile.get(i).setX(1);
														mage.mageProjectile.get(i).setY(1);
														Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
														mage.mageProjectile.remove(i);
														i--;
													}

													// REMOVING MINION
													if (Levels.minion4.size() > 0)
													{
														Levels.minion4.get(j).isDead = true;
														Levels.minion4.get(j).setX(40000);
														Levels.minion4.get(j).setY(40000);
														Levels.root.getChildren().remove(Levels.minion4.get(j).getNode());
														Levels.minion4.remove(j);
														j--;
													}
												}
											}
									}

									if (mage.mageProjectile.size() > 0)
									{
										// CHECKING IF PROJECTILE COLLIDES WITH BOSS
										if (mage.mageProjectile.get(i).getNode().getBoundsInParent().intersects(Levels.boss4.getNode().getBoundsInParent()))
										{
											// REDUCING BOSS HP
											Levels.boss4.greenHP.setWidth(Levels.boss4.greenHP.getWidth() - 6);

											// REMOVING MAGE PROJECTILE
											mage.mageProjectile.get(i).setX(1);
											mage.mageProjectile.get(i).setY(1);
											Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
											mage.mageProjectile.remove(i);
											i--;
										}
									}

									if (mage.mageProjectile.size() > 0)
									{
										// CHECKING IF PROJECTILE GREATER THAN SCENE DIMENSIONS
										if (mage.mageProjectile.get(i).getX() + mage.mageProjectile.get(i).getWidth() <= 0 ||
												mage.mageProjectile.get(i).getX() >= 1200)
										{
											// REMOVING MAGE PROJECTILE
											mage.mageProjectile.get(i).setX(1);
											mage.mageProjectile.get(i).setY(1);
											Levels.root.getChildren().remove(mage.mageProjectile.get(i).getNode());
											mage.mageProjectile.remove(i);
											i--;
										}
									}
								}
							}
					}
				}
			}	
		};
		Levels.mageProjectileTimer.start();
		
		// BOSS TIMER
		Levels.bossTimer = new AnimationTimer()
		{
			public void handle(long e) {
	
				// LEVEL 1
				if (levelChoice.equals("1 - Beginner"))
				{
					Levels.boss1.sceneBounds(Levels.scene.getWidth(), Levels.platformTopArray[0]);
					Levels.boss1.move();
					
					// ENABLING BOSS GRAVITY
					if (Levels.boss1.bossGravityActive)
					{
						Levels.boss1.setY(Levels.boss1.getY() + Levels.boss1.bossGravity);
					}
					
					Levels.bossPlatformTouched = false;
					// ENABLING FREE ROAM IF PLAYER IS CLOSE TO BOSS
					if (Town.character.equals("samurai"))
					{
						if (Samurai.getNode().getBoundsInParent().intersects(Levels.roamInitiate.getBoundsInParent()))
						{
							Levels.boss1.enableFreeRoam();
						}
					}
					else if (Town.character.equals("mage"))
					{
						if (Mage.getNode().getBoundsInParent().intersects(Levels.roamInitiate.getBoundsInParent()))
						{
							Levels.boss1.enableFreeRoam();
						}
					}
					bossPlatformIntersected();
					
					if (Town.character.equals("samurai"))
					{
						// CHECKING IF BOSS IS DEAD AND REWARDING PLAYER
						if (Levels.boss1.greenHP.getWidth() <= 0 || Samurai.greenHP.getWidth() <= 0)
						{
							// PLAYING BACKGROUND MUSIC
							Level1.level1Music.pause();
							
							// RESSETTING LOCATION OF PLAYER
							Samurai.setX(600 - Samurai.iv.getFitWidth()/2);
							Samurai.setY(Town.scene.getHeight() - 200);
							Samurai.greenHP.setWidth(100);
							
							if (Levels.boss1.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 25);
								UntitledHandler.bossDead.play();
							}
							
							else if (Samurai.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 0);
								UntitledHandler.playerDead.play();
							}
							boolLevelScreen = false;
							boolTownScreen = true;
							
							// START MAIN GAME HANDLERS
							UntitledHandler.mainGame();
							UntitledHandler.townMove();
							Town.plrTimer.start();
							Levels.plrTimer.stop();
							Levels.tlMinionSpawn.stop();
							Levels.minionTimer.stop();
							Levels.mageProjectileTimer.stop();
							Levels.tlAccel.stop();
							Levels.bossTimer.stop();
						}
					}
					else if (Town.character.equals("mage"))
					{
						// CHECKING IF BOSS IS DEAD AND REWARDING PLAYER
						if (Levels.boss1.greenHP.getWidth() <= 0 || Mage.greenHP.getWidth() <= 0)
						{
							// PLAYING BACKGROUND MUSIC
							Level1.level1Music.pause();
							
							// RESSETTING LOCATION OF PLAYER
							Mage.resetPos();
								
							if (Levels.boss1.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 25);
								UntitledHandler.bossDead.play();
							}

							else if (Mage.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 0);
								UntitledHandler.playerDead.play();
							}

							boolLevelScreen = false;
							boolTownScreen = true;
							
							// START MAIN GAME HANDLERS
							UntitledHandler.mainGame();
							UntitledHandler.townMove();
							Town.plrTimer.start();
							Levels.plrTimer.stop();
							Levels.tlMinionSpawn.stop();
							Levels.minionTimer.stop();
							Levels.mageProjectileTimer.stop();
							Levels.tlAccel.stop();
							Levels.bossTimer.stop();
						}
					}
				}
				
				// LEVEL 2
				else if (levelChoice.equals("2 - Easy"))
				{
					// MOVING BOSS
					Levels.boss2.sceneBounds(Levels.scene.getWidth(), Levels.platformTopArray[0]);
					Levels.boss2.move();
					
					// ENABLING BOSS GRAVITY
					if (Levels.boss2.bossGravityActive)
					{
						Levels.boss2.setY(Levels.boss2.getY() + Levels.boss2.bossGravity);
					}
					
					Levels.bossPlatformTouched = false;
					
					// ENABLING FREE ROAM IF PLAYER IS CLOSE TO BOSS
					if (Town.character.equals("samurai"))
					{
						if (Samurai.getNode().getBoundsInParent().intersects(Levels.roamInitiate.getBoundsInParent()))
						{
							Levels.boss2.enableFreeRoam();
						}
					}
					else if (Town.character.equals("mage"))
					{
						if (Mage.getNode().getBoundsInParent().intersects(Levels.roamInitiate.getBoundsInParent()))
						{
							Levels.boss2.enableFreeRoam();
						}
					}
					bossPlatformIntersected();
					
					if (Town.character.equals("samurai"))
					{
						// CHECKING IF BOSS IS DEAD AND REWARDING PLAYER
						if (Levels.boss2.greenHP.getWidth() <= 0 || Samurai.greenHP.getWidth() <= 0)
						{
							// PLAYING BACKGROUND MUSIC
							Level2.level2Music.pause();

							if (Levels.boss2.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 50);
								UntitledHandler.bossDead.play();
							}

							else if (Samurai.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 0);
								UntitledHandler.playerDead.play();
							}
							boolLevelScreen = false;
							boolTownScreen = true;
							
							// START MAIN GAME HANDLERS
							UntitledHandler.mainGame();
							UntitledHandler.townMove();
							Town.plrTimer.start();
							Levels.plrTimer.stop();
							Levels.tlMinionSpawn.stop();
							Levels.minionTimer.stop();
							Levels.mageProjectileTimer.stop();
							Levels.tlAccel.stop();
							Levels.bossTimer.stop();
						}
					}
					else if (Town.character.equals("mage"))
					{
						// CHECKING IF BOSS IS DEAD AND REWARDING PLAYER
						if (Levels.boss2.greenHP.getWidth() <= 0 || Mage.greenHP.getWidth() <= 0)
						{
							// PLAYING BACKGROUND MUSIC
							Level2.level2Music.pause();

							if (Levels.boss2.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 50);
								UntitledHandler.bossDead.play();
							}

							else if (Mage.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 0);
								UntitledHandler.playerDead.play();
							}

							boolLevelScreen = false;
							boolTownScreen = true;
							
							// START MAIN GAME HANDLERS
							UntitledHandler.mainGame();
							UntitledHandler.townMove();
							Town.plrTimer.start();
							Levels.plrTimer.stop();
							Levels.tlMinionSpawn.stop();
							Levels.minionTimer.stop();
							Levels.mageProjectileTimer.stop();
							Levels.tlAccel.stop();
							Levels.bossTimer.stop();
						}
					}
				}
				
				// LEVEL 3
				else if (levelChoice.equals("3 - Medium"))
				{
					// MOVING BOSS
					Levels.boss3.sceneBounds(Levels.scene.getWidth(), Levels.platformTopArray[0]);
					Levels.boss3.move();
					
					// ENABLING BOSS GRAVITY
					if (Levels.boss3.bossGravityActive)
					{
						Levels.boss3.setY(Levels.boss3.getY() + Levels.boss3.bossGravity);
					}
					
					Levels.bossPlatformTouched = false;
					// ENABLING FREE ROAM IF PLAYER IS CLOSE TO BOSS
					if (Town.character.equals("samurai"))
					{
						if (Samurai.getNode().getBoundsInParent().intersects(Levels.roamInitiate.getBoundsInParent()))
						{
							Levels.boss3.enableFreeRoam();
						}
					}
					else if (Town.character.equals("mage"))
					{
						if (Mage.getNode().getBoundsInParent().intersects(Levels.roamInitiate.getBoundsInParent()))
						{
							Levels.boss3.enableFreeRoam();
						}
					}
					bossPlatformIntersected();
					
					if (Town.character.equals("samurai"))
					{
						// CHECKING IF BOSS IS DEAD AND REWARDING PLAYER
						if (Levels.boss3.greenHP.getWidth() <= 0 || Samurai.greenHP.getWidth() <= 0)
						{
							// PLAYING BACKGROUND MUSIC
							Level3.level3Music.pause();

							if (Levels.boss3.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 75);
								UntitledHandler.bossDead.play();
							}

							else if (Samurai.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 0);
								UntitledHandler.playerDead.play();
							}
							
							boolLevelScreen = false;
							boolTownScreen = true;
							
							// START MAIN GAME HANDLERS
							UntitledHandler.mainGame();
							UntitledHandler.townMove();
							Town.plrTimer.start();
							Levels.plrTimer.stop();
							Levels.tlMinionSpawn.stop();
							Levels.minionTimer.stop();
							Levels.mageProjectileTimer.stop();
							Levels.tlAccel.stop();
							Levels.bossTimer.stop();
						}
					}
					else if (Town.character.equals("mage"))
					{
						// CHECKING IF BOSS IS DEAD AND REWARDING PLAYER
						if (Levels.boss3.greenHP.getWidth() <= 0 || Mage.greenHP.getWidth() <= 0)
						{
							// PLAYING BACKGROUND MUSIC
							Level3.level3Music.pause();

							if (Levels.boss3.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 75);
								UntitledHandler.bossDead.play();
							}

							else if (Mage.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 0);
								UntitledHandler.playerDead.play();
							}

							boolLevelScreen = false;
							boolTownScreen = true;
							
							// START MAIN GAME HANDLERS
							UntitledHandler.mainGame();
							UntitledHandler.townMove();
							Town.plrTimer.start();
							Levels.plrTimer.stop();
							Levels.tlMinionSpawn.stop();
							Levels.minionTimer.stop();
							Levels.mageProjectileTimer.stop();
							Levels.tlAccel.stop();
							Levels.bossTimer.stop();
						}
					}
				}
				// LEVEL 4
				else if (levelChoice.equals("4 - Hard"))
				{
					// MOVING BOSS
					Levels.boss4.sceneBounds(Levels.scene.getWidth(), Levels.platformTopArray[0]);
					Levels.boss4.move();
					
					// ENABLING BOSS GRAVITY
					if (Levels.boss4.bossGravityActive)
					{
						Levels.boss4.setY(Levels.boss4.getY() + Levels.boss4.bossGravity);
					}
					
					Levels.bossPlatformTouched = false;
					// ENABLING FREE ROAM IF PLAYER GETS TOO CLOSE TO BOSS
					if (Town.character.equals("samurai"))
					{
						if (Samurai.getNode().getBoundsInParent().intersects(Levels.roamInitiate.getBoundsInParent()))
						{
							Levels.boss4.enableFreeRoam();
						}
					}
					else if (Town.character.equals("mage"))
					{
						if (Mage.getNode().getBoundsInParent().intersects(Levels.roamInitiate.getBoundsInParent()))
						{
							Levels.boss4.enableFreeRoam();
						}
					}
					bossPlatformIntersected();
					
					if (Town.character.equals("samurai"))
					{
						// CHECKING IF BOSS IS DEAD AND REWARDING PLAYER
						if (Levels.boss4.greenHP.getWidth() <= 0 || Samurai.greenHP.getWidth() <= 0)
						{
							
							// PLAYING BACKGROUND MUSIC
							Level4.level4Music.pause();
							
							if (Levels.boss4.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 100);
								UntitledHandler.bossDead.play();
							}

							else if (Samurai.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 0);
								UntitledHandler.playerDead.play();
							}

							boolLevelScreen = false;
							boolTownScreen = true;
							
							// START MAIN GAME HANDLERS
							UntitledHandler.mainGame();
							UntitledHandler.townMove();
							Town.plrTimer.start();
							Levels.plrTimer.stop();
							Levels.tlMinionSpawn.stop();
							Levels.minionTimer.stop();
							Levels.mageProjectileTimer.stop();
							Levels.tlAccel.stop();
							Levels.bossTimer.stop();
						}
					}
					else if (Town.character.equals("mage"))
					{
						// CHECKING IF BOSS IS DEAD AND REWARDING PLAYER
						if (Levels.boss4.greenHP.getWidth() <= 0 || Mage.greenHP.getWidth() <= 0)
						{
							
							// PLAYING BACKGROUND MUSIC
							Level4.level4Music.pause();
							
							if (Levels.boss4.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 100);
								UntitledHandler.bossDead.play();
							}

							else if (Mage.greenHP.getWidth() <= 0)
							{
								town = new Town(Levels.primaryStage, Main.plrData, 0);
								UntitledHandler.playerDead.play();
							}

							boolLevelScreen = false;
							boolTownScreen = true;
							
							// START MAIN GAME HANDLERS
							UntitledHandler.mainGame();
							UntitledHandler.townMove();
							Town.plrTimer.start();
							Levels.plrTimer.stop();
							Levels.tlMinionSpawn.stop();
							Levels.minionTimer.stop();
							Levels.mageProjectileTimer.stop();
							Levels.tlAccel.stop();
							Levels.bossTimer.stop();
						}
					}
				}	
			}
		};
		// STARTING BOSS TIMER
		Levels.bossTimer.start();
		
		// MINION SPAWN RATE TIMELINE
		Levels.minionSpawn = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0) {

				// INITIATING RANDOM OBJECT
				Random rnd = new Random();
				
				// SETTING SPAWN RATE
				int spawnRate = rnd.nextInt(5) + 1;
				
				if (spawnRate == 1)
				{
					if (levelChoice.equals("1 - Beginner"))
					{
						// ADDING MINION TO SCENE AND ARRAYLIST
						Levels.minion1.add(new Minion1(Levels.boss1.getX(), Levels.boss1.getY()));
						Levels.root.getChildren().add(Levels.minion1.get(Levels.minion1.size() - 1).getNode());
					}
					else if (levelChoice.equals("2 - Easy"))
					{
						// ADDING MINION TO SCENE AND ARRAYLIST
						Levels.minion2.add(new Minion2(Levels.boss2.getX(), Levels.boss2.getY()));
						Levels.root.getChildren().add(Levels.minion2.get(Levels.minion2.size() - 1).getNode());
					}
					else if (levelChoice.equals("3 - Medium"))
					{
						// ADDING MINION TO SCENE AND ARRAYLIST
						Levels.minion3.add(new Minion3(Levels.boss3.getX(), Levels.boss3.getY()));
						Levels.root.getChildren().add(Levels.minion3.get(Levels.minion3.size() - 1).getNode());
					}
					else if (levelChoice.equals("4 - Hard"))
					{
						// ADDING MINION TO SCENE AND ARRAYLIST
						Levels.minion4.add(new Minion4(Levels.boss4.getX(), Levels.boss4.getY()));
						Levels.root.getChildren().add(Levels.minion4.get(Levels.minion4.size() - 1).getNode());
					}
				}
			}
		});
		Levels.tlMinionSpawn = new Timeline(Levels.minionSpawn);
		Levels.tlMinionSpawn.setCycleCount(Timeline.INDEFINITE);
		Levels.tlMinionSpawn.play();
		
		// MINION TIMER
		Levels.minionTimer = new AnimationTimer()
		{
			public void handle(long arg0) {
				
				// LEVEL 1
				if (levelChoice.equals("1 - Beginner"))
				{
					for (int i = 0; i < Levels.minion1.size(); i ++)
					{
						// MOVING MINION
						if (!Levels.minion1.get(i).detonate)
						{
							Levels.minion1.get(i).move();
						}
						else
						{
							Levels.minion1.get(i).dead();
						}
						Levels.minion1.get(i).sceneBounds(Levels.scene.getWidth(), Levels.platformTopArray[0]);
						
						// CHECKING IF MINION GRAVITY IS ACTIVE
						if (Levels.minion1.get(i).minionGravityActive)
						{
							Levels.minion1.get(i).setY(Levels.minion1.get(i).getY() + Levels.minion1.get(i).minionGravity);
							Levels.minion1.get(i).minionPlatformTouched = false;
						}
					}					
					minionPlatformIntersected();
					
					for (int i = 0; i < Levels.minion1.size(); i ++)
					{
						if (Town.character.equals("samurai"))
						{
							// CHECKING IF PLAYER TOUCH MINION THAT IS EXPLOSIVE
							if (Samurai.getNode().getBoundsInParent().intersects(Levels.minion1.get(i).getNode().getBoundsInParent()))
							{
								if (Levels.minion1.get(i).explodeChance == 1)
								{
									if (!Levels.minion1.get(i).detonate)
									{
										// REDUCE HEALTH AND PLAY EXPLOSION ANIM
										Samurai.greenHP.setWidth(Samurai.greenHP.getWidth() - 5);
										UntitledHandler.minionExplode.play();
									}
									Levels.minion1.get(i).detonate = true;
								}
							}
						}
						else if (Town.character.equals("mage"))
						{
							// CHECKING IF PLAYER TOUCH MINION THAT IS EXPLOSIVE
							if (Mage.getNode().getBoundsInParent().intersects(Levels.minion1.get(i).getNode().getBoundsInParent()))
							{
								if (Levels.minion1.get(i).explodeChance == 1)
								{
									if (!Levels.minion1.get(i).detonate)
									{
										// REDUCE HEALTH AND PLAY EXPLOSION ANIM
										Mage.greenHP.setWidth(Mage.greenHP.getWidth() - 10);
										UntitledHandler.minionExplode.play();
									}
									Levels.minion1.get(i).detonate = true;
								}
							}
						}
					}
				}
				// LEVEL 2
				else if (levelChoice.equals("2 - Easy"))
				{
					// MOVING MINION
					for (int i = 0; i < Levels.minion2.size(); i ++)
					{
						if (!Levels.minion2.get(i).detonate)
						{
							Levels.minion2.get(i).move();
						}
						else
						{
							Levels.minion2.get(i).dead();
						}
						Levels.minion2.get(i).sceneBounds(Levels.scene.getWidth(), Levels.platformTopArray[0]);
						
						if (Levels.minion2.get(i).minionGravityActive)
						{
							Levels.minion2.get(i).setY(Levels.minion2.get(i).getY() + Levels.minion2.get(i).minionGravity);
							Levels.minion2.get(i).minionPlatformTouched = false;
						}
					}					
					minionPlatformIntersected();
					
					for (int i = 0; i < Levels.minion2.size(); i ++)
					{
						if (Town.character.equals("samurai"))
						{	// CHEKCING FOR INTERSECTION WITH EXPLOSIVE MINION
							if (Samurai.getNode().getBoundsInParent().intersects(Levels.minion2.get(i).getNode().getBoundsInParent()))
							{
								if (Levels.minion2.get(i).explodeChance == 1)
								{
									if (!Levels.minion2.get(i).detonate)
									{
										// REDUCING HP AND PLAYING ANIM
										Samurai.greenHP.setWidth(Samurai.greenHP.getWidth() - 5);
										UntitledHandler.minionExplode.play();
									}
									Levels.minion2.get(i).detonate = true;
								}
							}
						}
						else if (Town.character.equals("mage"))
						{
							// CHEKCING FOR INTERSECTION WITH EXPLOSIVE MINION
							if (Mage.getNode().getBoundsInParent().intersects(Levels.minion2.get(i).getNode().getBoundsInParent()))
							{
								if (Levels.minion2.get(i).explodeChance == 1)
								{
									if (!Levels.minion2.get(i).detonate)
									{
										// REDUCE HEALTH AND PLAY EXPLOSION ANIM
										Mage.greenHP.setWidth(Mage.greenHP.getWidth() - 10);
										UntitledHandler.minionExplode.play();
									}
									Levels.minion2.get(i).detonate = true;
								}
							}
						}
					}
				}
				// LEVEL 3
				else if (levelChoice.equals("3 - Medium"))
				{
					// MOVING MINION
					for (int i = 0; i < Levels.minion3.size(); i ++)
					{
						if (!Levels.minion3.get(i).detonate)
						{
							Levels.minion3.get(i).move();
						}
						else
						{
							Levels.minion3.get(i).dead();
						}
						Levels.minion3.get(i).sceneBounds(Levels.scene.getWidth(), Levels.platformTopArray[0]);
						
						if (Levels.minion3.get(i).minionGravityActive)
						{
							Levels.minion3.get(i).setY(Levels.minion3.get(i).getY() + Levels.minion3.get(i).minionGravity);
							Levels.minion3.get(i).minionPlatformTouched = false;
						}
					}					
					minionPlatformIntersected();
					
					for (int i = 0; i < Levels.minion3.size(); i ++)
					{
						if (Town.character.equals("samurai"))
						{
							// CHEKCING FOR INTERSECTION WITH EXPLOSIVE MINION
							if (Samurai.getNode().getBoundsInParent().intersects(Levels.minion3.get(i).getNode().getBoundsInParent()))
							{
								if (Levels.minion3.get(i).explodeChance == 1)
								{
									if (!Levels.minion3.get(i).detonate)
									{
										// REDUCING HP
										Samurai.greenHP.setWidth(Samurai.greenHP.getWidth() - 10);
										UntitledHandler.minionExplode.play();
									}
									Levels.minion3.get(i).detonate = true;
								}
							}
						}
						else if (Town.character.equals("mage"))
						{
							// CHEKCING FOR INTERSECTION WITH EXPLOSIVE MINION
							if (Mage.getNode().getBoundsInParent().intersects(Levels.minion3.get(i).getNode().getBoundsInParent()))
							{
								if (Levels.minion3.get(i).explodeChance == 1)
								{
									if (!Levels.minion3.get(i).detonate)
									{
										// REDUCING HP
										Mage.greenHP.setWidth(Mage.greenHP.getWidth() - 20);
										UntitledHandler.minionExplode.play();
									}
									Levels.minion3.get(i).detonate = true;
								}
							}
						}
					}
				}
				// LEVEL 4
				else if (levelChoice.equals("4 - Hard"))
				{
					// MOVING MINION
					for (int i = 0; i < Levels.minion4.size(); i ++)
					{
						if (!Levels.minion4.get(i).detonate)
						{
							Levels.minion4.get(i).move();
						}
						else
						{
							Levels.minion4.get(i).dead();
						}
						Levels.minion4.get(i).sceneBounds(Levels.scene.getWidth(), Levels.platformTopArray[0]);
						
						if (Levels.minion4.get(i).minionGravityActive)
						{
							Levels.minion4.get(i).setY(Levels.minion4.get(i).getY() + Levels.minion4.get(i).minionGravity);
							Levels.minion4.get(i).minionPlatformTouched = false;
						}
					}					
					minionPlatformIntersected();
					
					for (int i = 0; i < Levels.minion4.size(); i ++)
					{
						if (Town.character.equals("samurai"))
						{
							// CHEKCING FOR INTERSECTION WITH EXPLOSIVE MINION
							if (Samurai.getNode().getBoundsInParent().intersects(Levels.minion4.get(i).getNode().getBoundsInParent()))
							{
								if (Levels.minion4.get(i).explodeChance == 1)
								{
									if (!Levels.minion4.get(i).detonate)
									{
										// REDUCING HP
										Samurai.greenHP.setWidth(Samurai.greenHP.getWidth() - 10);
										UntitledHandler.minionExplode.play();
									}
									Levels.minion4.get(i).detonate = true;
								}
							}
						}
						else if (Town.character.equals("mage"))
						{
							// CHEKCING FOR INTERSECTION WITH EXPLOSIVE MINION
							if (Mage.getNode().getBoundsInParent().intersects(Levels.minion4.get(i).getNode().getBoundsInParent()))
							{
								if (Levels.minion4.get(i).explodeChance == 1)
								{
									if (!Levels.minion4.get(i).detonate)
									{
										// REDUCING HP
										Mage.greenHP.setWidth(Mage.greenHP.getWidth() - 20);
										UntitledHandler.minionExplode.play();
									}
									Levels.minion4.get(i).detonate = true;
								}
							}
						}
					}
				}
			}
		};
		Levels.minionTimer.start();
	}
	
	// PLATFORM TOP INTERSECTED METHOD
	public static void platformTopIntersected()
	{
		// DISABLING GRAVITY IF PLAYER STANDING ON TOP PLATFORM
		if (Levels.topTouched)
		{
			Levels.gravityActive = false;
			Levels.accFactor = 0;
			Levels.tlAccel.pause();
			Levels.jumpDebounce = false;
		}
		else
		{
			Levels.gravityActive = true;
		}
	}
	
	// PLATFORM BOTTOM INTERSECTED METHOD
	public static void platformBottomIntersected()
	{
		if (Levels.bottomTouched)
		{
			// TURNIGN JUMP DEBOUNCE TO TRUE AND TURNING GRAVITY BACK ON
			Levels.jumpDebounce = true;
			Levels.tlAccel.play();
			Levels.yPos += Levels.gravity + Levels.accFactor;
			Levels.yPos2 += Levels.gravity + Levels.accFactor;
			
			// MAKING PLAYER FALL BACK DOWN
			if (Town.character.equals("samurai"))
			{
				Samurai.setY(samurai.getY() + Levels.accFactor);
			}
			else if (Town.character.equals("mage"))
			{
				Mage.setY(mage.getY() + Levels.accFactor);
			}
		}
	}
	
	// BOSS PLATFORM INTERSECTED
	public static void bossPlatformIntersected()
	{
		// LEVEL 1
		// SETTING GRAVITY TO FALSE IF BOSS IS ON PLATFORM
		if (levelChoice.equals("1 - Beginner"))
		{
			for (int i = 0; i < level.getPlatformTop().length; i++)
			{
				if (Levels.boss1.getNode().intersects(level.getPlatformTop()[i].getBoundsInParent()))
				{
					Levels.bossPlatformTouched = true;
				}
			}

			if (Levels.bossPlatformTouched)
			{
				Levels.boss1.bossGravityActive = false;
			}
			else
				Levels.boss1.bossGravityActive = true;
		}
		// LEVEL 2
		// SETTING GRAVITY TO FALSE IF BOSS IS ON PLATFORM
		else if (levelChoice.equals("2 - Easy"))
		{
			for (int i = 0; i < level.getPlatformTop().length; i++)
			{
				if (Levels.boss2.getNode().intersects(level.getPlatformTop()[i].getBoundsInParent()))
				{
					Levels.bossPlatformTouched = true;
				}
			}

			if (Levels.bossPlatformTouched)
			{
				Levels.boss2.bossGravityActive = false;
			}
			else
				Levels.boss2.bossGravityActive = true;
		}
		// LEVEL 3
		// SETTING GRAVITY TO FALSE IF BOSS IS ON PLATFORM
		else if (levelChoice.equals("3 - Medium"))
		{
			for (int i = 0; i < level.getPlatformTop().length; i++)
			{
				if (Levels.boss3.getNode().intersects(level.getPlatformTop()[i].getBoundsInParent()))
				{
					Levels.bossPlatformTouched = true;
				}
			}

			if (Levels.bossPlatformTouched)
			{
				Levels.boss3.bossGravityActive = false;
			}
			else
				Levels.boss3.bossGravityActive = true;
		}
		// LEVEL 4
		// SETTING GRAVITY TO FALSE IF BOSS IS ON PLATFORM
		else if (levelChoice.equals("4 - Hard"))
		{
			for (int i = 0; i < level.getPlatformTop().length; i++)
			{
				if (Levels.boss4.getNode().intersects(level.getPlatformTop()[i].getBoundsInParent()))
				{
					Levels.bossPlatformTouched = true;
				}
			}

			if (Levels.bossPlatformTouched)
			{
				Levels.boss4.bossGravityActive = false;
			}
			else
				Levels.boss4.bossGravityActive = true;
		}
	}
	
	// MINION INTERSECTED PLATFORM METHOD
	public static void minionPlatformIntersected()
	{	
		// LEVEL 1
		// SETTING GRAVITY TO FALSE IF MINION IS ON PLATFORM
		if (levelChoice.equals("1 - Beginner"))
		{
			for (int i = 0; i < Levels.minion1.size(); i++)
			{
				for (int j = 0; j < level.getPlatformTop().length; j++)
				{
					if (Levels.minion1.get(i).getNode().intersects(level.getPlatformTop()[j].getBoundsInParent()))
					{
						// SETTING PLATFORM TOUCHED TO TRUE IF MINION INTERSECTS WITH PLATFORM
						Levels.minion1.get(i).minionPlatformTouched = true;
						break;
					}
					else
					{
						Levels.minion1.get(i).minionPlatformTouched = false;
						
					}
				}

				if (Levels.minion1.get(i).minionPlatformTouched)
				{
					Levels.minion1.get(i).minionGravityActive = false;
				}
				else
					Levels.minion1.get(i).minionGravityActive = true;
			}
		}
		
		// LEVEL 2
		// SETTING GRAVITY TO FALSE IF MINION IS ON PLATFORM
		else if (levelChoice.equals("2 - Easy"))
		{
			for (int i = 0; i < Levels.minion2.size(); i++)
			{
				for (int j = 0; j < level.getPlatformTop().length; j++)
				{
					if (Levels.minion2.get(i).getNode().intersects(level.getPlatformTop()[j].getBoundsInParent()))
					{
						// SETTING PLATFORM TOUCHED TO TRUE IF MINION INTERSECTS WITH PLATFORM
						Levels.minion2.get(i).minionPlatformTouched = true;
						break;
					}
					else
					{
						Levels.minion2.get(i).minionPlatformTouched = false;
						
					}
				}

				if (Levels.minion2.get(i).minionPlatformTouched)
				{
					Levels.minion2.get(i).minionGravityActive = false;
				}
				else
					Levels.minion2.get(i).minionGravityActive = true;
			}
		}
		
		// LEVEL 3
		// SETTING GRAVITY TO FALSE IF MINION IS ON PLATFORM
		else if (levelChoice.equals("3 - Medium"))
		{
			for (int i = 0; i < Levels.minion3.size(); i++)
			{
				for (int j = 0; j < level.getPlatformTop().length; j++)
				{
					if (Levels.minion3.get(i).getNode().intersects(level.getPlatformTop()[j].getBoundsInParent()))
					{
						// SETTING PLATFORM TOUCHED TO TRUE IF MINION INTERSECTS WITH PLATFORM
						Levels.minion3.get(i).minionPlatformTouched = true;
						break;
					}
					else
					{
						Levels.minion3.get(i).minionPlatformTouched = false;
						
					}
				}

				if (Levels.minion3.get(i).minionPlatformTouched)
				{
					Levels.minion3.get(i).minionGravityActive = false;
				}
				else
					Levels.minion3.get(i).minionGravityActive = true;
			}
		}
		
		// LEVEL 4
		// SETTING GRAVITY TO FALSE IF MINION IS ON PLATFORM
		else if (levelChoice.equals("4 - Hard"))
		{
			for (int i = 0; i < Levels.minion4.size(); i++)
			{
				for (int j = 0; j < level.getPlatformTop().length; j++)
				{
					if (Levels.minion4.get(i).getNode().intersects(level.getPlatformTop()[j].getBoundsInParent()))
					{
						// SETTING PLATFORM TOUCHED TO TRUE IF MINION INTERSECTS WITH PLATFORM
						Levels.minion4.get(i).minionPlatformTouched = true;
						break;
					}
					else
					{
						Levels.minion4.get(i).minionPlatformTouched = false;
						
					}
				}

				if (Levels.minion4.get(i).minionPlatformTouched)
				{
					Levels.minion4.get(i).minionGravityActive = false;
				}
				else
					Levels.minion4.get(i).minionGravityActive = true;
			}
		}
	}
	
}
