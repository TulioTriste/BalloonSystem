package me.arjona.balloons.profile;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.arjona.balloons.Main;
import me.arjona.balloons.profile.listener.ProfileListener;
import org.bson.Document;

@Getter
public class ProfileManager {

    private final MongoCollection<Document> mongoCollection;

    public ProfileManager(Main plugin) {
        this.mongoCollection = plugin.getMongoDatabase().getCollection("profiles");

        plugin.getServer().getPluginManager().registerEvents(new ProfileListener(plugin), plugin);
    }

    /*public Profile loadProfile(Main plugin, UUID uuid, String name) {
        Document document = mongoCollection.find(Filters.eq("uuid", uuid.toString())).first();

        if (document == null) {
            return new Profile(uuid, name);
        }

        return loadProfile(plugin, document);
    }

    public Profile loadProfile(Main plugin, Document document) {
        UUID uuid = UUID.fromString(document.getString("uuid"));
        String name = document.getString("name");

        Profile profile = new Profile(uuid, name);

        if (document.containsKey("mascot")) {
            String mascot = document.getString("mascot");
            if (plugin.getMascotManager().isValid(mascot)) {
                profile.setMascot(mascot);
            }
        }

        return profile;
    }*/
}
