package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import modelo.Banco;
import modelo.Cuenta;
import modelo.Movimiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MovimientoTest {

	private Movimiento movimiento;
	private Cuenta cuenta;

	@Mock
	private Banco bancoMock;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		cuenta = new Cuenta("06-456213-33", "Alfredo Hernandez", bancoMock);
		movimiento = new Movimiento(bancoMock);

	}

	/*
	 * PRUEBAS UNITARIAS
	 */

	@Test
	public void testSetImporte() {
		movimiento.setImporte(150.0);
		assertEquals(150.0, movimiento.getImporte(), "Importe should be set correctly.");
	}

	@Test
	public void testGetConcepto() {
		String concepto = "Pago de prueba";
		movimiento.setConcepto(concepto);
		assertEquals(concepto, movimiento.getConcepto(), "Concepto should be returned correctly.");
	}

	@Test
	public void testGetComision() {
		assertEquals(50.0, movimiento.getComision(), "Comision should be initialized to 50.");
	}

	@Test
	public void testSetFecha() {
		Date fecha = new Date();
		movimiento.setFecha(fecha);
		assertEquals(fecha, movimiento.getFecha(), "Fecha should be set correctly.");
	}

	@Test
	public void testComisionNoNegativa() {
		double comision = movimiento.getComision();
		assertTrue(comision >= 0, "Comision should not be negative.");
	}

}
