package me.arjona.balloons.generic_profile;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.arjona.balloons.Main;
import me.arjona.balloons.mascot.impl.Body;
import me.arjona.balloons.mascot.impl.Mascot;
import org.bukkit.entity.Player;

import java.util.UUID;

@Setter @Getter
@RequiredArgsConstructor
public class Profile {

    @NonNull private final UUID uuid;
    private String name;

    private Body mascotBody;

    private Mascot mascotModel;

    /*public void load(Main plugin) {
        Document document = plugin.getProfileManager().getMongoCollection().find(Filters.eq("uuid", uuid.toString())).first();

        if (document == null) {
            save(plugin);
            load(plugin);
            return;
        }

        if (document.containsKey("mascotBody")) {
            String body = document.getString("mascotBody");
            if (plugin.getMascotManager().exists(body)) {
                this.mascotBody = plugin.getMascotManager().getBody(body));
            }
        }
    }

    public void save(Main plugin) {
        Document document = new Document("uuid", uuid.toString())
                .append("name", name);

        if (mascotBody != null) {
            document.append("mascotBody", mascotBody.getName());
        }

        plugin.getProfileManager().getMongoCollection().replaceOne(
                Filters.eq("uuid", uuid.toString()),
                document,
                new ReplaceOptions().upsert(true));
    }*/

    public void applyMascot(Main plugin, Player player, Body body) {
        if (mascotModel != null) removeMascot();

        if (this.mascotBody == null || !this.mascotBody.getName().equals(body.getName()))
            this.mascotBody = body;

        this.mascotModel = new Mascot(plugin, player, body);
        plugin.getMascotManager().getMascots().put(uuid, mascotModel);
    }

    public void removeMascot() {
        this.mascotBody = null;

        if (this.mascotModel != null) this.mascotModel.die();

        this.mascotModel = null;
    }
}
