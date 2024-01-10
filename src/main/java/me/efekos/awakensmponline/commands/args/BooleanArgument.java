package me.efekos.awakensmponline.commands.args;

import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BooleanArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "value";
    }

    @Override
    public List<String> getList(Player player, String current) {
        return Arrays.asList("true", "false");
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.OPTIONAL;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String given) {
        if (!Objects.equals(given, "true") && !Objects.equals(given, "false"))
            return ArgumentHandleResult.fail(given + " is not a boolean");
        return ArgumentHandleResult.success();
    }
}
