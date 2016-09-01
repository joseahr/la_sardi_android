/* Este paquete es de libre uso y distribuciÃ³n */
package clases_basicas;

import android.util.Log;

/**
 *  La sardi 2015
 *  Clase Jugada. Representa una Jugada sobre la que se va a 
 *  apoyar la mayorÃ­a de la lÃ³gica del juego junto con la clase Mesa.
 * @author hermo. Jose Ã�ngel Hermosilla Rodrigo. 
 *
 */
public class Jugada 
{
	Mesa mesa;
	
	boolean devolver;
	
	public Jugada (Mesa mesa)
	{
		this.mesa = mesa;
	}
	/**
	 * MÃ©todo que permite tomar la decisiÃ³n a un jugador 
	 * entre tirar y robar.
	 * @throws Exception
	 */
	public void resolverJugada () throws Exception
	{
		mesa.comprobarSiTodosPasan();
		if (mesa.isJuegoAcabado()) return;
		mesa.comprobarSiTodosPasan();
		if (mesa.getJugadorActual().getTipo() == Jugador.HUMANO)
		{
			((JugadorHumano)mesa.getJugadorActual()).roboPorBazaOTiroObligatorio(mesa);
//			mesa.tirarCartaPasarRobarPruebaHumano();
			return;
		}
		else
		{
			if(mesa.getJugadorActual().cartasQuePuedeTirar(mesa.getCartaMesa()).isEmpty())
			{
				if(mesa.asesRobar == 0 && mesa.dosesRobar == 0)robar(); // Si no tiene carta roba
				else { tirar(); }
			}
			else
			{
				tirar();
			}
			
		}
	}
	/**
	 * MÃ©todo especifico para los BOTS
	 * para robar hasta que puedan tirar una carta.
	 * Devuelve true si ha podido robar una carta 
	 * vÃ¡lida para tirar.
	 * @return boolean
	 * @throws Exception
	 */
	public boolean robar() throws Exception {
		// El jugador roba obligatoriamente hasta que pueda tirar.
		devolver = false;
		
		int cont = 0;
		mesa.comprobarSinCartasBaza();
		while (true)
		{
			if (!mesa.quedanCartas()) 
			{
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " PASA 2");
				Log.d("DEBUG", mesa.getJugadorActual().toString());
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + " PASA 2");
				mesa.getJugadorActual().pasa(true);
				mesa.getJugadorActual().cambiarTurno(this);
				resolverJugada();
				break;
			}
			cont ++;
			Carta carta = mesa.getJugadorActual().robarCarta(mesa.baraja);
			if (carta.mismoPalo(mesa.getCartaMesa()))
			{
				devolver = true;
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
				tirar();
				break;
			}
			else if (carta.mismoValor(mesa.getCartaMesa()))
			{
				devolver = true;
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
				tirar();
				break;
			}
			else if (carta.mismoValor(10))
			{
				devolver = true;
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
				tirar();
				break;
			}
			Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
			mesa.displayMsg(mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
			
			sleep(1000);
		}
		return devolver;
	}
	/**
	 * Este mÃ©todo se usa para que los BOTS y HUMANOS 
	 * roben un nÃºmero de cartas 
	 * @param cartasARobar
	 * @throws Exception
	 */
	public void robar(final int cartasARobar) throws Exception 
	{
		// El jugador roba obligatoriamente hasta que pueda tirar.
		mesa.comprobarSinCartasBaza();
		Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " se come " + cartasARobar);
		mesa.displayMsg(mesa.getJugadorActual().getNombre() + " se come " + cartasARobar);
		int cont = 0;
		try
		{
			for(int i = 0; i < cartasARobar; i++)
			{
				if (!mesa.quedanCartas())
					throw new Exception("Baraja vacÃ­a");
				cont ++;
				Carta carta = mesa.getJugadorActual().robarCarta(mesa.baraja);
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + " roba " + carta.toString() + "; cartas robadas: " + cont);
				sleep(1000);
			}
		}
		catch (Exception e)
		{
//			System.out.println("EXCEPTIOOOOON");
			//mesa.getJugadorActual().cambiarTurno(Jugada.this);
			//resolverJugada();
			return;
		}
	}
	/**
	 * Representa una tirada de un BOT.
	 * Antes de tirar comprueba que haya asesaRobar
	 * o dosesARobar y por tanto un 1 o un 2 en la mesa.
	 * Si no tiene un 1 o un 2 respectivamente robarÃ¡ 
	 * las correspondientes cartas.
	 * @throws Exception
	 */
	public void tirar () throws Exception
	{
		// Primero comprobamos que la carta en la mesa sea un 1 o un 2 
		// y no tengamos ningÃºn uno o dos. Con lo cual deberemos robar
		// En funciÃ³n de los ases que se hayan tirado en la misma jugada
		if (mesa.asesRobar > 0)
		{
			if (mesa.getJugadorActual().tiene(1).isEmpty()) 
			{
				robar(mesa.asesRobar);
				mesa.asesRobar = 0;
				
			}
			else
			{
				// Si tengo un as.
				Carta tirar = mesa.getJugadorActual().tiene(1).get(0);
				int index = mesa.getJugadorActual().getMano().indexOf(tirar);
				mesa.setCartaMesa(tirar);
				mesa.getJugadorActual().getMano().remove(index);
				mesa.asesRobar += 6;
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + 
						" dice: AhÃ­ van 6 mÃ¡s. Robas " + mesa.asesRobar + " cartas?");
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + 
						" dice: AhÃ­ van 6 mÃ¡s. Robas " + mesa.asesRobar + " cartas?");
				mesa.getJugadorActual().cambiarTurno(this);
				mesa.reinicializarEstadoJugadores();
				resolverJugada();
				return;
			}
		}
		
		if (mesa.dosesRobar > 0)
		{
			if (mesa.getJugadorActual().tiene(2).isEmpty()) 
			{
				robar(mesa.dosesRobar);
				mesa.dosesRobar = 0;
			}
			else
			{
				// Si tengo un dos.
				Carta tirar = mesa.getJugadorActual().tiene(2).get(0);
				int index = mesa.getJugadorActual().getMano().indexOf(tirar);
				mesa.setCartaMesa(tirar);
				mesa.getJugadorActual().getMano().remove(index);
				mesa.dosesRobar += 2;
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + 
						" dice: AhÃ­ van 2 mÃ¡s. Robas " + mesa.dosesRobar + " cartas?");
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + 
						" dice: Ahí van 2 más. Robas " + mesa.dosesRobar + " cartas?");
				mesa.getJugadorActual().cambiarTurno(this);
				mesa.reinicializarEstadoJugadores();
				resolverJugada();
				return;
			}
		}
		
		
		try 
		{
			// Si puede tira
			Carta carta = ((JugadorBot)mesa.getJugadorActual()).tirarCarta(mesa.getCartaMesa());
			mesa.setCartaMesa(carta);
			mesa.getJugadorActual().setUltimaCartaTirada(carta);
			mesa.getJugadorActual().pasa(false);
			Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " tira " + mesa.getCartaMesa().toString());
			Log.d("DEBUG", mesa.getJugadorActual().toString());
			mesa.displayMsg(mesa.getJugadorActual().getNombre() + " tira " + mesa.getCartaMesa().toString());
			
			mesa.getJugadorActual().comprobarFinal(this);
			
			comprobarCartaTiradaYContinuar(carta);
		} // Fin del try
		
		catch (NullPointerException npe)
		{
			// Si no deberÃ¡ pasar...
			if (!mesa.quedanCartas())
			{
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " PASA 1");
				Log.d("DEBUG", mesa.getJugadorActual().toString());
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + " PASA");
				mesa.getJugadorActual().pasa(true);
				mesa.getJugadorActual().cambiarTurno(this);
				resolverJugada();
			}
			else {robar();}
		}
	}
	/** 
	 * Comprueba carta tirada y continua el bucle.
	 * @param carta
	 * @throws Exception
	 */
	public void comprobarCartaTiradaYContinuar(Carta carta) throws Exception {
		if (carta.getValor() == 1) 
		{
			if (mesa.quedanCartas())
			{
				mesa.asesRobar += 6;
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + 
						" dice: AhÃ­ van 6 mÃ¡s. Robas " + mesa.asesRobar + " cartas?");
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + 
						" dice: Ahí van 6 más. Robas " + mesa.asesRobar + " cartas?");
			}
			mesa.getJugadorActual().cambiarTurno(this);
		}
		else if (carta.getValor() == 2) 
		{
			if (mesa.quedanCartas())
			{
				mesa.dosesRobar += 2;
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + 
						" dice: AhÃ­ van 2 mÃ¡s. Robas " + mesa.dosesRobar + " cartas?");
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + 
						" dice: Ahí van 2 más. Robas " + mesa.dosesRobar + " cartas?");
			}
			mesa.getJugadorActual().cambiarTurno(this);
		}
		else if (carta.getValor() == 10)
		{
			if (mesa.getJugadorActual().getTipo() == Jugador.HUMANO)
			{
//				mesa.elegirPaloHumanoScanner();
				// MOSTRAR MENÃš
				return;
			}
			else
			{
				mesa.getCartaMesa().setPalo(((JugadorBot)mesa.getJugadorActual()).elegirPalo(this));
				Log.d("DEBUG", mesa.getJugadorActual().getNombre() + " cambia a "
					+ mesa.getCartaMesa().getPalo());
				mesa.displayMsg(mesa.getJugadorActual().getNombre() + " cambia a "
						+ mesa.getCartaMesa().getPalo());
			}
			mesa.getJugadorActual().cambiarTurno(this);
		}
		else if (carta.getValor() == 11)
		{
			mesa.displayMsg(mesa.getJugadorActual().getNombre() + " salta a " + mesa.getJugadorActual().getJugadorSiguiente(mesa.jugada).getNombre());
			mesa.getJugadorActual().saltarJugador(this);
		}
		else if (carta.getValor() == 12)
		{
			mesa.displayMsg(mesa.getJugadorActual().getNombre() + " cambia de sentido");
			mesa.cambiarSentido();
		}
		else if (carta.getValor() == 3)
		{
			mesa.displayMsg(mesa.getJugadorActual().getNombre() + " repite");
			Log.d("DEBUG", "repite");
		}
		else if (carta.getValor() == 7)
		{
			mesa.displayMsg(mesa.getJugadorActual().getNombre() + " repite");
		}
		else
		{
			mesa.getJugadorActual().cambiarTurno(this);
		}
		mesa.reinicializarEstadoJugadores();
		sleep(2000);
		resolverJugada();
	}
	/**
	 * Permite "dormir" el hila principal "milis" milisegundos.
	 * @param milis
	 */
	public void sleep (long milis)
	{
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
