package me.efekos.awakensmponline.commands.awakensmp;

import me.efekos.awakensmponline.commands.args.AlivePlayerArgument;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
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

@Command(name = "kill",description = "Kill someone permanently",permission = "awakensmp.kill")
public class Kill extends SubCommand {
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
        PlayerData data = PlayerDataManager.get(args[0]);
        if(data==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.kill.not-player").replace("%player%",args[0])));
            return;
        }
        OfflinePlayer offlineVictim = Bukkit.getOfflinePlayer(data.getUuid());
        if(!offlineVictim.isOnline()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.kill.not-online").replace("%player%",data.getName())));
            return;
        }
        if(!data.isAlive()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.kill.not-alive").replace("%player%",data.getName())));
            return;
        }
        Player victim = (Player) offlineVictim;
        if(victim.hasPermission("awakensmp.god")){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.kill.god-player").replace("%player%",data.getName())));
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
        victim.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.kill.force").replace("%killer%",data.getName())));
        victim.setGameMode(GameMode.SPECTATOR);

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.kill.done").replace("%PLAYER%",victim.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        onPlayerUse((Player) sender,args);
    }

    public Kill(@NotNull String name) {
        super(name);
    }

    public Kill(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}