package com.adam.ImageDedup.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

/*
    The main View for users to select stuff
*/
public class MainView extends JFrame{
    public MainView(){
        super();
        
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JPanel dir = initDirChoose();
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
        add(initGo());

        // Spacer
        gbc.gridy = 999;
        gbc.gridx = 0;
        gbc.weighty = 100;
        add(new JPanel());
    }

    private JComponent initGo(){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setMaximumSize(new Dimension(9999999, 200));

        JButton go = new JButton("Go");
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

    private JPanel initDirChoose(){
        JPanel opts = new JPanel();
        opts.setBorder(BorderFactory.createTitledBorder("Directories"));
        opts.setLayout(new GridBagLayout());
        opts.setMaximumSize(new Dimension(99999999, 200));

        JLabel inLabel = new JLabel("Input Directory:");
        final JTextField inPath = new JTextField();        
        JButton chooseInDir = new JButton("Choose Dir");
        chooseInDir.addActionListener((ActionEvent e) -> {btnInputFile(inPath);});

        JLabel outLabel = new JLabel("Output Directory:");
        JTextField outPath = new JTextField();        
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
    }

    private void btnOutputFile(JTextField area){
        openFileSelect(area);
    }

    private void openFileSelect(JTextField area){
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int ret = fc.showOpenDialog(this);

        if(ret == JFileChooser.APPROVE_OPTION){
            // User chose somthing
            area.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }
}