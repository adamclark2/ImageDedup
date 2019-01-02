package com.adam.ImageDedup;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;

import org.apache.commons.io.FileUtils;

public class HashDedup{
    MessageDigest dig;

    Map<String, List<File>> dedup = new HashMap<String, List<File>>();

    public HashDedup(InputManager im){
        try{
            dig = MessageDigest.getInstance("md5");

            for(File tmp : im.imageFiles){
                String tmpHash = hashFile(tmp);

                if(!dedup.containsKey(tmpHash)){
                    dedup.put(tmpHash, new ArrayList<File>());
                }

                dedup.get(tmpHash).add(tmp);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String hashFile(File f) throws Exception{
        byte[] fileContents = Files.readAllBytes(f.toPath());
        dig.update(fileContents);
        byte[] hash = dig.digest();
        BigInteger hashInt = new BigInteger(hash);

        // Base 16 representation of hash
        return hashInt.toString(16);
    }

    @Override
    public String toString(){
        StringBuilder bldr = new StringBuilder();

        for(String key : dedup.keySet()){
            bldr.append(key);
            bldr.append(": [");
            for(File f : dedup.get(key)){
                bldr.append(f.getAbsolutePath());
                bldr.append(", ");
            }
            bldr.append("]\n");
        }  

        return bldr.toString();
    }

    public void WriteOutput(String outputDir) throws Exception{
        File f = new File(outputDir);
        if(!f.exists()){
            f.mkdirs();
        }

        for(String key : dedup.keySet()){
            List<File> files = dedup.get(key);
            if(files.size() == 1){
                FileUtils.copyFile(files.get(0), new File(outputDir + "/" + files.get(0).getName()));
            }else{
                // Multiple Files
                f = new File(outputDir + "/" + key);
                if(!f.exists()){
                    f.mkdirs();

                    for(File ff : files){
                        File out = new File(outputDir + "/" + key + "/" + ff.getName());
                        int idx = 1;
                        while(out.exists()){
                            out = new File(outputDir + "/" + key + "/" + (idx++) + ff.getName());
                        }

                        FileUtils.copyFile(ff, out);
                    }
                }
            }
        }  
    }
}