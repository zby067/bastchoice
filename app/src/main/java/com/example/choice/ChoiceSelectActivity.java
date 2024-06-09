package com.example.choice;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ChoiceSelectedActivity extends Activity {
    private Choice choice;

    private void initViews() {
        ((ImageView)findViewById(R.id.iv_close)).setOnClickListener(new _$$Lambda$ChoiceSelectedActivity$a7cyndx5YkggGsO0lycuiQ_j1Jo(this));
        ((ImageView)findViewById(R.id.iv_color)).setColorFilter(Color.parseColor(this.choice.getColor()));
        TextView textView = (TextView)findViewById(R.id.iv_relust);
        textView.setTextColor(Color.parseColor(this.choice.getColor()));
        textView.setText(this.choice.getDesc());
    }

    private void initWindow() {
        Window window = getWindow();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int j = displayMetrics.widthPixels;
        int i = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = j - getResources().getDimensionPixelSize(R.dimen.dialog_margin) * 2;
        layoutParams.height = i;
        window.setAttributes(layoutParams);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_choice_selelcted);
        this.choice = (Choice)getIntent().getSerializableExtra("choice");
        if (this.choice == null) {
            finish();
            return;
        }
        initWindow();
        initViews();
    }
}
