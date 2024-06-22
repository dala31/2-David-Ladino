
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaGestionCursos {
    private List<Estudiante> estudiantes = new ArrayList<>();
    private List<Profesor> profesores = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();

    private JFrame ventana;
    private JTabbedPane cuaderno;
    private JPanel pestañaIngreso;
    private JPanel pestañaModificacion;

    private JTextField entradaNombreProfesor;
    private JTextField entradaApellidoProfesor;
    private JTextField entradaNombreEstudiante;
    private JTextField entradaNombreCurso;

    private JTree listaEstudiantes;
    private JTree listaProfesores;
    private JTree listaCursos;

    public SistemaGestionCursos() {
        inicializarGUI();
    }

    private void inicializarGUI() {
        ventana = new JFrame("Sistema de Gestión de Cursos");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(600, 400);
        ventana.getContentPane().setBackground(new Color(173, 216, 230)); // lightblue

        cuaderno = new JTabbedPane();
        ventana.add(cuaderno);

        pestañaIngreso = new JPanel();
        pestañaModificacion = new JPanel();

        cuaderno.addTab("Ingreso de Datos", pestañaIngreso);
        cuaderno.addTab("Modificación de Datos", pestañaModificacion);

        crearPestañaIngreso();
        crearPestañaModificacion();

        ventana.setVisible(true);
    }

    private void crearPestañaIngreso() {
        pestañaIngreso.setLayout(new BoxLayout(pestañaIngreso, BoxLayout.Y_AXIS));

        JLabel etiquetaTitulo = new JLabel("Ingreso de Datos");
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        pestañaIngreso.add(etiquetaTitulo);
        pestañaIngreso.add(Box.createRigidArea(new Dimension(0, 20)));

        pestañaIngreso.add(new JLabel("Nombre del Profesor:"));
        entradaNombreProfesor = new JTextField();
        pestañaIngreso.add(entradaNombreProfesor);

        pestañaIngreso.add(new JLabel("Apellido del Profesor:"));
        entradaApellidoProfesor = new JTextField();
        pestañaIngreso.add(entradaApellidoProfesor);

        pestañaIngreso.add(new JLabel("Nombre del Estudiante:"));
        entradaNombreEstudiante = new JTextField();
        pestañaIngreso.add(entradaNombreEstudiante);

        pestañaIngreso.add(new JLabel("Nombre del Curso:"));
        entradaNombreCurso = new JTextField();
        pestañaIngreso.add(entradaNombreCurso);

        JButton botonIngresar = new JButton("Ingresar");
        botonIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIngresar.addActionListener(e -> ingresarDatos());
        pestañaIngreso.add(Box.createRigidArea(new Dimension(0, 20)));
        pestañaIngreso.add(botonIngresar);
    }

    private void crearPestañaModificacion() {
        pestañaModificacion.setLayout(new BorderLayout());

        JLabel etiquetaTituloModificacion = new JLabel("Modificación de Datos", SwingConstants.CENTER);
        etiquetaTituloModificacion.setFont(new Font("Arial", Font.BOLD, 16));
        pestañaModificacion.add(etiquetaTituloModificacion, BorderLayout.NORTH);

        JTabbedPane notebookModificacion = new JTabbedPane();
        pestañaModificacion.add(notebookModificacion, BorderLayout.CENTER);

        JPanel pestañaEstudiantes = new JPanel(new BorderLayout());
        JPanel pestañaProfesores = new JPanel(new BorderLayout());
        JPanel pestañaCursos = new JPanel(new BorderLayout());

        notebookModificacion.addTab("Estudiantes", pestañaEstudiantes);
        notebookModificacion.addTab("Profesores", pestañaProfesores);
        notebookModificacion.addTab("Cursos", pestañaCursos);

        crearPestañaEstudiantes(pestañaEstudiantes);
        crearPestañaProfesores(pestañaProfesores);
        crearPestañaCursos(pestañaCursos);
    }

    private void crearPestañaEstudiantes(JPanel panel) {
        listaEstudiantes = new JTree(new DefaultMutableTreeNode("Estudiantes"));
        panel.add(new JScrollPane(listaEstudiantes), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton botonEliminarEstudiante = new JButton("Eliminar");
        JButton botonModificarEstudiante = new JButton("Modificar");
        botonEliminarEstudiante.addActionListener(e -> eliminarEstudiante());
        botonModificarEstudiante.addActionListener(e -> modificarEstudiante());
        panelBotones.add(botonEliminarEstudiante);
        panelBotones.add(botonModificarEstudiante);
        panel.add(panelBotones, BorderLayout.SOUTH);
    }

    private void crearPestañaProfesores(JPanel panel) {
        listaProfesores = new JTree(new DefaultMutableTreeNode("Profesores"));
        panel.add(new JScrollPane(listaProfesores), BorderLayout.CENTER);

        JButton botonModificarProfesor = new JButton("Modificar");
        botonModificarProfesor.addActionListener(e -> modificarProfesor());
        panel.add(botonModificarProfesor, BorderLayout.SOUTH);
    }

    private void crearPestañaCursos(JPanel panel) {
        listaCursos = new JTree(new DefaultMutableTreeNode("Cursos"));
        panel.add(new JScrollPane(listaCursos), BorderLayout.CENTER);

        JButton botonModificarCurso = new JButton("Modificar");
        botonModificarCurso.addActionListener(e -> modificarCurso());
        panel.add(botonModificarCurso, BorderLayout.SOUTH);
    }

    private void ingresarDatos() {
        String nombreProfesor = entradaNombreProfesor.getText();
        String apellidoProfesor = entradaApellidoProfesor.getText();
        String nombreEstudiante = entradaNombreEstudiante.getText();
        String nombreCurso = entradaNombreCurso.getText();

        Profesor profesor = new Profesor(nombreProfesor, apellidoProfesor);
        profesores.add(profesor);
        Estudiante estudiante = new Estudiante(nombreEstudiante);
        estudiantes.add(estudiante);
        Horario horario = new Horario("Lunes", "09:00", "11:00");
        Curso curso = new Curso(nombreCurso, profesor, new ArrayList<>(), horario);
        cursos.add(curso);
        estudiante.inscribirCurso(curso);

        actualizarListas();
        System.out.println("Datos ingresados correctamente.");
    }

    private void actualizarListas() {
        actualizarListaEstudiantes();
        actualizarListaProfesores();
        actualizarListaCursos();
    }

    private void actualizarListaEstudiantes() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Estudiantes");
        for (Estudiante estudiante : estudiantes) {
            root.add(new DefaultMutableTreeNode(estudiante.nombre));
        }
        ((DefaultTreeModel) listaEstudiantes.getModel()).setRoot(root);
    }

    private void actualizarListaProfesores() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Profesores");
        for (Profesor profesor : profesores) {
            root.add(new DefaultMutableTreeNode(profesor.nombre + " " + profesor.apellido));
        }
        ((DefaultTreeModel) listaProfesores.getModel()).setRoot(root);
    }

    private void actualizarListaCursos() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Cursos");
        for (Curso curso : cursos) {
            root.add(new DefaultMutableTreeNode(curso.nombre));
        }
        ((DefaultTreeModel) listaCursos.getModel()).setRoot(root);
    }

    private void eliminarEstudiante() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) listaEstudiantes.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getParent() != null) {
            int index = selectedNode.getParent().getIndex(selectedNode);
            estudiantes.remove(index);
            actualizarListaEstudiantes();
        }
    }

    private void modificarEstudiante() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) listaEstudiantes.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getParent() != null) {
            int index = selectedNode.getParent().getIndex(selectedNode);
            Estudiante estudiante = estudiantes.get(index);
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:", estudiante.nombre);
            if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                estudiante.nombre = nuevoNombre;
                actualizarListaEstudiantes();
            }
        }
    }

    private void modificarProfesor() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) listaProfesores.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getParent() != null) {
            int index = selectedNode.getParent().getIndex(selectedNode);
            Profesor profesor = profesores.get(index);
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:", profesor.nombre);
            String nuevoApellido = JOptionPane.showInputDialog("Ingrese el nuevo apellido:", profesor.apellido);
            if (nuevoNombre != null && !nuevoNombre.isEmpty() && nuevoApellido != null && !nuevoApellido.isEmpty()) {
                profesor.nombre = nuevoNombre;
                profesor.apellido = nuevoApellido;
                actualizarListaProfesores();
            }
        }
    }

    private void modificarCurso() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) listaCursos.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getParent() != null) {
            int index = selectedNode.getParent().getIndex(selectedNode);
            Curso curso = cursos.get(index);
            String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:", curso.nombre);
            if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                curso.nombre = nuevoNombre;
                actualizarListaCursos();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaGestionCursos::new);
    }
}

class Curso {
    String nombre;
    Profesor profesor;
    List<Estudiante> estudiantes;
    Horario horario;

    public Curso(String nombre, Profesor profesor, List<Estudiante> estudiantes, Horario horario) {
        this.nombre = nombre;
        this.profesor = profesor;
        this.estudiantes = estudiantes;
        this.horario = horario;
    }

    public void mostrarInfo() {
        System.out.println("Curso: " + nombre);
        System.out.println("Profesor: " + profesor.nombre);
        System.out.println("Estudiantes:");
        for (Estudiante estudiante : estudiantes) {
            System.out.println("- " + estudiante.nombre);
        }
        System.out.println("Horario: " + horario);
    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
        System.out.println("Estudiante " + estudiante.nombre + " agregado al curso " + nombre);
    }
}

class Profesor {
    String nombre;
    String apellido;
    List<Asignatura> asignaturas = new ArrayList<>();

    public Profesor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public void asignarAsignatura(Asignatura asignatura) {
        asignaturas.add(asignatura);
        System.out.println("Asignatura " + asignatura.nombre + " asignada al profesor " + nombre);
    }
}

class Estudiante {
    String nombre;
    List<Curso> cursos = new ArrayList<>();

    public Estudiante(String nombre) {
        this.nombre = nombre;
    }

    public void inscribirCurso(Curso curso) {
        cursos.add(curso);
        curso.agregarEstudiante(this);
        System.out.println("Estudiante " + nombre + " inscrito en el curso " + curso.nombre);
    }
}

class Asignatura {
    String nombre;
    Profesor profesor;

    public Asignatura(String nombre, Profesor profesor) {
        this.nombre = nombre;
        this.profesor = profesor;
        profesor.asignarAsignatura(this);
    }
}

class Evaluacion {
    Curso curso;
    Estudiante estudiante;
    double nota;

    public Evaluacion(Curso curso, Estudiante estudiante, double nota) {
        this.curso = curso;
        this.estudiante = estudiante;
        this.nota = nota;
    }

    public void mostrarInfo() {
        System.out.println("Evaluación del curso " + curso.nombre);
        System.out.println("Estudiante: " + estudiante.nombre);
        System.out.println("Nota: " + nota);
    }
}

class Horario {
    String dia;
    String horaInicio;
    String horaFin;

    public Horario(String dia, String horaInicio, String horaFin) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return dia + " de " + horaInicio + " a " + horaFin;
    }
}

