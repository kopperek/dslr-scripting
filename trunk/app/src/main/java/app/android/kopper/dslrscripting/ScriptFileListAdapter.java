package app.android.kopper.dslrscripting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class ScriptFileListAdapter extends BaseAdapter {

    private final Context context;
    private List<ScriptFile> values=new LinkedList<>();

    private int selection=-1;

    public ScriptFileListAdapter(Context context) {
        this.context=context;
    }

    public void setValues(List<ScriptFile> values) {
        this.values=values;
        this.selection=-1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public ScriptFile getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
        public View getView(int position,View convertView,ViewGroup parent) {
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.main_list_row, parent, false);
            ScriptFile scriptFile=values.get(position);
            ((TextView)rowView.findViewById(R.id.text_name)).setText(scriptFile.getName());
            ((TextView)rowView.findViewById(R.id.text_desc)).setText(scriptFile.getDesc());
            ((TextView)rowView.findViewById(R.id.text_author)).setText(scriptFile.getAuthor());
            if(position==selection)
                ((ImageView)rowView.findViewById(R.id.selection)).setImageResource(R.drawable.ic_circle_selected);
            return rowView;
        }

    public void setSelection(int selection) {
        if(selection<values.size()) {
            this.selection=selection;
            notifyDataSetChanged();
        }
    }

    public int getSelection() {
        return selection;
    }
}
