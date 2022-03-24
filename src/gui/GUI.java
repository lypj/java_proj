package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;

public class GUI extends JFrame{
	
	private double totalprice = 0;
	JLabel totalpriceLabel;
	ArrayList<String> foodlist;
	ArrayList<String> imagelist;
	ArrayList<Double> pricelist;
	Integer[] foodquantity;
	Client client;
	
	public GUI() {
		super("Menu");		
		class FoodPanel extends JPanel{
			private int quantity = 0;
			JLabel numberLabel;
			
			public void reset()
			{
				quantity = 0;
				numberLabel.setText(Integer.toString(quantity));
			}
			
			public FoodPanel(String name, String img, Double price) {
				this.setLayout(new GridBagLayout());
				JPanel leftPanel = new JPanel(new GridBagLayout());
				JPanel rightPanel = new JPanel(new GridBagLayout());
				leftPanel.setBackground(Color.white);
				rightPanel.setBackground(Color.white);
				
				ImagePanel pic = new ImagePanel(img);
				JLabel foodname = new JLabel(name);
				
				JLabel priceLabel = new JLabel(Double.toString(price) + "$");
				JButton addButton = new JButton("+");
				JButton removeButton = new JButton("-");
				numberLabel = new JLabel(Integer.toString(quantity));
				removeButton.setFocusable(false);
				addButton.setFocusable(false);
				
				class addListener implements ActionListener {
					public void actionPerformed(ActionEvent e) {
						totalprice = totalprice - price * quantity;
						quantity = quantity + 1;
						totalprice = totalprice + price * quantity;
						numberLabel.setText(Integer.toString(quantity));
						totalpriceLabel.setText(Double.toString(totalprice) + "$");
					}
				}
				
				class removeListener implements ActionListener {
					public void actionPerformed(ActionEvent e) {
						totalprice = totalprice - price * quantity;
						if(quantity>0) {
							quantity = quantity - 1;
						}
						totalprice = totalprice + price * quantity;
						numberLabel.setText(Integer.toString(quantity));
						totalpriceLabel.setText(Double.toString(totalprice) + "$");
					}
				}
				
				removeButton.addActionListener(new removeListener());
				addButton.addActionListener(new addListener());
				
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 1;
			    c.gridy = 0;
				leftPanel.add(pic, c);
			    c.gridy = 1;
			    c.insets = new Insets(5, 0, 0, 0); 
				leftPanel.add(foodname, c);
				
				c.gridx = 1;
			    c.gridy = 0;
			    c.ipadx = 5;
			    c.ipady = 5;
			    c.insets = new Insets(0, 5, 0, 0); 
				rightPanel.add(priceLabel,c);
				c.gridx = 0;
			    c.gridy = 1;
			    c.ipadx = 5;
			    c.ipady = 5;
			    c.insets = new Insets(0, 0, 0, 0); 
			    rightPanel.add(removeButton,c);
			    c.gridx = 1;
			    rightPanel.add(numberLabel,c);
			    c.gridx = 2;
			    rightPanel.add(addButton,c);
			    
				c.gridx = 0;
			    c.gridy = 0;
			    c.insets = new Insets(0, 0, 0, 0);
				add(leftPanel,c);
				c.gridx = 1;
			    c.ipadx = 20;
			    c.ipady = 50;	
				c.insets = new Insets(5, 5, 5, 5);
				add(rightPanel,c);						
			}
			
			public Integer get_quantity()
			{
				return quantity;
			}
		}
		
		foodlist = new ArrayList<String>(Arrays.asList("BigMac","Fries","Burrito","Coca-Cola","Vanilla Cone","Combo"));
		imagelist = new ArrayList<String>(Arrays.asList("Picture/bigmac.jpg","Picture/fries.jpg","Picture/burrito.jpg","Picture/coke.jpg","Picture/cone.jpg","Picture/combo.jpg"));
		pricelist = new ArrayList<Double>(Arrays.asList(4.0, 2.0, 3.0, 2.0, 1.0, 7.0));
		foodquantity = new Integer[6];
		
		ArrayList<FoodPanel> panellist = new ArrayList<FoodPanel>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320,450);
		
		JPanel cardPanel = new JPanel();
		CardLayout card = new CardLayout();
		JPanel userPanel = new JPanel();
		JPanel mainPanel = new JPanel(new BorderLayout());	
		JPanel MenuPanel = new JPanel(new GridBagLayout());
		JPanel resultPanel = new JPanel(new GridBagLayout());
		resultPanel.setPreferredSize(new Dimension(320,450));
		JTextArea orderArea = new JTextArea();
		orderArea.setOpaque(false);
		orderArea.setFont(new Font("Monospaced",Font.PLAIN,12));
		
		JPanel inputPanel = new JPanel(new GridLayout(2,2));
		inputPanel.setMaximumSize(new Dimension(300,50));
		JLabel userlb = new JLabel("username:");
		JLabel pwlb = new JLabel("password:");
		JTextField usertf = new JTextField();
		JTextField pwtf = new JTextField();
		
		inputPanel.add(userlb);
		inputPanel.add(usertf);
		inputPanel.add(pwlb);
		inputPanel.add(pwtf);
		
		JButton loginButton = new JButton("log in");
		JButton signupButton = new JButton("sign up");
		loginButton.setFocusable(false);
		signupButton.setFocusable(false);
		class signupListener implements ActionListener{
			public void actionPerformed(ActionEvent e)
			{
				try {
					client = new Client();
					String signup = client.signup(usertf.getText(), pwtf.getText());
					if(signup.equals("yes"))
					{
						JOptionPane.showMessageDialog(userPanel, "Signed up");
					}
					else
					{
						JOptionPane.showMessageDialog(userPanel, "User exists");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		}
		class loginListener implements ActionListener{
			public void actionPerformed(ActionEvent e)
			{
				try {
					client = new Client();
					String login = client.login(usertf.getText(), pwtf.getText());
					if(login.equals("yes"))
					{
						card.next(cardPanel);
					}
					else if(login.equals("no"))
					{
						JOptionPane.showMessageDialog(userPanel, "Wrong password");
					}
					else
					{
						JOptionPane.showMessageDialog(userPanel, "User doesn't exists");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		signupButton.addActionListener(new signupListener());
		loginButton.addActionListener(new loginListener());
		
		GridBagConstraints c = new GridBagConstraints();
		userPanel.setLayout(new GridBagLayout());
	    c.gridy = 0;
	    c.ipadx = 60;
		userPanel.add(inputPanel,c);
	    c.gridy = 1;
	    c.insets = new Insets(5, 5, 5, 5); 
	    c.ipadx = 5;
		userPanel.add(loginButton,c);
		c.gridy = 2;
		userPanel.add(signupButton,c);
		
		for(int i=0;i<foodlist.size();i++)
		{
			panellist.add(new FoodPanel(foodlist.get(i), imagelist.get(i), pricelist.get(i)));
			panellist.get(i).setBackground(Color.white);
		}

		c.insets = new Insets(20, 5, 5, 5); 
		c.gridx = 0;
	    c.gridy = 0;
	    c.ipadx = 5;
	    c.ipady = 10;	    
		MenuPanel.add(panellist.get(0),c);
		c.insets = new Insets(5, 5, 5, 5); 
	    c.gridy = 1;
		MenuPanel.add(panellist.get(1),c);
	    c.gridy = 2;
		MenuPanel.add(panellist.get(2),c);
	    c.gridy = 3;
		MenuPanel.add(panellist.get(3),c);
		c.gridy = 4;
		MenuPanel.add(panellist.get(4),c);
		c.gridy = 5;
		c.insets = new Insets(5, 5, 20, 5); 
		MenuPanel.add(panellist.get(5),c);
		
		JScrollPane scrollPane = new JScrollPane(MenuPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		JPanel orderPanel= new JPanel(new GridLayout(1,2));
		orderPanel.setPreferredSize(new Dimension(70,70));
		JPanel pricePanel = new JPanel(new GridBagLayout());
		JPanel checkoutPanel = new JPanel(new GridBagLayout());
		totalpriceLabel = new JLabel(Double.toString(totalprice) + "$");
		JButton checkout = new JButton("Check Out");
		checkout.setFocusable(false);
		pricePanel.add(totalpriceLabel, new GridBagConstraints());
		checkoutPanel.add(checkout, new GridBagConstraints());
		orderPanel.add(pricePanel);
		orderPanel.add(checkoutPanel);

		class CheckoutListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				
				
				if(totalprice==0)
				{
					JOptionPane.showMessageDialog(mainPanel, "what?");
				}
				else
				{
					String massage = "";
					Integer[] foodquantity = new Integer[6];
					String[] spaces = {"           ","            ","          ","        ","     ","            "};
					
					orderArea.append("Your Order: "+"\n");
					for(int i=0;i<panellist.size();i++)
					{
						foodquantity[i] = panellist.get(i).get_quantity();
						orderArea.append(foodlist.get(i)+": "+spaces[i]+Integer.toString(foodquantity[i])+"\n");
					}
					orderArea.append("Total Price: "+"      "+Double.toString(totalprice)+"\n");
					try {
						massage = client.checkout(totalprice, foodquantity, foodlist);
					} catch (IOException e1) {
						e1.printStackTrace();
					}	
					card.next(cardPanel);
				}
			}
		}
		
		class returnListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				totalprice = 0;
				totalpriceLabel.setText(0 + "$");
				for(int i=0;i<panellist.size();i++)
				{
					panellist.get(i).reset();
				}
				scrollPane.getVerticalScrollBar().setValue(0);
				card.next(cardPanel);
				card.next(cardPanel);
				orderArea.setText("");
			}
		}
		checkout.addActionListener(new CheckoutListener());
				
		
		JButton returnButton = new JButton("Order again!");
		returnButton.addActionListener(new returnListener());
		c.gridx = 0;
		c.gridy = 0;
		resultPanel.add(orderArea,c);
		c.gridy = 1;
		resultPanel.add(returnButton,c);
		
		mainPanel.add(scrollPane);
		mainPanel.add(orderPanel,BorderLayout.SOUTH);
		cardPanel.setLayout(card);
		cardPanel.add(userPanel);
		cardPanel.add(mainPanel);
		cardPanel.add(resultPanel);
		add(cardPanel);
		
	}
	
	public static void main(String[] args)
	{
		GUI gui = new GUI();
		gui.setVisible(true);
	}
}
