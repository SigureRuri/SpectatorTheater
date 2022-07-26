package com.github.sigureruri.spectatortheater.config

import org.bukkit.configuration.file.FileConfiguration

class SpectatorTheaterConfig(config: FileConfiguration) {

    val ENABLED_WORLDS = config.getStringList("enabled-worlds").mapNotNull { it.lowercase() }

    val COOL_TIME = config.getLong("cool-time")

    val TIME_LIMIT = config.getLong("time-limit")

}