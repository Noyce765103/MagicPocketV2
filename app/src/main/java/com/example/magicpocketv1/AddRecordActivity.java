package com.example.magicpocketv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magicpocketv1.Adapter.CategoryRecyclerAdapter;
import com.example.magicpocketv1.Bean.AccountBean;
import com.example.magicpocketv1.Utils.GlobalUtil;

/**
 * 记账详细界面
 */
public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener, CategoryRecyclerAdapter.OnCategoryClickListener {

    private TextView moneyText;
    private String userinput="";
    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter adapter;
    private EditText editText;

    private String category = "General";
    private AccountBean.Type type = AccountBean.Type.MONEY_TYPE_OUTCOME;
    private String remark = category;
    AccountBean record = new AccountBean();
    private boolean inEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_seven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);
        moneyText = findViewById(R.id.text_view_add_money);
        //小数点的监听事件
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userinput.contains(".")){
                    userinput += ".";
                }
            }
        });

        //种类的监听事件
        findViewById(R.id.type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == AccountBean.Type.MONEY_TYPE_OUTCOME){
                    type = AccountBean.Type.MONEY_TYPE_INCOME;
                }else {
                    type = AccountBean.Type.MONEY_TYPE_OUTCOME;
                }
                adapter.changeType(type);
                category = adapter.getSelected();

            }
        });
        //完成按钮的监听事件
        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double money;
                if(!userinput.equals("")){
                    money = Double.valueOf(userinput);

                    record.setMoney(money);
                    if(type == AccountBean.Type.MONEY_TYPE_OUTCOME){
                        record.setMoney_type(1);
                    }else{
                        record.setMoney_type(2);
                    }
                    record.setCategory(adapter.getSelected());
                    record.setRemark(editText.getText().toString());

                    if (inEdit){
                        GlobalUtil.getInstance().dataBaseHelper.editRecord(record.getUuid(),record);


                    }else{
                        GlobalUtil.getInstance().dataBaseHelper.addRecord(record);
                    }
                    finish();

                }else{
                    money = 0.0;
                    Toast.makeText(getApplicationContext(),"金额为0元，请重新输入！",Toast.LENGTH_LONG).show();
                }

            }
        });
        //回退按钮的监听事件
        findViewById(R.id.backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userinput.length()>0){
                    userinput = userinput.substring(0,(userinput.length()-1));
                }
                if(userinput.length()>0 && userinput.charAt(userinput.length()-1) == '.'){
                    userinput = userinput.substring(0,(userinput.length()-1));
                }
                updateMoney();
            }
        });
        editText = findViewById(R.id.edit_text1);


        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CategoryRecyclerAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.notifyDataSetChanged();
        adapter.setOnCategoryClickListener(this);

        editText.setText(remark);
        AccountBean record = (AccountBean) getIntent().getSerializableExtra("record");
        if (record != null){
            inEdit = true;
            this.record = record;
        }
    }


    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String input = button.getText().toString();
        if(userinput.contains(".")){
            if(userinput.split("\\.").length == 1 || userinput.split("\\.")[1].length()<2){
                userinput += input;
            }

        }else{
            if(userinput.length() < 12){
                userinput += input;
            }
        }
        updateMoney();
    }
    private void updateMoney(){

        if(userinput.contains(".")){
            if(userinput.split("\\.").length == 1){
                moneyText.setText(userinput+"00");
            }else if(userinput.split("\\.")[1].length() == 1){
                moneyText.setText(userinput+"0");
            }else{
                moneyText.setText(userinput);
            }
        }else{
            if(userinput.length() == 0){
                moneyText.setText("0.00");
            }else {
                moneyText.setText(userinput+".00");
            }
        }
    }

    @Override
    public void onClick(String category) {
        this.category = category;
        editText.setText(category);
    }
}
