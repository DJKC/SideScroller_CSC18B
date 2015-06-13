package Entity;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class VGSEmoji{
	private int width;
	private int height;
	private Image[][] images;
	private Image image;
	BufferedImage imageSet;
	private int numberSecondaryCommands;
	private int numberTernaryCommands;
	private boolean emojiSet;
	private long elapsed;
	private long start;
	
	public VGSEmoji(){
		width = 30;
		height = 30;
		numberSecondaryCommands = 3;
		numberTernaryCommands = 3;
		emojiSet = false;
		
		images = new Image[numberSecondaryCommands + 1][numberTernaryCommands];
		
		try{
			BufferedImage imageSet = ImageIO.read(
					getClass().getResourceAsStream(
						"/Sprites/Player/emojis2.gif"));
		
			for(int i  = 0; i < numberSecondaryCommands + 1; i ++){
				for(int j = 0; j < numberTernaryCommands; j++){
					//System.out.printf("\n2: %d, 3: %d\n", i, j);
					images[i][j] = imageSet.getSubimage(j * width, i * height, width, height);
				}
			}
			
			//empty transparent image
			setImage(3, 0);
		}
		
		catch(Exception e){e.printStackTrace();}
	}
	
	public void setImage(int r, int c){
		if(r != 3){
			start = System.nanoTime();
		}
		
		image = images[r][c];
	}
	
	public Image getImage(){
		return image;
	}
	
	public void update(){			
		//if emoji is set and 5 seconds pass
			//set blank emoji
		
		if(emojiSet){
			elapsed = (System.nanoTime() - start) / 1000000;
			
			if(elapsed > 3000){
				setImage(3, 0);
				emojiSet = false;
				elapsed = 0;
				//System.out.println("\nUNSET BITCH");
			}
			
			else{
				//System.out.println("\nSET BITCH");
				emojiSet = true;
			}
		}
		
		else{
			//System.out.println("\nno emoji set");
		}
	}

	public boolean getEmojiSet() {
		return emojiSet;
	}

	public void setEmojiSet(boolean set) {
		emojiSet = set;
	}
}
