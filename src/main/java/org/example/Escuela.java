package org.example;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Estudiante {
    private String nombre;
    private String apellido;
    private String codigo;
    private String email;
    private String carrera;

    public Estudiante(String nombre, String apellido, String codigo, String email, String carrera) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigo = codigo;
        this.email = email;
        this.carrera = carrera;
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getCarrera() { return carrera; }
}

class Curso {
    private String nombre;
    private String codigo;
    private int creditos;
    private double costoPorCredito;

    public Curso(String nombre, String codigo, int creditos, double costoPorCredito) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.creditos = creditos;
        this.costoPorCredito = costoPorCredito;
    }

    public String getNombre() { return nombre; }
    public int getCreditos() { return creditos; }
    public double getCostoPorCredito() { return costoPorCredito; }
}

class PeriodoAcademico {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public PeriodoAcademico(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getNombre() { return nombre; }
}

interface PoliticaCostoMatricula {
    double calcularCosto(Curso curso);
}

class PoliticaCostoMatriculaDefault implements PoliticaCostoMatricula {
    @Override
    public double calcularCosto(Curso curso) {
        double costo = curso.getCreditos() * curso.getCostoPorCredito();
        if (curso.getCreditos() >= 5) {
            costo *= 0.95;
        }
        return costo;
    }
}

interface PoliticaDevolucion {
    double calcularDevolucion(double costoTotal);
}

class PoliticaDevolucionDefault implements PoliticaDevolucion {
    @Override
    public double calcularDevolucion(double costoTotal) {
        return costoTotal * 0.70;
    }
}

class Matricula {
    private Estudiante estudiante;
    private Curso curso;
    private PeriodoAcademico periodo;
    private LocalDate fechaMatricula;
    private String estado; // "Activa", "Retirada", "Finalizada"
    private LocalDate fechaRetiro;
    private double costoTotal;

    private final PoliticaCostoMatricula politicaCosto;
    private final PoliticaDevolucion politicaDevolucion;

    public Matricula(Estudiante estudiante, Curso curso, PeriodoAcademico periodo) {
        this(estudiante, curso, periodo, new PoliticaCostoMatriculaDefault(), new PoliticaDevolucionDefault());
    }

    public Matricula(Estudiante estudiante,
                     Curso curso,
                     PeriodoAcademico periodo,
                     PoliticaCostoMatricula politicaCosto,
                     PoliticaDevolucion politicaDevolucion) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.periodo = periodo;
        this.politicaCosto = politicaCosto;
        this.politicaDevolucion = politicaDevolucion;
        this.fechaMatricula = LocalDate.now();
        this.estado = "Activa";
        this.costoTotal = politicaCosto.calcularCosto(curso);

        System.out.println("Matrícula registrada para " + estudiante.getNombre() +
                " en el curso " + curso.getNombre() +
                " del periodo " + periodo.getNombre() +
                ". Créditos: " + curso.getCreditos() +
                ". Costo total: S/" + costoTotal);
    }

    public void retirarCurso(LocalDate fechaRetiro) {
        long diasDesdeMatricula = ChronoUnit.DAYS.between(fechaMatricula, fechaRetiro);

        if (diasDesdeMatricula <= 7) {
            this.estado = "Retirada";
            this.fechaRetiro = fechaRetiro;
            double devolucion = politicaDevolucion.calcularDevolucion(costoTotal);
            System.out.println("Retiro realizado correctamente. Se aplicará devolución del 70%: S/" + devolucion);
        } else {
            System.out.println("No se puede retirar el curso. El retiro solo está permitido dentro de los primeros 7 días.");
        }
    }

    public void finalizarCurso() {
        this.estado = "Finalizada";
        System.out.println("Curso finalizado para el estudiante " + estudiante.getNombre());
    }

    public double getCostoTotal() { return costoTotal; }
    public String getEstado() { return estado; }
}