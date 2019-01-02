package com.adam.ImageDedup;

public class MainViewController{
    public String inputPath;
    public String outputPath;

    public MainViewController(){

    }

    public void btnBegin(){
        try{     
            InputManager im = new InputManager(inputPath);
            HashDedup hdd = new HashDedup(im);
            
            hdd.WriteOutput(outputPath);

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}