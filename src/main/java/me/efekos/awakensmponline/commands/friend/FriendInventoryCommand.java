package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.FriendCommand;
import me.efekos.awakensmponline.commands.args.FriendArgument;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.menu.FriendInventoryMenu;
import me.efekos.simpler.commands.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.config.YamlConfig;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.menu.MenuManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "inventory", description = "See the inventory of your player", permission = "awakensmp.friend.inventory")
public class FriendInventoryCommand extends SubCommand {
    public FriendInventoryCommand(@NotNull String name) {
        super(name);
    }

    public FriendInventoryCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return FriendCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new FriendArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        me.efekos.awakensmponline.data.Friend dataFriend = data.getFriend(args[0]);
        YamlConfig lang = Main.LANG;
        if (dataFriend == null) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.not-friend", "&b%player% &cis not your friend.").replace("%player%", args[0])));
            return;
        }
        OfflinePlayer offlineFriend = Bukkit.getOfflinePlayer(dataFriend.getPlayerId());
        if (!offlineFriend.isOnline()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.not-online", "&b%player% &cis not online.").replace("%player%", offlineFriend.getName())));
            return;
        }
        if (!dataFriend.getModifications().isInventoryAllowed()) {
            player.sendMessage(TranslateManager.translateColors(lang.getString("commands.friend.not-allowed", "&b%player% &cdid not allow you to do that.").replace("%player%", offlineFriend.getName())));
            return;
        }
        // we are allowed to and can see offlineFriend's inventory.
        Player friend = offlineFriend.getPlayer();
        PlayerInventory friendInventory = friend.getInventory();
        MenuData menuData = MenuManager.getMenuData(player);
        menuData.set("invToOpen", friendInventory);
        MenuManager.updateMenuData(player, menuData);

        MenuManager.Open(player, FriendInventoryMenu.class);
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
