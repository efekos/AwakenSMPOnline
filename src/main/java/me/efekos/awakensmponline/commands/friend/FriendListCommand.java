package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.FriendCommand;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.utils.ButtonManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Command(name = "list", description = "See a list of your friends!", permission = "awakensmp.friend.list")
public class FriendListCommand extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return FriendCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.list.header", "&4--------&cYour Friends&4--------")));

        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        data.getFriends().forEach(friend -> {
            PlayerData friendData = Main.fetchPlayer(friend.getPlayerId());

            player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.list.format", "- &e%name%")
                    .replace("%name%", friendData.getName())
            )), new TextComponent(" "), ButtonManager.generateModifyButton(friendData.getName()), new TextComponent(" "), ButtonManager.generateInventoryButton(friendData.getName()), new TextComponent(" "), ButtonManager.generateArmorButton(friendData.getName()), new TextComponent(" "), ButtonManager.generateRemoveButton(friendData.getName()));

        });

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.list.footer", "&4---------------------------")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }

    public FriendListCommand(@NotNull String name) {
        super(name);
    }

    public FriendListCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, java.util.@NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
}
