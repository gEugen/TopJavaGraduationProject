package com.github.geugen.voting.web.user;

import com.github.geugen.voting.model.Role;
import com.github.geugen.voting.model.User;
import com.github.geugen.voting.util.JsonUtil;
import com.github.geugen.voting.web.MatcherFactory;

import java.time.LocalDateTime;
import java.util.Collections;


public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class, "password", "registered", "roles", "votes");
    public static final int USER1_ID = 1;
    public static final int USER2_ID = 2;
    public static final int USER3_ID = 3;
    public static final int ADMIN_ID = 4;
    public static final int GUEST_ID = 5;
    public static final int USER4_ID = 6;
    public static final int USER5_ID = 7;
    public static final int USER6_ID = 8;

    public static final int NOT_FOUND = 100;

    public static final String USER1_MAIL = "user1@yandex.ru";
    public static final String USER2_MAIL = "user2@yandex.ru";
    public static final String USER3_MAIL = "user3@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";
    public static final String USER4_MAIL = "user4@yandex.ru";
    public static final String USER5_MAIL = "user5@yandex.ru";
    public static final String USER6_MAIL = "user6@yandex.ru";

    public static final User user1 = new User(USER1_ID, "User1", USER1_MAIL, "password1", Role.USER);
    public static final User user2 = new User(USER2_ID, "User2", USER2_MAIL, "password2", Role.USER);
    public static final User user3 = new User(USER3_ID, "User3", USER3_MAIL, "password3", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest");
    public static final User user4 = new User(USER4_ID, "User4", USER4_MAIL, "password4", Role.USER);
    public static final User user5 = new User(USER5_ID, "User5", USER5_MAIL, "password5", Role.USER);
    public static final User user6 = new User(USER6_ID, "User6", USER6_MAIL, "password5", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, LocalDateTime.now(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER1_ID, "UpdatedName", USER1_MAIL, "newPass", Role.ADMIN);
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}
