package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modelo.Banco;
import modelo.Cuenta;
import modelo.Movimiento;

import java.util.Date;

public class BancoIntegracionTest {

	private Banco banco = new Banco();

	/*
	 * PRUEBAS DE INTEGRACION
	 * 
	 */

	@Test
	public void testAbrirCuentaYObtenerla() {
		banco.abrirCuenta("06-456213-33", "Alfredo Hernandez");

		Cuenta cuenta = banco.obtenerCuenta("06-456213-33");
		assertNotNull(cuenta);
		assertEquals("06-456213-33", cuenta.getmNumero());
	}

	@Test
	public void testAprobarOperacionRetiroConSaldoSuficiente() throws Exception {
		banco.abrirCuenta("06-456213-33", "Alfredo Hernandez");
		Cuenta cuentaReal = banco.obtenerCuenta("06-456213-33");
		cuentaReal.ingresar(500.0);

		Movimiento movimiento = new Movimiento();
		movimiento.setImporte(-300.0);
		assertTrue(banco.aprobarOperacion(movimiento, cuentaReal));
	}

	@Test
	public void testAprobarOperacionRetiroConSaldoInsuficiente() throws Exception {
		banco.abrirCuenta("06-456213-33", "Alfredo Hernandez");
		Cuenta cuentaReal = banco.obtenerCuenta("06-456213-33");
		try {
			cuentaReal.ingresar(100.0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Movimiento movimiento = new Movimiento();
		movimiento.setImporte(-200.0);
		assertFalse(banco.aprobarOperacion(movimiento, cuentaReal));
	}

	@Test
	public void testCrearTarjetaDebitoCuentaInexistente() {
		Exception exception = assertThrows(Exception.class, () -> {
			banco.crearTarjetaDebito("1234567890123456", "Alfredo Hernandez", new Date(), "06-456213-34");
		});
		assertEquals("Cuenta no encontrada", exception.getMessage());
	}

	@Test
	public void testMovimientoAprobadoYSaldoActualizado() throws Exception {
		Banco banco = new Banco("Banco Test");
		banco.abrirCuenta("06-456213-33", "Alfredo Hernandez");
		Cuenta cuentaReal = banco.obtenerCuenta("06-456213-33");
		cuentaReal.ingresar(500.0);

		Movimiento movimiento = new Movimiento();
		movimiento.setImporte(-200.0);
		boolean aprobado = banco.aprobarOperacion(movimiento, cuentaReal);
		assertTrue(aprobado);
		cuentaReal.retirar(-movimiento.getImporte());
		assertEquals(300.0, cuentaReal.getSaldo());
	}

}
