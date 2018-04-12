package com.github.sebastiancegielka.securitykeeper.model;

import org.junit.Test;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class PasswordSafeTest {
    @Test
    public void shouldAddGivenObjectToMapIfAddEntryCalledOnObject() {
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();

        //when
        mapTest.addEntryToMap(test);

        //then
        assertThat(test).isEqualToComparingFieldByField(mapTest.getEntryFromMap(test.getId()));
    }

    @Test
    public void shouldAddGivenObjectWithKeyCreatedFromItsID() {
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("h", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();

        //when
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        //then
        assertThat(mapTest.getEntryFromMap(test.getId())).isEqualToComparingFieldByField(test);
        assertThat(mapTest.getEntryFromMap(test2.getId())).isEqualToComparingFieldByField(test2);
    }

    @Test
    public void shouldRemoveEntryOnlyByWebsiteIfTheresNoOtherAccountsOnThisWebsite() {
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("h", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        //when
        mapTest.removeEntryByWebsite("a");

        //then
        assertThat(mapTest.containsWebsite(test)).isEqualTo(false);
        assertThat(mapTest.containsWebsite(test2)).isEqualTo(true);
    }

    @Test
    public void shouldChangeThePasswordOnlyByWebsiteIfTheresNoOtherAccountsOnThisWebsite() {
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("h", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        //when
        mapTest.changePasswordByWebsite("a", "duck".toCharArray());

        //then
        assertThat(test.getPassword()).isEqualToIgnoringWhitespace("duck");
    }

    @Test
    public void shouldRemoveEntryByWebsiteAndLoginIfThereAreMultipleAccountsOnOneWebsite() {
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("a", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        //when
        assertThat(mapTest.isThereOneAccountsOnTheWebsite("a")).isEqualTo(false);
        mapTest.removeEntryByLogin("a", "test");

        //then
        assertThat(mapTest.containsWebsite(test)).isEqualTo(false);
        assertThat(mapTest.containsWebsite(test2)).isEqualTo(true);
    }

    @Test
    public void shouldChangeThePasswordByWebsiteAndLoginIfThereAreMultipleAccountsOnOneWebsite() {
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("a", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        //when
        assertThat(mapTest.isThereOneAccountsOnTheWebsite("a")).isEqualTo(false);
        mapTest.changePasswordByLogin("a", "test", "duck".toCharArray());

        //then
        assertThat(test.getPassword()).isEqualToIgnoringWhitespace("duck");
    }

    @Test
    public void shouldCopyPasswordToClipboardWhenWebsiteAdressGiven(){
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);

        //when
        mapTest.getPasswordByWebsite("a");

        //then
        try {
            String pass = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
            assertThat(pass).isEqualToIgnoringWhitespace("admin1");
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldCopyPasswordToClipboardWhenWebsiteAndLoginAdressGiven_WhenMultipleAccountsForOneWebsite(){
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("a", "test2", "haslo123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        //when
        assertThat(mapTest.isThereOneAccountsOnTheWebsite("a")).isEqualTo(false);
        mapTest.getPasswordByLogin("a", "test2");

        //then
        try {
            String pass = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
            assertThat(pass).isEqualToIgnoringWhitespace("haslo123");
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldThrowExceptionIfTryingToAddSecondObjectWithSameWebsiteAdressAndLogin(){
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("a", "test", "haslo123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);

        //when
        try{
            mapTest.addEntryToMap(test2);
        }

        //then
        catch (RuntimeException e){

        }
    }

    @Test
    public void shouldReturnTrueIfTheresAlreadySameWebsiteAndLoginEntry(){
        //given
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("a", "test", "haslo123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();

        //when
        mapTest.addEntryToMap(test);

        //then
        assertThat(mapTest.checkIfAlreadyInSafe(test2)).isEqualTo(true);
    }
}
