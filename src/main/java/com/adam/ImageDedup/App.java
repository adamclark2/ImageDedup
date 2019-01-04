package com.adam.ImageDedup;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.adam.ImageDedup.view.*;

import javafx.application.*;
import javafx.stage.*;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    @Override
    public void start(Stage s){
        try{
        MainViewController mvc = new MainViewController();
        JavaFXMainView jfxmv = new JavaFXMainView(mvc, s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) 
    {
        /*
        try{
            setLookAndFeel();
        }catch(Exception e){
            System.err.println("ERROR:\n");
            e.printStackTrace();
        }*/

        launch(args);

        /*
        MainView mv = new MainView(mvc);
        mv.setSize(380, 500);
        mv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mv.setTitle("Image Dedup");
        mv.setVisible(true);
        */
    }

    public static void setLookAndFeel() throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
}
