/*
 * This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.neatmonster.nocheatplus.command.admin;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import fr.neatmonster.nocheatplus.NCPAPIProvider;
import fr.neatmonster.nocheatplus.command.BaseCommand;
import fr.neatmonster.nocheatplus.command.NoCheatPlusCommand.NCPReloadEvent;
import fr.neatmonster.nocheatplus.components.registry.feature.INotifyReload;
import fr.neatmonster.nocheatplus.components.registry.order.Order;
import fr.neatmonster.nocheatplus.config.ConfigManager;
import fr.neatmonster.nocheatplus.logging.LogManager;
import fr.neatmonster.nocheatplus.logging.Streams;
import fr.neatmonster.nocheatplus.permissions.Permissions;
import fr.neatmonster.nocheatplus.players.DataManager;
import fr.neatmonster.nocheatplus.utilities.StringUtil;
import fr.neatmonster.nocheatplus.worlds.WorldDataManager;

public class ReloadCommand extends BaseCommand {

    /** Components that need to be notified on reload */
    private final List<INotifyReload> notifyReload;

    public ReloadCommand(JavaPlugin plugin, List<INotifyReload> notifyReload) {
        super(plugin, "reload", Permissions.COMMAND_RELOAD);
        this.notifyReload = notifyReload;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
            String[] args) {
        if (args.length != 1) 
            return false;
        try {
            handleReloadCommand(sender);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * Handle the '/nocheatplus reload' command.
     *
     * @param sender
     *            the sender
     * @return true, if successful
     */
    private void handleReloadCommand(final CommandSender sender) throws IOException, InvalidConfigurationException {
        final LogManager logManager = NCPAPIProvider.getNoCheatPlusAPI().getLogManager();
        if (!sender.equals(Bukkit.getConsoleSender())) {
            sender.sendMessage(ChatColor.GREEN + "正在重载配置文件...");
        }
        logManager.info(Streams.INIT, "正在重载配置文件...");

        // Do the actual reload.
        ConfigManager.cleanup();
        // (Magic/TODO)
        final WorldDataManager worldDataManager = (WorldDataManager) NCPAPIProvider.getNoCheatPlusAPI().getWorldDataManager();
        ConfigManager.init(access, worldDataManager);
        worldDataManager.removeCachedConfigs();
        if (logManager instanceof INotifyReload) { // TODO: This is a band-aid.
            ((INotifyReload) logManager).onReload();
        }

        // Remove all cached configs from data.
        NCPAPIProvider.getNoCheatPlusAPI().getPlayerDataManager().removeCachedConfigs();

        // Reset debug flags to default (temp, heavy).
        DataManager.restoreDefaultDebugFlags();

        // Tell the registered listeners to adapt to new config, first sort them (!).
        Collections.sort(notifyReload, Order.cmpSetupOrder);
        for (final INotifyReload component : notifyReload){
            component.onReload();
        }

        // Say to the other plugins that we've reloaded the configuration.
        Bukkit.getPluginManager().callEvent(new NCPReloadEvent());

        // Log reloading done.
        if (!sender.equals(Bukkit.getConsoleSender())) {
            sender.sendMessage(ChatColor.GREEN + "配置文件已重载.");
        }
        logManager.info(Streams.INIT, "配置文件已重载.");
        logManager.info(Streams.DEFAULT_FILE, StringUtil.join(VersionCommand.getVersionInfo(), "\n")); // Queued (!).
    }

}
