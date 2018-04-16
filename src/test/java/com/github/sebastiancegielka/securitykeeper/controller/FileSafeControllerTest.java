package com.github.sebastiancegielka.securitykeeper.controller;

import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;
import com.github.sebastiancegielka.securitykeeper.model.PasswordSafe;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileSafeControllerTest {

    @Test
    public void givenTwoPasswordEntriesWrittenToTheFile_WhenEncryptedAndDecrypted_ThenShouldBeEqualToTheFileBeforeEncryption() throws IOException {
        //given
        FileSafeController fileSafeController = new FileSafeController();
        File file = new File("test-file-cipher");
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("h", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);
        fileSafeController.writeToFile(mapTest, file);
        List<String> pre = FileUtils.readLines(file, "UTF-8");

        //when
        fileSafeController.encryptTheFile(file, "test");
        fileSafeController.decryptTheFile(file, "test");
        List<String> post = FileUtils.readLines(file, "UTF-8");

        //then
        assertThat(pre).isEqualTo(post);
    }
}
