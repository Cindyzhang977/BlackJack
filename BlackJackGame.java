import java.util.*;

/**
 * @author Cindy Zhang
 *
 */
public class BlackJackGame {
	static Scanner s = new Scanner (System.in);
	public static ArrayList<BlackJackPlayer> players = new ArrayList<BlackJackPlayer>();

	/*isInt - checks if user entry is an integer
	 * @param: String input - user input
	 * @return: boolean - whether or not input is an integer
	 * uses a for loop to check if user input is an integer
	 */
	public static boolean isInt(String input){
		for (int i = 1; i < 100; i++){
			if(input.equals(Integer.toString(i))){
				return true;
			}
		}
		return false;
	}

	/*deal - deals cards to each player and dealer and allows each player to place a bet
	 * @param: numPlayers - the number of players playing the game
	 * 			ArrayList<Card> playDeck - the playing deck of cards
	 * 			int startIndex - the index at which to start dealing
	 * @return: int - the index of the next card on top of the deck
	 * uses for loop to deal cards to each player and the dealer
	 */
	public static int deal(int numPlayers, ArrayList<Card> playDeck, int startIndex){
		//place bets if bet is less than the number of tokens player has
		for (int i = 1; i < players.size(); i++){
			boolean b = false;
			while(b == false){
				System.out.print(players.get(i).getName() + " Enter number of tokens to bet: ");
				String input = s.next();
				if (isInt(input) == true){
					int bet = Integer.parseInt(input);
					if (bet <= players.get(i).getTokens()){
						players.get(i).setBet(bet);
						b = true;	
					}
					else{
						System.out.println("You don't have than many tokens. Please bet realistically.");
					}
				}
				else{
					System.out.println("Invalid input, please try again.");
				}
			}
		}

		//deals two cards to the dealer (player 0) and to each player and adds that card to the hand of that player
		//prints out cards of dealer and one player at a time to allows them to play
		int index = startIndex;
		for(int i = 1; i < players.size(); i++){
			//deals cards to dealer
			ArrayList<Card> dealerCards = players.get(0).getHand();
			dealerCards.clear();
			Card d1 = playDeck.get(index);
			dealerCards.add(d1);
			index++;
			Card d2 = playDeck.get(index);
			dealerCards.add(d2);
			index++;
			//deals cards to player
			ArrayList<Card> playerCards = players.get(i).getHand();
			playerCards.clear();
			Card card1 = playDeck.get(index);
			playerCards.add(card1);
			index++;
			Card card2 = playDeck.get(index);
			playerCards.add(card2);
			index++;
			if( i != 0){
				play(playDeck, i, index, playerCards, false);
			}
		}
		return index;
	}

	/*play - plays a full round with a player
	 * @param:ArrayList<Card> playDeck - the deck of cards
	 * 			int player - the index of a specific player in players ArrayList
	 * 			int startIndex - the index at which to start dealing cards
	 * @return: void
	 * uses while loop to keep playing until player stays, gets 21, surrenders, or busts
	 */
	public static void play(ArrayList<Card> playDeck, int player, int startIndex, ArrayList<Card> playerCards, boolean bool){
		//prints the dealer's deck and player's deck and asks them what they want to do
		players.get(0).printDealer();
		BlackJackPlayer p = players.get(player);
		System.out.println(p);
		if(sum(player) == 21){
			checkWin(player, false);
		}
		else{
			boolean win = false;
			int index = startIndex;
			int move = 1;
			boolean ifDouble = false; //check if player doubled token
			boolean ifSplit = bool; //check if player split the deck
			while(win == false){
				if(move == 1 && p.getBet() * 2 > p.getTokens()){
					System.out.println("You do not have the option to split or doubleDown, because you don't have enough tokens.");
				}
				//if first two cards dealt are identical
				if(move == 1 && p.getHand().get(0).getName().charAt(0) == p.getHand().get(1).getName().charAt(0)
						&& p.getBet() * 2 <= p.getTokens() && ifSplit == false){
					System.out.println(p.getName() + ", do you want to hit, stay, doubleDown, split, or surrender? (Enter H, S, DD, Sp, or Sur)");
					String input = s.next();
					boolean b = false;
					while(b == false){
						if(input.equalsIgnoreCase("h")){
							hit(playDeck, index, player);
							index++;
							move++;
							if(sum(player) >= 21){
								win = checkWin(player, ifSplit);
							}
							else{
								System.out.println(p);
							}
							break;
						}
						else if(input.equalsIgnoreCase("s")){
							//stay, so dealer's turn; hit until greater than 17
							while(sum(0) <= 17 || sum(0) < sum(player)){
								hit(playDeck, index, 0);
								index++;
								sum(0);
							}
							win = checkWin(player, ifSplit);
							b = true;
						}
						else if(input.equalsIgnoreCase("sp")){
							ifSplit = true;
							split(player, players.get(player).getHand(), playDeck, index);
							win = true;
							break;
						}
						else if(input.equalsIgnoreCase("dd")){
							ifDouble = true;
							doubleDown(player, index, playDeck);
							index++;
							win = checkWin(player, ifSplit);
							move++;
							break;
						}
						else if(input.equalsIgnoreCase("sur")){
							System.out.println("---You have surrendered---");
							p.setTokens(p.getTokens() - p.getBet() / 2);
							System.out.println(p);
							System.out.println("--------------------------");
							win = true;
							break;
						}
						else{
							System.out.println("Invalid entry, please try again.");
							input = s.next();
						}
					}
				}

				//give the option to double down on first move
				else if(move == 1 && p.getBet() * 2 <= p.getTokens()){
					System.out.println(p.getName() + ", do you want to hit, stay, doubleDown, or surrender? (Enter H, S, DD, or Sur)");
					String input = s.next();
					boolean b = false;
					while(b == false){
						if(input.equalsIgnoreCase("h")){
							hit(playDeck, index, player);
							index++;
							move++;
							if(sum(player) >= 21){
								win = checkWin(player, ifSplit);
							}
							else{
								System.out.println(p);
							}
							break;
						}
						else if(input.equalsIgnoreCase("s")){
							//stay, so dealer's turn; hit until greater than 17
							while(sum(0) <= 17 || sum(0) < sum(player)){
								hit(playDeck, index, 0);
								index++;
								sum(0);
							}
							win = checkWin(player, ifSplit);
							b = true;
						}
						else if(input.equalsIgnoreCase("dd")){
							ifDouble = true;
							doubleDown(player, index, playDeck);
							index++;
							win = checkWin(player, ifSplit);
							move++;
							break;
						}
						else if(input.equalsIgnoreCase("sur")){
							System.out.println("---You have surrendered---");
							p.setTokens(p.getTokens() - p.getBet() / 2);
							System.out.println(players.get(0).toStringDealer());
							System.out.println(p);
							System.out.println("--------------------------");
							win = true;
							break;
						}
						else{
							System.out.println("Invalid entry, please try again.");
							input = s.next();
						}
					}
				}

				//first two cards are different and cannot double down, but can surrender
				else if(move == 1){
					System.out.println(p.getName() + ", do you want to hit, stay, or surrender? (Enter H, S, or Sur)");
					String input = s.next();
					boolean b = false;
					while(b == false){
						if(input.equalsIgnoreCase("h")){
							hit(playDeck, index, player);
							index++;
							move++;
							if(sum(player) >= 21){
								win = checkWin(player, ifSplit);
							}
							else{
								System.out.println(p);
							}
							break;
						}
						else if(input.equalsIgnoreCase("s")){
							//stay, so dealer's turn; hit until greater than 17
							while(sum(0) <= 17 || sum(0) < sum(player)){
								hit(playDeck, index, 0);
								index++;
								sum(0);
							}
							win = checkWin(player, ifSplit);
							b = true;
						}
						else if(input.equalsIgnoreCase("sur")){
							System.out.println("---You have surrendered---");
							p.setTokens(p.getTokens() - p.getBet() / 2);
							System.out.println(players.get(0).toStringDealer());
							System.out.println(p);
							System.out.println("--------------------------");
							win = true;
							break;
						}
						else{
							System.out.println("Invalid entry, please try again.");
							input = s.next();
						}
					}
				}
				
				//first two cards are different and cannot double down
				else{
					System.out.println(p.getName() + ", do you want to hit or stay? (Enter H or S)");
					String input = s.next();
					boolean b = false;
					while(b == false){
						if(input.equalsIgnoreCase("h")){
							hit(playDeck, index, player);
							index++;
							move++;
							if(sum(player) >= 21 || ifDouble == true){
								win = checkWin(player, ifSplit);
							}
							else{
								System.out.println(p);
							}
							break;
						}
						else if(input.equalsIgnoreCase("s")){
							//stay, so dealer's turn; hit until greater than 17
							while(sum(0) <= 17 || sum(0) < sum(player)){
								hit(playDeck, index, 0);
								index++;
								sum(0);
							}
							win = checkWin(player, ifSplit);
							b = true;
						}
						else{
							System.out.println("Invalid entry, please try again.");
							input = s.next();
						}
					}
				}
			}	
		}
	}

	/*hit - adds one card to the hand of the player
	 * @param: int startIndex - the index to start at for handing out a card in the deck ArrayList
	 * 			int player - a player in players ArrayList
	 * @return: void
	 * gives the top card in deck to the player
	 */
	public static void hit(ArrayList<Card> playDeck, int startIndex, int player){
		BlackJackPlayer p = players.get(player);
		int index = startIndex;
		Card c = playDeck.get(index);
		p.getHand().add(c);
	}

	/*doubleDown - allows player to double bet 
	 * @param: int player - a player in players ArrayList
	 * @return: void
	 * sets the player's bet to double what it was before
	 */
	public static void doubleDown(int player, int index, ArrayList<Card> playDeck){
		BlackJackPlayer p = players.get(player);
		int bet = p.getBet();
		p.setBet(bet * 2);
		System.out.println("Your bet is now " + (bet * 2));
		index++;
		hit(playDeck, index, player);
		while(sum(0) <= 17 || sum(0) < sum(player)){
			hit(playDeck, index, 0);
			index++;
			sum(0);
		}
	}

	/*split - allows player to split their hand, and place an equal bet on the other deck
	 * @param: int player - a player in players ArrayList
	 * 			int bet - the original bet placed by the player ???
	 * @return: void
	 * creates a 2nd Card ArrayList for the player's second hand
	 */
	public static void split(int player, ArrayList<Card> playerCards, ArrayList<Card> playDeck, int index){
		BlackJackPlayer p = players.get(player);
		p.setBet(2 * p.getBet());
		//add last card in original hand to a new ArrayList
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(playerCards.get(playerCards.size() - 1));
		//remove last card from original hand
		playerCards.remove(playerCards.size() - 1);
		System.out.println("Your hand has now been split, and a bet of " + p.getBet() / 2 + " tokens has also been placed on the second deck.\n");
		//play first round
		System.out.println("Play the first deck: ");
		playerCards.add(playDeck.get(index));
		play(playDeck, player, index, playerCards, true);
		playerCards.clear();
		players.get(0).getHand().clear();
		index+=10;
		//play second round
		System.out.println("Play the second deck: ");
		for(int i = 0; i < 2; i++){
			players.get(0).getHand().add(playDeck.get(index));
			index++;	
		}
		playerCards.add(hand2.get(0));
		playerCards.add(playDeck.get(index));
		index++;
		play(playDeck, player, index, playerCards, true);
	}

	/*sum - adds up the cards of a player
	 * @param: int player - a player in players ArrayList
	 * @return: int
	 * uses for loop and getValue() to add up cards, and uses if else statements to account for aces
	 */
	public static int sum(int player){
		ArrayList<Card> playerHand = players.get(player).getHand();
		int sum = 0;
		int ace = 0;
		for (int i = 0; i < playerHand.size(); i++){
			if(playerHand.get(i).getName().startsWith("ace")){
				ace++;
			}

			sum += playerHand.get(i).getValue();
		}
		if(sum > 21 && ace > 0){
			//make ace value = 1 instead of default 11
			while(sum > 21 && ace > 0){
				sum -= 10;	
				ace--;
			}
		}
		return sum;
	}

	/*checkWin - checks whether a player won
	 * @param: int player - player index of any player in players ArrayList
	 * @return: boolean
	 * checks if sum of cards are greater than 21, equal to 21, or (if < 21) compare to dealer's hand 
	 */
	public static boolean checkWin(int player, boolean ifSplit){
		BlackJackPlayer p = players.get(player);
		if(sum(player) == sum(0)){
			//player's cards = dealer's cards
			System.out.println("---It's a tie!---\n");
			System.out.println(players.get(0).toStringDealer() + "\n" + p);
			System.out.println("-----------------");
			return true;
		}
		if(ifSplit == true){
			if(sum(player) == 21){
				//player got blackjack
				System.out.println("--Congratulations! You got a BlackJack!--\n");
				p.setTokens(p.getTokens() + p.getBet() / 2 + 1);
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-----------------------------------------");
				return true;
			}
			else if(sum(player) < 21 && sum(0) < 21 && sum(player) > sum(0)){
				//check if player's cards are greater than dealer's cards
				System.out.println("---Congratulations! You won!---\n");
				p.setTokens(p.getTokens() + p.getBet() / 2);
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-------------------------------");
				return true;
			}
			else if (sum(player) > 21){
				//check if player busted
				System.out.println("---You busted!---\n");
				p.setTokens(p.getTokens() - p.getBet() / 2);
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-----------------");
				return true;
			}
			else if (sum(0) < 21 && sum(0) > sum(player)){
				//dealer cards are greater than player's cards
				System.out.println("---Oh no! You lost!---\n");
				p.setTokens(p.getTokens() - p.getBet() / 2);
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("----------------------");
				return true;
			}
			else if (sum(0) == 21 && sum(player) != 21){
				//check if dealer got a blackJack
				System.out.println("--Dealer got a BlackJack! You lost!--\n");
				p.setTokens(p.getTokens() - p.getBet() / 2 - 1);
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-------------------------------------");
				return true;
			}
			else if (sum(0) > 21 && sum(player) < 21){
				//check if dealer busted
				System.out.println("---Dealer busted! You won!---\n");
				p.setTokens(p.getTokens() + p.getBet() / 2);
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-----------------------------");
				return true;
			}

		}
		else{
			if(sum(player) == 21){
				//player got blackjack
				System.out.println("--Congratulations! You got a BlackJack!--\n");
				p.setTokens(p.getTokens() + p.getBet() + 1);
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-----------------------------------------");
				return true;
			}
			else if(sum(player) < 21 && sum(0) < 21 && sum(player) > sum(0)){
				//check if player's cards are greater than dealer's cards
				System.out.println("---Congratulations! You won!---\n");
				p.setTokens(p.getTokens() + p.getBet());
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-------------------------------");
				return true;
			}
			else if (sum(player) > 21){
				//check if player busted
				System.out.println("---You busted!---\n");
				p.setTokens(p.getTokens() - p.getBet());
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-----------------");
				return true;
			}
			else if (sum(0) < 21 && sum(0) > sum(player)){
				//dealer cards are greater than player's cards
				System.out.println("---Oh no! You lost!---\n");
				p.setTokens(p.getTokens() - p.getBet());
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("----------------------");
				return true;
			}
			else if (sum(0) == 21 && sum(player) != 21){
				//check if dealer got a blackJack
				System.out.println("--Dealer got a BlackJack! You lost!--\n");
				p.setTokens(p.getTokens() - p.getBet() - 1);
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-------------------------------------");
				return true;
			}
			else if (sum(0) > 21 && sum(player) < 21){
				//check if dealer busted
				System.out.println("---Dealer busted! You won!---\n");
				p.setTokens(p.getTokens() + p.getBet());
				System.out.println(players.get(0).toStringDealer() + "\n" + p);
				System.out.println("-----------------------------");
				return true;
			}	
		}
		return false;
	}

	public static void main(String[] args) {
		
		System.out.println("Welcome to BlackJack!");

		//create a new deck and an ArrayList to access the deck
		Deck d = new Deck();
		ArrayList<Card> playDeck = d.getDeck();

		//ask how many players there are and convert valid input to from String to integer
		boolean b = false;
		int numPlayers = 0;
		while (b == false){
			System.out.print("Enter number of players (up to 5): ");
			String input = s.nextLine();
			if(isInt(input) == true){
				numPlayers = Integer.parseInt(input);
				if(numPlayers <= 5){
					numPlayers = Integer.parseInt(input);
					b = true;
				}
				else{
					System.out.println("Invalid number of players, please try again.");
				}
			}
			else{
				System.out.println("Invalid entry, please try again.");
			}
		}

		//create a dealer as player 0
		BlackJackPlayer dealer = new BlackJackPlayer("Dealer");
		players.add(dealer);

		//for each player, create a new BlackJackPlayer object, enter a name, then print player
		if(numPlayers == 1){
			System.out.print("Enter player name: ");
			String name = s.nextLine();
			BlackJackPlayer p = new BlackJackPlayer(name);
			players.add(p);
			System.out.println(p);
		}
		else{
			for(int i = 1; i <= numPlayers; i++){
				System.out.print("Player " + i + " enter name: ");
				String name = s.nextLine();
				BlackJackPlayer p = new BlackJackPlayer(name);
				players.add(p);
				System.out.println(p);
			}	
		}
		boolean quit = false;
		while(quit == false){
			//deals cards at the beginning
			d.shuffle();
			//System.out.println(d);
			int startIndex = 0;
			deal(numPlayers, playDeck, startIndex);

			//if players in multi-player game still have tokens, ask if they want to play again
			if(players.size() > 2){
				int count = 0;
				ArrayList<Integer> outPlayers = new ArrayList<Integer>(); //store indexes of players that ran out of tokens
				for(int i = 1; i < players.size(); i++){
					if(players.get(i).getTokens() > 0){
						count++;
					}
					else{
						outPlayers.add(i);
					}
				}
				if(count == players.size() - 1){
					System.out.print("Do you guys want to play again? (Enter \"yes\" or \"no\")");
					String answer = s.next();
					while(!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")){
						System.out.println("Invalid answer, please try again.");
						answer = s.next();
					}
					if(answer.equalsIgnoreCase("no")){
						quit = true;
					}
					else{
						quit = false;
					}
				}	
				else if(count > 0){
					//print and remove all players that are out of tokens
					for(int i = 0; i < outPlayers.size(); i++){
						int outIndex = outPlayers.get(i);
						System.out.println(players.get(outIndex).getName() + " ran out of tokens and can no longer play.");
						players.remove(outIndex);
					}
					//ask remaining players if they still want to play
					for(int i = 1; i < players.size(); i++){
						System.out.print(players.get(i).getName() + ", ");
					}
					System.out.print("do you still want to play? (Enter \"yes\" or \"no\")");
					String answer = s.next();
					while(!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")){
						System.out.println("Invalid answer, please try again.");
						answer = s.next();
					}
					if(answer.equalsIgnoreCase("no")){
						quit = true;
					}
					else{
						quit = false;
					}
				}
			}
			//ask player in single player game if player wants to play again if he/she still has tokens remaining
			else if(players.size() == 2){
				if(players.get(1).getTokens() > 0){
					System.out.println(players.get(1).getName() + ", do you still want to play? (Enter \"yes\" or \"no\")");
					String answer = s.next();
					while(!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")){
						System.out.println("Invalid answer, please try again.");
						answer = s.next();
					}
					if(answer.equalsIgnoreCase("no")){
						quit = true;
					}
					else{
						quit = false;
					}
				}
				else{
					System.out.println("Sorry, you ran out of tokens and can no longer play.");
					quit = true;
				}
			}
		}
		System.out.println("See you next time!");
	}
}
