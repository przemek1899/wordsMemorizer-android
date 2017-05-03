package com.vcf.przemek.wordsmemorizer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vcf.przemek.wordsmemorizer.R;
import com.vcf.przemek.wordsmemorizer.objects.GroupWords;


/**
 * Created by Przemek on 2017-05-03.
 */

public class SpinnerGroupAdapter extends ArrayAdapter<GroupWords> {

    private final Context context;
    //    private final String[] values;
    private final GroupWords[] values;

    public SpinnerGroupAdapter(Context context, GroupWords[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.spinner_group, parent, false);

        TextView placeView = (TextView) rowView.findViewById(R.id.group_name_entry);
        placeView.setText(values[position].getName());

        rowView.setTag(values[position].getId());
        return rowView;
    }
}
