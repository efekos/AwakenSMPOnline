package me.efekos.awakensmponline.files;

import com.google.gson.Gson;
import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.data.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class PlayerDataManager {
    private static ArrayList<PlayerData> datas = new ArrayList<>();

    public static void create(PlayerData newData){
        datas.add(newData);
        save();
    }

    public static PlayerData get(UUID id){
        for (PlayerData data:datas){
            if(data.getId().equals(id)) return data;
        }
        return null;
    }

    public static PlayerData fetch(Player player){
        return fetch(player.getUniqueId());
    }

    public static PlayerData get(OfflinePlayer player){
        return get(player.getName());
    }

    public static PlayerData fetch(UUID id){
        if(get(id) == null){
            Player p = Bukkit.getPlayer(id);

            assert p != null;
            create(new PlayerData(id,p.getName(),true,true,new ParticleOptions(ParticleType.POP, ParticleColor.WHITE)));
        }
        return get(id);
    }

    public static PlayerData get(String name){
        for(PlayerData data:datas){
            if(data.getName().equals(name))return data;
        }
        return null;
    }

    public static void makeFriends(@NotNull Player p1, @NotNull Player p2){
       makeFriends(p1.getUniqueId(),p2.getUniqueId());
    }
    public static void makeFriends(@NotNull OfflinePlayer p1, @NotNull Player p2){
        makeFriends(p1.getUniqueId(),p2.getUniqueId());
    }
    public static void makeFriends(@NotNull Player p1, @NotNull OfflinePlayer p2){
        makeFriends(p1.getUniqueId(),p2.getUniqueId());
    }
    public static void makeFriends(@NotNull OfflinePlayer p1, @NotNull OfflinePlayer p2){
        makeFriends(p1.getUniqueId(),p2.getUniqueId());
    }

    public static void makeFriends(UUID p1u,UUID p2u){
        PlayerData p1Data = fetch(p1u);
        PlayerData p2Data = fetch(p2u);

        Friend p1tp2Friend = new Friend(p1Data.getDefaultFriendModifications(),p2u,p2Data.getName());
        Friend p2tp1Friend = new Friend(p2Data.getDefaultFriendModifications(),p1u,p1Data.getName());

        p1Data.addFriend(p1tp2Friend);
        p2Data.addFriend(p2tp1Friend);

        update(p1Data.getId(),p1Data);
        update(p2Data.getId(),p2Data);
    }

    public static PlayerData fetch(String name){
        if(get(name)==null){
            Player p =Bukkit.getPlayer(name);

            assert p!=null;
            create(new PlayerData(p.getUniqueId(),name,false,false,new ParticleOptions(ParticleType.POP,ParticleColor.WHITE)));
        }
        return get(name);
    }

    public static void update(UUID id,PlayerData newData){
        for (PlayerData data:datas){
            if(data.getId().equals(id)){
                data.setAlive(newData.isAlive());
                data.setName(newData.getName());
                data.setRevived(newData.isRevived());
                data.setParticleOptions(newData.getParticleOptions());
                data.setDefaultFriendModifications(newData.getDefaultFriendModifications());
                data.setFriends(newData.getFriends());
            }
        }
        save();
    }

    public static void save(){
        Gson gson = new Gson();

        String path = Main.getInstance().getDataFolder().getAbsolutePath()+"\\data\\PlayerData.json";

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
        File file = new File(Main.getInstance().getDataFolder().getAbsolutePath()+"\\data\\PlayerData.json");
        if(file.exists()){
            try {
                Reader reader = new FileReader(file);
                PlayerData[] n = gson.fromJson(reader,PlayerData[].class);
                datas = new ArrayList<>(Arrays.asList(n));
            } catch (Exception e){
                e.getCause();
            }
        }

    }

    public static ArrayList<PlayerData> getAll() {
        return datas;
    }
}