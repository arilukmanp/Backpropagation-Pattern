/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pattern;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Ari
 */
public class MainPattern extends javax.swing.JFrame implements Runnable {
    
    AlphabetLibrary pola = new AlphabetLibrary();
    
    boolean stop = false;
    double z_net[] = new double[15];
    double y_net[] = new double[7];
    double z[] = new double[15];
    double y[] = new double[7];
    double bias_z[] = new double[15];
    double bias_y[] = new double[7];
    double[][] v = new double[pola.x[0].length][z.length];
    double[][] w = new double[z.length][y.length];
    
    Thread thread = new Thread(this);
    
    int[] input = {-1,-1,-1,-1,-1,-1,-1,
                      -1,-1,-1,-1,-1,-1,-1,
                      -1,-1,-1,-1,-1,-1,-1,
                      -1,-1,-1,-1,-1,-1,-1,
                      -1,-1,-1,-1,-1,-1,-1,
                      -1,-1,-1,-1,-1,-1,-1,
                      -1,-1,-1,-1,-1,-1,-1,
                      -1,-1,-1,-1,-1,-1,-1,
                      -1,-1,-1,-1,-1,-1,-1};
    
    
    /**
     * Creates new form MainPattern
     */
    public MainPattern() {
        initComponents();
        cprght();setLocationRelativeTo(null);
        btnStop.setEnabled(false);
    }
    
    public void switchMenu(JLabel x) {
        int[] menu = {1, 0};
        
        if(x == testingMenu) {
            menu[0] = 1;
            menu[1] = 0;
            x.setForeground(Color.BLACK);
            trainingMenu.setForeground(new Color(153,153,153));
            testFrame.setVisible(true);
            trainingFrame.setVisible(false);
        }
        else {
            menu[0] = 0;
            menu[1] = 1;
            testingMenu.setForeground(new Color(153,153,153));
            x.setForeground(Color.BLACK);
            testFrame.setVisible(false);
            trainingFrame.setVisible(true);
        }
    }
    
    public void boxValue(int x, JPanel y) {
        if(input[x] == -1) {
            input[x] = 1;
            y.setBackground(new Color(38,200,211));
        }
        else {
            input[x] = -1;
            y.setBackground(new Color(200,236,250));
        }
    }
    
    public void resetBoxValue() {
        JPanel[] boxJpanel = {RC11, RC12, RC13, RC14, RC15, RC16, RC17,
                             RC21, RC22, RC23, RC24, RC25, RC26, RC27,
                             RC31, RC32, RC33, RC34, RC35, RC36, RC37,
                             RC41, RC42, RC43, RC44, RC45, RC46, RC47,
                             RC51, RC52, RC53, RC54, RC55, RC56, RC57, 
                             RC61, RC62, RC63, RC64, RC65, RC66, RC67,
                             RC71, RC72, RC73, RC74, RC75, RC76, RC77,
                             RC81, RC82, RC83, RC84, RC85, RC86, RC87,
                             RC91, RC92, RC93, RC94, RC95, RC96, RC97};
        
        for(int i=0; i < boxJpanel.length; i++) {
            input[i] = -1;
            boxJpanel[i].setBackground(new Color(200,236,250));
        }
    }
    
    private double sigmoid_biner(double x) {
        return (1.0 / (1.0 + Math.exp(-x)));
    }

    private double sigmoid_bipolar(double x) {
        return (2 * sigmoid_biner(x)) - 1;
    }

    private double turunan_sigmoid_bipolar(double x) {
        return 0.5 * (1 + sigmoid_bipolar(x)) * (1 - sigmoid_bipolar(x));
    }
    
    
    public void tes() {
        for (byte i = 0; i < input.length; i++) {
            System.out.print("Pola Huruf:"+input[i]);
        }
        System.out.println();
        
        for (byte j = 0; j < z.length; j++) {
            z_net[j] = 0;
            for (byte i = 0; i < input.length; i++) {
                z_net[j] += (input[i] * v[i][j]);
            }
            z_net[j] += bias_z[j];
            z[j] = sigmoid_bipolar(z_net[j]);
        }

        //feed-forward layer hidden-output
        for (byte j = 0; j < y.length; j++) {
            y_net[j] = 0;
            for (byte i = 0; i < z.length; i++) {
                y_net[j] += (z[i] * w[i][j]);
            }
            y_net[j] += bias_y[j];
            y[j] = sigmoid_bipolar(y_net[j]);
            System.out.println(y[j]);
        }
        if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "A");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]>0) {
            notifPanel.showMessageDialog(this, "B");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] <= 0 && y[5]>0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "C");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] > 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "D");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] > 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "E");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] > 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "F");
        } else if (y[0] <= 0 && y[1] > 0 && y[2] <= 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "G");
        } else if (y[0] > 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "H");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] <= 0 && y[5]>0 && y[6]>0) {
            notifPanel.showMessageDialog(this, "I");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] > 0 && y[5]>0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "J");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] > 0 && y[4] > 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "K");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] > 0 && y[3] > 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "L");
        } else if (y[0] <= 0 && y[1] > 0 && y[2] > 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "M");
        } else if (y[0] > 0 && y[1] > 0 && y[2] <= 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "N");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] > 0 && y[5]<=0 && y[6]>0) {
            notifPanel.showMessageDialog(this, "O");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] > 0 && y[4] <= 0 && y[5]>0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "P");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] > 0 && y[3] <= 0 && y[4] > 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "Q");
        } else if (y[0] <= 0 && y[1] > 0 && y[2] <= 0 && y[3] > 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "R");
        } else if (y[0] > 0 && y[1] <= 0 && y[2] > 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "S");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] <= 0 && y[4] > 0 && y[5]>0 && y[6]>0) {
            notifPanel.showMessageDialog(this, "T");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] > 0 && y[4] > 0 && y[5]>0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "U");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] > 0 && y[3] > 0 && y[4] > 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "V");
        } else if (y[0] <= 0 && y[1] > 0 && y[2] > 0 && y[3] > 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "W");
        } else if (y[0] > 0 && y[1] > 0 && y[2] > 0 && y[3] <= 0 && y[4] <= 0 && y[5]<=0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "X");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] <= 0 && y[3] > 0 && y[4] <= 0 && y[5]>0 && y[6]>0) {
            notifPanel.showMessageDialog(this, "Y");
        } else if (y[0] <= 0 && y[1] <= 0 && y[2] > 0 && y[3] <= 0 && y[4] > 0 && y[5]>0 && y[6]<=0) {
            notifPanel.showMessageDialog(this, "Z");
        } else{
            notifPanel.showMessageDialog(this, "Pattern Tidak Dikenali");
        }
    }
    
    public void close() {
        int confirm = notifPanel.showOptionDialog(
             null, "Apakah Anda Yakin Ingin Keluar Dari Aplikasi?", 
             "Konfirmasi Keluar",
             notifPanel.YES_NO_OPTION, 
             notifPanel.QUESTION_MESSAGE, null, null, null);
        if (confirm == 0) {
           System.exit(0);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     */public void cprght(){authorLabel.setText("© github.com/arilukmanp");}
    /**regenerated by the Form Editor.
     */ 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        notifPanel = new javax.swing.JOptionPane();
        sidebarPanel = new javax.swing.JPanel();
        btnA = new javax.swing.JPanel();
        lbl_A = new javax.swing.JLabel();
        btnB = new javax.swing.JPanel();
        lbl_B = new javax.swing.JLabel();
        btnC = new javax.swing.JPanel();
        lbl_C = new javax.swing.JLabel();
        btnD = new javax.swing.JPanel();
        lbl_D = new javax.swing.JLabel();
        btnE = new javax.swing.JPanel();
        lbl_E = new javax.swing.JLabel();
        btnF = new javax.swing.JPanel();
        lbl_F = new javax.swing.JLabel();
        btnG = new javax.swing.JPanel();
        lbl_G = new javax.swing.JLabel();
        btnH = new javax.swing.JPanel();
        lbl_H = new javax.swing.JLabel();
        btnI = new javax.swing.JPanel();
        lbl_I = new javax.swing.JLabel();
        btnJ = new javax.swing.JPanel();
        lbl_J = new javax.swing.JLabel();
        btnK = new javax.swing.JPanel();
        lbl_K = new javax.swing.JLabel();
        btnL = new javax.swing.JPanel();
        lbl_L = new javax.swing.JLabel();
        btnM = new javax.swing.JPanel();
        lbl_M = new javax.swing.JLabel();
        btnN = new javax.swing.JPanel();
        lbl_N = new javax.swing.JLabel();
        btnO = new javax.swing.JPanel();
        lbl_O = new javax.swing.JLabel();
        btnP = new javax.swing.JPanel();
        lbl_P = new javax.swing.JLabel();
        btnQ = new javax.swing.JPanel();
        lbl_Q = new javax.swing.JLabel();
        btnR = new javax.swing.JPanel();
        lbl_R = new javax.swing.JLabel();
        btnS = new javax.swing.JPanel();
        lbl_S = new javax.swing.JLabel();
        btnT = new javax.swing.JPanel();
        lbl_T = new javax.swing.JLabel();
        btnU = new javax.swing.JPanel();
        lbl_U = new javax.swing.JLabel();
        btnV = new javax.swing.JPanel();
        lbl_V = new javax.swing.JLabel();
        btnW = new javax.swing.JPanel();
        lbl_W = new javax.swing.JLabel();
        btnX = new javax.swing.JPanel();
        lbl_X = new javax.swing.JLabel();
        btnY = new javax.swing.JPanel();
        lbl_Y = new javax.swing.JLabel();
        btnZ = new javax.swing.JPanel();
        lbl_Z = new javax.swing.JLabel();
        btnReset = new javax.swing.JPanel();
        lbl_Reset = new javax.swing.JLabel();
        authorLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        menubarPanel = new javax.swing.JPanel();
        btn_closeApp = new javax.swing.JLabel();
        testingMenu = new javax.swing.JLabel();
        trainingMenu = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        testFrame = new javax.swing.JInternalFrame();
        testPanel = new javax.swing.JPanel();
        RC11 = new javax.swing.JPanel();
        RC12 = new javax.swing.JPanel();
        RC13 = new javax.swing.JPanel();
        RC14 = new javax.swing.JPanel();
        RC15 = new javax.swing.JPanel();
        RC16 = new javax.swing.JPanel();
        RC17 = new javax.swing.JPanel();
        RC21 = new javax.swing.JPanel();
        RC22 = new javax.swing.JPanel();
        RC23 = new javax.swing.JPanel();
        RC24 = new javax.swing.JPanel();
        RC25 = new javax.swing.JPanel();
        RC26 = new javax.swing.JPanel();
        RC27 = new javax.swing.JPanel();
        RC31 = new javax.swing.JPanel();
        RC32 = new javax.swing.JPanel();
        RC33 = new javax.swing.JPanel();
        RC34 = new javax.swing.JPanel();
        RC35 = new javax.swing.JPanel();
        RC36 = new javax.swing.JPanel();
        RC37 = new javax.swing.JPanel();
        RC41 = new javax.swing.JPanel();
        RC42 = new javax.swing.JPanel();
        RC43 = new javax.swing.JPanel();
        RC44 = new javax.swing.JPanel();
        RC45 = new javax.swing.JPanel();
        RC46 = new javax.swing.JPanel();
        RC47 = new javax.swing.JPanel();
        RC51 = new javax.swing.JPanel();
        RC52 = new javax.swing.JPanel();
        RC53 = new javax.swing.JPanel();
        RC54 = new javax.swing.JPanel();
        RC55 = new javax.swing.JPanel();
        RC56 = new javax.swing.JPanel();
        RC57 = new javax.swing.JPanel();
        RC61 = new javax.swing.JPanel();
        RC62 = new javax.swing.JPanel();
        RC63 = new javax.swing.JPanel();
        RC64 = new javax.swing.JPanel();
        RC65 = new javax.swing.JPanel();
        RC66 = new javax.swing.JPanel();
        RC67 = new javax.swing.JPanel();
        RC71 = new javax.swing.JPanel();
        RC72 = new javax.swing.JPanel();
        RC73 = new javax.swing.JPanel();
        RC74 = new javax.swing.JPanel();
        RC75 = new javax.swing.JPanel();
        RC76 = new javax.swing.JPanel();
        RC77 = new javax.swing.JPanel();
        RC81 = new javax.swing.JPanel();
        RC82 = new javax.swing.JPanel();
        RC83 = new javax.swing.JPanel();
        RC84 = new javax.swing.JPanel();
        RC85 = new javax.swing.JPanel();
        RC86 = new javax.swing.JPanel();
        RC87 = new javax.swing.JPanel();
        RC91 = new javax.swing.JPanel();
        RC92 = new javax.swing.JPanel();
        RC93 = new javax.swing.JPanel();
        RC94 = new javax.swing.JPanel();
        RC95 = new javax.swing.JPanel();
        RC96 = new javax.swing.JPanel();
        RC97 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btnTest = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        trainingFrame = new javax.swing.JInternalFrame();
        trainingPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        logDataField = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        alphaField = new javax.swing.JTextField();
        toleranceField = new javax.swing.JTextField();
        btnTraining = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        epochField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_maxErrorValue = new javax.swing.JLabel();
        lbl_epoch = new javax.swing.JLabel();
        btnStop = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pengenalan Pola Huruf");
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebarPanel.setBackground(new java.awt.Color(38, 200, 211));
        sidebarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnA.setBackground(new java.awt.Color(224, 246, 251));
        btnA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnA.setPreferredSize(new java.awt.Dimension(30, 30));
        btnA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAMouseClicked(evt);
            }
        });

        lbl_A.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_A.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_A.setText("A");

        org.jdesktop.layout.GroupLayout btnALayout = new org.jdesktop.layout.GroupLayout(btnA);
        btnA.setLayout(btnALayout);
        btnALayout.setHorizontalGroup(
            btnALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, btnALayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_A, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnALayout.setVerticalGroup(
            btnALayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_A, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnA, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 364, 29, -1));

        btnB.setBackground(new java.awt.Color(224, 246, 251));
        btnB.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnB.setPreferredSize(new java.awt.Dimension(30, 30));
        btnB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBMouseClicked(evt);
            }
        });

        lbl_B.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_B.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_B.setText("B");

        org.jdesktop.layout.GroupLayout btnBLayout = new org.jdesktop.layout.GroupLayout(btnB);
        btnB.setLayout(btnBLayout);
        btnBLayout.setHorizontalGroup(
            btnBLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnBLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_B, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnBLayout.setVerticalGroup(
            btnBLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_B, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnB, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 364, 29, -1));

        btnC.setBackground(new java.awt.Color(224, 246, 251));
        btnC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnC.setPreferredSize(new java.awt.Dimension(30, 30));
        btnC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCMouseClicked(evt);
            }
        });

        lbl_C.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_C.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_C.setText("C");

        org.jdesktop.layout.GroupLayout btnCLayout = new org.jdesktop.layout.GroupLayout(btnC);
        btnC.setLayout(btnCLayout);
        btnCLayout.setHorizontalGroup(
            btnCLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnCLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_C, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnCLayout.setVerticalGroup(
            btnCLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_C, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnC, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 364, 29, -1));

        btnD.setBackground(new java.awt.Color(224, 246, 251));
        btnD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnD.setPreferredSize(new java.awt.Dimension(30, 30));
        btnD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDMouseClicked(evt);
            }
        });

        lbl_D.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_D.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_D.setText("D");

        org.jdesktop.layout.GroupLayout btnDLayout = new org.jdesktop.layout.GroupLayout(btnD);
        btnD.setLayout(btnDLayout);
        btnDLayout.setHorizontalGroup(
            btnDLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnDLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_D, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnDLayout.setVerticalGroup(
            btnDLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_D, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnD, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 364, 29, -1));

        btnE.setBackground(new java.awt.Color(224, 246, 251));
        btnE.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnE.setPreferredSize(new java.awt.Dimension(30, 30));
        btnE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEMouseClicked(evt);
            }
        });

        lbl_E.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_E.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_E.setText("E");

        org.jdesktop.layout.GroupLayout btnELayout = new org.jdesktop.layout.GroupLayout(btnE);
        btnE.setLayout(btnELayout);
        btnELayout.setHorizontalGroup(
            btnELayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnELayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_E, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnELayout.setVerticalGroup(
            btnELayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_E, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnE, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 364, 29, -1));

        btnF.setBackground(new java.awt.Color(224, 246, 251));
        btnF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnF.setPreferredSize(new java.awt.Dimension(30, 30));
        btnF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFMouseClicked(evt);
            }
        });

        lbl_F.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_F.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_F.setText("F");

        org.jdesktop.layout.GroupLayout btnFLayout = new org.jdesktop.layout.GroupLayout(btnF);
        btnF.setLayout(btnFLayout);
        btnFLayout.setHorizontalGroup(
            btnFLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnFLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_F, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnFLayout.setVerticalGroup(
            btnFLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_F, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnF, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 364, 29, -1));

        btnG.setBackground(new java.awt.Color(224, 246, 251));
        btnG.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnG.setPreferredSize(new java.awt.Dimension(30, 30));
        btnG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGMouseClicked(evt);
            }
        });

        lbl_G.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_G.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_G.setText("G");

        org.jdesktop.layout.GroupLayout btnGLayout = new org.jdesktop.layout.GroupLayout(btnG);
        btnG.setLayout(btnGLayout);
        btnGLayout.setHorizontalGroup(
            btnGLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnGLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_G, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnGLayout.setVerticalGroup(
            btnGLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_G, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnG, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 364, 29, -1));

        btnH.setBackground(new java.awt.Color(224, 246, 251));
        btnH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnH.setPreferredSize(new java.awt.Dimension(30, 30));
        btnH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHMouseClicked(evt);
            }
        });

        lbl_H.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_H.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_H.setText("H");

        org.jdesktop.layout.GroupLayout btnHLayout = new org.jdesktop.layout.GroupLayout(btnH);
        btnH.setLayout(btnHLayout);
        btnHLayout.setHorizontalGroup(
            btnHLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnHLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_H, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnHLayout.setVerticalGroup(
            btnHLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_H, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnH, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 405, 29, -1));

        btnI.setBackground(new java.awt.Color(224, 246, 251));
        btnI.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnI.setPreferredSize(new java.awt.Dimension(30, 30));
        btnI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIMouseClicked(evt);
            }
        });

        lbl_I.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_I.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_I.setText("I");

        org.jdesktop.layout.GroupLayout btnILayout = new org.jdesktop.layout.GroupLayout(btnI);
        btnI.setLayout(btnILayout);
        btnILayout.setHorizontalGroup(
            btnILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, btnILayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_I, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnILayout.setVerticalGroup(
            btnILayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_I, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnI, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 405, 29, -1));

        btnJ.setBackground(new java.awt.Color(224, 246, 251));
        btnJ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnJ.setPreferredSize(new java.awt.Dimension(30, 30));
        btnJ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnJMouseClicked(evt);
            }
        });

        lbl_J.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_J.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_J.setText("J");

        org.jdesktop.layout.GroupLayout btnJLayout = new org.jdesktop.layout.GroupLayout(btnJ);
        btnJ.setLayout(btnJLayout);
        btnJLayout.setHorizontalGroup(
            btnJLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnJLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_J, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnJLayout.setVerticalGroup(
            btnJLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_J, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 405, 29, -1));

        btnK.setBackground(new java.awt.Color(224, 246, 251));
        btnK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnK.setPreferredSize(new java.awt.Dimension(30, 30));
        btnK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKMouseClicked(evt);
            }
        });

        lbl_K.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_K.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_K.setText("K");

        org.jdesktop.layout.GroupLayout btnKLayout = new org.jdesktop.layout.GroupLayout(btnK);
        btnK.setLayout(btnKLayout);
        btnKLayout.setHorizontalGroup(
            btnKLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnKLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_K, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnKLayout.setVerticalGroup(
            btnKLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_K, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnK, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 405, 29, -1));

        btnL.setBackground(new java.awt.Color(224, 246, 251));
        btnL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnL.setPreferredSize(new java.awt.Dimension(30, 30));
        btnL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLMouseClicked(evt);
            }
        });

        lbl_L.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_L.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_L.setText("L");

        org.jdesktop.layout.GroupLayout btnLLayout = new org.jdesktop.layout.GroupLayout(btnL);
        btnL.setLayout(btnLLayout);
        btnLLayout.setHorizontalGroup(
            btnLLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnLLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_L, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnLLayout.setVerticalGroup(
            btnLLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_L, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnL, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 405, 29, -1));

        btnM.setBackground(new java.awt.Color(224, 246, 251));
        btnM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnM.setPreferredSize(new java.awt.Dimension(30, 30));
        btnM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMMouseClicked(evt);
            }
        });

        lbl_M.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_M.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_M.setText("M");

        org.jdesktop.layout.GroupLayout btnMLayout = new org.jdesktop.layout.GroupLayout(btnM);
        btnM.setLayout(btnMLayout);
        btnMLayout.setHorizontalGroup(
            btnMLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnMLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_M, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnMLayout.setVerticalGroup(
            btnMLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_M, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnM, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 405, 29, -1));

        btnN.setBackground(new java.awt.Color(224, 246, 251));
        btnN.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnN.setPreferredSize(new java.awt.Dimension(30, 30));
        btnN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNMouseClicked(evt);
            }
        });

        lbl_N.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_N.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_N.setText("N");

        org.jdesktop.layout.GroupLayout btnNLayout = new org.jdesktop.layout.GroupLayout(btnN);
        btnN.setLayout(btnNLayout);
        btnNLayout.setHorizontalGroup(
            btnNLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnNLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_N, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnNLayout.setVerticalGroup(
            btnNLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_N, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnN, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 405, 29, -1));

        btnO.setBackground(new java.awt.Color(224, 246, 251));
        btnO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnO.setPreferredSize(new java.awt.Dimension(30, 30));
        btnO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOMouseClicked(evt);
            }
        });

        lbl_O.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_O.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_O.setText("O");

        org.jdesktop.layout.GroupLayout btnOLayout = new org.jdesktop.layout.GroupLayout(btnO);
        btnO.setLayout(btnOLayout);
        btnOLayout.setHorizontalGroup(
            btnOLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnOLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_O, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnOLayout.setVerticalGroup(
            btnOLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_O, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnO, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 446, 29, -1));

        btnP.setBackground(new java.awt.Color(224, 246, 251));
        btnP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnP.setPreferredSize(new java.awt.Dimension(30, 30));
        btnP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPMouseClicked(evt);
            }
        });

        lbl_P.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_P.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_P.setText("P");

        org.jdesktop.layout.GroupLayout btnPLayout = new org.jdesktop.layout.GroupLayout(btnP);
        btnP.setLayout(btnPLayout);
        btnPLayout.setHorizontalGroup(
            btnPLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnPLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_P, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnPLayout.setVerticalGroup(
            btnPLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_P, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnP, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 446, 29, -1));

        btnQ.setBackground(new java.awt.Color(224, 246, 251));
        btnQ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQ.setPreferredSize(new java.awt.Dimension(30, 30));
        btnQ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQMouseClicked(evt);
            }
        });

        lbl_Q.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Q.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Q.setText("Q");

        org.jdesktop.layout.GroupLayout btnQLayout = new org.jdesktop.layout.GroupLayout(btnQ);
        btnQ.setLayout(btnQLayout);
        btnQLayout.setHorizontalGroup(
            btnQLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnQLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_Q, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnQLayout.setVerticalGroup(
            btnQLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_Q, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnQ, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 446, 29, -1));

        btnR.setBackground(new java.awt.Color(224, 246, 251));
        btnR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnR.setPreferredSize(new java.awt.Dimension(30, 30));
        btnR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRMouseClicked(evt);
            }
        });

        lbl_R.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_R.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_R.setText("R");

        org.jdesktop.layout.GroupLayout btnRLayout = new org.jdesktop.layout.GroupLayout(btnR);
        btnR.setLayout(btnRLayout);
        btnRLayout.setHorizontalGroup(
            btnRLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnRLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_R, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnRLayout.setVerticalGroup(
            btnRLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_R, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnR, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 446, 29, -1));

        btnS.setBackground(new java.awt.Color(224, 246, 251));
        btnS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnS.setPreferredSize(new java.awt.Dimension(30, 30));
        btnS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSMouseClicked(evt);
            }
        });

        lbl_S.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_S.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_S.setText("S");

        org.jdesktop.layout.GroupLayout btnSLayout = new org.jdesktop.layout.GroupLayout(btnS);
        btnS.setLayout(btnSLayout);
        btnSLayout.setHorizontalGroup(
            btnSLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnSLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_S, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnSLayout.setVerticalGroup(
            btnSLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_S, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnS, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 446, 29, -1));

        btnT.setBackground(new java.awt.Color(224, 246, 251));
        btnT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnT.setPreferredSize(new java.awt.Dimension(30, 30));
        btnT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTMouseClicked(evt);
            }
        });

        lbl_T.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_T.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_T.setText("T");

        org.jdesktop.layout.GroupLayout btnTLayout = new org.jdesktop.layout.GroupLayout(btnT);
        btnT.setLayout(btnTLayout);
        btnTLayout.setHorizontalGroup(
            btnTLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnTLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_T, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnTLayout.setVerticalGroup(
            btnTLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_T, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnT, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 446, 29, -1));

        btnU.setBackground(new java.awt.Color(224, 246, 251));
        btnU.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnU.setPreferredSize(new java.awt.Dimension(30, 30));
        btnU.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUMouseClicked(evt);
            }
        });

        lbl_U.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_U.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_U.setText("U");

        org.jdesktop.layout.GroupLayout btnULayout = new org.jdesktop.layout.GroupLayout(btnU);
        btnU.setLayout(btnULayout);
        btnULayout.setHorizontalGroup(
            btnULayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnULayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_U, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnULayout.setVerticalGroup(
            btnULayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_U, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnU, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 446, 29, -1));

        btnV.setBackground(new java.awt.Color(224, 246, 251));
        btnV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnV.setPreferredSize(new java.awt.Dimension(30, 30));
        btnV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVMouseClicked(evt);
            }
        });

        lbl_V.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_V.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_V.setText("V");

        org.jdesktop.layout.GroupLayout btnVLayout = new org.jdesktop.layout.GroupLayout(btnV);
        btnV.setLayout(btnVLayout);
        btnVLayout.setHorizontalGroup(
            btnVLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnVLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_V, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnVLayout.setVerticalGroup(
            btnVLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_V, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnV, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 487, 29, -1));

        btnW.setBackground(new java.awt.Color(224, 246, 251));
        btnW.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnW.setPreferredSize(new java.awt.Dimension(30, 30));
        btnW.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnWMouseClicked(evt);
            }
        });

        lbl_W.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_W.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_W.setText("W");

        org.jdesktop.layout.GroupLayout btnWLayout = new org.jdesktop.layout.GroupLayout(btnW);
        btnW.setLayout(btnWLayout);
        btnWLayout.setHorizontalGroup(
            btnWLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnWLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_W, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnWLayout.setVerticalGroup(
            btnWLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_W, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnW, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 487, 29, -1));

        btnX.setBackground(new java.awt.Color(224, 246, 251));
        btnX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnX.setPreferredSize(new java.awt.Dimension(30, 30));
        btnX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXMouseClicked(evt);
            }
        });

        lbl_X.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_X.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_X.setText("X");

        org.jdesktop.layout.GroupLayout btnXLayout = new org.jdesktop.layout.GroupLayout(btnX);
        btnX.setLayout(btnXLayout);
        btnXLayout.setHorizontalGroup(
            btnXLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnXLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_X, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnXLayout.setVerticalGroup(
            btnXLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_X, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnX, new org.netbeans.lib.awtextra.AbsoluteConstraints(92, 487, 29, -1));

        btnY.setBackground(new java.awt.Color(224, 246, 251));
        btnY.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnY.setPreferredSize(new java.awt.Dimension(30, 30));
        btnY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnYMouseClicked(evt);
            }
        });

        lbl_Y.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Y.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Y.setText("Y");

        org.jdesktop.layout.GroupLayout btnYLayout = new org.jdesktop.layout.GroupLayout(btnY);
        btnY.setLayout(btnYLayout);
        btnYLayout.setHorizontalGroup(
            btnYLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnYLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_Y, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnYLayout.setVerticalGroup(
            btnYLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_Y, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnY, new org.netbeans.lib.awtextra.AbsoluteConstraints(127, 487, 29, -1));

        btnZ.setBackground(new java.awt.Color(224, 246, 251));
        btnZ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnZ.setPreferredSize(new java.awt.Dimension(30, 30));
        btnZ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnZMouseClicked(evt);
            }
        });

        lbl_Z.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Z.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Z.setText("Z");

        org.jdesktop.layout.GroupLayout btnZLayout = new org.jdesktop.layout.GroupLayout(btnZ);
        btnZ.setLayout(btnZLayout);
        btnZLayout.setHorizontalGroup(
            btnZLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnZLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_Z, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 9, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnZLayout.setVerticalGroup(
            btnZLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_Z, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnZ, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 487, 29, -1));

        btnReset.setBackground(new java.awt.Color(224, 246, 251));
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.setPreferredSize(new java.awt.Dimension(30, 30));
        btnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetMouseClicked(evt);
            }
        });

        lbl_Reset.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lbl_Reset.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Reset.setText("Reset");

        org.jdesktop.layout.GroupLayout btnResetLayout = new org.jdesktop.layout.GroupLayout(btnReset);
        btnReset.setLayout(btnResetLayout);
        btnResetLayout.setHorizontalGroup(
            btnResetLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnResetLayout.createSequentialGroup()
                .addContainerGap()
                .add(lbl_Reset, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnResetLayout.setVerticalGroup(
            btnResetLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, lbl_Reset, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        sidebarPanel.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 487, 64, -1));

        authorLabel.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        authorLabel.setForeground(new java.awt.Color(255, 255, 255));
        authorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebarPanel.add(authorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 570, 200, 20));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Drawable/ic_sidebar.png"))); // NOI18N
        sidebarPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 121, -1, -1));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Backpropagation Algorithm");
        sidebarPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 200, -1));

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Artificial Neural Network with");
        sidebarPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 200, -1));

        getContentPane().add(sidebarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 598));

        menubarPanel.setBackground(new java.awt.Color(255, 255, 255));

        btn_closeApp.setFont(new java.awt.Font("Muli", 0, 21)); // NOI18N
        btn_closeApp.setForeground(new java.awt.Color(48, 3, 12));
        btn_closeApp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_closeApp.setText("x");
        btn_closeApp.setToolTipText("Close");
        btn_closeApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_closeApp.setMaximumSize(new java.awt.Dimension(19, 26));
        btn_closeApp.setMinimumSize(new java.awt.Dimension(19, 26));
        btn_closeApp.setPreferredSize(new java.awt.Dimension(19, 26));
        btn_closeApp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_closeAppMouseClicked(evt);
            }
        });

        testingMenu.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        testingMenu.setText("Testing");
        testingMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        testingMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                testingMenuMouseClicked(evt);
            }
        });

        trainingMenu.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        trainingMenu.setForeground(new java.awt.Color(153, 153, 153));
        trainingMenu.setText("Training");
        trainingMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        trainingMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                trainingMenuMouseClicked(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));

        org.jdesktop.layout.GroupLayout menubarPanelLayout = new org.jdesktop.layout.GroupLayout(menubarPanel);
        menubarPanel.setLayout(menubarPanelLayout);
        menubarPanelLayout.setHorizontalGroup(
            menubarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, menubarPanelLayout.createSequentialGroup()
                .add(143, 143, 143)
                .add(testingMenu)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(trainingMenu)
                .add(147, 147, 147))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, menubarPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btn_closeApp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(menubarPanelLayout.createSequentialGroup()
                .add(35, 35, 35)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 510, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        menubarPanelLayout.setVerticalGroup(
            menubarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(menubarPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btn_closeApp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(menubarPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(testingMenu)
                    .add(trainingMenu))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(menubarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 583, -1));

        jDesktopPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        testFrame.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        testFrame.setNormalBounds(new java.awt.Rectangle(0, 0, 41, 0));
        testFrame.setVisible(true);

        testPanel.setBackground(new java.awt.Color(255, 255, 255));

        RC11.setBackground(new java.awt.Color(200, 236, 250));
        RC11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC11.setPreferredSize(new java.awt.Dimension(30, 30));
        RC11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC11MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC11Layout = new org.jdesktop.layout.GroupLayout(RC11);
        RC11.setLayout(RC11Layout);
        RC11Layout.setHorizontalGroup(
            RC11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC11Layout.setVerticalGroup(
            RC11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC12.setBackground(new java.awt.Color(200, 236, 250));
        RC12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC12.setPreferredSize(new java.awt.Dimension(30, 30));
        RC12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC12MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC12Layout = new org.jdesktop.layout.GroupLayout(RC12);
        RC12.setLayout(RC12Layout);
        RC12Layout.setHorizontalGroup(
            RC12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC12Layout.setVerticalGroup(
            RC12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC13.setBackground(new java.awt.Color(200, 236, 250));
        RC13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC13.setPreferredSize(new java.awt.Dimension(30, 30));
        RC13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC13MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC13Layout = new org.jdesktop.layout.GroupLayout(RC13);
        RC13.setLayout(RC13Layout);
        RC13Layout.setHorizontalGroup(
            RC13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC13Layout.setVerticalGroup(
            RC13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC14.setBackground(new java.awt.Color(200, 236, 250));
        RC14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC14.setPreferredSize(new java.awt.Dimension(30, 30));
        RC14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC14MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC14Layout = new org.jdesktop.layout.GroupLayout(RC14);
        RC14.setLayout(RC14Layout);
        RC14Layout.setHorizontalGroup(
            RC14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC14Layout.setVerticalGroup(
            RC14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC15.setBackground(new java.awt.Color(200, 236, 250));
        RC15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC15.setPreferredSize(new java.awt.Dimension(30, 30));
        RC15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC15MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC15Layout = new org.jdesktop.layout.GroupLayout(RC15);
        RC15.setLayout(RC15Layout);
        RC15Layout.setHorizontalGroup(
            RC15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC15Layout.setVerticalGroup(
            RC15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC16.setBackground(new java.awt.Color(200, 236, 250));
        RC16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC16.setPreferredSize(new java.awt.Dimension(30, 30));
        RC16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC16MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC16Layout = new org.jdesktop.layout.GroupLayout(RC16);
        RC16.setLayout(RC16Layout);
        RC16Layout.setHorizontalGroup(
            RC16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC16Layout.setVerticalGroup(
            RC16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC17.setBackground(new java.awt.Color(200, 236, 250));
        RC17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC17.setPreferredSize(new java.awt.Dimension(30, 30));
        RC17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC17MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC17Layout = new org.jdesktop.layout.GroupLayout(RC17);
        RC17.setLayout(RC17Layout);
        RC17Layout.setHorizontalGroup(
            RC17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC17Layout.setVerticalGroup(
            RC17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC21.setBackground(new java.awt.Color(200, 236, 250));
        RC21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC21.setPreferredSize(new java.awt.Dimension(30, 30));
        RC21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC21MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC21Layout = new org.jdesktop.layout.GroupLayout(RC21);
        RC21.setLayout(RC21Layout);
        RC21Layout.setHorizontalGroup(
            RC21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC21Layout.setVerticalGroup(
            RC21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC22.setBackground(new java.awt.Color(200, 236, 250));
        RC22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC22.setPreferredSize(new java.awt.Dimension(30, 30));
        RC22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC22MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC22Layout = new org.jdesktop.layout.GroupLayout(RC22);
        RC22.setLayout(RC22Layout);
        RC22Layout.setHorizontalGroup(
            RC22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC22Layout.setVerticalGroup(
            RC22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC23.setBackground(new java.awt.Color(200, 236, 250));
        RC23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC23.setPreferredSize(new java.awt.Dimension(30, 30));
        RC23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC23MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC23Layout = new org.jdesktop.layout.GroupLayout(RC23);
        RC23.setLayout(RC23Layout);
        RC23Layout.setHorizontalGroup(
            RC23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC23Layout.setVerticalGroup(
            RC23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC24.setBackground(new java.awt.Color(200, 236, 250));
        RC24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC24.setPreferredSize(new java.awt.Dimension(30, 30));
        RC24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC24MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC24Layout = new org.jdesktop.layout.GroupLayout(RC24);
        RC24.setLayout(RC24Layout);
        RC24Layout.setHorizontalGroup(
            RC24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC24Layout.setVerticalGroup(
            RC24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC25.setBackground(new java.awt.Color(200, 236, 250));
        RC25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC25.setPreferredSize(new java.awt.Dimension(30, 30));
        RC25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC25MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC25Layout = new org.jdesktop.layout.GroupLayout(RC25);
        RC25.setLayout(RC25Layout);
        RC25Layout.setHorizontalGroup(
            RC25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC25Layout.setVerticalGroup(
            RC25Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC26.setBackground(new java.awt.Color(200, 236, 250));
        RC26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC26.setPreferredSize(new java.awt.Dimension(30, 30));
        RC26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC26MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC26Layout = new org.jdesktop.layout.GroupLayout(RC26);
        RC26.setLayout(RC26Layout);
        RC26Layout.setHorizontalGroup(
            RC26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC26Layout.setVerticalGroup(
            RC26Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC27.setBackground(new java.awt.Color(200, 236, 250));
        RC27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC27.setPreferredSize(new java.awt.Dimension(30, 30));
        RC27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC27MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC27Layout = new org.jdesktop.layout.GroupLayout(RC27);
        RC27.setLayout(RC27Layout);
        RC27Layout.setHorizontalGroup(
            RC27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC27Layout.setVerticalGroup(
            RC27Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC31.setBackground(new java.awt.Color(200, 236, 250));
        RC31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC31.setPreferredSize(new java.awt.Dimension(30, 30));
        RC31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC31MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC31Layout = new org.jdesktop.layout.GroupLayout(RC31);
        RC31.setLayout(RC31Layout);
        RC31Layout.setHorizontalGroup(
            RC31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC31Layout.setVerticalGroup(
            RC31Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC32.setBackground(new java.awt.Color(200, 236, 250));
        RC32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC32.setPreferredSize(new java.awt.Dimension(30, 30));
        RC32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC32MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC32Layout = new org.jdesktop.layout.GroupLayout(RC32);
        RC32.setLayout(RC32Layout);
        RC32Layout.setHorizontalGroup(
            RC32Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC32Layout.setVerticalGroup(
            RC32Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC33.setBackground(new java.awt.Color(200, 236, 250));
        RC33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC33.setPreferredSize(new java.awt.Dimension(30, 30));
        RC33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC33MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC33Layout = new org.jdesktop.layout.GroupLayout(RC33);
        RC33.setLayout(RC33Layout);
        RC33Layout.setHorizontalGroup(
            RC33Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC33Layout.setVerticalGroup(
            RC33Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC34.setBackground(new java.awt.Color(200, 236, 250));
        RC34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC34.setPreferredSize(new java.awt.Dimension(30, 30));
        RC34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC34MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC34Layout = new org.jdesktop.layout.GroupLayout(RC34);
        RC34.setLayout(RC34Layout);
        RC34Layout.setHorizontalGroup(
            RC34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC34Layout.setVerticalGroup(
            RC34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC35.setBackground(new java.awt.Color(200, 236, 250));
        RC35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC35.setPreferredSize(new java.awt.Dimension(30, 30));
        RC35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC35MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC35Layout = new org.jdesktop.layout.GroupLayout(RC35);
        RC35.setLayout(RC35Layout);
        RC35Layout.setHorizontalGroup(
            RC35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC35Layout.setVerticalGroup(
            RC35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC36.setBackground(new java.awt.Color(200, 236, 250));
        RC36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC36.setPreferredSize(new java.awt.Dimension(30, 30));
        RC36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC36MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC36Layout = new org.jdesktop.layout.GroupLayout(RC36);
        RC36.setLayout(RC36Layout);
        RC36Layout.setHorizontalGroup(
            RC36Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC36Layout.setVerticalGroup(
            RC36Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC37.setBackground(new java.awt.Color(200, 236, 250));
        RC37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC37.setPreferredSize(new java.awt.Dimension(30, 30));
        RC37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC37MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC37Layout = new org.jdesktop.layout.GroupLayout(RC37);
        RC37.setLayout(RC37Layout);
        RC37Layout.setHorizontalGroup(
            RC37Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC37Layout.setVerticalGroup(
            RC37Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC41.setBackground(new java.awt.Color(200, 236, 250));
        RC41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC41.setPreferredSize(new java.awt.Dimension(30, 30));
        RC41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC41MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC41Layout = new org.jdesktop.layout.GroupLayout(RC41);
        RC41.setLayout(RC41Layout);
        RC41Layout.setHorizontalGroup(
            RC41Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC41Layout.setVerticalGroup(
            RC41Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC42.setBackground(new java.awt.Color(200, 236, 250));
        RC42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC42.setPreferredSize(new java.awt.Dimension(30, 30));
        RC42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC42MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC42Layout = new org.jdesktop.layout.GroupLayout(RC42);
        RC42.setLayout(RC42Layout);
        RC42Layout.setHorizontalGroup(
            RC42Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC42Layout.setVerticalGroup(
            RC42Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC43.setBackground(new java.awt.Color(200, 236, 250));
        RC43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC43.setPreferredSize(new java.awt.Dimension(30, 30));
        RC43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC43MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC43Layout = new org.jdesktop.layout.GroupLayout(RC43);
        RC43.setLayout(RC43Layout);
        RC43Layout.setHorizontalGroup(
            RC43Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC43Layout.setVerticalGroup(
            RC43Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC44.setBackground(new java.awt.Color(200, 236, 250));
        RC44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC44.setPreferredSize(new java.awt.Dimension(30, 30));
        RC44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC44MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC44Layout = new org.jdesktop.layout.GroupLayout(RC44);
        RC44.setLayout(RC44Layout);
        RC44Layout.setHorizontalGroup(
            RC44Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC44Layout.setVerticalGroup(
            RC44Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC45.setBackground(new java.awt.Color(200, 236, 250));
        RC45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC45.setPreferredSize(new java.awt.Dimension(30, 30));
        RC45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC45MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC45Layout = new org.jdesktop.layout.GroupLayout(RC45);
        RC45.setLayout(RC45Layout);
        RC45Layout.setHorizontalGroup(
            RC45Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC45Layout.setVerticalGroup(
            RC45Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC46.setBackground(new java.awt.Color(200, 236, 250));
        RC46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC46.setPreferredSize(new java.awt.Dimension(30, 30));
        RC46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC46MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC46Layout = new org.jdesktop.layout.GroupLayout(RC46);
        RC46.setLayout(RC46Layout);
        RC46Layout.setHorizontalGroup(
            RC46Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC46Layout.setVerticalGroup(
            RC46Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC47.setBackground(new java.awt.Color(200, 236, 250));
        RC47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC47.setPreferredSize(new java.awt.Dimension(30, 30));
        RC47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC47MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC47Layout = new org.jdesktop.layout.GroupLayout(RC47);
        RC47.setLayout(RC47Layout);
        RC47Layout.setHorizontalGroup(
            RC47Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC47Layout.setVerticalGroup(
            RC47Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC51.setBackground(new java.awt.Color(200, 236, 250));
        RC51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC51.setPreferredSize(new java.awt.Dimension(30, 30));
        RC51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC51MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC51Layout = new org.jdesktop.layout.GroupLayout(RC51);
        RC51.setLayout(RC51Layout);
        RC51Layout.setHorizontalGroup(
            RC51Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC51Layout.setVerticalGroup(
            RC51Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC52.setBackground(new java.awt.Color(200, 236, 250));
        RC52.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC52.setPreferredSize(new java.awt.Dimension(30, 30));
        RC52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC52MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC52Layout = new org.jdesktop.layout.GroupLayout(RC52);
        RC52.setLayout(RC52Layout);
        RC52Layout.setHorizontalGroup(
            RC52Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC52Layout.setVerticalGroup(
            RC52Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC53.setBackground(new java.awt.Color(200, 236, 250));
        RC53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC53.setPreferredSize(new java.awt.Dimension(30, 30));
        RC53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC53MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC53Layout = new org.jdesktop.layout.GroupLayout(RC53);
        RC53.setLayout(RC53Layout);
        RC53Layout.setHorizontalGroup(
            RC53Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC53Layout.setVerticalGroup(
            RC53Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC54.setBackground(new java.awt.Color(200, 236, 250));
        RC54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC54.setPreferredSize(new java.awt.Dimension(30, 30));
        RC54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC54MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC54Layout = new org.jdesktop.layout.GroupLayout(RC54);
        RC54.setLayout(RC54Layout);
        RC54Layout.setHorizontalGroup(
            RC54Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC54Layout.setVerticalGroup(
            RC54Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC55.setBackground(new java.awt.Color(200, 236, 250));
        RC55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC55.setPreferredSize(new java.awt.Dimension(30, 30));
        RC55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC55MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC55Layout = new org.jdesktop.layout.GroupLayout(RC55);
        RC55.setLayout(RC55Layout);
        RC55Layout.setHorizontalGroup(
            RC55Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC55Layout.setVerticalGroup(
            RC55Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC56.setBackground(new java.awt.Color(200, 236, 250));
        RC56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC56.setPreferredSize(new java.awt.Dimension(30, 30));
        RC56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC56MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC56Layout = new org.jdesktop.layout.GroupLayout(RC56);
        RC56.setLayout(RC56Layout);
        RC56Layout.setHorizontalGroup(
            RC56Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC56Layout.setVerticalGroup(
            RC56Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC57.setBackground(new java.awt.Color(200, 236, 250));
        RC57.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC57.setPreferredSize(new java.awt.Dimension(30, 30));
        RC57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC57MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC57Layout = new org.jdesktop.layout.GroupLayout(RC57);
        RC57.setLayout(RC57Layout);
        RC57Layout.setHorizontalGroup(
            RC57Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC57Layout.setVerticalGroup(
            RC57Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC61.setBackground(new java.awt.Color(200, 236, 250));
        RC61.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC61.setPreferredSize(new java.awt.Dimension(30, 30));
        RC61.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC61MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC61Layout = new org.jdesktop.layout.GroupLayout(RC61);
        RC61.setLayout(RC61Layout);
        RC61Layout.setHorizontalGroup(
            RC61Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC61Layout.setVerticalGroup(
            RC61Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC62.setBackground(new java.awt.Color(200, 236, 250));
        RC62.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC62.setPreferredSize(new java.awt.Dimension(30, 30));
        RC62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC62MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC62Layout = new org.jdesktop.layout.GroupLayout(RC62);
        RC62.setLayout(RC62Layout);
        RC62Layout.setHorizontalGroup(
            RC62Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC62Layout.setVerticalGroup(
            RC62Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC63.setBackground(new java.awt.Color(200, 236, 250));
        RC63.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC63.setPreferredSize(new java.awt.Dimension(30, 30));
        RC63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC63MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC63Layout = new org.jdesktop.layout.GroupLayout(RC63);
        RC63.setLayout(RC63Layout);
        RC63Layout.setHorizontalGroup(
            RC63Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC63Layout.setVerticalGroup(
            RC63Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC64.setBackground(new java.awt.Color(200, 236, 250));
        RC64.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC64.setPreferredSize(new java.awt.Dimension(30, 30));
        RC64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC64MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC64Layout = new org.jdesktop.layout.GroupLayout(RC64);
        RC64.setLayout(RC64Layout);
        RC64Layout.setHorizontalGroup(
            RC64Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC64Layout.setVerticalGroup(
            RC64Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC65.setBackground(new java.awt.Color(200, 236, 250));
        RC65.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC65.setPreferredSize(new java.awt.Dimension(30, 30));
        RC65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC65MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC65Layout = new org.jdesktop.layout.GroupLayout(RC65);
        RC65.setLayout(RC65Layout);
        RC65Layout.setHorizontalGroup(
            RC65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC65Layout.setVerticalGroup(
            RC65Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC66.setBackground(new java.awt.Color(200, 236, 250));
        RC66.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC66.setPreferredSize(new java.awt.Dimension(30, 30));
        RC66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC66MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC66Layout = new org.jdesktop.layout.GroupLayout(RC66);
        RC66.setLayout(RC66Layout);
        RC66Layout.setHorizontalGroup(
            RC66Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC66Layout.setVerticalGroup(
            RC66Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC67.setBackground(new java.awt.Color(200, 236, 250));
        RC67.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC67.setPreferredSize(new java.awt.Dimension(30, 30));
        RC67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC67MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC67Layout = new org.jdesktop.layout.GroupLayout(RC67);
        RC67.setLayout(RC67Layout);
        RC67Layout.setHorizontalGroup(
            RC67Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC67Layout.setVerticalGroup(
            RC67Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC71.setBackground(new java.awt.Color(200, 236, 250));
        RC71.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC71.setPreferredSize(new java.awt.Dimension(30, 30));
        RC71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC71MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC71Layout = new org.jdesktop.layout.GroupLayout(RC71);
        RC71.setLayout(RC71Layout);
        RC71Layout.setHorizontalGroup(
            RC71Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC71Layout.setVerticalGroup(
            RC71Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC72.setBackground(new java.awt.Color(200, 236, 250));
        RC72.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC72.setPreferredSize(new java.awt.Dimension(30, 30));
        RC72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC72MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC72Layout = new org.jdesktop.layout.GroupLayout(RC72);
        RC72.setLayout(RC72Layout);
        RC72Layout.setHorizontalGroup(
            RC72Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC72Layout.setVerticalGroup(
            RC72Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC73.setBackground(new java.awt.Color(200, 236, 250));
        RC73.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC73.setPreferredSize(new java.awt.Dimension(30, 30));
        RC73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC73MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC73Layout = new org.jdesktop.layout.GroupLayout(RC73);
        RC73.setLayout(RC73Layout);
        RC73Layout.setHorizontalGroup(
            RC73Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC73Layout.setVerticalGroup(
            RC73Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC74.setBackground(new java.awt.Color(200, 236, 250));
        RC74.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC74.setPreferredSize(new java.awt.Dimension(30, 30));
        RC74.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC74MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC74Layout = new org.jdesktop.layout.GroupLayout(RC74);
        RC74.setLayout(RC74Layout);
        RC74Layout.setHorizontalGroup(
            RC74Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC74Layout.setVerticalGroup(
            RC74Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC75.setBackground(new java.awt.Color(200, 236, 250));
        RC75.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC75.setPreferredSize(new java.awt.Dimension(30, 30));
        RC75.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC75MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC75Layout = new org.jdesktop.layout.GroupLayout(RC75);
        RC75.setLayout(RC75Layout);
        RC75Layout.setHorizontalGroup(
            RC75Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC75Layout.setVerticalGroup(
            RC75Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC76.setBackground(new java.awt.Color(200, 236, 250));
        RC76.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC76.setPreferredSize(new java.awt.Dimension(30, 30));
        RC76.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC76MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC76Layout = new org.jdesktop.layout.GroupLayout(RC76);
        RC76.setLayout(RC76Layout);
        RC76Layout.setHorizontalGroup(
            RC76Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC76Layout.setVerticalGroup(
            RC76Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC77.setBackground(new java.awt.Color(200, 236, 250));
        RC77.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC77.setPreferredSize(new java.awt.Dimension(30, 30));
        RC77.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC77MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC77Layout = new org.jdesktop.layout.GroupLayout(RC77);
        RC77.setLayout(RC77Layout);
        RC77Layout.setHorizontalGroup(
            RC77Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC77Layout.setVerticalGroup(
            RC77Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC81.setBackground(new java.awt.Color(200, 236, 250));
        RC81.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC81.setPreferredSize(new java.awt.Dimension(30, 30));
        RC81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC81MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC81Layout = new org.jdesktop.layout.GroupLayout(RC81);
        RC81.setLayout(RC81Layout);
        RC81Layout.setHorizontalGroup(
            RC81Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC81Layout.setVerticalGroup(
            RC81Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC82.setBackground(new java.awt.Color(200, 236, 250));
        RC82.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC82.setPreferredSize(new java.awt.Dimension(30, 30));
        RC82.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC82MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC82Layout = new org.jdesktop.layout.GroupLayout(RC82);
        RC82.setLayout(RC82Layout);
        RC82Layout.setHorizontalGroup(
            RC82Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC82Layout.setVerticalGroup(
            RC82Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC83.setBackground(new java.awt.Color(200, 236, 250));
        RC83.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC83.setPreferredSize(new java.awt.Dimension(30, 30));
        RC83.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC83MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC83Layout = new org.jdesktop.layout.GroupLayout(RC83);
        RC83.setLayout(RC83Layout);
        RC83Layout.setHorizontalGroup(
            RC83Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC83Layout.setVerticalGroup(
            RC83Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC84.setBackground(new java.awt.Color(200, 236, 250));
        RC84.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC84.setPreferredSize(new java.awt.Dimension(30, 30));
        RC84.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC84MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC84Layout = new org.jdesktop.layout.GroupLayout(RC84);
        RC84.setLayout(RC84Layout);
        RC84Layout.setHorizontalGroup(
            RC84Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC84Layout.setVerticalGroup(
            RC84Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC85.setBackground(new java.awt.Color(200, 236, 250));
        RC85.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC85.setPreferredSize(new java.awt.Dimension(30, 30));
        RC85.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC85MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC85Layout = new org.jdesktop.layout.GroupLayout(RC85);
        RC85.setLayout(RC85Layout);
        RC85Layout.setHorizontalGroup(
            RC85Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC85Layout.setVerticalGroup(
            RC85Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC86.setBackground(new java.awt.Color(200, 236, 250));
        RC86.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC86.setPreferredSize(new java.awt.Dimension(30, 30));
        RC86.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC86MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC86Layout = new org.jdesktop.layout.GroupLayout(RC86);
        RC86.setLayout(RC86Layout);
        RC86Layout.setHorizontalGroup(
            RC86Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC86Layout.setVerticalGroup(
            RC86Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC87.setBackground(new java.awt.Color(200, 236, 250));
        RC87.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC87.setPreferredSize(new java.awt.Dimension(30, 30));
        RC87.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC87MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC87Layout = new org.jdesktop.layout.GroupLayout(RC87);
        RC87.setLayout(RC87Layout);
        RC87Layout.setHorizontalGroup(
            RC87Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC87Layout.setVerticalGroup(
            RC87Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC91.setBackground(new java.awt.Color(200, 236, 250));
        RC91.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC91.setPreferredSize(new java.awt.Dimension(30, 30));
        RC91.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC91MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC91Layout = new org.jdesktop.layout.GroupLayout(RC91);
        RC91.setLayout(RC91Layout);
        RC91Layout.setHorizontalGroup(
            RC91Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC91Layout.setVerticalGroup(
            RC91Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC92.setBackground(new java.awt.Color(200, 236, 250));
        RC92.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC92.setPreferredSize(new java.awt.Dimension(30, 30));
        RC92.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC92MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC92Layout = new org.jdesktop.layout.GroupLayout(RC92);
        RC92.setLayout(RC92Layout);
        RC92Layout.setHorizontalGroup(
            RC92Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC92Layout.setVerticalGroup(
            RC92Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC93.setBackground(new java.awt.Color(200, 236, 250));
        RC93.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC93.setPreferredSize(new java.awt.Dimension(30, 30));
        RC93.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC93MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC93Layout = new org.jdesktop.layout.GroupLayout(RC93);
        RC93.setLayout(RC93Layout);
        RC93Layout.setHorizontalGroup(
            RC93Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC93Layout.setVerticalGroup(
            RC93Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC94.setBackground(new java.awt.Color(200, 236, 250));
        RC94.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC94.setPreferredSize(new java.awt.Dimension(30, 30));
        RC94.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC94MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC94Layout = new org.jdesktop.layout.GroupLayout(RC94);
        RC94.setLayout(RC94Layout);
        RC94Layout.setHorizontalGroup(
            RC94Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC94Layout.setVerticalGroup(
            RC94Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC95.setBackground(new java.awt.Color(200, 236, 250));
        RC95.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC95.setPreferredSize(new java.awt.Dimension(30, 30));
        RC95.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC95MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC95Layout = new org.jdesktop.layout.GroupLayout(RC95);
        RC95.setLayout(RC95Layout);
        RC95Layout.setHorizontalGroup(
            RC95Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC95Layout.setVerticalGroup(
            RC95Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC96.setBackground(new java.awt.Color(200, 236, 250));
        RC96.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC96.setPreferredSize(new java.awt.Dimension(30, 30));
        RC96.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC96MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC96Layout = new org.jdesktop.layout.GroupLayout(RC96);
        RC96.setLayout(RC96Layout);
        RC96Layout.setHorizontalGroup(
            RC96Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC96Layout.setVerticalGroup(
            RC96Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        RC97.setBackground(new java.awt.Color(200, 236, 250));
        RC97.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RC97.setPreferredSize(new java.awt.Dimension(30, 30));
        RC97.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RC97MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout RC97Layout = new org.jdesktop.layout.GroupLayout(RC97);
        RC97.setLayout(RC97Layout);
        RC97Layout.setHorizontalGroup(
            RC97Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );
        RC97Layout.setVerticalGroup(
            RC97Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 30, Short.MAX_VALUE)
        );

        btnTest.setBackground(new java.awt.Color(38, 200, 211));
        btnTest.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTest.setPreferredSize(new java.awt.Dimension(30, 30));
        btnTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTestMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TEST");

        org.jdesktop.layout.GroupLayout btnTestLayout = new org.jdesktop.layout.GroupLayout(btnTest);
        btnTest.setLayout(btnTestLayout);
        btnTestLayout.setHorizontalGroup(
            btnTestLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnTestLayout.createSequentialGroup()
                .add(96, 96, 96)
                .add(jLabel1)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        btnTestLayout.setVerticalGroup(
            btnTestLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, btnTestLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(jLabel1)
                .add(10, 10, 10))
        );

        org.jdesktop.layout.GroupLayout testPanelLayout = new org.jdesktop.layout.GroupLayout(testPanel);
        testPanel.setLayout(testPanelLayout);
        testPanelLayout.setHorizontalGroup(
            testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(testPanelLayout.createSequentialGroup()
                .add(198, 198, 198)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(testPanelLayout.createSequentialGroup()
                        .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(RC11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(RC21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(RC31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(RC41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(RC51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(RC61, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(RC71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(RC81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(RC91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(2, 2, 2)
                        .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(testPanelLayout.createSequentialGroup()
                                .add(RC92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(2, 2, 2)
                                .add(RC93, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(2, 2, 2)
                                .add(RC94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(testPanelLayout.createSequentialGroup()
                                .add(RC62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(2, 2, 2)
                                .add(RC63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(2, 2, 2)
                                .add(RC64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(testPanelLayout.createSequentialGroup()
                                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(RC32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(RC12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(RC22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(RC42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(2, 2, 2)
                                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(RC13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(RC23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(RC33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(RC43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                            .add(testPanelLayout.createSequentialGroup()
                                .add(RC52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(2, 2, 2)
                                .add(RC53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(2, 2, 2)
                                .add(RC54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(testPanelLayout.createSequentialGroup()
                                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(RC72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(RC82, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(2, 2, 2)
                                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(RC83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(RC73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                        .add(2, 2, 2)
                        .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(RC45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(RC15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(RC25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(2, 2, 2)
                                        .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(RC16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(RC26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                        .add(2, 2, 2)
                                        .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(RC27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                            .add(RC17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                    .add(testPanelLayout.createSequentialGroup()
                                        .add(RC35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(2, 2, 2)
                                        .add(RC37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .add(testPanelLayout.createSequentialGroup()
                                    .add(RC55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(2, 2, 2)
                                    .add(RC56, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(2, 2, 2)
                                    .add(RC57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(testPanelLayout.createSequentialGroup()
                                    .add(RC65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(2, 2, 2)
                                    .add(RC66, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(2, 2, 2)
                                    .add(RC67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(testPanelLayout.createSequentialGroup()
                                    .add(RC75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(2, 2, 2)
                                    .add(RC76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(2, 2, 2)
                                    .add(RC77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(testPanelLayout.createSequentialGroup()
                                    .add(RC85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(2, 2, 2)
                                    .add(RC86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(2, 2, 2)
                                    .add(RC87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                            .add(testPanelLayout.createSequentialGroup()
                                .add(RC95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(2, 2, 2)
                                .add(RC96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(2, 2, 2)
                                .add(RC97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(btnTest, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(188, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, testPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 283, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(159, 159, 159))
        );
        testPanelLayout.setVerticalGroup(
            testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(testPanelLayout.createSequentialGroup()
                .add(75, 75, 75)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC12, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC14, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC27, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC35, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC44, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC53, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC56, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC61, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC66, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC73, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC77, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC82, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC84, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(2, 2, 2)
                .add(testPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(RC97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC96, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC93, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(RC91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(30, 30, 30)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(btnTest, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(81, 81, 81))
        );

        org.jdesktop.layout.GroupLayout testFrameLayout = new org.jdesktop.layout.GroupLayout(testFrame.getContentPane());
        testFrame.getContentPane().setLayout(testFrameLayout);
        testFrameLayout.setHorizontalGroup(
            testFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 618, Short.MAX_VALUE)
            .add(testFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, testFrameLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(testPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        testFrameLayout.setVerticalGroup(
            testFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 561, Short.MAX_VALUE)
            .add(testFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, testFrameLayout.createSequentialGroup()
                    .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(testPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jDesktopPane1.add(testFrame, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -41, 620, 575));

        trainingFrame.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        trainingFrame.setVisible(false);

        trainingPanel.setBackground(new java.awt.Color(255, 255, 255));

        logDataField.setEditable(false);
        logDataField.setColumns(20);
        logDataField.setRows(5);
        jScrollPane2.setViewportView(logDataField);

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel2.setText("Training Process Log");

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel6.setText("Alpha");

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel8.setText("Tolerance");

        alphaField.setText("0.1");

        toleranceField.setText("0.1");

        btnTraining.setBackground(new java.awt.Color(38, 200, 211));
        btnTraining.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTraining.setPreferredSize(new java.awt.Dimension(30, 30));
        btnTraining.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTrainingMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("TRAIN");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        org.jdesktop.layout.GroupLayout btnTrainingLayout = new org.jdesktop.layout.GroupLayout(btnTraining);
        btnTraining.setLayout(btnTrainingLayout);
        btnTrainingLayout.setHorizontalGroup(
            btnTrainingLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnTrainingLayout.createSequentialGroup()
                .add(93, 93, 93)
                .add(jLabel3)
                .addContainerGap(94, Short.MAX_VALUE))
        );
        btnTrainingLayout.setVerticalGroup(
            btnTrainingLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, btnTrainingLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(jLabel3)
                .addContainerGap())
        );

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel9.setText("Epoch");

        epochField.setText("10000");

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel10.setText("Max Error Value");

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        jLabel11.setText("Epoch on Training");

        lbl_maxErrorValue.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_maxErrorValue.setText("0");
        lbl_maxErrorValue.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lbl_epoch.setFont(new java.awt.Font("Roboto", 0, 11)); // NOI18N
        lbl_epoch.setText("0");
        lbl_epoch.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        btnStop.setBackground(new java.awt.Color(238, 217, 220));
        btnStop.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStop.setPreferredSize(new java.awt.Dimension(30, 30));
        btnStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStopMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("STOP");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        org.jdesktop.layout.GroupLayout btnStopLayout = new org.jdesktop.layout.GroupLayout(btnStop);
        btnStop.setLayout(btnStopLayout);
        btnStopLayout.setHorizontalGroup(
            btnStopLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnStopLayout.createSequentialGroup()
                .add(96, 96, 96)
                .add(jLabel12)
                .addContainerGap(96, Short.MAX_VALUE))
        );
        btnStopLayout.setVerticalGroup(
            btnStopLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, btnStopLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(jLabel12)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout trainingPanelLayout = new org.jdesktop.layout.GroupLayout(trainingPanel);
        trainingPanel.setLayout(trainingPanelLayout);
        trainingPanelLayout.setHorizontalGroup(
            trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(trainingPanelLayout.createSequentialGroup()
                .add(39, 39, 39)
                .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(trainingPanelLayout.createSequentialGroup()
                        .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel6)
                            .add(jLabel8)
                            .add(alphaField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(toleranceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel9)
                            .add(epochField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(61, 61, 61))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, trainingPanelLayout.createSequentialGroup()
                        .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel11)
                            .add(jLabel10))
                        .add(18, 18, 18)
                        .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(lbl_maxErrorValue, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(lbl_epoch, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(17, 17, 17)))
                .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 266, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 42, Short.MAX_VALUE))
            .add(trainingPanelLayout.createSequentialGroup()
                .add(100, 100, 100)
                .add(jSeparator3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 398, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, trainingPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnStop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(btnTraining, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(72, 72, 72))
        );
        trainingPanelLayout.setVerticalGroup(
            trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(trainingPanelLayout.createSequentialGroup()
                .add(52, 52, 52)
                .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(trainingPanelLayout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 279, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(trainingPanelLayout.createSequentialGroup()
                        .add(jLabel6)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(alphaField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel8)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(toleranceField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(epochField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel10)
                            .add(lbl_maxErrorValue))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel11)
                            .add(lbl_epoch))))
                .add(33, 33, 33)
                .add(jSeparator3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(37, 37, 37)
                .add(trainingPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(btnTraining, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnStop, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(65, 65, 65))
        );

        org.jdesktop.layout.GroupLayout trainingFrameLayout = new org.jdesktop.layout.GroupLayout(trainingFrame.getContentPane());
        trainingFrame.getContentPane().setLayout(trainingFrameLayout);
        trainingFrameLayout.setHorizontalGroup(
            trainingFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 618, Short.MAX_VALUE)
            .add(trainingFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(trainingFrameLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(trainingPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        trainingFrameLayout.setVerticalGroup(
            trainingFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 552, Short.MAX_VALUE)
            .add(trainingFrameLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(trainingFrameLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(trainingPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jDesktopPane1.add(trainingFrame, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, -41, 620, 575));

        getContentPane().add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 79, 583, 519));

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RC11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC11MouseClicked
        boxValue(0, RC11);
    }//GEN-LAST:event_RC11MouseClicked

    private void RC12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC12MouseClicked
        boxValue(1, RC12);
    }//GEN-LAST:event_RC12MouseClicked

    private void RC13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC13MouseClicked
        boxValue(2, RC13);
    }//GEN-LAST:event_RC13MouseClicked

    private void RC14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC14MouseClicked
        boxValue(3, RC14);
    }//GEN-LAST:event_RC14MouseClicked

    private void RC15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC15MouseClicked
        boxValue(4, RC15);
    }//GEN-LAST:event_RC15MouseClicked

    private void RC16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC16MouseClicked
        boxValue(5, RC16);
    }//GEN-LAST:event_RC16MouseClicked

    private void RC17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC17MouseClicked
        boxValue(6, RC17);
    }//GEN-LAST:event_RC17MouseClicked

    private void RC21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC21MouseClicked
        boxValue(7, RC21);
    }//GEN-LAST:event_RC21MouseClicked

    private void RC22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC22MouseClicked
        boxValue(8, RC22);
    }//GEN-LAST:event_RC22MouseClicked

    private void RC23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC23MouseClicked
        boxValue(9, RC23);
    }//GEN-LAST:event_RC23MouseClicked

    private void RC24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC24MouseClicked
        boxValue(10, RC24);
    }//GEN-LAST:event_RC24MouseClicked

    private void RC25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC25MouseClicked
        boxValue(11, RC25);
    }//GEN-LAST:event_RC25MouseClicked

    private void RC26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC26MouseClicked
        boxValue(12, RC26);
    }//GEN-LAST:event_RC26MouseClicked

    private void RC27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC27MouseClicked
        boxValue(13, RC27);
    }//GEN-LAST:event_RC27MouseClicked

    private void RC31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC31MouseClicked
        boxValue(14, RC31);
    }//GEN-LAST:event_RC31MouseClicked

    private void RC32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC32MouseClicked
        boxValue(15, RC32);
    }//GEN-LAST:event_RC32MouseClicked

    private void RC33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC33MouseClicked
        boxValue(16, RC33);
    }//GEN-LAST:event_RC33MouseClicked

    private void RC34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC34MouseClicked
        boxValue(17, RC34);
    }//GEN-LAST:event_RC34MouseClicked

    private void RC35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC35MouseClicked
        boxValue(18, RC35);
    }//GEN-LAST:event_RC35MouseClicked

    private void RC36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC36MouseClicked
        boxValue(19, RC36);
    }//GEN-LAST:event_RC36MouseClicked

    private void RC37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC37MouseClicked
        boxValue(20, RC37);
    }//GEN-LAST:event_RC37MouseClicked

    private void RC41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC41MouseClicked
        boxValue(21, RC41);
    }//GEN-LAST:event_RC41MouseClicked

    private void RC42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC42MouseClicked
        boxValue(22, RC42);
    }//GEN-LAST:event_RC42MouseClicked

    private void RC43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC43MouseClicked
        boxValue(23, RC43);
    }//GEN-LAST:event_RC43MouseClicked

    private void RC44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC44MouseClicked
        boxValue(24, RC44);
    }//GEN-LAST:event_RC44MouseClicked

    private void RC45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC45MouseClicked
        boxValue(25, RC45);
    }//GEN-LAST:event_RC45MouseClicked

    private void RC46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC46MouseClicked
        boxValue(26, RC46);
    }//GEN-LAST:event_RC46MouseClicked

    private void RC47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC47MouseClicked
        boxValue(27, RC47);
    }//GEN-LAST:event_RC47MouseClicked

    private void RC51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC51MouseClicked
        boxValue(28, RC51);
    }//GEN-LAST:event_RC51MouseClicked

    private void RC52MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC52MouseClicked
        boxValue(29, RC52);
    }//GEN-LAST:event_RC52MouseClicked

    private void RC53MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC53MouseClicked
        boxValue(30, RC53);
    }//GEN-LAST:event_RC53MouseClicked

    private void RC54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC54MouseClicked
        boxValue(31, RC54);
    }//GEN-LAST:event_RC54MouseClicked

    private void RC55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC55MouseClicked
        boxValue(32, RC55);
    }//GEN-LAST:event_RC55MouseClicked

    private void RC56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC56MouseClicked
        boxValue(33, RC56);
    }//GEN-LAST:event_RC56MouseClicked

    private void RC57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC57MouseClicked
        boxValue(34, RC57);
    }//GEN-LAST:event_RC57MouseClicked

    private void RC61MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC61MouseClicked
        boxValue(35, RC61);
    }//GEN-LAST:event_RC61MouseClicked

    private void RC62MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC62MouseClicked
        boxValue(36, RC62);
    }//GEN-LAST:event_RC62MouseClicked

    private void RC63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC63MouseClicked
        boxValue(37, RC63);
    }//GEN-LAST:event_RC63MouseClicked

    private void RC64MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC64MouseClicked
        boxValue(38, RC64);
    }//GEN-LAST:event_RC64MouseClicked

    private void RC65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC65MouseClicked
        boxValue(39, RC65);
    }//GEN-LAST:event_RC65MouseClicked

    private void RC66MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC66MouseClicked
        boxValue(40, RC66);
    }//GEN-LAST:event_RC66MouseClicked

    private void RC67MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC67MouseClicked
        boxValue(41, RC67);
    }//GEN-LAST:event_RC67MouseClicked

    private void RC71MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC71MouseClicked
        boxValue(42, RC71);
    }//GEN-LAST:event_RC71MouseClicked

    private void RC72MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC72MouseClicked
        boxValue(43, RC72);
    }//GEN-LAST:event_RC72MouseClicked

    private void RC73MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC73MouseClicked
        boxValue(44, RC73);
    }//GEN-LAST:event_RC73MouseClicked

    private void RC74MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC74MouseClicked
        boxValue(45, RC74);
    }//GEN-LAST:event_RC74MouseClicked

    private void RC75MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC75MouseClicked
        boxValue(46, RC75);
    }//GEN-LAST:event_RC75MouseClicked

    private void RC76MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC76MouseClicked
        boxValue(47, RC76);
    }//GEN-LAST:event_RC76MouseClicked

    private void RC77MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC77MouseClicked
        boxValue(48, RC77);
    }//GEN-LAST:event_RC77MouseClicked

    private void RC81MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC81MouseClicked
        boxValue(49, RC81);
    }//GEN-LAST:event_RC81MouseClicked

    private void RC82MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC82MouseClicked
        boxValue(50, RC82);
    }//GEN-LAST:event_RC82MouseClicked

    private void RC83MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC83MouseClicked
        boxValue(51, RC83);
    }//GEN-LAST:event_RC83MouseClicked

    private void RC84MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC84MouseClicked
        boxValue(52, RC84);
    }//GEN-LAST:event_RC84MouseClicked

    private void RC85MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC85MouseClicked
        boxValue(53, RC85);
    }//GEN-LAST:event_RC85MouseClicked

    private void RC86MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC86MouseClicked
        boxValue(54, RC86);
    }//GEN-LAST:event_RC86MouseClicked

    private void RC87MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC87MouseClicked
        boxValue(55, RC87);
    }//GEN-LAST:event_RC87MouseClicked

    private void RC91MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC91MouseClicked
        boxValue(56, RC91);
    }//GEN-LAST:event_RC91MouseClicked

    private void RC92MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC92MouseClicked
        boxValue(57, RC92);
    }//GEN-LAST:event_RC92MouseClicked

    private void RC93MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC93MouseClicked
        boxValue(58, RC93);
    }//GEN-LAST:event_RC93MouseClicked

    private void RC94MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC94MouseClicked
        boxValue(59, RC94);
    }//GEN-LAST:event_RC94MouseClicked

    private void RC95MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC95MouseClicked
        boxValue(60, RC95);
    }//GEN-LAST:event_RC95MouseClicked

    private void RC96MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC96MouseClicked
        boxValue(61, RC96);
    }//GEN-LAST:event_RC96MouseClicked

    private void RC97MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RC97MouseClicked
        boxValue(62, RC97);
    }//GEN-LAST:event_RC97MouseClicked

    private void btn_closeAppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_closeAppMouseClicked
        close();
    }//GEN-LAST:event_btn_closeAppMouseClicked

    private void btnGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGMouseClicked
        resetBoxValue();
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(21, RC41);
        boxValue(28, RC51);
        boxValue(35, RC61);
        boxValue(38, RC64);
        boxValue(39, RC65);
        boxValue(40, RC66);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(61, RC96);
    }//GEN-LAST:event_btnGMouseClicked

    private void btnFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(14, RC31);
        boxValue(21, RC41);
        boxValue(28, RC51);
        boxValue(29, RC52);
        boxValue(30, RC53);
        boxValue(31, RC54);
        boxValue(32, RC55);
        boxValue(33, RC56);
        boxValue(35, RC61);
        boxValue(42, RC71);
        boxValue(49, RC81);
        boxValue(56, RC91);
    }//GEN-LAST:event_btnFMouseClicked

    private void btnEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(14, RC31);
        boxValue(21, RC41);
        boxValue(28, RC51);
        boxValue(29, RC52);
        boxValue(30, RC53);
        boxValue(31, RC54);
        boxValue(32, RC55);
        boxValue(33, RC56);
        boxValue(35, RC61);
        boxValue(42, RC71);
        boxValue(49, RC81);
        boxValue(56, RC91);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(61, RC96);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnEMouseClicked

    private void btnDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(7, RC21);
        boxValue(12, RC26);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(54, RC86);
        boxValue(56, RC91);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        
    }//GEN-LAST:event_btnDMouseClicked

    private void btnCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMouseClicked
        resetBoxValue();
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(21, RC41);
        boxValue(28, RC51);
        boxValue(35, RC61);
        boxValue(42, RC71);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(61, RC96);
    }//GEN-LAST:event_btnCMouseClicked

    private void btnBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(29, RC52);
        boxValue(30, RC53);
        boxValue(31, RC54);
        boxValue(32, RC55);
        boxValue(33, RC56);
        boxValue(35, RC61);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(56, RC91);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(61, RC96);
    }//GEN-LAST:event_btnBMouseClicked

    private void btnAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAMouseClicked
        resetBoxValue();
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(8, RC22);
        boxValue(12, RC26);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(36, RC62);
        boxValue(37, RC63);
        boxValue(38, RC64);
        boxValue(39, RC65);
        boxValue(40, RC66);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(56, RC91);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnAMouseClicked

    private void btnLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(7, RC21);
        boxValue(14, RC31);
        boxValue(21, RC41);
        boxValue(28, RC51);
        boxValue(35, RC61);
        boxValue(42, RC71);
        boxValue(49, RC81);
        boxValue(56, RC91);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(61, RC96);
    }//GEN-LAST:event_btnLMouseClicked

    private void btnMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(8, RC22);
        boxValue(12, RC26);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(16, RC33);
        boxValue(18, RC35);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(24, RC44);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(31, RC54);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(56, RC91);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnMMouseClicked

    private void btnNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(8, RC22);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(16, RC33);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(24, RC44);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(32, RC55);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(40, RC66);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(56, RC91);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnNMouseClicked

    private void btnIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIMouseClicked
        resetBoxValue();
        boxValue(3, RC14);
        boxValue(10, RC24);
        boxValue(17, RC34);
        boxValue(24, RC44);
        boxValue(31, RC54);
        boxValue(38, RC64);
        boxValue(45, RC74);
        boxValue(52, RC84);
        boxValue(59, RC94);
    }//GEN-LAST:event_btnIMouseClicked

    private void btnJMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnJMouseClicked
        resetBoxValue();
        boxValue(5, RC16);
        boxValue(12, RC26);
        boxValue(19, RC36);
        boxValue(26, RC46);
        boxValue(33, RC56);
        boxValue(40, RC66);
        boxValue(43, RC72);
        boxValue(47, RC76);
        boxValue(50, RC82);
        boxValue(54, RC86);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
    }//GEN-LAST:event_btnJMouseClicked

    private void btnKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(5, RC16);
        boxValue(7, RC21);
        boxValue(11, RC25);
        boxValue(14, RC31);
        boxValue(17, RC34);
        boxValue(21, RC41);
        boxValue(23, RC43);
        boxValue(28, RC51);
        boxValue(29, RC52);
        boxValue(35, RC61);
        boxValue(37, RC63);
        boxValue(42, RC71);
        boxValue(45, RC74);
        boxValue(49, RC81);
        boxValue(53, RC85);
        boxValue(56, RC91);
        boxValue(61, RC96);
    }//GEN-LAST:event_btnKMouseClicked

    private void btnHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(29, RC52);
        boxValue(30, RC53);
        boxValue(31, RC54);
        boxValue(32, RC55);
        boxValue(33, RC56);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(56, RC91);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnHMouseClicked

    private void btnSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSMouseClicked
        resetBoxValue();
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(21, RC41);
        boxValue(29, RC52);
        boxValue(30, RC53);
        boxValue(31, RC54);
        boxValue(32, RC55);
        boxValue(33, RC56);
        boxValue(41, RC67);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(61, RC96);
    }//GEN-LAST:event_btnSMouseClicked

    private void btnTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(6, RC17);
        boxValue(10, RC24);
        boxValue(17, RC34);
        boxValue(24, RC44);
        boxValue(31, RC54);
        boxValue(38, RC64);
        boxValue(45, RC74);
        boxValue(52, RC84);
        boxValue(59, RC94);
    }//GEN-LAST:event_btnTMouseClicked

    private void btnUMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(61, RC96);
    }//GEN-LAST:event_btnUMouseClicked

    private void btnPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPMouseClicked
        resetBoxValue();
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(29, RC52);
        boxValue(30, RC53);
        boxValue(31, RC54);
        boxValue(32, RC55);
        boxValue(33, RC56);
        boxValue(35, RC61);
        boxValue(42, RC71);
        boxValue(49, RC81);
        boxValue(56, RC91);
    }//GEN-LAST:event_btnPMouseClicked

    private void btnQMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQMouseClicked
        resetBoxValue();
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(8, RC22);
        boxValue(12, RC26);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(46, RC75);
        boxValue(48, RC77);
        boxValue(50, RC82);
        boxValue(54, RC86);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnQMouseClicked

    private void btnRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRMouseClicked
        resetBoxValue();
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(29, RC52);
        boxValue(30, RC53);
        boxValue(31, RC54);
        boxValue(32, RC55);
        boxValue(33, RC56);
        boxValue(35, RC61);
        boxValue(38, RC64);
        boxValue(42, RC71);
        boxValue(46, RC75);
        boxValue(49, RC81);
        boxValue(54, RC86);
        boxValue(56, RC91);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnRMouseClicked

    private void btnOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOMouseClicked
        resetBoxValue();
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(8, RC22);
        boxValue(12, RC26);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(48, RC77);
        boxValue(50, RC82);
        boxValue(54, RC86);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
    }//GEN-LAST:event_btnOMouseClicked

    private void btnZMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnZMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(1, RC12);
        boxValue(2, RC13);
        boxValue(3, RC14);
        boxValue(4, RC15);
        boxValue(5, RC16);
        boxValue(6, RC17);
        boxValue(13, RC27);
        boxValue(19, RC36);
        boxValue(25, RC45);
        boxValue(31, RC54);
        boxValue(37, RC63);
        boxValue(43, RC72);
        boxValue(49, RC81);
        boxValue(56, RC91);
        boxValue(57, RC92);
        boxValue(58, RC93);
        boxValue(59, RC94);
        boxValue(60, RC95);
        boxValue(61, RC96);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnZMouseClicked

    private void btnResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetMouseClicked
        resetBoxValue();
    }//GEN-LAST:event_btnResetMouseClicked

    private void btnWMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(31, RC54);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(38, RC64);
        boxValue(41, RC67);
        boxValue(42, RC71);
        boxValue(44, RC73);
        boxValue(45, RC74);
        boxValue(46, RC75);
        boxValue(48, RC77);
        boxValue(49, RC81);
        boxValue(51, RC83);
        boxValue(53, RC85);
        boxValue(55, RC87);
        boxValue(57, RC92);
        boxValue(61, RC96);
    }//GEN-LAST:event_btnWMouseClicked

    private void btnXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(15, RC32);
        boxValue(19, RC36);
        boxValue(23, RC43);
        boxValue(25, RC45);
        boxValue(31, RC54);
        boxValue(37, RC63);
        boxValue(39, RC65);
        boxValue(43, RC72);
        boxValue(47, RC76);
        boxValue(49, RC81);
        boxValue(55, RC87);
        boxValue(56, RC91);
        boxValue(62, RC97);
    }//GEN-LAST:event_btnXMouseClicked

    private void btnYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnYMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(15, RC32);
        boxValue(19, RC36);
        boxValue(23, RC43);
        boxValue(25, RC45);
        boxValue(31, RC54);
        boxValue(38, RC64);
        boxValue(45, RC74);
        boxValue(52, RC84);
        boxValue(59, RC94);
    }//GEN-LAST:event_btnYMouseClicked

    private void btnVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVMouseClicked
        resetBoxValue();
        boxValue(0, RC11);
        boxValue(6, RC17);
        boxValue(7, RC21);
        boxValue(13, RC27);
        boxValue(14, RC31);
        boxValue(20, RC37);
        boxValue(21, RC41);
        boxValue(27, RC47);
        boxValue(28, RC51);
        boxValue(34, RC57);
        boxValue(35, RC61);
        boxValue(41, RC67);
        boxValue(43, RC72);
        boxValue(47, RC76);
        boxValue(51, RC83);
        boxValue(53, RC85);
        boxValue(59, RC94);
    }//GEN-LAST:event_btnVMouseClicked

    private void testingMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testingMenuMouseClicked
        switchMenu(testingMenu);
    }//GEN-LAST:event_testingMenuMouseClicked

    private void trainingMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_trainingMenuMouseClicked
        switchMenu(trainingMenu);
    }//GEN-LAST:event_trainingMenuMouseClicked

    private void btnTestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTestMouseClicked
        tes();
    }//GEN-LAST:event_btnTestMouseClicked

    private void btnTrainingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrainingMouseClicked
//        int x = (int)(Math.random()*999999999);
//        lbl_maxErrorValue.setText("0.0"+String.valueOf(x));

//        training();
        stop = false;
        thread = new Thread(this);
        thread.start();
    }//GEN-LAST:event_btnTrainingMouseClicked

    private void btnStopMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStopMouseClicked
        stop = true;
        btnStop.setEnabled(false);
        btnStop.setBackground(new Color(238,217,220));
        btnTraining.setEnabled(true);
        btnTraining.setBackground(new Color(38,200,211));
    }//GEN-LAST:event_btnStopMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPattern.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPattern.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPattern.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPattern.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPattern().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel RC11;
    private javax.swing.JPanel RC12;
    private javax.swing.JPanel RC13;
    private javax.swing.JPanel RC14;
    private javax.swing.JPanel RC15;
    private javax.swing.JPanel RC16;
    private javax.swing.JPanel RC17;
    private javax.swing.JPanel RC21;
    private javax.swing.JPanel RC22;
    private javax.swing.JPanel RC23;
    private javax.swing.JPanel RC24;
    private javax.swing.JPanel RC25;
    private javax.swing.JPanel RC26;
    private javax.swing.JPanel RC27;
    private javax.swing.JPanel RC31;
    private javax.swing.JPanel RC32;
    private javax.swing.JPanel RC33;
    private javax.swing.JPanel RC34;
    private javax.swing.JPanel RC35;
    private javax.swing.JPanel RC36;
    private javax.swing.JPanel RC37;
    private javax.swing.JPanel RC41;
    private javax.swing.JPanel RC42;
    private javax.swing.JPanel RC43;
    private javax.swing.JPanel RC44;
    private javax.swing.JPanel RC45;
    private javax.swing.JPanel RC46;
    private javax.swing.JPanel RC47;
    private javax.swing.JPanel RC51;
    private javax.swing.JPanel RC52;
    private javax.swing.JPanel RC53;
    private javax.swing.JPanel RC54;
    private javax.swing.JPanel RC55;
    private javax.swing.JPanel RC56;
    private javax.swing.JPanel RC57;
    private javax.swing.JPanel RC61;
    private javax.swing.JPanel RC62;
    private javax.swing.JPanel RC63;
    private javax.swing.JPanel RC64;
    private javax.swing.JPanel RC65;
    private javax.swing.JPanel RC66;
    private javax.swing.JPanel RC67;
    private javax.swing.JPanel RC71;
    private javax.swing.JPanel RC72;
    private javax.swing.JPanel RC73;
    private javax.swing.JPanel RC74;
    private javax.swing.JPanel RC75;
    private javax.swing.JPanel RC76;
    private javax.swing.JPanel RC77;
    private javax.swing.JPanel RC81;
    private javax.swing.JPanel RC82;
    private javax.swing.JPanel RC83;
    private javax.swing.JPanel RC84;
    private javax.swing.JPanel RC85;
    private javax.swing.JPanel RC86;
    private javax.swing.JPanel RC87;
    private javax.swing.JPanel RC91;
    private javax.swing.JPanel RC92;
    private javax.swing.JPanel RC93;
    private javax.swing.JPanel RC94;
    private javax.swing.JPanel RC95;
    private javax.swing.JPanel RC96;
    private javax.swing.JPanel RC97;
    private javax.swing.JTextField alphaField;
    private javax.swing.JLabel authorLabel;
    private javax.swing.JPanel btnA;
    private javax.swing.JPanel btnB;
    private javax.swing.JPanel btnC;
    private javax.swing.JPanel btnD;
    private javax.swing.JPanel btnE;
    private javax.swing.JPanel btnF;
    private javax.swing.JPanel btnG;
    private javax.swing.JPanel btnH;
    private javax.swing.JPanel btnI;
    private javax.swing.JPanel btnJ;
    private javax.swing.JPanel btnK;
    private javax.swing.JPanel btnL;
    private javax.swing.JPanel btnM;
    private javax.swing.JPanel btnN;
    private javax.swing.JPanel btnO;
    private javax.swing.JPanel btnP;
    private javax.swing.JPanel btnQ;
    private javax.swing.JPanel btnR;
    private javax.swing.JPanel btnReset;
    private javax.swing.JPanel btnS;
    private javax.swing.JPanel btnStop;
    private javax.swing.JPanel btnT;
    private javax.swing.JPanel btnTest;
    private javax.swing.JPanel btnTraining;
    private javax.swing.JPanel btnU;
    private javax.swing.JPanel btnV;
    private javax.swing.JPanel btnW;
    private javax.swing.JPanel btnX;
    private javax.swing.JPanel btnY;
    private javax.swing.JPanel btnZ;
    private javax.swing.JLabel btn_closeApp;
    private javax.swing.JTextField epochField;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbl_A;
    private javax.swing.JLabel lbl_B;
    private javax.swing.JLabel lbl_C;
    private javax.swing.JLabel lbl_D;
    private javax.swing.JLabel lbl_E;
    private javax.swing.JLabel lbl_F;
    private javax.swing.JLabel lbl_G;
    private javax.swing.JLabel lbl_H;
    private javax.swing.JLabel lbl_I;
    private javax.swing.JLabel lbl_J;
    private javax.swing.JLabel lbl_K;
    private javax.swing.JLabel lbl_L;
    private javax.swing.JLabel lbl_M;
    private javax.swing.JLabel lbl_N;
    private javax.swing.JLabel lbl_O;
    private javax.swing.JLabel lbl_P;
    private javax.swing.JLabel lbl_Q;
    private javax.swing.JLabel lbl_R;
    private javax.swing.JLabel lbl_Reset;
    private javax.swing.JLabel lbl_S;
    private javax.swing.JLabel lbl_T;
    private javax.swing.JLabel lbl_U;
    private javax.swing.JLabel lbl_V;
    private javax.swing.JLabel lbl_W;
    private javax.swing.JLabel lbl_X;
    private javax.swing.JLabel lbl_Y;
    private javax.swing.JLabel lbl_Z;
    private javax.swing.JLabel lbl_epoch;
    private javax.swing.JLabel lbl_maxErrorValue;
    private javax.swing.JTextArea logDataField;
    private javax.swing.JPanel menubarPanel;
    private javax.swing.JOptionPane notifPanel;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JInternalFrame testFrame;
    private javax.swing.JPanel testPanel;
    private javax.swing.JLabel testingMenu;
    private javax.swing.JTextField toleranceField;
    private javax.swing.JInternalFrame trainingFrame;
    private javax.swing.JLabel trainingMenu;
    private javax.swing.JPanel trainingPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        AlphabetLibrary to = new AlphabetLibrary();
        btnTraining.setEnabled(false);
        btnTraining.setBackground(new Color(193,229,231));
        btnStop.setEnabled(true);
        btnStop.setBackground(new Color(247,61,83));
        
        //baca paramater inisialisasi
        double alpha = Double.valueOf(alphaField.getText());
        double teta = Double.valueOf(toleranceField.getText());
        double maxEpoch = Double.valueOf(epochField.getText());

        //inisialisasi bobot layer input-hidden
        for (byte j = 0; j < z.length; j++) {
            for (byte i = 0; i < pola.x[0].length; i++) {
                v[i][j] = Math.random();
            }
        }

        //inisialisasi bias ke layer hidden
        for (byte j = 0; j < z.length; j++) {
            bias_z[j] = Math.random();
        }

        //inisialisasi bobot layer hidden-output
        for (byte j = 0; j < y.length; j++) {
            for (byte i = 0; i < z.length; i++) {
                w[i][j] = Math.random();
            }
        }

        //inisialisasi bias ke layer output
        for (byte j = 0; j < y.length; j++) {
            bias_y[j] = Math.random();
        }

        double err = 0;
        double mse = 0;
        int epoch = 1;
        boolean ada_error = false;
        do {
            System.out.println("=====Epoch -> " + epoch);
            logDataField.setText("");
            logDataField.append("========= Epoch "+epoch+" =========\n");
            lbl_epoch.setText(String.valueOf(epoch));

            mse = 0;
            ada_error = false;
            for (byte k = 0; k < pola.x.length; k++) {
                //feed-forward layer input-hidden
                for (byte j = 0; j < z.length; j++) {
                    z_net[j] = 0;
                    for (byte i = 0; i < pola.x[k].length; i++) {
                        z_net[j] += (pola.x[k][i] * v[i][j]);
                    }
                    z_net[j] += bias_z[j];
                    z[j] = sigmoid_bipolar(z_net[j]);
                }

                //feed-forward layer hidden-output
                for (byte j = 0; j < y.length; j++) {
                    y_net[j] = 0;
                    for (byte i = 0; i < z.length; i++) {
                        y_net[j] += (z[i] * w[i][j]);
                    }
                    y_net[j] += bias_y[j];
                    y[j] = sigmoid_bipolar(y_net[j]);
                }

                //hitung error output
                //hitung koreksi bobot hidden-output
                err = 0;
                double[] delta = new double[y.length];
                for (byte j = 0; j < y.length; j++) {
                    double e = to.target[k][j] - y[j];
                    System.out.println("error node ke-"+(j+1)+"=" + e);
//                    logDataField.append("Error node ke: "+(j + 1)+" -> "+ e+"\n");
                    err += Math.pow(e, 2);
                    //err += Math.abs(e);
                    delta[j] = e * turunan_sigmoid_bipolar(y_net[j]);
                    //System.out.println("delta="+delta[j]);
                }

                //err /= jml_output;
                err *= 0.5;
                //if (err>teta) ada_error=true;
                mse += (err * err);
                //mse += err;
                System.out.println("Error pola ke-" + (k + 1) + " -> " + err);
                logDataField.append("Error pola "+(k + 1)+" = "+ err+"\n");
                //tf_error.setText(String.valueOf(err));
                //if (err > teta) {
                //stop = false;
                //}

                //update bobot layer hidden-output
                for (byte j = 0; j < y.length; j++) {
                    for (byte i = 0; i < z.length; i++) {
                        w[i][j] += (alpha * delta[j] * z[i]);
                        //System.out.println("w["+i+"]["+j+"]="+w[i][j]);
                    }
                    bias_y[j] += (alpha * delta[j]);
                    //System.out.println("bias_y["+j+"]="+bias_y[j]);
                }

                //hitung koreksi bobot input-hidden
                double[] delta2 = new double[z.length];
                for (byte j = 0; j < z.length; j++) {
                    double error_hidden = 0;
                    for (byte i = 0; i < y.length; i++) {
                        error_hidden += (delta[i] * w[j][i]);
                    }
                    delta2[j] = error_hidden * turunan_sigmoid_bipolar(z_net[j]);
                    //System.out.println("delta2="+delta2[j]);
                }

                //update bobot layer input-hidden
                for (byte j = 0; j < z.length; j++) {
                    for (byte i = 0; i < pola.x[k].length; i++) {
                        v[i][j] += (alpha * delta2[j] * pola.x[k][i]);
                        //System.out.println("v["+i+"]["+j+"]="+v[i][j]);
                    }
                    bias_z[j] += (alpha * delta2[j]);
                    //System.out.println("bias_z["+j+"]="+bias_z[j]);
                }
            }
            mse /= pola.x.length;
            System.out.println("MSE=" + mse);
            lbl_maxErrorValue.setText(String.valueOf(mse));
            epoch++;
            try {
                Thread.sleep(7);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainPattern.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (epoch > maxEpoch) {
                stop = true;
                btnStop.setEnabled(false);
                btnStop.setBackground(new Color(238,217,220));
                btnTraining.setEnabled(true);
                btnTraining.setBackground(new Color(38,200,211));
            }
        } while (mse > teta && !stop);
        //} while (ada_error && !stop);
        btnStop.setEnabled(false);
    }
}
