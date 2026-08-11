public class LPiece extends Piece {
    public LPiece(int row, int col) {
        super(row,col);
        this.type=PieceType.L;
        this.shape=new int[][] {{0,0,1},{1,1,1}};
        updateOffsets();
    }

}
