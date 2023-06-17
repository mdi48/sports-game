package main;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * GUI for the {@link main.Market Market} class. It displays {@link main.Athlete
 * Athletes} and {@link main.Item Items} available for purchase by the
 * {@link main.Player Player} as well as the Player's own items and athletes
 * which can be sold back to the Market.
 * 
 * @author bsm62
 * @version 1.03
 **/
public class MarketWindow {
	private static JFrame marketWindow;
	private static ItemContainer playerItemPanel, storeItemPanel;
	private static AthleteContainer playerAthletePanel, storeAthletePanel;
	private JScrollPane playerScroller, storeScroller;
	private static JLabel moneyLabel, athleteCountLabel;
	private JButton athletesButton, homeButton, itemsButton;

	/**
	 * Constructor that creates the window and adds all of the graphical elements.
	 * By default this displays the {@link main.Athlete Athletes} available for
	 * purchase as well as those in the {@link main.Player Players}'
	 * {@link main.Team Team}. This can be toggled to show {@link main.Item Items}
	 * available for purchase and in the players' inventory.
	 * 
	 * @param x used to inherit the horizontal position of the previous window
	 * @param y used to inherit the vertical position of the previous window
	 */
	public MarketWindow(int x, int y) {
		marketWindow = new JFrame();
		marketWindow.getContentPane().setBackground(new Color(0, 0, 0));
		marketWindow.setTitle("Market");
		marketWindow.setResizable(false);
		marketWindow.setBounds(x, y, 900, 600);
		marketWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marketWindow.getContentPane().setLayout(null);

		playerAthletePanel = new AthleteContainer();
		storeAthletePanel = new AthleteContainer(Market.getAthletes());
		playerItemPanel = new ItemContainer(false);
		playerItemPanel.updateItems();
		storeItemPanel = new ItemContainer(Market.getItems());
		storeItemPanel.updateItems();

		playerScroller = new JScrollPane();
		playerScroller.setBorder(new LineBorder(new Color(0, 255, 0)));
		playerScroller.getHorizontalScrollBar().setBackground(Color.BLACK);
		playerScroller.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(0, 200, 0);
			}
		});
		playerScroller.setBounds(10, 10, 860, 185);
		playerScroller.getHorizontalScrollBar().setUnitIncrement(16);
		playerScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		playerScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		playerScroller.setViewportView(playerAthletePanel);
		marketWindow.getContentPane().add(playerScroller);

		storeScroller = new JScrollPane();
		storeScroller.setBorder(new LineBorder(new Color(0, 255, 0)));
		storeScroller.getHorizontalScrollBar().setBackground(Color.BLACK);
		storeScroller.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(0, 200, 0);
			}
		});
		storeScroller.setBounds(10, 260, 860, 185);
		storeScroller.getHorizontalScrollBar().setUnitIncrement(16);
		storeScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		storeScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		storeScroller.setViewportView(storeAthletePanel);
		marketWindow.getContentPane().add(storeScroller);

		moneyLabel = new JLabel("Money: $" + Player.getMoney());
		moneyLabel.setForeground(new Color(0, 255, 0));
		moneyLabel.setBounds(350, 215, 200, 30);
		moneyLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		marketWindow.getContentPane().add(moneyLabel);

		athletesButton = new JButton("Buy Athletes");
		athletesButton.setBackground(Color.black);
		athletesButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		athletesButton.setForeground(new Color(0, 255, 0));
		athletesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerScroller.setViewportView(playerAthletePanel);
				storeScroller.setViewportView(storeAthletePanel);
			}
		});
		athletesButton.setBounds(75, 490, 150, 40);
		marketWindow.getContentPane().add(athletesButton);

		homeButton = new JButton("Return to Hub");
		homeButton.setBackground(Color.black);
		homeButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		homeButton.setForeground(new Color(0, 255, 0));
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				marketWindow.dispose();
				new Hub(marketWindow.getX() + 150, marketWindow.getY() + 100);
			}
		});
		homeButton.setBounds(375, 490, 150, 40);
		marketWindow.getContentPane().add(homeButton);

		itemsButton = new JButton("Buy Items");
		itemsButton.setBackground(Color.black);
		itemsButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		itemsButton.setForeground(new Color(0, 255, 0));
		itemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerScroller.setViewportView(playerItemPanel);
				storeScroller.setViewportView(storeItemPanel);
			}
		});
		itemsButton.setBounds(675, 490, 150, 40);
		marketWindow.getContentPane().add(itemsButton);

		marketWindow.setVisible(true);
		homeButton.requestFocusInWindow();
	}

	/**
	 * Constructor that creates the window and adds all of the graphical elements.
	 * By default this displays the {@link main.Athlete Athletes} available for
	 * purchase as well as those in the {@link main.Player Players}'
	 * {@link main.Team Team}. This can be toggled to show {@link main.Item Items}
	 * available for purchase and in the players' inventory.
	 */
	public static void updateAthletePanels() {
		int position = 0;
		playerAthletePanel.removeAll();
		for (AthletePanel panel : playerAthletePanel.getPanels()) {
			panel.setBounds(10 + 140 * position, 10, 130, 150);
			playerAthletePanel.add(panel);
			position++;
		}
		position = 0;
		storeAthletePanel.removeAll();
		storeAthletePanel.setPreferredSize(new Dimension(storeAthletePanel.getAthletes().size() * 140 + 10, 170));
		for (AthletePanel panel : storeAthletePanel.getPanels()) {
			panel.setBounds(10 + 140 * position, 10, 130, 150);
			storeAthletePanel.add(panel);
			position++;
		}
		moneyLabel.setText("Money: $" + Player.getMoney());
		athleteCountLabel.setText(Player.getTeam().athleteCount() + "/" + Player.getTeamSize() + " required Athletes");
		marketWindow.repaint();
	}

	/**
	 * Constructor that creates the window and adds all of the graphical elements.
	 * By default this displays the {@link main.Athlete Athletes} available for
	 * purchase as well as those in the {@link main.Player Players}'
	 * {@link main.Team Team}. This can be toggled to show {@link main.Item Items}
	 * available for purchase and in the players' inventory.
	 */
	public static void updateItemPanels() {
		for (ItemPanel panel : playerItemPanel.getPanels()) {
			panel.updatePlayerCount();
		}
		for (ItemPanel panel : storeItemPanel.getPanels()) {
			panel.updateStoreCount();
		}
		moneyLabel.setText("Money: $" + Player.getMoney());
		marketWindow.repaint();
	}

	/**
	 * Used to toggle the MarketWindow into 'setup' mode for creating the
	 * {@link main.Player Players}' {@link main.Team Team} during setup. In this
	 * mode the option to buy {@link main.Item Items} is hidden, and more
	 * {@link main.Athlete Athletes} are available for purchase than normal. The
	 * athletes also have their buyback prices set to match their purchase price,
	 * allowing buying and selling without net loss of money.
	 */
	public void setupMode() {
		athletesButton.setVisible(false);
		itemsButton.setVisible(false);
		marketWindow.setTitle("Team Picker");
		athleteCountLabel = new JLabel(
				Player.getTeam().athleteCount() + "/" + Player.getTeamSize() + " required Athletes");
		athleteCountLabel.setBounds(350, 455, 200, 30);
		athleteCountLabel.setForeground(new Color(0, 255, 0));
		athleteCountLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		athleteCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		marketWindow.getContentPane().add(athleteCountLabel);
		homeButton.setText("Continue");
		homeButton.removeActionListener(homeButton.getActionListeners()[0]);
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Player.getTeam().athleteCount() >= Player.getTeamSize()) {
					marketWindow.dispose();
					Market.athleteBuyBack();
					new Hub(marketWindow.getX() + 150, marketWindow.getY() + 100);
				} else {
					new Notification("You do not have enough athletes to start!", marketWindow.getX() + 250,
							marketWindow.getY() + 245);
				}
			}
		});
	}

	public static JFrame getWindow() {
		return marketWindow;
	}

	public static ItemContainer getPlayerItemPanel() {
		return playerItemPanel;
	}

	public static ItemContainer getStoreItemPanel() {
		return storeItemPanel;
	}

	public static AthleteContainer getPlayerAthletePanel() {
		return playerAthletePanel;
	}

	public static AthleteContainer getStoreAthletePanel() {
		return storeAthletePanel;
	}

}
