package me.efekos.awakensmponline.commands.args;

import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.ArgumentResult;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeadPlayerArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "player";
    }

    @Override
    public ArrayList<ArgumentResult> getList(Player player, String current) {
        return (ArrayList<ArgumentResult>) PlayerDataManager.getAll().stream().filter(playerData -> !playerData.isAlive()).map(d->new ArgumentResult().setValue(d.getName()).setName(d.getName())).collect(Collectors.toList());
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.REQUIRED;
    }

    @Override
    public boolean handleCorrection(String given) {
        PlayerData data = PlayerDataManager.get(given);
        return data!=null&&!data.isAlive();
    }
}
