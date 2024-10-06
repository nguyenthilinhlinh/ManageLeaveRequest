package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import component.LoginPanel;
import component.MainPanel;
import context.CardController;

public class JFrameMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private final LoginPanel panelLogin;
	private final MainPanel panelMain;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(() -> {
			try {
				JFrameMain frame = new JFrameMain();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrameMain() {
		setBackground(new Color(243, 202, 82));
		setTitle("Leave Management Application");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		// Tính toán kích thước hợp lý, có thể là 70% chiều rộng và 70% chiều cao của
		// màn hình
		int frameWidth = (int) (screenWidth * 0.8);
		int frameHeight = (int) (screenHeight * 0.8);

		// Thiết lập kích thước và vị trí cho JFrame
		setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);

		final var contentPaneCard = new JPanel();
		contentPaneCard.setBackground(new Color(243, 202, 82));
		contentPaneCard.setPreferredSize(new Dimension(800, 600));
		contentPaneCard.setLayout(new CardLayout(0, 0));
		setContentPane(contentPaneCard);
		
		final var frameCardController = new CardController(contentPaneCard);
		
		panelLogin = new LoginPanel(frameCardController, "Main");
		frameCardController.addCard(panelLogin, "Login");
		panelMain = new MainPanel(frameCardController);
		frameCardController.addCard(panelMain, "Main");
		
		ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/a.png")));
		setIconImage(icon.getImage());
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				panelLogin.setFrameSize(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
		});
	}
}
