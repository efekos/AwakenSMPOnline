package me.efekos.awakensmponline;

import me.efekos.awakensmponline.commands.*;
import me.efekos.awakensmponline.config.GameConfig;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.events.OnPlayer;
import me.efekos.awakensmponline.exceptions.InvalidRecipeException;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
import me.efekos.awakensmponline.recipe.RecipeManager;
import me.efekos.awakensmponline.utils.Logger;
import me.efekos.simpler.Metrics;
import me.efekos.simpler.commands.CommandManager;
import me.efekos.simpler.items.ItemManager;
import me.efekos.simpler.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class AwakenSMPOnline extends JavaPlugin {

    private static AwakenSMPOnline plugin;

    public static AwakenSMPOnline getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        try {
            Metrics metrics = new Metrics(this,16413);

            Logger.info("Starting plugin.");

            Logger.log("Loading config.");
            GameConfig.setup();
            LangConfig.setup();

            Logger.log("Loading data.");
            PlayerDataManager.load();
            TeamDataManager.load();
            RequestDataManager.load();

            Logger.log("Loading recipe.");
            if(GameConfig.get().getBoolean("recipe.use-default",true)){
                Bukkit.addRecipe(RecipeManager.loadDefaultRecipe(this));
            } else {
                try {
                    ShapedRecipe recipe = RecipeManager.loadConfigRecipe(this);
                    Bukkit.addRecipe(recipe);
                } catch (InvalidRecipeException e){
                    Logger.error("An error occurred while loading the revive head recipe from config: "+e.getMessage());
                    Logger.error("Loading default recipe.");
                    Bukkit.addRecipe(RecipeManager.loadDefaultRecipe(this));
                }
            }

            Logger.log("Loading commands.");
            try {
                CommandManager.registerCoreCommand(this, AwakenSMP.class);
                if(GameConfig.get().getBoolean("features.friend",true))
                CommandManager.registerCoreCommand(this, Friend.class);
                if (GameConfig.get().getBoolean("features.team",true))
                CommandManager.registerCoreCommand(this, Team.class);
                CommandManager.registerBaseCommand(this, Particles.class);
            }catch (Exception e){
                e.printStackTrace();
                Logger.error("Experienced an error while trying to load commands.");
                Logger.error("Stopping plugin.");
                Bukkit.getPluginManager().disablePlugin(this);
            }

            Logger.log("Loading events.");

            getServer().getPluginManager().registerEvents(new OnPlayer(),this);
            MenuManager.setPlugin(this);

            Logger.log("Loading items.");

            ItemManager.setPlugin(this);

            Logger.success("Successfully started!");
        } catch (Exception e){
            e.printStackTrace();
            Logger.error("Experienced an unknown error while trying to enable plugin.");
            Logger.error("Stopping plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Logger.info("Stopping plugin.");

        Logger.log("Saving data.");
        //PlayerDataManager.save();
        //RequestDataManager.save();
        //TeamDataManager.save();

        Logger.success("Successfully stopped!");
    }
}
