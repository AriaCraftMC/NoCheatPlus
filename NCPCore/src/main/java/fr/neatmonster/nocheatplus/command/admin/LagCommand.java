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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.neatmonster.nocheatplus.command.BaseCommand;
import fr.neatmonster.nocheatplus.permissions.Permissions;
import fr.neatmonster.nocheatplus.utilities.StringUtil;
import fr.neatmonster.nocheatplus.utilities.TickTask;

public class LagCommand extends BaseCommand {

    public LagCommand(JavaPlugin plugin) {
        super(plugin, "lag", Permissions.COMMAND_LAG);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        final String cGO, cG, cR, bO;
        if (sender instanceof Player) {
            cGO = ChatColor.GOLD.toString();
            cR = ChatColor.RED.toString();
            cG = ChatColor.GRAY.toString();
            bO = ChatColor.BOLD.toString();
        }
        else cGO = cR = cG = bO = "";

        StringBuilder builder = new StringBuilder(300);

        // Lag spikes.
        long[] spikeDurations = TickTask.getLagSpikeDurations();
        int[] spikes = TickTask.getLagSpikes();
        builder.append(cR +""+ bO + "»延迟高峰«\n");

        if (spikes[0] == 0){
            builder.append(cG +"在过去的60分钟内没有延迟高峰.");
        }
        else if (spikes[0] > 0){

            builder.append(cG + "总峰数: " + cGO +""+ spikes[0] + cG + "\n这些是"+cGO+ " 60 "+cG+"分钟内峰值大于 " + cGO +""+ spikeDurations[0] + cG + " ms的延迟高峰.");
            builder.append("\n" + "结果:");

            for (int i = 0; i < spikeDurations.length; i++){
                if (i < spikeDurations.length - 1 && spikes[i] == spikes[i + 1]){
                    // Ignore these, get printed later.
                    continue;
                }
                if (spikes[i] == 0){
                    continue; // Could be break.
                }
                else if (i < spikeDurations.length - 1){
                    builder.append(cG + "\n• " + cGO +""+ (spikes[i] - spikes[i + 1]) + cG + " 次 " + cGO +""+ cGO +""+ spikeDurations[i] + cG + " ms -> " + cGO +""+ spikeDurations[i + 1] + cG + ". ");
                }
                else{
                    builder.append(cG + "\n• " + cGO +""+ spikes[i] + cG + " 次 " + cGO +""+ cGO +""+ spikeDurations[i] +"ms"+ cG + ".");
                }
            }
        }
        builder.append("\n");
        // TPS lag.
        long max = 50L * (1L + TickTask.lagMaxTicks) * TickTask.lagMaxTicks;
        long medium = 50L * TickTask.lagMaxTicks;
        long second = 1200L;
        builder.append(cR +""+ bO + "»TPS 延迟«" + cG + "\n[百分比][追踪时间], 0% = 20 TPS");
        for (long ms : new long[]{second, medium, max}){
            double lag = TickTask.getLag(ms, true);
            int p = Math.max(0, (int) ((lag - 1.0) * 100.0));
            builder.append(cG + "\n• 在过去 " + cGO +""+ StringUtil.fdec1.format((double) ms / 1200.0) + cG + " 秒有 " + cGO + p + "%" + cG + " 的 TPS 延迟.");
        }
        // Send message.
        sender.sendMessage(builder.toString());
        return true;
    }

}
