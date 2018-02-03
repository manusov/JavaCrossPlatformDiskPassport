package diskpassport;

public class Decode117 implements Decoder {

public String decodeValue(long x1)
    {
    String s1="?";
    if (x1==0)   { return "Default 512 byte logical sectors"; }
    if ( (x1<256) | (x1>2048) | ((x1&0xFF)>0) )
                 { return "Wrong logical sector size"; }
    return String.format("%d Bytes", x1*2);
    }
    
}
