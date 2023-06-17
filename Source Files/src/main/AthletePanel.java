package main;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * Graphical element that represents an {@link main.Athlete Athlete} as a panel
 * which displays information about the athlete. It also provides an interface
 * for buying and selling athletes, or changing their position and using items
 * on them.
 *
 * @author mdi48, bsm62
 * @version 1.03
 * @see main.Athlete
 *
 */
public class AthletePanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Athlete athlete;
	private AthletePanel athletePanel = this;
	private JButton moveButton, hereButton, buyButton, sellButton;
	private JToggleButton itemButton;
	private JTextField nameField;
	private JLabel typeLabel, offenceLabel, defenceLabel, staminaLabel;
	private JProgressBar offenceBar, defenceBar, staminaBar;

	/**
	 * Constructs panel for use in the {@link main.Club Club} and
	 * {@link main.MatchWindow MatchWindow} classes
	 *
	 * @param setAthlete determines the athlete which this panel will represent
	 */
	public AthletePanel(Athlete setAthlete) {
		athlete = setAthlete;
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setBackground(Color.black);
		athleteInfo();

		hereButton = new JButton("To here");
		hereButton.setBackground(Color.black);
		hereButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		hereButton.setForeground(new Color(0, 255, 0));
		hereButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Club.swapFunction(athletePanel);
			}
		});
		hereButton.setBounds(5, 125, 120, 20);
		hereButton.setVisible(false);
		add(hereButton);

		moveButton = new JButton("Move");
		moveButton.setBackground(Color.black);
		moveButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		moveButton.setForeground(new Color(0, 255, 0));
		moveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Club.setActive(athletePanel);
				Club.showHereButtons();
			}
		});
		moveButton.setBounds(5, 125, 120, 20);
		moveButton.setVisible(false);
		add(moveButton);

		itemButton = new JToggleButton("Use Item");
		itemButton.setBackground(Color.black);
		itemButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		itemButton.setForeground(new Color(0, 255, 0));
		itemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Club.unselectItemButtons(athletePanel);
				if (itemButton.isSelected()) {
					if (Player.getItems().size() > 0) {
						Club.setActive(athletePanel);
						Club.showItems();
					} else {
						new Notification("You don't own any items!", Club.getWindow().getX() + 250,
								Club.getWindow().getY() + 280);
						itemButton.setSelected(false);
					}
				} else {
					Club.hideItems();
				}
			}
		});
		itemButton.setBounds(5, 125, 120, 20);
		add(itemButton);
	}

	/**
	 * Constructs panel for use in the {@link main.MarketWindow MarketWindow} class.
	 *
	 * @param setAthlete determines the athlete which this panel will represent
	 * @param isStore    determines whether this panel will be in the player or
	 *                   market inventory
	 */
	public AthletePanel(Athlete setAthlete, boolean isStore) {
		athlete = setAthlete;
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setBackground(Color.black);
		athleteInfo();

		buyButton = new JButton("Buy: $" + athlete.getContractPrice());
		buyButton.setBackground(Color.black);
		buyButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		buyButton.setForeground(new Color(0, 255, 0));
		buyButton.setHorizontalAlignment(SwingConstants.LEADING);
		buyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Market.buyAthlete(athletePanel, athlete);
			}
		});
		buyButton.setBounds(5, 125, 120, 20);
		add(buyButton);

		sellButton = new JButton("Sell: $" + athlete.getBuyBackPrice());
		sellButton.setBackground(Color.black);
		sellButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		sellButton.setForeground(new Color(0, 255, 0));
		sellButton.setHorizontalAlignment(SwingConstants.LEADING);
		sellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Market.sellAthlete(athletePanel, athlete);
			}
		});
		sellButton.setBounds(5, 125, 120, 20);
		add(sellButton);

		if (isStore == true) {
			showBuyButton();
			nameField.setEditable(false);
		} else {
			showSellButton();
		}
	}

	/**
	 * Constructs a blank panel for either the {@link main.Club Club} or
	 * {@link main.MatchWindow MarketWindow} class. Used to represent an empty slot
	 * in the players' team.
	 */
	public AthletePanel() {
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setBackground(Color.black);
		setLayout(null);

		addNameField("Empty slot");
		nameField.setEditable(false);
		add(nameField);

		hereButton = new JButton("To here");
		hereButton.setBackground(Color.black);
		hereButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		hereButton.setForeground(new Color(0, 255, 0));
		hereButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Club.swapFunction(athletePanel);
			}
		});
		hereButton.setBounds(5, 125, 120, 20);
		hereButton.setVisible(false);
		add(hereButton);

	}

	/**
	 * Adds various graphical elements displaying information about an
	 * {@link main.Athlete Athlete} to the panel. Called by constructors.
	 */
	private void athleteInfo() {
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setBackground(Color.black);
		setLayout(null);

		addNameField(athlete.getName());
		add(nameField);

		typeLabel = new JLabel(athlete.getType());
		typeLabel.setBackground(Color.black);
		typeLabel.setForeground(new Color(0, 255, 0));
		typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		typeLabel.setBounds(5, 25, 125, 20);
		add(typeLabel);

		offenceLabel = new JLabel("Off: " + athlete.getOffence());
		offenceLabel.setForeground(new Color(0, 255, 0));
		offenceLabel.setBounds(20, 50, 100, 15);
		add(offenceLabel);

		defenceLabel = new JLabel("Def: " + athlete.getDefence());
		defenceLabel.setForeground(new Color(0, 255, 0));
		defenceLabel.setBounds(20, 70, 100, 15);
		add(defenceLabel);

		staminaLabel = new JLabel("Sta: " + athlete.getCurrentStamina() + "/" + athlete.getStamina());
		staminaLabel.setForeground(new Color(0, 255, 0));
		staminaLabel.setBounds(20, 90, 100, 15);
		add(staminaLabel);

		offenceBar = new JProgressBar();
		offenceBar.setForeground(new Color(0, 100, 0));
		offenceBar.setBackground(Color.black);
		offenceBar.setBounds(20, 50, 100, 15);
		offenceBar.setBorderPainted(false);
		offenceBar.setValue(athlete.getOffence());
		add(offenceBar);

		defenceBar = new JProgressBar();
		defenceBar.setForeground(new Color(0, 100, 0));
		defenceBar.setBackground(Color.black);
		defenceBar.setBounds(20, 70, 100, 15);
		defenceBar.setBorderPainted(false);
		defenceBar.setValue(athlete.getDefence());
		add(defenceBar);

		staminaBar = new JProgressBar();
		staminaBar.setForeground(new Color(0, 100, 0));
		staminaBar.setBackground(Color.black);
		staminaBar.setBounds(20, 90, 100, 15);
		staminaBar.setBorderPainted(false);
		staminaBar.setValue(athlete.getCurrentStamina());
		add(staminaBar);
	}

	/**
	 * Creates an editable text field to display/edit the {@link main.Athlete
	 * Athletes}' name. Called by constructors.
	 *
	 * @param string the default text to display
	 */
	private void addNameField(String string) {
		nameField = new JTextField(string);
		nameField.setBounds(5, 10, 115, 20);
		nameField.setBackground(Color.black);
		nameField.setForeground(new Color(0, 255, 0));
		nameField.setBorder(null);
		nameField.setFont(new Font("Tahoma", Font.BOLD, 11));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					setName();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	/**
	 * Updates {@link main.Athlete Athlete}' name to the text entered in the text
	 * field if it meets format requirements.
	 */
	public void setName() {
		if (nameField.getText().length() >= 3 && nameField.getText().length() <= 16
				&& nameField.getText().matches("[a-zA-Z ]+")) {
			athlete.setName(nameField.getText());
		} else {
			int x;
			int y;
			if (Club.getWindow() != null) {
				x = Club.getWindow().getX();
				y = Club.getWindow().getY();
			} else {
				x = MarketWindow.getWindow().getX();
				y = MarketWindow.getWindow().getY();
			}
			new Notification(
					"Invalid athlete name!" + "\n"
							+ "Name must be between 3 and 16 characters and contain no numbers or symbols.",
					x + 250, y + 280);
		}
	}

	/**
	 * Updates elements displaying {@link main.Athlete Athlete} statistics in order
	 * to reflect changes.
	 */
	public void updateStats() {
		offenceLabel.setText("Off: " + athlete.getOffence());
		defenceLabel.setText("Def: " + athlete.getDefence());
		staminaLabel.setText("Sta: " + athlete.getCurrentStamina() + "/" + athlete.getStamina());
		offenceBar.setValue(athlete.getOffence());
		defenceBar.setValue(athlete.getDefence());
		staminaBar.setValue(athlete.getCurrentStamina());
		this.repaint();
	}

	/**
	 * Shows the 'sell' button on the panel and hides other buttons.
	 */
	public void showSellButton() {
		nameField.setEditable(true);
		sellButton.setVisible(true);
		buyButton.setVisible(false);
	}

	/**
	 * Shows the 'buy' button on the panel and hides other buttons.
	 */
	public void showBuyButton() {
		nameField.setEditable(false);
		sellButton.setVisible(false);
		buyButton.setVisible(true);
	}

	/**
	 * Shows the 'here' button on the panel and hides other buttons. This button is
	 * used to select where to move an athlete to.
	 */
	public void showHereButton() {
		hereButton.setVisible(true);
		if (athlete != null) {
			moveButton.setVisible(false);
		}
	}

	/**
	 * Shows the 'move' button on the panel and hides other buttons. This button is
	 * used to select an athlete to move.
	 */
	public void showMoveButton() {
		hereButton.setVisible(false);
		if (athlete != null) {
			moveButton.setVisible(true);
			itemButton.setVisible(false);
		}
	}

	/**
	 * shows the 'item' button on the panel and hides other buttons. This button is
	 * used to apply items to athletes.
	 */
	public void showItemButton() {
		itemButton.setVisible(true);
		moveButton.setVisible(false);
	}

	/**
	 * Deactivates a toggled item button.
	 */
	public void unselectItemButton() {
		itemButton.setSelected(false);
	}

	public Athlete getAthlete() {
		return athlete;
	}
}
