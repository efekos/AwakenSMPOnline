package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.TeamCommand;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.TeamData;

import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.syntax.impl.StringArgument;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

;

@Command(name = "chat", description = "Use your team's chat!", permission = "awakensmp.team.chat")
public class TeamChatCommand extends SubCommand {
    public TeamChatCommand(@NotNull String name) {
        super(name);
    }

    public TeamChatCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage,
                           @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return TeamCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new StringArgument("message", ArgumentPriority.REQUIRED, 0, Integer.MAX_VALUE));
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        StringBuilder builder = new StringBuilder();
        for (String word : args) {
            builder.append(" ");
            builder.append(word);
        }
        String message = builder.substring(1);

        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        if (data.getCurrentTeam() == null) {
            player.sendMessage(TranslateManager
                    .translateColors(Main.LANG.getString("commands.team.not-in-team", "&cYou are not in a team.")));
            return;
        }
        TeamData team = Main.TEAM_DATA.get(data.getCurrentTeam());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer()
                        .sendMessage(TranslateManager.translateColors(Main.LANG
                                .getString("notifications.team.chat", "&5[&dTEAM CHAT&5] &e%player%&8: &a%message%")
                                .replace("%player%", player.getName()).replace("%message%", message)));
            }
        });
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
