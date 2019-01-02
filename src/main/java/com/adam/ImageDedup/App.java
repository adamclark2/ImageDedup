package com.adam.ImageDedup;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.adam.ImageDedup.view.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try{
            setLookAndFeel();
        }catch(Exception e){
            System.err.println("ERROR:\n");
            e.printStackTrace();
        }

        MainView mv = new MainView(new MainViewController());
        mv.setSize(380, 500);
        mv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mv.setTitle("Image Dedup");
        mv.setVisible(true);
    }

    public static void setLookAndFeel() throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
}
