package com.example.choice;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ChoiceAdapter extends ArrayAdapter<Choice> implements View.OnClickListener {
    private OnChoiceClickListener onChoiceClickListener;

    public ChoiceAdapter(Context paramContext, List<Choice> paramList) {
        super(paramContext, 0, paramList);
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        Choice choice = (Choice)getItem(paramInt);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_choice, paramViewGroup, false);
        int i = Color.parseColor(choice.getColor());
        TextView textView1 = (TextView)view.findViewById(R.id.tv_desc);
        textView1.setTextColor(i);
        textView1.setText(choice.getDesc());
        textView1.setSelected(true);
        TextView textView2 = (TextView)view.findViewById(R.id.tv_weight);
        textView2.setText(String.valueOf(choice.getWeight()));
        textView2.setTextColor(i);
        ImageView imageView1 = (ImageView)view.findViewById(R.id.iv_color);
        imageView1.setColorFilter(i);
        ImageView imageView2 = (ImageView)view.findViewById(R.id.iv_delete);
        imageView2.setColorFilter(i);
        textView1.setTag(Integer.valueOf(paramInt));
        textView2.setTag(Integer.valueOf(paramInt));
        imageView1.setTag(Integer.valueOf(paramInt));
        imageView2.setTag(Integer.valueOf(paramInt));
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        return view;
    }

    public void onClick(View paramView) {
        if (this.onChoiceClickListener == null)
            return;
        int i = ((Integer)paramView.getTag()).intValue();
        if (paramView.getId() == R.id.iv_delete) {
            this.onChoiceClickListener.onChoiceDeleteClick(i);
        } else {
            this.onChoiceClickListener.onChoiceContentClick((Choice)getItem(i));
        }
    }

    public void setOnChoiceClickListener(OnChoiceClickListener paramOnChoiceClickListener) {
        this.onChoiceClickListener = paramOnChoiceClickListener;
    }

    public static interface OnChoiceClickListener {
        void onChoiceContentClick(Choice param1Choice);

        void onChoiceDeleteClick(int param1Int);
    }
}
