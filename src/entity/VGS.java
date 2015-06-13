package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class VGS{
	boolean vgsStarted;
	private String[] emojiTypes = {"Happy", "Mad", "Sad"};
	private String[] emojiLevels = {"Crazy", "Super", "Normal"};
	protected String currentCommand;
	static public String firstCommand;
	static public String secondCommand;
	static public String[] lastCommand;
	public int maxVGSLevels;
	long startTime;
	long elapsedTime;
	protected VGSEmoji emoji;
	static public char[] GameKeys = {KeyEvent.VK_E, KeyEvent.VK_W, KeyEvent.VK_R,
									 KeyEvent.VK_F, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT,
									 KeyEvent.VK_UP, KeyEvent.VK_DOWN};
	
	public VGS(){
		startTime = elapsedTime = 0;
		maxVGSLevels = 3;
		
		currentCommand = "";
		firstCommand = "V";
		secondCommand = "ABC";
		lastCommand = new String[]{"123", "456", "789"};
	}
	
	public void vgsOver(){
		vgsStarted = false;
		currentCommand = "";
		//System.out.println("\nVGS Over");
	}
	
	public boolean isVGSCommand(int vgsLevel, int k){
		if(vgsLevel == 2 || vgsLevel == 3){
			String command = "";
						
			if(vgsLevel == 2){
				command = secondCommand;
			}
			
			else if(vgsLevel == 3){
				command = lastCommand[secondCommand.indexOf(currentCommand.charAt(1))];
			}
			
			
			//System.out.println("\nLevel: " + vgsLevel + " " + (char)k);
			//System.out.println("\nCOMMAND: " + command);
			
			for(int i  = 0; i < command.length(); i++){
				if((char)k == command.charAt(i)){
					//System.out.println("\n" + (char)k + " is a valid vgs key");
					
					return true;
				}
			}
		}
			
		return false;
	}
	
	public String buildCommand(int k){
		//System.out.println("\n------------------------------------------\n" + 
		//"COMMAND CHECKED: " + (char)k);
		
		if(vgsStarted == true){
			elapsedTime = (System.nanoTime() - startTime) / 1000000000;
			
			//System.out.println("\nELAPSED: " + elapsedTime);
			
			if(elapsedTime < 5){
				//System.out.println("\nVGS STARTED");
				
				if(isVGSCommand(2, k) && currentCommand.length() == 1){
					currentCommand += (char)(k);
					
					//System.out.printf("\nCommand started2(%d): %s",
							   //currentCommand.length(), currentCommand);
				}
				
				else if(currentCommand.length() == 2 && isVGSCommand(3, k)){				
					currentCommand += (char)(k);
					
					//System.out.printf("\nCommand started3(%d): %s",
							   //currentCommand.length(), currentCommand);
					
					return currentCommand;
				}
				
				else{
					vgsOver();
					//System.out.println("\nNOT AN OPTION");
				}
			}
			
			else{
				vgsOver();
				//System.out.println("\nVGS TIME OUT");
			}
		}
		
		if (k == KeyEvent.VK_V){
			if(vgsStarted == false){
				vgsStarted = true;
				currentCommand = "";
				currentCommand += (char)(k);
				startTime = System.nanoTime();
				
				//System.out.printf("Command started1(%d): %s",
								   //currentCommand.length(), currentCommand);
			}
			
			else{
				//System.out.println("\nVGS already started");
			}
		}
		
		return null;
	}
	
	public String getCommand(){
		return currentCommand;
	}
	
	public boolean isGameKey(int k){
		for(char key : GameKeys){
			if(k == key){
				return true;
			}
		}
		
		return false;
	}
	
	public void draw(Graphics2D g){
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.WHITE);
		
		if(vgsStarted && currentCommand.length() == 1){
			g.drawString("VGA STARTED", 50, 10);
			
			for(int i = 0 ; i < 3; i++){
				String text = String.format("\t" + secondCommand.charAt(i) + " for " + emojiTypes[i]);
				g.drawString(text, 50, i * 10 + 20);
			}
		}
		
		else if(vgsStarted && currentCommand.length() == 2){
			for(int i = 0; i < 3; i++){
				//finds the corresponding string in lastCommand array depending on second letter
				//	and then shows each option one at a time per line
				//ie for A its "123", for B its "456", for C its "789"
				int vgsIndex = secondCommand.indexOf(currentCommand.charAt(1));
				char vgsEnding = lastCommand[vgsIndex].charAt(i);
				String text = String.format("\t" + vgsEnding + " for " +
											emojiLevels[i] + " " + emojiTypes[vgsIndex]);
				g.drawString(text, 50, i * 10 + 50);
			}
		}
		
		else if(vgsStarted == false){
			g.drawString("", 0, 0);
		}
	}
	
	public void update(){					
		if(vgsStarted){
			elapsedTime = (System.nanoTime() - startTime) / 1000000;
			
			if(elapsedTime > 5000){
				vgsStarted = false;
				elapsedTime = 0;
			}
		}
	}
}
