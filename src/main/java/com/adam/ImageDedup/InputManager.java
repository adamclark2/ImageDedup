package com.adam.ImageDedup;

import java.io.File;
import java.util.*;

public class InputManager {
    public static long MAX_DEPTH = 10;

    public List<File> imageFiles = new ArrayList<File>();
    
    public InputManager(String path) throws Exception{
        File f = new File(path);
        addFileToPath(f, 0);
    }

    public boolean isImage(File f){
        if(!f.getName().contains(".")){
            return false;
        }

        String tmp = "";
        for(int i = f.getName().length() - 1;i > 0;i--){
            if(f.getName().charAt(i) == '.'){
                i = 0;
            }else{
                tmp = f.getName().charAt(i) + tmp;
            }
        }

        String image[] = {"jpeg", "jpg", "png", "bmp"};
        for(String s : image){
            if(s.equals(tmp.toLowerCase().trim())){
                return true;
            }
        }

        return false;
    }

    public void addFileToPath(File f, long depth){
        if(depth >= MAX_DEPTH){
            return;
        }

        if(f.isFile()){
            if(isImage(f)){
                imageFiles.add(f);
            }
        }else{
            for(File tmp : f.listFiles()){
                addFileToPath(tmp, ++depth);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder bldr = new StringBuilder();
        for(File f : this.imageFiles){
            bldr.append(f.getAbsolutePath() + "\n");
        }

        return bldr.toString();
    }
}