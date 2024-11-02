package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Banco;
import modelo.Cuenta;
import modelo.Credito;

public class CreditoIntegracionTest {

	private Banco banco;
	private Cuenta unaCuenta;
	private Credito tarjeta;

	@BeforeEach
	public void setUp() {
		banco = new Banco("Banco Test");
		banco.abrirCuenta("06-456213-33", "Alfredo Hernandez");
		unaCuenta = banco.obtenerCuenta("06-456213-33");
		tarjeta = new Credito("1234567890123456", "Alfredo Hernandez", new Date(), 1000.0);
	}

	/*
	 * PRUEBAS DE INTEGRACION
	 * 
	 */

	@Test
	public void testRetirarConComision() {
		try {
			tarjeta.retirar(300.0);

			// Comprobación de la comisión
			double comision = Math.max(3.0, 300.0 * 0.05);
			double totalRetiro = 300.0 + comision;

			assertEquals(1000.0 - totalRetiro, tarjeta.getCreditoDisponible(), 0.001);
		} catch (Exception e) {
			fail("No debería lanzar excepción");
		}
	}

}
