package context;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class CardController {

	private JPanel container;
	private CardLayout cardLayout;
	private Map<String, JPanel> mapCards = new HashMap<>();

	public CardController(JPanel container) {
		this.container = container;
		this.cardLayout = (CardLayout) container.getLayout();
	}

	public void showCard(String cardName) {
		cardLayout.show(container, cardName);
		mapCards.get(cardName).revalidate();
		mapCards.get(cardName).repaint();
	}

	public void nextCard() {
		cardLayout.next(container);
	}

	public void previousCard() {
		cardLayout.previous(container);
	}

	public void addCard(JPanel panel, String cardName) {
		container.add(panel, cardName);
		mapCards.put(cardName, panel);
	}
}
