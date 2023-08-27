package me.efekos.awakensmponline;

import me.efekos.awakensmponline.commands.AwakenSMP;
import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.data.*;
import me.efekos.awakensmponline.events.OnPlayer;
import me.efekos.awakensmponline.exceptions.InvalidRecipeException;
import me.efekos.awakensmponline.utils.Logger;
import me.efekos.awakensmponline.utils.RecipeManager;
import me.efekos.simpler.Metrics;
import me.efekos.simpler.Utilities;
import me.efekos.simpler.commands.CommandManager;
import me.efekos.simpler.config.Config;
import me.efekos.simpler.config.JSONDataManager;
import me.efekos.simpler.items.ItemManager;
import me.efekos.simpler.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getInstance() {
        return plugin;
    }

    public static Config GAME;
    public static Config LANG;

    public static JSONDataManager<PlayerData> PLAYER_DATA;
    public static JSONDataManager<TeamData> TEAM_DATA;
    public static JSONDataManager<Request> REQUEST_DATA;

    @Override
    public void onEnable() {
        plugin = this;
        GAME = new Config("config.yml",this);
        LANG = new Config("lang.yml",this);
        PLAYER_DATA = new JSONDataManager<>("data\\PlayerData.json",this);
        TEAM_DATA = new JSONDataManager<>("data\\TeamData.json",this);
        REQUEST_DATA = new JSONDataManager<>("data\\RequestData.json",this);
        try {
            // Setup metrics
            Metrics metrics = new Metrics(this,16413);

            //configs

            GAME.setup();
            LANG.setup();

            // data

            PLAYER_DATA.load(PlayerData[].class);
            TEAM_DATA.load(TeamData[].class);
            REQUEST_DATA.load(Request[].class);

            //recipe

            if(GAME.getBoolean("recipe.use-default",true)){
                // Default açıksa direk default yükle
                Bukkit.addRecipe(RecipeManager.loadDefaultRecipe(this));
            } else {
                try {
                    ShapedRecipe recipe = RecipeManager.loadConfigRecipe(this);
                    Bukkit.addRecipe(recipe);
                } catch (InvalidRecipeException e){
                    // adam mal gibi recipe verince.
                    Logger.error("An error occurred while loading the revive head recipe from config: "+e.getMessage());
                    Logger.error("Loading default recipe.");
                    Bukkit.addRecipe(RecipeManager.loadDefaultRecipe(this));
                }
            }

            // commands

            try {
                CommandManager.registerCoreCommand(this, AwakenSMP.class); // /awakensmp

                if(GAME.getBoolean("features.friend",true)) // arkadaş şeyleri açıkmı diye kontrol
                    CommandManager.registerCoreCommand(this, Friend.class); // /friend

                if (GAME.getBoolean("features.team",true)) // takım şeyleri açıkmı diye kontrol
                    CommandManager.registerCoreCommand(this, Team.class); // /team

            }catch (Exception e){
                e.printStackTrace();
                Logger.error("Experienced an error while trying to load commands.");
                Logger.error("Stopping plugin.");
                Bukkit.getPluginManager().disablePlugin(this); // hata varsa direk duruyo
            }

            // event

            getServer().getPluginManager().registerEvents(new OnPlayer(),this);
            MenuManager.setPlugin(this); // setup menu manager
            ItemManager.setPlugin(this);

            //done!!1!1!

            //updates

            Logger.info("Checking For Updates...");
            boolean upToDate = Utilities.checkUpdates(this,102573);
            if(upToDate) Logger.info("There are no updates avaliable.");
            else Logger.warn("There is a new update avaliable!");

        } catch (Exception e){ // random bişeyler olursa
            e.printStackTrace();
            Logger.error("Experienced an unexpected error while enabling plugin.");
            Logger.error("Stopping plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Logger.log("Saving data.");
        PLAYER_DATA.save();
        TEAM_DATA.save();
        REQUEST_DATA.save();
    }

    public static PlayerData fetchPlayer(UUID id){
        OfflinePlayer player = Bukkit.getOfflinePlayer(id);
        if (PLAYER_DATA.get(id)==null) PLAYER_DATA.update(id,new PlayerData(id,player.getName(),true,false,new ParticleOptions(ParticleType.POP,ParticleColor.WHITE)));
        return PLAYER_DATA.get(id);
    }

    public static PlayerData getPlayerFromName(String name){
        for (PlayerData data : PLAYER_DATA.getAll()) {
            if(data.getName().equals(name))return data;
        }
        return null;
    }

    public static void makeFriends(UUID p1u,UUID p2u){
        PlayerData p1Data = fetchPlayer(p1u);
        PlayerData p2Data = fetchPlayer(p2u);

        me.efekos.awakensmponline.data.Friend p1tp2Friend = new me.efekos.awakensmponline.data.Friend(p1Data.getDefaultFriendModifications(),p2u,p2Data.getName());
        me.efekos.awakensmponline.data.Friend p2tp1Friend = new me.efekos.awakensmponline.data.Friend(p2Data.getDefaultFriendModifications(),p1u,p1Data.getName());

        p1Data.addFriend(p1tp2Friend);
        p2Data.addFriend(p2tp1Friend);

        PLAYER_DATA.update(p1Data.getUuid(),p1Data);
        PLAYER_DATA.update(p2Data.getUuid(),p2Data);
    }
}
