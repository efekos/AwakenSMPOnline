package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.TeamCommand;
import me.efekos.awakensmponline.commands.args.GotRequestUUIDArgument;
import me.efekos.awakensmponline.data.*;
import me.efekos.simpler.annotations.Command;
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

@Command(name = "join",description = "Join to a team that sent a friend request to you!",permission = "awakensmp.team.join")
public class TeamJoinCommand extends SubCommand {
    public TeamJoinCommand(@NotNull String name) {
        super(name);
    }

    public TeamJoinCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
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
        if(req==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.join.invalid-req","&cThere is no request with id &b%id%&c.").replace("%id%",args[0])));
            return;
        }
        if(req.getType()!= RequestType.TEAMMATE){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.join.not-team","&cThis request is not a team invite.")));
            return;
        }
        if(req.getGetter()!=player.getUniqueId()){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.join.not-got","&cThis request was not sent to you.")));
        }
        //we have a team req and wanna accept it.

        PlayerData data = Main.fetchPlayer(player.getUniqueId());

        if(data.getCurrentTeam()!=null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.already-in-team","&cYou are in a team already.")));
            return;
        }

        data.setCurrentTeam(req.getSender());
        Main.PLAYER_DATA.update(data.getUuid(),data);
        req.setDone(true);
        TeamData team = Main.TEAM_DATA.get(req.getSender());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if(offlinePlayer.isOnline()){
                offlinePlayer.getPlayer().sendMessage(TranslateManager.translateColors(Main.LANG.getString("notifications.team.joined","&5[&dTEAM&5] &b%player% &ejoined the team!").replace("%player%",player.getName())));
            }
        });

        team.getMembers().add(player.getUniqueId());
        Main.TEAM_DATA.update(team.getId(),team);

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.join.done","&aSuccessfully joined to the team called &b%team%&a!").replace("%team%", team.getDisplayName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
