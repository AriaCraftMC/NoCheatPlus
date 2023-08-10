package fr.neatmonster.nocheatplus.hooks.ncpdragdown;

import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import fr.neatmonster.nocheatplus.logging.LogManager;
import fr.neatmonster.nocheatplus.logging.Streams;
import fr.neatmonster.nocheatplus.players.DataManager;
import fr.neatmonster.nocheatplus.players.IPlayerData;

import org.bukkit.block.Block;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.checks.moving.MovingData;
import fr.neatmonster.nocheatplus.compat.BridgeHealth;

import org.bukkit.entity.Player;

import fr.neatmonster.nocheatplus.NCPAPIProvider;
import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.hooks.NCPHook;

public class NCPDragDownHook implements NCPHook
{
    //private IGenericInstanceHandle<MCAccess> mcAccess = null;
    private final CheckType type[] = {CheckType.MOVING_SURVIVALFLY, CheckType.MOVING_PASSABLE};
    public NCPDragDownHook() {
        hook();
        //mcAccess = NCPAPIProvider.getNoCheatPlusAPI().getGenericInstanceHandle(MCAccess.class);
    }

    public boolean onCheckFailure(final CheckType checkType, final Player p, final IViolationInfo vlInfo) {
        return !p.hasPermission("ncpdd.bypass") && vlInfo.willCancel() && dragDown(p, checkType);
    }

    private boolean dragDown(final Player p, final CheckType checkType) {
        double MaxY = -1.0;
        float FallDist = -1.0f;
        final boolean debug = p.hasPermission("ncpdd.debug");
        final IPlayerData pData = DataManager.getPlayerData(p);
        if (pData != null) {
            final MovingData mData = pData.getGenericInstance(MovingData.class);
            MaxY = mData.noFallMaxY;
            FallDist = mData.noFallFallDistance;
        }

        if (p.getLocation().getY() <= 0.0) {
            if (MaxY != -1.0 && FallDist != -1.0) {
                final MovingData mData = pData.getGenericInstance(MovingData.class);
                mData.noFallMaxY = MaxY;
                mData.setTeleported(p.getLocation().clone().subtract(0.0, 2.0, 0.0));
                mData.noFallFallDistance = FallDist + 2.0f;
                if (debug) log("Set position to 2 blocks lower!", p);
            }
            // Kill player
            if (p.getLocation().getY() <= -70.0) {
                BridgeHealth.setHealth(p, 0.0);
                BridgeHealth.damage(p, 1.0);
            }
            return false;
        }

        final Location ploc = p.getLocation();
        final Block bDown = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (PlayerUtil.isOnGround(p, 1.0) || PlayerUtil.isOnGround(p, 0.0)) {
            if (pData != null) {
                final MovingData mData = pData.getGenericInstance(MovingData.class);
                if (mData.hasSetBack()) if (mData.getSetBackY() >= p.getLocation().getY()) {
                    mData.noFallMaxY = MaxY;
                    mData.noFallFallDistance = FallDist;
                    mData.setTeleported(p.getLocation());
                    if (debug) {
                        log("Set position to current location!", p);
                    }
                    return false;
                }
            }
            return false;
        }

        ploc.setX(p.getLocation().getX());
        ploc.setZ(p.getLocation().getZ());
        ploc.setPitch(p.getLocation().getPitch());
        ploc.setYaw(p.getLocation().getYaw());

        // Handle on set to lower y location
        boolean is2 = false;
        if (PlayerUtil.isAir(bDown.getType())) {
            final Block bDown2 = bDown.getRelative(BlockFace.DOWN);
            if (PlayerUtil.isAir(bDown2.getType())) {
                is2 = true;
                ploc.setY((double)bDown2.getLocation().getBlockY());
            } else ploc.setY((double)bDown.getLocation().getBlockY());
        }
        if (MaxY != -1.0 && FallDist != -1.0) {
            final MovingData mData = pData.getGenericInstance(MovingData.class);
            mData.noFallMaxY = MaxY;
            mData.setTeleported(ploc);
            mData.noFallFallDistance = FallDist + (is2 ? 2.0f : 1.0f);
        }
        if (debug) log("Set position to " + (is2 ? "2": "1") + " block(s) lower!", p);
        return false;
    }

    public void hook() {
        NCPHookManager.addHook(type, (NCPHook)this);
    }

    public String getHookName() {
        return "NCPDragDown";
    }

    public String getHookVersion() {
        return "1.1";
    }

    private void log(String s, Player p) {
        final LogManager logManager = NCPAPIProvider.getNoCheatPlusAPI().getLogManager();
        final StringBuilder builder = new StringBuilder(300);
        builder.append(getHookName());
        builder.append(" [" + ChatColor.YELLOW + p.getName());
        builder.append(ChatColor.WHITE + "] ");
        builder.append(s);
        final String message = builder.toString();
        logManager.info(Streams.NOTIFY_INGAME, message);
    }
}
