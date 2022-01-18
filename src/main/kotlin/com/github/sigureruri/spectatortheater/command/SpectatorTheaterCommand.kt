package com.github.sigureruri.spectatortheater.command

import com.github.sigureruri.spectatortheater.SpectatorTheater
import com.github.sigureruri.spectatortheater.spectator.SpectatorManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SpectatorTheaterCommand : TabExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}spectatortheaterコマンドはプレイヤーのみ使用可能です。")
            return true
        }

        val subCommand = args.getOrNull(0) ?: return false
        when (subCommand) {
            "start" -> {
                start(sender)
            }
            "end" -> {
                end(sender)
            }
            else -> return false
        }

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        val result = mutableListOf<String>()
        val subCommand = args.getOrElse(0) { "" }

        listOf("start", "end").forEach {
            if (it.startsWith(subCommand)) result.add(it)
        }

        return result
    }

    private fun start(player: Player) {
        val message = when (SpectatorTheater.spectatorManager.start(player)) {
            SpectatorManager.StartResult.IN_COOL_TIME -> "${ChatColor.RED}クールタイム中であるため、スペクテイターモードを開始できません。"
            SpectatorManager.StartResult.OUT_OF_ENABLED_WORLDS -> "${ChatColor.RED}このワールドではスペクテイターモードが有効でありません。"
            SpectatorManager.StartResult.ALREADY_STARTED -> "${ChatColor.RED}すでにスペクテイターモードを開始しています。"
            SpectatorManager.StartResult.SUCCESS -> "${ChatColor.GREEN}スペクテイターモードを開始しました。(有効時間: ${ChatColor.GREEN}${ChatColor.BOLD}${SpectatorTheater.stConfig.TIME_LIMIT}${ChatColor.GREEN}秒)"
        }
        player.sendMessage(message)
    }

    private fun end(player: Player) {
        val message = when (SpectatorTheater.spectatorManager.end(player)) {
            SpectatorManager.EndResult.NOT_STARTED_YET -> "${ChatColor.RED}スペクテイターモードを開始していません。"
            SpectatorManager.EndResult.SUCCESS -> "${ChatColor.GREEN}スペクテイターモードを終了しました。"
        }
        player.sendMessage(message)
    }

}