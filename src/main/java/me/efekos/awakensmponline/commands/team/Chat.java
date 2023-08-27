package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
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

@Command(name = "chat",description = "Use your team's chat!",permission = "awakensmp.command.team.chat")
public class Chat extends SubCommand {
    public Chat(@NotNull String name) {
        super(name);
    }
    public Chat(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }
    @Override
    public Class<? extends CoreCommand> getParent() {
        return Team.class;
    }
    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new StringArgument("message",ArgumentPriority.REQUIRED,0,Integer.MAX_VALUE));
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        StringBuilder builder = new StringBuilder();
        for(String word:args){
            builder.append(" ");
            builder.append(word);
        }
        String message = builder.substring(1);

        PlayerData data = PlayerDataManager.fetch(player);
        if(data.getCurrentTeam()==null){
            player.sendMessage(TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("commands.team.not-in-team","&cYou are not in a team.")));
            return;
        }
        TeamData team = TeamDataManager.get(data.getCurrentTeam());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if(offlinePlayer.isOnline()){
                offlinePlayer.getPlayer().sendMessage(TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("notifications.team.chat","&5[&dTEAM CHAT&5] &e%player%&8: &a%message%").replace("%player%",player.getName()).replace("%message%",message)));
            }
        });
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
