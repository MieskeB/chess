package nl.michelbijnen;

import javafx.application.Platform;
import org.junit.*;

public class IntegrationTests {

    private ControllerMock controllerMock;
    private IUserLogic iUserLogic;
    private IGameLogic iGameLogic;

    @Before
    public void beforeTests() {
        this.controllerMock = new ControllerMock();
        this.iUserLogic = Factory.getIUserLogic(controllerMock);
        this.iGameLogic = Factory.getIGameLogic(this.controllerMock, this.controllerMock);
    }

    @After
    public void afterTests() {
    }

    @Ignore
    @Test
    public void testIfUserCanLogin() {
        // Right password
        this.iUserLogic.login("admin1", "admin1");
        this.blockThread();
        Assert.assertTrue((Boolean)this.controllerMock.read());

        // Wrong password
        this.iUserLogic.login("admin2", "admin2");
        this.blockThread();
        Assert.assertTrue((Boolean)this.controllerMock.read());
    }

    @Ignore
    @Test
    public void testIfUserCanGet() {
        this.iUserLogic.login("admin1", "admin1");
        this.blockThread();
        Assert.assertTrue((Boolean)this.controllerMock.read());

        this.iUserLogic.getLoggedInUser();
        this.blockThread();
        User user = (User) this.controllerMock.read();
        Assert.assertEquals(user.getUsername(), "admin1");
        Assert.assertEquals(user.getPassword(), "");
        Assert.assertEquals(user.getEmail(), "admin1@admin.nl");
        Assert.assertEquals(user.getFirstName(), "admin1");
        Assert.assertEquals(user.getLastName(), "admin1");
        Assert.assertEquals(user.getGender(), "Male");
    }

    @Ignore
    @Test
    public void testIfUserCanAddScore() {
        this.iUserLogic.login("admin1", "admin1");
        this.blockThread();
        Assert.assertTrue((Boolean)this.controllerMock.read());

        this.iUserLogic.getLoggedInUser();
        this.blockThread();
        Assert.assertNotNull(this.controllerMock.read());

        this.iGameLogic.initializeGame(PlayerColor.WHITE, true, Difficulty.VERYEASY);

    }

    private void blockThread() {
        int i = 0;
        while (!this.controllerMock.isGotResponse()) {
            try {
                Thread.sleep(1000);
                i++;
                if (i > 10) {
                    Assert.fail("Did not get a response from the server! (request timed out)");
                }
            }
            catch (InterruptedException e) {
            }
        }
    }
}
