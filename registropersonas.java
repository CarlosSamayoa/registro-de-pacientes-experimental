import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class registropersonas {
    JFrame frame;
    JTabbedPane tabbedPane;
    JPanel inicioPanel, registroPanel, busquedaPanel, ayudaPanel;
    DefaultTableModel resultadosModel;
    JDialog dialogFrame;
    JTable dataTable;

    JTextField codigoField, nombresField, apellidosField, direccionField, fechaNacimientoField, edadField, dpiField,
            pesoField;
    JComboBox<String> sexoComboBox, pesoUnidadComboBox;
    JTextArea ayudaTextArea;

    public registropersonas() {
        frame = new JFrame("Registro de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        tabbedPane = new JTabbedPane();

        // Pestaña 1: Inicio
        inicioPanel = new JPanel();
        inicioPanel.setLayout(new BorderLayout());
        JLabel bienvenidaLabel = new JLabel("¡Bienvenido a la aplicación de registro!");
        bienvenidaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        bienvenidaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inicioPanel.add(bienvenidaLabel, BorderLayout.CENTER);
        tabbedPane.addTab("Inicio", inicioPanel);

        // Pestaña 2: Datos del Cliente
        registroPanel = new JPanel();
        registroPanel.setLayout(new GridLayout(14, 2));
        tabbedPane.addTab("Registro", registroPanel);

        codigoField = new JTextField();
        nombresField = new JTextField();
        apellidosField = new JTextField();
        direccionField = new JTextField();
        fechaNacimientoField = new JTextField();
        edadField = new JTextField();
        dpiField = new JTextField();
        pesoField = new JTextField();
        sexoComboBox = new JComboBox<>(new String[] { "Masculino", "Femenino", "Otro" });
        sexoComboBox.setSelectedIndex(0);
        pesoUnidadComboBox = new JComboBox<>(new String[] { "kg", "lb" });
        pesoUnidadComboBox.setSelectedIndex(0);

        agregarCampo("Código del Cliente:", codigoField);
        agregarCampo("Nombres:", nombresField);
        agregarCampo("Apellidos:", apellidosField);
        agregarCampo("Edad:", edadField);
        agregarCampo("Fecha de Nacimiento:", fechaNacimientoField);
        agregarCampo("Dirección:", direccionField);
        agregarCampo("Sexo:", sexoComboBox);
        agregarCampo("DPI:", dpiField);
        agregarCampo("Peso:", pesoField);
        agregarCampo("Unidad de Peso:", pesoUnidadComboBox);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });
        registroPanel.add(guardarButton);

        JButton nuevoButton = new JButton("Nuevo");
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        registroPanel.add(nuevoButton);

        // Pestaña 3: Historial
        ayudaPanel = new JPanel();
        ayudaPanel.setLayout(new BorderLayout());
        tabbedPane.addTab("Historial", ayudaPanel);

        dataTable = new JTable();
        ayudaPanel.add(new JScrollPane(dataTable), BorderLayout.CENTER);

        JButton cargarDatosButton = new JButton("Cargar Datos del CSV");
        cargarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosCSV();
            }
        });
        ayudaPanel.add(cargarDatosButton, BorderLayout.NORTH);

        busquedaPanel = new JPanel();
        busquedaPanel.setLayout(new BorderLayout());
        tabbedPane.addTab("Búsqueda", busquedaPanel);

        JPanel buscarPanel = new JPanel();
        buscarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField buscarNombreField = new JTextField(20);
        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreBuscado = buscarNombreField.getText();
                buscarRegistrosPorNombre(nombreBuscado);
            }
        });
        buscarPanel.add(new JLabel("Buscar por nombre:"));
        buscarPanel.add(buscarNombreField);
        buscarPanel.add(buscarButton);
        busquedaPanel.add(buscarPanel, BorderLayout.NORTH);

        resultadosModel = new DefaultTableModel();
        JTable resultadosTable = new JTable(resultadosModel);
        JScrollPane resultadosScrollPane = new JScrollPane(resultadosTable);
        busquedaPanel.add(resultadosScrollPane, BorderLayout.CENTER);

        JButton modificarButton = new JButton("Modificar");
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarRegistroSeleccionado(resultadosTable);
            }
        });
        busquedaPanel.add(modificarButton, BorderLayout.SOUTH);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    void agregarCampo(String label, JComponent component) {
        JLabel etiqueta = new JLabel(label);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 10));
        registroPanel.add(etiqueta);
        registroPanel.add(component);
    }

    void guardarDatos() {
        String codigo = codigoField.getText();
        String nombres = nombresField.getText();
        String apellidos = apellidosField.getText();
        String edad = edadField.getText();
        String fechaNacimiento = fechaNacimientoField.getText();
        String direccion = direccionField.getText();
        String sexo = (String) sexoComboBox.getSelectedItem();
        String dpi = dpiField.getText();
        String peso = pesoField.getText();
        String unidadPeso = (String) pesoUnidadComboBox.getSelectedItem();

        if (codigo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || edad.isEmpty() || fechaNacimiento.isEmpty()
                || direccion.isEmpty() || sexo.isEmpty() || dpi.isEmpty() || peso.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter("registro.csv", true))) {
                writer.print(codigo + ";");
                writer.print(nombres + ";");
                writer.print(apellidos + ";");
                writer.print(edad + ";");
                writer.print(fechaNacimiento + ";");
                writer.print(direccion + ";");
                writer.print(sexo + ";");
                writer.print(dpi + ";");
                writer.print(peso + ";");
                writer.print(unidadPeso);
                writer.println();

                System.out.println("Datos guardados correctamente");
                JOptionPane.showMessageDialog(frame, "Datos guardados con éxito.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                System.out.println("Error al guardar los datos: " + e.getMessage());
                JOptionPane.showMessageDialog(frame, "Error al ingresar los valores.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    void limpiarCampos() {
        codigoField.setText("");
        nombresField.setText("");
        apellidosField.setText("");
        edadField.setText("");
        fechaNacimientoField.setText("");
        direccionField.setText("");
        sexoComboBox.setSelectedIndex(0);
        dpiField.setText("");
        pesoField.setText("");
        pesoUnidadComboBox.setSelectedIndex(0);
    }

    void cargarDatosCSV() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Nombres");
        model.addColumn("Apellidos");
        model.addColumn("Edad");
        model.addColumn("Fecha de Nacimiento");
        model.addColumn("Dirección");
        model.addColumn("Sexo");
        model.addColumn("DPI");
        model.addColumn("Peso");
        model.addColumn("Unidad de Peso");

        try (BufferedReader reader = new BufferedReader(new FileReader("registro.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 10) {
                    model.addRow(parts);
                }
            }
            dataTable.setModel(model);
            resultadosModel = model;
            JOptionPane.showMessageDialog(frame, "Datos cargados desde el CSV.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.out.println("Error al cargar los datos del CSV: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error al cargar los datos desde el CSV.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarRegistrosPorNombre(String nombreBuscado) {
        if (nombreBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Ingresa un nombre para buscar.", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        ArrayList<String[]> matchingRows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("registro.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 10 && parts[1].toLowerCase().contains(nombreBuscado.toLowerCase())) {
                    matchingRows.add(parts);
                }
            }

            for (String[] row : matchingRows) {
                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(frame, "No se encontraron registros con similitudes al nombre ingresado.",
                        "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

            model.fireTableDataChanged();
        } catch (IOException e) {
            System.out.println("Error al buscar registros: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error al buscar registros.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarRegistroSeleccionado(JTable resultadosTable) {
        int selectedRow = resultadosTable.getSelectedRow();
        if (selectedRow != -1) {
            Object[] filaSeleccionada = new Object[resultadosModel.getColumnCount()];
            for (int i = 0; i < resultadosModel.getColumnCount(); i++) {
                filaSeleccionada[i] = resultadosModel.getValueAt(selectedRow, i);
            }

            abrirVentanaEdicion(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(frame, "Selecciona un registro para modificar.", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void abrirVentanaEdicion(Object[] datosRegistro) {
        dialogFrame = new JDialog(frame, "Editar Registro", true);
        dialogFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogFrame.setSize(400, 300);
        dialogFrame.setLayout(new GridLayout(11, 2));

        JTextField codigoField = new JTextField((String) datosRegistro[0]);
        JTextField nombresField = new JTextField((String) datosRegistro[1]);
        JTextField apellidosField = new JTextField((String) datosRegistro[2]);
        JTextField edadField = new JTextField((String) datosRegistro[3]);
        JTextField fechaNacimientoField = new JTextField((String) datosRegistro[4]);
        JTextField direccionField = new JTextField((String) datosRegistro[5]);
        JComboBox<String> sexoComboBox = new JComboBox<>(new String[] { "Masculino", "Femenino", "Otro" });
        sexoComboBox.setSelectedItem(datosRegistro[6]);
        JTextField dpiField = new JTextField((String) datosRegistro[7]);
        JTextField pesoField = new JTextField((String) datosRegistro[8]);
        JComboBox<String> pesoUnidadComboBox = new JComboBox<>(new String[] { "kg", "lb" });
        pesoUnidadComboBox.setSelectedItem(datosRegistro[9]);

        agregarCampoDialog("Código del Cliente:", codigoField);
        agregarCampoDialog("Nombres:", nombresField);
        agregarCampoDialog("Apellidos:", apellidosField);
        agregarCampoDialog("Edad:", edadField);
        agregarCampoDialog("Fecha de Nacimiento:", fechaNacimientoField);
        agregarCampoDialog("Dirección:", direccionField);
        agregarCampoDialog("Sexo:", sexoComboBox);
        agregarCampoDialog("DPI:", dpiField);
        agregarCampoDialog("Peso:", pesoField);
        agregarCampoDialog("Unidad de Peso:", pesoUnidadComboBox);

        JButton guardarButton = new JButton("Guardar Cambios");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambiosRegistro(datosRegistro, codigoField, nombresField, apellidosField, edadField,
                        fechaNacimientoField, direccionField, sexoComboBox, dpiField, pesoField, pesoUnidadComboBox);
            }
        });

        dialogFrame.add(guardarButton);
        dialogFrame.setVisible(true);
    }

    void agregarCampoDialog(String label, JComponent component) {
        JLabel etiqueta = new JLabel(label);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 10));
        dialogFrame.add(etiqueta);
        dialogFrame.add(component);
    }

    private void guardarCambiosRegistro(Object[] datosOriginales, JTextField codigoField, JTextField nombresField,
            JTextField apellidosField, JTextField edadField, JTextField fechaNacimientoField, JTextField direccionField,
            JComboBox<String> sexoComboBox, JTextField dpiField, JTextField pesoField,
            JComboBox<String> pesoUnidadComboBox) {
        String codigo = codigoField.getText();
        String nombres = nombresField.getText();
        String apellidos = apellidosField.getText();
        String edad = edadField.getText();
        String fechaNacimiento = fechaNacimientoField.getText();
        String direccion = direccionField.getText();
        String sexo = (String) sexoComboBox.getSelectedItem();
        String dpi = dpiField.getText();
        String peso = pesoField.getText();
        String unidadPeso = (String) pesoUnidadComboBox.getSelectedItem();

        if (codigo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || edad.isEmpty() || fechaNacimiento.isEmpty()
                || direccion.isEmpty() || sexo.isEmpty() || dpi.isEmpty() || peso.isEmpty()) {
            JOptionPane.showMessageDialog(dialogFrame, "Todos los campos son obligatorios.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                File archivoTemporal = new File("registro_temp.csv");
                PrintWriter writer = new PrintWriter(new FileWriter(archivoTemporal, true));

                try (BufferedReader reader = new BufferedReader(new FileReader("registro.csv"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (Arrays.equals(parts, datosOriginales)) {
                            String nuevaLinea = codigo + ";" + nombres + ";" + apellidos + ";" + edad + ";"
                                    + fechaNacimiento + ";" + direccion + ";" + sexo + ";" + dpi + ";" + peso + ";"
                                    + unidadPeso;
                            writer.println(nuevaLinea);
                        } else {
                            writer.println(line);
                        }
                    }
                }

                writer.close();

                archivoTemporal.renameTo(new File("registro.csv"));
                archivoTemporal.delete();

                for (int i = 0; i < resultadosModel.getColumnCount(); i++) {
                    resultadosModel.setValueAt(datosOriginales[i], resultadosModel.getRowCount() - 1, i);
                }

                dialogFrame.dispose();

                JOptionPane.showMessageDialog(frame, "Datos modificados con éxito.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                System.out.println("Error al guardar los cambios: " + e.getMessage());
                JOptionPane.showMessageDialog(frame, "Error al guardar los cambios.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void borrarRegistroSeleccionado(JTable resultadosTable) {
        int selectedRow = resultadosTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirmacion = JOptionPane.showConfirmDialog(frame,
                    "¿Estás seguro de que deseas borrar este registro?", "Confirmar Borrado",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                Object[] filaSeleccionada = new Object[resultadosModel.getColumnCount()];
                for (int i = 0; i < resultadosModel.getColumnCount(); i++) {
                    filaSeleccionada[i] = resultadosModel.getValueAt(selectedRow, i);
                }
                borrarRegistro(filaSeleccionada);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Selecciona un registro para borrar.", "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void borrarRegistro(Object[] datosRegistro) {
        File archivoTemporal = new File("registro_temp.csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivoTemporal, true));
                BufferedReader reader = new BufferedReader(new FileReader("registro.csv"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (!Arrays.equals(parts, datosRegistro)) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al borrar el registro: " + e.getMessage());
            JOptionPane.showMessageDialog(frame, "Error al borrar el registro.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        archivoTemporal.renameTo(new File("registro.csv"));
        archivoTemporal.delete();

        cargarDatosCSV();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new registropersonas();
            }
        });
    }
}
