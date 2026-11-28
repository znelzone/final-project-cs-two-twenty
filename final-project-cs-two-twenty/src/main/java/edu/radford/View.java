package edu.radford;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

 /**
  * Swing GUI that shows up to 500 Roblox games in a table
  * Search Bar allowing user to search game up by name or game id
  * Displays the rank and name of game based on current players, total likes, or like %
  * Button dedicated to Displaying info from highest to lowest or vise versa
  * @version 11-28-2026
  * @since 10-24-2026
  */

 public class View implements ActionListener, PropertyChangeListener {
     // Options for num of games displayed and ways to sort games
     private static final int [] gameCounts = {20, 50, 100, 500};
     private static final String[] sortingHeaders = {"Current Players", "Likes", "Like %"};

     private JFrame frame= new JFrame("Roblox Top Games");
     private DefaultTableModel model = new DefaultTableModel(new String[]{"Rank", "Name", "Current players"},0)
     {
         @Override
         public boolean isCellEditable(int row, int coloumn)
         {
             return false;
         }
     };
     private JTable table = new JTable(model);
     private JButton orderButton = new JButton("Order: Highest to Lowest");
     private JTextField searchField = new JTextField(20);
     private JButton searchButton = new JButton("Search");
     private JLabel status = new JLabel("Getting game data...");
     private static final String[] sortingNames = {"Player Count", "Likes", "Likes to Dislikes"};
     private JRadioButton[] sortButtons = new JRadioButton[sortingNames.length];
     private JRadioButton[] countButtons = new JRadioButton[gameCounts.length];
     private SwingWorker<ArrayList<edu.radford.RobloxGame>,Void> worker;
     private ArrayList<edu.radford.RobloxGame> allGames = new ArrayList<>();
     private ArrayList<edu.radford.RobloxGame> games = new ArrayList<>();
     private boolean descending = true;

     //===================================================== CONSTRUCTOR =====================================================

     /**
      * Window shows while game data is loading
      */
 public View() {
    orderButton.setEnabled(false);
    orderButton.addActionListener(this);
    searchButton.addActionListener(e->searchGame());
    searchField.addActionListener(e->searchGame());
    table.setFillsViewportHeight(true);
    // Rank / Name / Likes sizes
    table.getColumnModel().getColumn(0).setPreferredWidth(40);
    table.getColumnModel().getColumn(1).setPreferredWidth(350);
    table.getColumnModel().getColumn(2).setPreferredWidth(150);
    JPanel searchPanel=new JPanel();
    searchPanel.add(new JLabel("Game Name or Universe ID:"));
    searchPanel.add(searchField);
    searchPanel.add(searchButton);
    // Will allow user to choose sorting option
    JPanel sortPanel = new JPanel();
    sortPanel.add(new JLabel("Sort by:"));
    ButtonGroup sortGroup = new ButtonGroup();
    for (int i = 0; i < sortingNames.length; i++){
        sortButtons [i] = new JRadioButton(sortingNames[i],i == 0);
        sortButtons [i].setEnabled(false);
        sortButtons [i].addActionListener(this);
        sortGroup.add(sortButtons[i]);
        sortPanel.add(sortButtons[i]);
    }
    // Will allow user to select number of games they would like to display
    JPanel countPanel = new JPanel();
    countPanel.add(new JLabel("Games Displayed:"));
    ButtonGroup countGroup = new ButtonGroup();
    for(int i = 0; i < gameCounts.length; i++){
        countButtons [i] = new JRadioButton(String.valueOf(gameCounts[i]),i == 0);
        countButtons [i].setEnabled(false);
        countButtons [i].addActionListener(this);
        countGroup.add(countButtons[i]);
        countPanel.add(countButtons[i]);
    }
    // Panel for interactables
    JPanel choicesPanel = new JPanel(new GridLayout(3,1));
    choicesPanel.add(sortPanel);
    choicesPanel.add(countPanel);
    choicesPanel.add(orderButton);
    JPanel topPanel=new JPanel(new BorderLayout());
    topPanel.add(searchPanel,BorderLayout.NORTH);
    topPanel.add(choicesPanel,BorderLayout.CENTER);
    frame.setLayout(new BorderLayout());
    frame.add(topPanel, BorderLayout.NORTH);
    frame.add(new JScrollPane(table),BorderLayout.CENTER);
    frame.add(status, BorderLayout.SOUTH);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700,550);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    loadGames();
 }

    //===================================================== METHODS =============================================================

     /**
      * Retrieves and sorts game data
      */
 private void loadGames() {
     worker = new SwingWorker<ArrayList<RobloxGame>, Void>() {
         @Override
         protected ArrayList<RobloxGame> doInBackground() throws Exception {
             RobloxDataRetriever retriever = new RobloxDataRetriever();
             FileSort sorter = new FileSort();
             // Sort by Player counter
             return sorter.sortByPlayers(retriever.getCollection());
         }
     };
     worker.addPropertyChangeListener(this);
     worker.execute();
 }

     /**
      * Returns index of selected button in its group
      * @param buttons
      * @return
      */
 private int selectedIndex(JRadioButton[] buttons){
     for (int i = 0; i < buttons.length; i++) {
         if (buttons[i].isSelected()) {
             return i;
         }
     }
     return 0;
 }

     /**
      * Turns buttons on or off
      * @param enabled
      */
     private void setOptionsEnabled(boolean enabled) {
         for (JRadioButton b : sortButtons) {
             b.setEnabled(enabled);
         }
         for (JRadioButton b : countButtons) {
             b.setEnabled(enabled);
         }
     }

     /**
      * Returns the number for currently selected sort choices
      * @param game
      * @param sortChoice
      * @return
      */
     private double statOf(RobloxGame game, int sortChoice) {
         if (sortChoice == 0) {
             return game.getPlaying();
         } else if (sortChoice == 1) {
             return game.getUpVotes();
         }
         return game.calcLikeToDislike(game.getUpVotes(), game.getDownVotes());
     }
     /**
      * Fills in table using selected sort choice
      * Rank 1 is always highest game in that valute
      * Runs when a new buttons is clicked or order button is clicked
      */

     private void refreshTable() {
         int sortChoice = selectedIndex(sortButtons);
         int limit = gameCounts[selectedIndex(countButtons)];
         ArrayList<RobloxGame> sorted = new ArrayList<>(allGames);
         sorted.sort(Comparator.comparingDouble(g -> statOf(g, sortChoice)));
         int from = Math.max(0, sorted.size() - limit);
         games = new ArrayList<>(sorted.subList(from, sorted.size()));
         model.setRowCount(0);
         int size = games.size();
         if (descending) {
             for (int i = size - 1; i >= 0; i--) {
                 addRow(i, size, sortChoice);
             }
         } else {
             for (int i = 0; i < size; i++) {
                 addRow(i, size, sortChoice);
             }
         }
         table.getColumnModel().getColumn(2).setHeaderValue(sortingHeaders[sortChoice]);
         table.getTableHeader().repaint();
         orderButton.setText(descending ? "Order: High to Low" : "Order: Low to High");
         status.setText("Displaying top " + size + " games by " + sortingNames[sortChoice]);
     }
     private void addRow(int index, int size, int sortChoice) {
         RobloxGame game = games.get(index);
         int rank = size - index;
         String value;
         if (sortChoice == 2) {
             value = String.format("%.1f%%", statOf(game, sortChoice));
         } else {
             value = String.format("%,d", (long) statOf(game, sortChoice));
         }
         model.addRow(new Object[]{rank, game.getName(), value});
     }

     /**
      * Search bar function
      */
     
     private void searchGame(){
         String query=searchField.getText().trim();
         if(query.isEmpty()){
             JOptionPane.showMessageDialog(frame,"Please enter a game name or Universe ID.");
             return;
         }
         searchButton.setEnabled(false);
         searchField.setEnabled(false);
         SwingWorker<String,Void> searchWorker=new SwingWorker<String,Void>(){
             @Override
             protected String doInBackground() throws Exception{
                 RobloxDataRetriever retriever=new RobloxDataRetriever(true);
                 if(query.matches("\\d+")){
                     RobloxGame game=retriever.getGameByUniverseID(Long.parseLong(query));
                     return game==null?"Game not found.":game.getInfo();
                 }
                 ArrayList<RobloxGame> results=retriever.searchGames(query);
                 StringBuilder output=new StringBuilder();
                 for(RobloxGame game:results){
                     output.append(game.getName()).append(" - ").append(game.getID()).append("\n");
                 }
                 return results.isEmpty()?"No games found.":output.toString();
             }
             @Override
             protected void done(){
                 try{
                     JTextArea resultsArea=new JTextArea(get(),20,45);
                     resultsArea.setEditable(false);
                     resultsArea.setCaretPosition(0);
                     JOptionPane.showMessageDialog(frame,new JScrollPane(resultsArea),"Search Results",JOptionPane.INFORMATION_MESSAGE);
                 }catch(Exception ex){
                     JOptionPane.showMessageDialog(frame,"Search failed: "+ex.getMessage());
                 }finally{
                     searchButton.setEnabled(true);
                     searchField.setEnabled(true);
                 }
             }
         };
         searchWorker.execute();
     }


     /**
      * Order Button functions
      */

     @Override
     public void actionPerformed(ActionEvent e){
         if (e.getSource() == orderButton) {
             descending = !descending;
         }
         refreshTable();
     }

     /**
      * Fills table after loading
      * Enables buttons
      */
     @Override
     public void propertyChange(PropertyChangeEvent evt){
         if("state".equals(evt.getPropertyName())
             && evt.getNewValue() == SwingWorker.StateValue.DONE){
             try {
                 allGames = worker.get();
                 refreshTable();
                 orderButton.setEnabled(true);
                 setOptionsEnabled(true);
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



