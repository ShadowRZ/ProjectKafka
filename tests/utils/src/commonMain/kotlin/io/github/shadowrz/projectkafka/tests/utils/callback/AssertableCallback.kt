package io.github.shadowrz.projectkafka.tests.utils.callback

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.should

/** A callback that are assertable. */
class AssertableCallback : () -> Unit {
    internal var counter = 0

    override operator fun invoke() {
        counter++
    }
}

/** A callback with a single paramter that are assertable. */
class AssertableCallbackWithParam<T> : (T) -> Unit {
    internal val called = mutableListOf<T>()

    internal val counter
        get() = called.size

    override operator fun invoke(param: T) {
        called.add(param)
    }
}

fun called(times: Int = 1) =
    Matcher<AssertableCallback> { callback ->
        MatcherResult(
            callback.counter == times,
            { "Callback should be called exactly $times times, but called ${callback.counter} times" },
            { "Callback should not be called exactly $times times" },
        )
    }

fun <T> calledTimes(times: Int = 1) =
    Matcher<AssertableCallbackWithParam<T>> { callback ->
        MatcherResult(
            callback.counter == times,
            { "Callback should be called exactly $times times, but called ${callback.counter} times" },
            { "Callback should not be called exactly $times times" },
        )
    }

fun neverCalled() =
    Matcher<AssertableCallback> { callback ->
        MatcherResult(
            callback.counter == 0,
            { "Callback should be never called, but called ${callback.counter} times" },
            { "Callback should be called" },
        )
    }

fun AssertableCallback.shouldBeCalled(times: Int = 1): AssertableCallback {
    this should called(times)
    return this
}

fun AssertableCallback.shouldNeverBeCalled(): AssertableCallback {
    this should neverCalled()
    return this
}

fun <T> AssertableCallbackWithParam<T>.shouldBeCalledOnceWith(param: T): AssertableCallbackWithParam<T> {
    this should calledTimes(1)
    this.called shouldContainExactly listOf(param)
    return this
}
