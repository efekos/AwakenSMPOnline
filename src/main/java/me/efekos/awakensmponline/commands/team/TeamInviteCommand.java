package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.TeamCommand;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.Request;
import me.efekos.awakensmponline.data.RequestType;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.awakensmponline.utils.ButtonManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.syntax.impl.PlayerArgument;
import me.efekos.simpler.translation.TranslateManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Command(name = "invite",description = "Invite someone to your team!",permission = "awakensmp.team.invite")
public class TeamInviteCommand extends SubCommand {
    @Override
    public Class<? extends CoreCommand> getParent() {
        return TeamCommand.class;
    }

    public TeamInviteCommand(@NotNull String name) {
        super(name);
    }

    public TeamInviteCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new PlayerArgument(ArgumentPriority.REQUIRED));
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData ptiData = Main.fetchPlayer(UUID.fromString(args[0]));
        if(ptiData==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.invite.not-player","&cThere is no one called &b%player%&c.").replace("%player%",args[0])));
            return;
        }
        if(ptiData.getCurrentTeam()!=null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.already-in-team-other","&b%player% &cis in another team.").replace("%player%",ptiData.getName())));
            return;
        }
        OfflinePlayer offlinePti = Bukkit.getOfflinePlayer(ptiData.getUuid());
        if(!offlinePti.isOnline()){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.invite.not-online","&b%player% &cis not online.").replace("%player%",offlinePti.getName())));
        }
        // there is a player online rn and we wanna send team req to him.
        Player pti = offlinePti.getPlayer();

        PlayerData data = Main.fetchPlayer(player.getUniqueId());
        if(data.getCurrentTeam()==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.not-in-team","&cYou are not in a team.")));
            return;
        }
        TeamData team = Main.TEAM_DATA.get(data.getCurrentTeam());
        if(!team.getOwner().equals(player.getUniqueId())){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.invite.not-owner","&cYou are not the owner of this team. Only team owners can invite people to their team.")));
            return;
        }

        Request req = new Request(RequestType.TEAMMATE,team.getId(),pti.getUniqueId());
        Main.REQUEST_DATA.add(req);

        pti.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.invite.hey","&eTeam called &b%team% &esent you an invite to join their team!").replace("%team%",team.getDisplayName())));
        pti.spigot().sendMessage(ButtonManager.generateJoinTeamButton(req.getId()+""),new TextComponent(" "),ButtonManager.generateRejectTeamInviteButton(req.getId()+""));

        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.team.invite.done","&aSuccessfully sent an invite to &b%player%&a!").replace("%player%",pti.getName())));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
