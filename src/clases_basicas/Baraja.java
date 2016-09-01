/* Este paquete es de libre uso y distribuciÃ³n */
package clases_basicas;

import java.util.ArrayList;
import android.util.Log;
/**
 *  La sardi 2015
 *  Clase Baraja. Representa una Baraja.
 * @author hermo. Jose Ã�ngel Hermosilla Rodrigo. 
 *
 */
public class Baraja 
{
	private ArrayList<Carta> baraja; // simboliza el mazo 
	String [] palos = {"oros", "copas", "espadas", "bastos"}; // palos
	/**
	 * Constructor: vacÃ­o. Inicializa la baraja y la rellana de cartas
	 */
	public Baraja ()
	{
		/* Constructor */
		// Inicializalizamos el arraylist que harÃ¡ de mazo
		baraja = new ArrayList<Carta>();
		
		// Rellenamos el mazo con cartas
		for (String palo : palos)
		{
			for (int i = 1; i <= 12; i++)
			{
				Carta carta = new Carta(i,palo);
				baraja.add(carta);
			}
		}
	}
	/**
	 * Constructor: Recibe el nÃºmero de barajas a utilizar. 
	 * Inicializa el mazo y las rellena con las barajas que
	 * hayamos pasado como parÃ¡metro.
	 */
	public Baraja(int numeroDeBarajas)
	{
		/* Constructor secundario, con nÃºmero de barajas,
		 * El constructor Baraja() es equivalente a Baraja(1)
		 */
		if (numeroDeBarajas < 1 || numeroDeBarajas > 4)
			throw new Error ("NÃºmero de Barajas no vÃ¡lido.");
		
		// Inicializalizamos el arraylist que harÃ¡ de mazo
		baraja = new ArrayList<Carta>();
		for (int i = 1; i <= numeroDeBarajas; i++)
		{
			// Rellenamos el mazo con cartas
			for (String palo : palos)
			{
				for (int index = 1; index <= 12; index++)
				{
					Carta carta = new Carta(index,palo);
					baraja.add(carta);
				}
			}
		}
	}
	/**
	 * MÃ©todo barajar()."Desordena" el mazo de forma aleatoria.
	 */
	public void barajar ()
	{
		/* Baraja arbitrariamente las cartas de la baraja */
		java.util.Collections.shuffle(baraja);
	}
	
	@Override
	public String toString() 
	{
		String cartasEnBaraja = "";
		for (Carta carta : baraja)
		{
			cartasEnBaraja += carta.toString() + "\n";
		}
		return cartasEnBaraja;
	}
	/**
	 * MÃ©todo repartir. Recibe la lista de jugadores y el nÃºmero de cartas a 
	 * repartir.
	 * @param cartas
	 * @param jugadores
	 */
	public void repartir (int cartas, Jugada jugada)
	{
		/* MÃ©tedo que recibe el nÃºmero de cartas a repartir
		 *  y los jugadores. Reparte cartas a los jugadores
		 *  uno a uno.
		 */
		jugada.mesa.toastMsg("REPARTIENDO");
		sleep(1000);
		ArrayList<Jugador> jugadores = jugada.mesa.jugadores;
		for (int i=1; i<=cartas; i++)
		{
			for (Jugador jugador : jugadores)
			{
				try
				{
					Carta carta = baraja.get(0);
					jugador.getMano().add(carta);
					baraja.remove(0);
					Log.d("DEBUG", jugador.getNombre() + " recibe " + carta.toString());
					jugada.mesa.displayMsg(jugador.getNombre() + " recibe " + carta.toString());
					sleep(2000);
				}
				catch (NullPointerException npe)
				{
					throw new Error ("Baraja vacía.");
				}

			}
		}
	}
	/**
	 * MÃ©todo que coge una carta de la baraja y la devuelve.
	 * Devuelve null si la baraja estÃ¡ vacÃ­a.
	 * @return Carta
	 * @throws Error
	 */
	public Carta cogerCarta () throws Error
	{
		/* MÃ©todo que coge una carta de la baraja y la devuelve.
		 * Devuelve null si la baraja estÃ¡ vacÃ­a.
		 *  */
		try
		{
			Carta carta = baraja.get(0);
			baraja.remove(0);
			return carta;
		}
		catch (NullPointerException npe)
		{
			return null;
		}
		catch (IndexOutOfBoundsException err){return null;}
		
	}
	/**
	 * Devuelve el mazo. Getter del ArrayList de Cartas
	 * @return ArrayList<Carta>
	 */
	public ArrayList<Carta> getBaraja ()
	{
		/* Devuelve el mazo */
		return baraja;
	}
	/*public static void main (String [] args)
	{
		Baraja baraja = new Baraja(1);
		baraja.barajar();
		baraja.barajar();
		baraja.barajar();
		System.out.println(baraja.toString());
		System.out.println(baraja.baraja.size());
	}**************************************/
	/**
	 * Devuelve el nÃºmero de cartas que contiene la baraja
	 * @return int
	 */
	public int getNumeroDeCartas ()
	{
		return this.baraja.size();
	}
	/**
	 * "Duerme" el hilo principal durante el tiempo que
	 * le pasas como parÃ¡metro.
	 * @param milis
	 */
	public void sleep(long milis)
	{
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
