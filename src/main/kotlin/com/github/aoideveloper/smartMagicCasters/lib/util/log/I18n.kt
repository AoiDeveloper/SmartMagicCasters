package com.github.aoideveloper.smartMagicCasters.lib.util.log

import java.text.MessageFormat
import java.util.MissingResourceException
import java.util.ResourceBundle

object I18n {
  private val bundle: ResourceBundle = ResourceBundle.getBundle("messages")

  fun msg(key: String, vararg args: Any?): String {
    return try {
      val raw = bundle.getString(key)
      MessageFormat.format(raw, *args)
    } catch (e: MissingResourceException) {
      "!!!$key!!!"
    }
  }
}
