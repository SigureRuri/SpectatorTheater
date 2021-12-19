package com.github.sigureruri.spectatortheater.task

import com.github.sigureruri.spectatortheater.SpectatorTheater
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable
import java.time.LocalDateTime

class EverySecondTask : BukkitRunnable() {

    override fun run() {
        val spectatorManager = SpectatorTheater.spectatorManager
        spectatorManager.getAllSpectatorInformation().forEach {
            val player = it.player
            val startedAt = it.startedAt
            val now = LocalDateTime.now()

            if (now > startedAt.plusSeconds(SpectatorTheater.stConfig.TIME_LIMIT)) {
                spectatorManager.end(player)
                player.sendMessage("${ChatColor.RED}${SpectatorTheater.stConfig.TIME_LIMIT}秒が経過したため、時間制限によりスペクテイターモードを終了しました。")
            }
        }
    }

}