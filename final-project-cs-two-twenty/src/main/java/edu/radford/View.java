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
//
// /**
//  * Swing GUI that shows up to 20 Roblox games in a table
//  * Rank, Name and current players with a button that flips the order.
//  *
//  * @version 11-21-2026
//  * @since 10-24-2026
//  */
//
// public class View implements ActionListener, PropertyChangeListener {
//     private static final int Max_Games = 20;
//
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
//
//     //sorted by player count lowest to highest
//     private ArrayList<edu.radford.RobloxGame> games = new ArrayList<>();
//     private boolean descending = true;
//
//     /**
//      * Window shows while game data is loading
//      */
// public View() {
//    orderButton.setEnabled(false);
//    orderButton.addActionListener(this);
//    table.setFillsViewportHeight(true);
//    table.getColumnModel().getColumn(0).setPreferredWidth(60);
//    table.getColumnModel().getColumn(1).setPreferredWidth(350);
//    table.getColumnModel().getColumn(2).setPreferredWidth(150);
//    frame.setLayout(new BorderLayout());
//    frame.add(orderButton,BorderLayout.NORTH);
//    frame.add(new JScrollPane(table),BorderLayout.CENTER);
//    frame.add(status, BorderLayout.SOUTH);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setSize(600,500);
//    frame.setLocationRelativeTo(null);
//    frame.setVisible(true);
//    loadGames();
// }
//
//     /**
//      * Retrieves and sorts game data
//      */
// private void loadGames() {
//     worker = new SwingWorker<Arraylist<edu.radford.RobloxGame>,Void>() {
//         @Override
//         protected ArrayList<edu.radford.RobloxGame>doInBackground() throws Exception{
//             edu.radford.RobloxDataRetriever retriever = new edu.radford.RobloxDataRetriever();
//             edu.radford.FileSort sorter = new edu.radford.FileSort();
//             ArrayList<edu.radford.RobloxGame> sorted = sorter.sortByPlayers(retriever.getCollection());
//             // lowest to highest
//             int from = Math.max(0, sorted.size() - Max_Games);
//             return new ArrayList<>(sorted.subList(from,sorted.size));
//         }
//     };
//        worker.addPropertyChangeListener(this);
//        worker.execute();
//     }
// }
//
// }



