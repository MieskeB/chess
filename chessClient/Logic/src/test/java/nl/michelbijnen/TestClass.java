package nl.michelbijnen;

import nl.michelbijnen.ChessPieces.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestClass {

    private User user;
    private UserLogic userLogic;
    private GameLogic gameLogic;

    private ControllerMock controllerMock;

    @Before
    public void testInitializer() {
        this.user = new User();
        this.user.setUsername("admin");
        this.user.setPassword("admin");
        this.user.setFirstName("admin");
        this.user.setLastName("admin");
        this.user.setScore(0);
        this.user.setEmail("admin.admin@admin.nl");
        this.user.setGender("Male");

        IUserDal iUserDal = mock(IUserDal.class);
        IGameDal iGameDal = mock(IGameDal.class);
        this.controllerMock = new ControllerMock();
        this.userLogic = new UserLogic(iUserDal);
        this.gameLogic = new GameLogic(iGameDal, this.controllerMock);

        Session.getInstance().setLoggedInUserId("123");
        Session.getInstance().setLoggedInUser(this.user);

        // Create a new game
        this.gameLogic.initializeGame(PlayerColor.WHITE, false, Difficulty.VERYEASY);
    }

    @Test
    public void testIfAllPiecesAreInRightLocationAtStart() {
        ChessBoard chessBoard = this.gameLogic.getChessBoard();

        // Black
        ChessPiece chessPiece = chessBoard.getChessPieceOfPoint(new Point(0, 0));
        if (!(chessPiece instanceof Rook) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(1, 0));
        if (!(chessPiece instanceof Knight) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(2, 0));
        if (!(chessPiece instanceof Bishop) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(3, 0));
        if (!(chessPiece instanceof Queen) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(4, 0));
        if (!(chessPiece instanceof King) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(5, 0));
        if (!(chessPiece instanceof Bishop) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(6, 0));
        if (!(chessPiece instanceof  Knight) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(7, 0));
        if (!(chessPiece instanceof Rook) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
            Assert.fail();
        }

        // Black pawns
        for (int i = 0; i <= 7; i++) {
            chessPiece = chessBoard.getChessPieceOfPoint(new Point(i, 1));
            if (!(chessPiece instanceof Pawn) || chessPiece.getPlayerColor() != PlayerColor.BLACK) {
                Assert.fail();
            }
        }

        // White
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(0, 7));
        if (!(chessPiece instanceof Rook) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(1, 7));
        if (!(chessPiece instanceof Knight) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(2, 7));
        if (!(chessPiece instanceof Bishop) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(3, 7));
        if (!(chessPiece instanceof Queen) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(4, 7));
        if (!(chessPiece instanceof King) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(5, 7));
        if (!(chessPiece instanceof Bishop) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(6, 7));
        if (!(chessPiece instanceof  Knight) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
            Assert.fail();
        }
        chessPiece = chessBoard.getChessPieceOfPoint(new Point(7, 7));
        if (!(chessPiece instanceof Rook) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
            Assert.fail();
        }

        // White pawns
        for (int i = 0; i <= 7; i++) {
            chessPiece = chessBoard.getChessPieceOfPoint(new Point(i, 6));
            if (!(chessPiece instanceof Pawn) || chessPiece.getPlayerColor() != PlayerColor.WHITE) {
                Assert.fail();
            }
        }
    }

    @Test
    public void testIfThereAre32Pieces() {
        ChessBoard chessBoard = this.gameLogic.getChessBoard();
        List<ChessPiece> chessPieces = chessBoard.getAllChessPieces();
        // Test if there are 32 white pieces
        Assert.assertEquals(chessPieces.size(), 32);
    }

    @Test
    public void testMovePawn() {
        ChessBoard chessBoard = this.gameLogic.getChessBoard();
        ChessPiece whiteChessPiece = chessBoard.getChessPieceOfPoint(new Point(0, 6));
        ChessPiece blackChessPiece = chessBoard.getChessPieceOfPoint(new Point(1, 1));

        // Tests bad movement
        Assert.assertFalse(this.gameLogic.moveChessPiece(whiteChessPiece, new Point(1, 5)));
        Assert.assertFalse(this.gameLogic.moveChessPiece(whiteChessPiece, new Point(0, 7)));

        // White move 1 step
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteChessPiece, new Point(0, 5)));
        // Black move 2 steps
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackChessPiece, new Point(1, 3)));
        // White move 1 steps
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteChessPiece, new Point(0, 4)));
        // Black hit white pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackChessPiece, new Point(0, 4)));

        // Test if black piece is in right location now
        Assert.assertNotEquals(chessBoard.getChessPieceOfPoint(new Point(0, 4)), whiteChessPiece);
        Assert.assertEquals(chessBoard.getChessPieceOfPoint(new Point(0, 4)), blackChessPiece);

        // Test if white chesspiece is removed from the game
        Assert.assertEquals(chessBoard.getAllChessPieces().size(), 31);
    }

    @Test
    public void testMoveRook() {
        ChessBoard chessBoard = this.gameLogic.getChessBoard();
        ChessPiece whitePawn = chessBoard.getChessPieceOfPoint(new Point(0, 6));
        ChessPiece blackPawn = chessBoard.getChessPieceOfPoint(new Point(0, 1));
        ChessPiece whiteRook = chessBoard.getChessPieceOfPoint(new Point(0, 7));
        ChessPiece blackRook = chessBoard.getChessPieceOfPoint(new Point(0, 0));

        // White moves pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(whitePawn, new Point(0, 4)));
        // Black moves pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackPawn, new Point(0, 3)));
        // White moves rook vertical
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteRook, new Point(0, 5)));
        // Black moves rook vertical
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackRook, new Point(0, 2)));
        // White moves rook horizontal
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteRook, new Point(7, 5)));
        // Black moves rook horizontal
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackRook, new Point(7, 2)));

        // Test if they are really there
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(7, 5)));
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(7, 2)));
    }

    @Test
    public void testMoveBishop() {
        ChessBoard chessBoard = this.gameLogic.getChessBoard();
        ChessPiece whitePawn = chessBoard.getChessPieceOfPoint(new Point(3, 6));
        ChessPiece blackPawn = chessBoard.getChessPieceOfPoint(new Point(3, 1));
        ChessPiece whiteBishop = chessBoard.getChessPieceOfPoint(new Point(2, 7));
        ChessPiece blackBishop = chessBoard.getChessPieceOfPoint(new Point(2, 0));

        // White moves pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(whitePawn, new Point(3, 4)));
        // Black moves pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackPawn, new Point(3, 3)));
        // White moves Bishop
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteBishop, new Point(3, 6)));
        // Black moves bishop
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackBishop, new Point(3, 1)));
        // White moves Bishop
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteBishop, new Point(1, 4)));
        // Black moves bishop
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackBishop, new Point(1, 3)));

        // Test if they are really there
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(1, 4)));
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(1, 3)));
    }

    @Test
    public void testMoveKnight() {
        ChessBoard chessBoard = this.gameLogic.getChessBoard();
        ChessPiece whiteKnight = chessBoard.getChessPieceOfPoint(new Point(1, 7));
        ChessPiece blackKnight = chessBoard.getChessPieceOfPoint(new Point(1, 0));

        // White moves knight
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteKnight, new Point(0, 5)));
        // Black moves knight
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackKnight, new Point(0, 2)));
        // White moves knight
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteKnight, new Point(1, 3)));
        // Black moves knight
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackKnight, new Point(1, 4)));

        // Test if they are really there
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(1, 3)));
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(1, 4)));
    }

    @Test
    public void testMoveQueen() {
        ChessBoard chessBoard = this.gameLogic.getChessBoard();
        ChessPiece whitePawn = chessBoard.getChessPieceOfPoint(new Point(3, 6));
        ChessPiece blackPawn = chessBoard.getChessPieceOfPoint(new Point(3, 1));
        ChessPiece whiteQueen = chessBoard.getChessPieceOfPoint(new Point(3, 7));
        ChessPiece blackQueen = chessBoard.getChessPieceOfPoint(new Point(3, 0));

        // White moves pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(whitePawn, new Point(3, 4)));
        // Black moves pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackPawn, new Point(3, 3)));
        // White moves queen
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteQueen, new Point(3, 5)));
        // Black moves queen
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackQueen, new Point(3, 2)));
        // White moves queen
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteQueen, new Point(5, 3)));
        // Black moves queen
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackQueen, new Point(5, 4)));

        // Test if they are really there
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(5, 3)));
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(5, 4)));
    }

    @Test
    public void testMoveKing() {
        ChessBoard chessBoard = this.gameLogic.getChessBoard();
        ChessPiece whitePawn = chessBoard.getChessPieceOfPoint(new Point(4, 6));
        ChessPiece blackPawn = chessBoard.getChessPieceOfPoint(new Point(4, 1));
        ChessPiece whiteKing = chessBoard.getChessPieceOfPoint(new Point(4, 7));
        ChessPiece blackKing = chessBoard.getChessPieceOfPoint(new Point(4, 0));

        // White moves pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(whitePawn, new Point(4, 4)));
        // Black moves pawn
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackPawn, new Point(4, 3)));
        // White moves king
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteKing, new Point(4, 6)));
        // Black moves king
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackKing, new Point(4, 1)));
        // White moves king
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteKing, new Point(3, 5)));
        // Black moves king
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackKing, new Point(3, 2)));
        // White moves king
        Assert.assertTrue(this.gameLogic.moveChessPiece(whiteKing, new Point(2, 5)));
        // Black moves king
        Assert.assertTrue(this.gameLogic.moveChessPiece(blackKing, new Point(2, 2)));

        // Test if they are really there
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(2, 5)));
        Assert.assertNotNull(chessBoard.getChessPieceOfPoint(new Point(2, 2)));
    }
}
