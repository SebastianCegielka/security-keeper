package com.github.sebastiancegielka.securitykeeper.controller;

import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;
import com.github.sebastiancegielka.securitykeeper.model.PasswordSafe;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.File;

public class FileSafeControllerTest {

    @Test
    public void shouldReadExactlyWhatsBeenWrittenToFile(){
        //given
        FileSafeController fileSafeController = new FileSafeController();
        File file = new File("test-file");
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("h", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        //when
        fileSafeController.writeToFile(mapTest, file);
        PasswordSafe mapRead = fileSafeController.readFromFile(file);

        //then
        assertThat(mapRead.containsWebsite(test)).isEqualTo(true);
        assertThat(mapRead.containsWebsite(test2)).isEqualTo(true);
    }
}
