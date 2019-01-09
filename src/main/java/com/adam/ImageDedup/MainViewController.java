package com.adam.ImageDedup;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.application.Platform;


public class MainViewController{
    public String inputPath;
    public String outputPath;

    public MainViewController(){

    }

    // Callback to update the progress bar on screen
    public void update(Scene sc, Double progress, String label){
        Platform.runLater(() -> {
                ((ProgressBar) sc.lookup("#progress")).setProgress(progress);
                ((Label) sc.lookup("#lbProgress")).setText(label);;
        });
    }


    public void btnBegin(OutputOptions opt, Scene sc){    
        Thread t = new Thread(() -> {
            try{ 
                InputManager im = new InputManager(inputPath);
                HashDedup hdd = new HashDedup(im);
                hdd.WriteOutput(outputPath, opt, (Double pro, String lbl) -> update(sc, pro, lbl));

                update(sc, 1.0, "DONE");
            } catch(Exception e){
                e.printStackTrace();
            }
        });
        t.start();
    }
}