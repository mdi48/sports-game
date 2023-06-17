package main;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Graphical element that creates and holds multiple {@link main.ItemPanel
 * ItemPanels}. Used to show {@link main.Item Items} for purchase/sale in the
 * {@link main.Market Market} and use in the {@link main.Club Club}.
 *
 * @author bsm62
 * @version 1.03
 **/
public class ItemContainer extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Item> itemList;
	private ArrayList<ItemPanel> panelList;

	/**
	 * Constructs a panel for displaying {@link main.Player Player} {@link main.Item
	 * Items}. If this is used in the {@link main.Club Club} the items have a 'use'
	 * option, in the MarketWindow{@link main.MarketWindow} they have a 'sell'
	 * option.
	 * 
	 * @param inClub determines whether this container is being constructed for the
	 *               Club or Market
	 */
	public ItemContainer(boolean inClub) {
		itemList = Player.getItems();
		panelList = new ArrayList<ItemPanel>();
		setPreferredSize(new Dimension((Player.getItems().size()) * 160 + 10, 170));
		setBackground(Color.black);
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setLayout(null);
		for (Item item : itemList) {
			if (item.getPlayerCount() > 0) {
				if (inClub == true) {
					panelList.add(new ItemPanel(item, this));
				} else {
					panelList.add(new ItemPanel(item, false));
				}
			}
		}
	}

	/**
	 * Constructs a panel for displaying {@link main.Market Market} {@link main.Item
	 * Items} that can be bought.
	 * 
	 * @param items list of items to display
	 */
	public ItemContainer(ArrayList<Item> items) {
		itemList = items;
		panelList = new ArrayList<ItemPanel>();
		setPreferredSize(new Dimension((items.size()) * 160 + 10, 170));
		setBackground(Color.black);
		setBorder(new LineBorder(new Color(0, 255, 0)));
		setLayout(null);
		for (Item item : itemList) {
			if (item.getStoreCount() > 0) {
				panelList.add(new ItemPanel(item, true));
			}
		}
	}

	/**
	 * Refreshes the panel. Adds or removes {@link main.ItemPanel ItemPanels} to
	 * reflect changes in it's list of {@link main.Item Items}.
	 */
	public void updateItems() {
		removeAll();
		setPreferredSize(new Dimension((itemList.size()) * 160 + 10, 170));
		for (ItemPanel panel : panelList) {
			if (itemList.contains(panel.getItem()) == false) {
				panelList.remove(panel);
				break;
			}
		}
		int position = 0;
		for (ItemPanel panel : panelList) {
			panel.setBounds(10 + 160 * position, 10, 150, 150);
			add(panel);
			position++;
		}
	}

	public ArrayList<Item> getItems() {
		return itemList;
	}

	public ArrayList<ItemPanel> getPanels() {
		return panelList;
	}

}
