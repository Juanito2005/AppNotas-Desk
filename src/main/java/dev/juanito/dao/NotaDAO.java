package dev.juanito.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import dev.juanito.model.Nota;

public class NotaDAO {

    public NotaDAO() {}

    public boolean insertar(Nota nota) {
        String sql = "INSERT INTO nota (titulo, contenido, fecha_creacion) VALUES (?, ?, ?)";

        // Esto es un try-with-resources, seguridad adicional por si las moscas
        try (Connection connection = ConnectionDB.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nota.getTitulo());
            preparedStatement.setString(2, nota.getContenido());
            preparedStatement.setDate(3, new java.sql.Date(nota.getFechaCreacion().getTime()));
            int filasInsertadas = preparedStatement.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM nota WHERE id = ?";
        try (Connection connection = ConnectionDB.connection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            int filasEliminadas = preparedStatement.executeUpdate();
            return filasEliminadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    public Nota obtenerPorId(int id) {
        String sql = "SELECT * FROM nota WHERE id = ?";
        Nota nota = null;

        try (Connection connection = ConnectionDB.connection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { // Si rs.next() es true, encontramos una fila
                    nota = new Nota(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("contenido"),
                        rs.getTimestamp("fecha_creacion")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener por ID: " + e.getMessage());
        }
        return nota;
    }

    public List<Nota> obtenerTodas() {
        List<Nota> listaNotas = new ArrayList<>();
        String sql = "SELECT * FROM nota";

        try (Connection connection = ConnectionDB.connection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Se va a repetir as long as new rows exists
            while (rs.next()) {
                Nota nota = new Nota(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("contenido"),
                    rs.getTimestamp("fecha_creacion")
                );
                listaNotas.add(nota);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar notas: " + e.getMessage());
        }
        return listaNotas;
    }

    public boolean actualizar(Nota nota) {

        String sql = "UPDATE nota SET titulo = ?, contenido = ?, fecha_creacion = ? WHERE id = ?";

        try (Connection connection = ConnectionDB.connection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nota.getTitulo());
            ps.setString(2, nota.getContenido());
            ps.setTimestamp(3, new java.sql.Timestamp(nota.getFechaCreacion().getTime()));
            
            ps.setInt(4, nota.getId());

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }
    
}
