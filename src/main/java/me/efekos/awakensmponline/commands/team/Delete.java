package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "delete",description = "Delete a team",permission = "awakensmp.team.delete")
public class Delete extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return Team.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    public Delete(@NotNull String name) {
        super(name);
    }

    public Delete(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        if(data.getCurrentTeam()==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.not-in-team","&cYou are not in a team.")));
            return;
        }
        TeamData team = Main.TEAM_DATA.get(data.getCurrentTeam());
        if(!team.getOwner().equals(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.not-owner","&cYou are not the owner of team called &b%team%&c.").replace("%team%",team.getDisplayName())));
            return;
        }

        team.getMembers().forEach(uuid -> {
            PlayerData memberData = Main.fetchPlayer(uuid);
            memberData.setCurrentTeam(null);
            Main.PLAYER_DATA.update(memberData.getUuid(),memberData);
        });

        data.setCurrentTeam(null);
        Main.PLAYER_DATA.update(data.getUuid(),data);
        Main.TEAM_DATA.delete(team.getId());
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.delete.done","&aSuccessfully deleted the team &b%team%&a!").replace("%team%",team.getDisplayName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
