package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import modelo.Banco;
import modelo.Cuenta;
import modelo.Movimiento;

public class MovimientoIntegracionTest {

	private Banco banco;
	private Cuenta cuenta;
	private Movimiento movimiento;

	@BeforeEach
	public void setUp() {
		banco = new Banco("Banco Test");
		cuenta = new Cuenta("06-456213-33", "Alfredo Hernandez", banco);
		movimiento = new Movimiento(banco);
	}

	/*
	 * PRUEBAS DE INTEGRACION
	 * 
	 */

	@Test
	public void testProcesarPagoAprobado() throws Exception {
		double cantidad = 100.0;

		boolean resultado = movimiento.procesarPago(cantidad, cuenta);
		assertTrue(resultado, "El pago debería procesarse exitosamente.");
	}

	@Test
	public void testProcesarPagoRechazado() {
		double cantidad = -200.0;

		Movimiento movimiento = new Movimiento(banco);

		Exception exception = assertThrows(Exception.class, () -> {
			movimiento.procesarPago(cantidad, cuenta);
		});

		assertEquals("No se puede procesar un importe negativo", exception.getMessage());
	}

	@Test
	public void testImporteNegativoNoProcesado() {
		double cantidad = -100.0;

		Exception exception = assertThrows(Exception.class, () -> {
			movimiento.procesarPago(cantidad, cuenta);
		});

		assertEquals("No se puede procesar un importe negativo", exception.getMessage());
	}

	@Test
	public void testMovimientoConComision() throws Exception {
		double cantidad = 200.0;
		movimiento.setImporte(cantidad);

		boolean resultado = movimiento.procesarPago(cantidad, cuenta);
		assertTrue(resultado, "El pago debería procesarse exitosamente.");
		assertEquals(cantidad, movimiento.getImporte(), "El importe debe reflejar el monto original.");
		assertEquals(50, movimiento.getComision(), "La comisión debe ser 50.");
	}
}
