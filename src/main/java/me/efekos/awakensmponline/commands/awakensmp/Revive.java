package me.efekos.awakensmponline.commands.awakensmp;

import me.efekos.awakensmponline.commands.AwakenSMP;
import me.efekos.awakensmponline.commands.args.DeadPlayerArgument;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.utils.ParticleManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "revive",description = "Revives a player",permission = "awakensmp.cmd.revive")
public class Revive extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return AwakenSMP.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new DeadPlayerArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = PlayerDataManager.get(args[0]);
        if(data==null) {
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.not-player")
                    .replace("%player%",args[0])
            ));
            return;
        }

        if(data.isAlive()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.not-dead")
                    .replace("%player%",args[0])));
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(data.getUuid());
        if(!offlinePlayer.isOnline()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.not-online").replace("%player%", offlinePlayer.getName())));
            return;
        }
        Player p = (Player )offlinePlayer;

        p.setGameMode(GameMode.SURVIVAL);
        p.teleport(player.getLocation());
        p.removePotionEffect(PotionEffectType.BLINDNESS);

        ParticleManager.spawnParticle(data.getParticleOptions(),p);

        data.setRevived(true);
        data.setAlive(true);

        PlayerDataManager.update(data.getUuid(),data);

        p.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.hey").replace("%player%",player.getName())));
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("reviving.done")
                .replace("%player%",args[0])));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS,100,1);
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        onPlayerUse((Player) sender,args);
    }

    public Revive(@NotNull String name) {
        super(name);
    }

    public Revive(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
