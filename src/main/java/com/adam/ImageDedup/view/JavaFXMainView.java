package com.adam.ImageDedup.view;

import com.adam.ImageDedup.*;

import javafx.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

import java.net.*;
import javafx.scene.control.*;
import javafx.event.*;

import java.io.*;

public class JavaFXMainView {
    private MainViewController mvc;
    private Scene sc;
    private Stage st;

    public JavaFXMainView(MainViewController mvc, Stage s) throws Exception{
        this.mvc = mvc;
        this.st = s;
        URL gui = ClassLoader.class.getResource("/MainView.fxml");
        
        s.initStyle(StageStyle.DECORATED);
        Parent root = FXMLLoader.load(gui);
        Scene scene = new Scene(root);
        sc = scene;
        s.setScene(scene);

        s.show();

        Button b = (Button) scene.lookup("#btnGo");
        b.setOnAction(e -> this.btnGo(e));

        b = (Button) scene.lookup("#btnInPath");
        b.setOnAction(e -> this.btnInPath());

        b = (Button) scene.lookup("#btnOutPath");
        b.setOnAction(e -> this.btnOutPath());

        CheckBox cbox = (CheckBox) scene.lookup("#chkExif");
        cbox.setOnAction(e -> {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Application Error");
            a.setHeaderText("This functionality is not implemented yet...");

            a.showAndWait();
        });
    }

    public void btnGo(ActionEvent e){
        TextField in = (TextField) sc.lookup("#tfIn");
        TextField out = (TextField) sc.lookup("#tfOut");
        mvc.inputPath = in.getText();
        mvc.outputPath = out.getText();

        mvc.btnBegin();
    }

    public void btnInPath(){
        DirectoryChooser dc = new DirectoryChooser();
        File f = dc.showDialog(st);
        
        TextField in = (TextField) sc.lookup("#tfIn");
        in.setText(f.getAbsolutePath());
    }

    public void btnOutPath(){
        DirectoryChooser dc = new DirectoryChooser();
        File f = dc.showDialog(st);

        TextField out = (TextField) sc.lookup("#tfOut");
        out.setText(f.getAbsolutePath());
    }
}