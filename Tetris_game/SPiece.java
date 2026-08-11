public class SPiece extends Piece{
    public SPiece(int row, int col) {
        super(row,col);
        this.type=PieceType.S;
        this.shape=new int[][] {{0,1,1}, {1,1,0}};
        updateOffsets();

    }
    
}
