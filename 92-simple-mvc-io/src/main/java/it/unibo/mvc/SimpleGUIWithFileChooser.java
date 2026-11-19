package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {
    private static String TITLE="SimpleGUIWithFileChooser4REAL";
    private final JFrame frame = new JFrame(TITLE);

    public SimpleGUIWithFileChooser(){
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.setContentPane(panel);

        //Button
        final JButton save = new JButton("Save");
        save.setFont(new Font("Helvetica",Font.PLAIN,50));
        JPanel pan4bot = new JPanel();
        pan4bot.add(save);
        panel.add(pan4bot,BorderLayout.PAGE_END);
        save.setPreferredSize(new Dimension(10000,200));
        

        //Text
        final JTextArea textArea = new JTextArea();
        panel.add(textArea,BorderLayout.CENTER);
        textArea.setFont(new Font("Arial",Font.PLAIN,200));

        //Controler
        final Controller cont = new Controller();
        save.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(final ActionEvent e){
                try{
                    cont.save(textArea.getText());
                }catch(IOException ex){
                    System.out.println("Errore del salvataggio : "+ex.getMessage());
                }
            }
        });


        //?
        final JTextField filepath = new JTextField(cont.getfile());
        //FileSetter
        final JButton browse = new JButton("Browse..");
        panel.add(browse,BorderLayout.PAGE_START);
        browse.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(final ActionEvent  e){
                final JFileChooser fc = new JFileChooser("Choose where to save");
                                final int result = fc.showSaveDialog(frame);
                switch (result) {
                case JFileChooser.APPROVE_OPTION:
                    final File newDest = fc.getSelectedFile();
                    cont.setfile(newDest);
                    pathuser.setText(newDest.getPath());
                    break;
                case JFileChooser.CANCEL_OPTION:
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, result, "Meh!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Set visible 
        frame.setVisible(true);
    }
}
