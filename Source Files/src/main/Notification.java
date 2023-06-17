package main;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dialog;

/**
 * A pop-up window that is created to notify the {@link main.Player Player} with
 * important messages. It will prevent the Player from interacting with other
 * windows until it is acknowledged.
 *
 * @author bsm62
 * @version 1.03
 **/
public class Notification extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea messageLabel;

	/**
	 * Constructor for the notification window.
	 * 
	 * @param message the message to display to the player.
	 * @param x       used to inherit the horizontal position of the window that
	 *                called the notification
	 * @param y       used to inherit the vertical position of the window that
	 *                called the notification
	 */
	public Notification(String message, int x, int y) {
		getContentPane().setBackground(new Color(0, 0, 0));
		setBackground(new Color(0, 0, 0));
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setTitle("Notification");
		setBounds(x, y, 400, 170);
		getContentPane().setLayout(null);
		contentPanel.setBackground(new Color(0, 0, 0));
		contentPanel.setBounds(0, 0, 400, 90);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new GridBagLayout());

		messageLabel = new JTextArea();
		messageLabel.setForeground(new Color(0, 255, 0));
		messageLabel.setWrapStyleWord(true);
		messageLabel.setRows(2);
		messageLabel.setBorder(null);
		messageLabel.setLineWrap(true);
		messageLabel.setText(message);
		messageLabel.setBackground(new Color(0, 0, 0));
		messageLabel.setBounds(30, 10, 340, 75);
		messageLabel.setEditable(false);
		contentPanel.add(messageLabel);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(0, 0, 0));
			buttonPane.setBounds(0, 90, 400, 50);
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.setBackground(Color.black);
				okButton.setBorder(new LineBorder(new Color(0, 255, 0)));
				okButton.setForeground(new Color(0, 255, 0));
				okButton.setBounds(175, 5, 55, 25);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeWindow();
					}
				});
				buttonPane.setLayout(null);
				okButton.setHorizontalAlignment(SwingConstants.CENTER);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.requestFocus();
	}

	public void closeWindow() {
		this.dispose();
	}
}
