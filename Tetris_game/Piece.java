import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected int[][] shape;
    protected int row;
    protected int col;
    protected int rotationState;
    protected int[][] offsets;
    protected PieceType type;
    
    
    public Piece(int row, int col) {
        this.row = row;
        this.col=col;
        this.rotationState= 0;
    }
    public void rotateCW() { 
        shape = rotateMatrix(shape);
        rotationState = (rotationState + 1)%4;
        updateOffsets();
    }
    public void rotateCCW() {
        shape = rotateMatrixCCW(shape);
        rotationState = (rotationState + 3)%4;
        updateOffsets();
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;

    }

    protected int[][] rotateMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] rotated = new int[cols][rows];
        //Rotate 90* = dao nguoc thu tu cac hang -> chuyen vi 
        //[::-1].T (trong Python)
        for (int r = 0 ; r<rows; r++) {
            for (int c=0; c < cols; c++) {

                /* Hàng đầu tiên của ma trận cũ sẽ biến thành Cột cuối cùng của ma trận mới.
                Hàng thứ hai của ma trận cũ sẽ biến thành Cột áp chót của ma trận mới.
                Và cứ tiếp tục như vậy... */

                rotated[c][rows - 1 - r] = matrix[r][c];

                /* Hãy nhớ quy trình:

                Duyệt từng ô của ma trận cũ.
                Tính vị trí mới của ô đó.
                Đặt nó vào ma trận mới.
                Khi quen rồi, chỉ cần nhớ một dòng:
                Xoay 90° clockwise = (r, c) → (c, rows - 1 - r) */
                
            }

        }
        return rotated;
    }
    public void restoreState(int[][] shape, int row, int col, int rotationState) {
        this.shape = shape;
        this.row = row;
        this.col = col;
        this.rotationState = rotationState;
        updateOffsets();
    }
    protected int[][] rotateMatrixCCW(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];

        for (int c = 0; c< cols; c++) {
            for (int r = 0; r< rows; r++) {
                rotated[cols -1 -c][r] = matrix[r][c];
            }
        }
        return rotated;
    }
    protected void updateOffsets() {
        List<int[]> cells = new ArrayList<>();
        int rows = shape.length;
        int cols = shape[0].length;
        /*  updateOffsets() thực chất là:
            quét shape rồi lấy tọa độ của tất cả ô có block. */
        for (int r = 0; r<rows; r++) {
            for (int c = 0; c<cols; c++) {
                if (shape[r][c] == 1){
                    cells.add(new int[] {r,c});
                }
            } 
        }
        offsets = cells.toArray( new int[cells.size()][]);
    }
    public int[][] getAbsoluteCells() {
        int[][] cells = new int[offsets.length][2];
        //sau khi updateOffsets thi +row, +col de xem no o dau trong Board(arena)
        for (int i = 0; i<offsets.length; i++) {
            cells[i][0] = row + offsets[i][0];
            cells[i][1] = col + offsets[i][1];
        }
        return cells;
    }
    public int getRotationState() {
        return rotationState;
    }
    public void move(int dr, int dc) {
        row +=dr;
        col +=dc;
    }
    
    public int[][] getShape() {
        int[][] copy = new int[shape.length][];
        for (int i = 0; i< shape.length; i++) {
            copy[i]=shape[i].clone();
        }
        return copy;
    }
    public PieceType getType() {
        return type;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int[][] getOffsets() {
        int[][] copy = new int[offsets.length][];
        for (int i = 0; i< offsets.length; i++) {
            copy[i]=offsets[i].clone();
        }
        return copy;
    }




}
