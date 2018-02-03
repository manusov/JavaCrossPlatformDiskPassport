package diskpassport;

public class Decode0015 implements Decoder {
    
private String[] s1 =
{
"ATA device"                       ,
"Unknown ATA/ATAPI classification" ,
"ATAPI device"                     ,
"Unknown ATA/ATAPI classification" 
};

public String decodeValue(long x1)
    {
    int x = (int)x1;
    String s2 = "?";
    if ( x < s1.length ) { s2 = s1[x]; }
    return s2;
    }

}
