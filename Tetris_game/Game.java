import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


public class Game {
    private Board board;
    private Piece currentPiece;
    private Piece holdPiece;
    private boolean holdUsed;
    private Queue<PieceType> queue;
    private Random random;
    private int score;
    private boolean gameOver;

    public Game() {
        board = new Board();
        queue = new LinkedList<>();
        holdUsed = false;
        random = new Random();
        score = 0;
        gameOver = false;

        refillBag();
        spawnPiece();

    }
    private void refillBag() {
        List<PieceType> bag = new ArrayList<>(Arrays.asList(PieceType.values()));
        Collections.shuffle(bag, random);
        queue.addAll(bag);
    }
    private void spawnPiece() {
        while (queue.size() <= 5) {
            refillBag();
        }
        currentPiece = PieceFactory.create(queue.poll());

        holdUsed = false;
        if (!board.isValid(currentPiece)) {
            gameOver = true;
        }
    }
    public boolean moveLeft() {
        currentPiece.move(0,-1);
        if (!(board.isValid((currentPiece)))) {
            currentPiece.move(0, 1);
            return false;
        }
        return true;
    }
    public boolean moveRight() {
        currentPiece.move(0,1);
        if (!(board.isValid((currentPiece)))) {
            currentPiece.move(0, -1);
            return false;
        }
        return true;
    }    
    public boolean moveDown() {
        currentPiece.move(1,0);
        if (!(board.isValid((currentPiece)))) {
            currentPiece.move(-1, 0);
            freezePiece();
            return false;
        }
        return true;
    }
    private void freezePiece() {
        board.placePiece(currentPiece);
        int combo = board.clearLines();
        updateScore(combo);
        spawnPiece();
    }
    private void updateScore(int combo) {
        switch (combo) {
            case 1: score += 100; break;
            case 2: score += 300; break;
            case 3: score += 500; break;
            case 4: score += 800; break;
        }
    } 
    public void hardDrop() {
        while (true) { 
            currentPiece.move(1,0);
            if (!(board.isValid((currentPiece)))) {
                currentPiece.move(-1, 0);
                break;
            }
        }
        freezePiece();
    }
    public void hold() {
        if (holdUsed) return;

        holdUsed = true; 
        if (holdPiece == null) {
            holdPiece = currentPiece;
            spawnPiece();
            return;
        }

        PieceType type = holdPiece.getType();
        holdPiece = currentPiece;
        currentPiece = PieceFactory.create(type);

    }
    public boolean rotateCW() {
        return RotationSystem.tryRotateCW(board, currentPiece);
    }

    public boolean rotateCCW() {
        return RotationSystem.tryRotateCCW(board, currentPiece);
    }

    public boolean rotate180() {
        return RotationSystem.tryRotate180(board, currentPiece);
    }   

    public Board getBoard() {
        return board;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Piece getHoldPiece() {
        return holdPiece;
    }

    public Queue<PieceType> getQueue() {
        return new LinkedList<>(queue);
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
