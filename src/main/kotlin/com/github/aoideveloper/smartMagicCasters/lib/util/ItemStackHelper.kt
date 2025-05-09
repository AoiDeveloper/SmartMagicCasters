package com.github.aoideveloper.smartMagicCasters.lib.util

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

inline fun <reified M : ItemMeta> ItemStack.editMeta(crossinline edit: M.() -> Unit): Boolean {
  return this.editMeta(M::class.java) { (it as M).edit() }
}
