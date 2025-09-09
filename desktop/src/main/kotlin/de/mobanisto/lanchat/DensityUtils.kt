package de.mobanisto.lanchat

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import java.awt.Dimension
import java.awt.GraphicsConfiguration
import java.awt.GraphicsEnvironment

object DensityUtils {
    // See androidx.compose.ui.window.LayoutConfiguration
    private val GlobalDensity
        get() = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .defaultScreenDevice
            .defaultConfiguration
            .density

    // See androidx.compose.ui.window.LayoutConfiguration
    private val GraphicsConfiguration.density: Float
        get() = defaultTransform.scaleX.toFloat()

    /**
     * Compute an AWT Dimension for the given width and height in dp units, taking
     * into account the LocalDensity as well as the global density as detected by the
     * local graphics environment.
     *
     * On macOS hidpi devices, the global density is usually something like 2 while on Linux
     * it is usually 1 independent of the actual density. The global density is taken into
     * account by AWT itself, so we need to remove that factor from the equation, otherwise
     * it will be accounted for twice resulting in windows that are bigger than expected.
     */
    @Composable
    fun DensityDimension(width: Int, height: Int): Dimension {
        return DensityDimension(width, height, LocalDensity.current.density)
    }

    /**
     * Compute an AWT Dimension for the given width and height in dp units, taking
     * into account the optional UI scale factor from the shared settings as well as the global
     * density as detected by the local graphics environment.
     *
     * On macOS hidpi devices, the global density is usually something like 2 while on Linux
     * it is usually 1 independent of the actual density. The global density is taken into
     * account by AWT itself, so we need to remove that factor from the equation, otherwise
     * it will be accounted for twice resulting in windows that are bigger than expected.
     */
    @Composable
    fun DensityDimension(width: Int, height: Int, uiScale: Float?): Dimension {
        return DensityDimension(width, height, uiScale ?: GlobalDensity)
    }

    @Composable
    private fun DensityDimension(width: Int, height: Int, uiScale: Float): Dimension {
        with(Density(uiScale / GlobalDensity)) {
            return Dimension(width.dp.roundToPx(), height.dp.roundToPx())
        }
    }
}
