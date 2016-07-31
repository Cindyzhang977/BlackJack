import java.util.*;

public class Deck {
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	/*constructor - initializes deck object
	 * @param: none
	 * @return: none
	 * uses for loops to add all cards as Card objects in ArrayList<Card> deck and assign each card a value
	 */
	public Deck(){
		Card c;
		String s = " of spades";
		String aceS = "ace" + s;
		c = new Card(1, aceS);
		deck.add(c);
		for (int i = 2; i <= 13; i++){
			String spades = null;
			if(i >= 2 && i <= 10){
				spades = i + s;	
				c = new Card(i, spades);
			}
			else if (i == 11){
				spades = "jack" + s;
				c = new Card(10, spades);
			}
			else if (i == 12){
				spades = "queen" + s;
				c = new Card(10, spades);
			}
			else if (i == 13){
				spades = "king" + s;
				c = new Card(10, spades);
			}
			deck.add(c);
		}
		
		String h = " of hearts";
		String aceH = "ace " + h;
		c = new Card (1, aceH);
		deck.add(c);
		for (int i = 2; i <= 13; i++){
			String hearts = null;
			if(i >= 2 && i <= 10){
				hearts = i + h;	
				c = new Card (i, hearts);
			}
			else if (i == 11){
				hearts = "jack" + h;
				c = new Card (10, hearts);
			}
			else if (i == 12){
				hearts = "queen" + h;
				c = new Card (10, hearts);
			}
			else if (i == 13){
				hearts = "king" + h;
				c = new Card (10, hearts);
			}
			deck.add(c);
		}
		
		String d = "  of diamonds";
		String aceD = "ace" + d;
		c = new Card(1, aceD);
		deck.add(c);
		for (int i = 2; i <= 13; i++){
			String diamonds = null;
			if(i >= 2 && i <= 10){
				diamonds = i + d;	
				c = new Card(i, diamonds);
			}
			else if (i == 11){
				diamonds = "jack" + d;
				c = new Card(10, diamonds);
			}
			else if (i == 12){
				diamonds = "queen" + d;
				c = new Card(10, diamonds);
			}
			else if (i == 13){
				diamonds = "king" + d;
				c = new Card(10, diamonds);
			}
			deck.add(c);
		}
		
		String cl = " of clovers";
		String aceC = "ace" + cl;
		String clovers = null;
		c = new Card(1, aceC);
		deck.add(c);
		for (int i = 2; i <= 13; i++){
			clovers = i + cl;
			if(i >= 2 && i <= 10){
				clovers = i + cl;
				c = new Card(i, clovers);
			}
			else if (i == 11){
				clovers = "jack" + cl;
				c = new Card(10, clovers);
			}
			else if (i == 12){
				clovers = "queen" + cl;
				c = new Card(10, clovers);
			}
			else if (i == 13){
				clovers = "king" + cl;
				c = new Card(10, clovers);
			}
			deck.add(c);
		}
	}
	
	/*getDeck - allows client class to access the cards in the deck 
	 * @param: none
	 * @return: ArrayList<Card> - the ArrayList of Cards
	 * returns the Deck object as an ArrayList
	 */
	public ArrayList<Card> getDeck(){
		return this.deck;
	}
	
	/*shuffle - shuffles the deck of cards for the black jack game (3x for max randomness)
	 * @param: none
	 * @return: void
	 * uses Math.random() to generate a random index, and switch start element with the element in the generated index
	 */
	public void shuffle(){
		for (int n = 0; n < 3; n++){
			for (int i = 0; i < deck.size(); i++){
				int moveIndex = (int)(Math.random() * deck.size());
				Card cardToReplace = deck.get(moveIndex);
				deck.set(moveIndex, deck.get(i));
				deck.set(i, cardToReplace);
			}	
		}
	}
	
	/*toString - allows client class to print deck object as a String
	 * @param: none
	 * @return: String - the String to be printed
	 * uses for loop to concatenate all elements of ArratList into a String
	 */
	public String toString(){
		String str = "";
		for(int i = 0; i < deck.size(); i++){
			str += deck.get(i) + "\n";
		}
		return str;
	}
}
