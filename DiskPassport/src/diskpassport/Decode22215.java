package diskpassport;

public class Decode22215 implements Decoder {
private String[] s1 =
{
"Parallel transport"      ,
"Serial transport"        ,
"Reserved transport code"
};

public String decodeValue(long x1)
    {
    int x = (int)x1;
    String s2 = "?";
    if    ( x < s1.length ) { s2 = s1[x]; }
    else  { x = s1.length-1;  s2 = s1[x]; }
    return s2;
    }

    
}
