package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.TeamCommand;
import me.efekos.awakensmponline.commands.args.GotRequestUUIDArgument;
import me.efekos.awakensmponline.data.Request;
import me.efekos.awakensmponline.data.RequestType;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.simpler.commands.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Command(name = "reject", description = "Reject a team that sent a friend request to you!", permission = "awakensmp.team.reject", playerOnly = true)
public class TeamRejectCommand extends SubCommand {
    public TeamRejectCommand(@NotNull String name) {
        super(name);
    }

    public TeamRejectCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage,
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
                .withArgument(new GotRequestUUIDArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        Request req = Main.REQUEST_DATA.get(UUID.fromString(args[0]));
        if (req == null) {
            player.sendMessage(TranslateManager.translateColors(
                    Main.LANG.getString("commands.team.reject.invalid-req", "&cThere is no request with id &b%id%&c.")
                            .replace("%id%", args[0])));
            return;
        }
        if (req.getType() != RequestType.TEAMMATE) {
            player.sendMessage(TranslateManager.translateColors(
                    Main.LANG.getString("commands.team.reject.not-team", "&cThis request is not a team invite.")));
            return;
        }
        if (req.getGetter() != player.getUniqueId()) {
            player.sendMessage(TranslateManager.translateColors(
                    Main.LANG.getString("commands.team.reject.not-got", "&cThis request was not sent to you.")));
        }
        // we have a team req and wanna reject it.

        req.setDone(true);
        TeamData team = Main.TEAM_DATA.get(req.getSender());
        Main.REQUEST_DATA.delete(req.getId());


        player.sendMessage(TranslateManager.translateColors(Main.LANG
                .getString("commands.team.reject.done", "&aSuccessfully rejected the team invite came from &b%team%&a!")
                .replace("%team%", team.getDisplayName())));

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer()
                        .sendMessage(TranslateManager.translateColors(Main.LANG
                                .getString("notifications.team.reject",
                                        "&5[&dTEAM&5] &b%player% &erejected our team invite!")
                                .replace("%player%", player.getName())));
            }
        });
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
