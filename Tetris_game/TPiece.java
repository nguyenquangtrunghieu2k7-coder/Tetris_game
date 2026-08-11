public class TPiece extends Piece{
    public TPiece(int row, int col) {
        super(row,col);
        this.type=PieceType.T;
        this.shape=new int[][] {{0,1,0},{1,1,1}};
        updateOffsets();
    }

    
}
