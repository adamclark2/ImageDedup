package com.adam.ImageDedup;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.*;
import java.util.function.*;

import com.drew.imaging.*;
import com.drew.metadata.exif.*;
import com.drew.metadata.*;

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

    private Calendar getImageModificationDate(File f) throws Exception{
        Metadata metadata = ImageMetadataReader.readMetadata(f);
        ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        Date date_original = directory.getDateOriginal();
        Calendar cal_original = Calendar.getInstance();
        cal_original.setTime(date_original);

        return cal_original;
    }

    private void writeFile(List<File> f, String outputDir, OutputOptions opt, String key) throws Exception{
        String date = "";
        if(opt.sortDate){
            long mod = f.get(0).lastModified();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(mod);
            date = c.get(Calendar.YEAR) + File.separator;
            if(opt.useExifTime){
                String date_old = date;
                try{
                    Calendar orig = getImageModificationDate(f.get(0));
                    if(orig.get(Calendar.YEAR) > 1900){
                        date = "" + orig.get(Calendar.YEAR) + File.separator;
                    }
                }catch (Exception e){
                    date = date_old;
                }
            }
        }

        File out = new File(outputDir + date);
        if(!out.exists()){
            out.mkdirs();
        }

        if(f.size() > 1){
            out = new File(outputDir + date + key);
            if(!out.exists()){
                out.mkdirs();
            }
        }

        if(!opt.dirsOnly && f.size() == 1){
            File to = new File(outputDir + date + f.get(0).getName());
            File tmp = f.get(0);
            FileUtils.copyFile(tmp, to);
            if(opt.useExifTime){
                try{
                    to.setLastModified(getImageModificationDate(to).getTimeInMillis());
                } catch(Exception e){
                    to.setLastModified(tmp.lastModified());
                }
            }else{
                to.setLastModified(tmp.lastModified());
            }

        }else if(!opt.dirsOnly && f.size() > 1){
            for(File tmp : f){
                int sep = 0;
                File to = new File(outputDir + date + key + File.separator + tmp.getName());
                while(to.exists()){
                    to = new File(outputDir + date + key + File.separator + (sep++) + tmp.getName());
                }
                FileUtils.copyFile(tmp, to);
                if(opt.useExifTime){
                    try{
                        to.setLastModified(getImageModificationDate(to).getTimeInMillis());
                    } catch(Exception e){
                        to.setLastModified(tmp.lastModified());
                    }
                }else{
                    to.setLastModified(tmp.lastModified());
                }
            }
        }
    }

    public void WriteOutput(String outputDir, OutputOptions opt, BiConsumer<Double,String> updateFunc) throws Exception{
        File f = new File(outputDir);
        if(!f.exists()){
            f.mkdirs();
        }

        if(!outputDir.substring(outputDir.length() - 2).equals(File.separator)){
            outputDir = outputDir + File.separator;
        }
        
        int i = 0;
        int max = dedup.keySet().size();
        for(String key : dedup.keySet()){
            List<File> files = dedup.get(key);
            writeFile(files, outputDir, opt, key);
            i++;
            if(i % 10 == 0){
                updateFunc.accept((i + 0.0)/max, "" + i + " of " + max);
            }
        }  
    }
}