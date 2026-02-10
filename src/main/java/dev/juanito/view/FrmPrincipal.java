package dev.juanito.view;

import javax.swing.*;
import java.awt.*;

public class FrmPrincipal extends JFrame {
    
    public FrmPrincipal() {

        this.setTitle("Gestor de Notas");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir");
        
        itemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);
        this.setJMenuBar(menuBar);

        JButton btnGestionar = new JButton("Abrir Gestor de Notas");
        btnGestionar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGestionar.setPreferredSize(new Dimension(200, 50));


        btnGestionar.addActionListener(e -> {
               new DlgListaNotas(this, true).setVisible(true);

        });

        this.add(btnGestionar);
    }
}