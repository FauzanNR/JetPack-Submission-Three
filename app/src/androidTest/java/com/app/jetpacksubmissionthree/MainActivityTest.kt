package com.app.jetpacksubmissionthree

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.jetpacksubmissionthree.data.utils.EspressoIdlingResource
import com.app.jetpacksubmissionthree.ui.movie.MovieAdapter
import com.app.jetpacksubmissionthree.ui.tv.TvAdapter
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsEqual
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val navController = TestNavHostController(
        ApplicationProvider.getApplicationContext()
    )

    @get:Rule
    var activity: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }


    @Test
    fun mainTest() {
//create TestNavigationHostController
        activity.scenario.onActivity { act ->
            navController.setGraph(R.navigation.bottom_navigation)
            Navigation.setViewNavController(act.findViewById(R.id.fragment_host), navController)
        }
        Espresso.onView(withId(R.id.id_bottom_naview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//check navigation
        Espresso.onView(withId(R.id.id_bottom_naview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.id_menu_movie)).perform(ViewActions.click())
        Assert.assertThat(navController.currentDestination?.id, IsEqual(R.id.id_menu_movie))

        activity.scenario.onActivity { navController.setCurrentDestination(R.id.id_menu_tv) }
        Espresso.onView(withId(R.id.id_menu_tv)).perform(ViewActions.click())
        Assert.assertThat(navController.currentDestination?.id, IsEqual(R.id.id_menu_tv))

        activity.scenario.onActivity { navController.setCurrentDestination(R.id.id_menu_fav) }
        Espresso.onView(withId(R.id.id_menu_fav)).perform(ViewActions.click())
        Assert.assertThat(navController.currentDestination?.id, IsEqual(R.id.id_menu_fav))
//remote
//cek recycler view Movie
        Espresso.pressBack()
        Espresso.onView(withId(R.id.id_recview_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.id_recview_movie)).perform(
            RecyclerViewActions.scrollToPosition<MovieAdapter.MovieHolder>(
                (20)
            )
        )
//select movie item
        for (i in 0..15) {
            Espresso.onView(withId(R.id.id_recview_movie)).perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieHolder>(
                    i,
                    ViewActions.click()
                )
            )
//cek movie detail
            Espresso.onView(withId(R.id.id_detail_img))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_collap))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_rating))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_description)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            ).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
//cek fab add to fav
            Espresso.onView(withId(R.id.id_fab_detail))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

            Espresso.pressBack()
        }
//select delete 5 fav movie item in movie
        for (i in 3..7) {
            Espresso.onView(withId(R.id.id_recview_movie)).perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieHolder>(
                    i,
                    ViewActions.click()
                )
            )
//cek movie detail
            Espresso.onView(withId(R.id.id_detail_img))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_collap))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_rating))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_description)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            ).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
//cek fab add to fav
            Espresso.onView(withId(R.id.id_fab_detail))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

            Espresso.pressBack()
        }
//cek recycler view Tv show
//        Espresso.pressBack()
        Espresso.onView(withId(R.id.id_menu_tv)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.id_recview_tv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.id_recview_tv)).perform(
            RecyclerViewActions.scrollToPosition<TvAdapter.TvHolder>(
                (20)
            )
        )
//select tv item
        for (i in 0..15) {
            Espresso.onView(withId(R.id.id_recview_tv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<TvAdapter.TvHolder>(
                    i,
                    ViewActions.click()
                )
            )
//cek tv detail
            Espresso.onView(withId(R.id.id_detail_img))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_collap))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_rating))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_description)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            ).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
//cek fab add to fav
            Espresso.onView(withId(R.id.id_fab_detail))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
            Espresso.pressBack()
        }

//select delete 10 fav tv item in tv
        for (i in 3..7) {
            Espresso.onView(withId(R.id.id_recview_tv)).perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieHolder>(
                    i,
                    ViewActions.click()
                )
            )
//cek tv detail
            Espresso.onView(withId(R.id.id_detail_img))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_collap))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_rating))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_description)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            ).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
//cek fab add to fav
            Espresso.onView(withId(R.id.id_fab_detail))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

            Espresso.pressBack()
        }
//local
//cek recview movie fav local
        Espresso.onView(withId(R.id.id_menu_fav)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.id_recview_movie_fav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.id_recview_movie_fav))
            .check(CustomRecyclerViewItemCountAssertion(11))
        Espresso.onView(withId(R.id.id_recview_movie_fav)).perform(
            RecyclerViewActions.scrollToPosition<TvAdapter.TvHolder>(
                (10)
            )
        )

//cek  and delete 4 data
        for (i in 0..3) {
            Espresso.onView(withId(R.id.id_recview_movie_fav)).perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieHolder>(
                    i,
                    ViewActions.click()
                )
            )
//cek movie detail
            Espresso.onView(withId(R.id.id_detail_img))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_collap))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_rating))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_description)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            ).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
//cek fab add to fav
            Espresso.onView(withId(R.id.id_fab_detail))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

            Espresso.pressBack()
        }
//cek recview tv fav local
        Espresso.onView(withId(R.id.id_recview_movie_fav)).perform(ViewActions.swipeLeft())
        Espresso.onView(withId(R.id.id_recview_tv_fav))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.id_recview_tv_fav))
            .check(CustomRecyclerViewItemCountAssertion(11))
        Espresso.onView(withId(R.id.id_recview_tv_fav)).perform(
            RecyclerViewActions.scrollToPosition<TvAdapter.TvHolder>(
                (10)
            )
        )
//delete 4 data
        for (i in 0..3) {
            Espresso.onView(withId(R.id.id_recview_tv_fav)).perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieAdapter.MovieHolder>(
                    i,
                    ViewActions.click()
                )
            )
//cek tv detail
            Espresso.onView(withId(R.id.id_detail_img))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_collap))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_rating))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
            Espresso.onView(withId(R.id.id_detail_description)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            ).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(""))))
//cek fab add to fav
            Espresso.onView(withId(R.id.id_fab_detail))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
            Espresso.pressBack()
        }
    }

    internal class CustomRecyclerViewItemCountAssertion(private val expectedCount: Int) :
        ViewAssertion {
        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter!!.itemCount, `is`(expectedCount))
        }
    }
}