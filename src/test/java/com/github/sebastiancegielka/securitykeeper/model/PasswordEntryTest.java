package com.github.sebastiancegielka.securitykeeper.model;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class PasswordEntryTest {
    @Test
    public void givenWebsiteLoginAndPasswordWhenUsedConstructorAndBuilderThenShouldGiveSameResult(){
        //given
        String website = "a";
        String login = "test";
        char[] password = "1234".toCharArray();

        //when
        PasswordEntry test1 = new PasswordEntry(website, login, password);
        PasswordEntry test2 = PasswordEntry.Builder
                .create()
                .withWebsite(website)
                .withLogin(login)
                .withPassword(password)
                .build();

        //then
        assertThat(test1).isEqualToComparingOnlyGivenFields(test2, "website", "login", "password");
    }
}
