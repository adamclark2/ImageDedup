# ImageDedup
De-Duplicate images.  
  
Let's say you have a bunch of images, some of them are copies of eachother.
You'd like an automated process to detect the duplicates and only keep one of
the images.

# Running Manually

        mvn package
        java -jar ./target/ImageDedup-2.jar