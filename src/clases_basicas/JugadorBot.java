package clases_basicas;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;

public class JugadorBot extends Jugador 
{

	public JugadorBot(String nombre) 
	{
		super(nombre, Jugador.BOT);
	}
	
	/**
	 * Recibe una Carta (La carta en la mesa).
	 * Devuelve la carta que tira el jugador.
	 * Este método solo deben usarlo los BOTS.
	 * @param cartaEnMesa
	 * @return Carta
	 */
	public Carta tirarCarta (Carta cartaEnMesa)
	{
		 ArrayList<Carta> mano = super.getMano();
		ordenarManoPorValor();
		revertirOrdenMano();
		if (cartasQuePuedeTirar(cartaEnMesa).isEmpty()) throw new NullPointerException();
		if (cartasSueltas(cartasQuePuedeTirar(cartaEnMesa)).isEmpty())
		{
			pasa(false);
			Log.d("DEBUG", "PUEDE TIRAR");
			Carta cartaTirar = cartasQuePuedeTirar(cartaEnMesa).get(0);
			mano.remove(mano.indexOf(cartaTirar));
			return cartaTirar;
		}
		else
		{
			pasa(false);
			Log.d("DEBUG", "PUEDE TIRAR");
			Carta cartaTirar = cartasSueltas(cartasQuePuedeTirar(cartaEnMesa)).get(0);
			mano.remove(mano.indexOf(cartaTirar));
			return cartaTirar;
		}
		
	}
	
	/**
	 * Devuelve el palo al que desea cambiar el jugador.
	 * Función hecha para que los BOTS elijan el palo
	 * al que desean cambiar.
	 * @return String
	 */
	public String elegirPalo(Jugada jugada)
	{
		if (super.getMano().isEmpty())
			return Carta.palos[new Random().nextInt(Carta.palos.length-1)];
		ArrayList<Carta> cartas = super.getMano();
		int oros= 0, copas = 0, espadas = 0, bastos = 0;
		for (Carta carta : cartas)
		{
			if (carta.getPalo() == Carta.OROS) oros += 1;
			else if (carta.getPalo() == Carta.COPAS) copas += 1;
			else if (carta.getPalo() == Carta.ESPADAS) espadas += 1;
			else if (carta.getPalo() == Carta.BASTOS) bastos += 1;
		}
		String mayor = "";
		int mayorInt = 0;
		for (String palo : Carta.palos)
		{
			if (palo == Carta.OROS && oros > mayorInt)
			{
				mayorInt = oros;
				mayor = Carta.OROS;
			}
			else if (palo == Carta.COPAS && copas > mayorInt)
			{
				mayorInt = copas;
				mayor = Carta.COPAS;
			}
			else if (palo == Carta.ESPADAS && espadas > mayorInt)
			{
				mayorInt = espadas;
				mayor = Carta.ESPADAS;
			}
			else if (palo == Carta.BASTOS && bastos > mayorInt)
			{
				mayorInt = bastos;
				mayor = Carta.BASTOS;
			}
		}
		return mayor;
		
	}

}
