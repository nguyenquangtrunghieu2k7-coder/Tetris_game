import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameEngine {

// =========================
// Timing
// =========================

private static final int FPS = 60;
private static final int FRAME_TIME = 1000 / FPS;

private static final int DAS = 200;
private static final int ARR = 50;

private static final int GRAVITY = 500;

private static final int LOCK_DELAY = 500;
private static final int MAX_LOCK_RESETS = 15;

// =========================
// Core
// =========================

private Game game;
private GameWindow window;

private Timer timer;

// =========================
// Input
// =========================

private boolean leftHeld = false;
private boolean rightHeld = false;

private int currentDirection = 0;   // -1 left, +1 right, 0 none
private int dasCounter = 0;
private int arrCounter = 0;

// =========================
// Gravity
// =========================

private int gravityCounter = 0;

// =========================
// Lock delay
// =========================

private boolean onGround = false;
private int lockCounter = 0;
private int lockResets = 0;

public GameEngine() {

    game = new Game();
    window = new GameWindow(game, this);

    timer = new Timer(FRAME_TIME, new GameLoop());
    timer.start();
}

// =====================================================
// Game loop
// =====================================================

private class GameLoop implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!game.isGameOver()) {

            updateInput(FRAME_TIME);
            updateGravity(FRAME_TIME);
            updateLockDelay(FRAME_TIME);
        }

        window.refresh();
    }
}

// =====================================================
// Input (DAS / ARR)
// =====================================================

private void updateInput(int delta) {

    int direction = 0;

    if (leftHeld && !rightHeld) {
        direction = -1;
    } else if (rightHeld && !leftHeld) {
        direction = 1;
    }

    if (direction == 0) {

        currentDirection = 0;
        dasCounter = 0;
        arrCounter = 0;

        return;
    }

    if (direction != currentDirection) {

        currentDirection = direction;
        dasCounter = 0;
        arrCounter = 0;

        if (direction == -1) {
            if (game.moveLeft()) resetLockDelay();
        } else {
            if (game.moveRight()) resetLockDelay();
        }

        return;
    }

    dasCounter += delta;

    if (dasCounter < DAS) return;

    arrCounter += delta;

    while (arrCounter >= ARR) {

        boolean moved;

        if (direction == -1) {
            moved = game.moveLeft();
        } else {
            moved = game.moveRight();
        }

        if (moved) {
            resetLockDelay();
        }

        arrCounter -= ARR;
    }
}

// =====================================================
// Gravity
// =====================================================

private void updateGravity(int delta) {

    gravityCounter += delta;

    if (gravityCounter < GRAVITY) return;

    gravityCounter -= GRAVITY;

    boolean moved = game.moveDown();

    onGround = game.isOnGround();

    if (moved) {
        lockCounter = 0;
        lockResets = 0;
    }
}

// =====================================================
// Lock delay
// =====================================================

private void updateLockDelay(int delta) {

    if (!onGround) return;

    lockCounter += delta;

    if (lockCounter >= LOCK_DELAY) {

        game.lockPiece();

        onGround = false;
        lockCounter = 0;
        lockResets = 0;
    }
}

private void resetLockDelay() {

    if (!onGround) return;

    if (lockResets >= MAX_LOCK_RESETS) return;

    lockCounter = 0;
    lockResets++;
}

// =====================================================
// Input API (GameWindow gọi)
// =====================================================

public void pressLeft() {
    leftHeld = true;
}

public void releaseLeft() {
    leftHeld = false;
}

public void pressRight() {
    rightHeld = true;
}

public void releaseRight() {
    rightHeld = false;
}

public void softDrop() {
    boolean moved = game.moveDown();

    onGround = game.isOnGround();

    if (moved) {
        lockCounter = 0;
        lockResets = 0;
    }
}

public void hardDrop() {
    game.hardDrop();
    onGround = false;
    lockCounter = 0;
    lockResets = 0;
}

public void rotateCW() {
    if (game.rotateCW()) {
        resetLockDelay();
    }
}

public void rotateCCW() {
    if (game.rotateCCW()) {
        resetLockDelay();
    }
}

public void rotate180() {
    if (game.rotate180()) {
        resetLockDelay();
    }
}

public void hold() {
    game.hold();

    onGround = game.isOnGround();
    lockCounter = 0;
    lockResets = 0;
}

// =====================================================
// Restart
// =====================================================

public void restart() {

    timer.stop();
    window.dispose();

    SwingUtilities.invokeLater(GameEngine::new);
}

}
