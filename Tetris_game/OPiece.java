public class OPiece extends Piece{
    public OPiece(int row, int col) {
        super(row,col);
        this.type=PieceType.O;
        this.shape=new int[][] {{1,1},{1,1}};
    }
    @Override
    public void rotateCW(){
        //k lam gi
    }
}
