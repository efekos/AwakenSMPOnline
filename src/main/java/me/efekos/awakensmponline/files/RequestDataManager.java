package me.efekos.awakensmponline.files;

import com.google.gson.Gson;
import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.data.Request;
import me.efekos.awakensmponline.data.RequestType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class RequestDataManager {
    private static ArrayList<Request> datas = new ArrayList<>();

    public static void create(Request newData){
        datas.add(newData);
        save();
    }

    public static boolean exists(UUID sender, UUID getter, RequestType type){
        for (Request data:datas){
            if(data.getSender().equals(sender))
                if (data.getGetter().equals(getter))
                    if (data.getType().equals(type))
                        return true;
        }
        return false;
    }

    public static void delete(UUID id){
        datas.removeIf(req -> req.getId().equals(id));
    }

    public static Request get(UUID id){
        for (Request data:datas){
            if(data.getId().equals(id)) return data;
        }
        return null;
    }

    public static void update(UUID id,Request newData){
        for (Request data:datas){
            if(data.getId().equals(id)){
                data.setDone(newData.isDone());
                data.setGetter(newData.getGetter());
                data.setSender(newData.getSender());
                data.setType(newData.getType());
            }
        }
        save();
    }

    public static void save(){
        Gson gson = new Gson();

        String path = Main.getInstance().getDataFolder().getAbsolutePath()+"\\data\\RequestData.json";

        File file = new File(path);
        file.getParentFile().mkdir();
        try {
            file.createNewFile();
            Writer writer = new FileWriter(file,false);
            gson.toJson(datas,writer);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void load(){

        Gson gson = new Gson();
        File file = new File(Main.getInstance().getDataFolder().getAbsolutePath()+"\\data\\RequestData.json");
        if(file.exists()){
            try {
                Reader reader = new FileReader(file);
                Request[] n = gson.fromJson(reader,Request[].class);
                datas = new ArrayList<>(Arrays.asList(n));
            } catch (Exception e){
                e.getCause();
            }
        }

    }

    public static ArrayList<Request> getAll() {
        return datas;
    }
}
