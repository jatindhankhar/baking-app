package in.jatindhankhar.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import in.jatindhankhar.bakingapp.ui.activity.RecipeListActivity;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import android.support.test.espresso.contrib.RecyclerViewActions;

/**
 * Created by jatin on 1/21/18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
    @Rule public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<RecipeListActivity>(RecipeListActivity.class);

    @Test
    public void checkPacmanProgressBar()
    {
        Espresso.onView(withId(R.id.pacman_loading)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkListItemClick()
    {
        Espresso.onView(withId(R.id.recipe_list)).perform( RecyclerViewActions.actionOnItemAtPosition(0,click()));
    }

}
