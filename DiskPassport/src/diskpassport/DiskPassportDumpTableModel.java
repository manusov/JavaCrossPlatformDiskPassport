package diskpassport;
import javax.swing.table.AbstractTableModel;

public class DiskPassportDumpTableModel extends AbstractTableModel {
    
private String[] columnsNames =
    {
    "Parameter"  ,
    "Word(s) #"  ,
    "Value, hex"        ,
    "Comments"
    };

//---------- Names for device data, words 0-255 (512 bytes) names --------------
    String[] wordsNamesConst =
    {
    "Device common info"                                     ,  // word 0
    "Number of logical cylinders"                            ,  // 1
    "Specific configuration"                                 ,  // 2
    "Number of logical heads"                                ,  // 3
    "Vendor-specific"                                        ,  // 4
    "Vendor-specific"                                        ,  // 5
    "Number of logical sectors per track"                    ,  // 6
    "CFA parameters word 1"                                  ,  // 7
    "CFA parameters word 2"                                  ,  // 8
    "Retired"                                                ,  // 9
    "Serial number word 1"                                   ,  // 10
    "Serial number word 2"                                   ,  // 11
    "Serial number word 3"                                   ,  // 12
    "Serial number word 4"                                   ,  // 13
    "Serial number word 5"                                   ,  // 14
    "Serial number word 6"                                   ,  // 15
    "Serial number word 7"                                   ,  // 16
    "Serial number word 8"                                   ,  // 17
    "Serial number word 9"                                   ,  // 18
    "Serial number word 10"                                  ,  // 19
    "Retired"                                                ,  // 20
    "Retired"                                                ,  // 21
    "Number of ECC bytes for READ/WRITE LONG"                ,  // 22
    "Firmware revision word 1"                               ,  // 23
    "Firmware revision word 2"                               ,  // 24
    "Firmware revision word 3"                               ,  // 25
    "Firmware revision word 4"                               ,  // 26
    "Drive model word 1"                                     ,  // 27
    "Drive model word 2"                                     ,  // 28
    "Drive model word 3"                                     ,  // 29
    "Drive model word 4"                                     ,  // 30
    "Drive model word 5"                                     ,  // 31
    "Drive model word 6"                                     ,  // 32
    "Drive model word 7"                                     ,  // 33
    "Drive model word 8"                                     ,  // 34
    "Drive model word 9"                                     ,  // 35
    "Drive model word 10"                                    ,  // 36
    "Drive model word 11"                                    ,  // 37
    "Drive model word 12"                                    ,  // 38
    "Drive model word 13"                                    ,  // 39
    "Drive model word 14"                                    ,  // 40
    "Drive model word 15"                                    ,  // 41
    "Drive model word 16"                                    ,  // 42
    "Drive model word 17"                                    ,  // 43
    "Drive model word 18"                                    ,  // 44
    "Drive model word 19"                                    ,  // 45
    "Drive model word 20"                                    ,  // 46
    "Number of sectors per data request"                     ,  // 47
    "Trusted computing feature set options"                  ,  // 48
    "Device capabilities word 1"                             ,  // 49
    "Device capabilities word 2"                             ,  // 50
    "PIO mode number"                                        ,  // 51
    "Obsolete"                                               ,  // 52
    "Free fall control and validity flags"                   ,  // 53
    "Current number of logical cylinders"                    ,  // 54
    "Current number of logical heads"                        ,  // 55
    "Current number of logical sectors per logical track"    ,  // 56
    "Current total number of sectors, low word"              ,  // 57
    "Current total number of sectors, high word"             ,  // 58
    "Security features and Sectors per interrupt (SPI)"      ,  // 59
    "Total number of sectors (28-bit mode), low word"        ,  // 60
    "Total number of sectors (28-bit mode), high word"       ,  // 61
    "Obsolete"                                               ,  // 62
    "Multiword DMA mode supported and selected"              ,  // 63
    "Advanced PIO modes"                                     ,  // 64
    "Minimum multiword DMA transfer cycle time per word"     ,  // 65
    "Recommended multiword DMA transfer cycle time"          ,  // 66
    "Minimum PIO transfer cycle time w/o flow control"       ,  // 67
    "Minimum PIO transfer cycle time with IORDY flow control",  // 68
    "Additional features"                                    ,  // 69
    "Reserved"                                               ,  // 70
    "Reserved for IDENTIFY PACKET DEVICE, word 1"            ,  // 71
    "Reserved for IDENTIFY PACKET DEVICE, word 2"            ,  // 72
    "Reserved for IDENTIFY PACKET DEVICE, word 3"            ,  // 73
    "Reserved for IDENTIFY PACKET DEVICE, word 4"            ,  // 74
    "Queue depth"                                            ,  // 75
    "Serial ATA capabilities"                                ,  // 76
    "Serial ATA additional capabilities"                     ,  // 77
    "Serial ATA features supported"                          ,  // 78
    "Serial ATA features enabled"                            ,  // 79
    "Major version number"                                   ,  // 80
    "Minor version number"                                   ,  // 81
    "Commands and features supported, word 1"                ,  // 82
    "Commands and features supported, word 2"                ,  // 83
    "Commands and features supported, word 3"                ,  // 84
    "Commands and features supported or enabled, word 4"     ,  // 85
    "Commands and features supported or enabled, word 5"     ,  // 86
    "Commands and features supported or enabled, word 6"     ,  // 87
    "Ultra DMA modes supported and selected"                 ,  // 88
    "Time for normal erase mode SECURITY ERASE UNIT"         ,  // 89
    "Time for enhanced erase mode SECURITY ERASE UNIT"       ,  // 90
    "Current APM level value"                                ,  // 91
    "Master password identifier"                             ,  // 92
    "Hardware reset results"                                 ,  // 93
    "Automatic acoustic management"                          ,  // 94
    "Stream minimum request size"                            ,  // 95
    "Streaming transfer time - DMA"                          ,  // 96
    "Streaming access latency - DMA and PIO"                 ,  // 97
    "Streaming performance granularity, low word"            ,  // 98
    "Streaming performance granularity, high word"           ,  // 99
    "Number of user addressable logical sectors, word 1"     ,  // 100
    "Number of user addressable logical sectors, word 2"     ,  // 101
    "Number of user addressable logical sectors, word 3"     ,  // 102
    "Number of user addressable logical sectors, word 4"     ,  // 103
    "Streaming transfer time - PIO"                          ,  // 104
    "Max. num. of 512-b blocks per DATA SET MANAGEMENT"      ,  // 105
    "Physical and logical sector size"                       ,  // 106
    "Inter-seek delay for ISO/IEC 7779 acoustic test"        ,  // 107
    "World wide name, word 1"                                ,  // 108
    "World wide name, word 2"                                ,  // 109
    "World wide name, word 3"                                ,  // 110
    "World wide name, word 4"                                ,  // 111
    "Reserved"                                               ,  // 112   
    "Reserved"                                               ,  // 113
    "Reserved"                                               ,  // 114
    "Reserved"                                               ,  // 115
    "Obsolete"                                               ,  // 116
    "Logical sector size, word 1"                            ,  // 117
    "Logical sector size, word 2"                            ,  // 118
    "Commands and features sets supported, word 1"           ,  // 119
    "Commands and features sets supported, word 2"           ,  // 120
    "Rsvd. for expanded sup. and enab. settings, word 1"     ,  // 121
    "Rsvd. for expanded sup. and enab. settings, word 2"     ,  // 122
    "Rsvd. for expanded sup. and enab. settings, word 3"     ,  // 123
    "Rsvd. for expanded sup. and enab. settings, word 4"     ,  // 124
    "Rsvd. for expanded sup. and enab. settings, word 5"     ,  // 125
    "Rsvd. for expanded sup. and enab. settings, word 6"     ,  // 126
    "Media change notification method"                       ,  // 127
    "Security status"                                        ,  // 128
    "Vendor-specific, word 1"                                ,  // 129
    "Vendor-specific, word 2"                                ,  // 130
    "Vendor-specific, word 3"                                ,  // 131
    "Vendor-specific, word 4"                                ,  // 132
    "Vendor-specific, word 5"                                ,  // 133
    "Vendor-specific, word 6"                                ,  // 134
    "Vendor-specific, word 7"                                ,  // 135
    "Vendor-specific, word 8"                                ,  // 136
    "Vendor-specific, word 9"                                ,  // 137
    "Vendor-specific, word 10"                               ,  // 138
    "Vendor-specific, word 11"                               ,  // 139
    "Vendor-specific, word 12"                               ,  // 140
    "Vendor-specific, word 13"                               ,  // 141
    "Vendor-specific, word 14"                               ,  // 142
    "Vendor-specific, word 15"                               ,  // 143
    "Vendor-specific, word 16"                               ,  // 144
    "Vendor-specific, word 17"                               ,  // 145
    "Vendor-specific, word 18"                               ,  // 146
    "Vendor-specific, word 19"                               ,  // 147
    "Vendor-specific, word 20"                               ,  // 148
    "Vendor-specific, word 21"                               ,  // 149
    "Vendor-specific, word 22"                               ,  // 150
    "Vendor-specific, word 23"                               ,  // 151
    "Vendor-specific, word 24"                               ,  // 152
    "Vendor-specific, word 25"                               ,  // 153
    "Vendor-specific, word 26"                               ,  // 154
    "Vendor-specific, word 27"                               ,  // 155
    "Vendor-specific, word 28"                               ,  // 156
    "Vendor-specific, word 29"                               ,  // 157
    "Vendor-specific, word 30"                               ,  // 158
    "Vendor-specific, word 31"                               ,  // 159
    "CFA parameters, word 1"                                 ,  // 160
    "CFA parameters, word 2"                                 ,  // 161
    "CFA parameters, word 3"                                 ,  // 162
    "CFA parameters, word 4"                                 ,  // 163
    "CFA parameters, word 5"                                 ,  // 164
    "CFA parameters, word 6"                                 ,  // 165
    "CFA parameters, word 7"                                 ,  // 166
    "CFA parameters, word 8"                                 ,  // 167
    "Device nominal form factor"                             ,  // 168
    "DATA SET MANAGEMENT command support options"            ,  // 169
    "Additional product identifier, word 1"                  ,  // 170
    "Additional product identifier, word 2"                  ,  // 171
    "Additional product identifier, word 3"                  ,  // 172
    "Additional product identifier, word 4"                  ,  // 173
    "Reserved"                                               ,  // 174
    "Reserved"                                               ,  // 175
    "Current media serial number, word 1"                    ,  // 176
    "Current media serial number, word 2"                    ,  // 177
    "Current media serial number, word 3"                    ,  // 178
    "Current media serial number, word 4"                    ,  // 179
    "Current media serial number, word 5"                    ,  // 180
    "Current media serial number, word 6"                    ,  // 181
    "Current media serial number, word 7"                    ,  // 182
    "Current media serial number, word 8"                    ,  // 183
    "Current media serial number, word 9"                    ,  // 184
    "Current media serial number, word 10"                   ,  // 185
    "Current media serial number, word 11"                   ,  // 186
    "Current media serial number, word 12"                   ,  // 187
    "Current media serial number, word 13"                   ,  // 188
    "Current media serial number, word 14"                   ,  // 189
    "Current media serial number, word 15"                   ,  // 190
    "Current media serial number, word 16"                   ,  // 191
    "Current media serial number, word 17"                   ,  // 192
    "Current media serial number, word 18"                   ,  // 193
    "Current media serial number, word 19"                   ,  // 194
    "Current media serial number, word 20"                   ,  // 195
    "Current media serial number, word 21"                   ,  // 196
    "Current media serial number, word 22"                   ,  // 197
    "Current media serial number, word 23"                   ,  // 198
    "Current media serial number, word 24"                   ,  // 199
    "Current media serial number, word 25"                   ,  // 200
    "Current media serial number, word 26"                   ,  // 201
    "Current media serial number, word 27"                   ,  // 202
    "Current media serial number, word 28"                   ,  // 203
    "Current media serial number, word 29"                   ,  // 204
    "Current media serial number, word 30"                   ,  // 205
    "SCT Command Transport"                                  ,  // 206
    "Reserved"                                               ,  // 207
    "Reserved"                                               ,  // 208
    "Alignment of logical sect. within a physical sect."     ,  // 209
    "Write-Read-Verify sector mode 3 count, word 1"          ,  // 210
    "Write-Read-Verify sector mode 3 count, word 2"          ,  // 211
    "Write-Read-Verify sector mode 2 count, word 1"          ,  // 212
    "Write-Read-Verify sector mode 2 count, word 2"          ,  // 213
    "Obsolete"                                               ,  // 214
    "Obsolete"                                               ,  // 215
    "Obsolete"                                               ,  // 216
    "Nominal media rotation rate"                            ,  // 217
    "Reserved"                                               ,  // 218
    "Obsolete"                                               ,  // 219
    "Write-Read-Verify feature set current mode"             ,  // 220
    "Reserved"                                               ,  // 221
    "Transport major version number"                         ,  // 222
    "Transport minor version number"                         ,  // 223
    "Reserved"                                               ,  // 224
    "Reserved"                                               ,  // 225
    "Reserved"                                               ,  // 226
    "Reserved"                                               ,  // 227
    "Reserved"                                               ,  // 228
    "Reserved"                                               ,  // 229
    "Extended number of user addressable sectors, word 1"    ,  // 230
    "Extended number of user addressable sectors, word 2"    ,  // 231
    "Extended number of user addressable sectors, word 3"    ,  // 232
    "Extended number of user addressable sectors, word 4"    ,  // 233
    "Minimum 512-b data blocks per download microcode"       ,  // 234
    "Maximum 512-b data blocks per download microcode"       ,  // 235
    "Reserved"                                               ,  // 236
    "Reserved"                                               ,  // 237
    "Reserved"                                               ,  // 238
    "Reserved"                                               ,  // 239
    "Reserved"                                               ,  // 240
    "Reserved"                                               ,  // 241
    "Reserved"                                               ,  // 242
    "Reserved"                                               ,  // 243
    "Reserved"                                               ,  // 244
    "Reserved"                                               ,  // 245
    "Reserved"                                               ,  // 246
    "Reserved"                                               ,  // 247
    "Reserved"                                               ,  // 248
    "Reserved"                                               ,  // 249
    "Reserved"                                               ,  // 250
    "Reserved"                                               ,  // 251
    "Reserved"                                               ,  // 252
    "Reserved"                                               ,  // 253
    "Reserved"                                               ,  // 254
    "Integrity word"                                         ,  // 255
    };    

private short[] diskPassport;
private String[] wordsNames, wordsNumbers, wordsHex, wordsComments; 
private final static int diskPassportLength = 256;

//---------- Table model constructor -------------------------------------------
DiskPassportDumpTableModel()
    {
//--- Initializing and blank ---
    int n = diskPassportLength;
    String s = " ";
    diskPassport = new short[n];
    for (int i=0; i<n; i++)
        {
        diskPassport[i]=0;
        }
    wordsNames    = new String[n];
    wordsNumbers  = new String[n];
    wordsHex      = new String[n];
    wordsComments = new String[n];
    for (int i=0; i<n; i++)
        {
        wordsNames[i]    = s;
        wordsNumbers[i]  = s;
        wordsHex[i]      = s;
        wordsComments[i] = s;
        }
    }

//---------- Get strings for output --------------------------------------------
public Object getValueAt (int row, int column)
    { 
        switch (column)
        {
        case 0:  return " " + wordsNames[row];
        case 1:  return " " + wordsNumbers[row];
        case 2:  return " " + wordsHex[row];
        case 3:  return " " + wordsComments[row];
        default: return null;            
        }
    }

//---------- Table configuration -----------------------------------------------
public int getColumnCount ()             { return columnsNames.length;    }
public int getRowCount ()                { return wordsNamesConst.length; }
public boolean isCellEditable ()         { return false;                  }
public String getColumnName (int column) { return columnsNames[column];   }

//---------- Specific methods --------------------------------------------------
public byte[] getDiskPassport()
    {
    byte[] dpb = new byte[diskPassportLength*2];
    int x;
    for (int i=0; i<diskPassportLength; i++)
        {
        x = diskPassport[i];
        dpb[i*2]   = ( byte )( x & 0xFF );
        dpb[i*2+1] = ( byte )( (x >> 8) & 0xFF );
        }
    return dpb;
    }

public void setDiskPassport(byte[] dpb )
    {
    int x1, x2;
    for (int i=0; i<diskPassportLength; i++)
        {
        x1 = dpb[i*2];
        x2 = dpb[i*2+1];
        x1 = x1 & 0xFF;
        x2 = (x2 & 0xFF) << 8;
        x1 = x1 | x2;
        diskPassport[i] = (short)x1;
        }
    }

public void builtDiskPassport()
    {
//--- Built table, write values after read passport ---
    int n = diskPassportLength;
    String blankString = " ";
    wordsNames    = new String[n];
    wordsNumbers  = new String[n];
    wordsHex      = new String[n];
    wordsComments = new String[n];
    for (int i=0; i<n; i++)
        {
        wordsNames[i]    = wordsNamesConst[i];
        wordsNumbers[i]  = String.format( " %d"   , i );
        wordsHex[i]      = String.format( " %04X" , diskPassport[i] );
        wordsComments[i] = blankString;
        }
    }


}
