package com.example.magicpocketv1.Utils;

import android.content.Context;

import com.example.magicpocketv1.Bean.AccountBean;
import com.example.magicpocketv1.Bean.CategoryResBean;
import com.example.magicpocketv1.DataBase.AccountDataBaseHelper;
import com.example.magicpocketv1.MainActivity;
import com.example.magicpocketv1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GlobalUtil {
    private static GlobalUtil instance;
    public AccountDataBaseHelper dataBaseHelper;
    public LinkedList<CategoryResBean> costRes = new LinkedList<>();
    public LinkedList<CategoryResBean> earnRes = new LinkedList<>();
    public Map<String,Integer> map = new HashMap<>();
    public MainActivity activity;

    public static String[] costTitle={"General","Food","Drinks","Groceries","Shopping","Personal","Entertain","Movies","Social",
                                        "Transport","Application","Mobile","Computer","Gifts","Hotel","Travel","Tickets","Books",
                                        "Medical","Transfer"};
    public static int[] costIconRes={R.drawable.general,R.drawable.food,R.drawable.drink,R.drawable.grocery,R.drawable.shopping,
                                 R.drawable.personals,R.drawable.entertain,R.drawable.movie,R.drawable.social,R.drawable.transport,
                                 R.drawable.app,R.drawable.mobile,R.drawable.computer,R.drawable.gift,R.drawable.house,
                                 R.drawable.travel,R.drawable.ticket,R.drawable.book,R.drawable.medical,R.drawable.transfer};

    public static String[] earnTitle={"General","Reimburse","Salary","RedPocket","Part_time","Bonus","Investment"};

    public static int[] earnIconRes={R.drawable.general,R.drawable.reimburse,R.drawable.salary,R.drawable.redpocket,R.drawable.timepart,
                                 R.drawable.bonus,R.drawable.investment};
    private GlobalUtil(){
        for(int i = 0;i<costTitle.length;i++){
            map.put(costTitle[i],i);
        }
    }

    public Context mContext;
    public static GlobalUtil getInstance(){

        if(instance == null){
            instance = new GlobalUtil();
        }
        return instance;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
        dataBaseHelper = new AccountDataBaseHelper(mContext,AccountDataBaseHelper.DB_NAME,null,1);

        for (int i = 0; i < costTitle.length; i++) {
            CategoryResBean temp = new CategoryResBean();
            temp.title = costTitle[i];
            temp.res = costIconRes[i];
            costRes.add(temp);
        }

        for (int i = 0; i < earnTitle.length; i++) {
            CategoryResBean temp = new CategoryResBean();
            temp.title = earnTitle[i];
            temp.res = earnIconRes[i];
            earnRes.add(temp);
        }
    }
    public int getRes(String category){
        for(CategoryResBean res:costRes){
            if (res.title.equals(category)){
                return res.res;
            }
        }

        for(CategoryResBean res:earnRes){
            if (res.title.equals(category)){
                return res.res;
            }
        }
        return costRes.get(0).res;
    }
//获得一天的消费总额
    public double getDayTotal(LinkedList<AccountBean> records){
        double sum = 0.0;
        for(AccountBean temp:records){
            if(temp.getMoney_type() == 1){
                sum += temp.getMoney();
            }
        }
        return sum;
    }
    public ArrayList<Double> getSevenDayData(ArrayList<LinkedList<AccountBean>> record){
        double sum = 0.0;
        ArrayList<Double> result = new ArrayList<>();
        for(LinkedList<AccountBean> temp:record){
            sum = getDayTotal(temp);
            result.add(sum);
        }
        return result;
    }
    //获得过去一周的消费记录总条数
    public int getTotalRecordCount(ArrayList<LinkedList<AccountBean>> record){
        int ans = 0;
        for(int i = 0; i < record.size();i++){
            for (int j = 0;j<record.get(i).size();j++){
                ans++;
            }
        }
        return ans;
    }

    //获得过去一周的消费的种类
    public ArrayList<String> getSevenDayCostTitle(ArrayList<LinkedList<AccountBean>> record){
        ArrayList<String> ans = new ArrayList<>();
        if (record.size() == 0){
            return ans;
        }
        for (int i = 0; i < record.size(); i++) {
            for (int j = 0; j < record.get(i).size();j++){
                AccountBean temp = record.get(i).get(j);
                String costTitle = temp.getCategory();
                if(!ans.contains(costTitle)){
                    ans.add(costTitle);
                }
            }
        }
        return ans;
    }

    /*
    获得过去一周的消费的种类所占比，这里用一个数组对应消费种类以达到计数的目的，数组的大小为costTitle.size()，
    每个种类对应的编号为costTitle数组中默认的顺序。
     */
    public int[] getCostTitleCount(ArrayList<LinkedList<AccountBean>> record){
        int[] ans = new int[GlobalUtil.costTitle.length];
        if(record.size() == 0){
            return ans;
        }
        for (int i = 0; i < record.size(); i++) {
            for (int j = 0; j < record.get(i).size();j++){
                AccountBean temp = record.get(i).get(j);
                String costTitle = temp.getCategory();
                switch (costTitle){
                    case "General":
                        ans[0]++;
                        break;
                    case "Food":
                        ans[1]++;
                        break;
                    case "Drinks":
                        ans[2]++;
                        break;
                    case "Groceries":
                        ans[3]++;
                        break;
                    case "Shopping":
                        ans[4]++;
                        break;
                    case "Personal":
                        ans[5]++;
                    case "Entertain":
                        ans[6]++;
                        break;
                    case "Movies":
                        ans[7]++;
                        break;
                    case "Social":
                        ans[8]++;
                        break;
                    case "Transport":
                        ans[9]++;
                        break;
                    case "Application":
                        ans[10]++;
                        break;
                    case "Mobile":
                        ans[11]++;
                        break;
                    case "Computer":
                        ans[12]++;
                        break;
                    case "Gifts":
                        ans[13]++;
                        break;
                    case "Hotel":
                        ans[14]++;
                        break;
                    case "Travel":
                        ans[15]++;
                        break;
                    case "Tickets":
                        ans[16]++;
                        break;
                    case "Books":
                        ans[17]++;
                        break;
                    case "Medical":
                        ans[18]++;
                        break;
                    case "Transfer":
                        ans[19]++;
                        break;
                }
            }
        }
        return ans;
    }
}
