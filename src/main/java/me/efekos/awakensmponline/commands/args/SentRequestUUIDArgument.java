package me.efekos.awakensmponline.commands.args;

import me.efekos.awakensmponline.data.Request;
import me.efekos.awakensmponline.files.RequestDataManager;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.ArgumentResult;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SentRequestUUIDArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return null;
    }

    @Override
    public ArrayList<ArgumentResult> getList(Player player, String current) {
        Stream<Request> xrequests = RequestDataManager.getAll().stream().filter(request -> request.getSender().equals(player.getUniqueId()));
        ArrayList<ArgumentResult> results = new ArrayList<>();

        for (Request req:xrequests.collect(Collectors.toList())){
            results.add(new ArgumentResult().setName(req.getId().toString()).setValue(req.getId().toString()));
        }

        return results;
    }

    @Override
    public ArgumentPriority getPriority() {
        return null;
    }

    @Override
    public boolean handleCorrection(String given) {
        return true;
    }
}
