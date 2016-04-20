package at.weScrabble.interfaces;

import java.util.ArrayList;

import com.example.wescrabble.utils.MyLetter;

public interface JWeScrabble {
	
	public JWeScrabble registerATApp(ATWeScrabble weScrabble);

	public void showMessage(String message);
	
	public int getTeam();
	
	public int[] getLetterList();
	
	public void removeWord();
	
	public void addLetter(char letter);
	
	public void removeLetter(char letter);
	
	public void showPartner(int id, int[] letters);
	
	public void removePartnerLetter(int userId, char letter);
	
	public void addPartnerLetter(int userId, char letter);
	
	public void requestLetter(int userId, char letter);
	
	public void updatePartners();
	
	public void completeCheck();
	
	public void removePartner(int userId);
	
	public void looseGame();
	
	public void winGame();
	
}
