package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password";
    private static final int DEFAULT_AGE = 18;
    private static RegistrationService service;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        User registeredUser = new User();
        registeredUser.setLogin(DEFAULT_LOGIN);
        registeredUser.setPassword(DEFAULT_PASSWORD);
        registeredUser.setAge(DEFAULT_AGE);
        service.register(registeredUser);
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin("testLogin");
        testUser.setPassword("testPassword");
        testUser.setAge(19);
    }

    @AfterEach
    void clearTheStorage() {
        Storage.people.clear();
    }

    @Test
    void validLoginPasswordAge_Ok() {
        service.register(testUser);
    }

    @Test
    void invalidAge_NotOk() {
        testUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void invalidPassword_NotOk() {
        testUser.setPassword("1234");
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void loginAlreadyAdded_NotOk() {
        testUser.setLogin(DEFAULT_LOGIN);
        assertThrows(RuntimeException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void loginIsNull_NotOk() {
        testUser.setLogin(null);
        assertThrows(NullPointerException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void passwordIsNull_NotOk() {
        testUser.setPassword(null);
        assertThrows(NullPointerException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void ageIsNull_NotOk() {
        testUser.setAge(null);
        assertThrows(NullPointerException.class, () -> {
            service.register(testUser);
        });
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(NullPointerException.class, () -> {
            service.register(null);
        });
    }
}
