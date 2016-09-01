package clases_basicas;

import com.games.sardineta.Principal;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastMsg
{
	Context ctx;
	public ToastMsg (Context context)
	{
		this.ctx = context;
	}
	
	public void makeText(String text)
	{
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
	}

}
