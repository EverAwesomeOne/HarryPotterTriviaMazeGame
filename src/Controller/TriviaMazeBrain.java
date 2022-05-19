package Controller;

import Model.Direction;
import Model.Door;
import Model.Maze;
import Model.QuestionAnswer;
import View.*;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TriviaMazeBrain {
    private Connection myConnection;
    private Statement myStatement;
    private Maze myMaze;
    private GamePanel myGamePanel;
    private MainFrame myMainFrame;
    private MazePanel myMazePanel;

    public final static int MAZE_LENGTH = 4;

    public static void main(final String[] theArgs) {
        new TriviaMazeBrain();
    }

    public TriviaMazeBrain() {
        runGame();
    }

    private void runGame() {
        myMaze = new Maze(MAZE_LENGTH);
        openDatabaseConnection();
        myMainFrame = new MainFrame(this);

    }

    public void move(final String theDirectionType) {
        myGamePanel = myMainFrame.getTheMainMenuPanel().getGamePanel();
        myMazePanel = myGamePanel.getMyMazePanel();
        final Direction directionToMove = Direction.valueOf(theDirectionType);
        final Door chosenDoor = myMaze.getCurrentRoom().getDoor(directionToMove);
        // check first time of door to move freely
        final QuestionAnswer qa = chosenDoor.getQuestion();
        qa.getQuestionAnswerFromDatabase(myStatement);
        myGamePanel.askQuestion(qa.getQuestionList(), theDirectionType);
    }

    public void move2 (final String theUserAnswer, final String theDirectionType) {

        final Direction directionToMove = Direction.valueOf(theDirectionType);
        final Door chosenDoor = myMaze.getCurrentRoom().getDoor(directionToMove);
        final QuestionAnswer qa = chosenDoor.getQuestion();

        if (!qa.selectedCorrectAnswer(theUserAnswer)) {
            chosenDoor.lockDoor();
            myMaze.removeEdgeFromGraph(Direction.valueOf(theDirectionType));
        }

        if (!myMaze.hasValidPaths()) {
            myGamePanel.displayLosingMessageBox();
        }

        if(!chosenDoor.isLocked()) {
            myMaze.updatePosition(directionToMove);
        }
        myMazePanel.updateCharacterPlacement(myMaze.getCharacterRow(),
                myMaze.getCharacterColumn());
    }

    public boolean checkIsLockedStatus(final String theDirectionType) {
        return !myMaze.getCurrentRoom().getDoor
                (Direction.valueOf(theDirectionType)).isLocked();
    }

    public void resetGameState() {
        myMaze = new Maze(MAZE_LENGTH);
    }

    public void openDatabaseConnection() {
        SQLiteDataSource ds = null;

        //establish connection (creates db file if it does not exist :-)
        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:questions.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        try {
            myConnection = ds.getConnection();
            myStatement = myConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void closeDatabaseConnection() {
        if (myStatement != null) {
            try {
                myStatement.close();
            } catch (SQLException e) { /* Ignored */}
        }
        if (myConnection != null) {
            try {
                myConnection.close();
            } catch (SQLException e) { /* Ignored */}
        }
    }
}
