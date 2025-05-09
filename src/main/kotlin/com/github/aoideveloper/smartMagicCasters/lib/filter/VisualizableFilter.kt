package com.github.aoideveloper.smartMagicCasters.lib.filter

import org.bukkit.entity.Player

interface VisualizableFilter<T, R>: Filter<T, R> {
    fun visualize() : (Array<Player>) -> Unit
}