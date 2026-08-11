import java.util.Arrays;
public class Board {
    private final int ROWS = 23;
    private final int COLS = 10;

    private int[][] grid = new int[ROWS][COLS];
    public boolean isValid(Piece piece) {
        int[][] coor = piece.getAbsoluteCells();
        for (int r=0; r < coor.length; r++) {

                int x = coor[r][0];
                int y = coor[r][1];

                if (x<0 || x>= ROWS || y<0 || y>=COLS) return false;
                if (grid[x][y]!=0) return false;
            }
        return true;
        }
    public void placePiece(Piece piece) {
        int[][] cells = piece.getAbsoluteCells();

        for (int i = 0; i<cells.length; i++) {
            grid[cells[i][0]][cells[i][1]] = 1;
        }
    }

    public int clearLines() {
        int combo = 0;
        for (int i = ROWS-1; i>=0; i--) {
            boolean isLinesFull = true;
            for (int c = 0; c<COLS; c++) {
                if (grid[i][c] == 0) {
                    isLinesFull = false;
                break;
                }
            }
            if (isLinesFull) {
                combo++;
                // Dịch chuyển tất cả các hàng phía trên hàng r xuống 1 nấc
                for (int k = i; k>0; k--) {
                    grid[k] = grid[k-1].clone();
                }
                // Tạo mới hoàn toàn hàng trên cùng (hàng số 0) thành hàng trống
                grid[0]=new int[grid[0].length];
                // Vì hàng phía trên vừa dồn xuống vị trí r này cũng cần phải kiểm tra, 
                // nên ta phải tăng r thêm 1 để vòng lặp kế tiếp quét lại chính hàng r hiện tại.
                //De no tu don cac o khi no rot xuong
                i++; 
            }
        }
        return combo;
    }
    public int[][] getGrid() {
        return grid;
    }
    public void clear() {
        for (int r=0; r<ROWS; r++) {
            Arrays.fill(grid[r], 0);
        }
    }
    public boolean isGameOver(Piece piece){
        return !isValid(piece);
    }
    

}

