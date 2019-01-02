package com.adam.ImageDedup.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.adam.ImageDedup.*;

/*
    The main View for users to select stuff
*/
public class MainView extends JFrame{
    private MainViewController ctrl;

    private final JTextField inPath = new JTextField();
    private final JTextField outPath = new JTextField();

    public MainView(MainViewController mvc){
        super();
        ctrl = mvc;
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        System.setProperty("apple.awt.fileDialogForDirectories", "true"); 
        JPanel dir = initDirChoose(mvc);
        JPanel algo = initChooseAlgorithm();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(dir);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(algo);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(initStatus());

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(initGo(mvc));

        // Spacer
        gbc.gridy = 999;
        gbc.gridx = 0;
        gbc.weighty = 100;
        add(new JPanel());
    }

    private JComponent initGo(MainViewController mvc){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setMaximumSize(new Dimension(9999999, 200));

        JButton go = new JButton("Go");
        go.addActionListener(e -> {
            mvc.inputPath = inPath.getText();
            mvc.outputPath = outPath.getText();
            mvc.btnBegin();
        });
        p.add(go, BorderLayout.CENTER);
        return p;
    }

    private JComponent initStatus(){
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createTitledBorder("Progress"));
        p.setLayout(new BorderLayout());
        p.setMaximumSize(new Dimension(9999999, 200));
        
        JProgressBar bar = new JProgressBar();
        bar.setValue(50);
        bar.setMaximum(100);
        p.add(bar, BorderLayout.CENTER);
        p.add(new JLabel("7 of 9"), BorderLayout.EAST);
        p.add(new JLabel("Blurb About Progress"), BorderLayout.SOUTH);
        return p;
    }

    private JPanel initChooseAlgorithm(){
        JPanel algo = new JPanel();
        algo.setBorder(BorderFactory.createTitledBorder("Algorithm"));
        algo.setMaximumSize(new Dimension(9999999, 200));

        String[] options = {"Hash", "Other"};
        JComboBox algoChoice = new JComboBox<String>(options);

        algo.setLayout(new BorderLayout());
        algo.add(new JLabel("Algorithm"), BorderLayout.WEST);
        algo.add(algoChoice, BorderLayout.CENTER);
        algo.add(new JLabel("Description Goes Here.............................................................."), BorderLayout.SOUTH);

        return algo;
    }

    private JPanel initDirChoose(MainViewController mvc){
        JPanel opts = new JPanel();
        opts.setBorder(BorderFactory.createTitledBorder("Directories"));
        opts.setLayout(new GridBagLayout());
        opts.setMaximumSize(new Dimension(99999999, 200));

        JLabel inLabel = new JLabel("Input Directory:");       
        JButton chooseInDir = new JButton("Choose Dir");
        chooseInDir.addActionListener((ActionEvent e) -> {btnInputFile(inPath);});

        JLabel outLabel = new JLabel("Output Directory:");         
        JButton chooseOutDir = new JButton("Choose Dir");
        chooseOutDir.addActionListener((ActionEvent e) -> {btnInputFile(outPath);});

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        opts.add(inLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 10;
        opts.add(inPath, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        opts.add(chooseInDir, gbc);




        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        opts.add(outLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 10;
        opts.add(outPath, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        opts.add(chooseOutDir, gbc);

        return opts;
    }

    private void btnInputFile(JTextField area){
        openFileSelect(area);
        ctrl.inputPath = area.getText() == null ? "" : area.getText();
    }

    private void btnOutputFile(JTextField area){
        openFileSelect(area);
        ctrl.outputPath = area.getText() == null ? "" : area.getText();
    }

    private void openFileSelect(JTextField area){
        FileDialog dia = new FileDialog(this);
        dia.setVisible(true);
        String dir = dia.getDirectory();

        if(dir != null && !dir.equals(""))
            area.setText(dir);
    }
}