package com.github.sigureruri.spectatortheater

import com.github.sigureruri.spectatortheater.command.SpectatorTheaterCommand
import com.github.sigureruri.spectatortheater.config.SpectatorTheaterConfig
import com.github.sigureruri.spectatortheater.listener.SpectatorTheaterListener
import com.github.sigureruri.spectatortheater.spectator.STSpectatorManager
import com.github.sigureruri.spectatortheater.spectator.SpectatorManager
import com.github.sigureruri.spectatortheater.task.EverySecondTask
import org.bukkit.plugin.java.JavaPlugin

class SpectatorTheater : JavaPlugin() {

    override fun onEnable() {
        instance = this

        saveDefaultConfig()
        stConfig = SpectatorTheaterConfig(config)

        spectatorManager = STSpectatorManager()

        server.pluginManager.registerEvents(SpectatorTheaterListener(), this)

        getCommand("spectatortheater").executor = SpectatorTheaterCommand()

        EverySecondTask().runTaskTimer(this, 20, 20)
    }

    companion object {

        lateinit var stConfig: SpectatorTheaterConfig
            private set

        lateinit var spectatorManager: SpectatorManager
            private set

        lateinit var instance: SpectatorTheater
            private set

    }

}