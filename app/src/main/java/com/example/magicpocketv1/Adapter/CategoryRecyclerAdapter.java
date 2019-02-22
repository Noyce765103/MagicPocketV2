package com.example.magicpocketv1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.magicpocketv1.Bean.AccountBean;
import com.example.magicpocketv1.Bean.CategoryResBean;
import com.example.magicpocketv1.R;
import com.example.magicpocketv1.Utils.GlobalUtil;

import java.util.LinkedList;

import static com.example.magicpocketv1.R.drawable.bg_category_selected;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder>{
    private LayoutInflater layoutInflater;
    public Context context;
    private LinkedList<CategoryResBean> cellList = GlobalUtil.getInstance().costRes;

    public String getSelected() {
        return selected;
    }

    private String selected = "";

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    private OnCategoryClickListener onCategoryClickListener;

    public CategoryRecyclerAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        selected = cellList.get(0).title;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.cell_category,viewGroup,false);
        CategoryViewHolder mViewHolder = new CategoryViewHolder(view);

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        final CategoryResBean temp = cellList.get(i);
        categoryViewHolder.imageView.setImageResource(temp.res);
        categoryViewHolder.textView.setText(temp.title);
        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = temp.title;
                notifyDataSetChanged();
                if(onCategoryClickListener != null){
                    onCategoryClickListener.onClick(selected);
                }
            }
        });

        if(categoryViewHolder.textView.getText().toString().equals(selected)){
            categoryViewHolder.background.setBackgroundResource(R.drawable.bg_category_selected);
        }else{
            categoryViewHolder.background.setBackgroundResource(R.color.colorLight);
        }
    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }

    public void changeType(AccountBean.Type type){
        if(type == AccountBean.Type.MONEY_TYPE_OUTCOME){
            cellList = GlobalUtil.getInstance().costRes;
        }else{
            cellList = GlobalUtil.getInstance().earnRes;
        }
        selected = cellList.get(0).title;
        notifyDataSetChanged();
    }

    public interface OnCategoryClickListener{
        void onClick(String category);
    }
}
class CategoryViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout background;
    ImageView imageView;
    TextView textView;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        background = itemView.findViewById(R.id.cell_background);
        imageView = itemView.findViewById(R.id.imageView_category);
        textView = itemView.findViewById(R.id.textView_category);

    }
}