package dev.juanito.view;

import dev.juanito.dao.NotaDAO;
import dev.juanito.model.Nota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DlgListaNotas extends JDialog {

    private JTable tablaNotas;
    private DefaultTableModel modelo;
    private NotaDAO notaDAO;

    public DlgListaNotas(Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Lista de Notas");
        this.setSize(600, 400);
        this.setLocationRelativeTo(parent);

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
        
        tablaNotas = new JTable(modelo);

        // --- MEJORAS VISUALES TABLA ---
        tablaNotas.setRowHeight(30);
        tablaNotas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaNotas);
        // Añadir un borde blanco alrededor para que respire
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        JButton btnNueva = new JButton("Nueva nota");
        JButton btnEditar = new JButton("Editar nota");
        JButton btnBorrar = new JButton("Borrar nota");
        JButton btnCerrar = new JButton("Cerrar");

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