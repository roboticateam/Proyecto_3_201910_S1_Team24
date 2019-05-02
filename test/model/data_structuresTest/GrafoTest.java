package model.data_structuresTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.Grafo;

public class GrafoTest {
	//
	// Prueba grafoo
	// -----------------------------------------------------------------
	private Grafo<Integer,String,Integer> grafoTest;

	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------
	/**
	 * Escenario con el grafo vac�o
	 */
	@Before
	public void setUpEscenario1()
	{
		grafoTest = new Grafo<Integer,String,Integer>();

	}
	/*	public static void main(String[] args) {
						System.out.println("hi");
					}*/

	public void setUpEscenario2()
	{
		grafoTest = new Grafo<Integer,String,Integer>();

	}
	@Test
	public void addEdgeTest()
	{
		setUpEscenario1();
		grafoTest.addVertex(1, "a");
		assertEquals("No agreg� bien", grafoTest.V(), 1);
	}
	@Test
	public void VTest()
	{
		setUpEscenario1();
		assertEquals("Cantidad de vertices incorrecta", grafoTest.V(), 0);
	}

}
