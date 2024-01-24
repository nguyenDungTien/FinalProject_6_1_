package com.example.finalproject_6_1.utils

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import com.example.finalproject_6_1.utils.Typefaces.Value.SERIF
import com.example.finalproject_6_1.utils.Typefaces.Value.SERIF_BOLD
import com.example.finalproject_6_1.utils.Typefaces.Value.SERIF_BOLD_ITALIC
import com.example.finalproject_6_1.utils.Typefaces.Value.SERIF_ITALIC
import java.util.*

object Typefaces {

    object Value{
        const val SERIF = "SERIF"
        const val SERIF_BOLD = "SERIF_BOLD"
        const val SERIF_ITALIC = "SERIF_ITALIC"
        const val SERIF_BOLD_ITALIC = "SERIF_BOLD_ITALIC"
    }

    private const val TAG = "Typefaces"
    private val CACHE = Hashtable<String, Typeface>()
    operator fun get(context: Context, assetPath: String?): Typeface? {
        val t = Typeface.createFromAsset(context.assets, assetPath)
        return assetPath?.let { cachedTypeface(it, t) }
    }
    private fun cachedTypeface(assetPath: String, t: Typeface): Typeface? {
        synchronized(CACHE) {
            if (!CACHE.containsKey(assetPath)) {
                try {
                    CACHE[assetPath] = t
                } catch (e: Exception) {
                    Log.e(TAG, "Could not get setTypeface '$assetPath", e)
                    return null
                }
            }
            return CACHE[assetPath]
        }
    }
    fun getTypefaceRobotoBold(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/Roboto-Bold.ttf"] }
    }
    fun getTypefaceRobotoItalic(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/Roboto-Italic.ttf"] }
    }
    fun getTypefaceRobotoNormal(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/Roboto-Regular.ttf"] }
    }
    fun getTypefaceRobotoMedium(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/roboto_medium.ttf"] }
    }
    fun getTypefaceSFProTextBold(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/SF-Pro-Text-Bold.otf"] }
    }
    fun getTypefaceSFProTextBoldItalic(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/SF-Pro-Text-BoldItalic.otf"] }
    }
    fun getTypefaceSFProTextMedidum(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/SF-Pro-Text-Medium.ttf"] }
    }
    fun getTypefaceSFProTextMediumItalic(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/SF-Pro-Text-MediumItalic.otf"] }
    }
    fun getTypefaceSFProTextRegular(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/SF-Pro-Text-Regular.ttf"] }
    }
    fun getTypefaceSFProTextRegularItalic(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/SF-Pro-Text-RegularItalic.otf"] }
    }
    fun getTypefaceSFProTextSemibold(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/SF-Pro-Text-Semibold.otf"] }
    }
    fun getTypefaceSFProTextSemiboldItalic(context: Context?): Typeface? {
        return context?.let { Typefaces[it, "fonts/SF-Pro-Text-SemiboldItalic.otf"] }
    }

    fun getConfigTypeface(type: String?): Typeface{
        when(type){
            SERIF ->{
                return Typeface.SERIF
            }
            SERIF_BOLD ->{
                return Typeface.create(Typeface.SERIF, Typeface.BOLD)
            }
            SERIF_ITALIC ->{
                return Typeface.create(Typeface.SERIF, Typeface.ITALIC)
            }
            SERIF_BOLD_ITALIC ->{
                return Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)
            }
            else ->{
                return Typeface.SERIF
            }
        }
    }
}