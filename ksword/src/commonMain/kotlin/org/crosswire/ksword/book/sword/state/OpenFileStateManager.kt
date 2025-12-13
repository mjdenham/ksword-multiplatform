package org.crosswire.ksword.book.sword.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Clock
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.BlockType
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.ExperimentalTime

internal object OpenFileStateManager {

    private val metaToState = mutableMapOf<BookMetaData, AbstractOpenFileState>()

    private var releaseResourceJob: Job? = null

    // Allow 30 seconds for BackendState to be reused before it is released
    private const val TIME_TO_RELEASE_MILLIS: Long = 1000 * 10

    fun getZVerseBackendState(metadata: BookMetaData, blockType: BlockType): ZVerseBackendState {
        return getOrCreateState(metadata) { ZVerseBackendState(metadata, blockType) }
    }

    fun getRawLDBackendState(metadata: BookMetaData, dataSize: Int): RawLDBackendState {
        return getOrCreateState(metadata) { RawLDBackendState(metadata, dataSize) }
    }

    private inline fun <reified T : AbstractOpenFileState> getOrCreateState(
        metadata: BookMetaData,
        factory: () -> T
    ): T {
        var state = metaToState[metadata] as? T
        if (state == null) {
            state = factory()
            metaToState[metadata] = state
        }
        state.lastAccess = timeMillis()
        return state
    }

    fun release(fileState: OpenFileState) {
        releaseResourceJob?.cancel()
        val releaseRequestTime = timeMillis()
        fileState.lastAccess = releaseRequestTime

        //TODO consider implementing JSword's more thorough approach
        releaseResourceJob = CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.Default) {
            delay(TIME_TO_RELEASE_MILLIS)
            if (releaseRequestTime == fileState.lastAccess) {
                metaToState.remove(fileState.bookMetaData)?.let { removedState ->
                    if (removedState == fileState && releaseRequestTime == removedState.lastAccess) {
                        fileState.releaseResources()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun timeMillis() = Clock.System.now().toEpochMilliseconds()
}