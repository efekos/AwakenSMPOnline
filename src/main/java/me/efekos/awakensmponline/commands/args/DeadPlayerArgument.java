package me.efekos.awakensmponline.commands.args;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class DeadPlayerArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "player";
    }

    @Override
    public List<String> getList(Player player, String current) {
        return Main.PLAYER_DATA.getAll().stream().filter(playerData -> !playerData.isAlive()).map(PlayerData::getName).collect(Collectors.toList());
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.REQUIRED;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String given) {
        Player player = Bukkit.getPlayer(given);
        if (player == null) return ArgumentHandleResult.fail(given + " is not a player, or not online");
        PlayerData data = Main.PLAYER_DATA.get(player.getUniqueId());
        if (data == null) return ArgumentHandleResult.fail(given + " is not a player");
        if (data.isAlive()) return ArgumentHandleResult.fail(given + " is not dead");
        return ArgumentHandleResult.success();
    }
}
