package diskpassport;

public class Decode491 implements Decoder {
    
private String[] s1 =
{
"Long physical sector alignment error reporting is disabled"  ,
"Long physical sector alignment error reporting is enabled"   ,
"Device shall report command aborted when alignment error"    ,
"Reserved"
};

public String decodeValue(long x1)
    {
    int x = (int)x1;
    String s2 = "?";
    if ( x < s1.length ) { s2 = s1[x]; }
    return s2;
    }
    
}
