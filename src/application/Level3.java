// IMPORTS
package application;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

// LEVEL 3 CLASS
public class Level3 extends Levels{

	// FIELDS
	// RECTANGLE BOTTOM PLATFORMS
	private Rectangle platformBottom1, platformBottom2, platformBottom3, platformBottom4, platformBottom5, 
	platformBottom6, platformBottom7, platformBottom8, platformBottom9, platformBottom10, platformBottom11,
	platformBottom12, platformBottom13;
	
	// RECTANGLE TOP PLATFORMS
	private Rectangle platformTop1, platformTop2, platformTop3, platformTop4, platformTop5, 
	platformTop6, platformTop7, platformTop8, platformTop9, platformTop10, platformTop11,
	platformTop12, platformTop13, platformTop14;

	protected static MediaPlayer level3Music;
	
	// CONSTRUCTOR
	public Level3(Stage primaryStage, Image bg)
	{
		// PLAYING LEVEL 3 MUSIC
		Town.townMusicPlayer.pause();
		File bgMusic = new File("sounds\\level3.mp3");
		Media media = new Media(bgMusic.toURI().toString());
		level3Music = new MediaPlayer(media);
		level3Music.setOnEndOfMedia(new Runnable()
		{
			public void run() {
				level3Music.seek(Duration.ZERO);
			}
		});
		level3Music.setCycleCount(MediaPlayer.INDEFINITE);
		level3Music.play();
		
		// INITIATING LEVEL BACKGROUND AND IMAGEVIEW OBJECT
		super.bg = bg;
		iv = new ImageView();
		iv.setImage(super.bg);
		
		// INITIATING PANE AND SCENE
		root = new Pane();
		Levels.scene = Town.scene;
		Levels.scene = new Scene(root, super.bg.getWidth(), super.bg.getHeight());
		
		// PLAYER COLLISION RECTANGLES
		plrTop = new Rectangle(xPos,yPos, 30, 40);
		plrBottom = new Rectangle(xPos2,yPos2, 30, 15);
		
		// ARRAY FOR RECTANGLE PLATFORMS
		platformBottomArray = new Rectangle[13];
		platformTopArray = new Rectangle[14];
		
		// RECTANGLE OBJECTS FOR TOP PLATFORMS
		platformTop1 = new Rectangle(304.9, 90.6, 582.5, 17);
		platformTop2 = new Rectangle(38.4, 181.6, 175.5, 15.1);
		platformTop3 = new Rectangle(7.8, 411.8, 175.5, 13.9);
		platformTop4 = new Rectangle(7.8, 633.7, 175.5, 15.1);
		platformTop5 = new Rectangle(252.2, 300.01, 175.5, 15.1);
		platformTop6 = new Rectangle(256.4, 518.6, 175.5, 15.1);
		platformTop7 = new Rectangle(512.3, 416.1, 175.5, 15.1);
		platformTop8 = new Rectangle(520, 630.1, 175.5, 15.1);
		platformTop9 = new Rectangle(755, 304.3, 175.5, 15.1);
		platformTop10 = new Rectangle(765.9, 519, 175.5, 15.1);
		platformTop11 = new Rectangle(961.1, 186.8, 175.5, 15.1);
		platformTop12 = new Rectangle(1016.8, 411.8, 175.5, 15.1);
		platformTop13 = new Rectangle(1020.6, 634.4, 175.5, 15.1);
		platformTop14 = new Rectangle(0, 765.8, 1200, 50);
		
		// RECTANGLE OBJECTS FOR BOTTOM PLATFORMS
		platformBottom1 = new Rectangle(316, 120, 554.3, 65);
		platformBottom2 = new Rectangle(38.4, 207.6, 175.5, 14.6);
		platformBottom3 = new Rectangle(7.8, 435.4, 175.5, 15);
		platformBottom4 = new Rectangle(5.8, 656, 175.5, 12.8);
		platformBottom5 = new Rectangle(256.4, 323.3, 175.5, 15.5);
		platformBottom6 = new Rectangle(256.4, 541, 175.5, 12.1);
		platformBottom7 = new Rectangle(516.1, 441, 175.5, 11.9);
		platformBottom8 = new Rectangle(523.9, 653.4, 175.5, 9.4);	
		platformBottom9 = new Rectangle(758.9, 330.7, 175.5, 12.3);
		platformBottom10 = new Rectangle(765.9, 543.9, 175.5, 13.3);
		platformBottom11 = new Rectangle(961.1, 211.9, 175.5, 12.6);
		platformBottom12 = new Rectangle(1029.6, 436.8, 175.5, 12.4);
		platformBottom13 = new Rectangle(1024.5, 661.5, 175.5, 12.3);
		
		// RECTANGLE TO INITIATE BOSS ROAM
		roamInitiate = new Rectangle(0, 0, 1200, 242.2);
		
		// ARRAY OF TOP PLATFORMS
		platformTopArray[0] = platformTop1;
		platformTopArray[1] = platformTop2;
		platformTopArray[2] = platformTop3;
		platformTopArray[3] = platformTop4;
		platformTopArray[4] = platformTop5;
		platformTopArray[5] = platformTop6;
		platformTopArray[6] = platformTop7;
		platformTopArray[7] = platformTop8;
		platformTopArray[8] = platformTop9;
		platformTopArray[9] = platformTop10;
		platformTopArray[10] = platformTop11;
		platformTopArray[11] = platformTop12;
		platformTopArray[12] = platformTop13;
		platformTopArray[13] = platformTop14;
		
		// ARRAY OF BOTTOM PLATFORMS
		platformBottomArray[0] = platformBottom1;
		platformBottomArray[1] = platformBottom2;
		platformBottomArray[2] = platformBottom3;
		platformBottomArray[3] = platformBottom4;
		platformBottomArray[4] = platformBottom5;
		platformBottomArray[5] = platformBottom6;
		platformBottomArray[6] = platformBottom7;
		platformBottomArray[7] = platformBottom8;
		platformBottomArray[8] = platformBottom9;
		platformBottomArray[9] = platformBottom10;
		platformBottomArray[10] = platformBottom11;
		platformBottomArray[11] = platformBottom12;
		platformBottomArray[12] = platformBottom13;
		
		// INITIATE BOSS OBJECTS
		boss3 = new Boss3(545.7, 0);
		minion3 = new ArrayList<Boss3>();
		
		// ADDING LEVEL BACKGROUND AND BOSS TO SCREEN
		root.getChildren().addAll(iv, boss3.getNode(), boss3.redHP, boss3.greenHP);

		// CHECKING FOR PLAYER'S CHARACTER AND ADDING SPECIFIC NODE TO SCREEN
		if (Town.character.equals("samurai"))
		{
			// SAMURAI CHARACTER
			Samurai.setX(600 - iv.getFitWidth()/2);
			Samurai.setY(Levels.scene.getHeight() - 200);
			root.getChildren().addAll(Samurai.getNode(), Samurai.redHP, Samurai.greenHP);
		}
		else if (Town.character.equals("mage"))
		{
			// MAGE CHARACTER
			Mage.setX(600 - iv.getFitWidth()/2);
			Mage.setY(Levels.scene.getHeight() - 200);
			root.getChildren().addAll(Mage.getNode(), Mage.redHP, Mage.greenHP);
		}
		
		// INTIIALIZING THE STAGE
		Levels.primaryStage = primaryStage;
		Levels.primaryStage.setScene(Levels.scene);
		Levels.primaryStage.centerOnScreen();
		Levels.primaryStage.show();
		
		// CALLING HANDLERS TO HANDLE LEVEL GAMEPLAY
		UntitledHandler.levelHandler();
		UntitledHandler.levelTimers();
	}

	// METHODS

	// RETURNING ARRAY OF TOP PLATFORMS
	public Rectangle[] getPlatformTop() {
		return platformTopArray;
	}

	// RETURNING ARRAY OF BOTTOM PLATFORMS
	public Rectangle[] getPlatformBottom() {
		return platformBottomArray;
	}

}