package io.github.shadowrz.projectkafka.tests.utils.callback

/** Errors if being called. */
class AssertNeverCalled : () -> Unit {
    override operator fun invoke() {
        error("Callback should not be called but is being called")
    }
}

class AssertNeverCalledWithParam<T> : (T) -> Unit {
    override operator fun invoke(param: T) {
        error("Callback should not be called but is being called")
    }
}
