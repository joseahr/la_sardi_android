package clases_basicas;

import java.util.ArrayList;

import android.util.Log;

public class JugadorHumano extends Jugador 
{

	public JugadorHumano(String nombre) 
	{
		super(nombre, Jugador.HUMANO);
	}
	
	/**
	 * Método que cabia de palo la carta en la mesa
	 * @param palo
	 * @param jugada
	 */
	public void cambiarPalo(String palo, Jugada jugada) 
	{
		jugada.mesa.getCartaMesa().setPalo(palo);
	}
	
	/**
	 * Método para que los humanos tiren carta, 
	 * comprueba que sea un humano el que ha tirado y que 
	 * la carta que ha tirado es una carta válida
	 * @param carta
	 * @param mesa
	 * @throws Exception
	 */
	public void tirarCarta(Carta carta, Jugada jugada) throws Exception
	{ 
		Mesa mesa = jugada.mesa;
		// Comprobamos por si a caso que el jugador es humano
		if (mesa.getJugadorActual().getTipo() == Jugador.BOT)
			throw new Exception("El jugador debe ser humano");
		ArrayList<Carta> cartas = mesa.getJugadorActual().getMano();
		// Comprobamos que ha tirado una carta válida
		if (carta.mismoValor(mesa.getCartaMesa())){}
		else if (carta.mismoPalo(mesa.getCartaMesa())) {}
		else if (carta.mismoValor(10)) {}
		else 
		{
			throw new Exception("Carta no válida");
		}
		try
		{
			cartas.remove(cartas.indexOf(carta));
			super.setUltimaCartaTirada(carta);
			super.pasa(false);
			mesa.setCartaMesa(carta);
			Log.d("DEBUG", "HAS TIRADO " + carta.toString());
		}
		catch (NullPointerException npe)
		{
			// Mostrar mensaje de error
			return;
		}
		// Si ha tirado una carta válida
		if (mesa.getJugadorActual().isTirarBazaObligatorio())
		{
			if (mesa.asesRobar > 0) 
				if (super.getUltimaCartaTirada().getValor() != 1)
				{
					mesa.jugada.robar(mesa.asesRobar);
					mesa.jugada.resolverJugada();
				}
			else if (mesa.dosesRobar > 0) 
				if (super.getUltimaCartaTirada().getValor() != 2)
				{
					mesa.jugada.robar(mesa.dosesRobar);
					mesa.jugada.resolverJugada();
				}
			super.setTirarBazaObligatorio(false);
		}
		super.comprobarFinal(jugada);
		mesa.jugada.comprobarCartaTiradaYContinuar(carta);
		
	}

	/** 
	 * Método que permite pasar al jugador humano
	 * @throws Exception
	 */
	public void pasar (Jugada jugada) throws Exception
	{
		if (getTipo() == Jugador.BOT)
			throw new Exception("Jugador no válido");
		if (jugada.mesa.quedanCartas()) 
			throw new Exception("No puedes pasa, aún quedan cartas");
		super.pasa(true);
		jugada.mesa.getJugadorActual().cambiarTurno(jugada);	
		jugada.resolverJugada();
	}
	/**
	 * Método que permite al jugador humano robar cartas
	 * @throws Exception 
	 * 
	 */
	public void robar (Jugada jugada) throws Exception
	{
		if (super.isTirarBazaObligatorio()) return;
		Mesa mesa = jugada.mesa;
		if (mesa.getJugadorActual().getTipo() == Jugador.BOT)
			throw new Exception("Jugador no válido");
		if (!mesa.quedanCartas()) 
			throw new Exception("No quedan cartas.");
		mesa.jugada.robar(1);
		//mesa.jugada.resolverJugada();
	}
	
	public void roboPorBazaOTiroObligatorio(Mesa mesa) throws Exception
	{
		if (mesa.asesRobar > 0)
		{
			if (mesa.getJugadorActual().tiene(1).isEmpty()) 
			{
				// El jugador roba obligatoriamente las cartas
				mesa.jugada.robar(mesa.asesRobar);
				mesa.asesRobar = 0;
				
			}
			else
			{
				// Si tengo un as.
				mesa.getJugadorActual().setTirarBazaObligatorio(true);
				return;
			}
		}
		
		if (mesa.dosesRobar > 0)
		{
			if (mesa.getJugadorActual().tiene(2).isEmpty()) 
			{
				mesa.jugada.robar(mesa.dosesRobar);
				mesa.dosesRobar = 0;
			}
			else
			{
				// Si tengo un dos.
				mesa.getJugadorActual().setTirarBazaObligatorio(true);
				return;
			}
		}
	}

}
