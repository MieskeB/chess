package nl.michelbijnen;

public class Factory {
    //region gameLogic
    public static IGameLogic getIGameLogic(IController controller, IBoardController boardController) {
        return new GameLogic(new GameDal(new ControllerLogic(controller)), boardController);
    }
    //endregion

    //region userLogic
    public static IUserLogic getIUserLogic(IController controller) {
        return new UserLogic(new UserDal(new ControllerLogic(controller)));
    }
    //endregion
}
