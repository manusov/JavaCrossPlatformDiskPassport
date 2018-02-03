package diskpassport;

public class Decode02 implements Decoder {
private String s1[] = 
{        
"Requires SET FEATURES to spin-up, incomplete IDENTIFY DEVICE"          ,
"Requires SET FEATURES to spin-up, complete IDENTIFY DEVICE"            ,
"Does not requires SET FEATURES to spin-up, incomplete IDENTIFY DEVICE" ,
"Does not requires SET FEATURES to spin-up, complete IDENTIFY DEVICE"   ,
};
private String s2 = "Unknown spin-up requirements code";
private int[] i1 = { 0x37C8 , 0x738C , 0x8C73 , 0xC837 };
    
public String decodeValue(long x1)
    {
    String s3 = s2;
    for (int i=0; i<s1.length; i++)
        {
        if (x1==i1[i]) { s3 = s1[i]; break; }
        }
    return s3;
    }

}
