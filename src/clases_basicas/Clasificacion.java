/* Este paquete es de libre uso y distribuciÃ³n */
package clases_basicas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
/**
 *  La sardi 2015
 *  Clase Clasificacion. Representa un Rankin sobre el que
 *  se van a ir aÃ±adiendo los jugadores una vez terminen.
 *  TambiÃ©n dispone de el mÃ©todo pegarPerdedor()
 *  para simbolizar el verdadero final del juego.
 * @author hermo. Jose Ã�ngel Hermosilla Rodrigo. 
 *
 */
public class Clasificacion 
{
	private ArrayList<Jugador> jugadores;
	private int numJug;
	private ArrayList<Jugador> podio;
	private ArrayList<Jugador> ganadores;
	Jugador perdedor;
	
	Baraja baraja;
	
	ArrayList<Carta> barajaDeCartas;
	/**
	 * Constructor. Recibe la lista de jugadores Inicial.
	 * @param jugadores
	 */
	public Clasificacion (ArrayList<Jugador> jugadores)
	{
		this.jugadores = jugadores;
		this.numJug = jugadores.size();
		this.podio = new ArrayList<Jugador>();
		this.ganadores = new ArrayList<Jugador>();
		this.baraja = new Baraja();
		baraja.barajar(); baraja.barajar();
		barajaDeCartas = baraja.getBaraja();
	}
	/**
	 * Getter de la lista de Jugadores.
	 * @return ArrayList<Jugador>
	 */
	public ArrayList<Jugador> getJugadores() 
	{
		return jugadores;
	}
	/**
	 *  Devuelve el nÃºmero de Jugadores.
	 * @return int 
	 */
	public int getNumJug() {
		return numJug;
	}
	/**
	 * AÃ±ade un jugador al podio. Si el jugador que se aÃ±ade
	 * es el Ãºltimo se excluye de la lista de ganadores y 
	 * se le pone como jugador perdedor.
	 * @param jugador
	 * @throws Exception
	 */
	public void añadirJugador (Jugador jugador) throws Exception
	{
		if (this.podio.size() == this.numJug) throw new Exception("Max. Jugadores");
		if (this.podio.contains(jugador)) throw new Exception("Jugador ya puntuado");
		this.podio.add(jugador);
		if (this.podio.size() == this.numJug) 
		{
			perdedor = jugador;
			ganadores = new ArrayList<Jugador>();
			ganadores.addAll(podio);
			ganadores.remove(ganadores.indexOf(jugador));
		}
	}
	/**
	 * Cada jugador ganador elige cuantas pegarle al 
	 * perdedor que previamente a escogido una carta.
	 * Al finalizar la ronda de hostias, el perdedor
	 * al ver su valor de carta puede devolver la
	 * diferencia entre las hostias que le ha dado un
	 * jugador y el vlor de carta que tenÃ­a.
	 * @throws Exception
	 */
	public void PegarPerdedor () throws Exception
	{
//		for (Jugador jug : podio) System.out.println(jug.getNombre());
		if (this.podio.size() < numJug) throw new Exception ("AÃºn no ha acabado la partida.");
		// En primer lugar el perdedor cogerÃ¡ una carta aleatoria

		perdedor.setCuantasRecibe(barajaDeCartas.get(new Random().nextInt(barajaDeCartas.size())));
		if (perdedor.getCuantasRecibe().mismoValor(1))
		{
//			System.out.println("El jugador perdedor " + perdedor.getNombre() + " da dos hostias a los demÃ¡s "
//					+ " ha obtenido un As. " + perdedor.getCuantasRecibe().toString());
			return;
		}
		// Si el jugador perdedor no ha sacado un as
		// los demÃ¡s deberÃ¡n elegir cuantas le dan
		Carta cartaPerdedor = perdedor.getCuantasRecibe();
		for (Jugador jugador : ganadores)
		{
			// Los jugadores deben elegir cuantas le pegan al perdedor
			if (jugador.getTipo() == Jugador.BOT)
			{
				int cuantasDa = new Random().nextInt(12) + 1;
				jugador.setCuantasPega(cuantasDa);
				if (cuantasDa <= cartaPerdedor.getValor())
				{
//					System.out.println(jugador.getNombre() + " da " + cuantasDa + 
//							" y el perdedor tenÃ­a un " + cartaPerdedor.toString());
				}
				else 
				{
					int diferencia = cuantasDa - cartaPerdedor.getValor();
//					System.out.println(jugador.getNombre() + " ha dado " + cuantasDa +
//							" y el perdedor tenÃ­a un " + cartaPerdedor.toString() + 
//							". El perdedor le devuelve " + diferencia + " hostias.");
				}
				
				// No puede pegar 0.
				// Como mÃ­nimo 1.
				// El mÃ¡ximo nÃºmero posible es 11 + 1 = 12
			}
			else 
			{
				// AQUÃ� HACER ALGO
			}
		}
	}
	/*public void setNumJug(int numJug) {
		this.numJug = numJug;
	}*/
	/**
	 * Devuelve la lista de ganadores.
	 * @return ArrayList<Jugador>
	 */
	public ArrayList<Jugador> getGanadores() {
		return ganadores;
	}

//	public void setGanadores(ArrayList<Jugador> ganadores) {
//		this.ganadores = ganadores;
//	}

//	public void setJugadores(ArrayList<Jugador> jugadores) 
//	{
//		this.jugadores = jugadores;
//	}
	
//	public static void main (String [] args)
//	{
//		//System.out.println(new Random().nextInt(2));
//	}

	public void sleep (long milis)
	{
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Este mÃ©todo resuelve el conflicto entre varios jugadores
	 * que pasan, mira quien es el perdedor.
	 * Si hay algÃºn empate, pierde el jugador
	 * que al azar saque la carta mÃ¡s grande
	 * @param jugadoresConCartas
	 * @throws Exception
	 */
	public void resolverConflicto (ArrayList<Jugador> jugadoresConCartas) throws Exception
	{
		// Implemetar: Si los jugadores han empatado a puntos
		// cogen carta al azar
		// Pierde el mayor nÃºmero
		Collections.sort(jugadoresConCartas, new Comparator<Jugador>() {

			@Override
			public int compare(Jugador j1, Jugador j2) {
				// TODO Auto-generated method stub
				return j1.valorDeCartas() - j2.valorDeCartas();
			}
		});
		int valorMax = 0;
		for (Jugador jug : jugadoresConCartas)
		{
			// Obtenemos el valor mÃ¡ximo de la suma de las cartas de los jugadores.
			if (jug.valorDeCartas() > valorMax) valorMax = jug.valorDeCartas();
		}
		
		ArrayList<Jugador> jugadoresEmpatados = new ArrayList<Jugador>();
		for (Jugador jug : jugadoresConCartas)
		{
			if (jug.valorDeCartas() == valorMax) jugadoresEmpatados.add(jug);
			// Vemos cuantos jugadores hay empatados.
		}
		
		// Finalmente si no hay jugadores empatados...
		if (jugadoresEmpatados.size() == 1)
		{
			for (Jugador jugador : jugadoresConCartas)
			{
				añadirJugador(jugador);
			}
		}
		else
		{
			resolverEmpate(jugadoresConCartas, jugadoresEmpatados);
			
		}

	}
	/**
	 * Resuelve un empate entre dos jugadores mediante carta al azar
	 * @param jugadoresConCartas
	 * @param jugadoresEmpatados
	 * @throws Exception
	 */
	private void resolverEmpate(ArrayList<Jugador> jugadoresConCartas,
			ArrayList<Jugador> jugadoresEmpatados) throws Exception {
		// Si hay jugadores empatados...
		ArrayList<Jugador> empatadosCopia = new ArrayList<Jugador>();
		empatadosCopia.addAll(jugadoresEmpatados);
		for (Jugador jugador : jugadoresConCartas)
		{
			if (!jugadoresEmpatados.contains(jugador))
			{
				añadirJugador(jugador);
				empatadosCopia.remove(jugador);
			}
			//AÃ±adimos los jugadores que no estÃ¡n en conflicto
		}
		for (Jugador jugador : empatadosCopia)
		{
			int index = new Random().nextInt(baraja.getBaraja().size());
			jugador.setCuantasRecibe(baraja.getBaraja().get(index));
			baraja.getBaraja().remove(index);
		}
		// Ahora que ya han elegido carta los pondremos por orden
		Collections.sort(empatadosCopia, new Comparator<Jugador>() {

			@Override
			public int compare(Jugador j1, Jugador j2) {
				return j1.getCuantasRecibe().getValor() - j2.getCuantasRecibe().getValor();
			}
		});
		for (Jugador jugador : empatadosCopia) añadirJugador(jugador);
	}
	
}
