package no.asgari.civilization.server.application;

import com.codahale.metrics.MetricRegistry;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.java8.auth.CachingAuthenticator;
import io.dropwizard.java8.auth.basic.BasicAuthProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.log4j.Log4j;
import no.asgari.civilization.server.model.Player;
import no.asgari.civilization.server.resource.GameResource;
import no.asgari.civilization.server.resource.LoginResource;
import no.asgari.civilization.server.resource.PlayerResource;
import org.mongojack.JacksonDBCollection;

import java.util.concurrent.TimeUnit;

@Log4j
@SuppressWarnings("unchecked")
public class CivBoardgameRandomizerApplication extends Application<CivBoardGameRandomizerConfiguration> {

    public static void main(String[] args) throws Exception {
        new CivBoardgameRandomizerApplication().run(new String[]{"server", "src/main/resources/config.yml"});
    }

    @Override
    public void initialize(Bootstrap<CivBoardGameRandomizerConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
    }

    @Override
    public void run(CivBoardGameRandomizerConfiguration configuration, Environment environment) throws Exception {
        MongoClient mongo = new MongoClient(configuration.mongohost, configuration.mongoport);
        DB db = mongo.getDB(configuration.mongodb);

        JacksonDBCollection<Player, String> playerCollection = JacksonDBCollection.wrap(db.getCollection(Player.COL_NAME), Player.class, String.class);

        createIndexForPlayer(playerCollection);
        MongoManaged mongoManaged = new MongoManaged(mongo);
        //Database
        environment.lifecycle().manage(mongoManaged);

        //healtcheck
        environment.healthChecks().register("MongoHealthCheck", new MongoHealthCheck(mongo));

        //Resources
        environment.jersey().register(new GameResource(db));
        environment.jersey().register(new LoginResource(db));
        environment.jersey().register(new PlayerResource(db));

        //Authentication

        environment.jersey().register(new BasicAuthProvider<>(new CachingAuthenticator<>(new MetricRegistry(), new CivAuthenticator(db),
                CacheBuilderSpec.parse("expireAfterWrite=120m")), "civilization"));


        createUsernameCache(playerCollection);
    }

    private void createUsernameCache(final JacksonDBCollection<Player, String> playerCollection) {
        LoadingCache<String, String> usernameCache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .maximumSize(100)
                .build(new CacheLoader<String, String>() {
                            public String load(String playerId) {
                                return playerCollection.findOneById(playerId).getUsername();
                            }
                       });

        CivSingleton.instance().setPlayerCache(usernameCache);
    }

    private void createIndexForPlayer(JacksonDBCollection<Player, String> playerCollection) {
        playerCollection.createIndex(new BasicDBObject(Player.USERNAME, 1), new BasicDBObject("unique", true));
    }

}
