package me.efekos.awakensmponline.files;

import com.google.gson.Gson;
import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.OfflineHead;
import me.efekos.awakensmponline.utils.Logger;
import org.bukkit.Location;
import org.bukkit.Material;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class OfflineHeadsJSON {

    private static ArrayList<OfflineHead> datas = new ArrayList<>();

    public static OfflineHead createData(UUID id, Location loc, UUID hologramId, Material bedrockBLock){

        OfflineHead data = new OfflineHead(id,loc,hologramId,bedrockBLock);
        datas.add(data);

        saveData();
        return data;
    }

    public static OfflineHead getData(UUID id){
        for (OfflineHead data : datas) {
            if (data.getRevivingPlayer().equals(id)) {
                return data;
            }
        }
        return null;
    }

    public static Boolean deleteData(UUID id){
        for (OfflineHead data : datas) {
            if (data.getRevivingPlayer().equals(id)) {
                datas.remove(data);
                saveData();
                return true;
            }
        }
        return false;
    }

    public static OfflineHead updateData(UUID uuid, OfflineHead newData){
        for (OfflineHead data : datas) {
            if (data.getRevivingPlayer().equals(uuid)) {
                data.setLocation(newData.getLocation());
                data.setRevivingPlayer(newData.getRevivingPlayer());
            }
        }
        saveData();
        return newData;
    }

    public static List<OfflineHead> getAllData(){
        return datas;
    }

    public static void loadData() {

        Gson gson = new Gson();
        File file = new File(AwakenSMPOnline.getPlugin().getDataFolder().getAbsolutePath() + "/data/OfflineHeads.json");
        if (file.exists()){
            try {
                Reader reader = new FileReader(file);
                OfflineHead[] n = gson.fromJson(reader, OfflineHead[].class);
                datas = new ArrayList<>(Arrays.asList(n));
            } catch (IOException e){
                e.getCause();
            }
        }

    }

    public static void saveData() {

        Gson gson = new Gson();
        File file = new File(AwakenSMPOnline.getPlugin().getDataFolder().getAbsolutePath() + "/data/OfflineHeads.json");
        file.getParentFile().mkdir();
        try {
            file.createNewFile();
            Writer writer = new FileWriter(file, false);
            gson.toJson(datas, writer);
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.getCause();
        }

    }
}