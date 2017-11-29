package com.wl.ireadbook.injection


import javax.inject.Scope

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * [ConfigPersistentComponent]
 */
@Scope
@kotlin.annotation.Retention
annotation class ConfigPersistent
