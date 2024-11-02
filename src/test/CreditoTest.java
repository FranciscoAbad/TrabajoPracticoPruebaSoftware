package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import modelo.Credito;
import modelo.Cuenta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreditoTest {
	private Date fecha;
	private Credito credito;
	private Credito tarjeta;

	@Mock
	private Cuenta unaCuenta; // Mock de Cuenta

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateInString = "2020-09-15";
		try {
			fecha = sdf.parse(dateInString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		credito = new Credito("06-456213-33", "Alfredo Hernandez", fecha, 5000.00);

		when(unaCuenta.getSaldo()).thenReturn(1000.0);
		tarjeta = new Credito("525675213239831", "Alfredo Hernandez", fecha, 1000.0);
		tarjeta.setCuenta(unaCuenta);
	}

	/*
	 * PRUEBAS UNITARIAS
	 * 
	 */

	@Test
	public void testComprasConTarjetaCredito() {
		assertNotNull(unaCuenta.getMovimientos());
	}

	@Test
	public void testGetCreditoDisponible() {
		assertNotNull(credito.getCreditoDisponible());
	}

	@Test
	public void testCreditoNoSobrepasaLimite() {
		try {
			tarjeta.retirar(200.0);
			assertEquals(800.0, tarjeta.getCreditoDisponible(), 0.001);
		} catch (Exception e) {
			fail("No debería lanzar excepción");
		}
	}

	@Test
	public void testIngresoNoModificaSaldoTarjeta() {
		try {
			tarjeta.ingresar(300.0);
			assertEquals(1000.0, tarjeta.getSaldo(), 0.001);
		} catch (Exception e) {
			fail("No debería lanzar excepción");
		}
	}

	@Test
	public void testCreditoSobrepasaLimite() {
		try {
			tarjeta.retirar(1200.0);
			fail("Se esperaba una excepción por exceder el límite de crédito");
		} catch (Exception e) {
			assertEquals("Crédito insuficiente", e.getMessage());
		}
	}

}
