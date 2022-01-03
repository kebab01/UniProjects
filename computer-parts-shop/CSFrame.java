import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import java.io.*;

class CSFrame extends JFrame{

	public static void main(String[] args){

		CSFrame newFrame = new CSFrame("Ordering System");
	}

	public CSFrame(String name){
		super(name);

		setLayout(new BorderLayout());

		MainPanel mainPanel = new MainPanel();

		LoginPanel loginPanel = new LoginPanel(mainPanel);

		add(loginPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);

		ExitPanel exitPanel = new ExitPanel(this);
		add(exitPanel, BorderLayout.SOUTH);

		setLocation(100,100);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
}
//-----------------------MAIN-------------------------
class MainPanel extends JPanel{

	private Order order;
	private boolean loggedIn = false;
	private OrderPanel orderPanel;
	private HistoryPanel historyPanel;
	private PartsPanel partsPanel;
	private OrderingSystem ors;

	public MainPanel(){

		initializeShop();
		
		setLayout(new GridLayout(3,0));

		orderPanel = new OrderPanel(this);
		historyPanel = new HistoryPanel(this);
		partsPanel = new PartsPanel(this);

		add(partsPanel);
		add(orderPanel);
		add(historyPanel);

	}
	private void initializeShop(){

		try{
			FileInputStream fileIn = new FileInputStream("compshop.ser");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);

			ors = (OrderingSystem) objectIn.readObject();
			objectIn.close();

		}
		catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}

	}

	public void setLogin(Customer customer){

		ors.setCurrentCustomer(customer);
		loggedIn=true;
		resetOrder();
		refresh();
		refreshHistory();
	}

	private void resetOrder(){

		if (getCurrentCustomer() instanceof MemberCustomer){
			MemberCustomer memberCustomer = (MemberCustomer) getCurrentCustomer();
			order = new DiscountOrder(memberCustomer.getDISCOUNT());
		}
		else{
			order = new Order();
		}
	}
	public boolean checkLogin(){
		return loggedIn;
	}
	public void loginError(){
		loggedIn=false;
		JOptionPane.showMessageDialog(null, "Not logged in, Please login to continue", "Not Logged in", JOptionPane.INFORMATION_MESSAGE, null);
	}
	public Customer getCurrentCustomer(){
		return ors.getCurrentCustomer();
	}
	public Order getOrder(){
		return order;
	}
	public ArrayList<ComputerPart> getPartsInOrder(){
		return order.getComputerParts();
	}
	public ArrayList<ComputerPart> getParts(){
		return ors.getComputerParts();
	}
	public ArrayList<Order> getOrders(){
		return getCurrentCustomer().getOrders();
	}
	public void addToOrder(ComputerPart part){
		order.addComputerPart(part);
	}
	public void refresh(){
		orderPanel.refresh();
	}
	public void refreshHistory(){
		historyPanel.refresh();
	}
	public void submitOrder(){
		getCurrentCustomer().addOrder(order);
		resetOrder();
		refresh();
		refreshHistory();
		JOptionPane.showMessageDialog(null, "Order Submitted!\nThank you for shopping with the computer parts shop", "Success!", JOptionPane.INFORMATION_MESSAGE, null);
	}
	public void removeFromOrder(int index){
		order.removeComputerPart(index);
	}
	public void removeOrder(int index){
		getCurrentCustomer().removeOrder(index);
		refreshHistory();
	}
	public Customer getCustomer(String id){

		for (Customer customer : ors.getCustomers()){
			if (customer.getID().equals(id)){
				return customer;
			}
		}
		return null;
	}

	public void noPartsErr(){
		JOptionPane.showMessageDialog(null, "Please add parts to your order", "No parts in order", JOptionPane.INFORMATION_MESSAGE, null);
	}
}
//-----------------------LOGIN-------------------------
class LoginPanel extends JPanel{

	private JButton exCustomer, newCustomer;
	private Customer currentCustomer;
	private MainPanel mainPanel;
	private JLabel lblCustomerInfo;

	public LoginPanel(MainPanel mainPanel){

		setLayout(new BorderLayout());

		currentCustomer = null;		
		this.mainPanel = mainPanel;

		exCustomer = new JButton("Login as Member");
		exCustomer.setPreferredSize(new Dimension(400,60));
		exCustomer.addActionListener(new ButtonHandler());

		add(exCustomer, BorderLayout.WEST);

		lblCustomerInfo = new JLabel("");
		add(lblCustomerInfo, BorderLayout.CENTER);

		newCustomer = new JButton("Sign up as new customer");
		newCustomer.setPreferredSize(new Dimension(400,60));
		newCustomer.addActionListener(new ButtonHandler());
		add(newCustomer, BorderLayout.EAST);

	}
	private class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){

			if (e.getSource()==exCustomer){
				String customerID = JOptionPane.showInputDialog("Enter customer ID: ");
				currentCustomer = checkValid(customerID);

				if (currentCustomer!=null){
					//if customer exists log them in
					MemberCustomer memberCustomer = (MemberCustomer) currentCustomer;	
					currentCustomer = memberCustomer;
					showWelcome();
					lblCustomerInfo.setText("\t\tLogged in: " + currentCustomer.getName().split(" ")[0]);
					mainPanel.setLogin(currentCustomer);
				}
			}
			else if(e.getSource()==newCustomer){
				//Display create customer window
				NewCustomerFrame newCustomerFrame = new NewCustomerFrame("Sign up as New Customer", mainPanel);
			}
		}
	}
	private Customer checkValid(String input){

		//If user clicked cancel then skip validation
		if (input!=null){
			try{
				//If customer entered and ID
				if (!input.equals("")){
					System.out.println("ID given " + input);
	 				 
	 				//If id is a valid customer id
	 				if (mainPanel.getCustomer(input) != null){
	 					Customer customer = mainPanel.getCustomer(input);
	 					System.out.println("Successfull login as " + input);
	 					return customer;
	 				}
	 				else{
	 					throw new InputMismatchException("CustomerID not found. Please login as new customer");
	 				}
				}
				else{
					System.out.println("No ID given");
					throw new InputMismatchException("CustomerID not given. Please input a valid customerID");
				}
			}catch(InputMismatchException e){
				JOptionPane.showMessageDialog(null,e, "Invalid CustomerID", JOptionPane.ERROR_MESSAGE);

			}
		}
		return null;
	}

	public void showWelcome(){
		//Shows welcome screen to customer
		if (currentCustomer instanceof MemberCustomer){
			JOptionPane.showMessageDialog(null, String.format("Welcome %s!\nA 5%% discount will be applied to your order.",currentCustomer.getName()), "Login successful", JOptionPane.INFORMATION_MESSAGE, null);
		}
		else{
			JOptionPane.showMessageDialog(null, String.format("Welcome %s!\n",currentCustomer.getName()), "Login successful", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
	private class NewCustomerFrame extends JFrame{

		private JButton btnClose, btnConfirm;
		private JTextField txtName, txtMobile, txtAddress;
		private JComboBox<String> cmbGender;

		public NewCustomerFrame(String name, MainPanel mainPanel){
			super(name);

			setLayout(new GridLayout(5,2));

			//Add labels and boxes to frame
			add(new JLabel("Name: "));
			txtName = new JTextField();
			txtName.setPreferredSize(new Dimension(200,0));
			add(txtName);

			//Gender
			add(new JLabel("Gender: "));
			String[] genders = {"Male", "Female"};
			cmbGender = new JComboBox<String>(genders);
			add(cmbGender);

			//Mobile number
			add(new JLabel("Mobile Number"));
			txtMobile = new JTextField();
			add(txtMobile);

			add(new JLabel("Address: "));
			txtAddress = new JTextField();
			add(txtAddress);

			btnClose = new JButton("Close");
			btnClose.addActionListener((e) -> {dispose();});
			add(btnClose);

			btnConfirm = new JButton("Confirm");
			btnConfirm.addActionListener((e) -> {
				String[] customerDetails = {txtName.getText(),cmbGender.getItemAt(cmbGender.getSelectedIndex()), txtMobile.getText(),txtAddress.getText()};

					if(isValid(customerDetails)){
						currentCustomer = new Customer(customerDetails[0],customerDetails[1],customerDetails[2],customerDetails[3]);
						System.out.println("Customer Created");
						showWelcome();
						lblCustomerInfo.setText("\t\tLogged in: " + currentCustomer.getName().split(" ")[0]);
						mainPanel.setLogin(currentCustomer);
						dispose();
					}
			});
			add(btnConfirm);

			setLocation(200,200);
			pack();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}
		private boolean isValid(String[] customerDetails){

			try{
				for (String detail: customerDetails){
					if (!detail.equals("")){
					}
					else{
						throw new InputMismatchException("Invalid details entered, please try again");
					}
				}
				return true;
			}catch(InputMismatchException e){
				JOptionPane.showMessageDialog(null,e, "Invalid entries", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
	}
}
//-----------------------PARTS-------------------------
class PartsPanel extends JPanel{

	private PartsContentPanel content;

	public PartsPanel(MainPanel mainPanel){

		setLayout(new BorderLayout());

		JLabel lblParts = new JLabel("Parts", SwingConstants.CENTER);
		lblParts.setFont(new Font("Sherif", Font.BOLD, 20));
		add(lblParts, BorderLayout.NORTH);

		add(new GenSubHeadingPanel("Parts List:", "Parts Information:"), BorderLayout.CENTER);

		content = new PartsContentPanel(mainPanel);
		add(content, BorderLayout.SOUTH);
	}
}
class PartsContentPanel extends JPanel{

	private ArrayList<ComputerPart>parts;
	private JList lstParts,lstPartInfo;
	private DefaultListModel<String> mdlPartsLst, mdlInfoLst;
	private MainPanel mainPanel;

	public PartsContentPanel(MainPanel mainPanel){

		setLayout(new GridLayout(1,3));

		this.parts = mainPanel.getParts();
		this.mainPanel = mainPanel;

		mdlPartsLst = new DefaultListModel<String>();

		//Load parts into listbox
		for (ComputerPart part:parts){
			mdlPartsLst.addElement(part.getID());
		}

		lstParts = new JList<String>(mdlPartsLst);
		lstParts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstParts.addListSelectionListener(new listHandler());

		add(new JScrollPane(lstParts));

		mdlInfoLst = new DefaultListModel<String>();
		lstPartInfo = new JList<String>(mdlInfoLst);
		add(new JScrollPane(lstPartInfo));

		add(new PartsButtonPanel());
	}
	private class listHandler implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e){

        	if(e.getSource()==lstParts){
        		mdlInfoLst.clear();
        		ComputerPart partSelected = getPart(lstParts.getSelectedIndex());

        		for(String item:partSelected.infoList()){
        			mdlInfoLst.addElement(item);
        		}

        		mdlInfoLst.addElement("$"+Double.toString(partSelected.getPrice()));
        	}
        }
    }
	private class PartsButtonPanel extends JPanel{

		public PartsButtonPanel(){

			setLayout(new GridLayout(3,1));

			JButton btnAdd = new JButton("Add part");
			btnAdd.addActionListener(new ButtonHandler());
			add(btnAdd);
			add(new JLabel());
			add(new JLabel());
		}
	}
	public class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){

			//Check if a part has been selected, if not do nothing
			if (lstParts.getSelectedIndex()!=-1){

				if(mainPanel.checkLogin()==true){
					ComputerPart partSelected = getPart(lstParts.getSelectedIndex());
					mainPanel.addToOrder(partSelected);
					mainPanel.refresh();
				}
				else{
					mainPanel.loginError();
				}
			}
		}
	}
	private ComputerPart getPart(int index){
		return parts.get(index);
	}
}
//-----------------------ORDER-------------------------
class OrderPanel extends JPanel{

	private OrderContentPanel orderContentPanel;

	public OrderPanel(MainPanel mainPanel){
		setLayout(new BorderLayout());

		JLabel lblOrder = new JLabel("Order", SwingConstants.CENTER);
		lblOrder.setFont(new Font("Sherif", Font.BOLD, 20));
		add(lblOrder, BorderLayout.NORTH);

		add(new GenSubHeadingPanel("Parts In Order:", "Part Information:"), BorderLayout.CENTER);

		orderContentPanel = new OrderContentPanel(mainPanel);
		add(orderContentPanel, BorderLayout.SOUTH);

	}
	public void refresh(){
		//Call the orderContent refresh function
		orderContentPanel.refresh();
	}
}
class OrderContentPanel extends JPanel{

	private ArrayList<ComputerPart> parts;
	private DefaultListModel<String> mdlOrder, mdlOrderInfo;
	private JList lstOrder, lstOrderInfo;
	private Order order;
	private JLabel lblTotal, lblTotalInc;
	private MainPanel mainPanel;

	public OrderContentPanel(MainPanel mainPanel){

		this.parts = mainPanel.getParts();
		this.mainPanel = mainPanel;

		setLayout(new GridLayout(1,3));

		// Real list box for parts
		mdlOrder = new DefaultListModel<String>();
		lstOrder = new JList<String>(mdlOrder);
		lstOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    lstOrder.addListSelectionListener(new listHandler());
		add(new JScrollPane(lstOrder));

		mdlOrderInfo = new DefaultListModel<String>();
		lstOrderInfo = new JList<String>(mdlOrderInfo);
		add(new JScrollPane(lstOrderInfo));

		add(new OrderButtonPanel());
	}
	public void refresh(){

		//Update contentPanels order object and reshresh list box
		order = mainPanel.getOrder();
		mdlOrder.clear();
		for (ComputerPart part:mainPanel.getPartsInOrder()){
			mdlOrder.addElement(part.getID());
		}

		lblTotal.setText(String.format("$%.2f",order.getTotalPrice()));

		if (order instanceof DiscountOrder){
			DiscountOrder discOrder = (DiscountOrder) order; 
			lblTotalInc.setText(String.format("$%.2f",discOrder.getTotalPrice() * (1 - discOrder.getDiscount()))); 
		}
		else{
			lblTotalInc.setText(String.format("$%.2f",order.getTotalPrice()));
		}

	}
	private class listHandler implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e){

    		mdlOrderInfo.clear();
    		int itemSelected = lstOrder.getSelectedIndex();

    		//-1 indicates no selection
    		if (itemSelected!=-1){
        		ComputerPart partSelected = order.getComputerPart(itemSelected);
        		lstOrder.setSelectionBackground(Color.BLUE);

        		for(String item:partSelected.infoList()){
        			mdlOrderInfo.addElement(item);

        		}
        		for (ComputerPart part:order.getComputerParts()){
    				if(partSelected.isCompatible(part)==false){
    					mdlOrderInfo.addElement("Incompatible with " + part.getID());
    					lstOrder.setSelectionBackground(Color.RED);
    				}
        		}
        		mdlOrderInfo.addElement("$"+Double.toString(partSelected.getPrice()));
    		}
        }
	}
	class OrderButtonPanel extends JPanel{

		private JButton btnRemove, btnSubmit;

		public OrderButtonPanel(){

			setLayout(new GridLayout(3,2));

			btnRemove = new JButton("Remove Part");
			btnRemove.addActionListener(new ButtonHandler());
			add(btnRemove);

			btnSubmit = new JButton("Submit Order");
			btnSubmit.addActionListener(new ButtonHandler());
			add(btnSubmit);

			//Add Total label and adjust border
			JLabel lblTitle = new JLabel("Total: ");
			Border titelBorder = lblTitle.getBorder();
			Border titelMargin = new EmptyBorder(10,10,10,10);
			lblTitle.setBorder(new CompoundBorder(titelBorder, titelMargin));
			add(lblTitle);

			lblTotal =  new JLabel("$0");
			add(lblTotal);

			JLabel lblTitleInc = new JLabel("Total incl. discounts: ");
			Border incBorder = lblTitleInc.getBorder();
			Border incMargin = new EmptyBorder(10,10,10,10);
			lblTitleInc.setBorder(new CompoundBorder(incBorder, incMargin));
			add(lblTitleInc);

			lblTotalInc = new JLabel("$0");
			add(lblTotalInc);
		}

		private class ButtonHandler implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e){

				if (mainPanel.checkLogin()==true){

					if(e.getSource()==btnRemove){

						if (mainPanel.getOrder().getComputerParts().size()!=0){
							order.removeComputerPart(lstOrder.getSelectedIndex());
							refresh();
						}
						else{
							mainPanel.noPartsErr();
						}
					}
					if(e.getSource()==btnSubmit){

						if (mainPanel.getOrder().getComputerParts().size()!=0){
							mainPanel.submitOrder();
						}
						else{
							mainPanel.noPartsErr();
						}
					}
				}
				else{
						mainPanel.loginError();
					}
			}
		}
	}
}
//-----------------------HISTORY-------------------------
class HistoryPanel extends JPanel{

	private HistoryContentPanel historyContentPanel;

	public HistoryPanel(MainPanel mainPanel){

		setLayout(new BorderLayout());

		JLabel lblOrder = new JLabel("History", SwingConstants.CENTER);
		lblOrder.setFont(new Font("Sherif", Font.BOLD, 20));
		add(lblOrder, BorderLayout.NORTH);

		add(new GenSubHeadingPanel("Orders:", "Order Information:"), BorderLayout.CENTER);

		historyContentPanel = new HistoryContentPanel(mainPanel);
		add(historyContentPanel, BorderLayout.SOUTH);

	}
	public void refresh(){
		historyContentPanel.refresh();
	}
}
class HistoryContentPanel extends JPanel{

	private DefaultListModel<String> mdlOrders, mdlOrderInfo;
	private MainPanel mainPanel;
	private ArrayList<Order> orders;
	private JList lstOrders, lstOrderInfo;

	public HistoryContentPanel(MainPanel mainPanel){

		this.mainPanel = mainPanel;
		setLayout(new GridLayout(1,3));

		mdlOrders = new DefaultListModel<String>();
		lstOrders = new JList<String>(mdlOrders);
		lstOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    lstOrders.addListSelectionListener(new listHandler());
		add(new JScrollPane(lstOrders));

		mdlOrderInfo = new DefaultListModel<String>();
		lstOrderInfo = new JList<String>(mdlOrderInfo);
		JScrollPane orderInfoScrollPane = new JScrollPane(lstOrderInfo);
		add(orderInfoScrollPane);

		add(new HistoryButtonPanel());
	}
	public void refresh(){
		mdlOrders.clear();
		orders = mainPanel.getOrders();
		for(Order order:orders){
			mdlOrders.addElement(order.getID());
		}
	}
	private class listHandler implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e){

			mdlOrderInfo.clear();
			int itemSelected = lstOrders.getSelectedIndex();

			if(itemSelected !=-1){
				Order order = orders.get(itemSelected);

				for (String item:order.getDetails()){
					mdlOrderInfo.addElement(item);
				}
			}
		}
	}
	class HistoryButtonPanel extends JPanel{

		private JButton btnDelete, btnExport;

		public HistoryButtonPanel(){

			setLayout(new GridLayout(3,1));

			btnDelete = new JButton("Delete Order");
			btnDelete.addActionListener(new ButtonHandler());
			add(btnDelete);

			btnExport = new JButton("Export Orders");
			btnExport.addActionListener(new ButtonHandler());
			add(btnExport);

			add(new JLabel());
		}
		private class ButtonHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e){

				//If user is logged in
				if (mainPanel.checkLogin()==true){

					if(e.getSource()==btnDelete){
						int itemSelected = lstOrders.getSelectedIndex();

						if (itemSelected!=-1){
							mainPanel.removeOrder(itemSelected);
						}
					}
					else if (e.getSource()==btnExport){

						Customer currentCustomer = mainPanel.getCurrentCustomer();

						//If there are orders to export
						if (currentCustomer.getOrders().size()!=0){
							try{
								Formatter textOutput = new Formatter(currentCustomer.getID()+".txt");
								textOutput.format(currentCustomer.toString());

								textOutput.format("\n\n------------------------\n");

								for (Order order:currentCustomer.getOrders()){
									textOutput.format(order.toString());
									textOutput.format("\n------------------------");
								}
								textOutput.close();

								JOptionPane.showMessageDialog(null, String.format("Orders exported\nAll of your orders have been exported to %s.txt",currentCustomer.getID()), "Success!", JOptionPane.INFORMATION_MESSAGE, null);
							}
							catch (FileNotFoundException ie){
								System.out.println(ie);
							}
						}
						else{
								JOptionPane.showMessageDialog(null, "Please create an order", "Export failed", JOptionPane.INFORMATION_MESSAGE, null);

						}
					}
				}
				else{
					mainPanel.loginError();
				}
			}
		}
	}
}
//-----------------------OTHER-------------------------
class ExitPanel extends JPanel{
	//exits computer shop

	private CSFrame frame;

	public ExitPanel(CSFrame frame){
		setLayout(new BorderLayout());

		this.frame = frame;
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ButtonHandler());
		exit.setPreferredSize(new Dimension(100,50));
		add(exit, BorderLayout.EAST);
	}
	private class ButtonHandler implements ActionListener{
		@Override 
		public void actionPerformed(ActionEvent e){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

		}
	}
}
class GenSubHeadingPanel extends JPanel{
	
	//Generic Subheading panel
	public GenSubHeadingPanel(String sub1, String sub2){

		setLayout(new GridLayout(1,3));
		add(new JLabel(sub1));
		add(new JLabel(sub2));
		add(new JLabel());
	}
}