package com.example.about_theme_ui

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.dsl.module
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class AboutThemeFragmentTest : TestCase() {
    private lateinit var scenario: FragmentScenario<AboutThemeFragment>
    lateinit var mockVm: ThemeAboutViewModel

    @Before
    fun setup() {
        mockVm = mock(ThemeAboutViewModel::class.java)

        loadKoinModules(
            module {
                viewModel {
                    mockVm
                }
            },
        )
        scenario = launchFragmentInContainer(
            bundleOf("id" to 0),
        )
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun testAddingSpend() {
        Espresso.onView(ViewMatchers.withId((R.id.title)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
