package me.efekos.awakensmponline.files;

import com.google.gson.Gson;
import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;

public class PlayerDataManager {

    private static ArrayList<PlayerData> datas = new ArrayList<>();

    private static void create(Player p){

        PlayerData note = new PlayerData(p.getUniqueId(),p.getName(),false,false, false, new CustomParticleOptions(ParticleColor.WHITE, ParticleType.POP),p.getLocation(),new ArrayList<>());
        datas.add(note);

        save();
    }

    public static PlayerData fetch(Player p){
        //find data
        for (PlayerData data : datas) {
            if (data.getPlayerUniqueId().equals(p.getUniqueId())) {
                return data;
            }
        }
        //if no data, create it and find it again
        create(p);
        return fetch(p);
    }

    public static PlayerData fetch(UUID id){
        for(PlayerData data:datas){
            if(data.getPlayerUniqueId().equals(id)) return data;
        }
        if(Bukkit.getPlayer(id) != null){
            create(Objects.requireNonNull(Bukkit.getPlayer(id)));
            return fetch(id);
        }
        return null;
    }


    public static PlayerData fetch(String playerName){
        for(PlayerData data:datas){
            if(data.getPlayerName().equals(playerName)) return data;
        }
        if(Bukkit.getPlayer(playerName) != null){
            create(Objects.requireNonNull(Bukkit.getPlayer(playerName)));
            return fetch(playerName);
        }
        return null;
    }

    public static PlayerData update(UUID UUID, PlayerData newData){
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
        save();
        return newData;
    }

    public static PlayerData update(Player p, PlayerData newData){
        for (PlayerData data : datas) {
            if (data.getPlayerUniqueId().equals(p.getUniqueId())) {
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
        save();
        return newData;
    }

    public static PlayerData update(String playerName, PlayerData newData){
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
        save();
        return newData;
    }

    public static List<PlayerData> getAllData(){
        return datas;
    }

    public static void load() {

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

    public static void save() {

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
