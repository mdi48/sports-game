package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

/**
 * GUI for of viewing and organizing the {@link main.Player Players}'
 * {@link main.Athlete Athletes} into different positions for the matches.
 * Athletes should ideally be put into positions matching their type, but this
 * is not required. Also allows {@link main.Item Items} to be viewed and used on
 * Athletes.
 *
 * @author mdi48, bsm62
 * @version 1.09
 **/
public class Club extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The athlete panel that is currently selected to be moved, or have an item
	 * used on it.
	 */
	public static AthletePanel activePanel;
	private static ArrayList<AthletePanel> panelList;
	private static JFrame clubWindow;
	private static JScrollPane itemScroller, teamScroller;
	private ItemContainer itemPicker;
	private static JPanel internalFrame;
	private static JButton itemButton;

	/**
	 * Constructor that creates the window and adds all of the graphical elements.
	 * Displays all of the available {@link main.Team Team} positions, including
	 * reserves, and the {@link main.Athlete Athletes} (if any) in those positions.
	 *
	 * @param x used to inherit the horizontal position of the previous window
	 * @param y used to inherit the vertical position of the previous window
	 */
	public Club(int x, int y) {
		clubWindow = new JFrame();
		clubWindow.getContentPane().setBackground(new Color(0, 0, 0));
		clubWindow.setTitle("Club");
		clubWindow.setResizable(false);
		clubWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clubWindow.setBounds(x, y, 900, 600);
		clubWindow.getContentPane().setLayout(null);

		internalFrame = new JPanel();
		internalFrame.setPreferredSize(new Dimension(780, 860));
		internalFrame.setBackground(Color.black);
		internalFrame.setLayout(null);

		itemPicker = new ItemContainer(true);
		itemPicker.updateItems();
		itemScroller = new JScrollPane();
		itemScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		itemScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		itemScroller.getHorizontalScrollBar().setBackground(Color.BLACK);
		itemScroller.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(0, 200, 0);
			}
		});
		itemScroller.setViewportView(itemPicker);
		itemScroller.setBounds(10, 315, 860, 185);
		itemScroller.getHorizontalScrollBar().setUnitIncrement(16);
		itemScroller.setBorder(new LineBorder(new Color(0, 255, 0)));
		itemScroller.setVisible(false);
		clubWindow.getContentPane().add(itemScroller);

		JPanel attackerBorder = new JPanel();
		attackerBorder.setBorder(new LineBorder(new Color(0, 255, 0)));
		attackerBorder.setBounds(35, 10, 770, 210);
		attackerBorder.setOpaque(false);
		internalFrame.add(attackerBorder);

		JPanel allRounderBorder = new JPanel();
		allRounderBorder.setOpaque(false);
		allRounderBorder.setBorder(new LineBorder(new Color(0, 255, 0)));
		allRounderBorder.setBounds(35, 220, 770, 210);
		internalFrame.add(allRounderBorder);

		JPanel defenderBorder = new JPanel();
		defenderBorder.setOpaque(false);
		defenderBorder.setBorder(new LineBorder(new Color(0, 255, 0)));
		defenderBorder.setBounds(35, 430, 770, 210);
		internalFrame.add(defenderBorder);

		JPanel reservesBorder = new JPanel();
		reservesBorder.setOpaque(false);
		reservesBorder.setBorder(new LineBorder(new Color(0, 255, 0)));
		reservesBorder.setBounds(35, 640, 770, 210);
		internalFrame.add(reservesBorder);

		JLabel attackerLabel = new JLabel("ATTACKERS");
		attackerLabel.setForeground(new Color(0, 255, 0));
		attackerLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		attackerLabel.setBounds(325, 15, 200, 30);
		attackerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		internalFrame.add(attackerLabel);

		JLabel allRoundersLabel = new JLabel("ALL-ROUNDERS");
		allRoundersLabel.setForeground(new Color(0, 255, 0));
		allRoundersLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		allRoundersLabel.setBounds(325, 225, 200, 30);
		allRoundersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		internalFrame.add(allRoundersLabel);

		JLabel defendersLabel = new JLabel("DEFENDERS");
		defendersLabel.setForeground(new Color(0, 255, 0));
		defendersLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		defendersLabel.setBounds(325, 435, 200, 30);
		defendersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		internalFrame.add(defendersLabel);

		JLabel reserveLabel = new JLabel("RESERVES");
		reserveLabel.setForeground(new Color(0, 200, 0));
		reserveLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		reserveLabel.setBounds(325, 645, 200, 30);
		reserveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		internalFrame.add(reserveLabel);

		JToggleButton moveToggleButton = new JToggleButton("Move Athletes");
		moveToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (moveToggleButton.isSelected()) {
					showMoveButtons();
					unselectItemButtons(null);
					hideItems();
				} else {
					showItemButtons();
				}
			}
		});
		moveToggleButton.setBounds(110, 510, 140, 40);
		moveToggleButton.setBackground(Color.black);
		moveToggleButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		moveToggleButton.setForeground(new Color(0, 255, 0));
		clubWindow.getContentPane().add(moveToggleButton);

		itemButton = new JButton("Close Items");
		itemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideItems();
			}
		});
		itemButton.setBounds(370, 510, 140, 40);
		itemButton.setBackground(Color.black);
		itemButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		itemButton.setForeground(new Color(0, 255, 0));
		clubWindow.getContentPane().add(itemButton);
		itemButton.setVisible(false);

		JButton homeButton = new JButton("Return to Hub");
		homeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clubWindow.dispose();
				new Hub(clubWindow.getX() + 150, clubWindow.getY() + 100);
			}
		});
		homeButton.setBounds(630, 510, 140, 40);
		homeButton.setBackground(Color.black);
		homeButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		homeButton.setForeground(new Color(0, 255, 0));
		clubWindow.getContentPane().add(homeButton);

		panelList = new ArrayList<AthletePanel>();
		addPanels(Player.getTeam().getAttackers(), 50);
		addPanels(Player.getTeam().getAllRounders(), 260);
		addPanels(Player.getTeam().getDefenders(), 470);
		addPanels(Player.getTeam().getReserves(), 680);

		teamScroller = new JScrollPane();
		teamScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		teamScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		teamScroller.getVerticalScrollBar().setUnitIncrement(16);
		teamScroller.getVerticalScrollBar().setBackground(Color.BLACK);
		teamScroller.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = new Color(0, 200, 0);
			}
		});
		teamScroller.setViewportView(internalFrame);
		teamScroller.setBounds(10, 10, 860, 490);
		teamScroller.setBorder(new LineBorder(new Color(0, 255, 0)));
		teamScroller.setVisible(true);
		clubWindow.getContentPane().add(teamScroller);

		clubWindow.setVisible(true);
		clubWindow.requestFocusInWindow();
	}

	/**
	 * Used to create dynamically sized rows of {@link main.AthletePanel
	 * AthletePanels}.
	 *
	 * @param list      the list of athletes to create panels for
	 * @param rowHeight used to determine the vertical height of the row in the
	 *                  window
	 */
	public void addPanels(ArrayList<Athlete> list, int rowHeight) {
		int offset = ((5 - list.size()) * 75) + 55;
		AthletePanel panel;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				panel = new AthletePanel();
				panelList.add(panel);
			} else {
				panel = new AthletePanel(list.get(i));
				panelList.add(panel);
			}
			panel.setBounds(offset + 150 * i, rowHeight, 130, 150);
			internalFrame.add(panel);
		}
	}

	/**
	 * Swaps the active {@link main.AthletePanel AthletePanel} with the provided
	 * AthletePanel
	 *
	 * @param AthletePanel the panel to be swapped with
	 */
	public static void swapFunction(AthletePanel AthletePanel) {
		Rectangle temp = activePanel.getBounds();
		activePanel.setBounds(AthletePanel.getBounds());
		AthletePanel.setBounds(temp);
		internalFrame.remove(AthletePanel);
		internalFrame.add(AthletePanel);
		internalFrame.remove(activePanel);
		internalFrame.add(activePanel);
		internalFrame.repaint();
		Collections.swap(panelList, panelList.indexOf(AthletePanel), panelList.indexOf(activePanel));
		Player.getTeam().swapAthletes(panelList.indexOf(AthletePanel), panelList.indexOf(activePanel));
		showMoveButtons();
	}

	/**
	 * Shows the players' {@link main.ItemContainer ItemContainer} and resizes the
	 * athlete window.
	 */
	public static void showItems() {
		teamScroller.setBounds(10, 10, 860, 295);
		itemScroller.setVisible(true);
		itemButton.setVisible(true);
		clubWindow.getContentPane().revalidate();
	}

	/**
	 * Hides the players' {@link main.ItemContainer ItemContainer} and resizes the
	 * athlete window.
	 */
	public static void hideItems() {
		teamScroller.setBounds(10, 10, 860, 490);
		itemScroller.setVisible(false);
		itemButton.setVisible(false);
		clubWindow.getContentPane().revalidate();
		if (activePanel != null) {
			activePanel.unselectItemButton();
		}
	}

	public static JFrame getWindow() {
		return clubWindow;
	}

	/**
	 * Shows the item buttons on all of the {@link main.AthletePanel AthletePanels}.
	 * These buttons are for using items on the Athletes.
	 */
	public void showItemButtons() {
		for (AthletePanel panel : panelList) {
			if (panel.getAthlete() != null) {
				panel.showItemButton();
			}
		}
	}

	/**
	 * Toggles the item buttons off on all {@link main.AthletePanel AthletePanels}
	 * except the provided one.
	 *
	 * @param exception the AthletePanel to be ignored.
	 */
	public static void unselectItemButtons(AthletePanel exception) {
		for (AthletePanel panel : panelList) {
			if (panel.getAthlete() != null & panel != exception) {
				panel.unselectItemButton();
			}
		}
	}

	/**
	 * Shows the here buttons on all of the {@link main.AthletePanel AthletePanels}.
	 * These buttons are used to select a destination for an AthletePanel that is
	 * being moved.
	 */
	public static void showHereButtons() {
		for (AthletePanel panel : panelList) {
			panel.showHereButton();
		}
	}

	/**
	 * Shows the move buttons on all of the {@link main.AthletePanel AthletePanels}.
	 * These buttons are used to select an AthletePanel to move.
	 */
	public static void showMoveButtons() {
		for (AthletePanel panel : panelList) {
			panel.showMoveButton();
		}
	}

	public static AthletePanel getActivePanel() {
		return activePanel;
	}

	public static void setActive(AthletePanel AthletePanel) {
		activePanel = AthletePanel;
	}
}
