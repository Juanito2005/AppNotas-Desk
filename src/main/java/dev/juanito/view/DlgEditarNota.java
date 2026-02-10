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
    private Font fontLabels = new Font("SansSerif", Font.BOLD, 14);
    private Font fontInputs = new Font("SansSerif", Font.PLAIN, 14);


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
        this.getContentPane().setBackground(new Color(245, 247, 250));
    }

    private void inicializarComponentes() {

        JPanel panelForm = new JPanel(new BorderLayout(10, 10)); 
        panelForm.setOpaque(false); // Para que se vea el color de fondo de la ventana
        
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // margenes

        // Titulo
        JPanel panelTitulo = new JPanel(new BorderLayout(5, 5));
        panelTitulo.setOpaque(false);
        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setFont(fontLabels);
        panelTitulo.add(lblTitulo, BorderLayout.NORTH);
        
        txtTitulo = new JTextField();
        txtTitulo.setFont(fontInputs);
        panelTitulo.add(txtTitulo, BorderLayout.CENTER);

        // Contenido
        JPanel panelContenido = new JPanel(new BorderLayout(5, 5));
        panelContenido.setOpaque(false);
        JLabel lblContenido = new JLabel("Contenido:");
        lblContenido.setFont(fontLabels);
        panelContenido.add(lblContenido, BorderLayout.NORTH);
        
        txtContenido = new JTextArea();
        txtContenido.setFont(fontInputs);
        txtContenido.setLineWrap(true);
        txtContenido.setWrapStyleWord(true);
        panelContenido.add(new JScrollPane(txtContenido), BorderLayout.CENTER);

        panelForm.add(panelTitulo, BorderLayout.NORTH);
        panelForm.add(panelContenido, BorderLayout.CENTER);
        
        this.add(panelForm, BorderLayout.CENTER);


        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        // Estilos botones
        btnGuardar.setBackground(new Color(70, 130, 180));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(fontLabels);
        
        btnCancelar.setBackground(new Color(119, 136, 153));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(fontLabels);

        btnGuardar.addActionListener(e -> accionGuardar());

        btnCancelar.addActionListener(e -> this.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 20));

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