/* Este paquete es de libre uso y distribuciÃ³n */
package clases_basicas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import android.util.Log;
/**
 *  La sardi 2015
 *  Clase Jugador. Representa a un jugador.
 * @author hermo. Jose Ã�ngel Hermosilla Rodrigo. 
 *
 */
public class Jugador 
{
	final static int HUMANO = 0;
	final static int BOT = 1;
	
	private boolean tirarBazaObligatorio = false;
	
	private ArrayList<Carta> mano;
	private String nombre;
	private int tipo;
	private boolean haPasado = false;
	
	private boolean sinCartaPeroTiraBaza = false;
	
	private Carta ultimaCartaTirada;
	
	// Esta variable se modificarÃ¡ mÃ¡s adelante
	private int cuantasPega;
	private Carta cuantasRecibe;
	/**
	 * Constructor. Recibe el nombre de jugador y el tipo. Si 
	 * es HUMANO o BOT.
	 * @param nombre
	 * @param tipo
	 */
	public Jugador (String nombre, int tipo)
	{
		if (tipo != HUMANO && tipo != BOT)throw new Error("Tipo Jugador no vÃ¡lido.");
		mano = new ArrayList<Carta>();
		this.nombre = nombre;
		this.tipo = tipo;
	}
	/**
	 * Devuelve la mano del jugador
	 * @return ArrayList<Carta> 
	 */
	public ArrayList<Carta> getMano() 
	{
		return mano;
	}
	/**
	 * Setter del mano. No se usa
	 * @param mano
	 */
	public void setMano(ArrayList<Carta> mano) 
	{
		this.mano = mano;
	}
	/**
	 * Devuelve el nombre del jugador (Getter)
	 * @return String
	 */
	public String getNombre() 
	{
		return nombre;
	}
	/**
	 * Setter del nombre
	 * @param nombre
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	/**
	 *  Recibe un valor de carta como parÃ¡metro.
	 *  Devuelve un ArrayList de Cartas con las
	 *  cartas que tiene de ese valor
	 * @param valor
	 * @return ArrayList<Carta>
	 */
	public ArrayList<Carta> tiene (int valor)
	{
		ArrayList<Carta> tiene = new ArrayList<Carta>();
		for (Carta carta : mano)
		{
			if (carta.mismoValor(valor)) tiene.add(carta);
		}
		return tiene;
	}
	/**
	 *  Recibe un palo de carta como parÃ¡metro.
	 *  Devuelve un ArrayList de Cartas con las
	 *  cartas que tiene de ese palo
	 * @param palo
	 * @return ArrayList<Carta>
	 */
	public ArrayList<Carta> tiene (String palo)
	{
		ArrayList<Carta> tiene = new ArrayList<Carta>();
		for (Carta carta : mano)
		{
			if (carta.mismoPalo(palo)) tiene.add(carta);
		}
		return tiene;
	}
	/**
	 *  Recibe una carta como parÃ¡metro.
	 *  Devuelve un ArrayList de Cartas con las
	 *  cartas que tiene de esa Carta
	 * @param carta
	 * @return ArrayList<Carta>
	 */
	public ArrayList<Carta> tiene (Carta cart)
	{
		ArrayList<Carta> tiene = new ArrayList<Carta>();
		for (Carta carta : mano)
		{
			if (carta.equals(cart)) tiene.add(carta);
		}
		return tiene;
	}
	/**
	 *  Recibe una Carta como parÃ¡metro. (Carta en Mesa)
	 *  Devuelve un ArrayList de Cartas con las
	 *  cartas que puede tirar
	 * @param cartaEnMesa
	 * @return ArrayList<Carta>
	 */
	public ArrayList<Carta> cartasQuePuedeTirar (Carta cartaEnMesa)
	{
		ArrayList<Carta> puede = new ArrayList<Carta>();
		for (Carta carta : mano)
		{
			if (carta.mismoValor(cartaEnMesa)) puede.add(carta);
			else if (carta.mismoPalo(cartaEnMesa)) puede.add(carta);
			else if (carta.mismoValor(10)) puede.add(carta);
		}
		return puede;	
	}
	/**
	 * Recibe un ArrayList<Carta> y devuelve otro ArrayList<Carta>
	 * con las cartas sueltas que contiene
	 * @param cartasPosibles
	 * @return ArrayList<Carta>
	 */
	public ArrayList<Carta> cartasSueltas (ArrayList<Carta> cartasPosibles)
	{
		ArrayList<Carta> cartasSueltas = new ArrayList<Carta>();
		for (Carta carta : cartasPosibles)
		{
			if (carta.getValor() > 3 && carta.getValor() < 7) cartasSueltas.add(carta);
			else if (carta.getValor() > 7 && carta.getValor() < 10) cartasSueltas.add(carta);
		}
		return cartasSueltas;
	}
	/**
	 * MÃ©todo. Ordena las cartas de la mano por valor.
	 */
	public void ordenarManoPorValor ()
	{
		try
		{
			Collections.sort(mano, 
					new Comparator<Carta>() 
					{
						@Override
						public int compare(Carta carta1, Carta carta2) 
						{
							// TODO Auto-generated method stub
							return carta1.getValor() - carta2.getValor();
						}
					});
		}
		catch (NullPointerException npe)
		{
			// Solo tiene una carta
		}

	}
	/**
	 * MÃ©todo. Ordena las cartas de la mano por palo. 
	 * (Oros, Copas, Espadas, Bastos)
	 */
	public void ordenarManoPorPalo ()
	{
		try
		{
			Collections.sort(mano, 
					new Comparator<Carta>() 
					{
						@Override
						public int compare(Carta carta1, Carta carta2) 
						{
							// TODO Auto-generated method stub
							return carta1.getNumeroDePalo() - carta2.getNumeroDePalo();
						}
					});
		}
		catch (NullPointerException npe)
		{
			// Solo tiene una carta
		}

	}
	/**
	 * MÃ©todo. Revierte el orden de la mano.
	 */
	public void revertirOrdenMano ()
	{
		Collections.reverse(mano);
	}
	/** 
	 * Recibe como parÃ¡metro una Baraja. 
	 * Coge una carta de la baraja (y se elimina de la baraja)
	 * y la aÃ±ade a la mano del jugador.
	 * @param baraja
	 * @return Carta
	 */
	public Carta robarCarta(Baraja baraja)
	{
		Carta cartaRobar = baraja.cogerCarta();
		mano.add(cartaRobar);
		return cartaRobar;
	}
	
	@Override
	public String toString() 
	{
		String s= nombre + " ";
		ordenarManoPorValor();
		Iterator<Carta> it = mano.iterator();
		while (it.hasNext())
		{
			Carta c = it.next();
			try 
			{
				s += c.toString() + ", ";
			} catch (NullPointerException e) {

			}
		}
		
		return s;
		
	}
	/**
	 * Getter del Tipo de Jugador
	 * @return int
	 */
	public int getTipo() 
	{
		return tipo;
	}
	/**
	 * Setter del tipo de jugador
	 * @param int tipo
	 */
	public void setTipo(int tipo) 
	{
		this.tipo = tipo;
	}
	/**
	 * Cambia el turno al siguiente jugador en funciÃ³n
	 * del sentido de la mesa.
	 * @param jugada
	 */
	public void cambiarTurno (Jugada jugada)
	{
		Jugador jugActual = jugada.mesa.getJugadorActual();
		ArrayList<Jugador> jugs = jugada.mesa.jugadores;
		try 
		{
			if (jugada.mesa.getSentido() == Mesa.DERECHA) 
				jugada.mesa.setJugadorActual(jugs.get(jugs.indexOf(jugActual) + 1));
			else
				jugada.mesa.setJugadorActual(jugs.get(jugs.indexOf(jugActual) - 1));
		}
		catch (IndexOutOfBoundsException exc)
		{
			if (jugada.mesa.getSentido() == Mesa.DERECHA) 
			{
				jugada.mesa.setJugadorActual(jugs.get(0)); // es el Ãºltimo por la derecha
			}
			else
			{
				jugada.mesa.setJugadorActual(jugs.get(jugs.size() - 1)); // es el Ãºltimo por la derecha
			}
		}
	}
	/**
	 * Cambia de turno al jugador siguiente del saltado
	 * en funciÃ³n del Sentido de la mesa.
	 * @param jugada
	 */
	public void saltarJugador (Jugada jugada)
	{
		Jugador jugActual = jugada.mesa.getJugadorActual();
		ArrayList<Jugador> jugs = jugada.mesa.jugadores;
		int index = jugs.indexOf(jugActual);
		int size = jugs.size();
		try 
		{
			if (jugada.mesa.getSentido() == Mesa.DERECHA) 
				jugada.mesa.setJugadorActual(jugs.get(jugs.indexOf(jugActual) + 2));
			else
				jugada.mesa.setJugadorActual(jugs.get(jugs.indexOf(jugActual) -2));
		}
		catch (IndexOutOfBoundsException exc)
		{
			if (jugada.mesa.getSentido() == Mesa.DERECHA) 
			{
				if (index == size - 1) jugada.mesa.setJugadorActual(jugs.get(1)); // es el Ãºltimo por la derecha
				else jugada.mesa.setJugadorActual(jugs.get(0));
			}
			else
			{
				if (index == 0) jugada.mesa.setJugadorActual(jugs.get(jugs.size() - 2)); // es el Ãºltimo por la derecha
				else jugada.mesa.setJugadorActual(jugs.get(jugs.size() - 1));
			}
		}
			
	}
	/*public static void main (String [] args)
	{
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		String nomJugs [] = {"Pepe", "Juan", "Fran"};
		for (String nombre : nomJugs)
		{
			jugadores.add(new Jugador(nombre));
		}
		
		Baraja baraja = new Baraja();
		baraja.barajar();baraja.barajar();baraja.barajar();
		baraja.repartir(5, jugadores);
		
		for (Jugador jugador : jugadores)
		{
			System.out.println(jugador.getNombre() + "\n");
			for (Carta c : jugador.getMano()) System.out.println(c.toString() + ", ");
			System.out.println("\n ordenadas por valor: ");
			jugador.ordenarManoPorValor();
			jugador.revertirOrdenMano();
			for (Carta c : jugador.getMano()) System.out.println(c.toString() + ", ");
			System.out.println("\n ordenadas por palo: ");
			jugador.ordenarManoPorPalo();
			for (Carta c : jugador.getMano()) System.out.println(c.toString() + ", ");
		}
		
		System.out.println("Cartas en baraja: " + baraja.getBaraja().size());
		
		// Hagamos pruebas...
		Carta cartaEnLaMesa = new Carta(5, "espadas");
		
		for (Jugador jugador : jugadores)
		{
			System.out.println(jugador.getNombre() + " puede tirar: \n");
			for (Carta c : jugador.cartasQuePuedeTirar(cartaEnLaMesa)) System.out.println(c.toString() + ", ");
			System.out.println("\n de las cuales son sueltas: \n");
			for (Carta c : jugador.cartasSueltas(jugador.cartasQuePuedeTirar(cartaEnLaMesa))) System.out.println(c.toString() + ", ");
			System.out.println("\n");
		}
		
		for (Jugador jugador : jugadores)
		{
			System.out.println(jugador.getNombre() + " tiene 11? : \n");
			System.out.println((jugador.tiene(11).isEmpty())? false : true);
			System.out.println(jugador.getNombre() + " tiene espadas? : \n");
			System.out.println((jugador.tiene("espadas").isEmpty())? false : true);
			System.out.println(jugador.getNombre() + " tiene 11 de espadas? : \n");
			System.out.println((jugador.tiene(new Carta(11, "espadas")).isEmpty())? false : true);
			
		}
		
		
	} // Main Pruebas *************/
	/**
	 * Getter de la variable haPasado.
	 * @return boolean
	 */
	public boolean haPasado() 
	{
		return haPasado;
	}
	/**
	 * Setter de la variable haPasado.
	 * @param haPasado
	 */
	public void pasa(boolean haPasado) 
	{
		this.haPasado = haPasado;
	}
	/**
	 * Devuelve true si el jugador tiene Cartas.
	 * false en caso contrario.
	 * @return boolean
	 */
	public boolean tieneCartas ()
	{
		return !this.mano.isEmpty();
	}
	
	/**
	 *  Devuelve la Ãºltima carta tirada por el jugador.
	 *  Getter de la variable ultimaCartaTirada.
	 * @return Carta
	 */
	public Carta getUltimaCartaTirada() 
	{
		return ultimaCartaTirada;
	}
	/**
	 * Setter de la variable ultimaCartaTirada.
	 * @param ultimaCartaTirada
	 */
	public void setUltimaCartaTirada(Carta ultimaCartaTirada) 
	{
		this.ultimaCartaTirada = ultimaCartaTirada;
	}
	/**
	 * Comprueba si el jugador ha acabado la partida.
	 * TambiÃ©n si ha tirado baza y estÃ¡ a la espera
	 * de que no le vuelva 
	 * @param jugada
	 * @throws Exception
	 */
	public void comprobarFinal (Jugada jugada) throws Exception
	{
		if (jugada.mesa.quedanCartas())
		{
			if (!tieneCartas())
			{
				if (ultimaCartaTirada.mismoValor(4) ||
					ultimaCartaTirada.mismoValor(5) ||
					ultimaCartaTirada.mismoValor(6) ||
					ultimaCartaTirada.mismoValor(8) ||
					ultimaCartaTirada.mismoValor(9) ||
					ultimaCartaTirada.mismoValor(10)) 
				{
					jugada.mesa.eliminarJugador(this);
					Log.d("DEBUG", this.nombre + " acabÃ³ la partida");
					jugada.mesa.clasificacion.añadirJugador(this);
				}
				
				if (ultimaCartaTirada.mismoValor(1) || ultimaCartaTirada.mismoValor(2))
					setSinCartaPeroTiraBaza(true, jugada);
				
				if (ultimaCartaTirada.mismoValor(11) || ultimaCartaTirada.mismoValor(12))
				{
					if (jugada.mesa.jugadores.size() == 2)
					{
						
					}
					else 
					{
						jugada.mesa.eliminarJugador(this);
						Log.d("DEBUG", this.nombre + " acabÃ³ la partida ");
						jugada.mesa.clasificacion.añadirJugador(this);
					}
				}
				
			}
		}
		else
		{
			// Si no quedan cartas en la mesa eliminamos al jugador si no tiene cartas
			if (!tieneCartas()) 
			{
				jugada.mesa.eliminarJugador(this);
				Log.d("DEBUG", this.nombre + " acabÃ³ la partida");
				jugada.mesa.clasificacion.añadirJugador(this);
			}
		}

	}
	/** 
	 * Getter de la variable sinCartaPeroTiraBaza.
	 * @return boolean
	 */
	public boolean isSinCartaPeroTiraBaza() 
	{
		return sinCartaPeroTiraBaza;
	}
	/**
	 * Setter de la variable sinCartaPeroTiraBaza.
	 * Si no hay cartas siempre estÃ¡ variable la pondremos a false.
	 * Ya que no importa tirar un 1 o un 2 (No hay para robar y se gana directament)
	 * Si no evaluamos esta variable al valor pasado como parÃ¡metro
	 * @param sinCartaPeroTiraBaza
	 * @param jugada
	 */
	public void setSinCartaPeroTiraBaza(boolean sinCartaPeroTiraBaza, Jugada jugada) 
	{
		if (jugada.mesa.quedanCartas() && !tieneCartas())
			this.sinCartaPeroTiraBaza = sinCartaPeroTiraBaza;
		else this.sinCartaPeroTiraBaza = false;
	}
	/**
	 * Devuelve el jugador anterior al actual. No se usa.
	 * @param jugada
	 * @return
	 */
	public Jugador getJugadorAnterior(Jugada jugada)
	{
		ArrayList<Jugador> jugs = jugada.mesa.jugadores;
		try 
		{
			if (jugada.mesa.getSentido() == Mesa.DERECHA) 
				 return jugs.get(jugs.indexOf(this) - 1);
			else return jugs.get(jugs.indexOf(this) + 1);
		}
		catch (IndexOutOfBoundsException exc)
		{
			if (jugada.mesa.getSentido() == Mesa.IZQUIERDA) 
				 return jugs.get(0); // es el Ãºltimo por la derecha
			else return jugs.get(jugs.size() - 1); // es el Ãºltimo por la derecha
		}
	}
	/** 
	 * Devuelve el jugador siguiente al actual. No se usa.
	 * @param jugada
	 * @return
	 */
	public Jugador getJugadorSiguiente(Jugada jugada)
	{
		ArrayList<Jugador> jugs = jugada.mesa.jugadores;
		try 
		{
			if (jugada.mesa.getSentido() == Mesa.DERECHA) 
				 return jugs.get(jugs.indexOf(this) + 1);
			else return jugs.get(jugs.indexOf(this) - 1);
		}
		catch (IndexOutOfBoundsException exc)
		{
			if (jugada.mesa.getSentido() == Mesa.DERECHA) 
				 return jugs.get(0); // es el Ãºltimo por la derecha
			else return jugs.get(jugs.size() - 1); // es el Ãºltimo por la derecha
		}
	}
	/**
	 * Getter de la variable cuantasPega.
	 * @return int
	 */
	public int getCuantasPega() 
	{
		return cuantasPega;
	}
	/**
	 * Setter de la variable cuantasPega
	 * @param cuantasPega
	 */
	public void setCuantasPega(int cuantasPega) 
	{
		this.cuantasPega = cuantasPega;
	}
	/** 
	 * Getter de la variable cuantasRecibe.
	 * @return cuantasRecibe
	 */
	public Carta getCuantasRecibe() 
	{
		return cuantasRecibe;
	}
	/**
	 * Setter de la variable cuantasRecibe.
	 * @param cuantasRecibe
	 */
	public void setCuantasRecibe(Carta cuantasRecibe) 
	{
		this.cuantasRecibe = cuantasRecibe;
	}
	/** 
	 * Devuelve la suma del valor de todas las cartas de la mano
	 * @return int
	 */
	public int valorDeCartas()
	{
		int valor = 0;
		for (Carta carta : mano)
		{
			valor += carta.getValor();
		}
		return valor;
	}
	public boolean isTirarBazaObligatorio() {
		return tirarBazaObligatorio;
	}
	public void setTirarBazaObligatorio(boolean tirarBazaObligatorio) {
		this.tirarBazaObligatorio = tirarBazaObligatorio;
	}
	
}
