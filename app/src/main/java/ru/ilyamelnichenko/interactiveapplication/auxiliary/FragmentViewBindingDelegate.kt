package ru.ilyamelnichenko.interactiveapplication.auxiliary

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <ViewBindingType : ViewBinding> Fragment.viewBinding(bind: ((view: View) -> ViewBindingType)): FragmentViewBindingDelegate<ViewBindingType> = FragmentViewBindingDelegate(this, bind)

class FragmentViewBindingDelegate<ViewBindingType>(
    fragment: Fragment,
    private val bind: (view: View) -> ViewBindingType
) : ReadOnlyProperty<Fragment, ViewBindingType>, LifecycleObserver {

    private var binding: ViewBindingType? = null

    private val handler = Handler(Looper.getMainLooper())

    init {

        fragment.viewLifecycleOwnerLiveData.observe(fragment) { lifecycleOwner ->

            lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    lifecycleOwner.lifecycle.removeObserver(this)

                    handler.post { binding = null }
                }
            })
        }

        fragment.lifecycle.addObserver(this)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): ViewBindingType {

        binding?.let { return it }

        return bind.invoke(thisRef.requireView()).also {
            binding = it
        }
    }
}
