package me.arjona.balloons.profile;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.arjona.balloons.Main;
import org.bson.Document;

import java.util.UUID;

@Setter @Getter
@RequiredArgsConstructor
public class Profile {

    @NonNull private final UUID uuid;
    private String name;

    private String mascotBody;

    public void load(Main plugin) {
        Document document = plugin.getProfileManager().getMongoCollection().find(Filters.eq("uuid", uuid.toString())).first();

        if (document == null) {
            save(plugin);
            load(plugin);
            return;
        }

        if (document.containsKey("mascotBody")) {
            String body = document.getString("mascotBody");
            if (plugin.getMascotManager().isValid(body)) {
                this.mascotBody = body;
            }
        }
    }

    public void save(Main plugin) {
        Document document = new Document("uuid", uuid.toString())
                .append("name", name);

        if (mascotBody != null) {
            document.append("mascotBody", mascotBody);
        }

        plugin.getProfileManager().getMongoCollection().replaceOne(
                Filters.eq("uuid", uuid.toString()),
                document,
                new ReplaceOptions().upsert(true));
    }
}
