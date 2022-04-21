package ru.netology.geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.lang.reflect.Executable;
import java.util.stream.Stream;

import static ru.netology.geo.GeoServiceImpl.MOSCOW_IP;
import static ru.netology.geo.GeoServiceImpl.NEW_YORK_IP;

public class GeoServiceImplTest {
    GeoServiceImpl sut;

    @BeforeAll
    public static void startedAll() {
        System.out.println(" Tests started");
    }

    @BeforeEach
    public void started() {
        System.out.println(" Test started");
        sut = new GeoServiceImpl();
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println(" Tests completed");
    }

    @AfterEach
    public void finished() {
        System.out.println(" Test completed");
    }

    @Test
    void test_byIp_success(){
        // given:
        String ip = "172.0.32.11";
        Location expected = new Location("Moscow", Country.RUSSIA, "Lenina", 15);

        // when:
        Location result = sut.byIp(ip);

        // then:
        Assertions.assertEquals(expected.getCity(), result.getCity());
        Assertions.assertEquals(expected.getCountry(), result.getCountry());
        Assertions.assertEquals(expected.getStreet(), result.getStreet());
        Assertions.assertEquals(expected.getBuiling(), result.getBuiling());

    }

    @Test
    void test_byCoordinates_exseption_success(){
        double latitude =3.33;
        double longitude = 3.009;
         Class<RuntimeException> expected = RuntimeException.class;

        // then:
        Assertions.assertThrows(expected, () -> sut.byCoordinates(latitude,longitude));
    }

    @ParameterizedTest
    @MethodSource("source")
    void test_byIp_success(String ip, Location expected) {
        // when:
        Location result = sut.byIp(ip);

        // then:
        Assertions.assertEquals(expected.getCity(), result.getCity());
        Assertions.assertEquals(expected.getCountry(), result.getCountry());
        Assertions.assertEquals(expected.getStreet(), result.getStreet());
        Assertions.assertEquals(expected.getBuiling(), result.getBuiling());
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of(MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.0.0", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.0.0.0",new Location("New York", Country.USA, null, 0)));

    }
    }
