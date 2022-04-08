package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.InvalidArgumentSimulationException;
import org.lsmr.selfcheckout.PriceLookupCode;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.CardLayout;

public class CustomerCheckoutPanel extends JPanel
{
	public final JButton useOwnBagsBtn;
	public final JButton scanItemBtn;
	public final JButton searchProductBtn;
	public final JButton placeItemBtn;
	public final JButton removeItemBtn;
	public final JButton doNotBagBtn;
	public final JButton checkoutBtn;
	
	private JPanel logoPanel;
	private JPanel pluEntryPanel;
	private JLabel pluEntryErrorMsgLabel;
	private PinPad pluEntryPinPad;
	
	/**
	 * Create the panel.
	 */
	public CustomerCheckoutPanel()
	{
		setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel mainBtnGroup = new JPanel();
		mainBtnGroup.setBackground(new Color(255, 255, 255));
		mainBtnGroup.setBorder(new EmptyBorder(0, 0, 5, 5));
		GridBagConstraints gbc_mainBtnGroup = new GridBagConstraints();
		gbc_mainBtnGroup.gridheight = 4;
		gbc_mainBtnGroup.insets = new Insets(0, 0, 5, 5);
		gbc_mainBtnGroup.fill = GridBagConstraints.BOTH;
		gbc_mainBtnGroup.gridx = 2;
		gbc_mainBtnGroup.gridy = 1;
		add(mainBtnGroup, gbc_mainBtnGroup);
		mainBtnGroup.setLayout(new GridLayout(0, 1, 10, 10));
		
		// TODO: connect action listener in main gui creation class that will call SelfCheckoutStationLogic function
		// TODO: Should we have a popup that informs use to "wait on attendant approval"?
		useOwnBagsBtn = new JButton("Use Own Bags");
		useOwnBagsBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
		useOwnBagsBtn.setBackground(new Color(245, 245, 245));
		useOwnBagsBtn.setForeground(new Color(0, 0, 0));
		mainBtnGroup.add(useOwnBagsBtn);
		
		scanItemBtn = new JButton("Scan Item");
		scanItemBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
		scanItemBtn.setBackground(new Color(245, 245, 245));
		scanItemBtn.setForeground(new Color(0, 0, 0));
		scanItemBtn.addActionListener(e -> scanItem());
		mainBtnGroup.add(scanItemBtn);
		
		JButton enterPLUCodeBtn = new JButton("Enter PLU Code");
		enterPLUCodeBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
		enterPLUCodeBtn.setBackground(new Color(245, 245, 245));
		enterPLUCodeBtn.setForeground(new Color(0, 0, 0));
		enterPLUCodeBtn.addActionListener(e -> showPluEntryPanel());
		mainBtnGroup.add(enterPLUCodeBtn);		
		
		// TODO: Connect to ProductLookupFrame in main gui creation class
		searchProductBtn = new JButton("Search Product");
		searchProductBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
		searchProductBtn.setBackground(new Color(245, 245, 245));
		searchProductBtn.setForeground(new Color(0, 0, 0));
		mainBtnGroup.add(searchProductBtn);
		
		// TODO: ActionListner needs to call method to place item in bagging, need to track last item added?
		placeItemBtn = new JButton("Place Item");
		placeItemBtn.setForeground(Color.BLACK);
		placeItemBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
		placeItemBtn.setBackground(new Color(245, 245, 245));
		mainBtnGroup.add(placeItemBtn);

		// TODO: ActionListner needs to call method to remove item from bagging
		removeItemBtn = new JButton("Remove Item");
		removeItemBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
		removeItemBtn.setBackground(new Color(245, 245, 245));
		removeItemBtn.setForeground(new Color(0, 0, 0));
		mainBtnGroup.add(removeItemBtn);
		
		// TODO: ActionListner needs to notify attendant? Not sure how this was implemented
		doNotBagBtn = new JButton("Do Not Bag");
		doNotBagBtn.setForeground(Color.BLACK);
		doNotBagBtn.setFont(new Font("Tahoma", Font.BOLD, 26));
		doNotBagBtn.setBackground(new Color(245, 245, 245));
		mainBtnGroup.add(doNotBagBtn);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder(0, 15, 0, 15));
		leftPanel.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_leftPanel = new GridBagConstraints();
		gbc_leftPanel.gridheight = 4;
		gbc_leftPanel.insets = new Insets(0, 0, 5, 5);
		gbc_leftPanel.fill = GridBagConstraints.BOTH;
		gbc_leftPanel.gridx = 3;
		gbc_leftPanel.gridy = 1;
		add(leftPanel, gbc_leftPanel);
		leftPanel.setLayout(new CardLayout(0, 0));
		
		logoPanel = new JPanel();
		logoPanel.setBackground(new Color(255, 255, 255));
		logoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		GridBagLayout gbl_logoPanel = new GridBagLayout();
		gbl_logoPanel.columnWidths = new int[]{57, 86, 0, 0};
		gbl_logoPanel.rowHeights = new int[]{17, 0, 0, 0};
		gbl_logoPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_logoPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		logoPanel.setLayout(gbl_logoPanel);
		leftPanel.add(logoPanel);
		logoPanel.setVisible(true);
		
		// TODO: Setup company logo to use.
		JLabel lblNewLabel = new JLabel("logo goes here");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		logoPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		pluEntryPanel = new JPanel();
		pluEntryPanel.setBackground(new Color(255, 255, 255));
		pluEntryPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
		leftPanel.add(pluEntryPanel);
		pluEntryPanel.setVisible(false);

		GridBagLayout gbl_pluEntryPanel = new GridBagLayout();
		gbl_pluEntryPanel.columnWidths = new int[]{57};
		gbl_pluEntryPanel.rowHeights = new int[]{17, 0};
		gbl_pluEntryPanel.columnWeights = new double[]{1.0};
		gbl_pluEntryPanel.rowWeights = new double[]{0.0, 1.0};
		pluEntryPanel.setLayout(gbl_pluEntryPanel);
		
		pluEntryErrorMsgLabel = new JLabel("Product not found. Please try aagin.");
		pluEntryErrorMsgLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		pluEntryErrorMsgLabel.setForeground(new Color(255, 0, 0));
		GridBagConstraints gbc_pluEntryErrorMsgLabel = new GridBagConstraints();
		gbc_pluEntryErrorMsgLabel.insets = new Insets(0, 0, 5, 0);
		gbc_pluEntryErrorMsgLabel.gridx = 0;
		gbc_pluEntryErrorMsgLabel.gridy = 0;
		pluEntryPanel.add(pluEntryErrorMsgLabel, gbc_pluEntryErrorMsgLabel);
		pluEntryErrorMsgLabel.setVisible(false);
				
		pluEntryPinPad = new PinPad();
		pluEntryPinPad.padEnterBtn.addActionListener(e -> getPluCode());
		GridBagConstraints gbc_pluEntryPad = new GridBagConstraints();
		gbc_pluEntryPad.fill = GridBagConstraints.BOTH;
		gbc_pluEntryPad.gridx = 0;
		gbc_pluEntryPad.gridy = 1;
		pluEntryPanel.add(pluEntryPinPad, gbc_pluEntryPad);
		
		checkoutBtn = new JButton("Proceed to Checkout");
		checkoutBtn.setMargin(new Insets(10, 14, 10, 14));
		checkoutBtn.setBackground(new Color(240, 255, 240));
		checkoutBtn.setFont(new Font("Tahoma", Font.BOLD, 36));
		checkoutBtn.setForeground(new Color(0, 100, 0));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 5;
		add(checkoutBtn, gbc_btnNewButton);

	}
	
	// TODO: Move to main class that will connect to selfCheckoutSYstemLogic
	// TODO: Make wrapper for pluEntryPinPad.getValue() so you can access information you need;
	// TODO: You will pobably also need a wrapper method for setting the actionlistener for the plu pin pad
	private void getPluCode()
	{
		String value = pluEntryPinPad.getValue();
		if(!value.isEmpty())
		{
			try
			{
				PriceLookupCode code = new PriceLookupCode(value);
				// TODO: Find product in database and try and add it to cart
				showLogoPanel();
			}
			catch(InvalidArgumentSimulationException e)
			{
				pluEntryErrorMsgLabel.setVisible(true);
				pluEntryPinPad.clear();
			}
		}
		// ignore empty searches
	}
	
	public void showPluEntryPanel()
	{
		hideLogoPanel();
		pluEntryPanel.setVisible(true);
	}
	
	public void showLogoPanel()
	{
		hidePluEntryPanel();
		logoPanel.setVisible(true);
	}
	
	private void hidePluEntryPanel()
	{
		if (pluEntryPanel.isVisible())
		{
			pluEntryPanel.setVisible(false);
		}
	}
	
	private void hideLogoPanel()
	{
		if (logoPanel.isVisible())
		{
			logoPanel.setVisible(false);
		}
	}
	
	private void scanItem()
	{
		// TODO: Generate random barcoded product from database
		// TODO: keep on scanning until item added
	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new CustomerCheckoutPanel());
					frame.setBounds(100, 100, 450, 450);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}