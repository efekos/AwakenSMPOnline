package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "leave",description = "Leave your team!",permission = "awakensmp.command.team.leave")
public class Leave extends SubCommand {
    public Leave(@NotNull String name) {
        super(name);
    }

    public Leave(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return Team.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        if(data.getCurrentTeam()==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.not-in-team")));
            return;
        }
        TeamData team = TeamDataManager.get(data.getCurrentTeam());
        if(team.getOwner().equals(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.leave.owner")));
            return;
        }

        data.setCurrentTeam(null);

        team.getMembers().remove(player.getUniqueId());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

            if(offlinePlayer.isOnline()){
                offlinePlayer.getPlayer().sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.team.leaved").replace("%player%",player.getName())));
            }
        });

        TeamDataManager.update(team.getId(),team);
        PlayerDataManager.update(data.getUuid(),data);

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.leave.done")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
