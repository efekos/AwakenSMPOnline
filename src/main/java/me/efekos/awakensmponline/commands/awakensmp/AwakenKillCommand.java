package me.efekos.awakensmponline.commands.awakensmp;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.args.AlivePlayerArgument;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.config.YamlConfig;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "kill", description = "Kill someone permanently", permission = "awakensmp.kill-command")
public class AwakenKillCommand extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return null;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new AlivePlayerArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.getPlayerFromName(args[0]);
        YamlConfig lang = Main.LANG;


        if (data == null) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.not-player", "&cThere is no one called &b%player%&c.").replace("%player%", args[0])));
            return;
        }
        OfflinePlayer offlineVictim = Bukkit.getOfflinePlayer(data.getUuid());
        if (!offlineVictim.isOnline()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.not-online", "&b%player% &cis not online.").replace("%player%", data.getName())));
            return;
        }
        if (!data.isAlive()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.not-alive", "&b%player% &cis dead already.").replace("%player%", data.getName())));
            return;
        }
        Player victim = (Player) offlineVictim;
        if (victim.hasPermission("awakensmp.god")) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.god-player", "&b%player% &cis a god, meaning there is no way to kill him.").replace("%player%", data.getName())));
            return;
        }
        // data exists, belongs to an online player, and that player can be killed.

        data.setAlive(false);
        data.setRevived(false);

        //add skull to his drops
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(victim);
        meta.setDisplayName(victim.getName());
        skull.setItemMeta(meta);

        victim.getInventory().addItem(skull);

        victim.setHealth(0);
        victim.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.force", "&b%killer% &cKilled you by force!").replace("%killer%", data.getName())));
        victim.setGameMode(GameMode.SPECTATOR);

        player.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.done", "&aSuccessfully killed &b%player%!").replace("%PLAYER%", victim.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        PlayerData data = Main.getPlayerFromName(args[0]);

        YamlConfig lang = Main.LANG;

        if (data == null) {
            sender.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.not-player", "&cThere is no one called &b%player%&c.").replace("%player%", args[0])));
            return;
        }
        OfflinePlayer offlineVictim = Bukkit.getOfflinePlayer(data.getUuid());
        if (!offlineVictim.isOnline()) {
            sender.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.not-online", "&b%player% &cis not online.").replace("%player%", data.getName())));
            return;
        }
        if (!data.isAlive()) {
            sender.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.not-alive", "&b%player% &cis dead already.").replace("%player%", data.getName())));
            return;
        }
        Player victim = (Player) offlineVictim;
        if (victim.hasPermission("awakensmp.god")) {
            sender.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.god-player", "&b%player% &cis a god, meaning there is no way to kill him.").replace("%player%", data.getName())));
            return;
        }
        // data exists, belongs to an online player, and that player can be killed.

        data.setAlive(false);
        data.setRevived(false);

        //add skull to his drops
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(victim);
        meta.setDisplayName(victim.getName());
        skull.setItemMeta(meta);

        victim.getInventory().addItem(skull);

        victim.setHealth(0);
        victim.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.force", "&b%killer% &cKilled you by force!").replace("%killer%", data.getName())));
        victim.setGameMode(GameMode.SPECTATOR);

        sender.sendMessage(TranslateManager.translateColors(lang.getString("commands.awakensmp.kill.done", "&aSuccessfully killed &b%player%!").replace("%player%", victim.getName())));
    }

    public AwakenKillCommand(@NotNull String name) {
        super(name);
    }

    public AwakenKillCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}