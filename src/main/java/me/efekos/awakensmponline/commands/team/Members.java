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
import java.util.UUID;

@Command(name = "members",description = "See the members on your team!",permission = "awakensmp.command.team.members")
public class Members extends SubCommand {
    public Members(@NotNull String name) {
        super(name);
    }

    public Members(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
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
    public void onPlayerUse(@NotNull Player player, String[] args) {
        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        if(data.getCurrentTeam()==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.not-in-team")));
            return;
        }
        TeamData team = TeamDataManager.get(data.getCurrentTeam());

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.members.header")));

        for(UUID uuid:team.getMembers()){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.members."+(team.getOwner().equals(uuid)?"owner":"member"))
                    .replace("%player%",offlinePlayer.getName())
            ));
        }

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.members.footer")));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
