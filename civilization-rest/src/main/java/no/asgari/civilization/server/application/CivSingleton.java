package no.asgari.civilization.server.application;

import com.google.common.cache.LoadingCache;
import lombok.extern.log4j.Log4j;

/**
 * Application singleton
 */
@SuppressWarnings("unchecked")
@Log4j
//This class is a @Singleton
public final class CivSingleton {

    private static CivSingleton civSingleton = new CivSingleton();
    //Key is playerId and value is username
    private LoadingCache<String, String> usernameCache;

    private CivSingleton() {
    }

    public static CivSingleton instance() {
        return civSingleton;
    }

    public void setPlayerCache(LoadingCache<String, String> usernameCache) {
        this.usernameCache = usernameCache;
    }

    /**
     * Key is playerId and value is username
     */
    public LoadingCache<String, String> playerCache() {
        return usernameCache;
    }
}
