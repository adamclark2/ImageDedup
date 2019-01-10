package com.adam.ImageDedup.view;

import com.adam.ImageDedup.*;

import javafx.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.fxml.*;

import java.net.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.beans.*;
import javafx.beans.value.*;

import java.io.*;

public class JavaFXMainView {
    private MainViewController mvc;
    private Scene sc;
    private Stage st;

    private int tabIndex = 0;

    private EventHandler<ActionEvent> notImplemented = e -> {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Application Error");
        a.setHeaderText("This functionality is not implemented yet...");

        a.showAndWait();
    };

    public JavaFXMainView(MainViewController mvc, Stage s) throws Exception{
        this.mvc = mvc;
        this.st = s;
        s.initStyle(StageStyle.DECORATED);

        URL gui = ClassLoader.getSystemClassLoader().getResource("MainView.fxml");
        if(gui == null){
            gui = ClassLoader.getSystemClassLoader().getResource("/MainView.fxml");
        }
        Parent root = FXMLLoader.load(gui);

        Scene scene = new Scene(root);
        sc = scene;
        s.setScene(scene);

        s.show();

        initButtons();
        initCbox();
        initTabs();
    }

    private void initTabs(){
        TabPane p = (TabPane) sc.lookup("#TabPane");
        p.getSelectionModel().clearAndSelect(tabIndex);
        p.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                    tabIndex = (new Integer(t1.getId().substring(3))) - 1;
                }
            }
        );

        Button b = (Button) sc.lookup("#btnNext");
        b.setOnAction(e -> {
            tabIndex = (tabIndex + 1) > 4 ? 3 : tabIndex + 1;
            p.getSelectionModel().clearAndSelect(tabIndex);
        });

        b = (Button) sc.lookup("#btnPrevious");
        b.setOnAction(e -> {
            tabIndex = (tabIndex - 1) < 0 ? 0 : tabIndex - 1;
            p.getSelectionModel().clearAndSelect(tabIndex);
        });

        b = (Button) sc.lookup("#btnExit");
        b.setOnAction(e -> {
            System.exit(0);
        });


    }

    private void initButtons(){
        Button b = (Button) sc.lookup("#btnGo");
        b.setOnAction(e -> this.btnGo(e));

        b = (Button) sc.lookup("#btnInPath");
        b.setOnAction(e -> this.btnInPath());

        b = (Button) sc.lookup("#btnOutPath");
        b.setOnAction(e -> this.btnOutPath());
    }

    private void initCbox(){
        // Un implemented functionality
        CheckBox cbox = (CheckBox) sc.lookup("#chkExif");
        cbox.setOnAction(notImplemented);
    }

    public void btnGo(ActionEvent e){
        TabPane p = (TabPane) sc.lookup("#TabPane");
        tabIndex = 3;
        p.getSelectionModel().clearAndSelect(tabIndex);

        TextField in = (TextField) sc.lookup("#tfIn");
        TextField out = (TextField) sc.lookup("#tfOut");
        mvc.inputPath = in.getText();
        mvc.outputPath = out.getText();

        OutputOptions opt = new OutputOptions();
        opt.useExifTime = false;
        opt.sortDate = ((CheckBox) sc.lookup("#chkSortDate")).isSelected();;
        opt.dirsOnly = ((CheckBox) sc.lookup("#chkNoCopy")).isSelected();

        mvc.btnBegin(opt,sc);
    }

    public void btnInPath(){
        DirectoryChooser dc = new DirectoryChooser();
        File f = dc.showDialog(st);
        
        TextField in = (TextField) sc.lookup("#tfIn");
        if(f != null){
            in.setText(f.getAbsolutePath());
        }
    }

    public void btnOutPath(){
        DirectoryChooser dc = new DirectoryChooser();
        File f = dc.showDialog(st);

        TextField out = (TextField) sc.lookup("#tfOut");
        if(f != null){
            out.setText(f.getAbsolutePath());
        }
    }
}