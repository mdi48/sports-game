package main;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * Graphical element that represents an {@link main.Item Item} as panels in the
 * {@link main.Market Market} and {@link main.Club Club}. Shows the current
 * quantity in either the market or {@link main.Player Player} inventory, and
 * displays the items' properties. Also provides and interface to buy, sell, or
 * use the item.
 * 
 * @author bsm62
 * @version 1.03
 **/
public class ItemPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean storeItem;
	private JLabel numberLabel, nameLabel;
	private ItemContainer parentWindow;
	private Item item;

	/**
	 * Constructs panel for use in the {@link main.MarketWindow MarketWindow} class.
	 * 
	 * @param setItem determines the item which this panel will represent
	 * @param isStore determines whether this panel will be in the player or market
	 *                inventory
	 */
	public ItemPanel(Item setItem, boolean isStore) {
		storeItem = isStore;
		item = setItem;
		if (storeItem == true) {
			numberLabel = new JLabel("Quantity: " + item.getStoreCount());
			JButton buyButton = new JButton("Buy: $" + item.getContractPrice());
			buyButton.setBackground(Color.black);
			buyButton.setBorder(new LineBorder(new Color(0, 255, 0)));
			buyButton.setForeground(new Color(0, 255, 0));
			buyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Market.buyItem(item);
				}
			});
			buyButton.setBounds(5, 125, 140, 20);
			buyButton.setHorizontalAlignment(SwingConstants.CENTER);
			add(buyButton);
		} else {
			numberLabel = new JLabel("Quantity: " + item.getPlayerCount());
			JButton sellButton = new JButton("Sell: $" + item.getBuyBackPrice());
			sellButton.setBackground(Color.black);
			sellButton.setBorder(new LineBorder(new Color(0, 255, 0)));
			sellButton.setForeground(new Color(0, 255, 0));
			sellButton.setBounds(5, 125, 140, 20);
			sellButton.setHorizontalAlignment(SwingConstants.CENTER);
			sellButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Market.sellItem(item);
				}
			});
			add(sellButton);
		}
		itemInfo();
		add(nameLabel);
	}

	/**
	 * Constructs panel for use in the {@link main.Club Club} class.
	 * 
	 * @param setItem determines the item which this panel will represent
	 * @param parent  the ItemContainer that this panel belongs to
	 */
	public ItemPanel(Item setItem, ItemContainer parent) {
		item = setItem;
		parentWindow = parent;
		numberLabel = new JLabel("Quantity: " + item.getPlayerCount());
		itemInfo();
		add(nameLabel);
		JButton useButton = new JButton("Use Item");
		useButton.setBackground(Color.black);
		useButton.setBorder(new LineBorder(new Color(0, 255, 0)));
		useButton.setForeground(new Color(0, 255, 0));
		useButton.setBounds(5, 125, 140, 20);
		useButton.setHorizontalAlignment(SwingConstants.CENTER);
		useButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Club.getActivePanel().getAthlete().useItem(item);
				if (item.getPlayerCount() == 0) {
					parentWindow.getItems().remove(item);
				}
				updatePlayerCount();
				Club.getActivePanel().updateStats();
				parentWindow.updateItems();
				parentWindow.repaint();
			}
		});
		add(useButton);
	}

	/**
	 * Adds various graphical and interface elements to the panel. Used to add
	 * labels for {@link main.Item Item} statistics and quantity, as well as buy,
	 * sell, and use buttons.
	 */
	public void itemInfo() {
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setBackground(Color.black);
		setLayout(null);

		nameLabel = new JLabel(item.getName());
		nameLabel.setForeground(new Color(0, 255, 0));
		nameLabel.setBounds(10, 11, 130, 14);

		numberLabel.setBounds(10, 25, 130, 12);
		numberLabel.setForeground(new Color(0, 255, 0));
		add(numberLabel);

		int position = 0;
		if (item.getOffenceBuff() != 0) {
			JLabel offenceLabel = new JLabel("Offence gain: " + item.getOffenceBuff());
			offenceLabel.setForeground(new Color(0, 255, 0));
			offenceLabel.setBounds(10, 50 + position * 15, 130, 20);
			add(offenceLabel);
			position++;
		}
		if (item.getDefenceBuff() != 0) {
			JLabel defenceLabel = new JLabel("Defence gain: " + item.getDefenceBuff());
			defenceLabel.setForeground(new Color(0, 255, 0));
			defenceLabel.setBounds(10, 50 + position * 15, 130, 20);
			add(defenceLabel);
			position++;
		}
		if (item.getStaminaBuff() != 0) {
			JLabel staminaLabel = new JLabel("Stamina gain: " + item.getStaminaBuff());
			staminaLabel.setForeground(new Color(0, 255, 0));
			staminaLabel.setBounds(10, 50 + position * 15, 130, 20);
			add(staminaLabel);
			position++;
		}
		if (item.getStaminaGain() != 0 || item.getStaminaBuff() != 0) {
			JLabel restoreLabel = new JLabel("Restoration: " + (item.getStaminaGain() + item.getStaminaBuff()));
			restoreLabel.setForeground(new Color(0, 255, 0));
			restoreLabel.setBounds(10, 50 + position * 15, 130, 20);
			add(restoreLabel);
			position++;
		}
	}

	public void updatePlayerCount() {
		numberLabel.setText("Quantity: " + item.getPlayerCount());
	}

	public void updateStoreCount() {
		numberLabel.setText("Quantity: " + item.getStoreCount());
	}

	public Item getItem() {
		return item;
	}
}
