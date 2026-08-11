public class PieceFactory {

    private PieceFactory() {}

    public static Piece create(PieceType type) {
        switch (type) {
            case I:
                return new IPiece(0, 3);
            case O:
                return new OPiece(0, 4);
            case T:
                return new TPiece(0, 3);
            case S:
                return new SPiece(0, 3);
            case Z:
                return new ZPiece(0, 3);
            case L:
                return new LPiece(0, 3);
            case J:
                return new JPiece(0, 3);
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}