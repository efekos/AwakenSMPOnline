package me.efekos.awakensmponline.commands;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.OfflineHeadsJSON;
import me.efekos.awakensmponline.utils.Particles;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class revive extends SubCommand {
    public revive() {
        super();
    }

    /**
     * @return The name of the subcommand
     */
    @Override
    public String getName() {
        return "revive";
    }

    /**
     * @return The aliases that can be used for this command. Can be null
     */
    @Override
    public List<String> getAliases() {
        return null;
    }

    /**
     * @return A description of what the subcommand does to be displayed
     */
    @Override
    public String getDescription() {
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.main.desc-rvv");
    }

    /**
     * @return An example of how to use the subcommand
     */
    @Override
    public String getSyntax() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String p = "messages.command-args.";
        return "/awakensmp revive " + cf.getString(p + "player");
    }

    /**
     * @param sender The thing that ran the command
     * @param args   The args passed into the command when run
     */
    @Override
    public void perform(CommandSender sender, String[] args) {
        Configuration cf = AwakenSMPOnline.getPlugin().getConfig();
        if (sender instanceof Player) {
            Player p = (Player) sender;
            PlayerDataManager.fetch(p);

            if(!p.hasPermission("awakensmp.cmd.revive")){
                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.no-perm"))));
                return;
            }


            if (args.length != 2) {
                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.revive.no-arg"))));
                return;
            }


            if (PlayerDataManager.getDataFromName(args[1]) == null) {

                if (args[1].equals("all")) {
                    AtomicReference<Boolean> isSome = new AtomicReference<>(false);
                    PlayerDataManager.getAllData().forEach(playerData -> {
                        if(playerData.isDead()) isSome.set(true);
                    });
                    if(isSome.get()){
                        if(!cf.getBoolean("offline-revives")) {
                            AtomicReference<Integer> offlineCount = new AtomicReference<>(0);
                            AtomicReference<Integer> deadCount = new AtomicReference<>(0);
                            PlayerDataManager.getAllData().stream().filter(PlayerData::isDead).forEach(playerData -> {
                                OfflinePlayer deadP = p.getServer().getOfflinePlayer(playerData.getPlayerUniqueId());
                                if (!deadP.isOnline()) offlineCount.set(offlineCount.get() + 1);
                                deadCount.set(deadCount.get() + 1);
                            });

                            if(deadCount.get().equals(offlineCount.get())){
                                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.revive.all.all-offline"))));
                                return;
                            }

                            if(offlineCount.get() > 0){
                                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(Objects.requireNonNull(cf.getString("messages.commands.revive.all.offline-players")).replace("%count%",offlineCount.toString()))));
                            }
                        }

                        PlayerDataManager.getAllData().forEach(playerData -> {
                            if (playerData.isDead()) {
                                OfflinePlayer deadP = p.getServer().getOfflinePlayer(playerData.getPlayerUniqueId());
                                if (deadP.isOnline()) {
                                    Player deadPlayer = (Player) deadP;
                                    deadPlayer.teleport(deadPlayer.getWorld().getSpawnLocation());
                                    deadPlayer.setGameMode(GameMode.SURVIVAL);
                                    if (cf.getBoolean("revive-particles")) {
                                        Particles.Spawn(deadPlayer);
                                    }

                                    playerData.setIsDead(false);
                                    PlayerDataManager.update(playerData.getPlayerUniqueId(), playerData);

                                    if (cf.getBoolean("announce.revived")) {
                                        Bukkit.broadcastMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.revived-announcement"))
                                                .replace("%reviver%", p.getName())
                                                .replace("%revived%", deadPlayer.getName())));
                                    }
                                } else {
                                    if (cf.getBoolean("offline-revives")) {
                                        playerData.setInstantOfflineReviving(true);
                                        playerData.setIsDead(false);
                                        PlayerDataManager.update(playerData.getPlayerUniqueId(), playerData);
                                    }
                                }
                            }
                        });
                        p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.revive.all.success"))));
                    } else {
                        p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.revive.all.no-dead"))));
                    }
                } else {
                    p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.revive.wrong-arg"))));
                }
                return;
            }

            PlayerData deadPlayerData = PlayerDataManager.getDataFromName(args[1]);

            assert deadPlayerData != null;
            if (!deadPlayerData.isDead()) {
                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.revive.not-dead"))));
                return;
            }

            OfflinePlayer deadP = p.getServer().getOfflinePlayer(deadPlayerData.getPlayerUniqueId());
            if (deadP.isOnline()) {
                deadPlayerData.setIsDead(false);
                PlayerDataManager.update(deadPlayerData.getPlayerUniqueId(), deadPlayerData);
                Player deadPlayer = deadP.getPlayer();
                Objects.requireNonNull(deadPlayer).setGameMode(GameMode.SURVIVAL);
                deadPlayer.teleport(Objects.requireNonNull(PlayerDataManager.getDataFromUniqueId(deadPlayer.getUniqueId())).getDeadLocation());
                if (cf.getBoolean("revive-particles")) {
                   Particles.Spawn(deadPlayer);
                }

                p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.commands.revive.success"))));
                if (cf.getBoolean("announce.revived")) {
                    Bukkit.broadcastMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.revived-announcement"))
                            .replace("%reviver%", p.getName())
                            .replace("%revived%", deadPlayer.getName())));
                }
            } else { //if deadP is online
                if (cf.getBoolean("offline-revives")) {
                    Location locationMain = p.getLocation();
                    locationMain.setX(locationMain.getBlockX());
                    locationMain.setY(locationMain.getBlockY());
                    locationMain.setZ(locationMain.getBlockZ());

                    ArmorStand hologram = (ArmorStand) p.getWorld().spawnEntity(locationMain.add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
                    hologram.setGravity(false);
                    hologram.setVisible(false);
                    hologram.setCustomName(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.offline-hologram"))
                            .replace("%player%", Objects.requireNonNull(deadP.getName()))
                    ));
                    hologram.setCustomNameVisible(true);

                    Location locationBreak = p.getLocation();
                    locationBreak.add(0, -1, 0);
                    OfflineHeadsJSON.createData(deadP.getUniqueId(), locationMain, hologram.getUniqueId(), locationBreak.getBlock().getType());
                    locationBreak.getBlock().setType(Material.BEDROCK);

                    deadPlayerData.setIsDead(false);
                    deadPlayerData.setIsOfflineReviving(true);
                    PlayerDataManager.update(deadPlayerData.getPlayerUniqueId(), deadPlayerData);
                } else { //if offline revives is enabled
                    p.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.not-online"))));
                }
            }
        } else { //if sender is a player
            sender.sendMessage(ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString("messages.not-player"))));
        }
    }

    /**
     * @param player The player who ran the command
     * @param args   The args passed into the command when run
     * @return A list of arguments to be suggested for autocomplete
     */
    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        List<String> list = new ArrayList<>();
        for (PlayerData playerData : PlayerDataManager.getAllData()) {
            if (playerData.isDead()) list.add(playerData.getPlayerName());
        }
        if(PlayerDataManager.getAllData() != null) list.add("all");
        return list;
    }
}
