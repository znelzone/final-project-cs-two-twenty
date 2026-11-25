package edu.radford;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

 /**
  * Swing GUI that shows up to 20 Roblox games in a table
  * Rank, Name and current players with a button that flips the order.
  *
  * @version 11-24-2026
  * @since 10-24-2026
  */

 public class View implements ActionListener, PropertyChangeListener {
     private static final int Max_Games = 50;

     private JFrame frame= new JFrame("Roblox Top Games");
     private DefaultTableModel model = new DefaultTableModel(new String[]{"rank", "name", "current players"},0)
     {
         @Override
         public boolean isCellEditable(int row, int coloumn)
         {
             return false;
         }
     };
     private JTable table = new JTable(model);
     private JButton orderButton = new JButton("Order: Highest to Lowest");
     private JLabel status = new JLabel("Getting game data...");
     private SwingWorker<ArrayList<edu.radford.RobloxGame>,Void> worker;
     //sorted by player count lowest to highest
     private ArrayList<edu.radford.RobloxGame> games = new ArrayList<>();
     private boolean descending = true;

     /**
      * Window shows while game data is loading
      */
 public View() {
    orderButton.setEnabled(false);
    orderButton.addActionListener(this);
    table.setFillsViewportHeight(true);
    table.getColumnModel().getColumn(0).setPreferredWidth(20);
    table.getColumnModel().getColumn(1).setPreferredWidth(350);
    table.getColumnModel().getColumn(2).setPreferredWidth(150);
    frame.setLayout(new BorderLayout());
    frame.add(orderButton,BorderLayout.NORTH);
    frame.add(new JScrollPane(table),BorderLayout.CENTER);
    frame.add(status, BorderLayout.SOUTH);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600,500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    loadGames();
 }

     /**
      * Retrieves and sorts game data
      */
 private void loadGames() {
     worker = new SwingWorker<ArrayList<edu.radford.RobloxGame>,Void>() {
         @Override
         protected ArrayList<edu.radford.RobloxGame>doInBackground() throws Exception{
             edu.radford.RobloxDataRetriever retriever = new edu.radford.RobloxDataRetriever();
             edu.radford.FileSort sorter = new edu.radford.FileSort();
             ArrayList<edu.radford.RobloxGame> sorted = sorter.sortByPlayers(retriever.getCollection());
             // lowest to highest
             int from = Math.max(0, sorted.size() - Max_Games);
             return new ArrayList<>(sorted.subList(from, sorted.size()));
         }
     };
        worker.addPropertyChangeListener(this);
        worker.execute();
     }


     /**
      * Fills in table using current order selected
      * This will always be based on play count
      */

     private void refreshTable() {
         model.setRowCount(0);
         int size = games.size();

         if (descending) {
             for (int i = size - 1; i >= 0; i--) {
                 addRow(i, size);
             }
         } else {
             for (int i = 0; i < size; i++) {
                 addRow(i, size);
             }
         }
         orderButton.setText(descending ? "Order: High to Low" : "Order Low to High");
         status.setText("Displaying top" +" " + size + " " + "games");
     }
     private void addRow(int index,int size){
         RobloxGame game = games.get(index);
         int rank = size - index;
         model.addRow(new Object[]{rank,game.getName(),String.format("%,d", game.getPlaying())});

     }

     /**
      * Makes button work
      */

     @Override
     public void actionPerformed(ActionEvent e){
         descending= !descending;
         refreshTable();
     }

     /**
      *
      */
     @Override
     public void propertyChange(PropertyChangeEvent evt){
         if("state".equals(evt.getPropertyName())
             && evt.getNewValue() == SwingWorker.StateValue.DONE){
             try {
                 games = worker.get();
                 refreshTable();
                 orderButton.setEnabled(true);
             }catch (InterruptedException | ExecutionException ex) {
                 status.setText("Could not load game data");
                 JOptionPane.showMessageDialog(frame, "Error loading games; " + ex.getMessage());

             }
         }
     }

     /**
      * Starts Gui
      */
     public static void main(String[] args){
         SwingUtilities.invokeLater(View::new);
     }
 }



