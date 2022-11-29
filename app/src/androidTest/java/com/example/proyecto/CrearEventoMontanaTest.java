package com.example.proyecto;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CrearEventoMontanaTest {

    @Rule
    public ActivityScenarioRule<LaunchActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LaunchActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Before
    public void a(){
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.bRegistrarse), withText("Registrarse"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("jorge"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("1234"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.bRegistrarse), withText("Registrarse"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.username),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("jorge"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("1234"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.bIniciarSesion), withText("Iniciar Sesión"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());
    }

    @Test
    public void crearEventoMontanaTest() {

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab), withContentDescription("BotonAnadirNuevoEvento"),
                        childAtPosition(
                                allOf(withId(R.id.app_bar_main),
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.CrearMontana), withText("Evento de montaña"),
                        childAtPosition(
                                allOf(withId(R.id.LayoutCrearEvento),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment_content_crear_evento),
                                                0)),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.InputNombreEvento),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                1)));
        appCompatEditText5.perform(scrollTo(), replaceText("Senderismo"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.SpinnerMunicipio),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0)));
        appCompatSpinner.perform(scrollTo(), click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(6);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.InputFechaEvento),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                3)));
        appCompatEditText6.perform(scrollTo(), click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.InputDescripcionEvento),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                2)));
        appCompatEditText7.perform(scrollTo(), replaceText("Senderismo\n"), closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.BotonModificar), withText("Crear evento"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        materialButton6.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        // Se comprueba que existe en el RecyclerView existe el nuevo evento creado.
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withId(R.id.Nombre))));

        ViewInteraction textView = onView(
                allOf(withId(R.id.Nombre), withText("Senderismo"),
                        withParent(allOf(withId(R.id.list_item),
                                withParent(withId(R.id.list)))),
                        isDisplayed()));
        textView.check(matches(withText("Senderismo")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.Fecha), withText("30/Nov/2022"),
                        withParent(allOf(withId(R.id.list_item),
                                withParent(withId(R.id.list)))),
                        isDisplayed()));
        textView2.check(matches(withText("30/Nov/2022")));

        // TODO - No estoy seguro si tengo que eliminar el evento creado del test. Si ejecuto de nuevo el test, tendría dos eventos iguales.
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
