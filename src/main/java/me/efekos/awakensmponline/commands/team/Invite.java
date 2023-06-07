package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.Request;
import me.efekos.awakensmponline.data.RequestType;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.awakensmponline.files.TeamDataManager;
import me.efekos.awakensmponline.utils.ButtonManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.PlayerArgument;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Command(name = "invite",description = "Invite someone to your team!",permission = "awakensmp.command.team.invite")
public class Invite extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return Team.class;
    }

    public Invite(@NotNull String name) {
        super(name);
    }

    public Invite(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new PlayerArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData ptiData = PlayerDataManager.get(args[0]);
        if(ptiData==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.invite.not-player").replace("%player%",args[0])));
            return;
        }
        if(ptiData.getCurrentTeam()!=null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.already-in-team").replace("%player%",ptiData.getName())));
            return;
        }
        OfflinePlayer offlinePti = Bukkit.getOfflinePlayer(ptiData.getUuid());
        if(!offlinePti.isOnline()){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.invite.not-online").replace("%player%",offlinePti.getName())));
        }
        // there is a player online rn and we wanna send team req to him.
        Player pti = offlinePti.getPlayer();

        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        if(data.getCurrentTeam()==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.not-in-team")));
            return;
        }
        TeamData team = TeamDataManager.get(data.getCurrentTeam());
        if(!team.getOwner().equals(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.invite.not-owner")));
            return;
        }

        Request req = new Request(UUID.randomUUID(), RequestType.TEAMMATE,team.getId(),pti.getUniqueId());
        RequestDataManager.create(req);

        pti.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.invite.hey").replace("%team%",team.getDisplayName())));
        pti.spigot().sendMessage(ButtonManager.generateJoinTeamButton(req.getId()+""),new TextComponent(" "),ButtonManager.generateRejectTeamInviteButton(req.getId()+""));

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.invite.done").replace("%player%",pti.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
