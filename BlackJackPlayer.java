import java.util.*;

public class BlackJackPlayer {
	private String name;
	private int tokens;
	private int bet;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	/*constructor - initializes a player object
	 * @param: none
	 * @return: none
	 * gives player a name and the start number of tokens
	 */
	public BlackJackPlayer(String name){
		this.name = name;
		this.tokens = 10;
	}

	/*getHand - allows client class to access each player's hand
	 * @param: none
	 * @return: ArrayList<Card>
	 * returns the cards in the players hand
	 */
	public ArrayList<Card> getHand(){
		return this.hand;
	}
	
	/*getName - allows client class to access client name
	 * @param: none
	 * @return: String - the name of player
	 * returns the player's name
	 */
	public String getName(){
		return this.name;
	}
	
	/*getTokens - get access to the current number of tokens
	 * @param: none
	 * @return: int - number of tokens
	 * allows client class to access the number of tokens each player currently has
	 */
	public int getTokens(){
		int t = this.tokens;
		return t;
	}
	
	/*setTokens - set a new number of tokens for a player
	 * @param: int num - new number of tokens player should have
	 * @return: void
	 * allows client class to change the number of tokens each player has
	 */
	public void setTokens(int num){
		this.tokens = num;
	}
	
	/*setBet - sets player bet to a certain number
	 * @param: int bet - number of tokens player wants to bet
	 * @return: void
	 * allows each player to bet a number of tokens 
	 */
	public void setBet(int bet){
		this.bet = bet;
	}
	
	/*getBet- allows client to access bets of each player in order to subtract it from tokens if player loses
	 * @param: none
	 * @return: int
	 * returns the bet placed by the player
	 */
	public int getBet(){
		return this.bet;
	}
	
	/*print dealer - prints the dealers hand with the first card hidden
	 * @param: none
	 * @return: none
	 * uses for loop to print the dealers hand, leaving the first card hidden
	 */
	public void printDealer(){
		System.out.println("Dealer: \n[hidden]");
		for(int i = 1; i < hand.size(); i++){
			System.out.println("[" + hand.get(i) + "]\n");
		}
	}
	
	/*toStringDealer - allows client class to print dealer object to reveal cards
	 * @param: none
	 * @return: String - the String to be printed
	 * concatenates the desired information to be printed (prints same as toString, excpet dealer has no tokens)
	 */
	public String toStringDealer(){
		String str = "Name: " + this.name + "\n";
		for (int i = 0; i < hand.size(); i++){
			str += "[" + hand.get(i) + "]\n";
		}
		return str;
	}
	
	/*toString - allows client class to print player object as a String
	 * @param: none
	 * @return: String - the String to be printed
	 * concatenates the desired information to be printed
	 */
	public String toString(){
		String str = "Name: " + this.name + "\nTokens: " + this.tokens + "\n";
		for (int i = 0; i < hand.size(); i++){
			str += "[" + hand.get(i) + "]\n";
		}
		return str;
	}
}
