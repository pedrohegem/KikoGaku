package com.example.proyecto;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.proyecto.ui.Eventos.CrearEventoActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CrearEventoMunicipioTest {

    @Rule
    public ActivityScenarioRule<InicioSesion> mActivityScenarioRule =
            new ActivityScenarioRule<>(InicioSesion.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Test
    public void crearEventoMunicipioTest() throws InterruptedException {
        // REGISTRO DE SESION
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

        // INICIO DE SESION
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

        // MAINACTIVITY - INICIOFRAGMENT
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


        // ELECCIÓN CREAR EVENTO MUNICIPIO O MONTAÑA
        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.CrearMunicipio), withText("Evento de municipio"),
                        childAtPosition(
                                allOf(withId(R.id.LayoutCrearEvento),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment_content_crear_evento),
                                                0)),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());

        // CREAR EVENTO MUNICIPIO
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.InputNombreEvento),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0), isDisplayed()));
        appCompatEditText5.perform(scrollTo(), replaceText("Futbol "), closeSoftKeyboard());


        onView(withId(R.id.inputLocalidad)).perform(typeText("Sevilla"), closeSoftKeyboard());

        onView(withId(R.id.InputFechaEvento)).perform(typeText("30/01/2023"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.InputDescripcionEvento),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                2), isDisplayed()));
        appCompatEditText8.perform(scrollTo(), replaceText("Con amigos"), closeSoftKeyboard());



        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.BotonModificar), withText("Crear evento"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1), isDisplayed()));
        materialButton6.perform(scrollTo(), click());


        // Vuelve al InicioFragment - para ver que se ha creado
        pressBack();

        // Se comprueba que existe en el RecyclerView existe el nuevo evento creado y con los datos del evento

        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withId(R.id.Nombre))));
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withText("Futbol "))));

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
