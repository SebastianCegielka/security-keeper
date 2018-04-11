import com.github.sebastiancegielka.securitykeeper.model.PasswordEntry;
        import com.github.sebastiancegielka.securitykeeper.model.PasswordSafe;
        import org.junit.Test;
        import static org.assertj.core.api.Assertions.*;

public class PasswordManagerTest {
    @Test
    public void shouldAddGivenObjectToMapIfAddEntryCalledOnObject(){
        PasswordEntry test = new PasswordEntry();
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        assertThat(test).isEqualToComparingFieldByField(mapTest.getEntryFromMap(test.getId()));
    }

    @Test
    public void shouldAddGivenObjectWithKeyCreatedFromItsID() {
        PasswordEntry test = new PasswordEntry();
        PasswordEntry test2 = new PasswordEntry();
        PasswordSafe mapTest = new PasswordSafe();

        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        assertThat(mapTest.getEntryFromMap(test.getId())).isEqualToComparingFieldByField(test);
        assertThat(mapTest.getEntryFromMap(test2.getId())).isEqualToComparingFieldByField(test2);
    }

    @Test
    public void shouldRemoveEntryOnlyByWebsiteIfTheresNoOtherAccountsOnThisWebsite(){
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("h", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        mapTest.removeEntry("a");
        assertThat(mapTest.containsWebsite(test)).isEqualTo(false);
        assertThat(mapTest.containsWebsite(test2)).isEqualTo(true);
    }

    @Test
    public void shouldChangeThePasswordOnlyByWebsiteIfTheresNoOtherAccountsOnThisWebsite(){
        PasswordEntry test = new PasswordEntry("a", "test", "admin1".toCharArray());
        PasswordEntry test2 = new PasswordEntry("h", "test2", "123".toCharArray());
        PasswordSafe mapTest = new PasswordSafe();
        mapTest.addEntryToMap(test);
        mapTest.addEntryToMap(test2);

        mapTest.changePasswordByWebsite("a", "duck".toCharArray());
        assertThat(test.getPassword()).isEqualToIgnoringWhitespace("duck");
    }
}
