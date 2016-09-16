package co.allza.deverdad.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import co.allza.deverdad.CargarDatos;
import co.allza.mararewards.R;
import co.allza.deverdad.adapter.LoginListAdapter;
import co.allza.deverdad.items.LoginItem;
/**
 * Created by Tavo on 07/06/2016.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private ListView lista;
    private LoginListAdapter adapter;
    private TextView adquirir;
    private Button comenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        lista=(ListView)findViewById(R.id.listViewLogin);
        adquirir=(TextView)findViewById(R.id.textViewLogin);
        comenzar=(Button)findViewById(R.id.buttonLogin);
        adapter=new LoginListAdapter(getApplicationContext(),R.layout.listview_login);
        adapter.add(new LoginItem("Verifica la cobertura de tu seguro contratado.",R.drawable.ic_verified_user_white_24dp));
        adapter.add(new LoginItem("Recibe notificaciones para que siempre estes seguro.",R.drawable.ic_notifications_active_white_24dp));
        adapter.add(new LoginItem("Accede a tu línea de emergencia para cada seguro que tengas.",R.drawable.ic_ring_volume_white_24dp));
        lista.setAdapter(adapter);
        adquirir.setTypeface(CargarDatos.getTypeface(getApplicationContext(), CargarDatos.ROBOTO_MEDIUM));
        comenzar.setOnClickListener(this);
        adquirir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.buttonLogin:
                Intent i = new Intent(LoginActivity.this, LoginCodigoActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.textViewLogin:
                Intent ii = new Intent(LoginActivity.this, CallToActionActivity.class);
                startActivity(ii);
        }

    }
}
