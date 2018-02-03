package diskpassport;

public class Decode21 implements Decoder {
    
    public String decodeValue(long x1)
        {
        String s1="?";
        double x2 = x1;
        s1 = String.format( "%.1f KB" , x2*512/1024.0 );
        return s1;
        }

}
