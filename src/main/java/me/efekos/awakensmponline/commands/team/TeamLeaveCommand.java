package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.TeamCommand;
import me.efekos.awakensmponline.data.PlayerData;
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

@Command(name = "leave", description = "Leave your team!", permission = "awakensmp.team.leave")
public class TeamLeaveCommand extends SubCommand {
    public TeamLeaveCommand(@NotNull String name) {
        super(name);
    }

    public TeamLeaveCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage,
                            @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return TeamCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        if (data.getCurrentTeam() == null) {
            player.sendMessage(TranslateManager
                    .translateColors(Main.LANG.getString("commands.team.not-in-team", "&cYou are not in a team.")));
            return;
        }
        TeamData team = Main.TEAM_DATA.get(data.getCurrentTeam());
        if (team.getOwner().equals(player.getUniqueId())) {
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.leave.owner",
                    "&cYou are the owner of this team, you can''t leave unless you delete the team.")));
            return;
        }

        data.setCurrentTeam(null);

        team.getMembers().remove(player.getUniqueId());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer()
                        .sendMessage(TranslateManager.translateColors(Main.LANG
                                .getString("notifications.team.leaved", "&5[&dTEAM&5] &b%player% &eleft the team!")
                                .replace("%player%", player.getName())));
            }
        });

        Main.TEAM_DATA.update(team.getId(), team);
        Main.PLAYER_DATA.update(data.getUuid(), data);

        player.sendMessage(TranslateManager
                .translateColors(Main.LANG.getString("commands.team.leave.done", "&aSuccessfully left the team!")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
