public class ZPiece extends Piece{
    public ZPiece(int row, int col) {
        super(row,col);
        this.type = PieceType.Z;
        this.shape=new int[][] {{1,1,0}, {0,1,1}};
        updateOffsets();
    }
    
}
