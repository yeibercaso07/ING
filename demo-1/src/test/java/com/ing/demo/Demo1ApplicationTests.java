package com.ing.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Demo1Application.class)
class Demo1ApplicationTests {

	@Test
	public void ArrayLeader() {

		int[] A = { 2, 1, 3, 1, 2, 2, 3 };
		int K = 3;
		int M = 5;
		solution(K, M, A);
	}

	/**
	 * ArrayLeader
	 * @param K
	 * @param M
	 * @param A
	 * @return
	 */
	public int[] solution(int K, int M, int[] A) {

		try {
			validateList(K, M, A);
			List<List<Integer>> listIntervalosPosibles = getListIntervalosPosibles(A, K);
			System.out.println("listados de intervalos:" + listIntervalosPosibles.toString());

			List<List<Integer>> listadosLideresRepetidosPorIntervalo = new ArrayList<>();
			// recorremos listado de todas las lista de intervalos
			lideresRpetidosPorIntervalo(listIntervalosPosibles, listadosLideresRepetidosPorIntervalo);

			// ordenamos las listas de posibles lideres por intervalo
			for (int i = 0; i < listadosLideresRepetidosPorIntervalo.size(); i++) {
				Collections.sort(listadosLideresRepetidosPorIntervalo.get(i));
			}
			System.out.println(
					"listados Lideres Repetidos x Intervalo:" + listadosLideresRepetidosPorIntervalo.toString());

			List<Integer> result = new ArrayList<>();
			for (int i = 0; i < listadosLideresRepetidosPorIntervalo.size(); i++) {
				for (int j = 0; j < listadosLideresRepetidosPorIntervalo.get(i).size(); j++) {
					result.add(listadosLideresRepetidosPorIntervalo.get(i).get(j));
				}
			}
			System.out.println("Lista valores intervalos unidos:" + result.toString());

			List<Integer> result2 = getListValoresRepetidos(result);
			//ordenamos lista
			Collections.sort(result2);

			System.out.println("Resultado:" + result2.toString());

			return listIntegerToArrayInt(result2);

		} catch (

		Exception e) {
			throw new Error("ERROR - " + e);
		}

	}

	/**
	 * Obtener lista de intervalos de posibles lideres
	 * 
	 * @param listIntervalosPosibles
	 * @param listadosLideresRepetidosPorIntervalo
	 */
	private void lideresRpetidosPorIntervalo(List<List<Integer>> listIntervalosPosibles,
			List<List<Integer>> listadosLideresRepetidosPorIntervalo) {

		for (int i = 0; i < listIntervalosPosibles.size(); i++) {
			List<Integer> listaIntervalo = listIntervalosPosibles.get(i);
			List<Integer> numMayores = getListValoresRepetidos(listaIntervalo);
			listadosLideresRepetidosPorIntervalo.add(numMayores);
		}

	}

	/**
	 * Metodo que lista los valores repetido a partir de una lista
	 * 
	 * @param listaIntervalo
	 * @return
	 */
	private List<Integer> getListValoresRepetidos(List<Integer> listaIntervalo) {
		Map<Integer, Integer> valoresRepetidos = new HashMap<Integer, Integer>();
		for (Integer val : listaIntervalo) {
			if (!valoresRepetidos.containsKey(val)) {
				valoresRepetidos.put(val, 1);
			} else {
				valoresRepetidos.replace(val, valoresRepetidos.get(val) + 1);
			}
		}
		System.out.println("Matriz valores repetido x Intervalo:" + valoresRepetidos.toString());
		List<Integer> numMayores = new ArrayList<Integer>();
		// extraemos los dos primeros valores que se repiten mas
		for (int j = 0; j < 2; j++) {
			filtarValoresRepetidos(valoresRepetidos, numMayores);
		}
		return numMayores;
	}

	/**
	 * Filtrar los valores para obtener los dos primeros mas repetidos, si no hay
	 * valores repetidos no se añade nada
	 * 
	 * @param valoresRepetidos
	 * @param numMayores
	 */
	private void filtarValoresRepetidos(Map<Integer, Integer> valoresRepetidos, List<Integer> numMayores) {
		// guardamos el valor maximo de la map para luego obtener su valor o key
		int max = Collections.max(valoresRepetidos.values());
		// reorremos la matriz y guardamos la Key cuyo valor coincida con max que
		// representa el numero de veces mas repetidas
		for (Map.Entry<Integer, Integer> entry : valoresRepetidos.entrySet()) {
			if (entry.getValue() == max && max != 1) {
				System.out.println("Valor mas Repetido:"+ entry.getKey() +" numero de veces: " + entry.getValue());
				numMayores.add(entry.getKey());
				// eliminamos el valor para poder conseguir el segundo que mas se repite
				valoresRepetidos.replace(entry.getKey(), 0);
			}
		}

	}

	/**
	 * Obtiene la lista de Intervalos Posibles
	 * 
	 * @param A
	 * @param items
	 * @return
	 */
	private List<List<Integer>> getListIntervalosPosibles(int[] A, int items) {
		// identificar cual es la primera posicion de ultimo segmento
		int ultimoItems = A.length - items;
		// inicializo la primera posicion del primer segmento
		int itemPivot = 0;
		// inicializo el listados que contendra la lista de resultado
		List<List<Integer>> listados = new ArrayList<>();

		// iteramos hasta que ejecutemos el ultimo segmento calculado
		while (itemPivot <= ultimoItems) {
			// inicilizo lista pivot siempre sera la original que luego sera modificada
			List<Integer> lisPivot = arrayIntToListInteger(A);
			// recorremos cada segmento y le sumamos una unidad a cada valor dentro del
			// rango definido por items inicial
			for (int i = itemPivot; i < items; i++) {
				lisPivot.set(i, lisPivot.get(i) + 1);
			}
			// añadimos las lista nueva con el rango modificado
			listados.add(lisPivot);
			items++;
			itemPivot++;
		}
		return listados;
	}

	/**
	 * 
	 * convierte de List a Array
	 * 
	 * @param A
	 * @return
	 */
	// convertir arreglo de int a List<int>
	private List<Integer> arrayIntToListInteger(int[] A) {
		List<Integer> original = new ArrayList<>();
		for (int i = 0; i < A.length; i++) {
			original.add(A[i]);
		}
		return original;
	}

	/**
	 * 
	 * convierte de Array a List
	 * 
	 * @param A
	 * @return
	 */
	// convertir arreglo de int a List<int>
	private int[] listIntegerToArrayInt(List<Integer> A) {
		int[] ret = new int[A.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = A.get(i);
		return ret;
	}

	private void validateList(int K, int M, int[] A) throws Error {
		if (A.length <= 0) {
			throw new Error("ERROR - La matriz esta vacia");
		}

		if (A.length < K) {
			throw new Error("ERROR - el intervalo a evaluar es mayor que el tamaño de la matriz");
		}

		if (K < 1) {
			throw new Error("ERROR - el intervalo no puede ser menor que 1");
		}

		for (int i = 0; i < A.length; i++) {
			if (A[i] > M) {
				throw new Error("ERROR - los valores de lista no pueden ser mayor que M");
			}
			if (M < 1 || M > 100000) {
				throw new Error("ERROR - M debe ser un numero entero comprendido entre 1 y 100,000");
			}
			if (A[i] < 1 || A[i] > 100000) {
				throw new Error(
						"ERROR - los valores de la Matriz deben ser un numero entero comprendido entre 1 y 100,000");
			}
		}
	}
}
