package org.example;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        PoliticaCostoMatricula mockCosto = mock(PoliticaCostoMatricula.class);
        PoliticaDevolucion mockDevolucion = mock(PoliticaDevolucion.class);
        when(mockCosto.calcularCosto(curso)).thenReturn(400.0);

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo, mockCosto, mockDevolucion);

        // Assert
        assertEquals("Activa", matricula.getEstado());
        verify(mockCosto).calcularCosto(curso);
    }

    // PRUEBA UNITARIA 2
    @Test
    void matriculaDebeUsarLaPoliticaDeCostoMockeadaSinDescuento() {
        // Arrange
        Estudiante estudiante = new Estudiante("Luis", "Gomez", "A002", "luis@mail.com", "Sistemas");
        Curso curso = new Curso("Programación I", "PRO101", 4, 150.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 7, 31));

        PoliticaCostoMatricula mockCosto = mock(PoliticaCostoMatricula.class);
        PoliticaDevolucion mockDevolucion = mock(PoliticaDevolucion.class);
        when(mockCosto.calcularCosto(curso)).thenReturn(600.0);

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo, mockCosto, mockDevolucion);

        // Assert
        assertEquals(600.0, matricula.getCostoTotal(), 0.0001);
        verify(mockCosto).calcularCosto(curso);
        verifyNoInteractions(mockDevolucion);
    }

    // PRUEBA UNITARIA 3
    @Test
    void matriculaDebeAplicarElCostoMockeadoConDescuento() {
        // Arrange
        Estudiante estudiante = new Estudiante("Maria", "Lopez", "A003", "maria@mail.com", "Industrial");
        Curso curso = new Curso("Física", "FIS201", 5, 200.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 7, 31));

        PoliticaCostoMatricula mockCosto = mock(PoliticaCostoMatricula.class);
        PoliticaDevolucion mockDevolucion = mock(PoliticaDevolucion.class);
        when(mockCosto.calcularCosto(curso)).thenReturn(950.0);

        // Act
        Matricula matricula = new Matricula(estudiante, curso, periodo, mockCosto, mockDevolucion);

        // Assert
        assertEquals(950.0, matricula.getCostoTotal(), 0.0001);
        verify(mockCosto).calcularCosto(curso);
    }

    // PRUEBA INTEGRAL
    @Test
    void matriculaDebePermitirRetiroDentroDeLosSieteDiasYUsarLaPoliticaDeDevolucion() {
        // Arrange
        Estudiante estudiante = new Estudiante("Carlos", "Diaz", "A004", "carlos@mail.com", "Derecho");
        Curso curso = new Curso("Historia", "HIS101", 3, 120.0);
        PeriodoAcademico periodo = new PeriodoAcademico("2026-I",
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 7, 31));

        PoliticaCostoMatricula mockCosto = mock(PoliticaCostoMatricula.class);
        PoliticaDevolucion mockDevolucion = mock(PoliticaDevolucion.class);
        when(mockCosto.calcularCosto(curso)).thenReturn(360.0);
        when(mockDevolucion.calcularDevolucion(360.0)).thenReturn(252.0);

        Matricula matricula = new Matricula(estudiante, curso, periodo, mockCosto, mockDevolucion);

        // Act
        matricula.retirarCurso(LocalDate.now().plusDays(7));

        // Assert
        assertEquals("Retirada", matricula.getEstado());
        verify(mockCosto).calcularCosto(curso);
        verify(mockDevolucion).calcularDevolucion(360.0);
    }
}