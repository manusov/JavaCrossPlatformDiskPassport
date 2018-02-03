package diskpassport;

public class Decode60 implements Decoder {

public String decodeValue(long x1)
    {
    String s1="?", s2="";
    double y1 = x1;

    double kb=1000.0,
           mb=kb*kb,
           gb=kb*kb*kb,
           tb=kb*kb*kb*kb,
           pb=kb*kb*kb*kb*kb;
        
    double kib=1024.0,
           mib=kib*kib,
           gib=kib*kib*kib,
           tib=kib*kib*kib*kib,
           pib=kib*kib*kib*kib*kib;
        
    if      ((y1>0)&&(y1<kb))  { s1 = String.format( "%.2f Bytes" , y1 ); }
    else if ((y1>0)&&(y1<mb))  { s1 = String.format( "%.2f KB" , y1/kb ); }
    else if ((y1>0)&&(y1<gb))  { s1 = String.format( "%.2f MB" , y1/mb ); }
    else if ((y1>0)&&(y1<tb))  { s1 = String.format( "%.2f GB" , y1/gb ); }
    else if ((y1>0)&&(y1<pb))  { s1 = String.format( "%.2f TB" , y1/tb ); }

    if      ((y1>0)&&(y1<kib)) { s2 = ""; }
    else if ((y1>0)&&(y1<mib)) { s2 = String.format( " = %.2f KiB" , y1/kib ); }
    else if ((y1>0)&&(y1<gib)) { s2 = String.format( " = %.2f MiB" , y1/mib ); }
    else if ((y1>0)&&(y1<tib)) { s2 = String.format( " = %.2f GiB" , y1/gib ); }
    else if ((y1>0)&&(y1<pib)) { s2 = String.format( " = %.2f TiB" , y1/tib ); }

    return s1+s2;
    }
    
}
