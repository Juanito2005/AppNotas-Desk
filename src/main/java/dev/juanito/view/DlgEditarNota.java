package dev.juanito.view;

import dev.juanito.dao.NotaDAO;
import dev.juanito.model.Nota;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class DlgEditarNota extends JDialog {

    private JTextField txtTitulo;
    private JTextArea txtContenido;
    private NotaDAO notaDAO;
    private Nota notaActual;
    private boolean guardado = false;


    public DlgEditarNota(Dialog parent, boolean modal, Nota notaParaEditar) {
        super(parent, modal);
        this.notaDAO = new NotaDAO();
        this.notaActual = notaParaEditar;

        configurarVentana();
        inicializarComponentes();
        

        if (notaActual != null) {
            cargarDatosNota();
            this.setTitle("Editar Nota");
        } else {
            this.setTitle("Nueva Nota");
        }
    }

    private void configurarVentana() {
        this.setSize(500, 400);
        this.setLocationRelativeTo(getParent());
        this.setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {

        JPanel panelForm = new JPanel(new BorderLayout(10, 10)); 
        
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // margenes

        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.add(new JLabel("Título:"), BorderLayout.NORTH);
        txtTitulo = new JTextField();
        panelTitulo.add(txtTitulo, BorderLayout.CENTER);

        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.add(new JLabel("Contenido:"), BorderLayout.NORTH);
        txtContenido = new JTextArea();
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        panelContenido.add(new JScrollPane(txtContenido), BorderLayout.CENTER);

        panelForm.add(panelTitulo, BorderLayout.NORTH);
        panelForm.add(panelContenido, BorderLayout.CENTER);
        
        this.add(panelForm, BorderLayout.CENTER);


        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> accionGuardar());

        btnCancelar.addActionListener(e -> this.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        this.add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarDatosNota() {
        txtTitulo.setText(notaActual.getTitulo());
        txtContenido.setText(notaActual.getContenido());
    }

    private void accionGuardar() {
        String titulo = txtTitulo.getText().trim();
        String contenido = txtContenido.getText();

        // Validación: Título obligatorio
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito;
        
        // LÓGICA BIPOLAR (Crear vs Editar)
        if (notaActual == null) {
            Nota nuevaNota = new Nota(titulo, contenido, new Date());
            exito = notaDAO.insertar(nuevaNota);
        } else {
            notaActual.setTitulo(titulo);
            notaActual.setContenido(contenido);
            notaActual.setFechaCreacion(new Date());
            
            exito = notaDAO.actualizar(notaActual);
        }

        if (exito) {
            guardado = true;
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.");
        }
    }
}