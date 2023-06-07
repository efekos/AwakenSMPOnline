package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.commands.Team;
import me.efekos.awakensmponline.commands.args.TeamNameArgument;
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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Command(name = "create",description = "Create a new team!",permission = "awakensmp.command.team.create")
public class Create extends SubCommand {
    public Create(@NotNull String name) {
        super(name);
    }

    public Create(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return Team.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new TeamNameArgument());
    }

    private final List<String> chars = Arrays.asList("q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","D","F","G","H","J","K","L","Z","X","C","V","B","N","M","_");

    @Override
    public void onPlayerUse(Player player, String[] args) {
        if (Arrays.stream(args[0].split("")).anyMatch(charVal -> !chars.contains(charVal))){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.create.not-valid-chars")));
            return;
        }

        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());

        if(data.getCurrentTeam()!=null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.already-in-team")));
            return;
        }
        if(TeamDataManager.get(args[0])!=null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.create.same-name").replace("%name%",args[0])));
        }


        TeamData teamData = new TeamData(UUID.randomUUID(),args[0],args[0],LangConfig.get("commands.team.create.default-description"),new ArrayList<>(),player.getUniqueId());
        TeamDataManager.create(teamData);
        teamData.getMembers().add(player.getUniqueId());

        data.setCurrentTeam(teamData.getId());
        PlayerDataManager.update(data.getUuid(),data);

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.team.create.done").replace("%team%",args[0])));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
