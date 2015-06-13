package src.appstate;

import java.awt.Graphics2D;
import java.util.ArrayList;

import core.Passport;
import layers.GeneralGraphicsLayer;

public class AppStateManager {

	private ArrayList<AppState> appStates;
	private int currentState;
	private GeneralGraphicsLayer appPanel;
	
	public static final int LOGINSTATE = 0;
	public static final int MENUSTATE = 1;
	public static final int LOBBYSTATE = 2;
	public static final int SETTINGSSTATE = 3;
	public static final int GAMESTATE = 4;
	public static final int CHARACTERSELECT = 5;
	
	public int gameID;
	public Passport _p;
	
	public AppStateManager(GeneralGraphicsLayer appPanel, Passport p){
		
		appStates = new ArrayList<AppState>();
		currentState = LOGINSTATE;
		this.appPanel = appPanel;
		this._p = p;
		appStates.add(new LoginState(this,this.appPanel));
		appStates.add(new MainMenuState(this,this.appPanel,_p));
		appStates.add(new LobbyState(this,this.appPanel));
		appStates.add(new SettingsState(this,this.appPanel));
		appStates.add(new GameState(this,this.appPanel,_p));
		appStates.add(new CharacterSelectState(this,this.appPanel,_p));
		gameID = 0;
	}
	public void setState(int state){
		currentState = state;
		appStates.get(currentState).init();
	}
	public void update(){
		appStates.get(currentState).update();
	}
	public void draw(java.awt.Graphics g){
		appStates.get(currentState).draw(g);
	}
	public void drawToScreen(Graphics2D g){
		appStates.get(currentState).drawToScreen(g);
	}
	public void keyPressed(int k){
		appStates.get(currentState).keyPressed(k);
	}
	public void keyReleased(int k){
		appStates.get(currentState).keyReleased(k);
	}

	
}
