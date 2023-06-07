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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "my",description = "See the current team you are in!",permission = "awakensmp.command.team.my")
public class My extends SubCommand {
    public My(@NotNull String name) {
        super(name);
    }

    public My(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
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

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.my.header")));

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.my.name").replace("%name%",team.getDisplayName())));
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.my.owner").replace("%owner%", Bukkit.getOfflinePlayer(team.getOwner()).getName())));
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.my.members.header")));

        team.getMembers().forEach(uuid -> {
            if(!uuid.equals(team.getOwner()))
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.my.members.format").replace("%player%",Bukkit.getOfflinePlayer(uuid).getName())));
        });
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
