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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import component.LoginPanel;
import component.MainPanel;
import context.MediatorCardController;

public class JFrameMain extends JFrame {
	
    private static Logger logger = LogManager.getLogger(JFrameMain.class);

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
				logger.info("Application started!");
			} catch (Exception e) {
				logger.error("Failed to startup application", e);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrameMain() {
		setBackground(new Color(243, 202, 82));
		setResizable(false);
		setTitle("Leave Management Application");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;


		int frameWidth = (int) (screenWidth * 0.7);
		int frameHeight = (int) (screenHeight * 0.7);
		setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);

		final var contentPaneCard = new JPanel();
		contentPaneCard.setBackground(new Color(243, 202, 82));
		contentPaneCard.setLayout(new CardLayout(0, 0));
		setContentPane(contentPaneCard);
		
		
		final var frameCardController = new MediatorCardController(contentPaneCard);
		
		panelLogin = new LoginPanel(frameCardController);
		frameCardController.addCardLazy(panelLogin, "Login");
		panelMain = new MainPanel(frameCardController);
		frameCardController.addCardLazy(panelMain, "Main");
		
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
