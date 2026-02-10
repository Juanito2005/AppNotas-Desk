package dev.juanito.view;

import javax.swing.*;
import java.awt.*;

public class FrmPrincipal extends JFrame {
    
    public FrmPrincipal() {

        this.setTitle("Gestor de Notas");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        this.getContentPane().setBackground(new Color(245, 247, 250));
        
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        
        Font fuenteGeneral = new Font("SansSerif", Font.PLAIN, 14);
        menuArchivo.setFont(fuenteGeneral);
        itemSalir.setFont(fuenteGeneral);
        
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);
        this.setJMenuBar(menuBar);

        JButton btnGestionar = new JButton("Abrir Gestor de Notas");
        
        btnGestionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGestionar.setBackground(new Color(70, 130, 180));
        btnGestionar.setForeground(Color.WHITE);
        btnGestionar.setFocusPainted(false);
        btnGestionar.setBorderPainted(false);
        btnGestionar.setPreferredSize(new Dimension(220, 50));

        btnGestionar.addActionListener(e -> {
               new DlgListaNotas(this, true).setVisible(true);

        });

        this.add(btnGestionar);
    }
}