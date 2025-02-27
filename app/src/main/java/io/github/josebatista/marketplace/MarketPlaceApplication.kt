package io.github.josebatista.marketplace

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main Application class for the Marketplace app.
 *
 * Annotated with [HiltAndroidApp], this class triggers Hilt's code generation and
 * serves as the base for dependency injection throughout the application.
 * It initializes Hilt's dependency container, making it available for injection in
 * activities, fragments, ViewModels, and other Android components.
 */
@HiltAndroidApp
public class MarketPlaceApplication : Application()
