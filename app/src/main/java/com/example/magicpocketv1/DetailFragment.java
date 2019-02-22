package com.example.magicpocketv1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.magicpocketv1.Adapter.ListViewAdapter;
import com.example.magicpocketv1.Bean.AccountBean;
import com.example.magicpocketv1.Utils.DateUtil;
import com.example.magicpocketv1.Utils.GlobalUtil;

import java.util.LinkedList;

/**
 * 此Fragment是第一个记账界面的子Fragment
 */

@SuppressLint("ValidFragment")
public class DetailFragment extends Fragment implements AdapterView.OnItemLongClickListener {

    private View rootView;
    private TextView textView;
    private ListView listView;

    public String getDate() {
        return date;
    }

    private String date;
    private ListViewAdapter listViewAdapter;
    private LinkedList<AccountBean> records;


    @SuppressLint("ValidFragment")
    public DetailFragment(String date){
        this.date = date;
        records = GlobalUtil.getInstance().dataBaseHelper.locateRecord(date);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.account_fragment,container,false);

        initView();
        return rootView;
    }
    private void initView(){
        textView = rootView.findViewById(R.id.data_text_view);
        listView = rootView.findViewById(R.id.list_view);
        textView.setText(DateUtil.getDataTitle(date));
        listViewAdapter = new ListViewAdapter(getContext());
        listViewAdapter.setData(records);
        listView.setAdapter(listViewAdapter);

        if(listViewAdapter.getCount()>0){
            rootView.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
        }
        listView.setOnItemLongClickListener(this);
    }

    public void reload(){
        records = GlobalUtil.getInstance().dataBaseHelper.locateRecord(date);
        if(listViewAdapter == null){
            return;
        }
        listViewAdapter.setData(records);
        listView.setAdapter(listViewAdapter);
        listViewAdapter.notifyDataSetChanged();


        if(listViewAdapter.getCount()>0){
            rootView.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
        }
    }
    public int getTotalCost(){
        double totalCost = 0;
        for(AccountBean temp:records){
            if(temp.getMoney_type() == 1){
                totalCost += temp.getMoney();
            }
        }

        return (int) totalCost;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(position);
        return false;
    }
    private void showDialog(int index){
        final String[] options = {"Remove","Edit"};
        final AccountBean selectedRecord = records.get(index);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.create();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    String uuid = selectedRecord.getUuid();
                    GlobalUtil.getInstance().dataBaseHelper.removeRecord(uuid);
                    reload();
                    ((AccountFragment)(DetailFragment.this.getParentFragment())).updateHeader();


                }else if(which == 1){
                    Intent intent = new Intent(getContext(),AddRecordActivity.class);
                    Bundle extra = new Bundle();
                    extra.putSerializable("record",selectedRecord);
                    intent.putExtras(extra);
                    startActivityForResult(intent,1);
                }
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reload();
        ((AccountFragment)(DetailFragment.this.getParentFragment())).updateHeader();
    }
}
