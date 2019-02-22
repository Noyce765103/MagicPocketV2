package com.example.magicpocketv1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magicpocketv1.Bean.AccountBean;
import com.example.magicpocketv1.R;
import com.example.magicpocketv1.Utils.GlobalUtil;

import java.util.LinkedList;
import java.util.zip.Inflater;

public class ListViewAdapter extends BaseAdapter {
    private LinkedList<AccountBean> records;
    private LayoutInflater mInflater;
    private Context mContext;
    public ListViewAdapter(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(LinkedList<AccountBean> record){
        this.records = record;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.cell_list_view,null);
            AccountBean recordBean = (AccountBean) getItem(position);
            holder = new ViewHolder(convertView,recordBean);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}

class ViewHolder{
    TextView TvRemark;
    TextView TvMoney;
    ImageView IvCategory;

    public ViewHolder(View view,AccountBean record){
        TvRemark = view.findViewById(R.id.text_view_remark);
        TvMoney = view.findViewById(R.id.text_view_money);
        IvCategory = view.findViewById(R.id.image_view_category);

        TvRemark.setText(record.getRemark());
        if(record.getMoney_type() == 1){
            TvMoney.setText("-" + record.getMoney());
        }else{
            TvMoney.setText("+" + record.getMoney());
        }

        IvCategory.setImageResource(GlobalUtil.getInstance().getRes(record.getCategory()));
    }
}