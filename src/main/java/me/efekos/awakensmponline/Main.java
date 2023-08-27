package me.efekos.awakensmponline;

import me.efekos.awakensmponline.commands.AwakenSMP;
import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.Request;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.awakensmponline.events.OnPlayer;
import me.efekos.awakensmponline.exceptions.InvalidRecipeException;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
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
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

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
        //PLAYER_DATA = new JSONDataManager<>("data\\PlayerData.json",this);
        //TEAM_DATA = new JSONDataManager<>("data\\TeamData.json",this);
        //REQUEST_DATA = new JSONDataManager<>("data\\RequestData.json",this);
        try {
            // Setup metrics
            Metrics metrics = new Metrics(this,16413);

            Logger.info("Starting plugin.");

            Logger.log("Loading config.");

            // Load configs and clone them if a file for them doesn't exist
            GAME.setup();
            LANG.setup();


            Logger.log("Loading data.");

            // Loading every data
            PlayerDataManager.load();
            TeamDataManager.load();
            RequestDataManager.load();

            Logger.log("Loading recipe.");
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

            Logger.log("Loading commands.");
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

            Logger.log("Loading events.");

            getServer().getPluginManager().registerEvents(new OnPlayer(),this);
            MenuManager.setPlugin(this); // setup menu manager

            Logger.log("Loading items.");

            ItemManager.setPlugin(this); // setup custom items

            Logger.success("Successfully started!");

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
        Logger.info("Stopping plugin.");

        Logger.log("Saving data.");
        PlayerDataManager.save();
        RequestDataManager.save();
        TeamDataManager.save();
        // datalar dosyadan değişse bile bi bok olmaz

        Logger.success("Successfully stopped!");
    }
}
