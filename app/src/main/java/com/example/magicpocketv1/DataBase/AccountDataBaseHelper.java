package com.example.magicpocketv1.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.magicpocketv1.Bean.AccountBean;

import java.util.LinkedList;

/**
 * 数据库操作类的封装
 */
public class AccountDataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Account";
    private static final String CREATE_DATABASE = "create table Account ("
            + "id integer primary key autoincrement,"
            + "uuid text,"
            + "type integer,"
            + "category integer,"
            + "remark text,"
            + "money double,"
            + "date date )";

    //创建数据库
    public AccountDataBaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //增加
    public void addRecord(AccountBean bean){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",bean.getUuid());
        values.put("type",bean.getMoney_type());
        values.put("category",bean.getCategory());
        values.put("remark",bean.getRemark());
        values.put("money",bean.getMoney());
        values.put("date",bean.getDate());
        db.insert(DB_NAME,null,values);
    }

    //删除
    public void removeRecord(String uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"uuid = ?",new String[]{uuid});
    }

    //改
    public void editRecord(String uuid,AccountBean bean){
        removeRecord(uuid);
        bean.setUuid(uuid);
        addRecord(bean);
    }

    //根据日期查询数据
    public LinkedList<AccountBean> loacteRecord(String dateInfo){
        LinkedList<AccountBean> answer = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select DISTINCT * from Account where date = ?",new String[]{dateInfo});

        if(cursor.moveToFirst()){
            do {
                String uuid  = cursor.getString(cursor.getColumnIndex("uuid"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                Integer category = cursor.getInt(cursor.getColumnIndex("category"));
                String remark  = cursor.getString(cursor.getColumnIndex("remark"));
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                String date = cursor.getString(cursor.getColumnIndex("data"));

                AccountBean temp = new AccountBean();
                temp.setUuid(uuid);
                temp.setMoney_type(type);
                temp.setCategory(category);
                temp.setRemark(remark);
                temp.setMoney(money);
                temp.setDate(date);

                answer.add(temp);

            }while(cursor.moveToNext());
        }
        cursor.close();
        return answer;
    }

    //查询之前的数据日期
    LinkedList<String> CountDate(){
        LinkedList<String> dates = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from Account order by date asc",new String[]{});
        if(cursor.moveToFirst()){
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if(!dates.contains(date)){
                    dates.add(date);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return dates;
    }

}
