package diskpassport;

public class Decode932 implements Decoder {
private String[] s1 =
{
"Device 0 determination method reserved" ,
"Device 0: jumper was used" ,
"Device 0: CSEL signal was used" ,
"Device 0: unknown determination method"
};
public String decodeValue(long x1)
    {
    String s2="?";
    int x = (int)x1;
    if ( x < s1.length ) { s2 = s1[x]; }
    return s2; }
   
}
