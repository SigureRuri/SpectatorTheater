package com.github.sigureruri.spectatortheater.listener

import com.github.sigureruri.spectatortheater.SpectatorTheater
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.PluginDisableEvent

class SpectatorTheaterListener : Listener {

    private val spectatorManager = SpectatorTheater.spectatorManager

    @EventHandler(priority = EventPriority.MONITOR)
    fun onChangeWorld(event: PlayerChangedWorldEvent) {
        val player = event.player
        if (spectatorManager.isSpectator(player)) {
            spectatorManager.end(player)
            player.sendMessage("${ChatColor.RED}ワールド間の移動を行ったため、スペクテイターモードを終了しました。")
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onChangeGameMode(event: PlayerGameModeChangeEvent) {
        val player = event.player
        if (spectatorManager.isSpectator(player)) {
            spectatorManager.end(player)
            player.sendMessage("${ChatColor.RED}ゲームモードが変更されたため、スペクテイターモードを終了しました。")
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player

        if (spectatorManager.isSpectator(player)) {
            spectatorManager.end(player)
            player.sendMessage("${ChatColor.RED}ゲームを退出したため、スペクテイターモードを終了しました。")
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDisable(event: PluginDisableEvent) {
        val plugin = event.plugin
        if (plugin.name != SpectatorTheater.instance.name) return

        spectatorManager.getAllSpectatorInformation().forEach {
            val player = it.player
            spectatorManager.end(player)
            player.sendMessage("${ChatColor.RED}SpectatorTheaterが無効化されたため、スペクテイターモードを終了しました。")
        }
    }

}