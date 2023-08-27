package me.efekos.awakensmponline.commands.args;

import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
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
        return PlayerDataManager.getAll().stream().filter(playerData -> !playerData.isAlive()).map(d->d.getName()).collect(Collectors.toList());
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.REQUIRED;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String given) {
        PlayerData data = PlayerDataManager.get(given);
        if(data==null) return ArgumentHandleResult.fail(given + " is not a player");
        if(data.isAlive()) return ArgumentHandleResult.fail(given + " is not dead");
        return ArgumentHandleResult.success();
    }
}
