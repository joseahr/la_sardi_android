/* Este paquete es de libre uso y distribuciÃ³n */
package clases_basicas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.games.sardineta.Principal;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
/**
 *  La sardi 2015
 *  Clase Mesa. Representa una Mesa donde se sientan los jugadores
 *  y comienza el loop principal de la partida. AdemÃ¡s almacena variables
 *  como el sentido de avance de la partida, el jugador Actual, etc...
 * @author hermo. Jose Ã�ngel Hermosilla Rodrigo. 
 *
 */
public class Mesa
{
	protected Principal ctx;
	
	Jugada jugada;
	Clasificacion clasificacion;
	
//	Scanner sc;
	
	final int NUMERO_MAX_DE_JUGADORES = 5; 
	Baraja baraja; // Baraja de Cartas
	ArrayList<Jugador> jugadores; // Lista de Jugadores
	
	int numJug, numBar, numCar; // NÃºmero de cartas a repartir, de jugadores y barajas.
	
	static final int DERECHA = 0;
	static final int IZQUIERDA = 1;
	
	private int sentido;
	
	private boolean juegoAcabado;
	
	private Jugador jugadorActual;  // Identifica el Jugador Actual
	private Carta cartaEnMesa; // Identifica la carta en la mesa actual
	
	int asesRobar = 0;
	int dosesRobar = 0;
	/**
	 * Constructor. Inicializa listas y variables.
	 * @param numeroJugadores
	 * @param numBarajas
	 * @param numCartasRepartir
	 */
	public Mesa (Principal ctx, int numeroJugadores, int numBarajas, int numCartasRepartir)
	{
		if (numeroJugadores < 2 || numeroJugadores > NUMERO_MAX_DE_JUGADORES)
			throw new Error ("Número de jugadores no válido.");
		this.ctx = ctx;
		sentido = DERECHA;
		baraja = new Baraja(numBarajas); // Creamos la baraja
		jugadores = new ArrayList<Jugador>(); // Creamos la lista d los jugadores
		numJug = numeroJugadores;
		numBar = numBarajas;
		numCar = numCartasRepartir;
		
		this.juegoAcabado = false;
//		sc = new Scanner(System.in);
	}
	
	public void toastMsg (final String text)
	{
		ctx.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void toastMsg (final String text, final int duracion)
	{
		ctx.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(ctx, text, duracion).show();
			}
		});
	}
	
	public void displayMsg (final String text)
	{
		ctx.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				ctx.tv.setText(text);
				return;
			}
		});
	}
	/**
	 * MÃ©todo para aÃ±adir Jugadores Humanos a la Mesa.
	 * @param nombre
	 * @param tipo
	 */
	public void añadirJugador (JugadorHumano jugadorHumano)
	{
		if (jugadores.size() == numJug) throw new Error("NÃºmero mÃ¡ximo de Jugadores alcanzado.");
		jugadores.add(jugadorHumano);
	}
	/**
	 * MÃ©todo para aÃ±adir Jugadores Humanos a la Mesa.
	 * @param nombre
	 * @param tipo
	 */
	public void añadirJugador (JugadorBot jugadorBot)
	{
		if (jugadores.size() == numJug) throw new Error("NÃºmero mÃ¡ximo de Jugadores alcanzado.");
		jugadores.add(jugadorBot);
	}
	/**
	 *  Este mÃ©todo se encarga de barajar, repartir a los jugadores
	 *  y empezar el hilo principal de la partida.
	 * @throws Exception
	 */
	public void empezarPartida () throws Exception
	{
		if (jugadores.size() < numJug)
			throw new Error("AÃºn faltan jugadores por sentarse. ");
		
		clasificacion = new Clasificacion(jugadores); // AquÃ­ estÃ¡ bien!!
		jugada = new Jugada(this);
		
		baraja.barajar();baraja.barajar(); // Barajamos "dos" veces 
		baraja.repartir(numCar, jugada); // Repartimos
		
		cartaEnMesa = baraja.cogerCarta();
		setJugadorActual(jugadores.get(new Random().nextInt(jugadores.size())));
//		System.out.println("Jugador Actual: " + getJugadorActual().getNombre() + 
//						   "\nCarta en Mesa : " + cartaEnMesa.toString());
//		for (Jugador j : jugadores) System.out.println(j.toString());
		
		checkCartaComienzo(cartaEnMesa);
		jugada.resolverJugada();
	}
	/**
	 * Elimina al Jugador pasado como parÃ¡metro de 
	 * la mesa. Simboliza que ya ha acabado la partida.
	 * Se ha quedado sin cartas.
	 * @param jugador
	 */
	public void eliminarJugador(Jugador jugador)
	{
		jugadores.remove(jugadores.indexOf(jugador));
	}
	/**
	 * Devuelve la carta central en la mesa.
	 * Getter de la variable cartaEnMesa.
	 * @return
	 */
	public Carta getCartaMesa()
	{
		return this.cartaEnMesa;
	}
	/**
	 * Setter de la variable cartaEnMesa.
	 * @param carta
	 */
	public void setCartaMesa(Carta carta)
	{
		this.cartaEnMesa = carta;
	}
	
//	public static void main (String [] args) throws Exception
//	{
//		Mesa mesa = new Mesa(3,1,2);
//		mesa.aÃ±adirJugador(new JugadorHumano("Jose"));
//		mesa.aÃ±adirJugador(new JugadorBot("Juan"));
//		mesa.aÃ±adirJugador(new JugadorBot("Abdul"));
////		mesa.aÃ±adirJugador("Perico",Jugador.BOT);
////		mesa.aÃ±adirJugador("Abdul",Jugador.BOT);
////		mesa.aÃ±adirJugador("Hasan",Jugador.BOT);
//		mesa.empezarPartida();
//	}
	/**
	 * Devuelve 0 si el sentido es a DERECHAS. 
	 * 1 en caso contrario. Utilizar con variables
	 * estÃ¡ticas Mesa.DERECHA o Mesa.IZQUIERDA
	 * @return int
	 */
	public int getSentido() {
		return sentido;
	}
	/**
	 * Setter del sentido.
	 * @param sentido
	 */
	public void setSentido(int sentido) {
		this.sentido = sentido;
	}
	/**
	 * Devuelve el jugador Actual.
	 * Getter del jugadpr Actual.
	 * @return Jugador
	 */
	public Jugador getJugadorActual() 
	{
		return jugadorActual;
	}
	/**
	 * Setter de la variable JugadorActual.
	 * @param jugadorActual
	 */
	public void setJugadorActual(Jugador jugadorActual) 
	{
		this.jugadorActual = jugadorActual;
	}
	/**
	 * Cambia el sentido de la mesa.
	 */
	public void cambiarSentido ()
	{
		if (jugadores.size() == 2) jugadorActual.saltarJugador(jugada);
		else 
		{
			if (sentido == DERECHA) setSentido(IZQUIERDA);
			else {setSentido(DERECHA);}
			jugadorActual.cambiarTurno(jugada);
		}
	}
	/**
	 * Devuelve true si quedan cartas en el mazo de la mesa.
	 * @return boolean
	 */
	public boolean quedanCartas ()
	{
		return !this.baraja.getBaraja().isEmpty();
	}
	/**
	 * Comprueba si el juego acaba.
	 * Bien porque todos pasan o bien porque solo queda un jugador
	 * en la mesa.
	 * @throws Exception
	 */
	public void comprobarSiTodosPasan() throws Exception
	{
		if (jugadores.size() == 1)
		{
//			System.out.println(jugadores.size() + " tamaño " + jugadores.get(0));
			clasificacion.añadirJugador(jugadores.get(0));
//			System.out.println("FIN DEL JUEGO");
			juegoAcabado = true;
			finalizarPartida();
			return;
		}
		
		for(Jugador jugador : jugadores)
		{
			if (!jugador.haPasado()) return;
		}
		clasificacion.resolverConflicto(jugadores);
//		System.out.println("FIN DEL JUEGO");
		finalizarPartida();

		return;
	}
	/**
	 * Finaliza la partida llamando a clasificacion.PegarPerdedor()
	 * y paralizndo el hilo principal.
	 * @throws Exception
	 */
	private void finalizarPartida() throws Exception {
		clasificacion.PegarPerdedor(); // HabrÃ¡ que cambiarlo ligeramente para Android.
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Cambia el estado de la variable haPasado de todos los
	 * jugadores a false.
	 */
	public void reinicializarEstadoJugadores ()
	{
		for (Jugador jugador : jugadores)
		{
			jugador.pasa(false);
		}
	}
	/**
	 * Comprueba que algÃºn jugador haya tirado baza
	 * y este esperando a que alguien robe o le llegue
	 * Como este mÃ©todo se llama desde cuando alguien 
	 * ha robado por tirar un As o 2. Elimina al jugador ya que
	 * no le llegarÃ¡ a no ser que sea el jugador Actual. (Es decir
	 * le ha llegado ya)
	 * @throws Exception
	 */
	public void comprobarSinCartasBaza () throws Exception
	{
		//FailFast
		if (!quedanCartas()) return;
		ArrayList<Jugador> eliminar = new ArrayList<Jugador>();
		eliminar.addAll(jugadores);
		Iterator<Jugador> it = eliminar.iterator();
		while (it.hasNext())
		{
			Jugador jugador = (Jugador) it.next();
			if (jugador.isSinCartaPeroTiraBaza())
			{
				if (jugador.equals(jugadorActual)){}
				else 
				{
//					System.out.println(jugador.getNombre() + " acabÃ³ la partida, tirÃ³ una baza que no le ha vuelto");
					eliminarJugador(jugador);
					clasificacion.añadirJugador(jugador);
				}
			}
		}
	}
	/**
	 * Getter de la variable juegoAcabado.
	 * @return
	 */
	public boolean isJuegoAcabado() 
	{
		return juegoAcabado;
	}
	/**
	 * Setter de la variable juegoAcabado.
	 * @param juegoAcabado
	 */
	public void setJuegoAcabado(boolean juegoAcabado) 
	{
		this.juegoAcabado = juegoAcabado;
	}
	
	public void checkCartaComienzo(Carta carta)
	{
		if (carta.mismoValor(1)) asesRobar += 6;
		else if (carta.mismoValor(2)) dosesRobar += 2;
		else if (carta.mismoValor(10)) cartaEnMesa.setPalo(Carta.palos[new Random().nextInt(Carta.palos.length)]);
		else if (carta.mismoValor(11))jugadorActual.cambiarTurno(jugada);
		else if (carta.mismoValor(12)) cambiarSentido();
	}
	
//	public void elegirPaloHumanoScanner()
//	{
//		JugadorHumano jugador = (JugadorHumano) jugadorActual;
//		System.out.println("0 Oros, 1 Copas, 2 Espadas, 3 Bastos");
//		try
//		{
//			int eleccion = sc.nextInt();
//			if (eleccion < 0 || eleccion > 3) throw new Exception();
//			jugador.cambiarPalo(Carta.getPalo(eleccion), jugada);
//		}
//		catch(Exception e)
//		{
//			elegirPaloHumanoScanner();
//		}
//	}
	
//	public void tirarCartaPasarRobarPruebaHumano () throws Exception
//	{
//		JugadorHumano jugador = (JugadorHumano)jugadorActual;
//		System.out.println(jugador.toString());
//		int eleccion = -3;
//		try 
//		{
//			eleccion = sc.nextInt();
//			Carta cartaTirar = jugador.getMano().get(eleccion);
//			jugador.tirarCarta(cartaTirar, jugada);
//		}
//		catch (Exception e) 
//		{
//			System.out.println("ERROR " + eleccion);
//			if (eleccion == -1) 
//			{
//				jugador.robar(jugada);
//				tirarCartaPasarRobarPruebaHumano();
//			}
//			else if (eleccion == -2) jugador.pasar(jugada);
//			else tirarCartaPasarRobarPruebaHumano();
//		}
//	}
	
}
