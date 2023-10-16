import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroPacientes {
    JFrame frame;
    JTabbedPane tabbedPane;
    JPanel registroPanel, datosPanel, ayudaPanel;
    JTextField codigoField, nombresField, apellidosField, dpiField, direccionField, ciudadField, fechaNacimientoField,
            edadField, pesoField;
    JComboBox<String> sexoComboBox, pesoUnidadComboBox; // Agregamos un ComboBox para la unidad de peso
    JTextArea ayudaTextArea;

    public RegistroPacientes() {
        frame = new JFrame("Registro de Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        tabbedPane = new JTabbedPane();

        // Pestaña 1: Registro de Clientes
        registroPanel = new JPanel();
        registroPanel.setLayout(new BorderLayout());

        JLabel tituloLabel = new JLabel("Registro de Pacientes");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registroPanel.add(tituloLabel, BorderLayout.NORTH);

        JLabel bienvenidaLabel = new JLabel("¡Bienvenido Proceda a la pagina de registro!");
        bienvenidaLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        bienvenidaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registroPanel.add(bienvenidaLabel, BorderLayout.CENTER);

        ImageIcon logo = new ImageIcon(
                "C:\\Users\\scajl\\Documents\\UMG Segundo a\u00F1o\\4to semestre\\progra 2\\JAVAUMG\\lab 6\\logo.png");

        JLabel logoLabel = new JLabel(logo);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registroPanel.add(logoLabel, BorderLayout.SOUTH);
        tabbedPane.addTab("Registro", registroPanel);

        // Pestaña 2: Datos del Cliente
        datosPanel = new JPanel();
        datosPanel.setLayout(new GridLayout(12, 2));
        tabbedPane.addTab("Datos del Cliente", datosPanel);

        codigoField = new JTextField();
        nombresField = new JTextField();
        apellidosField = new JTextField();
        dpiField = new JTextField();
        direccionField = new JTextField();
        ciudadField = new JTextField();
        fechaNacimientoField = new JTextField();
        sexoComboBox = new JComboBox<>(new String[] { "Masculino", "Femenino", "Otro" });
        sexoComboBox.setSelectedIndex(0);
        edadField = new JTextField();
        pesoField = new JTextField();
        pesoUnidadComboBox = new JComboBox<>(new String[] { "kg", "lb" });
        pesoUnidadComboBox.setSelectedIndex(0);

        agregarCampo("Código del Cliente:", codigoField);
        agregarCampo("Nombres:", nombresField);
        agregarCampo("Apellidos:", apellidosField);
        agregarCampo("DPI:", dpiField);
        agregarCampo("Dirección:", direccionField);
        agregarCampo("Ciudad:", ciudadField);
        agregarCampo("Fecha de Nacimiento:", fechaNacimientoField);
        agregarCampo("Sexo:", sexoComboBox);
        agregarCampo("Edad:", edadField);
        agregarCampo("Peso:", pesoField);
        agregarCampo("Unidad de Peso:", pesoUnidadComboBox);

        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });
        datosPanel.add(guardarButton);

        JButton nuevoButton = new JButton("Nuevo");
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        datosPanel.add(nuevoButton);

        // Pestaña 3: Ayuda
        ayudaPanel = new JPanel();
        ayudaPanel.setLayout(new BorderLayout());
        tabbedPane.addTab("Ayuda", ayudaPanel);

        ayudaTextArea = new JTextArea();
        ayudaTextArea.setText(
                "Instrucciones para llenar el formulario:\n1. Completa todos los campos obligatorios.\n2. Haz clic en el botón 'Guardar' para guardar los datos.\n3. Utiliza el botón 'Nuevo' para ingresar un nuevo cliente.");
        ayudaTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        ayudaTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ayudaTextArea);
        ayudaPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    void agregarCampo(String label, JComponent component) {
        JLabel etiqueta = new JLabel(label);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 10));
        datosPanel.add(etiqueta);
        datosPanel.add(component);
    }

    void guardarDatos() {
        String codigo = codigoField.getText();
        String nombres = nombresField.getText();
        String apellidos = apellidosField.getText();
        String dpi = dpiField.getText();
        String direccion = direccionField.getText();
        String ciudad = ciudadField.getText();
        String fechaNacimiento = fechaNacimientoField.getText();
        String sexo = (String) sexoComboBox.getSelectedItem();
        String edad = edadField.getText();
        String peso = pesoField.getText();
        String unidadPeso = (String) pesoUnidadComboBox.getSelectedItem(); // Obtenemos la unidad de peso

        if (codigo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || dpi.isEmpty() || direccion.isEmpty()
                || ciudad.isEmpty() || fechaNacimiento.isEmpty() || sexo.isEmpty() || edad.isEmpty()
                || peso.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos los campos son obligatorios.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Código: " + codigo);
            System.out.println("Nombres: " + nombres);
            System.out.println("Apellidos: " + apellidos);
            System.out.println("DPI: " + dpi);
            System.out.println("Dirección: " + direccion);
            System.out.println("Ciudad: " + ciudad);
            System.out.println("Fecha de Nacimiento: " + fechaNacimiento);
            System.out.println("Sexo: " + sexo);
            System.out.println("Edad: " + edad);
            System.out.println("Peso: " + peso + " " + unidadPeso); // Imprimimos la unidad de peso junto con el peso
        }
    }

    void limpiarCampos() {
        codigoField.setText("");
        nombresField.setText("");
        apellidosField.setText("");
        dpiField.setText("");
        direccionField.setText("");
        ciudadField.setText("");
        fechaNacimientoField.setText("");
        sexoComboBox.setSelectedIndex(0);
        edadField.setText("");
        pesoField.setText("");
        pesoUnidadComboBox.setSelectedIndex(0); // Establecemos kg como valor predeterminado al limpiar
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegistroPacientes();
            }
        });
    }
}
