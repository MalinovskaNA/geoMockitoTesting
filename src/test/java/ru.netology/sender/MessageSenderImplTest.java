package ru.netology.sender;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static ru.netology.geo.GeoServiceImpl.*;
import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

class MessageSenderImplTest {
        //MessageSenderImpl sut;

        @BeforeAll
        public static void startedAll() {
        System.out.println(" Tests started");
        }

        @BeforeEach
        public void started() {
            System.out.println(" Test started");
        }

        @AfterAll
        public static void finishedAll() {
            System.out.println(" Tests completed");
        }

        @AfterEach
        public void finished() {
            System.out.println(" Test completed");
        }

//        @Test
//        void test_send_success() {
//            GeoService geoService = Mockito.mock(GeoServiceImpl.class);
//            Mockito.when(geoService.byIp(MOSCOW_IP))
//                    .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
//
//            LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
//            Mockito.when(localizationService.locale(Country.RUSSIA))
//                    .thenReturn("Добро пожаловать");
//
//            MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
//
//            Map<String, String> headers = new HashMap<String, String>();
//            headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, MOSCOW_IP);
//            String preferences = messageSender.send(headers);
//
//            String expected= "Добро пожаловать";
//            Assertions.assertEquals(expected, preferences);
//        }

        @ParameterizedTest
        @MethodSource("source")
        void test_send_success(String ip, Location location, Country country, String expected) {
            GeoService geoService = Mockito.mock(GeoServiceImpl.class);
            Mockito.when(geoService.byIp(ip))
                    .thenReturn(location);

            LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
            Mockito.when(localizationService.locale(country))
                    .thenReturn(expected);

            MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

            Map<String, String> headers = new HashMap<String, String>();
            headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
            String preferences = messageSender.send(headers);

            Assertions.assertEquals(expected, preferences);
        }

        private static Stream<Arguments> source(){
            return Stream.of(
                    Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15), Country.RUSSIA, "Добро пожаловать"),
                    Arguments.of("96.44.183.149",new Location("New York", Country.USA, " 10th Avenue", 32), Country.USA, "Welcome"));
        }
    }



