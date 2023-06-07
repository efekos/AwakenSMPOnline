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
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.ArgumentResult;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
                .withArgument(new Argument() {
                    @Override
                    public String getPlaceHolder() {
                        return "message";
                    }

                    @Override
                    public ArrayList<ArgumentResult> getList(Player player, String current) {
                        return new ArrayList<>();
                    }

                    @Override
                    public ArgumentPriority getPriority() {
                        return ArgumentPriority.REQUIRED;
                    }

                    @Override
                    public boolean handleCorrection(String given) {
                        return given!=null;
                    }
                });
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
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.not-in-team")));
            return;
        }
        TeamData team = TeamDataManager.get(data.getCurrentTeam());

        team.getMembers().forEach(uuid -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if(offlinePlayer.isOnline()){
                offlinePlayer.getPlayer().sendMessage(TranslateManager.translateColors(LangConfig.get("notifications.team.chat").replace("%player%",player.getName()).replace("%message%",message)));
            }
        });
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
