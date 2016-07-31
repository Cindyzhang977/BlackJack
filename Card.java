
public class Card {
	private int value;
	private String name;
	
	public Card(int value, String name){
		this.value = value;
		this.name = name;
	}
	
	/*getValue - allows client class to access the value of each card
	 * @param: none
	 * @return: int
	 * uses if/else statements to return the right value of each card (ace by default is 11)
	 */
	public int getValue(){
		for(int i = 2; i <= 10; i++){
			if(this.name.startsWith(i +"")){
				this.value = i;
				return this.value;
			}
		}
		if(this.name.startsWith("jack") || this.name.startsWith("queen") || this.name.startsWith("king")){
			this.value = 10;
			return this.value;
		}
		else{
			this.value = 11;
			return this.value;
		}
	}
	
	/*getName - allows client class to access the name of each card
	 * @param: none
	 * @return: String
	 * returns the name of a card
	 */
	public String getName(){
		return this.name;
	}
	
	/*toString - allows client class to print card objects as a String
	 * @param: none
	 * @return: String - the String to be printed
	 * returns the name of each card to be printed into a String
	 */
	public String toString(){
		return this.name;
	}
}
