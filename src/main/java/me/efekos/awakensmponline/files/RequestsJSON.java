package me.efekos.awakensmponline.files;

import com.google.gson.Gson;
import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.*;
import me.efekos.awakensmponline.utils.Logger;
import me.efekos.awakensmponline.utils.Requests;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RequestsJSON {

    private static ArrayList<Request> datas = new ArrayList<>();

    public static Request createData(Player fromP, Player toP, RequestType type){

        Request request = new Request(fromP.getUniqueId(),toP.getUniqueId(),Requests.makeRequestId(fromP.getUniqueId(),toP.getUniqueId()),type);
        datas.add(request);

        saveData();
        return request;
    }

    public static Request getDataFromId(String id){
        for (Request data : datas) {
            if (data.getId().equals(id)) {
                return data;
            }
        }
        return null;
    }

    public static Request getDataFromEquality(Request request){
        for (Request data: datas){
            if(data.equalsWithoutId(request)){
                return data;
            }
        }
        return null;
    }

    public static Request updateData(String id, Request newData){
        for (Request data : datas) {
            if (data.getId().equals(id)) {
                data.setFrom(newData.getFrom());
                data.setTo(newData.getTo());
                data.setId(newData.getId());
                data.setType(newData.getType());
            }
        }
        saveData();
        return newData;
    }

    public static Boolean deleteData(String id){
        for (Request data : datas) {
            if (data.getId().equals(id)) {
                datas.remove(data);
                saveData();
                return true;
            }
        }
        return false;
    }

    public static List<Request> getAllData(){
        return datas;
    }

    public static void loadData() {

        Gson gson = new Gson();
        File file = new File(AwakenSMPOnline.getPlugin().getDataFolder().getAbsolutePath() + "/data/RequestData.json");
        if (file.exists()){
            try {
                Reader reader = new FileReader(file);
                Request[] n = gson.fromJson(reader, Request[].class);
                datas = new ArrayList<>(Arrays.asList(n));
            } catch (IOException e){e.getCause();}
        }

    }

    public static void saveData() {

        Gson gson = new Gson();
        File file = new File(AwakenSMPOnline.getPlugin().getDataFolder().getAbsolutePath() + "/data/RequestData.json");
        file.getParentFile().mkdir();
        try {
            file.createNewFile();
            Writer writer = new FileWriter(file, false);
            gson.toJson(datas, writer);
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.getMessage();
        }

    }
}