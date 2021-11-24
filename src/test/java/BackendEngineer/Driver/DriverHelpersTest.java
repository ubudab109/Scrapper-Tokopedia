package BackendEngineer.Driver;

import BackendEngineer.Helpers.DriverHelpers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.internal.matchers.Matches;

import java.util.List;

import static org.hamcrest.core.Is.is;

public class DriverHelpersTest {
    private DriverHelpers driverHelpers;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        driverHelpers = new DriverHelpers();
    }

    @Test
    public void whenPrepareTabsThenShouldReturnItems() throws Exception {
        List<String> tabs = driverHelpers.preparingTwoTabs();
        collector.checkThat(tabs.size(), is(2));
    }

    @After
    public void cleanUp() {
        driverHelpers.quit();
    }
}
