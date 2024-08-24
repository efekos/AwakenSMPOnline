package me.efekos.awakensmponline.commands.args;

import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class FriendModifySettingArgument extends Argument {
    private static final List<String> avaliable = Arrays.asList("worldAllowed", "locationAllowed", "compassAllowed", "inventoryAllowed", "armorAllowed", "healthAllowed", "expAllowed", "foodAllowed", "teleportAllowed");

    @Override
    public String getPlaceHolder() {
        return "setting";
    }

    @Override
    public List<String> getList(Player player, String current) {
        return avaliable;
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.OPTIONAL;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String given) {
        if (avaliable.contains(given)) return ArgumentHandleResult.fail(given + " is not a modify option");
        return ArgumentHandleResult.success();
    }
}
