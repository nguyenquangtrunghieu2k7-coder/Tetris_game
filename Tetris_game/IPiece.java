public class IPiece extends Piece{
    public IPiece(int row, int col) {
        super(row,col);
        this.type=PieceType.I;
        this.shape=new int[][] {{1,1,1,1}};
        updateOffsets();
    }
}
