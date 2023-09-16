package me.efekos.awakensmponline.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.data.TeamData;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class Awaken extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "awaken";
    }

    @Override
    public @NotNull String getAuthor() {
        return "efekos";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        List<String> splitParams = Arrays.stream(params.split("_")).toList();

        PlayerData data = Main.fetchPlayer(player.getUniqueId());

        switch (splitParams.get(0)){
            case "state": {
                String start = "";
                if(data.isAlive()){
                    if(splitParams.contains("colored")) start = ChatColor.GREEN+start;

                    return splitParams.contains("tick") ? start+"✔" : start+"Alive";
                } else {
                    if(splitParams.contains("colored")) start = ChatColor.RED+start;

                    return splitParams.contains("tick") ? start+"✖" : start+"Dead";
                }
            }
            case "team":{
                TeamData teamData = Main.TEAM_DATA.get(data.getCurrentTeam());
                if(teamData!=null)
                return teamData.getName();
                else return ChatColor.RED+"✖";
            }
            default:return "";
        }
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        List<String> splitParams = Arrays.stream(params.split("_")).toList();

        PlayerData data = Main.fetchPlayer(player.getUniqueId());

        switch (splitParams.get(0)){
            case "state": {
                String start = "";
                if(data.isAlive()){
                    if(splitParams.contains("colored")) start = ChatColor.GREEN+start;

                    return splitParams.contains("tick") ? start+"✔" : start+"Alive";
                } else {
                    if(splitParams.contains("colored")) start = ChatColor.RED+start;

                    return splitParams.contains("tick") ? start+"✖" : start+"Dead";
                }
            }
            case "team":{
                TeamData teamData = Main.TEAM_DATA.get(data.getCurrentTeam());
                return teamData.getName();
            }
            default: return "";
        }
    }
}
