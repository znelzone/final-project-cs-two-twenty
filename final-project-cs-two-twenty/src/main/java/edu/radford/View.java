package edu.radford;
import javax.swing.*;
import java.beans.*;
// import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//work in progress class


//version 10-26-2026
//since older than 10-24

public class View implements ActionListener, PropertyChangeListener
{
    private JFrame frame = new JFrame ("Awesome Gui");

    public View()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(600,400);
        frame.setVisible(true);
        frame.setResizable(false);

    }
    @Override
    public void actionPerformed(ActionEvent arg0)
    {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {

    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new View());
    }

}
