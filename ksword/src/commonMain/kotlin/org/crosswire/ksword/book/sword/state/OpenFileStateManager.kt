package org.crosswire.ksword.book.sword.state

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Clock
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.BlockType
import kotlin.time.ExperimentalTime

/** Caches one open-file state per book; reference-counted and lock-guarded so a handle is never closed mid-read. */
internal object OpenFileStateManager {

    private val lock = SynchronizedObject()

    private val metaToState = mutableMapOf<BookMetaData, AbstractOpenFileState>()

    private val activeCount = mutableMapOf<BookMetaData, Int>()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var evictionJob: Job? = null

    // Keep an idle (unused) state open this long so sequential reads can reuse it.
    private const val TIME_TO_RELEASE_MILLIS: Long = 10_000

    fun getZVerseBackendState(metadata: BookMetaData, blockType: BlockType): ZVerseBackendState {
        return getOrCreateState(metadata) { ZVerseBackendState(metadata, blockType) }
    }

    fun getRawLDBackendState(metadata: BookMetaData, dataSize: Int): RawLDBackendState {
        return getOrCreateState(metadata) { RawLDBackendState(metadata, dataSize) }
    }

    fun getZLDBackendState(metadata: BookMetaData): ZLDBackendState {
        return getOrCreateState(metadata) { ZLDBackendState(metadata) }
    }

    private inline fun <reified T : AbstractOpenFileState> getOrCreateState(
        metadata: BookMetaData,
        factory: () -> T
    ): T {
        return synchronized(lock) {
            var state = metaToState[metadata] as? T
            if (state == null) {
                state = factory()
                metaToState[metadata] = state
            }
            state.lastAccess = timeMillis()
            activeCount[metadata] = (activeCount[metadata] ?: 0) + 1
            state
        }
    }

    fun release(fileState: OpenFileState) {
        synchronized(lock) {
            val metadata = fileState.bookMetaData
            if (metadata != null) {
                val remaining = (activeCount[metadata] ?: 1) - 1
                if (remaining <= 0) activeCount.remove(metadata) else activeCount[metadata] = remaining
            }
            fileState.lastAccess = timeMillis()

            evictionJob?.cancel()
            evictionJob = scope.launch {
                delay(TIME_TO_RELEASE_MILLIS)
                evictIdleStates()
            }
        }
    }

    private fun evictIdleStates() {
        synchronized(lock) {
            val now = timeMillis()
            val iterator = metaToState.entries.iterator()
            while (iterator.hasNext()) {
                val (metadata, state) = iterator.next()
                val inUse = (activeCount[metadata] ?: 0) > 0
                if (!inUse && now - state.lastAccess >= TIME_TO_RELEASE_MILLIS) {
                    state.releaseResources()
                    iterator.remove()
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun timeMillis() = Clock.System.now().toEpochMilliseconds()
}