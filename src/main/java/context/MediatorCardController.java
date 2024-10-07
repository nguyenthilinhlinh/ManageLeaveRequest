package context;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JPanel;

public class MediatorCardController {

	private final JPanel container;
	private final CardLayout cardLayout;
	private final Map<String, MediatorColleague> lazyCardMap = new HashMap<>();

	public MediatorCardController(JPanel container) {
		this.container = container;
		this.cardLayout = (CardLayout) container.getLayout();
	}

	public void addCardLazy(MediatorColleague panel, String cardName) {
		lazyCardMap.put(cardName, panel);
		container.add((Component) panel, cardName);
		lazyCardMap.put(cardName, panel);
	}

	public void showCard(String cardName) {
		Optional.ofNullable(lazyCardMap.get(cardName)).ifPresent(MediatorColleague::onNotify);
		cardLayout.show(container, cardName);
		Optional.ofNullable(lazyCardMap.get(cardName)).map(t -> (Component) t).ifPresent(t -> {
			t.revalidate();
			t.repaint();
		});
	}

	public void nextCard() {
		cardLayout.next(container);
	}

	public void previousCard() {
		cardLayout.previous(container);
	}

}
