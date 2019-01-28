package com.papaya.boomerang

import org.apache.log4j.Level
import org.apache.log4j.Logger


object FileBoomerangLogger {

    val logger = Logger.getLogger("File-Boomerang")

        fun log(log : Boolean) {
            if (!log)
                logger.level = Level.OFF
        }

}