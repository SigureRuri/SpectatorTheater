package com.github.sigureruri.spectatortheater.spectator

import com.github.sigureruri.spectatortheater.SpectatorTheater
import org.bukkit.GameMode
import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.util.*

class STSpectatorManager : SpectatorManager {

    private val spectators = mutableMapOf<UUID, SpectatorInformation>()

    private val endHistory = mutableMapOf<UUID, LocalDateTime>()

    override fun start(player: Player): SpectatorManager.StartResult {
        val uuid = player.uniqueId

        if (spectators.contains(uuid))
            return SpectatorManager.StartResult.ALREADY_STARTED
        if (!SpectatorTheater.stConfig.ENABLED_WORLDS.contains(player.world))
            return SpectatorManager.StartResult.OUT_OF_ENABLED_WORLDS
        if (isInCoolTime(uuid))
            return SpectatorManager.StartResult.IN_COOL_TIME

        // ゲーム内ゲームモードの変更をspectatorsへのputより後に行うと、
        // PlayerGameModeChangeEventと干渉するため先にGameMode.SPECTATORへ変更する
        val gameModeBeforeChange = player.gameMode
        player.gameMode = GameMode.SPECTATOR

        val information = SpectatorInformation(
            player,
            LocalDateTime.now(),
            player.location.clone(),
            gameModeBeforeChange
        )
        spectators[uuid] = information

        return SpectatorManager.StartResult.SUCCESS
    }

    override fun end(player: Player): SpectatorManager.EndResult {
        val uuid = player.uniqueId

        if (!spectators.contains(uuid)) return SpectatorManager.EndResult.NOT_STARTED_YET

        val information = spectators[uuid]!!

        // ゲーム内ゲームモードの変更をspectatorsからのremoveより先に行うと、
        // PlayerGameModeChangeEventと干渉するため先にremoveする
        spectators.remove(uuid)
        endHistory[uuid] = LocalDateTime.now()

        player.teleport(information.locationBeforeStart)
        player.gameMode = information.gameModeBeforeStart

        return SpectatorManager.EndResult.SUCCESS
    }

    override fun getSpectatorInformation(player: Player): SpectatorInformation? {
        return spectators[player.uniqueId]
    }

    override fun isSpectator(player: Player): Boolean {
        return spectators.contains(player.uniqueId)
    }

    override fun getAllSpectatorInformation(): List<SpectatorInformation> {
        return spectators.values.toList()
    }

    private fun isInCoolTime(uuid: UUID): Boolean {
        val endedAt = endHistory[uuid] ?: return false
        val now = LocalDateTime.now()
        return now < endedAt.plusSeconds(SpectatorTheater.stConfig.COOL_TIME)
    }

}