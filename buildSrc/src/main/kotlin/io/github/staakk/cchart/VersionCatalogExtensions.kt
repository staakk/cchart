package io.github.staakk.cchart

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

internal val Project.libs: VersionCatalog
    get() = project.extensions.getByType(VersionCatalogsExtension::class.java).named("libs")