package it.unibo.mvc;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * A very simple program using a graphical interface.
 *      
 */
public final class SimpleGUI {
    private static String TITLE ="Simple GUI TITLE";
    private final JFrame frame = new JFrame(TITLE);

    public SimpleGUI(){
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        final JButton save = new JButton("Save");
        save.setFont(new Font("Helvetica",Font.PLAIN,50));
        
        //panel.add(save,BorderLayout.CENTER);
        
        JPanel pan4bot = new JPanel();
        pan4bot.add(save);
        panel.add(pan4bot,BorderLayout.PAGE_END);
        save.setPreferredSize(new Dimension(10000,200));
        

        final JTextArea textArea = new JTextArea();
        panel.add(textArea,BorderLayout.CENTER);
        textArea.setFont(new Font("Arial",Font.PLAIN,200));
        final Controller contr = new Controller();
        save.addActionListener(new ActionListener(){
            @Override 
            public void actionPerformed(final ActionEvent e){
                try{
                    contr.save(textArea.getText());
                }catch(IOException ex){
                    System.out.println("Errore nel salvataggio :"+ex.getMessage());
                }
            }
        });
    }

    private void display(){
        final Dimension dimensionn = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw =(int)dimensionn.getWidth();//(int) --> Serve per trasformare un dato di un altro tipo(double) in int.
        final int sh =(int)dimensionn.getHeight();
        frame.setSize(sw,sh);
        
    }
   public static void main(){
        new SimpleGUI().display();
    }   
}
