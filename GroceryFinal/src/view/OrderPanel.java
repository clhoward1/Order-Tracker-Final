package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Customer;
import model.Item;
import model.Order;

@SuppressWarnings("serial")
public class OrderPanel extends JPanel {

    /**
     *  Priority Queue sorts the orders by hour
     */
    private PriorityQueue<Order> ordersQueue;
    private DefaultListModel<String> listModel; // model for the JList
    private JList<String> ordersList; // used for displaying the priority queue
    private ArrayList<Order> ordersArrayList; // the middle ground between the priority queue and JList

    /**
     * List of Orders Panel
     */
    private JPanel orderListPanel = new JPanel(new BorderLayout());

    private JLabel title = new JLabel("  Totally Real Stores Online Ordering  ");
    
    /**
     * Button Panel on bottom of window
     */
    private JButton addOrder = new JButton("Add Order");
    private JButton removeOrder = new JButton("Remove Order");
    private JButton viewOrder = new JButton("View Order");
    private JButton viewList = new JButton("View List");
    

    /**
     * Adding Orders Panel
     */
    private JPanel addOrderPanel = new JPanel(new GridLayout(0, 2));

    private JLabel addOrderIDLabel = new JLabel("Order ID:");
    private JTextField orderIDField = new JTextField();
    
    private JLabel addHourDueLabel = new JLabel("Hour Due:");
    private JTextField hourDueField = new JTextField();
    
    private JLabel addCustomerLabel = new JLabel("Customer:");
    private JTextField customerField = new JTextField();
    
    private JLabel addItemOrderLabel = new JLabel("Items in Order:");
    private JTextField itemOrderField = new JTextField();
    
    private JButton submitOrderButton = new JButton("Submit Order");

    /**
     * Selected Order Details Panel
     */
    private JPanel orderDetailsPanel = new JPanel(new GridLayout(0, 2));

    private JLabel orderIDLabel = new JLabel("Order ID:");
    private JLabel orderIDValue = new JLabel();
    
    private JLabel hourDueLabel = new JLabel("Order Due:");
    private JLabel hourDueValue = new JLabel();
    
    private JLabel customerLabel = new JLabel("Customer:");
    private JLabel customerValue = new JLabel();
    
    private JLabel itemOrderLabel = new JLabel("Items in Order:");
    private JLabel itemOrderValue = new JLabel();
    
    private JLabel isCompletedLabel = new JLabel("Completion Status:");
    private JLabel isCompletedValue = new JLabel();
    
    private JButton toggleCompletionButton = new JButton("Toggle Completion");

    /**
     * Main Panel
     */
    private JPanel cardPanel = new JPanel(new CardLayout());
    private static final String ORDER_LIST_PANEL = "OrderListPanel";
    private static final String ORDER_DETAILS_PANEL = "OrderDetailsPanel";
    private static final String ADD_ORDER_PANEL = "AddOrderPanel";

    public OrderPanel() {
        super(new BorderLayout());

        /**
         * Priority Queue initialized and it sorts by time order is due
         */
        ordersQueue = new PriorityQueue<>(Comparator.comparingInt(Order::getHourDue));

        /**
         * Initializes the Lists from above
         */
        ordersArrayList = new ArrayList<>();

        listModel = new DefaultListModel<>();
        ordersList = new JList<>(listModel);

        
        /**
         * Order List Panel
         */
        orderListPanel.add(title, BorderLayout.NORTH);
        orderListPanel.add(new JScrollPane(ordersList), BorderLayout.CENTER);

        
        /**
         * Add Order Panel
         */
        addOrderPanel.add(addOrderIDLabel);
        addOrderPanel.add(orderIDField);
        
        addOrderPanel.add(addHourDueLabel);
        addOrderPanel.add(hourDueField);
        
        addOrderPanel.add(addCustomerLabel);
        addOrderPanel.add(customerField);
        
        addOrderPanel.add(addItemOrderLabel);
        addOrderPanel.add(itemOrderField);
        
        addOrderPanel.add(new JLabel("")); // pushes button to right side

        SubmitOrderButtonListener submitListen = new SubmitOrderButtonListener();
        submitOrderButton.addActionListener(submitListen);
        addOrderPanel.add(submitOrderButton);
        
        
        /**
         * Order Details Panel
         */
        orderDetailsPanel.add(orderIDLabel);
        orderDetailsPanel.add(orderIDValue);
        
        orderDetailsPanel.add(hourDueLabel);
        orderDetailsPanel.add(hourDueValue);
        
        orderDetailsPanel.add(customerLabel);
        orderDetailsPanel.add(customerValue);
        
        orderDetailsPanel.add(itemOrderLabel);
        orderDetailsPanel.add(itemOrderValue);
        
        orderDetailsPanel.add(isCompletedLabel);
        orderDetailsPanel.add(isCompletedValue);
        
        orderDetailsPanel.add(new JLabel("")); // done for layout
        
        ToggleCompletionButtonListener compListen = new ToggleCompletionButtonListener();
        toggleCompletionButton.addActionListener(compListen);
        orderDetailsPanel.add(toggleCompletionButton);
        
        
        /**
         * Add all panels to Main Card Panel
         * And then add Card Panel to OrderPanel
         */
        cardPanel.add(orderListPanel, ORDER_LIST_PANEL);
        cardPanel.add(orderDetailsPanel, ORDER_DETAILS_PANEL);
        cardPanel.add(addOrderPanel, ADD_ORDER_PANEL);

        add(title, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        
        /**
         * Buttons on bottom of window
         */
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());

        AddButtonListener addListen = new AddButtonListener();
        addOrder.addActionListener(addListen);
        buttonPanel.add(addOrder);
        
        RemoveButtonListener removeListen = new RemoveButtonListener();
        removeOrder.addActionListener(removeListen);
        buttonPanel.add(removeOrder);

        ViewButtonListener viewListen = new ViewButtonListener();
        viewOrder.addActionListener(viewListen);
        buttonPanel.add(viewOrder);
        
        ListButtonListener listListen = new ListButtonListener();
        viewList.addActionListener(listListen);
        buttonPanel.add(viewList);
    }

    /**
     * Adds order to priority queue
     */
    public void addOrder(Order order) {
        ordersQueue.offer(order);
        updateOrderList();
    }

    /**
     * Updates list of orders
     */
    private void updateOrderList() {
        ordersArrayList.clear(); // Clear the display list
        listModel.clear(); // Clear the list model

        /**
         * Transfers orders from the priority queue to the list for display
         */
        PriorityQueue<Order> tempQueue = new PriorityQueue<>(ordersQueue);
        while (!tempQueue.isEmpty()) {
            Order o = tempQueue.poll();
            ordersArrayList.add(o);
            
            String isCompleted = "Not Complete";
            if (o.isCompleted()) {
            	isCompleted = "Completed";
            }
            
            /**
             * Example
             * 
             * Order ID: 123 - Name: Paul - Due: 6:00 - Status: Not Completed
             */
            listModel.addElement("Order ID: " + o.getOrderID() + " - Name: " + o.getCustomer().getName() + " - Due: " + o.getHourDue() + ":00 - Status: " + isCompleted);
        }
    }

    /**
     * Returns order from priority queue
     */
    private Order getOrderFromIndex(int index) {
        int i = 0;
        for (Order order : ordersQueue) {
            if (i == index) {
                return order;
            }
            i++;
        }
        return null;
    }

    
    /**
     * Updates the text in Order Detail Panel
     */
    private void updateOrderDetails(Order selectedOrder) {
        orderIDValue.setText(selectedOrder.getOrderID());
        hourDueValue.setText(String.valueOf(selectedOrder.getHourDue()));
        customerValue.setText(selectedOrder.getCustomer().toString());

        /**
         * Turns Item Linked List into string
         */
        StringBuilder itemOrderString = new StringBuilder("<html>");
        for (Item item : selectedOrder.getItemOrder()) {
            itemOrderString.append(item.toString()).append("<br>");
        }
        itemOrderString.append("</html>");
        itemOrderValue.setText(itemOrderString.toString());
        
        if (selectedOrder.isCompleted()) {
        	isCompletedValue.setText("Completed");
        } else {
        	isCompletedValue.setText("Not Completed");
        }
    }

    
    /**
     * Goes to Add Order Panel
     */
    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, ADD_ORDER_PANEL);
        }
    }
    
    
    /**
     * Adds info from fields in Add Order Panel to Order List
     */
    int custIDCounter = 0;
    class SubmitOrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	try {
        		
        		if (Integer.parseInt(orderIDField.getText()) < 0) {
        			throw new NumberFormatException("ID cannot be negative.");
        		}
	            String orderID = orderIDField.getText();
	            
	            if ((Integer.parseInt(hourDueField.getText()) < 1) || (Integer.parseInt(hourDueField.getText()) > 24)) {
        			throw new NumberFormatException("Hour must be whole number between 1 and 24.");
        		}
	            int hourDue = Integer.parseInt(hourDueField.getText());
	            Customer customer = new Customer(customerField.getText(), Integer.toString(custIDCounter));
	            custIDCounter++;
	
	            String[] items = itemOrderField.getText().split(",");
	            LinkedList<Item> itemOrder = new LinkedList<>();
	            for (String itemStr : items) {
	                String[] itemDetails = itemStr.split("-");
	                
	                /**
	                 * Inserting 12 Pears would be: Pear-12
	                 *
	                 * Inserting multiple items would be: Pear-12,Orange-3
	                 */
	                if (itemDetails.length < 2) {
	                    throw new IllegalArgumentException("Invalid item format. Each item should be in the format 'name-amount' with commas separating each item.");
	                }
	                
	                String itemName = itemDetails[0].trim();
	                int itemStock = Integer.parseInt(itemDetails[1].trim());
	                itemOrder.add(new Item(itemName, itemStock));
	            }
	
	            /**
	             * Creates a new order and adds it to priority queue
	             */
	            Order newOrder = new Order(orderID, hourDue, customer);
	            newOrder.setItemOrder(itemOrder);
	
	            addOrder(newOrder);
	
	            /**
	             * Clears the text fields
	             */
	            orderIDField.setText("");
	            hourDueField.setText("");
	            customerField.setText("");
	            itemOrderField.setText("");
	
	            /**
	             * Returns to List of Orders
	             */
	            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
	            cardLayout.show(cardPanel, ORDER_LIST_PANEL);
	            
        	} catch (NumberFormatException ex) {
        		JOptionPane.showMessageDialog(OrderPanel.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        	} catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(OrderPanel.this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    /**
     * Removes selected order from priority queue
     */
    class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	int selectedIndex = ordersList.getSelectedIndex();
            
        	/**
        	 * Checks if order is selected from list
        	 */
            if (selectedIndex != -1) {
            	Order selectedOrder = getOrderFromIndex(selectedIndex);
            	
            	/**
            	 * Checks if there is an order to remove and removes it 
            	 */
            	if (selectedOrder != null) {
                    ordersQueue.remove(selectedOrder);
                    updateOrderList();
            	}
            } else {
            	JOptionPane.showMessageDialog(OrderPanel.this, "No order selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    

    
    /**
     * Goes to Order Detail Panel and uses order selected from list
     */
    class ViewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = ordersList.getSelectedIndex();
            Order selectedOrder = getOrderFromIndex(selectedIndex);

            if (selectedOrder != null) {
                updateOrderDetails(selectedOrder);
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, ORDER_DETAILS_PANEL);
            }
        }
    }

    /**
     * Goes to List of Orders Panel
     */
    class ListButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, ORDER_LIST_PANEL);
        }
    }
    
    /**
     * Switches the isCompleted bool of the currently viewed order
     * 
     * Seems you have to switch panels for the button to work again after clicking it
     */
    class ToggleCompletionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = ordersList.getSelectedIndex();
            Order selectedOrder = getOrderFromIndex(selectedIndex);
            
            if (selectedOrder != null) {
                selectedOrder.setIsCompleted(!selectedOrder.isCompleted());
                updateOrderDetails(selectedOrder);
                updateOrderList();
            }
        }
    }
}