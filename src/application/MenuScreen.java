package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

// MENU SCREEN CLASS
public class MenuScreen extends Main{

	// FIELDS
	private static Pane rootMenu;
	private static Image alertScroll;
	private static Image alertClasses;
	private static Image alertControls;
	private static Image bgMenu;
	private static ImageView iv, ivAlert;
	private static boolean newAccount;
	
	protected static String CLASS_CHOICE = "";
	
	// GAME HANDLER
	private UntitledHandler handler;
	
	// TOWN CLASS
	private Town town;
	
	// MEDIA PLAYER
	protected static MediaPlayer menuMusicPlayer;
	
	// STAGE HOLDER
	protected static Stage primaryStage;
	
	public void init(Stage primaryStage, boolean newAccount)
	{
		// PLAYING BACKGROUND MUSIC FOR MENU
		File bgMusic = new File("sounds\\Menu.mp3");
		Media media = new Media(bgMusic.toURI().toString());
		menuMusicPlayer = new MediaPlayer(media);
		menuMusicPlayer.setOnEndOfMedia(new Runnable()
		{
			public void run() {
				menuMusicPlayer.seek(Duration.ZERO);
			}
		});
		menuMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		menuMusicPlayer.play();
		
		// INITIATE STAGE OBJECT
		MenuScreen.primaryStage = primaryStage;
		
		// IMAGES
		alertScroll = new Image("file:images\\menu\\scroll.png");
		alertClasses = new Image("file:images\\menu\\samurai_mage.png");
		alertControls = new Image("file:images\\menu\\alertControls.png");
		bgMenu = new Image("file:images\\menu\\menu.gif");
		
		// INITIALIZE SCENE
		rootMenu = new Pane();
		Scene scene = new Scene(rootMenu, bgMenu.getWidth(), bgMenu.getHeight());
		
		// SETTING BOOL TO PASSED IN VARIABLE
		MenuScreen.newAccount = newAccount;
		
		// INITIATE HANDLER 
		handler = new UntitledHandler();
		try {
			handler.init(MenuScreen.primaryStage, scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// CHEKCING FOR MENU CLICKS
		handler.menuInteract();
		
		// INITIALIZING IMAGEVIEWS
		iv = new ImageView();
		iv.setImage(bgMenu);
		MenuScreen.ivAlert = new ImageView();
		MenuScreen.ivAlert.setFitHeight(120);
		MenuScreen.ivAlert.setPreserveRatio(true);
		
		// ADDING BACKGROUND AND BUTTONS TO SCENE
		rootMenu.getChildren().add(iv);
			
		// SETTING SCENE TO STAGE AND MAKING IT VISIBLE
		MenuScreen.primaryStage.setScene(scene);
		MenuScreen.primaryStage.setTitle("MENU");
		MenuScreen.primaryStage.setResizable(false);
		MenuScreen.primaryStage.setFullScreen(false);
		MenuScreen.primaryStage.centerOnScreen();
		MenuScreen.primaryStage.show();
	}
	
	// METHODS
	// PLAY METHOD
	public void play()
	{
		if (MenuScreen.newAccount)
		{
			// CHARACTER SELECTION ARRAY LIST
			ArrayList<String> classes = new ArrayList<String>();
			// ADDING CHARACTER CHOICES TO LIST
			classes.add("Samurai");
			classes.add("Mage");
			ChoiceDialog<String> dialog = new ChoiceDialog<String>(classes.get(0), classes);
			dialog.setTitle("Pick a class");
			dialog.setHeaderText("Samurai stats:\t\tMage Stats:\nWeapon: Sword\tWeapon: Staff\nSpeed: 7\t\t\tSpeed: 10\nHP: 200\t\t\tHP: 100");
			dialog.setContentText(null);
			MenuScreen.ivAlert.setImage(alertClasses);
			dialog.setGraphic(ivAlert);
			
			// PROMPTING PLAYER TO PICK A CHARACTER
			Optional<String> result = dialog.showAndWait();
			
			if (result.isPresent())
			{
				String input = result.get();
				
				if (input.equals("Samurai"))
				{
					CLASS_CHOICE = "samurai";
				}
				else if (input.equals("Mage"))
				{
					CLASS_CHOICE = "mage";
				}
				
				// ADDING CHARACTER PLAYER PICKS TO PLAYER'S DATA
				Main.plrData.remove(2);
				Main.plrData.add(2, CLASS_CHOICE);
				UntitledHandler.boolMenuScreen = false;
				UntitledHandler.boolTownScreen = true;
				
				// PROMPTING NEW INSTANCE OF THE TOWN OBJECT
				town = new Town(MenuScreen.primaryStage, Main.plrData);
			}
		}

		else
		{
			// LOADING PLAYER INTO SCREEN IF THEY ALREADY HAVE A CHARACTER
			UntitledHandler.boolMenuScreen = false;
			UntitledHandler.boolTownScreen = true;

			// PROMPTING NEW INSTANCE OF THE TOWN OBJECT
			town = new Town(MenuScreen.primaryStage, Main.plrData);
		}
	}
	
	// TUTORIAL METHOD
	public void tutorial()
	{
		// TUTORIAL
		Alert alert = new Alert(AlertType.NONE);
		alert.setContentText("Welcome To Ascend");
		alert.setTitle("TUTORIAL");
		alert.setHeaderText(null);
		ivAlert.setImage(alertScroll);
		alert.setGraphic(ivAlert);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.NEXT);
		alert.showAndWait();
		
		alert.setContentText("Controls\n\nW & S:\t\tUp And Down (Town Only)\nA & D:\t\tLeft And Right\nSpacebar:\t\tJump (Levels Only)\nLeft-Click:\t\tAttack 1 (Levels Only)\nRight-Click:\tAttack 2 (Levels Only)\nE:\t\t\tProjectile (Levels Only)");
		ivAlert.setImage(alertControls);
		alert.setGraphic(ivAlert);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.NEXT);
		alert.showAndWait();
		
		alert.setContentText("Levels\n\n- You Will Walk Up To The Gate And Click The Question Mark To Pick The Level You Want\n- Levels Increase In Difficulty\n- Bosses Will Spawn At The Top And Spawn Minions That Have A Chance Of Exploding Once They Touch You");
		ivAlert.setImage(new Image("file:images\\enemies\\minion_1_right.png"));
		alert.setGraphic(ivAlert);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.NEXT);
		alert.showAndWait();
		
		alert.setContentText("Objective\n\n- Scale The Level Platforms And Defeat The Boss While Trying To Stay Alive\n- You Will Be Rewarded With Coins If You Beat The Boss\n- Talk To The Old Man To Use Your Coins To Buy Better Projectiles");
		ivAlert.setImage(new Image("file:images\\enemies\\boss_1_front.png"));
		alert.setGraphic(ivAlert);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.NEXT);
		alert.showAndWait();
	}
	
	// CREDITS METHOD
	public void credits()
	{
		// CREDITS
		Alert alert = new Alert(AlertType.NONE);
		alert.setContentText("Game by Dinim.");
		alert.setTitle("CREDITS");
		alert.setHeaderText(null);
		ivAlert.setImage(alertScroll);
		alert.setGraphic(ivAlert);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.OK);
		alert.showAndWait();
	}
	
	// EXIT GAME
	public void exit()
	{
		System.exit(0);
	}
}
