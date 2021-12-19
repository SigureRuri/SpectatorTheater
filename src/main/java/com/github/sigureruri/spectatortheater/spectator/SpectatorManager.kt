package com.github.sigureruri.spectatortheater.spectator

import org.bukkit.entity.Player

interface SpectatorManager {

    /**
     * スペクテイターモードを開始する
     *
     * @param player 対象のプレイヤー
     */
    fun start(player: Player): StartResult

    /**
     * スペクテイターモードを終了する
     *
     * @param player 対象のプレイヤー
     */
    fun end(player: Player): EndResult

    /**
     * プレイヤーのスペクテイターモードの情報を取得する
     *
     * @param player 対象のプレイヤー
     * @return スペクテイターモードの情報。プレイヤーがスペクテイターモードでない場合は`null`
     */
    fun getSpectatorInformation(player: Player): SpectatorInformation?

    /**
     * プレイヤーがスペクテイターモードであるかどうかを取得する
     *
     * @param player 対象のプレイヤー
     * @return プレイヤーがスペクテイターモードであれば`true`、それ以外であれば`false`
     */
    fun isSpectator(player: Player): Boolean

    /**
     * 全てのスペクテイターモード状態のプレイヤーの情報を取得する
     *
     * @return 全てのスペクテイターモード状態のプレイヤーの情報。プレイヤーが存在しない場合は空のリストが返される。
     */
    fun getAllSpectatorInformation(): List<SpectatorInformation>

    enum class StartResult {
        IN_COOL_TIME,
        OUT_OF_ENABLED_WORLDS,
        ALREADY_STARTED,
        SUCCESS;
    }

    enum class EndResult {
        NOT_STARTED_YET,
        SUCCESS
    }

}