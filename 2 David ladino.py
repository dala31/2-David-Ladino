import tkinter as tk
from tkinter import ttk
import tkinter.simpledialog


class Curso:
    def __init__(self, nombre, profesor, estudiantes, horario):
        self.nombre = nombre
        self.profesor = profesor
        self.estudiantes = estudiantes
        self.horario = horario

    def mostrar_info(self):
        print(f"Curso: {self.nombre}")
        print(f"Profesor: {self.profesor.nombre}")
        print("Estudiantes:")
        for estudiante in self.estudiantes:
            print(f"- {estudiante.nombre}")
        print(f"Horario: {self.horario}")

    def agregar_estudiante(self, estudiante):
        self.estudiantes.append(estudiante)
        print(f"Estudiante {estudiante.nombre} agregado al curso {self.nombre}")

class Profesor:
    def __init__(self, nombre, apellido):
        self.nombre = nombre
        self.apellido = apellido
        self.asignaturas = []

    def asignar_asignatura(self, asignatura):
        self.asignaturas.append(asignatura)
        print(f"Asignatura {asignatura.nombre} asignada al profesor {self.nombre}")

class Estudiante:
    def __init__(self, nombre):
        self.nombre = nombre
        self.cursos = []

    def inscribir_curso(self, curso):
        self.cursos.append(curso)
        curso.agregar_estudiante(self)
        print(f"Estudiante {self.nombre} inscrito en el curso {curso.nombre}")

class Asignatura:
    def __init__(self, nombre, profesor):
        self.nombre = nombre
        self.profesor = profesor
        profesor.asignar_asignatura(self)

class Evaluacion:
    def __init__(self, curso, estudiante, nota):
        self.curso = curso
        self.estudiante = estudiante
        self.nota = nota

    def mostrar_info(self):
        print(f"Evaluación del curso {self.curso.nombre}")
        print(f"Estudiante: {self.estudiante.nombre}")
        print(f"Nota: {self.nota}")

class Horario:
    def __init__(self, dia, hora_inicio, hora_fin):
        self.dia = dia
        self.hora_inicio = hora_inicio
        self.hora_fin = hora_fin

    def __str__(self):
        return f"{self.dia} de {self.hora_inicio} a {self.hora_fin}"


class SistemaGestionCursos:
    def __init__(self):
        self.estudiantes = []
        self.profesores = []
        self.cursos = []

        self.ventana = tk.Tk()
        self.ventana.title("Sistema de Gestión de Cursos")
        self.ventana.geometry("600x400")
        self.ventana.configure(bg="lightblue")

        self.cuaderno = ttk.Notebook(self.ventana)
        self.cuaderno.pack(fill=tk.BOTH, expand=True)

        self.pestaña_ingreso = ttk.Frame(self.cuaderno)
        self.pestaña_modificacion = ttk.Frame(self.cuaderno)

        self.cuaderno.add(self.pestaña_ingreso, text="Ingreso de Datos")
        self.cuaderno.add(self.pestaña_modificacion, text="Modificación de Datos")

        self.crear_pestaña_ingreso()
        self.crear_pestaña_modificacion()

        self.ventana.mainloop()

    def crear_pestaña_ingreso(self):
        self.etiqueta_titulo = ttk.Label(self.pestaña_ingreso, text="Ingreso de Datos", font=("Arial", 16))
        self.etiqueta_titulo.pack(pady=20)

        self.etiqueta_nombre_profesor = ttk.Label(self.pestaña_ingreso, text="Nombre del Profesor:")
        self.etiqueta_nombre_profesor.pack()
        self.entrada_nombre_profesor = ttk.Entry(self.pestaña_ingreso)
        self.entrada_nombre_profesor.pack()

        self.etiqueta_apellido_profesor = ttk.Label(self.pestaña_ingreso, text="Apellido del Profesor:")
        self.etiqueta_apellido_profesor.pack()
        self.entrada_apellido_profesor = ttk.Entry(self.pestaña_ingreso)
        self.entrada_apellido_profesor.pack()

        self.etiqueta_nombre_estudiante = ttk.Label(self.pestaña_ingreso, text="Nombre del Estudiante:")
        self.etiqueta_nombre_estudiante.pack()
        self.entrada_nombre_estudiante = ttk.Entry(self.pestaña_ingreso)
        self.entrada_nombre_estudiante.pack()

        self.etiqueta_nombre_curso = ttk.Label(self.pestaña_ingreso, text="Nombre del Curso:")
        self.etiqueta_nombre_curso.pack()
        self.entrada_nombre_curso = ttk.Entry(self.pestaña_ingreso)
        self.entrada_nombre_curso.pack()

        self.boton_ingresar = ttk.Button(self.pestaña_ingreso, text="Ingresar", command=self.ingresar_datos)
        self.boton_ingresar.pack(pady=20)

    
    def ingresar_datos(self):
        nombre_profesor = self.entrada_nombre_profesor.get()
        apellido_profesor = self.entrada_apellido_profesor.get()
        nombre_estudiante = self.entrada_nombre_estudiante.get()
        nombre_curso = self.entrada_nombre_curso.get()

        profesor = Profesor(nombre_profesor, apellido_profesor)
        self.profesores.append(profesor)
        estudiante = Estudiante(nombre_estudiante)
        self.estudiantes.append(estudiante)
        horario = Horario("Lunes", "09:00", "11:00")
        curso = Curso(nombre_curso, profesor, [], horario)
        self.cursos.append(curso)
        estudiante.inscribir_curso(curso)

        self.actualizar_listas()
        print("Datos ingresados correctamente.")
    def crear_pestaña_modificacion(self):
        self.etiqueta_titulo_modificacion = ttk.Label(self.pestaña_modificacion, text="Modificación de Datos", font=("Arial", 16))
        self.etiqueta_titulo_modificacion.pack(pady=20)

        self.notebook_modificacion = ttk.Notebook(self.pestaña_modificacion)
        self.notebook_modificacion.pack(fill=tk.BOTH, expand=True)

        self.pestaña_estudiantes = ttk.Frame(self.notebook_modificacion)
        self.pestaña_profesores = ttk.Frame(self.notebook_modificacion)
        self.pestaña_cursos = ttk.Frame(self.notebook_modificacion)

        self.notebook_modificacion.add(self.pestaña_estudiantes, text="Estudiantes")
        self.notebook_modificacion.add(self.pestaña_profesores, text="Profesores")
        self.notebook_modificacion.add(self.pestaña_cursos, text="Cursos")

        self.crear_pestaña_estudiantes()
        self.crear_pestaña_profesores()
        self.crear_pestaña_cursos()

    
    def crear_pestaña_estudiantes(self):
        self.lista_estudiantes = ttk.Treeview(self.pestaña_estudiantes, columns=("Nombre",))
        self.lista_estudiantes.heading("Nombre", text="Nombre")
        self.lista_estudiantes.pack(fill=tk.BOTH, expand=True)

        self.boton_eliminar_estudiante = ttk.Button(self.pestaña_estudiantes, text="Eliminar", command=self.eliminar_estudiante)
        self.boton_eliminar_estudiante.pack(side=tk.LEFT, padx=10, pady=10)

        self.boton_modificar_estudiante = ttk.Button(self.pestaña_estudiantes, text="Modificar", command=self.modificar_estudiante)
        self.boton_modificar_estudiante.pack(side=tk.LEFT, padx=10, pady=10)
    def crear_pestaña_profesores(self):
        self.lista_profesores = ttk.Treeview(self.pestaña_profesores, columns=("Nombre", "Apellido"))
        self.lista_profesores.heading("Nombre", text="Nombre")
        self.lista_profesores.heading("Apellido", text="Apellido")
        self.lista_profesores.pack(fill=tk.BOTH, expand=True)

        self.boton_modificar_profesor = ttk.Button(self.pestaña_profesores, text="Modificar", command=self.modificar_profesor)
        self.boton_modificar_profesor.pack(side=tk.LEFT, padx=10, pady=10)
    def crear_pestaña_cursos(self):
        self.lista_cursos = ttk.Treeview(self.pestaña_cursos, columns=("Nombre",))
        self.lista_cursos.heading("Nombre", text="Nombre")
        self.lista_cursos.pack(fill=tk.BOTH, expand=True)

        self.boton_modificar_curso = ttk.Button(self.pestaña_cursos, text="Modificar", command=self.modificar_curso)
        self.boton_modificar_curso.pack(side=tk.LEFT, padx=10, pady=10)
    
    def actualizar_listas(self):
        self.lista_estudiantes.delete(*self.lista_estudiantes.get_children())
        for estudiante in self.estudiantes:
            self.lista_estudiantes.insert("", tk.END, values=(estudiante.nombre,))

        self.lista_profesores.delete(*self.lista_profesores.get_children())
        for profesor in self.profesores:
            self.lista_profesores.insert("", tk.END, values=(profesor.nombre, profesor.apellido))

        self.lista_cursos.delete(*self.lista_cursos.get_children())
        for curso in self.cursos:
            self.lista_cursos.insert("", tk.END, values=(curso.nombre,))

    def eliminar_estudiante(self):
        seleccion = self.lista_estudiantes.selection()
        if seleccion:
            indice = self.lista_estudiantes.index(seleccion)
            self.estudiantes.pop(indice)
            self.actualizar_lista_estudiantes()

    def modificar_estudiante(self):
        seleccion = self.lista_estudiantes.selection()
        if seleccion:
            indice = self.lista_estudiantes.index(seleccion)
            estudiante = self.estudiantes[indice]
            nuevo_nombre = tk.simpledialog.askstring("Modificar Estudiante", "Ingrese el nuevo nombre:", initialvalue=estudiante.nombre)
            if nuevo_nombre:
                estudiante.nombre = nuevo_nombre
                self.actualizar_lista_estudiantes()
    def modificar_profesor(self):
        seleccion = self.lista_profesores.selection()
        if seleccion:
            indice = self.lista_profesores.index(seleccion)
            profesor = self.profesores[indice]
            nuevo_nombre = tk.simpledialog.askstring("Modificar Profesor", "Ingrese el nuevo nombre:", initialvalue=profesor.nombre)
            nuevo_apellido = tk.simpledialog.askstring("Modificar Profesor", "Ingrese el nuevo apellido:", initialvalue=profesor.apellido)
            if nuevo_nombre and nuevo_apellido:
                profesor.nombre = nuevo_nombre
                profesor.apellido = nuevo_apellido
                self.actualizar_listas()

    def modificar_curso(self):
        seleccion = self.lista_cursos.selection()
        if seleccion:
            indice = self.lista_cursos.index(seleccion)
            curso = self.cursos[indice]
            nuevo_nombre = tk.simpledialog.askstring("Modificar Curso", "Ingrese el nuevo nombre:", initialvalue=curso.nombre)
            if nuevo_nombre:
                curso.nombre = nuevo_nombre
                self.actualizar_listas()

sistema = SistemaGestionCursos()