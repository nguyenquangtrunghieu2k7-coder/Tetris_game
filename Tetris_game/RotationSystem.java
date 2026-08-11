import java.util.HashMap;
import java.util.Map;

public final class RotationSystem {

    private RotationSystem() {}

    private static final Map<String, int[][]> NORMAL_KICKS = new HashMap<>();
    private static final Map<String, int[][]> I_KICKS = new HashMap<>();

    static {
        // =========================
        // NORMAL PIECES (J, L, S, Z, T)
        // =========================

        NORMAL_KICKS.put("0->1", new int[][]{{0,0},{-1,0},{-1,-1},{0,2},{-1,2}});
        NORMAL_KICKS.put("1->0", new int[][]{{0,0},{1,0},{1,1},{0,-2},{1,-2}});

        NORMAL_KICKS.put("1->2", new int[][]{{0,0},{1,0},{1,1},{0,-2},{1,-2}});
        NORMAL_KICKS.put("2->1", new int[][]{{0,0},{-1,0},{-1,-1},{0,2},{-1,2}});

        NORMAL_KICKS.put("2->3", new int[][]{{0,0},{1,0},{1,-1},{0,2},{1,2}});
        NORMAL_KICKS.put("3->2", new int[][]{{0,0},{-1,0},{-1,1},{0,-2},{-1,-2}});

        NORMAL_KICKS.put("3->0", new int[][]{{0,0},{-1,0},{-1,1},{0,-2},{-1,-2}});
        NORMAL_KICKS.put("0->3", new int[][]{{0,0},{1,0},{1,-1},{0,2},{1,2}});

        // -------- 180° rotation --------

        NORMAL_KICKS.put("0->2", new int[][]{
                {0,0},{0,-1},{1,-1},{-1,-1},{1,0},{-1,0}
        });

        NORMAL_KICKS.put("1->3", new int[][]{
                {0,0},{1,0},{1,-2},{1,-1},{0,-2},{0,-1}
        });

        NORMAL_KICKS.put("2->0", new int[][]{
                {0,0},{0,1},{-1,1},{1,1},{-1,0},{1,0}
        });

        NORMAL_KICKS.put("3->1", new int[][]{
                {0,0},{-1,0},{-1,-2},{-1,-1},{0,-2},{0,-1}
        });

        // =========================
        // I PIECE
        // =========================

        I_KICKS.put("0->1", new int[][]{{0,0},{-2,0},{1,0},{-2,1},{1,-2}});
        I_KICKS.put("1->0", new int[][]{{0,0},{2,0},{-1,0},{2,-1},{-1,2}});

        I_KICKS.put("1->2", new int[][]{{0,0},{-1,0},{2,0},{-1,-2},{2,1}});
        I_KICKS.put("2->1", new int[][]{{0,0},{1,0},{-2,0},{1,2},{-2,-1}});

        I_KICKS.put("2->3", new int[][]{{0,0},{2,0},{-1,0},{2,-1},{-1,2}});
        I_KICKS.put("3->2", new int[][]{{0,0},{-2,0},{1,0},{-2,1},{1,-2}});

        I_KICKS.put("3->0", new int[][]{{0,0},{1,0},{-2,0},{1,2},{-2,-1}});
        I_KICKS.put("0->3", new int[][]{{0,0},{-1,0},{2,0},{-1,-2},{2,1}});

        // -------- 180° rotation --------

        I_KICKS.put("0->2", new int[][]{
                {0,0},{-1,0},{-2,0},{1,0},{-2,-1},{1,1}
        });

        I_KICKS.put("1->3", new int[][]{
                {0,0},{0,1},{0,2},{0,-1},{-1,2},{1,-1}
        });

        I_KICKS.put("2->0", new int[][]{
                {0,0},{1,0},{2,0},{-1,0},{2,1},{-1,-1}
        });

        I_KICKS.put("3->1", new int[][]{
                {0,0},{0,-1},{0,-2},{0,1},{1,-2},{-1,1}
        });
    }

    public static boolean tryRotateCW(Board board, Piece piece) {
        return rotate(board, piece, 1);
    }

    public static boolean tryRotateCCW(Board board, Piece piece) {
        return rotate(board, piece, -1);
    }

    public static boolean tryRotate180(Board board, Piece piece) {
        return rotate(board, piece, 2);
    }

    private static boolean rotate(Board board, Piece piece, int direction) {

        if (piece.getType() == PieceType.O) {
            return true;
        }

        int[][] oldShape = piece.getShape();
        int oldRow = piece.getRow();
        int oldCol = piece.getCol();
        int oldState = piece.getRotationState();

        if (direction == 1) {
            piece.rotateCW();
        } else if (direction == -1) {
            piece.rotateCCW();
        } else {
            piece.rotateCW();
            piece.rotateCW();
        }

        int newState = piece.getRotationState();

        Map<String, int[][]> table =
                piece.getType() == PieceType.I ? I_KICKS : NORMAL_KICKS;

        String key = oldState + "->" + newState;
        int[][] tests = table.get(key);

        if (tests == null) {
            if (board.isValid(piece)) {
                return true;
            }

            piece.restoreState(oldShape, oldRow, oldCol, oldState);
            return false;
        }

        for (int[] kick : tests) {

            int dx = kick[0];
            int dy = kick[1];

            piece.setPosition(oldRow + dy, oldCol + dx);

            if (board.isValid(piece)) {
                return true;
            }
        }

        piece.restoreState(oldShape, oldRow, oldCol, oldState);
        return false;
    }
}