package context;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class CardController {
	
	private JPanel container; // The panel that holds the cards
	private CardLayout cardLayout; // The CardLayout managing the cards
	private Map<String, JPanel> mapCards = new HashMap<>();

	public CardController(JPanel container) {
		this.container = container;
		this.cardLayout = (CardLayout) container.getLayout(); // Cast to CardLayout
	}

	// Method to show a specific card by name
	public void showCard(String cardName) {
		cardLayout.show(container, cardName);
		mapCards.get(cardName).revalidate();
		mapCards.get(cardName).repaint();
	}

	// Method to go to the next card
	public void nextCard() {
		cardLayout.next(container);
	}

	// Method to go to the previous card
	public void previousCard() {
		cardLayout.previous(container);
	}

	// Method to add a new card dynamically
	public void addCard(JPanel panel, String cardName) {
		container.add(panel, cardName);
		mapCards.put(cardName, panel);
	}
}
