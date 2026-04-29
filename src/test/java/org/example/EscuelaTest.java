package org.example;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EscuelaTest {

    // PRUEBA UNITARIA 1
    @Test
    void matriculaDebeIniciarEnEstadoActiva() {
        // Arrange
        Estudiante estudiante = new Estudiante("Ana", "Perez", "A001", "ana@mail.com", "Ingeniería");
        Curso curso = new Curso("Matemática", "MAT101", 4, 100.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 7, 31));

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo);

        // Assert
        assertEquals("Activa", matricula.getEstado());
    }

    // PRUEBA UNITARIA 2
    @Test
    void matriculaDebeCalcularCostoSinDescuentoCuandoTieneMenosDeCincoCreditos() {
        // Arrange
        Estudiante estudiante = new Estudiante("Luis", "Gomez", "A002", "luis@mail.com", "Sistemas");
        Curso curso = new Curso("Programación I", "PRO101", 4, 150.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 7, 31));

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo);

        // Assert
        assertEquals(600.0, matricula.getCostoTotal(), 0.0001);
    }

    // PRUEBA UNITARIA 3
    @Test
    void matriculaDebeAplicarDescuentoDelCincoPorCientoCuandoTieneCincoCreditosOMas() {
        // Arrange
        Estudiante estudiante = new Estudiante("Maria", "Lopez", "A003", "maria@mail.com", "Industrial");
        Curso curso = new Curso("Física", "FIS201", 5, 200.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 7, 31));

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo);

        // Assert
        assertEquals(950.0, matricula.getCostoTotal(), 0.0001);
    }

    // PRUEBA INTEGRAL
    @Test
    void matriculaDebePermitirRetiroDentroDeLosSieteDiasYCambiarEstadoARetirada() {
        // Arrange
        Estudiante estudiante = new Estudiante("Carlos", "Diaz", "A004", "carlos@mail.com", "Derecho");
        Curso curso = new Curso("Historia", "HIS101", 3, 120.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 7, 31));
        Matricula matricula = new Matricula(estudiante, curso, periodo);

        // Act
        matricula.retirarCurso(LocalDate.now().plusDays(7));

        // Assert
        assertEquals("Retirada", matricula.getEstado());
    }
}