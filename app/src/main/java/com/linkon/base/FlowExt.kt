package com.linkon.base

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectWhenOwnerStarted(
    owner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                action(it)
            }
        }
    }
}

inline fun <T> Flow<T>.collectLatestWhenOwnerStarted(
    owner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectLatest {
                action(it)
            }
        }
    }
}

/**
 * The given flow is only collected when the [owner] is in the
 * [Lifecycle.State.RESUMED].
 *
 * As soon as the [owner] leaves that state, the flow collection
 * is cancelled. It will restart if it returns to that state.
 */
inline fun <T> Flow<T>.collectWhenOwnerResumed(
    owner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            collect {
                action(it)
            }
        }
    }
}

/**
 * The given flow is only collected when the [owner] is in the
 * [Lifecycle.State.CREATED].
 *
 * As soon as the [owner] leaves that state, the flow collection
 * is cancelled. It will restart if it returns to that state.
 */
inline fun <T> Flow<T>.collectWhenOwnerCreated(
    owner: LifecycleOwner,
    crossinline action: suspend (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            collect {
                action(it)
            }
        }
    }
}

/**
 * Extension function to convert EditText changes into a Flow of Strings.
 */
fun AppCompatEditText.textChanges(): Flow<String> {
    return callbackFlow {
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Send the new text to the Flow.
                trySend(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        addTextChangedListener(watcher)
        // When the Flow is closed, remove the listener.
        awaitClose { removeTextChangedListener(watcher) }
    }.onStart {
        // Emit the current text immediately on collection.
        emit(text.toString())
    }
}