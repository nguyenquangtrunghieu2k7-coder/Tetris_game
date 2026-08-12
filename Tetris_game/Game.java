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
            return false;
        }
        return true;
    }

    public void lockPiece() {
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
        lockPiece();
    }
    public void hold() {
        if (holdUsed) return;

        holdUsed = true; 
        if (holdPiece == null) {
            holdPiece = PieceFactory.create(currentPiece.getType());
            spawnPiece();
            return;
        }

        PieceType type = holdPiece.getType();
        PieceType currentType = currentPiece.getType();
        holdPiece = PieceFactory.create(currentType);
        currentPiece = PieceFactory.create(type);
        if (!board.isValid(currentPiece)) {
            gameOver = true;
        }

    }
    public boolean isOnGround() {
        currentPiece.move(1,0);

        boolean result = !board.isValid(currentPiece);

        currentPiece.move(-1,0);

        return result;
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
