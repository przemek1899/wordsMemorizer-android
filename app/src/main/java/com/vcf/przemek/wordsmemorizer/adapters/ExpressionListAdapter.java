package com.vcf.przemek.wordsmemorizer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vcf.przemek.wordsmemorizer.R;
import com.vcf.przemek.wordsmemorizer.objects.Expression;

/**
 * Created by Przemek on 2017-05-07.
 */

public class ExpressionListAdapter extends ArrayAdapter<Expression> {

    private final Context context;
    private final Expression[] values;

    public ExpressionListAdapter(Context context, Expression[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.expression_list, parent, false);

        TextView keyView = (TextView) rowView.findViewById(R.id.key_entry);
        keyView.setText(values[position].getKey());

        TextView translationView = (TextView) rowView.findViewById(R.id.translation_entry);
        translationView.setText(values[position].getTranslation());

        rowView.setTag(values[position].getID());
        return rowView;
    }
}
