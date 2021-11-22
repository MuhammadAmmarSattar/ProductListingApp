package com.example.myappproject.ui.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myappproject.MainActivity
import com.example.myappproject.R
import com.example.myappproject.ui.product.ProductListFragmentTest.MyViewAction.clickChildViewWithId
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


//@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ProductListFragmentTest {
    //    private lateinit var scenario: ActivityScenario<MainActivity>
//    private lateinit var scenario1: FragmentScenario<ProductListFragment>

//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)

//    @Inject
//    lateinit var fragmentFactory: ProductListFragment

    @Before
    fun setup() {
//        hiltRule.inject()
    }

    @Test
    fun rvTest() {
        // open fragment product listing
        ActivityScenario.launch(MainActivity::class.java)
        // test toolbar backpress dialog open or not

        onView(isRoot()).perform(waitFor(5000))
        onView((withId(R.id.imgBack)))
            .perform(ViewActions.click())

        onView(isRoot()).perform(waitFor(3000))
        // dialog cancel button press

        onView((withId(R.id.btnNo)))
            .perform(ViewActions.click())
        onView(isRoot()).perform(waitFor(3000))


        // item click on recyclerview detail response is getting  or not

        onView(withId(R.id.rvProductListing)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                clickChildViewWithId(R.id.rootView)
            )
        )

        onView(isRoot()).perform(waitFor(4000))
        //  backpress navigate from detail product listing

        pressBack()
        //  backpress check dialog opening or not whtih the help of backpress

        pressBack()
        onView(isRoot()).perform(waitFor(2000))
        onView((withId(R.id.btnNo)))
            .perform(ViewActions.click())

        onView(withId(R.id.title)).check(matches(withText(R.string.product_listing)))
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): org.hamcrest.Matcher<View> = isRoot()

            //            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    object MyViewAction {
        fun clickChildViewWithId(id: Int): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> {
                    return isRoot()
                }

                override fun getDescription(): String {
                    return "Click on a child view with specified id."
                }

                override fun perform(uiController: UiController, view: View) {
                    val v = view.findViewById<View>(id)
                    v.performClick()
                }
            }
        }
    }

    fun pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack())
    }
}
