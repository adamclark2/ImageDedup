package com.adam.ImageDedup.view;

import javafx.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

import java.net.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.beans.*;
import javafx.beans.value.*;
import javafx.*;
import javafx.application.*;


import java.io.*;

public class JavaFXAbout {
    public JavaFXAbout(Application a) throws Exception{
        Stage st = new Stage();
        st.initStyle(StageStyle.DECORATED);

        URL gui = ClassLoader.getSystemClassLoader().getResource("About.fxml");
        if(gui == null){
            gui = ClassLoader.getSystemClassLoader().getResource("/About.fxml");
        }

        Parent root = FXMLLoader.load(gui);
        Scene scene = new Scene(root);

        st.setScene(scene);
        st.show();

        ((Hyperlink) scene.lookup("#lnkSource")).setOnAction((e) -> lnkSourceCode(a, scene));
    }

    public void lnkSourceCode(Application a, Scene scene){
        Hyperlink lnk = ((Hyperlink) scene.lookup("#lnkSource"));
        System.out.println(lnk.getText());
        
        if(a != null && a.getHostServices() != null){
            a.getHostServices().showDocument(lnk.getText());
        }
    }
}