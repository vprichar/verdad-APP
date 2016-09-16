package co.allza.deverdad.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import co.allza.deverdad.CargarDatos;
import co.allza.mararewards.R;
import co.allza.deverdad.adapter.SegurosPagerAdapter;
import co.allza.deverdad.interfaces.VolleyCallback;
import co.allza.deverdad.items.CustomerItem;
import io.realm.Realm;
/**
 * Created by Tavo on 10/06/2016.
 */
public class LoginCodigoActivity extends Activity implements View.OnClickListener, VolleyCallback {
    TextView introducir;
    EditText editTextCodigo;
    Button botonEntrar;
    CustomerItem customer;
    ProgressBar progress;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login_codigo);

        introducir= (TextView)findViewById(R.id.textViewLoginCodigo);
        editTextCodigo=(EditText)findViewById(R.id.editTextLoginCodigo);
        botonEntrar=(Button)findViewById(R.id.buttonLoginCodigo);
        progress=(ProgressBar) findViewById(R.id.progress);

        introducir.setTypeface(CargarDatos.getTypeface(getApplicationContext(),CargarDatos.ROBOTO_MEDIUM));
        editTextCodigo.setTypeface(CargarDatos.getTypeface(getApplicationContext(),CargarDatos.RUBIK_REGULAR));
        botonEntrar.setTypeface(CargarDatos.getTypeface(getApplicationContext(),CargarDatos.ROBOTO_MEDIUM));
        botonEntrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLoginCodigo:
                CargarDatos.getRealm(this);
                CargarDatos.getTokenFromServer(getApplicationContext(),editTextCodigo.getText().toString(),this);
                botonEntrar.setEnabled(false);
                progress.setVisibility(View.VISIBLE);
                progress.setProgress(15);
                break;
        }
    }

    @Override
    public void onSuccess(SegurosPagerAdapter result) {

        Realm realm =CargarDatos.getRealm(getApplicationContext());
        customer=new CustomerItem();
        customer.setUsertoken(editTextCodigo.getText().toString());
        customer.setToken(token);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(customer);
        realm.commitTransaction();
        Intent i = new Intent(LoginCodigoActivity.this, SegurosActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onFailure(String error) {
       // Toast.makeText(LoginCodigoActivity.this, "Hubo un error al intentar conectar", Toast.LENGTH_SHORT).show();
        Toast.makeText(LoginCodigoActivity.this, error, Toast.LENGTH_SHORT).show();
        botonEntrar.setEnabled(true);
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onTokenReceived(String token) {
        this.token=token;
        CargarDatos.setToken(token);
        CargarDatos.setUser(editTextCodigo.getText().toString());
        CargarDatos.pullSeguros(LoginCodigoActivity.this,editTextCodigo.getText().toString(),token,LoginCodigoActivity.this);
    }
}
