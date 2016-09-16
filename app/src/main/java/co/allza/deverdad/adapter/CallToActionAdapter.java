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
import co.allza.deverdad.items.LoginItem;
/**
 * Created by Tavo on 10/06/2016.
 */
public class CallToActionAdapter extends ArrayAdapter<LoginItem> {

    private Context contexto;

    static class CardViewHolder2 {
    TextView line1;
    ImageView image;
}

    public CallToActionAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.contexto=context;
    }

    @Override
    public void add(LoginItem object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public LoginItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder2 viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.listview_calltoaction, parent, false);
            viewHolder = new CardViewHolder2();
            viewHolder.line1 = (TextView) row.findViewById(R.id.listViewCalltoActionTexto);
            viewHolder.image = (ImageView) row.findViewById(R.id.listViewCallToActionIcono);
            row.setTag(viewHolder);

        } else {
            viewHolder = (CardViewHolder2)row.getTag();
        }
        LoginItem item = getItem(position);
        viewHolder.line1.setText(item.getTexto());
        viewHolder.line1.setTypeface(CargarDatos.getTypeface(contexto,CargarDatos.RUBIK_MEDIUM));
        viewHolder.image.setImageResource(item.getIcono());
        return row;
    }
}
