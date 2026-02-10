package dev.juanito.view;

import dev.juanito.dao.NotaDAO;
import dev.juanito.model.Nota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class DlgListaNotas extends JDialog {

    private JTable tablaNotas;
    private DefaultTableModel modelo;
    private NotaDAO notaDAO;

    public DlgListaNotas(Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Lista de Notas");
        this.setSize(700, 450);
        this.setLocationRelativeTo(parent);
        this.getContentPane().setBackground(new Color(245, 247, 250));

        this.notaDAO = new NotaDAO();

        configurarTabla();

        configurarBotones();

        cargarNotas();
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Título", "Contenido", "Fecha"};
        
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaNotas = new JTable(modelo);

        // --- MEJORAS VISUALES TABLA ---
        tablaNotas.setRowHeight(35);
        tablaNotas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaNotas.setSelectionBackground(new Color(220, 230, 241));
        tablaNotas.setSelectionForeground(Color.BLACK);
        
        JTableHeader header = tablaNotas.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(Color.DARK_GRAY);

        tablaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaNotas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(new Color(245, 247, 250));

        JButton btnNueva = new JButton("Nueva nota");
        JButton btnEditar = new JButton("Editar nota");
        JButton btnBorrar = new JButton("Borrar nota");
        JButton btnCerrar = new JButton("Cerrar");

        JButton[] botones = {btnNueva, btnEditar, btnBorrar, btnCerrar};
        for (JButton btn : botones) {
            btn.setFont(new Font("SansSerif", Font.BOLD, 13));
            btn.setBackground(new Color(70, 130, 180));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
        }

        btnCerrar.setBackground(new Color(119, 136, 153));

        btnNueva.addActionListener(e -> {
            DlgEditarNota dialog = new DlgEditarNota(this, true, null);
            dialog.setVisible(true);
            cargarNotas();
        });


        btnEditar.addActionListener(e -> {
            int filaSeleccionada = tablaNotas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una nota para editar.");
                return;
            }
            
            int idNota = (int) tablaNotas.getValueAt(filaSeleccionada, 0);
            
            Nota notaAEditar = notaDAO.obtenerPorId(idNota);
            
            if (notaAEditar != null) {
                DlgEditarNota dialog = new DlgEditarNota(this, true, notaAEditar);
                dialog.setVisible(true);
                
                cargarNotas();
            }
        });


        btnBorrar.addActionListener(e -> {
            int filaSeleccionada = tablaNotas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una nota para borrar.");
                return;
            }
            
            int idNota = (int) tablaNotas.getValueAt(filaSeleccionada, 0);
            
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres borrar la nota " + idNota + "?");
            if (respuesta == JOptionPane.YES_OPTION) {
                if (notaDAO.eliminar(idNota)) {
                    cargarNotas();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al borrar la nota.");
                }
            }
        });


        btnCerrar.addActionListener(e -> this.dispose());

        panelBotones.add(btnNueva);
        panelBotones.add(btnEditar);
        panelBotones.add(btnBorrar);
        panelBotones.add(btnCerrar);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        this.add(panelBotones, BorderLayout.SOUTH);
    }


    private void cargarNotas() {

        modelo.setRowCount(0);
        
        List<Nota> lista = notaDAO.obtenerTodas();
        
        for (Nota n : lista) {
            modelo.addRow(new Object[]{
                n.getId(),
                n.getTitulo(),
                n.getContenido(),
                n.getFechaCreacion()
            });
        }
    }
}