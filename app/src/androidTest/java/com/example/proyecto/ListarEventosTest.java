package com.example.proyecto;


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
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.proyecto.Room.AppDatabase;
import com.example.proyecto.Room.Modelo.Evento;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListarEventosTest {

    @Rule
    public ActivityScenarioRule<LaunchActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LaunchActivity.class);

    @Test
    public void listarEventosTest() {
        //--------------------------------------- Cargar datos ---------------------------------------
        Evento event = new Evento("Cenar1", "Madrid", "Cañas", new Date(System.currentTimeMillis()), true);
        Evento event1 = new Evento("Cenar2", "Madrid", "Cañas", new Date(System.currentTimeMillis()), true);
        Evento event2 = new Evento("Cenar3", "Madrid", "Cañas", new Date(System.currentTimeMillis()), true);
        Evento event3 = new Evento("Cenar4", "Madrid", "Cañas", new Date(System.currentTimeMillis()), true);
        AppDatabase.getInstance(null).eventoDAO().insertEvent(event);
        AppDatabase.getInstance(null).eventoDAO().insertEvent(event1);
        AppDatabase.getInstance(null).eventoDAO().insertEvent(event2);
        AppDatabase.getInstance(null).eventoDAO().insertEvent(event3);
        //--------------------------------------------------------------------------------------------
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
        appCompatEditText.perform(replaceText("Miguel"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("miguel"), closeSoftKeyboard());

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
        appCompatEditText3.perform(replaceText("Miguel"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("miguel"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.bIniciarSesion), withText("Iniciar Sesión"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());
//----------------------------------- INICIO INICIO -----------------------------------
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withId(R.id.Nombre))));
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withText("Cenar1"))));
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withText("Cenar2"))));
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withText("Cenar3"))));
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withText("Cenar4"))));



        AppDatabase.getInstance(null).eventoDAO().deleteEvent(event);
        AppDatabase.getInstance(null).eventoDAO().deleteEvent(event1);
        AppDatabase.getInstance(null).eventoDAO().deleteEvent(event2);
        AppDatabase.getInstance(null).eventoDAO().deleteEvent(event3);
//----------------------------------- INICIO FIN -----------------------------------
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Abrir panel lateral de navegación"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_perfil),
                        childAtPosition(
                                allOf(withId(androidx.navigation.ui.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.bEliminar), withText("Eliminar cuenta"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                5),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Confirmar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton5.perform(scrollTo(), click());
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
