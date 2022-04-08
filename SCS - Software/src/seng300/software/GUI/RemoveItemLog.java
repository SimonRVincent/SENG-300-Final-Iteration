package seng300.software.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSplitPane;

import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.products.PLUCodedProduct;
import org.lsmr.selfcheckout.products.Product;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.Font;

public class RemoveItemLog extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel scrollPanel;
	private JPanel wrapper;
	private JSplitPane splitPane;
	private GridBagConstraints gbc_txtrPleaseSelectAll;
	private GridBagConstraints gbc_btnNewButton;
	private Dimension max;
	private JTextArea txtrPleaseSelectAll;
	private JButton remove;
	private JCheckBox [] productsInLog;
	private Map<JCheckBox, Product> removable;
	private ArrayList<Product> allProducts;

	/**
	 * Create the frame.
	 */
	public RemoveItemLog(ArrayList<Product> list) {
		this.allProducts = list;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		JScrollPane itemLog = new JScrollPane(scrollPanel);

		JPanel removeOrReturn = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		layout.rowWeights = new double[]{0.0, 1.0};
		layout.columnWeights = new double[]{0.0};
		removeOrReturn.setLayout(layout);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, itemLog, removeOrReturn);
		
		wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
		txtrPleaseSelectAll = new JTextArea();
		txtrPleaseSelectAll.setText(
				"   Please select all" + "\n" 
				+ "the items you would" 
				+ "\n" + "    like to remove," 
				+ "\n" + "then press remove");
		gbc_txtrPleaseSelectAll = new GridBagConstraints();
		gbc_txtrPleaseSelectAll.insets = new Insets(0, 0, 5, 5);
		gbc_txtrPleaseSelectAll.anchor = GridBagConstraints.NORTH;
		gbc_txtrPleaseSelectAll.gridx = 0;
		gbc_txtrPleaseSelectAll.gridy = 1;
		max = txtrPleaseSelectAll.getPreferredSize();
		max.height = 75;
		txtrPleaseSelectAll.setMaximumSize(max);
		txtrPleaseSelectAll.setEditable(false);
		wrapper.add(txtrPleaseSelectAll);
		removeOrReturn.add(wrapper, gbc_txtrPleaseSelectAll);
		
		remove = new JButton("Remove");
		remove.setFont(new Font("Tahoma", Font.BOLD, 12));
		gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		removeOrReturn.add(remove, gbc_btnNewButton);
		
		splitPane.setDividerLocation(250);
		splitPane.setResizeWeight(0.5);
		
		contentPane.add(splitPane);
		
		productsInLog = new JCheckBox [allProducts.size()];
		
		Product currentProduct;
		BarcodedProduct bProduct;
		PLUCodedProduct pProduct;
		for (int i = 0; i < allProducts.size(); i++) {
			currentProduct = allProducts.get(i);
			if (currentProduct instanceof BarcodedProduct) {
				bProduct = (BarcodedProduct) currentProduct;
				productsInLog[i] = new JCheckBox(bProduct.getDescription());
			} else {
				pProduct = (PLUCodedProduct) currentProduct;
				productsInLog[i] = new JCheckBox(pProduct.getDescription());
			}
			removable.put(productsInLog[i], currentProduct);
			scrollPanel.add(productsInLog[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Product temp;
		for (int i = 0; i < productsInLog.length; i++) {
			if (productsInLog[i].isSelected()) {
				temp = removable.get(productsInLog[i]);
				// selectItemToRemove method
			}
		}
	}

}