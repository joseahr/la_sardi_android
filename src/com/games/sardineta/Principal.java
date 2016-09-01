package com.games.sardineta;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import clases_basicas.*;

public class Principal extends ActionBarActivity {

	Mesa mesa;
	
	public TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		tv = (TextView)findViewById(R.id.tv);
		mesa = new Mesa(Principal.this, 3, 1, 3);
		mesa.añadirJugador(new JugadorBot("Jose"));
		mesa.añadirJugador(new JugadorBot("Hasan"));
		mesa.añadirJugador(new JugadorBot("Abdul"));
		
		Thread t;
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Looper.prepare();
				try {
					mesa.empezarPartida();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d("ERROR", e.getMessage());
				}
				Looper.loop();
			}
		};
		
		t = new Thread(r);
		t.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
