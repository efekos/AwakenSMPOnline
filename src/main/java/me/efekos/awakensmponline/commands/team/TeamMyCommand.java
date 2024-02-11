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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "my",description = "See the current team you are in!",permission = "awakensmp.team.my")
public class TeamMyCommand extends SubCommand {
    public TeamMyCommand(@NotNull String name) {
        super(name);
    }

    public TeamMyCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
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
        if(data.getCurrentTeam()==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.not-in-team","&cYou are not in a team.")));
            return;
        }
        TeamData team = Main.TEAM_DATA.get(data.getCurrentTeam());

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.my.header","&5----------&dTeam Information&5----------")));

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.my.name","&dName: &b%name%").replace("%name%",team.getDisplayName())));
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.my.owner","&dOwner: &b%owner%").replace("%owner%", Bukkit.getOfflinePlayer(team.getOwner()).getName())));
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.my.members.header","&dMembers:")));

        team.getMembers().forEach(uuid -> {
            if(!uuid.equals(team.getOwner())) player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.my.members.format","&5- &d%player%").replace("%player%",Bukkit.getOfflinePlayer(uuid).getName())));
        });
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
