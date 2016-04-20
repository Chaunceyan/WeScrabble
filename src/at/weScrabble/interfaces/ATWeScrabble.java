package at.weScrabble.interfaces;

import edu.vub.at.objects.coercion.Async;

public interface ATWeScrabble {
	@Async
	void showMessage(String message);
	
	void ping(JWeScrabble gui);
	@Async
	void removePartnerLetter(char letter);
	@Async 
	void addPartnerLetter(char letter);
	@Async
	void requestLetter(int userId, char letter);
	@Async
	void rejectRequest(int userId);
	@Async
	void acceptRequest(int userId, Character obj);
	@Async
	void submitWord(String word);
	@Async
	void finishGame();

}
