// package edu.radford;
// import java.awt.BorderLayout;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.beans.PropertyChangeEvent;
// import java.beans.PropertyChangeListener;
// import java.util.ArrayList;
// import java.util.Collections;
// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.SwingUtilities;
// import javax.swing.SwingWorker;
// import javax.swing.table.DefaultTableModel;

// /**
//  * Swing GUI that shows up to 20 Roblox games in a table
//  * Rank, Name and current players with a button that flips the order.
//  *
//  * @version 11-21-2026
//  * @since 10-24-2026
//  */

// public class View implements ActionListener, PropertyChangeListener {
//     private static final int Max_Games = 20;

//     private JFrame frame= new JFrame("Roblox Top Games");
//     private DefaultTableModel model = new DefaultTableModel(new String[]{"rank", "name", "current players"},0)
//     {
//         @Override
//         public boolean isCellEditable(int row, int coloumn)
//         {
//             return false;
//         }
//     };
//     private JTable table = new JTable(model);
//     private JButton orderButton = new JButton("Order: Highest to Lowest");
//     private JLabel status = new JLabel("Fetching game data...");
//     private SwingWorker<ArrayList<edu.radford.RobloxGame>,Void> worker;

//     //sorted by player count lowest to highest
//     private ArrayList<edu.radford.RobloxGame> games = new ArrayList<>();
//     private boolean descending = true;

//     /**
//      * Window shows while game data is loading
//      */
// public View(){

// }

// }



