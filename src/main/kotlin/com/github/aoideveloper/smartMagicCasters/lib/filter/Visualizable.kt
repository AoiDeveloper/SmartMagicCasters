package com.github.aoideveloper.smartMagicCasters.lib.filter

import org.bukkit.entity.Player

interface Visualizable {
    fun visualize() : (Array<Player>) -> Unit
}
