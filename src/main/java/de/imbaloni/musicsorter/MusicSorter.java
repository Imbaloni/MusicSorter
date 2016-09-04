package de.imbaloni.musicsorter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;


public class MusicSorter extends JFrame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JButton begin;
    JTextArea eingabe;
    JTextArea output;
    List<String> list = new LinkedList<String>();
    String[] name;
    File[] listOfFiles;
    File file;

    private MusicSorter(){
        super("MusicFinder0.1");

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // For exiting the program
        WindowAdapter adapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };

        this.addWindowListener(adapter);
        this.addWindowFocusListener(adapter);

        this.setSize(500, 500);
        JPanel panel= new JPanel(new BorderLayout());
        this.add(panel);
        begin = new JButton("Start");
        eingabe =  new JTextArea("Pfad");
        eingabe.setEditable(true);
        output = new JTextArea("ausgabe");
        output.setEditable(false);
        panel.add(eingabe,BorderLayout.CENTER);
        panel.add(begin,BorderLayout.PAGE_START);
        panel.add(output,BorderLayout.SOUTH);
        begin.addActionListener(this);



        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.begin ){

            output.setText("");
            File file = new File(eingabe.getText());
            listOfFiles = file.listFiles();
            for (File f : listOfFiles){
                String filename = f.getName();
                String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
                if (f.isFile() && extension.equals("mp3")){
                    list.add(f.getName());


                    String interpret = filename.substring(0, filename.indexOf('-'));
                    String foldername = eingabe.getText()+File.separator+interpret;
                    System.out.println(eingabe.getText()+File.separator+filename);
                    foldername=foldername.replaceAll("\\s+$", "");
                    System.out.println(foldername+File.separator+filename);
                    File folder = new File(foldername);
                    if(!folder.exists()){
                        folder.mkdirs();
                    }

                    Path moveSourcePath = Paths.get( eingabe.getText()+File.separator+filename ); //"C:\\Downloads\\Mind Surf feat daoko - STAR GUiTAR.mp3"
                    Path moveTargetPath = Paths.get( foldername+File.separator+filename ); //"C:\\Downloads\\Mind Surf feat daoko\\Mind Surf feat daoko - STAR GUiTAR.mp3"
                    try {
                        Files.move( moveSourcePath, moveTargetPath,StandardCopyOption.REPLACE_EXISTING );
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }

                output.append(f.getName()+"\n");
            }

        }
        output.append(""+list.size());
    }


    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                @SuppressWarnings("unused")
                MusicSorter mf = new MusicSorter();
            }
        });
    }
}
