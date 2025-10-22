package io.github.shadowrz.projectkafka.libraries.di.annotations

import dev.zacsweers.metro.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class IOScope {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    annotation class SystemScoped
}
