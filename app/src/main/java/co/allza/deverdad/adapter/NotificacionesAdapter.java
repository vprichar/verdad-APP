package co.allza.deverdad.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import co.allza.deverdad.CargarDatos;
import co.allza.mararewards.R;
import co.allza.deverdad.items.NotificacionItem;
/**
 * Created by Tavo on 14/06/2016.
 */
public class NotificacionesAdapter extends ArrayAdapter<NotificacionItem> {



    static class CardViewHolder4 {
       ImageView imagen;
        TextView titulo,subtitulo,valor;
    }

    public NotificacionesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(NotificacionItem object) {
        super.add(object);
    }

    @Override
    public void remove(NotificacionItem object) {
        super.remove(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public NotificacionItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder4 viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_notificaciones, parent, false);
            viewHolder = new CardViewHolder4();
            viewHolder.imagen = (ImageView) row.findViewById(R.id.listViewNotificacionesIcono);
            viewHolder.titulo = (TextView) row.findViewById(R.id.listViewNotificacionesTitulo);
            viewHolder.subtitulo = (TextView) row.findViewById(R.id.listViewNotificacionesSubtitulo);
            viewHolder.valor = (TextView) row.findViewById(R.id.listViewNotificacionesFecha);
            row.setTag(viewHolder);

        } else {
            viewHolder = (CardViewHolder4)row.getTag();
        }
        NotificacionItem item = getItem(position);
        viewHolder.titulo.setText(item.getTitulo());
        viewHolder.subtitulo.setText(item.getSubtitulo());
        viewHolder.valor.setText(item.getValor());
        viewHolder.imagen.setImageResource(item.getImagen());

        viewHolder.titulo.setTypeface(CargarDatos.getTypeface(getContext(),CargarDatos.RUBIK_REGULAR));
        viewHolder.subtitulo.setTypeface(CargarDatos.getTypeface(getContext(),CargarDatos.RUBIK_LIGHT));
        viewHolder.titulo.setTypeface(CargarDatos.getTypeface(getContext(),CargarDatos.RUBIK_LIGHT));

        return row;
    }

}
