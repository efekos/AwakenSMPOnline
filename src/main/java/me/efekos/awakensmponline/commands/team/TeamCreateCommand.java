package me.efekos.awakensmponline.commands.team;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.TeamCommand;
import me.efekos.awakensmponline.commands.args.TeamNameArgument;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.TeamData;
import me.efekos.simpler.commands.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Command(name = "create", description = "Create a new team!", permission = "awakensmp.team.create")
public class TeamCreateCommand extends SubCommand {
    public TeamCreateCommand(@NotNull String name) {
        super(name);
    }

    public TeamCreateCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage,
                             @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return TeamCommand.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new TeamNameArgument());
    }

    private final List<String> chars = Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d",
            "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m", "Q", "W", "E", "R", "T", "Y", "U", "I",
            "O", "P", "A", "S", "D", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M", "_");

    @Override
    public void onPlayerUse(Player player, String[] args) {
        if (Arrays.stream(args[0].split("")).anyMatch(charVal -> !chars.contains(charVal))) {
            player.sendMessage(
                    TranslateManager.translateColors(Main.LANG.getString("commands.team.create.not-valid-chars",
                            "&cYour team name should only include &ba-z characters&c,&bA-Z characters&c and &b_&c.")));
            return;
        }

        PlayerData data = Main.fetchPlayer(player.getUniqueId());

        if (data.getCurrentTeam() != null) {
            player.sendMessage(TranslateManager.translateColors(
                    Main.LANG.getString("commands.team.already-in-team", "&cYou are in a team already.")));
            return;
        }
        if (Main.TEAM_DATA.getAll().stream().anyMatch(teamData -> teamData.getName().equals(args[0]))) {
            player.sendMessage(TranslateManager.translateColors(
                    Main.LANG.getString("commands.team.create.same-name", "&b%name% &cis taken by another team.")
                            .replace("%name%", args[0])));
        }

        TeamData teamData = new TeamData(args[0], args[0],
                Main.LANG.getString("commands.team.create.default-description", "A brand new team!"), new ArrayList<>(),
                player.getUniqueId());
        Main.TEAM_DATA.add(teamData);
        teamData.getMembers().add(player.getUniqueId());

        data.setCurrentTeam(teamData.getId());
        Main.PLAYER_DATA.update(data.getUuid(), data);

        player.sendMessage(TranslateManager.translateColors(
                Main.LANG.getString("commands.team.create.done", "&aSuccessfully created a new team called &b%team%&a!")
                        .replace("%team%", args[0])));
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
