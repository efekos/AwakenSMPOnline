package me.efekos.awakensmponline.commands.awakensmp;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.AwakenSMPCommand;
import me.efekos.awakensmponline.commands.args.DeadPlayerArgument;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.utils.ParticleManager;
import me.efekos.simpler.commands.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "revive", description = "Revives a player", permission = "awakensmp.revive")
public class AwakenReviveCommand extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return AwakenSMPCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new DeadPlayerArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.getPlayerFromName(args[0]);
        if (data == null) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-player", "&cThere is no one called &b%player%&c.")
                    .replace("%player%", args[0])
            ));
            return;
        }

        if (data.isAlive()) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-dead", "&b%player% &cis not dead.")
                    .replace("%player%", args[0])));
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(data.getUuid());
        if (!offlinePlayer.isOnline()) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-online", "&b%player% &cis not online.").replace("%player%", offlinePlayer.getName())));
            return;
        }
        Player p = (Player) offlinePlayer;

        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(player.getLocation());
        p.removePotionEffect(PotionEffectType.BLINDNESS);

        ParticleManager.spawnParticle(data.getParticleOptions(), p);

        data.setRevived(true);
        data.setAlive(true);

        Main.PLAYER_DATA.update(data.getUuid(), data);

        p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.hey", "&b%player% &arevived you!").replace("%player%", player.getName())));
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.done", "&aSuccessfully revived &b%player%&a!")
                .replace("%player%", args[0])));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 100, 1);
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        PlayerData data = Main.getPlayerFromName(args[0]);
        if (data == null) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-player", "&cThere is no one called &b%player%&c.")
                    .replace("%player%", args[0])
            ));
            return;
        }

        if (data.isAlive()) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-dead", "&b%player% &cis not dead.")
                    .replace("%player%", args[0])));
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(data.getUuid());
        if (!offlinePlayer.isOnline()) {
            sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.not-online", "&b%player% &cis not online.").replace("%player%", offlinePlayer.getName())));
            return;
        }
        Player p = (Player) offlinePlayer;

        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(p.getWorld().getSpawnLocation());
        p.removePotionEffect(PotionEffectType.BLINDNESS);

        ParticleManager.spawnParticle(data.getParticleOptions(), p);

        data.setRevived(true);
        data.setAlive(true);

        Main.PLAYER_DATA.update(data.getUuid(), data);

        p.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.hey-console", "&bThe Server Console &arevived you!")));
        sender.sendMessage(TranslateManager.translateColors(Main.LANG.getString("reviving.done", "&aSuccessfully revived &b%player%&a!")
                .replace("%player%", args[0])));
    }

    public AwakenReviveCommand(@NotNull String name) {
        super(name);
    }

    public AwakenReviveCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
