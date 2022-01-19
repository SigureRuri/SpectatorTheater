package com.github.sigureruri.spectatortheater.command

import com.github.sigureruri.spectatortheater.SpectatorTheater
import com.github.sigureruri.spectatortheater.spectator.SpectatorManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SpectatorTheaterCommand : TabExecutor {

    private val subCommands = mapOf<String, (Player) -> Unit>(
        "start" to { start(it) },
        "end" to { end(it) },
        "list" to { list(it) }
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}spectatortheaterコマンドはプレイヤーのみ使用可能です。")
            return true
        }

        val subCommand = args.getOrNull(0) ?: return false
        val subCommandEffect = subCommands[subCommand] ?: return false

        if (!sender.hasPermission("spectatortheater.command.spectatortheater.${subCommand}")) {
            sender.sendMessage("You don't have permission to execute this command.")
            return true
        }

        subCommandEffect(sender)

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        val result = mutableListOf<String>()
        val inputSubCommand = args.getOrElse(0) { "" }

        subCommands.keys.forEach { subCommand ->
            if (!sender.hasPermission("spectatortheater.command.spectatortheater.${subCommand}")) return@forEach

            if (subCommand.startsWith(inputSubCommand)) result.add(subCommand)
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

    private fun list(player: Player) {
        val allOfInformation = SpectatorTheater.spectatorManager.getAllSpectatorInformation()
        if (allOfInformation.isEmpty()) {
            player.sendMessage("${ChatColor.YELLOW}スペクテイターモードのプレイヤーは存在しません。")
        } else {
            player.sendMessage("${ChatColor.YELLOW}${allOfInformation.size}人のプレイヤーがスペクテイターモードです:")
            player.sendMessage(allOfInformation.joinToString("${ChatColor.GRAY},${ChatColor.RESET}") { it.player.name })
        }
    }

}