package io.github.shadowrz.projectkafka.libraries.di.annotations

import dev.zacsweers.metro.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Qualifier
annotation class Dispatcher {
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    @Qualifier
    annotation class Main

    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    @Qualifier
    annotation class IO

    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    @Qualifier
    annotation class Compute
}
