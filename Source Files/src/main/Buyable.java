package main;

/**
 * Superclass for {@link main.Athlete Athlete} and {@link main.Item Item}.
 * 
 * @author bsm62, mdi48
 * @version 1.05
 */
public class Buyable {
	private String name;
	private long contractPrice, buyBackPrice;

	/**
	 * Constructor that is called by the subclasses to set pricing information and
	 * name. The base purchase price is increased or decreased in line with game
	 * difficulty, and the sale price is set as half of the purchase price.
	 * 
	 * @param setName string describing the athlete or item
	 * @param price   sets the base price for the athlete or item
	 */
	public Buyable(String setName, int price) {
		name = setName;
		contractPrice = (int) Math.round(price * Player.getDifficulty());
		buyBackPrice = (int) Math.round(contractPrice * 0.5);
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public long getContractPrice() {
		return contractPrice;
	}

	public long getBuyBackPrice() {
		return buyBackPrice;
	}

	/**
	 * Sets the sale price to be the same as the purchase price, or back to half if
	 * this is already active. This is used during the initial {@link main.Team
	 * Team} setup so that the {@link main.Player Player} can buy and sell athletes
	 * freely without losing money.
	 */
	public void toggleBuyBack() {
		if (buyBackPrice != contractPrice) {
			buyBackPrice = contractPrice;
		} else {
			buyBackPrice = (int) Math.round(contractPrice * 0.5);
		}
	}
}
