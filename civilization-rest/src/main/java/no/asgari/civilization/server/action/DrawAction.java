package no.asgari.civilization.server.action;

import com.google.common.base.Preconditions;
import com.mongodb.DB;
import lombok.extern.log4j.Log4j;
import no.asgari.civilization.server.application.CivSingleton;
import no.asgari.civilization.server.model.Aircraft;
import no.asgari.civilization.server.model.Artillery;
import no.asgari.civilization.server.model.Citystate;
import no.asgari.civilization.server.model.Civ;
import no.asgari.civilization.server.model.CultureI;
import no.asgari.civilization.server.model.CultureII;
import no.asgari.civilization.server.model.CultureIII;
import no.asgari.civilization.server.model.Draw;
import no.asgari.civilization.server.model.GreatPerson;
import no.asgari.civilization.server.model.Hut;
import no.asgari.civilization.server.model.Infantry;
import no.asgari.civilization.server.model.Mounted;
import no.asgari.civilization.server.model.PBF;
import no.asgari.civilization.server.model.Player;
import no.asgari.civilization.server.model.PrivateLog;
import no.asgari.civilization.server.model.PublicLog;
import no.asgari.civilization.server.model.Tile;
import no.asgari.civilization.server.model.Type;
import no.asgari.civilization.server.model.Undo;
import no.asgari.civilization.server.model.Village;
import no.asgari.civilization.server.model.Wonder;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import java.util.concurrent.ExecutionException;

/**
 * Class that will perform draws and log them
 */
@Log4j
public class DrawAction {
    private final JacksonDBCollection<PBF, String> pbfCollection;
    private final JacksonDBCollection<Draw, String> drawCollection;
    private final JacksonDBCollection<Player, String> playerCollection;
    private final GameLogAction logAction;

    public DrawAction(DB db) {
        this.pbfCollection = JacksonDBCollection.wrap(db.getCollection(PBF.COL_NAME), PBF.class, String.class);
        this.drawCollection = JacksonDBCollection.wrap(db.getCollection(Draw.COL_NAME), Draw.class, String.class);
        this.playerCollection = JacksonDBCollection.wrap(db.getCollection(Player.COL_NAME), Player.class, String.class);
        logAction = new GameLogAction(db);
    }

    /**
     * Will make a draw of a Civ and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Civ> drawCiv(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Civ civ = pbf.getCivs().remove(0);
        Draw<Civ> draw = new Draw<>(pbfId, playerId);
        draw.setItem(civ);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew civ " + civ + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());
        return draw;
    }

    /**
     * Will make a draw of a Aircraft and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Aircraft> drawAircraft(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Aircraft aircraft = pbf.getAircraft().remove(0);
        Draw<Aircraft> draw = new Draw<>(pbfId, playerId);
        draw.setItem(aircraft);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew aircraft " + aircraft + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());
        return draw;
    }

    /**
     * Will make a draw of a Artillery and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Artillery> drawArtillery(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Artillery artillery = pbf.getArtillery().remove(0);
        Draw<Artillery> draw = new Draw<>(pbfId, playerId);
        draw.setItem(artillery);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew artillery " + artillery + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a Citystate and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Citystate> drawCitystate(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Citystate citystate = pbf.getCitystates().remove(0);
        Draw<Citystate> draw = new Draw<>(pbfId, playerId);
        draw.setItem(citystate);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew Citystate " + citystate + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a CultureI and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<CultureI> drawCultureI(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        CultureI cultureI = pbf.getCultureIs().remove(0);
        Draw<CultureI> draw = new Draw<>(pbfId, playerId);
        draw.setItem(cultureI);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew CultureI " + cultureI + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a CultureII and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<CultureII> drawCultureII(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        CultureII cultureII = pbf.getCultureIIs().remove(0);
        Draw<CultureII> draw = new Draw<>(pbfId, playerId);
        draw.setItem(cultureII);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew CultureII " + cultureII + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a CultureIII and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<CultureIII> drawCultureIII(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        CultureIII cultureIII = pbf.getCultureIIIs().remove(0);
        Draw<CultureIII> draw = new Draw<>(pbfId, playerId);
        draw.setItem(cultureIII);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew CultureIII " + cultureIII + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a GreatPerson and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<GreatPerson> drawGreatPerson(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        GreatPerson greatPerson = pbf.getGreatPersons().remove(0);
        Draw<GreatPerson> draw = new Draw<>(pbfId, playerId);
        draw.setItem(greatPerson);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew GreatPerson " + greatPerson + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a Hut and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Hut> drawHut(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Hut hut = pbf.getHuts().remove(0);
        Draw<Hut> draw = new Draw<>(pbfId, playerId);
        draw.setItem(hut);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew Hut " + hut + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a Infantry and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Infantry> drawInfantry(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Infantry infantry = pbf.getInfantry().remove(0);
        Draw<Infantry> draw = new Draw<>(pbfId, playerId);
        draw.setItem(infantry);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew Infantry " + infantry + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a Mounted and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Mounted> drawMounted(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Mounted mounted = pbf.getMounted().remove(0);
        Draw<Mounted> draw = new Draw<>(pbfId, playerId);
        draw.setItem(mounted);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew Mounted " + mounted + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a Tile and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Tile> drawTile(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Tile tile = pbf.getTiles().remove(0);
        Draw<Tile> draw = new Draw<>(pbfId, playerId);
        draw.setItem(tile);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew Tile " + tile + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a Village and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Village> drawVillage(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Village village = pbf.getVillages().remove(0);
        Draw<Village> draw = new Draw<>(pbfId, playerId);
        draw.setItem(village);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew Village " + village + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    /**
     * Will make a draw of a Wonder and store the draw in the collection
     *
     * @param pbfId    - The PBF
     * @param playerId - The player making the draw
     * @return - Returns the draw made
     */
    public Draw<Wonder> drawWonder(String pbfId, String playerId) {
        Preconditions.checkNotNull(pbfId);
        Preconditions.checkNotNull(playerId);

        PBF pbf = pbfCollection.findOneById(pbfId);
        Wonder wonder = pbf.getWonders().remove(0);
        Draw<Wonder> draw = new Draw<>(pbfId, playerId);
        draw.setItem(wonder);

        WriteResult<Draw, String> drawInsert = drawCollection.insert(draw);
        pbfCollection.updateById(pbf.getId(), pbf);
        log.debug("Drew Wonder " + wonder + " and updated pbf");

        createLog(draw);
        draw.setId(drawInsert.getSavedId());

        return draw;
    }

    private void createLog(Draw<? extends Type> draw) {
        createPublicLog(draw);
        createPrivateLog(draw);
    }

    private PublicLog createPublicLog(Draw draw) {
        PublicLog pl = new PublicLog();
        pl.setDraw(draw);
        pl.setPbfId(draw.getPbfId());
        try {
            pl.setUsername(CivSingleton.getInstance().getUsernameCache().get(draw.getPlayerId()));
        } catch (ExecutionException e) {
            log.error("Couldn't retrieve username from cache");
            pl.setUsername(getUsernameFromPlayerId(draw.getPlayerId()));
        }
        pl.createAndSetLog();
        pl.setId(logAction.record(pl));
        return pl;
    }

    private PrivateLog createPrivateLog(Draw draw) {
        PrivateLog pl = new PrivateLog();
        pl.setDraw(draw);
        pl.setPbfId(draw.getPbfId());
        try {
            pl.setUsername(CivSingleton.getInstance().getUsernameCache().get(draw.getPlayerId()));
        } catch (ExecutionException e) {
            log.error("Couldn't retrieve username from cache");
            pl.setUsername(getUsernameFromPlayerId(draw.getPlayerId()));
        }
        pl.setReveal(false);
        pl.createAndSetLog();
        pl.setId(logAction.record(pl));
        return pl;
    }

    private String getUsernameFromPlayerId(String playerId) {
        return playerCollection.findOneById(playerId).getUsername();
    }

}
