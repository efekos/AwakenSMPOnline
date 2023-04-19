package me.efekos.awakensmponline;

import me.efekos.awakensmponline.commands.*;
import me.efekos.awakensmponline.events.*;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.efekos.awakensmponline.files.OfflineHeadsJSON;
import me.efekos.awakensmponline.files.RequestsJSON;
import me.efekos.awakensmponline.utils.HeadRecipe;
import me.efekos.awakensmponline.utils.Logger;
import me.efekos.awakensmponline.utils.UpdateChecker;
import me.efekos.awakensmponline.commands.friend.add;
import me.efekos.awakensmponline.commands.friend.accept;
import me.efekos.awakensmponline.commands.friend.deny;
import me.efekos.awakensmponline.commands.friend.cancel;
import me.efekos.awakensmponline.commands.friend.requests;
import me.efekos.awakensmponline.commands.friend.info;
import me.efekos.awakensmponline.commands.friend.coords;
import me.efekos.awakensmponline.commands.friend.armor;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.CommandList;
import me.kodysimpson.simpapi.command.CommandManager;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class AwakenSMPOnline extends JavaPlugin {

    private static AwakenSMPOnline plugin;

    private void registerCommands(){
        CommandList list = (sender, subCommandList) -> {
            FileConfiguration cf = getConfig();
            String p = "messages.commands.main.";
            sender.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(p + "header"))));
            for (SubCommand subCommand : subCommandList) {
                sender.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(p + "command-format"))
                        .replace("%syntax%",subCommand.getSyntax())
                        .replace("%description%",subCommand.getDescription())
                ));
            }
            sender.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(p + "footer"))));
        };

        try {
            CommandManager.createCoreCommand(this, "awakensmp",
                    "All commands about AwakenSMPOnline",
                    "/awakensmp",
                    list,
                    reloadconfig.class,deadplayers.class,revive.class,settings.class
            );

            CommandManager.createCoreCommand(this,"smp",
                    "All commands about AwakenSMPOnline custom settings",
                    "/smp",
                    list,
                    setParticle.class);

            CommandManager.createCoreCommand(this,"friend",
                    "All command about friend system",
                    "/friend",
                    list,
                    add.class,accept.class,deny.class,cancel.class,requests.class, me.efekos.awakensmponline.commands.friend.list.class,coords.class,info.class,armor.class
            );
        } catch (NoSuchFieldException | IllegalAccessException e){
                e.getCause();
        }
    }
    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new BlockPlace(),this);
        getServer().getPluginManager().registerEvents(new Craft(),this);
        getServer().getPluginManager().registerEvents(new EntityKilled(),this);
        getServer().getPluginManager().registerEvents(new Move(),this);
        getServer().getPluginManager().registerEvents(new PlayerTeleport(),this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(),this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(),this);
    }


    public static AwakenSMPOnline getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        Metrics metrics = new Metrics(this,16413);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Logger.log("Loaded config.");

        if(getConfig().getBoolean("recipe.use-default")) Bukkit.addRecipe(HeadRecipe.getDefault(this));
        else {
                try {
                    Bukkit.addRecipe(HeadRecipe.getRecipeFromConfig(getConfig(),this));
                } catch (Exception e){
                    Logger.error("Recipe data in config is written wrong or broken. Using default recipe");
                    Bukkit.addRecipe(HeadRecipe.getDefault(this));
                }
        }

        Logger.log("Loaded recipes.");

        DeadPlayersJSON.loadData();
        OfflineHeadsJSON.loadData();
        RequestsJSON.loadData();

        Logger.log("Loaded data.");

        registerEvents();
        MenuManager.setup(getServer(),this);

        Logger.log("Loaded events.");

        registerCommands();

        Logger.log("Loaded commands.");
        Logger.log("Checking for updates");

        new UpdateChecker(this, 102573).getLatestVersion(version -> {
            if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Logger.info(getConfig().getString("messages.up-to-date"));
            } else {
                Logger.warn(getConfig().getString("messages.update-available-console"));
            }

        });

        Logger.success("Plugin Started!");

        if(this.getDescription().getVersion().startsWith("SNAPSHOT")){
            Logger.warn("This version is a snapshot. Be careful!");
        }
    }

    @Override
    public void onDisable() {
        Logger.success("Plugin Stopped!");
    }

}
