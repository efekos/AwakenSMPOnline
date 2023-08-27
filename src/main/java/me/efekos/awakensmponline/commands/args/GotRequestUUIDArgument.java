package me.efekos.awakensmponline.commands.args;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.data.Request;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentHandleResult;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GotRequestUUIDArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "request";
    }

    @Override
    public List<String> getList(Player player, String current) {
        Stream<Request> xrequests = Main.REQUEST_DATA.getAll().stream().filter(request -> request.getGetter().equals(player.getUniqueId()));
        List<String> results = new ArrayList<>();

        for (Request req:xrequests.collect(Collectors.toList())){
            results.add(req.getId().toString());
        }

        return results;
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.REQUIRED;
    }

    @Override
    public ArgumentHandleResult handleCorrection(String given) {
        return ArgumentHandleResult.success();
    }
}
