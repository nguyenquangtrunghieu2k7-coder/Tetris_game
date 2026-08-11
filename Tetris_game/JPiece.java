public class JPiece extends Piece{
    public JPiece(int row, int col) {
        super(row,col);
        this.type=PieceType.J;
        this.shape=new int[][] {{1,0,0}, {1,1,1}};
        updateOffsets();
    }

}
