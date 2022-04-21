package ru.netology.i18n;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;


public class LocalizationServiceImplTest {
    LocalizationServiceImpl sut;

    @BeforeAll
    public static void startedAll() {
        System.out.println(" Tests started");
    }

    @BeforeEach
    public void started() {
        System.out.println(" Test started");
        sut = new LocalizationServiceImpl();
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println(" Tests completed");
    }

    @AfterEach
    public void finished() {
        System.out.println(" Test completed");
    }

//    @Test
//    void test_locale_success(){
//
//        // given:
//        Country country = Country.RUSSIA;
//        String expected = "Добро пожаловать";
//
//        // when:
//        String result = sut.locale(country);
//
//        // then:
//        Assertions.assertEquals(expected, result);
//    }

    @ParameterizedTest
    @MethodSource("source")
    void test_locale_success(Country country, String expected) {
        // when:
        String result = sut.locale(country);

        // then:
        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"));
    }

}
