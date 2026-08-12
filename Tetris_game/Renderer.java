import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Renderer {


private static final int WINDOW_W = 800;
private static final int WINDOW_H = 700;

private static final int CELL = 30;

private static final int BOARD_X = 180;
private static final int BOARD_Y = 20;

private static final int HOLD_X = 20;
private static final int HOLD_Y = 40;

private static final int QUEUE_X = 520;
private static final int QUEUE_Y = 40;

private static final int VISIBLE_ROWS = 20;
private static final int HIDDEN_ROWS = 3;

private static final int PREVIEW_W = 120;
private static final int PREVIEW_H = 80;

private static final Color[] COLORS = {
        new Color(30, 30, 30),      // empty
        new Color(0, 255, 255),     // I
        new Color(255, 255, 0),     // O
        new Color(160, 0, 240),     // T
        new Color(0, 255, 0),       // S
        new Color(255, 0, 0),       // Z
        new Color(0, 0, 255),       // J
        new Color(255, 140, 0)      // L
};

public void render(Graphics2D g, Game game) {

    g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
    );

    drawBackground(g);
    drawBoard(g, game.getBoard());
    drawShadowPiece(g, game);
    drawCurrentPiece(g, game.getCurrentPiece());
    drawHold(g, game.getHoldPiece());
    drawQueue(g, game.getQueue());
    drawHUD(g, game.getScore());

    if (game.isGameOver()) {
        drawGameOver(g);
    }
}

// =====================================================
// Background
// =====================================================

private void drawBackground(Graphics2D g) {
    g.setColor(new Color(18, 18, 18));
    g.fillRect(0, 0, WINDOW_W, WINDOW_H);
}

// =====================================================
// Board
// =====================================================

private void drawBoard(Graphics2D g, Board board) {

    int[][] grid = board.getGrid();

    g.setColor(new Color(35, 35, 35));
    g.fillRect(
            BOARD_X,
            BOARD_Y,
            10 * CELL,
            VISIBLE_ROWS * CELL
    );

    for (int r = HIDDEN_ROWS; r < HIDDEN_ROWS + VISIBLE_ROWS; r++) {

        for (int c = 0; c < 10; c++) {

            int value = grid[r][c];

            int x = BOARD_X + c * CELL;
            int y = BOARD_Y + (r - HIDDEN_ROWS) * CELL;

            if (value == 0) {

                g.setColor(new Color(50, 50, 50));
                g.drawRect(x, y, CELL, CELL);

            } else {

                drawCell(g, x, y, COLORS[value]);
            }
        }
    }

    g.setColor(Color.WHITE);
    g.drawRect(
            BOARD_X,
            BOARD_Y,
            10 * CELL,
            VISIBLE_ROWS * CELL
    );
}

// =====================================================
// Shadow piece
// =====================================================

private void drawShadowPiece(Graphics2D g, Game game) {

    Piece piece = game.getCurrentPiece();

    if (piece == null) return;

    int[] original = piece.getPosition();

    while (true) {
        piece.move(1, 0);

        if (!game.getBoard().isValid(piece)) {
            piece.move(-1, 0);
            break;
        }
    }

    int[][] cells = piece.getAbsoluteCells();

    piece.setPosition(original[0], original[1]);

    Color color = COLORS[piece.getType().ordinal() + 1];

    for (int[] cell : cells) {

        int row = cell[0];
        int col = cell[1];

        if (row < HIDDEN_ROWS) continue;

        int x = BOARD_X + col * CELL;
        int y = BOARD_Y + (row - HIDDEN_ROWS) * CELL;

        drawGhostCell(g, x, y, color);
    }
}

// =====================================================
// Current piece
// =====================================================

private void drawCurrentPiece(Graphics2D g, Piece piece) {

    if (piece == null) return;

    int[][] cells = piece.getAbsoluteCells();

    Color color = COLORS[piece.getType().ordinal() + 1];

    for (int[] cell : cells) {

        int row = cell[0];
        int col = cell[1];

        if (row < HIDDEN_ROWS) continue;

        int x = BOARD_X + col * CELL;
        int y = BOARD_Y + (row - HIDDEN_ROWS) * CELL;

        drawCell(g, x, y, color);
    }
}

// =====================================================
// Hold
// =====================================================

private void drawHold(Graphics2D g, Piece hold) {

    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    g.drawString("HOLD", HOLD_X, HOLD_Y - 10);

    g.setColor(new Color(40, 40, 40));
    g.fillRect(HOLD_X, HOLD_Y, PREVIEW_W, PREVIEW_W);

    g.setColor(Color.WHITE);
    g.drawRect(HOLD_X, HOLD_Y, PREVIEW_W, PREVIEW_W);

    if (hold != null) {
        drawPreviewPiece(g, hold, HOLD_X, HOLD_Y, PREVIEW_W, PREVIEW_W);
    }
}

// =====================================================
// Next queue
// =====================================================

private void drawQueue(Graphics2D g,
                       Queue<PieceType> queue) {

    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 18));
    g.drawString("NEXT", QUEUE_X, QUEUE_Y - 10);

    List<PieceType> list = new ArrayList<>(queue);

    int y = QUEUE_Y;

    for (int i = 0; i < Math.min(5, list.size()); i++) {

        g.setColor(new Color(40, 40, 40));
        g.fillRect(QUEUE_X, y, PREVIEW_W, PREVIEW_H);

        g.setColor(Color.WHITE);
        g.drawRect(QUEUE_X, y, PREVIEW_W, PREVIEW_H);

        Piece preview = PieceFactory.create(list.get(i));

        drawPreviewPiece(g, preview,
                QUEUE_X,
                y,
                PREVIEW_W,
                PREVIEW_H);

        y += PREVIEW_H + 10;
    }
}

// =====================================================
// Preview piece
// =====================================================

private void drawPreviewPiece(Graphics2D g,
                              Piece piece,
                              int boxX,
                              int boxY,
                              int boxW,
                              int boxH) {

    int[][] shape = piece.getShape();

    int mini = CELL / 2;

    int width = shape[0].length * mini;
    int height = shape.length * mini;

    int startX = boxX + (boxW - width) / 2;
    int startY = boxY + (boxH - height) / 2;

    Color color = COLORS[piece.getType().ordinal() + 1];

    for (int r = 0; r < shape.length; r++) {

        for (int c = 0; c < shape[r].length; c++) {

            if (shape[r][c] == 0) continue;

            drawMiniCell(
                    g,
                    startX + c * mini,
                    startY + r * mini,
                    color
            );
        }
    }
}

// =====================================================
// HUD
// =====================================================

private void drawHUD(Graphics2D g, int score) {

    g.setColor(Color.WHITE);

    g.setFont(new Font("Arial", Font.BOLD, 22));
    g.drawString("SCORE", QUEUE_X, 540);

    g.setFont(new Font("Arial", Font.PLAIN, 22));
    g.drawString(String.valueOf(score), QUEUE_X, 570);
}

// =====================================================
// Game over
// =====================================================

private void drawGameOver(Graphics2D g) {

    g.setColor(new Color(0, 0, 0, 180));
    g.fillRect(0, 0, WINDOW_W, WINDOW_H);

    g.setColor(Color.WHITE);

    g.setFont(new Font("Arial", Font.BOLD, 42));

    FontMetrics fm = g.getFontMetrics();

    String text = "GAME OVER";

    int x = (WINDOW_W - fm.stringWidth(text)) / 2;
    int y = 300;

    g.drawString(text, x, y);

    g.setFont(new Font("Arial", Font.PLAIN, 20));

    String sub = "Press R to restart";

    fm = g.getFontMetrics();

    x = (WINDOW_W - fm.stringWidth(sub)) / 2;

    g.drawString(sub, x, y + 40);
}

// =====================================================
// Cell drawing
// =====================================================

private void drawCell(Graphics2D g,
                      int x,
                      int y,
                      Color color) {

    g.setColor(color);
    g.fillRect(x, y, CELL, CELL);

    g.setColor(color.brighter());
    g.drawLine(x, y, x + CELL - 1, y);
    g.drawLine(x, y, x, y + CELL - 1);

    g.setColor(color.darker());
    g.drawLine(
            x + CELL - 1,
            y,
            x + CELL - 1,
            y + CELL - 1
    );
    g.drawLine(
            x,
            y + CELL - 1,
            x + CELL - 1,
            y + CELL - 1
    );
}

private void drawGhostCell(Graphics2D g,
                           int x,
                           int y,
                           Color color) {

    g.setColor(new Color(
            color.getRed(),
            color.getGreen(),
            color.getBlue(),
            80
    ));

    g.drawRect(x + 1, y + 1, CELL - 2, CELL - 2);
}

private void drawMiniCell(Graphics2D g,
                          int x,
                          int y,
                          Color color) {

    int s = CELL / 2;

    g.setColor(color);
    g.fillRect(x, y, s, s);

    g.setColor(Color.BLACK);
    g.drawRect(x, y, s, s);
}


}
