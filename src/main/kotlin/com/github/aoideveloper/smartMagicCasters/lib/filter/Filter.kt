package com.github.aoideveloper.smartMagicCasters.lib.filter

fun interface Filter<T, R> {
  fun filter(): Collection<R>
}
