package com.example.proyecto;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@LargeTest
@RunWith(AndroidJUnit4.class)


public class EliminarEventoTest {

    // Clase para comprobar que la recyclerview está vacia, se han eliminado correctamente los eventos creados
    public class RecyclerViewItemCountAssertion implements ViewAssertion {

        private final Matcher<Integer> matcher;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.matcher = is(expectedCount);
        }


        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), matcher);
        }

    }

    @Rule
    public ActivityScenarioRule<InicioSesion> mActivityScenarioRule =
            new ActivityScenarioRule<>(InicioSesion.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Test
    public void eliminarEventoTest() throws InterruptedException {


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

        // -- EVENTO MUNICIPIO

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

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.InputFechaEvento),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                3)));
        appCompatEditText7.perform(scrollTo(), click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        materialButton5.perform(click());

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


        // ASSERTS de los detalles del EVENTO
        onView(allOf(withId(R.id.EtiquetaDetalles), isDisplayed())).check(matches(withText("Futbol ")));
        onView(allOf(withId(R.id.DetallesLocalidad), isDisplayed())).check(matches(withText("Sevilla")));

        DateFormat formatter = new SimpleDateFormat("d/MM/yyyy");
        String date = formatter.format(new Date());

        onView(allOf(withId(R.id.DetallesFechaDeInicio), isDisplayed())).check(matches(withText(date)));
        onView(allOf(withId(R.id.DetallesDescripcion), isDisplayed())).check(matches(withText("Con amigos")));

        // Vuelve al InicioFragment - para ver que se ha creado
        pressBack();

        // Se comprueba que existe en el RecyclerView existe el nuevo evento creado y con los datos del evento

        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withId(R.id.Nombre))));
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withText("Futbol "))));

        // Hay que eliminar el nuevo evento creado

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.list),
                        childAtPosition(
                                withId(R.id.child_ListaEventos),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.BotonEliminar), withText("Eliminar"),
                        childAtPosition(
                                allOf(withId(R.id.LayoutDetallesEvento),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment_content_borrate),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction materialButton9 = onView(
                allOf(withId(android.R.id.button1), withText("Confirmar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton9.perform(scrollTo(), click());


        // ASSERT. SE COMPRUEBA QUE LA RECYCLERVIEW ESTÁ VACIA (NO TIENE EVENTOS CREADOS)
        onView(withId(R.id.list)).check(new RecyclerViewItemCountAssertion(0));

        // -- EVENTO MONTANA

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab), withContentDescription("BotonAnadirNuevoEvento"),
                        childAtPosition(
                                allOf(withId(R.id.app_bar_main),
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction materialButton11 = onView(
                allOf(withId(R.id.CrearMontana), withText("Evento de montaña"),
                        childAtPosition(
                                allOf(withId(R.id.LayoutCrearEvento),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment_content_crear_evento),
                                                0)),
                                2),
                        isDisplayed()));
        materialButton11.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.InputNombreEvento),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                1)));
        appCompatEditText12.perform(scrollTo(), replaceText("Senderismo"), closeSoftKeyboard());

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

        ViewInteraction materialButton13 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        materialButton13.perform(click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.InputDescripcionEvento),
                        childAtPosition(
                                allOf(withId(R.id.LayoutModificar),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                2)));
        appCompatEditText14.perform(scrollTo(), replaceText("Senderismo\n"), closeSoftKeyboard());

        ViewInteraction materialButton15 = onView(
                allOf(withId(R.id.BotonModificar), withText("Crear evento"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1)));
        materialButton15.perform(scrollTo(), click());

        // ASSERTS de los detalles del EVENTO
        onView(allOf(withId(R.id.EtiquetaDetalles), isDisplayed())).check(matches(withText("Senderismo")));
        onView(allOf(withId(R.id.DetallesLocalidad), isDisplayed())).check(matches(withText("Sierra Nevada")));


        String date2 = formatter.format(new Date());

        onView(allOf(withId(R.id.DetallesFechaDeInicio), isDisplayed())).check(matches(withText(date)));
        onView(allOf(withId(R.id.DetallesDescripcion), isDisplayed())).check(matches(withText("Senderismo\n")));


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
        onView(allOf(withId(R.id.list), isDisplayed())).check(matches(hasDescendant(withText("Senderismo"))));

        // Hay que eliminar el nuevo evento creado

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.list),
                        childAtPosition(
                                withId(R.id.child_ListaEventos),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton16 = onView(
                allOf(withId(R.id.BotonEliminar), withText("Eliminar"),
                        childAtPosition(
                                allOf(withId(R.id.LayoutDetallesEvento),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment_content_borrate),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton16.perform(click());

        ViewInteraction materialButton17 = onView(
                allOf(withId(android.R.id.button1), withText("Confirmar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton17.perform(scrollTo(), click());

        // ASSERT. SE COMPRUEBA QUE LA RECYCLERVIEW ESTÁ VACIA (NO TIENE EVENTOS CREADOS)
        onView(withId(R.id.list)).check(new RecyclerViewItemCountAssertion(0));

        // ELIMINAR PERFIL
        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

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

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.bEliminar), withText("Eliminar cuenta"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_main),
                                        0),
                                5),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(android.R.id.button1), withText("Confirmar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton10.perform(scrollTo(), click());
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
