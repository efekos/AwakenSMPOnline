package me.efekos.awakensmponline.files;

import com.google.gson.Gson;
import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.*;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DeadPlayersJSON {

    private static ArrayList<PlayerData> datas = new ArrayList<>();

    public static void createData(Player p){

        PlayerData note = new PlayerData(p.getUniqueId(),p.getName(),false,false, false, new CustomParticleOptions(ParticleColor.WHITE, ParticleType.POP),p.getLocation(),new ArrayList<>());
        datas.add(note);

        saveData();
    }

    public static void fetchData(Player p){
        if(getDataFromUniqueId(p.getUniqueId().toString()) == null) createData(p);
    }

    public static PlayerData getDataFromUniqueId(String id){
        for (PlayerData data : datas) {
            if (data.getPlayerUniqueId().toString().equals(id)) {
                return data;
            }
        }
        return null;
    }

    public static PlayerData getDataFromUniqueId(UUID id){
        for (PlayerData data : datas) {
            if (data.getPlayerUniqueId().equals(id)) {
                return data;
            }
        }
        return null;
    }

    public static PlayerData getDataFromName(String playerName){
        for (PlayerData data : datas) {
            if (data.getPlayerName().equals(playerName)) {
                return data;
            }
        }
        return null;
    }

    public static PlayerData updateData(UUID UUID, PlayerData newData){
        for (PlayerData data : datas) {
            if (data.getPlayerUniqueId().equals(UUID)) {
                data.setIsDead(data.isDead());
                data.setPlayerName(data.getPlayerName());
                data.setPlayerUniqueId(data.getPlayerUniqueId());
                data.setIsOfflineReviving(data.isOfflineReviving());
                data.setFriends(newData.getFriends());
                data.setDeadLocation(newData.getDeadLocation());
                data.setInstantOfflineReviving(data.isInstantOfflineReviving());
                data.setParticleOptions(newData.getParticleOptions());
            }
        }
        saveData();
        return newData;
    }

    public static PlayerData updateData(String playerName, PlayerData newData){
        for (PlayerData data : datas) {
            if (data.getPlayerName().equals(playerName)) {
                data.setIsDead(data.isDead());
                data.setPlayerName(data.getPlayerName());
                data.setPlayerUniqueId(data.getPlayerUniqueId());
                data.setIsOfflineReviving(data.isOfflineReviving());
                data.setFriends(newData.getFriends());
                data.setDeadLocation(newData.getDeadLocation());
                data.setInstantOfflineReviving(data.isInstantOfflineReviving());
                data.setParticleOptions(newData.getParticleOptions());
            }
        }
        saveData();
        return newData;
    }

    public static List<PlayerData> getAllData(){
        return datas;
    }

    public static void loadData() {

        Gson gson = new Gson();
        File file = new File(AwakenSMPOnline.getPlugin().getDataFolder().getAbsolutePath() + "/data/PlayerData.json");
        if (file.exists()){
            try {
                Reader reader = new FileReader(file);
                PlayerData[] n = gson.fromJson(reader, PlayerData[].class);
                datas = new ArrayList<>(Arrays.asList(n));
            } catch (IOException e){e.getCause();}
        }

    }

    public static void saveData() {

        Gson gson = new Gson();
        File file = new File(AwakenSMPOnline.getPlugin().getDataFolder().getAbsolutePath() + "/data/PlayerData.json");
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
