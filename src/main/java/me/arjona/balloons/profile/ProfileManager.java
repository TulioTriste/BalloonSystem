package me.arjona.balloons.profile;

import com.mongodb.client.MongoCollection;
import me.arjona.balloons.Main;
import org.bson.Document;

import java.util.UUID;

public class ProfileManager {

    private final MongoCollection<Document> mongoCollection;

    public ProfileManager(Main plugin) {
        this.mongoCollection = plugin.getDatabase().getCollection("profiles");
    }

    private void init() {
        mongoCollection.
    }

    public Profi loadProfile(UUID uuid) {

    }
}
