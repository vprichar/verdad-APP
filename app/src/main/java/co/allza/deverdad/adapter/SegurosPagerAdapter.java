package co.allza.deverdad.adapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import co.allza.deverdad.CargarDatos;
import co.allza.mararewards.R;
import co.allza.deverdad.interfaces.DialogCallback;
import co.allza.deverdad.interfaces.VolleyCallback;
import co.allza.deverdad.items.SeguroItem;
/**
 * Created by Tavo on 17/06/2016.
 */
public class SegurosPagerAdapter extends PagerAdapter implements VolleyCallback {
    Context context;
    LayoutInflater inflater;
    ArrayList<SeguroItem> pages = new ArrayList<>();
    TextView poliza,aseguradora,seguro,beneficiario,renovacion,emergencia;
    ImageView info,aseguradoraIcono,seguroIcono,beneficiarioIcono,renovacionIcono,emergenciaIcono;
    Calendar calendar;
    Date fechaActual;
    Date fechaSeguro;
    SimpleDateFormat parserFecha;
    DialogCallback callback;

    public SegurosPagerAdapter(Context context, ArrayList<SeguroItem> list) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.pages=list;
        parserFecha=new SimpleDateFormat("dd/MMM/yyyy");
        calendar=Calendar.getInstance();
        fechaActual=calendar.getTime();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View row = inflater.inflate(R.layout.listview_seguros, container, false);
        poliza=(TextView)row.findViewById(R.id.cardPoliza);
        aseguradora=(TextView)row.findViewById(R.id.cardAseguradora);
        seguro=(TextView)row.findViewById(R.id.cardNombreSeguro);
        beneficiario=(TextView)row.findViewById(R.id.cardBeneficiario);
        renovacion=(TextView)row.findViewById(R.id.cardRenovacion);
        emergencia=(TextView)row.findViewById(R.id.cardEmergencia);
        info=(ImageView)row.findViewById(R.id.cardInfoIcono);
        aseguradoraIcono=(ImageView)row.findViewById(R.id.cardAseguradoraIcono);
        seguroIcono=(ImageView)row.findViewById(R.id.cardNombreSeguroIcono);
        beneficiarioIcono=(ImageView)row.findViewById(R.id.cardBeneficiarioIcono);
        renovacionIcono=(ImageView)row.findViewById(R.id.cardRenovacionIcono);
        emergenciaIcono=(ImageView)row.findViewById(R.id.cardEmergenciaIcono);

        final SeguroItem item = pages.get(position);
        poliza.setText("Póliza: "+item.getPolicy());
        aseguradora.setText(item.getName());
        seguro.setText(item.getDescription());
        beneficiario.setText(item.getInsured_name());
        renovacion.setText("Renovación el "+item.getExpiration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            emergencia.setText("Emergencia al "+ PhoneNumberUtils.formatNumber(item.getEmergency(),"MX"));
        }
        else{
        emergencia.setText("Emergencia al "+ PhoneNumberUtils.formatNumber(item.getEmergency()));}


        poliza.setTypeface(CargarDatos.getTypeface(context,CargarDatos.RUBIK_REGULAR));
        aseguradora.setTypeface(CargarDatos.getTypeface(context,CargarDatos.RUBIK_REGULAR));
        seguro.setTypeface(CargarDatos.getTypeface(context,CargarDatos.RUBIK_REGULAR));
        beneficiario.setTypeface(CargarDatos.getTypeface(context,CargarDatos.RUBIK_REGULAR));
        renovacion.setTypeface(CargarDatos.getTypeface(context,CargarDatos.RUBIK_REGULAR));
        emergencia.setTypeface(CargarDatos.getTypeface(context,CargarDatos.RUBIK_MEDIUM));

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               callback=CargarDatos.getDialogCallback();
                callback.onDialogPetition(position);

            }
        });
        final int pos=position;
        emergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("tel:"+pages.get(pos).getEmergency().toString()));
                context.startActivity(intent);

            }
        });

        container.addView(row);

        try {
            fechaSeguro= parserFecha.parse(item.getExpiration());
            if(fechaActual.after(fechaSeguro))
            {
                poliza.setTextColor(context.getResources().getColor(R.color.grisVencido));
                aseguradora.setTextColor(context.getResources().getColor(R.color.grisVencido));
                seguro.setTextColor(context.getResources().getColor(R.color.grisVencido));
                beneficiario.setTextColor(context.getResources().getColor(R.color.grisVencido));
                emergencia.setTextColor(context.getResources().getColor(R.color.grisVencido));
                renovacion.setText("Venció el "+item.getExpiration());
                renovacion.setTypeface(CargarDatos.getTypeface(context,CargarDatos.RUBIK_MEDIUM));
                renovacion.setTextColor(context.getResources().getColor(R.color.rectanguloSplash));
                info.setImageResource(R.drawable.info_vencido);
                aseguradoraIcono.setImageResource(R.drawable.briefcase_vencido);
                seguroIcono.setImageResource(R.drawable.verified_vencido);
                beneficiarioIcono.setImageResource(R.drawable.account_vencido);
                renovacionIcono.setImageResource(R.drawable.history_vencido);
                emergenciaIcono.setImageResource(R.drawable.ring_vencido);

                return row;
            }
        } catch (ParseException e) {  e.printStackTrace();        }

        return row;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public ArrayList<SeguroItem> getArrayList()
    {
        return pages;
    }

    @Override
    public void onSuccess(SegurosPagerAdapter result) {

    }

    @Override
    public void onFailure(String error) {

    }

    @Override
    public void onTokenReceived(String token) {

    }
}
