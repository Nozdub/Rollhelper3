package com.example.rollhelper3.ui.utils

import com.example.rollhelper3.R

fun getAnimationFramesForDiceResourceIds(diceType: String): List<Int> {
    return when (diceType) {
        "d4" -> listOf(
            R.drawable.d4_frame_01,
            R.drawable.d4_frame_02,
            R.drawable.d4_frame_03,
            R.drawable.d4_frame_04,
            R.drawable.d4_frame_05,
            R.drawable.d4_frame_06,
            R.drawable.d4_frame_07,
            R.drawable.d4_frame_08
        )
        "d6" -> listOf(
            R.drawable.d6_frame_01,
            R.drawable.d6_frame_02,
            R.drawable.d6_frame_03,
            R.drawable.d6_frame_04,
            R.drawable.d6_frame_05,
            R.drawable.d6_frame_06,
            R.drawable.d6_frame_07,
            R.drawable.d6_frame_08
        )
        "d8" -> listOf(
            R.drawable.d8_frame_01,
            R.drawable.d8_frame_02,
            R.drawable.d8_frame_03,
            R.drawable.d8_frame_04,
            R.drawable.d8_frame_05,
            R.drawable.d8_frame_06,
            R.drawable.d8_frame_07,
            R.drawable.d8_frame_08,
        )
        "d10" -> listOf(
            R.drawable.d10_frame_01,
            R.drawable.d10_frame_02,
            R.drawable.d10_frame_03,
            R.drawable.d10_frame_04,
            R.drawable.d10_frame_05,
            R.drawable.d10_frame_06,
            R.drawable.d10_frame_07,
            R.drawable.d10_frame_08,
        )
        "d12" -> listOf(
            R.drawable.d12_frame_01,
            R.drawable.d12_frame_02,
            R.drawable.d12_frame_03,
            R.drawable.d12_frame_04,
            R.drawable.d12_frame_05,
            R.drawable.d12_frame_06,
            R.drawable.d12_frame_07,
            R.drawable.d12_frame_08,
        )
        "d20" -> listOf(
            R.drawable.d20_frame_01,
            R.drawable.d20_frame_02,
            R.drawable.d20_frame_03,
            R.drawable.d20_frame_04,
            R.drawable.d20_frame_05,
            R.drawable.d20_frame_06,
            R.drawable.d20_frame_07,
            R.drawable.d20_frame_08,
        )
        else -> emptyList()
    }
}

fun getStillImageForResultResourceId(diceType: String, result: Int): Int {
    return when (diceType) {
        "d4" -> when (result) {
            1 -> R.drawable.d4_still_01
            2 -> R.drawable.d4_still_02
            3 -> R.drawable.d4_still_03
            4 -> R.drawable.d4_still_04
            else -> R.drawable.d4_still_01
        }
        "d6" -> when (result) {
            1 -> R.drawable.d6_still_01
            2 -> R.drawable.d6_still_02
            3 -> R.drawable.d6_still_03
            4 -> R.drawable.d6_still_04
            5 -> R.drawable.d6_still_05
            6 -> R.drawable.d6_still_06
            else -> R.drawable.d6_still_01
        }
        "d8" -> when (result) {
            1 -> R.drawable.d8_still_01
            2 -> R.drawable.d8_still_02
            3 -> R.drawable.d8_still_03
            4 -> R.drawable.d8_still_04
            5 -> R.drawable.d8_still_05
            6 -> R.drawable.d8_still_06
            7 -> R.drawable.d8_still_07
            8 -> R.drawable.d8_still_08
            else -> R.drawable.d8_still_01
        }
        "d10" -> when (result) {
            1 -> R.drawable.d10_still_01
            2 -> R.drawable.d10_still_02
            3 -> R.drawable.d10_still_03
            4 -> R.drawable.d10_still_04
            5 -> R.drawable.d10_still_05
            6 -> R.drawable.d10_still_06
            7 -> R.drawable.d10_still_07
            8 -> R.drawable.d10_still_08
            9 -> R.drawable.d10_still_09
            10 -> R.drawable.d10_still_10
            else -> R.drawable.d10_still_01
        }
        "d12" -> when (result) {
            1 -> R.drawable.d12_still_01
            2 -> R.drawable.d12_still_02
            3 -> R.drawable.d12_still_03
            4 -> R.drawable.d12_still_04
            5 -> R.drawable.d12_still_05
            6 -> R.drawable.d12_still_06
            7 -> R.drawable.d12_still_07
            8 -> R.drawable.d12_still_08
            9 -> R.drawable.d12_still_09
            10 -> R.drawable.d12_still_10
            11 -> R.drawable.d12_still_11
            12 -> R.drawable.d12_still_12
            else -> R.drawable.d12_still_01
        }
        "d20" -> when (result) {
            1 -> R.drawable.d20_still_01
            2 -> R.drawable.d20_still_02
            3 -> R.drawable.d20_still_03
            4 -> R.drawable.d20_still_04
            5 -> R.drawable.d20_still_05
            6 -> R.drawable.d20_still_06
            7 -> R.drawable.d20_still_07
            8 -> R.drawable.d20_still_08
            9 -> R.drawable.d20_still_09
            10 -> R.drawable.d20_still_10
            11 -> R.drawable.d20_still_11
            12 -> R.drawable.d20_still_12
            13 -> R.drawable.d20_still_13
            14 -> R.drawable.d20_still_14
            15 -> R.drawable.d20_still_15
            16 -> R.drawable.d20_still_16
            17 -> R.drawable.d20_still_17
            18 -> R.drawable.d20_still_18
            19 -> R.drawable.d20_still_19
            20 -> R.drawable.d20_still_20
            else -> R.drawable.d20_still_01
        }
        else -> R.drawable.d4_still_01
    }
}

fun maxDiceValue(diceType: String): Int {
    return when (diceType) {
        "d4" -> 4
        "d6" -> 6
        "d8" -> 8
        "d10" -> 10
        "d12" -> 12
        "d20" -> 20
        else -> 6 // Default fallback
    }
}
