package diskpassport;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DiskPassport extends JFrame {

DiskPassport() {
    super("Mass Storage device details. Engineering sample.");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
//--- Disk passport dump table ---
    DiskPassportDumpTableModel dpdtm = new DiskPassportDumpTableModel();
    JTable ta = new JTable(dpdtm);
    JScrollPane sa = new JScrollPane(ta);
    ta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    ta.getColumnModel().getColumn(0).setPreferredWidth(340);
    ta.getColumnModel().getColumn(1).setPreferredWidth(70);
    ta.getColumnModel().getColumn(2).setPreferredWidth(70);
    ta.getColumnModel().getColumn(3).setPreferredWidth(440);

//--- Disk passport details table ---    
    DiskPassportTableModel dptm = new DiskPassportTableModel();
    JTable tb = new JTable(dptm);
    JScrollPane sb = new JScrollPane(tb);
    tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tb.getColumnModel().getColumn(0).setPreferredWidth(200);
    tb.getColumnModel().getColumn(1).setPreferredWidth(70);
    tb.getColumnModel().getColumn(2).setPreferredWidth(70);
    tb.getColumnModel().getColumn(3).setPreferredWidth(70);
    tb.getColumnModel().getColumn(4).setPreferredWidth(70);
    tb.getColumnModel().getColumn(5).setPreferredWidth(440);

//--- Disk SMART dump table ---
    DiskSmartDumpTableModel dsdtm = new DiskSmartDumpTableModel();
    JTable tc = new JTable(dsdtm);
    JScrollPane sc = new JScrollPane(tc);
    tc.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    tc.getColumnModel().getColumn(0).setPreferredWidth(240);
    tc.getColumnModel().getColumn(1).setPreferredWidth(70);
    tc.getColumnModel().getColumn(2).setPreferredWidth(220);
    tc.getColumnModel().getColumn(3).setPreferredWidth(350);

//--- Disk SMART details table ---
    DiskSmartTableModel dstm = new DiskSmartTableModel();
    JTable td = new JTable(dstm);
    JScrollPane sd = new JScrollPane(td);
    td.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    td.getColumnModel().getColumn(0).setPreferredWidth(35);
    td.getColumnModel().getColumn(1).setPreferredWidth(35);
    td.getColumnModel().getColumn(2).setPreferredWidth(150);
    td.getColumnModel().getColumn(3).setPreferredWidth(60);
    td.getColumnModel().getColumn(4).setPreferredWidth(140);
    td.getColumnModel().getColumn(5).setPreferredWidth(62);
    td.getColumnModel().getColumn(6).setPreferredWidth(62);
    td.getColumnModel().getColumn(7).setPreferredWidth(62);
    td.getColumnModel().getColumn(8).setPreferredWidth(62);
    td.getColumnModel().getColumn(9).setPreferredWidth(100);

//--- Panels and tables at scroll panes ---
    SpringLayout la = new SpringLayout(); JPanel pa = new JPanel(la);
    SpringLayout lb = new SpringLayout(); JPanel pb = new JPanel(lb);
    SpringLayout lc = new SpringLayout(); JPanel pc = new JPanel(lc);
    SpringLayout ld = new SpringLayout(); JPanel pd = new JPanel(ld);
    SpringLayout le = new SpringLayout(); JPanel pe = new JPanel(le);
    SpringLayout lf = new SpringLayout(); JPanel pf = new JPanel(lf);
    SpringLayout lg = new SpringLayout(); JPanel pg = new JPanel(lg);

//--- Constraints for Passport Dump Table position at JTabbedPane ---    
    la.putConstraint(SpringLayout.NORTH , sa ,   0 , SpringLayout.NORTH , pa);
    la.putConstraint(SpringLayout.SOUTH , sa ,   0 , SpringLayout.SOUTH , pa);
    la.putConstraint(SpringLayout.WEST  , sa ,   0 , SpringLayout.WEST  , pa);
    la.putConstraint(SpringLayout.EAST  , sa ,   0 , SpringLayout.EAST  , pa);

//--- Constraints for Passport Details Table position at JTabbedPane ---    
    lb.putConstraint(SpringLayout.NORTH , sb ,   0 , SpringLayout.NORTH , pb);
    lb.putConstraint(SpringLayout.SOUTH , sb ,   0 , SpringLayout.SOUTH , pb);
    lb.putConstraint(SpringLayout.WEST  , sb ,   0 , SpringLayout.WEST  , pb);
    lb.putConstraint(SpringLayout.EAST  , sb ,   0 , SpringLayout.EAST  , pb);

//--- Constraints for SMART dump Table position at JTabbedPane ---    
    lc.putConstraint(SpringLayout.NORTH , sc ,   0 , SpringLayout.NORTH , pc);
    lc.putConstraint(SpringLayout.SOUTH , sc ,   0 , SpringLayout.SOUTH , pc);
    lc.putConstraint(SpringLayout.WEST  , sc ,   0 , SpringLayout.WEST  , pc);
    lc.putConstraint(SpringLayout.EAST  , sc ,   0 , SpringLayout.EAST  , pc);

//--- Constraints for SMART details Table position at JTabbedPane ---    
    ld.putConstraint(SpringLayout.NORTH , sd ,   0 , SpringLayout.NORTH , pd);
    ld.putConstraint(SpringLayout.SOUTH , sd ,   0 , SpringLayout.SOUTH , pd);
    ld.putConstraint(SpringLayout.WEST  , sd ,   0 , SpringLayout.WEST  , pd);
    ld.putConstraint(SpringLayout.EAST  , sd ,   0 , SpringLayout.EAST  , pd);
    
//--- Add Tables with JScrollPane ---
    pa.add(sa);
    pb.add(sb);
    pc.add(sc);
    pd.add(sd);

//--- JTabbedPane and sheets=panels, tp = element for next operations/adds ---
    JTabbedPane tp = new JTabbedPane();

    tp.addTab("Passport"       , pb);
    tp.addTab("Passport dump"  , pa);
    tp.addTab("SMART"          , pd);
    tp.addTab("SMART dump"     , pc);
    tp.addTab("ACS operations" , pe);  tp.setEnabledAt(4,false);
    tp.addTab("Device path"    , pf);  tp.setEnabledAt(5,false);
    tp.addTab("Performance"    , pg);  tp.setEnabledAt(6,false);
    
//--- Buttons ---    
    JButton b1 = new JButton("Load");
    JButton b2 = new JButton("Cancel");
    
//--- Copyright label ---
    JLabel  l1 = new JLabel  ("(C)2018  IC Book Labs");
    JLabel  l2 = new JLabel  ("v0.10");
    l1.setForeground(Color.GRAY);
    l2.setForeground(Color.GRAY);
    Font font1 = new Font ("Verdana", Font.PLAIN, 10);
    l1.setFont(font1);
    l2.setFont(font1);
    
//--- Panel with layout --- 
    SpringLayout sl1 = new SpringLayout();
    JPanel p1 = new JPanel(sl1);
    
//--- Constraints for JTabbedPane position at Pseudo-root panel ---    
    sl1.putConstraint(SpringLayout.NORTH , tp ,   2 , SpringLayout.NORTH , p1);
    sl1.putConstraint(SpringLayout.SOUTH , tp , -36 , SpringLayout.SOUTH , p1);
    sl1.putConstraint(SpringLayout.WEST  , tp ,   2 , SpringLayout.WEST  , p1);
    sl1.putConstraint(SpringLayout.EAST  , tp ,  -2 , SpringLayout.EAST  , p1);
    
//--- Constraints for Buttons positions ---
    sl1.putConstraint(SpringLayout.SOUTH , b1 ,  -4 , SpringLayout.SOUTH , p1);
    sl1.putConstraint(SpringLayout.SOUTH , b2 ,  -4 , SpringLayout.SOUTH , p1);
    sl1.putConstraint(SpringLayout.EAST  , b2 ,  -4 , SpringLayout.EAST  , p1);
    sl1.putConstraint(SpringLayout.EAST  , b1 ,  -4 , SpringLayout.WEST  , b2);
    
//--- Constraints for Buttons widths ---
    sl1.putConstraint(SpringLayout.WEST  , b2 ,  -85 , SpringLayout.EAST , b2);
    sl1.putConstraint(SpringLayout.WEST  , b1 ,  -85 , SpringLayout.EAST , b1);

//--- Constraints for Copyright labels ---    
    sl1.putConstraint(SpringLayout.SOUTH , l2 ,  -3 , SpringLayout.SOUTH , p1);
    sl1.putConstraint(SpringLayout.SOUTH , l1 ,   0 , SpringLayout.NORTH , l2);
    sl1.putConstraint(SpringLayout.WEST  , l1 ,   7 , SpringLayout.WEST  , p1);
    sl1.putConstraint(SpringLayout.WEST  , l2 ,   7 , SpringLayout.WEST  , p1);

//--- Listener for LOAD ---
    b1.addActionListener(new ActionListener() {
    public void actionPerformed (ActionEvent e)
        {
        //--- Initializing array ---
        boolean error = false;
        int n = 512*3;
        byte[] buffer = new byte[n];
        for (int i=0; i<n; i++) { buffer[i]=0; }
        //--- Dialogue ---
        JFileChooser fileopen = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter
            ("Binary dump file", "bin");
        fileopen.setFileFilter(filter);
        fileopen.setAcceptAllFileFilterUsed(false);
        int ret = fileopen.showDialog(null, "Open binary file");
        if (ret == JFileChooser.APPROVE_OPTION)
            {
            File file = fileopen.getSelectedFile();
            //--- Read file ---
            try {
                FileInputStream fin = new FileInputStream(file);
                fin.read(buffer, 0, fin.available());
                } 
            catch(IOException ex)
                { 
                // DEBUG // System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog
                    (null, "Read error: " + file ,
                            "Error" ,
                            JOptionPane.ERROR_MESSAGE);
                error = true;
                }
            //--- Send data to table model, reshow table ---
            if (error==false)
                {
                //--- Set data for disk passport dump table ---
                dpdtm.setDiskPassport(buffer);
                dpdtm.builtDiskPassport();
                ta.revalidate();
                ta.repaint();
                //--- Set data for disk passport details table ---
                dptm.setDiskPassport(buffer);
                dptm.builtDiskPassport();
                tb.revalidate();
                tb.repaint();
                //--- Built sub-buffer for SMART ---
                int pSize = 512;      // size of Passport
                int sSize = 512+512;  // size of SMART+THRESHOLDS
                byte[] buffer1 = new byte[sSize];
                for (int i=0; i<sSize; i++) { buffer1[i]=0; }
                //if ( buffer.length >= 1024 )
                //    {
                    for (int i=0; i<sSize; i++)
                        { buffer1[i] = buffer[i+pSize]; }
                    //--- Set data for SMART dump table ---
                    dsdtm.setSmart(buffer1);
                    dsdtm.builtSmart();
                    tc.revalidate();
                    tc.repaint();
                    //--- Set data for SMART details table ---
                    dstm.setSmart(buffer1);
                    dstm.builtSmart();
                    td.revalidate();
                    td.repaint();
                //    }
                //--- Set data done ---
                }
            }
        } } );

//--- Listener for CANCEL ---
    b2.addActionListener (new ActionListener() {
    public void actionPerformed (ActionEvent e)
        {
        System.exit(0);
        } } );
//--- Built and visual window ---
    p1.add(tp);
    p1.add(b1); p1.add(b2);
    p1.add(l1); p1.add(l2);
    add(p1);
    setSize(780,580);
    setLocationRelativeTo(null);
    setVisible(true);
}

//--- Application entry point ---
    public static void main(String[] args) {
        // DEBUG // System.out.println(testPath);
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        new DiskPassport();
    }
    
}
