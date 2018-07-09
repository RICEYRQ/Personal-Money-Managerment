package com.example.riceyrq.personalmoneymanagerment.util;

import android.content.Context;
import android.util.Log;

import com.example.riceyrq.personalmoneymanagerment.dataBase.DBManager;
import com.example.riceyrq.personalmoneymanagerment.define.Data;
import com.example.riceyrq.personalmoneymanagerment.define.Message;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    public static int FILENOTFOUND = 0;
    public static int FILEWRITEERROR = 1;
    public static int FILEREADERROR = 2;
    public static int FILEWRITEOK = 3;
    public static int FILEREADOK = 4;
    public static int backupDatabase(Context context, DBManager dbManager, String username) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(username, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            return FILENOTFOUND;
        }
        List<Message> list = dbManager.getUserMessage(username);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Message message = list.get(i);
            stringBuffer.append(message.getInOrOut());
            stringBuffer.append(Data.smallSplit);
            stringBuffer.append(message.getValue());
            stringBuffer.append(Data.smallSplit);
            stringBuffer.append(message.getTime());
            stringBuffer.append(Data.smallSplit);
            stringBuffer.append(message.getOther());
            stringBuffer.append(Data.smallSplit);
            stringBuffer.append(message.getKind());
            if (i != list.size() - 1) {
                stringBuffer.append(Data.bigSplit);
            }
        }
        byte[] bytes = stringBuffer.toString().getBytes();
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        return FILEWRITEOK;
    }

    public static int restoreDatabase(Context context, DBManager dbManager, String username) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = context.openFileInput(username);
        } catch (FileNotFoundException e) {
            Log.e("fuck", e.toString());
            return FILENOTFOUND;
        }
        try {
            int length = fileInputStream.available();
            byte[] bytes = new byte[length];
            fileInputStream.read(bytes);
            String allStr = new String(bytes, "UTF-8");
            String[] oneMes = allStr.split(Data.bigSplit);
            List<Message> list = new ArrayList<>();
            for (int i = 0; i < oneMes.length; i++) {
                String[] o = oneMes[i].split(Data.smallSplit);
                if (oneMes[i].equals("") || o.length != 5){
                    continue;
                } else {
                    Message message = new Message();
                    message.setUserName(username);
                    message.setInOrOut(Integer.valueOf(o[0]));
                    message.setValue(Float.valueOf(o[1]));
                    message.setTime(Long.valueOf(o[2]));
                    message.setOther(o[3]);
                    message.setKind(o[4]);
                    list.add(message);
                }
            }
            dbManager.restore(username, list);
        } catch (IOException e) {
            Log.e("fuck", e.toString());
            return FILEREADERROR;
        } catch (Exception e) {
            Log.e("fuck", e.toString());
            return FILEWRITEERROR;
        }
        return FILEREADOK;
    }


}