//James Budday 800769601

public class Card implements Comparable<Card>{
	
	
	private int cardValue;
	private int valueWithoutSuit;
	
	public Card(int cv) {
		cardValue = cv;
		
		//normalize for card competitive value
		valueWithoutSuit = cardValue % 13;
	}
	
	public String getCardSymbol() {
		
		//get output for card suit
		//suits are 0 - 12, 13 - 25, 26 - 38, 39 - 51
		//           heart   diamond  club     spade
		if(cardValue <= 12) {
			return "♥";
		} else if(cardValue <= 25) {
			return "♦";
		} else if(cardValue <= 38) {
			return "♣";
		} else {
			return "♠";
		}
		
	}
	
	public String getCardNumber() {
		
		//get ouptput for card value
		//values are: 0  1  2  3  4  5  6  7 8   9  10 11 12
		//            2  3  4  5  6  7  8  9 10  J  Q  K  A
		if(valueWithoutSuit < 9) {
			return (valueWithoutSuit + 2) + ""; 
		} else {
			if(valueWithoutSuit == 9) {
				return "J";
			} else if(valueWithoutSuit == 10) {
				return "Q";
			} else if(valueWithoutSuit == 11) {
				return "K";
			} else {
				return "A";
			}
		}
	}
	
	public String getCardInfo() {
		
		String returnString = "";
		
		//get ouptput for card value
		//values are: 0  1  2  3  4  5  6  7 8   9  10 11 12
		//            2  3  4  5  6  7  8  9 10  J  Q  K  A
		if(valueWithoutSuit < 9) {
			returnString += valueWithoutSuit + 2; 
		} else {
			if(valueWithoutSuit == 9) {
				returnString += "Jack";
			} else if(valueWithoutSuit == 10) {
				returnString += "Queen";
			} else if(valueWithoutSuit == 11) {
				returnString += "King";
			} else if(valueWithoutSuit == 12) {
				returnString += "Ace";
			}
		}
		
		returnString += " of ";
		
		//get output for card suit
		//suits are 0 - 12, 13 - 25, 26 - 38, 39 - 51
		//           heart   diamond  club     spade
		if(cardValue <= 12) {
			returnString += "hearts";
		} else if(cardValue <= 25) {
			returnString += "diamonds";
		} else if(cardValue <= 38) {
			returnString += "clubs";
		} else if(cardValue <= 51) {
			returnString += "spades";
		}
		
		return returnString;
	}
	
	public int getValue() {
		return cardValue;
	}
	
	public int getValueWithoutSuit() {
		return valueWithoutSuit;
	}



	@Override
	public int compareTo(Card o) {
		return Integer.compare(this.valueWithoutSuit, o.valueWithoutSuit);
	}
}
