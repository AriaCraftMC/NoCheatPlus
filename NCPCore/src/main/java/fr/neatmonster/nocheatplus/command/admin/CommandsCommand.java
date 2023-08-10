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

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import fr.neatmonster.nocheatplus.command.BaseCommand;
import fr.neatmonster.nocheatplus.permissions.Permissions;
import fr.neatmonster.nocheatplus.utilities.StringUtil;

/**
 * This command just shows a list of all commands.
 * @author mc_dev
 *
 */
public class CommandsCommand extends BaseCommand {

    final String[] moreCommands = new String[]{
            // TODO: Mmmmh, spaghetti.
            ChatColor.GOLD + "" + ChatColor.BOLD + "可用命令:",
            ChatColor.GRAY + "" + ChatColor.BOLD + "• " + ChatColor.RED + "" + ChatColor.ITALIC + "/nocheatplus log" + ChatColor.GRAY + " - 显示计数器汇总.",
            ChatColor.GRAY + "" + ChatColor.BOLD + "• " + ChatColor.RED + "" + ChatColor.ITALIC + "/nocheatplus reset" + ChatColor.GRAY + " - 重置计数器.",
            ChatColor.GRAY + "" + ChatColor.BOLD + "• " + ChatColor.RED + "" + ChatColor.ITALIC + "/nocheatplus exemptions (玩家)" + ChatColor.GRAY + " - 列出绕过作弊的玩家.",
            ChatColor.GRAY + "" + ChatColor.BOLD + "• " + ChatColor.RED + "" + ChatColor.ITALIC + "/nocheatplus exempt (玩家) (检测类型)" + ChatColor.GRAY + " - 使玩家绕过检测(*代表全部). ",
            ChatColor.GRAY + "" + ChatColor.BOLD + "• " + ChatColor.RED + "" + ChatColor.ITALIC + "/nocheatplus unexempt (玩家) (检测类型)" + ChatColor.GRAY + " - 取消玩家的检测绕过",
    };
    
    final String allCommands;

    public CommandsCommand(JavaPlugin plugin) {
        super(plugin, "commands", Permissions.COMMAND_COMMANDS, new String[]{"cmds"});
        for (int i = 0; i < moreCommands.length; i++){
            moreCommands[i] = moreCommands[i].replace("<command>", "ncp");
        }
        String all = TAG + ChatColor.GOLD + "All commands info:\n";
        Command cmd = plugin.getCommand("nocheatplus");
        if (cmd != null){
            all += cmd.getUsage().replace("<command>", "ncp");
        }
        all += StringUtil.join(Arrays.asList(moreCommands), "\n");
        allCommands = all;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(moreCommands);
        return true;
    }

}
