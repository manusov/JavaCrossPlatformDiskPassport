package diskpassport;
import javax.swing.table.AbstractTableModel;

public class DiskSmartTableModel extends AbstractTableModel {
    
private String[] columnsNames =
    {
    "ID"         ,
    "hex"        ,
    "Attribute"  ,
    "Flags hex"  ,
    "Attribute class type" ,
    "Current"    ,
    "Worst"      ,
    "Threshold"  ,
    "Specific"   ,
    "Raw"        ,
    };

private String[] smartFlags =
    {
    "WRN" ,
    "ONL" ,
    "PRF" ,
    "ERR" ,
    "EVN" ,
    "PRS"
    };
    
private String[] stringsId, stringsHex, 
                 stringsAttribute, stringsFlags, stringsStatus,
                 stringsCurrent, stringsWorst, stringsThreshold,
                 stringsSpecific, stringsRaw;

private byte[] smartData;

private int smartLength = 512+512;
// private int smartOffset = 512;

private int columnsCount = 10;
private int rowsCount = 30;

//---------- Table model constructor -------------------------------------------
DiskSmartTableModel() {

//--- Initializing and blank fields ---

    smartData = new byte[smartLength];
    for (int i=0; i<smartLength; i++)
        { smartData[i]=0; }

    int n = rowsCount;
    String s = " ";
    stringsId        = new String[n];
    stringsHex       = new String[n];
    stringsAttribute = new String[n];
    stringsFlags     = new String[n];
    stringsStatus    = new String[n];
    stringsCurrent   = new String[n];
    stringsWorst     = new String[n];
    stringsThreshold = new String[n];
    stringsSpecific  = new String[n];
    stringsRaw       = new String[n];
    
    int i=0;  // cycle counter for required number of rows
    for (i=0; i<n; i++)
        {
        stringsId[i]        = s;
        stringsHex[i]       = s;
        stringsAttribute[i] = s;
        stringsFlags[i]     = s;
        stringsStatus[i]    = s;
        stringsCurrent[i]   = s;
        stringsWorst[i]     = s;
        stringsThreshold[i] = s;
        stringsSpecific[i]  = s;
        stringsRaw[i]       = s;
        }
    
}

//---------- Get strings for output --------------------------------------------
public Object getValueAt (int row, int column)
    { 
        switch (column)
        {
        case 0:  return " " + stringsId[row];
        case 1:  return " " + stringsHex[row];
        case 2:  return " " + stringsAttribute[row];
        case 3:  return " " + stringsFlags[row];
        case 4:  return " " + stringsStatus[row];
        case 5:  return " " + stringsCurrent[row];
        case 6:  return " " + stringsWorst[row];
        case 7:  return " " + stringsThreshold[row];
        case 8:  return " " + stringsSpecific[row];
        case 9:  return " " + stringsRaw[row];
        default: return null;            
        }
    }

//---------- Table configuration -----------------------------------------------
public int getColumnCount ()             { return columnsCount;           }
public int getRowCount ()                { return rowsCount;              }
public boolean isCellEditable ()         { return false;                  }
public String getColumnName (int column) { return columnsNames[column];   }

//---------- Specific methods --------------------------------------------------
public byte[] getSmart()
    {
    byte[] dsb = new byte[smartLength];
    for (int i=0; i<smartLength; i++) { dsb[i] = smartData[i]; }
    return dsb;
    }

public void setSmart(byte[] dsb )
    {
    for (int i=0; i<smartLength; i++) { smartData[i] = dsb[i]; }
    }

public void builtSmart()
    {
//--- Initializing CPR classes ---
    CprAcs acs = new CprAcs();

//--- Initializing fields ---
    int n = rowsCount;
    String s = " ";
    stringsId        = new String[n];
    stringsHex       = new String[n];
    stringsAttribute = new String[n];
    stringsFlags     = new String[n];
    stringsStatus    = new String[n];
    stringsCurrent   = new String[n];
    stringsWorst     = new String[n];
    stringsThreshold = new String[n];
    stringsSpecific  = new String[n];
    stringsRaw       = new String[n];
    
    int i=0;  // cycle counter for required number of rows
    int j=2;  // pointer for SMART data
    int x1,x2,x3,x4,x5,x6,y1,y2;
    for (i=0; i<n; i++) 
        {
        x1 = smartData[j] & 0xFF;
        x2 = ( smartData[j+1] & 0xFF ) + ( ( smartData[j+2] & 0xFF ) << 8 );
        x3 = smartData[j+3] & 0xFF;
        x4 = smartData[j+4] & 0xFF;
        x5 = selectThresholds( x1, j );    // smartData[j+11] & 0xFF;
        y1 = smartData[j+9] & 0xFF;
        y2 = smartData[j+10] & 0xFF;
        
        x6 =   ( smartData[j+5] & 0xFF )         +
             ( ( smartData[j+6] & 0xFF ) <<  8 ) +
             ( ( smartData[j+7] & 0xFF ) << 16 ) +
             ( ( smartData[j+8] & 0xFF ) << 24 );
        
        stringsId[i]        = s + String.format( "%d"   , x1 );
        stringsHex[i]       = s + String.format( "%02X" , x1 );
        stringsAttribute[i] = s + acs.decodeSmartAttribute(x1);
        stringsFlags[i]     = s + String.format( "%04X" , x2 );
        
        stringsStatus[i] = " ";
        int m=1;
        boolean b=false;
        for (int k=0; k<smartFlags.length; k++)
            {
            if ( (x2&m) != 0 )
                {
                if (b) { stringsStatus[i] = stringsStatus[i] + ", "; }
                stringsStatus[i] = stringsStatus[i] + smartFlags[k];
                b=true;
                }
            m = m<<1;
            }
                       
        stringsCurrent[i]   = s + String.format( "%d" , x3 );
        stringsWorst[i]     = s + String.format( "%d" , x4 );
        stringsThreshold[i] = s + String.format( "%d" , x5 );
        stringsSpecific[i]  = s + String.format( "%d, %d" , y1, y2 );
        stringsRaw[i]       = s + String.format( "%d" , x6 );
        j+=12;
        }

    }

//---------- Helper methods ----------------------------------------------------

private int selectThresholds (int value, int index)
    {
    int first = 512+2;
    int count = 30;
    int step = 12;
    int x=0;
    
    for (int i=0; i<count; i++)
        {
        if ( (smartData[ i*step + first ] & 0xFF ) == value)
            { x = smartData[ i*step + first + 1 ]; break; }
        }
            
    if (x==0) { x = smartData[index+11] & 0xFF; }
    
    return x;
    }

}
