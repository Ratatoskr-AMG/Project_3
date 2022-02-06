package ru.ratatoskr.project_3.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ru.ratatoskr.project_3.presentation.composable.MyComposable

abstract class ComposableFragment : Fragment() {

    var myComposable = MyComposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                myComposable.FixScreen()
            }
        }
    }

    @Composable
    abstract fun RenderView()
}