package com.example.choice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends Activity implements AutoChoiceView.OnPointerStopListener, View.OnClickListener, ChoiceAdapter.OnChoiceClickListener {
    private ChoiceAdapter adapter;

    private ArrayList<Choice> choices = new ArrayList<Choice>();

    private Choice isChoiceExist(String paramString) {
        for (Choice choice : this.choices) {
            if (choice.getId().equals(paramString))
                return choice;
        }
        return null;
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        if (paramInt2 == -1) {
            Choice choice = (Choice)paramIntent.getSerializableExtra("choice");
            if (choice != null) {
                Choice choice1 = isChoiceExist(choice.getId());
                if (choice1 != null) {
                    choice1.setDesc(choice.getDesc());
                    choice1.setWeight(choice.getWeight());
                } else {
                    this.choices.add(choice);
                }
                this.adapter.notifyDataSetChanged();
            }
        }
    }

    public void onChoiceContentClick(Choice paramChoice) {
        Intent intent = new Intent((Context)this, ChoiceAddActivity.class);
        intent.putExtra("choice", paramChoice);
        startActivityForResult(intent, 666);
    }

    public void onChoiceDeleteClick(int paramInt) {
        ColorUtil.restore(((Choice)this.choices.get(paramInt)).getColor());
        this.choices.remove(paramInt);
        this.adapter.notifyDataSetChanged();
    }

    public void onClick(View paramView) {
        if (paramView.getId() == 2131099625)
            startActivityForResult(new Intent((Context)this, ChoiceAddActivity.class), 666);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        AutoChoiceView autoChoiceView = (AutoChoiceView)findViewById(R.id.acv);
        autoChoiceView.setOnPointerStopListener(this);
        ((ImageView)findViewById(R.id.iv_refresh)).setOnClickListener(new _$$Lambda$MainActivity$_Wk_1cB_3jGGZCM3nscDmJ8WR_Q(this, autoChoiceView));
        ((ImageView)findViewById(R.id.iv_select)).setOnClickListener(new _$$Lambda$MainActivity$OKbGKNKimw78pBybOC_ffrMHkBI(autoChoiceView));
        ListView listView = (ListView)findViewById(R.id.lv_choice);
        View view = findViewById(R.id.fl_empty);
        ((ImageView)view.findViewById(R.id.iv_add)).setOnClickListener(this);
        listView.setEmptyView(view);
        view = LayoutInflater.from((Context)this).inflate(R.layout.layout_list_footer, null);
        view.findViewById(R.id.iv_add).setOnClickListener(this);
        listView.addFooterView(view);
        this.adapter = new ChoiceAdapter((Context)this, this.choices);
        this.adapter.setOnChoiceClickListener(this);
        listView.setAdapter((ListAdapter)this.adapter);
    }

    public void onPointerStop(Choice paramChoice) {
        Intent intent = new Intent((Context)this, ChoiceSelectedActivity.class);
        intent.putExtra("choice", paramChoice);
        startActivity(intent);
    }
}
