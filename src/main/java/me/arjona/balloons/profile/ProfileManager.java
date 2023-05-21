package me.arjona.balloons.profile;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.arjona.balloons.Main;
import me.arjona.balloons.profile.listener.ProfileListener;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Getter
public class ProfileManager {

    private final MongoCollection<Document> mongoCollection;

    private final LinkedHashMap<UUID, Profile> profiles;

    public ProfileManager(Main plugin) {
        this.mongoCollection = plugin.getMongoDatabase().getCollection("profiles");
        this.profiles = Maps.newLinkedHashMap();

        plugin.getServer().getPluginManager().registerEvents(new ProfileListener(plugin), plugin);
    }

    public Profile getProfile(String name) {
        return profiles.values().stream().filter(profile -> profile.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Profile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public Profile getOrLoadProfile(Main plugin, UUID uuid) {
        if (profiles.containsKey(uuid)) return profiles.get(uuid);

        Document document = mongoCollection.find(new Document("uuid", uuid.toString())).first();

        Profile profile = new Profile(uuid);

        if (document == null) {
            profile.save(plugin);
        }

        profile.load(plugin);
        profiles.put(uuid, profile);
        return profile;
    }
}
