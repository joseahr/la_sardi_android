/* Este paquete es de libre uso y distribución */
package clases_basicas;

import java.util.ArrayList;
/**
 *  La sardi 2015
 *  Clase Carta. Representa una Carta.
 * @author hermo. Jose Ángel Hermosilla Rodrigo. 
 *
 */
public class Carta 
{
	private int valor;
	private String palo;
	
	static final String palos [] = {"oros", "copas", "espadas", "bastos"};
	
	static final String OROS = "oros";
	static final String COPAS = "copas";
	static final String ESPADAS = "espadas";
	static final String BASTOS = "bastos";
	
	ArrayList<String> palosList = new ArrayList<String>();
	/**
	 * Constructor. Recibe el valor y el palo
	 * @param valor
	 * @param palo
	 */
	public Carta(int valor, String palo)
	{
		for (String p : palos) palosList.add(p);
		if (!palosList.contains(palo)) throw new Error("Palo no válido.");
		if (valor < 1 || valor > 12) throw new Error("Valor no válido.");
		this.valor = valor;
		this.palo = palo;
	}
	/**
	 * Getter del valor.
	 * @return int
	 */
	public int getValor() 
	{
		return valor;
	}
	/**
	 * Setter del valor.
	 * @param valor
	 */
	public void setValor(int valor) 
	{
		this.valor = valor;
	}
	/** 
	 * Getter del palo.
	 * @return String
	 */
	public String getPalo() 
	{
		return palo;
	}
	/**
	 * Setter del palo
	 * @param palo
	 */
	public void setPalo(String palo) 
	{
		this.palo = palo;
	}
	
	@Override
	public String toString() 
	{
		return this.valor + " de " + this.palo;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		Carta carta = (Carta) obj;
		return this.valor == carta.valor && this.palo == carta.palo;
	}
	/**
	 * Devuelve true si la carta que recibe como parámetro
	 * es del mismo palo que esta.
	 * @param carta
	 * @return boolean
	 */
	public boolean mismoPalo(Carta carta) 
	{
		return this.palo == carta.getPalo();
	}
	/**
	 * Devuelve true si la carta que recibe como parámetro 
	 * es del mismo valor que esta.
	 * @param carta
	 * @return boolean
	 */
	public boolean mismoValor(Carta carta) 
	{
		return this.valor == carta.getValor();
	}
	/**
	 * Devuelve true si esta carta es del mismo
	 * palo que el palo que se pasa como parámetro.
	 * @param palo
	 * @return String
	 */
	public boolean mismoPalo(String palo) 
	{
		return this.palo == palo;
	}
	/**
	 * Devuelve true si esta carta es del mismo valor 
	 * que el valor que se pasa como parámetro.
	 * @param valor
	 * @return boolean
	 */
	public boolean mismoValor(int valor) 
	{
		return this.valor == valor;
	}
	/**
	 * Este método sirve es usado a la hora de ordenar
	 * las manos por palo. (Oros irá antes que Copas, luego Espadas
	 * y finalmente bastos.
	 * @return int
	 */
	public int getNumeroDePalo ()
	{
		if (!palosList.contains(this.palo)) throw new Error ("Palo no válido.");
		return palosList.indexOf(this.palo);
	}
	public static String getPalo(int valorListaPalos)
	{
		ArrayList<String> palosList = new ArrayList<String>();
		String palos [] = {"oros", "copas", "espadas", "bastos"};
		for (String s : palos) palosList.add(s);
		return palosList.get(valorListaPalos);
	}
	
	// Implementar método getImage() o no
	
}
