package com.example.choice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.UUID;

public class ChoiceAddActivity extends Activity {
    private Choice choice;

    private EditText etDesc;

    private EditText etWeight;

    private void initViews() {
        ((ImageView)findViewById(R.id.iv_close)).setOnClickListener(new _$$Lambda$ChoiceAddActivity$b1lY51QfYMzBlQZoiJ6MmDVxcds(this));
        ImageView imageView = (ImageView)findViewById(R.id.iv_color);
        this.etDesc = (EditText)findViewById(R.id.et_desc);
        this.etWeight = (EditText)findViewById(R.id.et_weight);
        Choice choice = this.choice;
        if (choice != null) {
            this.etDesc.setText(choice.getDesc());
            this.etWeight.setText(String.valueOf(this.choice.getWeight()));
        } else {
            this.choice = new Choice();
            this.choice.setId(UUID.randomUUID().toString());
            this.choice.setColor(ColorUtil.getOneColor());
        }
        imageView.setColorFilter(Color.parseColor(this.choice.getColor()));
        ((TextView)findViewById(2131099660)).setOnClickListener(new _$$Lambda$ChoiceAddActivity$quddr2xDf0agQNmw_VreSTFvVCo(this));
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

    private boolean isDataCheck() {
        String str2 = this.etDesc.getText().toString().trim();
        String str1 = this.etWeight.getText().toString().trim();
        if (TextUtils.isEmpty(str2)) {
            Toast.makeText((Context)this, "请输入选项描述", R.styleable.AutoChoiceView_backgroundColor).show();
            return false;
        }
        if (TextUtils.isEmpty(str1) || Integer.parseInt(str1) <= 0) {
            Toast.makeText((Context)this, "请加入权重设置，范围1-1000", R.styleable.AutoChoiceView_backgroundColor).show();
            return false;
        }
        this.choice.setDesc(str2);
        this.choice.setWeight(Integer.parseInt(str1));
        return true;
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_choice_add);
        this.choice = (Choice)getIntent().getSerializableExtra("choice");
        initWindow();
        initViews();
    }
}
