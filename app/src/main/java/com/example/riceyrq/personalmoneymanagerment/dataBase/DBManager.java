package com.example.riceyrq.personalmoneymanagerment.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.riceyrq.personalmoneymanagerment.define.Message;
import com.example.riceyrq.personalmoneymanagerment.define.User;
import com.example.riceyrq.personalmoneymanagerment.util.TimeUtil;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //注册
    public void register(User user) throws Exception{
        db.beginTransaction();
        try {
            db.execSQL("insert into user values('" + user.getUserName() + "','" + user.getUserPas()
                        + "','" + user.getPasQuestion() + "','" + user.getPasAnswer() + "')");
            /*String username = user.getUserName().trim();
            db.execSQL("insert into user values(?,?,?,?)",
                    new Object[]{username, user.getUserPas(), user.getPasQuestion().trim(), user.getPasAnswer().trim()});*/
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    //登录
    public boolean login(User user) throws Exception {
        Cursor c = db.rawQuery("select * from user where username='" + user.getUserName() + "' and password='" + user.getUserPas() + "'", null);
        while (c.moveToNext()){
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    //修改密码
    public void changePas(User user) throws Exception{
        db.beginTransaction();
        try{
            db.execSQL("update user set password=? where username=?",
                    new Object[]{user.getUserPas(), user.getUserName()});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    //判断当前用户名是否存在
    public boolean isNameExsist(User user) throws Exception{
        Cursor c = db.rawQuery("select * from user where username='" + user.getUserName() + "'", null);
        while (c.moveToNext()){
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    //获取密保问题及答案
    public User getPasQueAndAns(User user) throws Exception{
        Cursor c = db.rawQuery("select * from user where username='" + user.getUserName() + "'", null);
        while (c.moveToNext()){
            user.setPasQuestion(c.getString(c.getColumnIndex("pasquestion")));
            user.setPasAnswer(c.getString(c.getColumnIndex("pasanswer")));
        }
        return user;
    }

    //获取全部用户信息
    public List<User> getAllUser() throws Exception{
        ArrayList<User> users = new ArrayList<>();
        Cursor c = db.rawQuery("select * from user", null);
        while (c.moveToNext()){
            User user = new User();
            user.setUserName(c.getString(c.getColumnIndex("username")));
            user.setUserPas(c.getString(c.getColumnIndex("password")));
            user.setPasQuestion(c.getString(c.getColumnIndex("pasquestion")));
            user.setPasAnswer(c.getString(c.getColumnIndex("pasanswer")));
            users.add(user);
        }
        c.close();
        return users;
    }

    //添加收支信息
    public void addMessage(Message message) throws Exception{
        db.beginTransaction();
        try {
            db.execSQL("insert into message values(?,?,?,?,?,?)",
                    new Object[]{message.getUserName(), message.getInOrOut(), message.getValue(), message.getTime(), message.getOther(), message.getKind()});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    //清空数据库
    public void truncate(String username) throws Exception{
        db.beginTransaction();
        try {
            db.execSQL("delete from message where username=?",
                    new Object[]{username});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void restore(String username, List<Message> list) throws Exception {
        db.beginTransaction();
        try {
            db.execSQL("delete from message where username=?",
                    new Object[]{username});
            for (int i = 0; i < list.size(); i++) {
                Message message = list.get(i);
                db.execSQL("insert into message values(?,?,?,?,?,?)",
                        new Object[]{message.getUserName(), message.getInOrOut(), message.getValue(), message.getTime(), message.getOther(), message.getKind()});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    //获取全部收支信息
    public List<Message> getAllMessage() throws Exception{
        ArrayList<Message> messages = new ArrayList<>();
        Cursor c = db.rawQuery("select * from message", null);
        while (c.moveToNext()){
            Message message = new Message();
            message.setUserName(c.getString(c.getColumnIndex("username")));
            message.setInOrOut(c.getInt(c.getColumnIndex("inorout")));
            message.setValue(c.getFloat(c.getColumnIndex("value")));
            message.setTime(c.getLong(c.getColumnIndex("time")));
            message.setOther(c.getString(c.getColumnIndex("other")));
            message.setKind(c.getString(c.getColumnIndex("kind")));
            messages.add(message);
        }
        c.close();
        return messages;
    }

    //获取某用户全部信息
    public List<Message> getUserMessage(String username){
        ArrayList<Message> messages = new ArrayList<>();
        Cursor c = db.rawQuery("select * from message where username='" + username + "'", null);
        while (c.moveToNext()){
            Message message = new Message();
            message.setUserName(c.getString(c.getColumnIndex("username")));
            message.setInOrOut(c.getInt(c.getColumnIndex("inorout")));
            message.setValue(c.getFloat(c.getColumnIndex("value")));
            message.setTime(c.getLong(c.getColumnIndex("time")));
            message.setOther(c.getString(c.getColumnIndex("other")));
            message.setKind(c.getString(c.getColumnIndex("kind")));
            messages.add(message);
        }
        c.close();
        return messages;
    }

    //获取某用户某年某月的收支信息
    public List<Message> getMonthMessage(String username, int year, int month) throws ParseException {
        ArrayList<Message> messages = new ArrayList<>();
        long firstDayOfMonth = TimeUtil.firstDayOfMonth(year, month);
        long lastDayOfMonth = TimeUtil.lastDayOfMonth(year, month);
        Cursor c = db.rawQuery("select * from message where username='" + username + "' and time>=" + firstDayOfMonth + " and time<=" + lastDayOfMonth, null);
        while (c.moveToNext()){
            Message message = new Message();
            message.setUserName(c.getString(c.getColumnIndex("username")));
            message.setInOrOut(c.getInt(c.getColumnIndex("inorout")));
            message.setValue(c.getFloat(c.getColumnIndex("value")));
            message.setTime(c.getLong(c.getColumnIndex("time")));
            message.setOther(c.getString(c.getColumnIndex("other")));
            message.setKind(c.getString(c.getColumnIndex("kind")));
            messages.add(message);
        }
        c.close();
        return messages;
    }

    //获取day1到day2的收支信息
    public List<Message> getMessage(int year1, int month1, int day1, int year2, int month2, int day2, String username) throws ParseException {
        ArrayList<Message> messages = new ArrayList<>();
        long firstDay = TimeUtil.getDayMillStart(year1, month1, day1);
        long lastDay = TimeUtil.getDayMillStop(year2, month2, day2);
        Cursor c = db.rawQuery("select * from message where username='" + username + "' and time>=" + firstDay + " and time<=" + lastDay, null);
        while (c.moveToNext()){
            Message message = new Message();
            message.setUserName(c.getString(c.getColumnIndex("username")));
            message.setInOrOut(c.getInt(c.getColumnIndex("inorout")));
            message.setValue(c.getFloat(c.getColumnIndex("value")));
            message.setTime(c.getLong(c.getColumnIndex("time")));
            message.setOther(c.getString(c.getColumnIndex("other")));
            message.setKind(c.getString(c.getColumnIndex("kind")));
            messages.add(message);
        }
        c.close();
        return messages;
    }

    public void closeDB(){
        db.close();
    }



}