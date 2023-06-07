package me.efekos.awakensmponline.files;

import com.google.gson.Gson;
import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.data.TeamData;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class TeamDataManager {
    private static ArrayList<TeamData> datas = new ArrayList<>();

    public static void create(TeamData newData){
        datas.add(newData);
        save();
    }

    public static TeamData get(UUID id){
        for (TeamData data:datas){
            if(data.getId().equals(id)) return data;
        }
        return null;
    }

    public static TeamData get(String name){
        for(TeamData data:datas){
            if(data.getName().equals(name))return data;
        }
        return null;
    }

    public static void update(UUID id,TeamData newData){
        for (TeamData data:datas){
            if(data.getId().equals(id)){
                data.setName(newData.getName());
                data.setDisplayName(newData.getDisplayName());
                data.setDescription(newData.getDescription());
                data.setMembers(newData.getMembers());
            }
        }
        save();
    }

    public static void delete(UUID id){
        datas.removeIf(data -> data.getId().equals(id));
        save();
    }

    public static void save(){
        Gson gson = new Gson();
        String path = AwakenSMPOnline.getPlugin().getDataFolder().getAbsolutePath()+"\\data\\TeamData.json";

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
        File file = new File(AwakenSMPOnline.getPlugin().getDataFolder().getAbsolutePath()+"\\data\\TeamData.json");
        if(file.exists()){
            try {
                Reader reader = new FileReader(file);
                TeamData[] n = gson.fromJson(reader,TeamData[].class);
                datas = new ArrayList<>(Arrays.asList(n));
            } catch (Exception e){
                e.getCause();
            }
        }

    }

    public static ArrayList<TeamData> getAll() {
        return datas;
    }
}
