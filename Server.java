
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class Server extends javax.swing.JFrame {


    ServerSocket ss;
    HashMap clientColl=new HashMap();
    
    
    public Server() {
        
        try{
             initComponents();
             ss=new ServerSocket (2089);
             this.sStatus.setText("Server Started.");
             
             new ClientAccept().start();     
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
    class ClientAccept extends Thread{
        public void run() {
            while(true){
                try{
                    Socket s=ss.accept();
                    String i=new DataInputStream(s.getInputStream()).readUTF();
                    if(clientColl.containsKey(i)) {
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("You are already registered...!!");
                    } else {
                        clientColl.put(i,s);
                        msgBox.append(i+" joined. \n");
                        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
                        dout.writeUTF("");
                        new MsgRead(s,i).start();
                        new PrepareClientList().start();
                    }
                    
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    class MsgRead extends Thread{
        Socket s;
        String ID;
        MsgRead(Socket s,String ID){
            this.s=s;
            this.ID=ID;
        }
        public void run(){
            while(!clientColl.isEmpty()){
                try{
                    String i = new DataInputStream(s.getInputStream()).readUTF();
                    if(i.equals("mkoihgteazdcvgyhujb096785542AXTY")){
                        clientColl.remove(ID);  
                        msgBox.append(ID+":removed! \n");
                        new PrepareClientList().start();
                        Set<String>k = clientColl.keySet();
                       
                         Iterator itr = k.iterator();
                        while(itr.hasNext()){           
                            String key = (String)itr.next();
                            if(!key.equalsIgnoreCase(ID)){
                                try{
                                    new DataOutputStream(((Socket)clientColl.get(key)).getOutputStream()).writeUTF(ID + ": LEFT CHAT!");
                                } catch (Exception ex) {
                                    clientColl.remove(key);
                                    msgBox.append(key+":removed!");
                                    new PrepareClientList().start();
                                }
                            }
                            
                        }
                    }
                    else if (i.contains("#4344554@@@@@67667@@")) {
                        i = i.substring(20);
                        StringTokenizer st = new StringTokenizer(i, ":");
                        String id = st.nextToken();
                        i = st.nextToken();
                        try {
                            new DataOutputStream(((Socket) clientColl.get(id)).getOutputStream()).writeUTF(" < "+ID+" to "+id+" > "+i);
                        } catch (Exception ex) {
                            clientColl.remove(id);
                            msgBox.append(id + "removed!");
                            new PrepareClientList().start();

                        }
                    } else {
                        Set k = clientColl.keySet();
                        Iterator itr = k.iterator();
                        while (itr.hasNext()) {
                            String key = (String) itr.next();
                            if (!key.equalsIgnoreCase(ID)) {
                                try {
                                    new DataOutputStream(((Socket) clientColl.get(key)).getOutputStream()).writeUTF(" < " + ID + " to ALL > " + i);
                                } catch (Exception ex) {
                                    clientColl.remove(key);
                                    msgBox.append(key + ":removed!");
                                    new PrepareClientList().start();
                                }
                            }
                        }
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
        }
        
    }
    class PrepareClientList extends Thread{
        
                public void run(){
                    try{
                        String ids = "";
                        Set k=clientColl.keySet();
                        Iterator itr=k.iterator();
                        while (itr.hasNext()){
                            String key=(String) itr.next();
                            ids+=key+",";
                        }
                        if (ids.length()!=0)
                            ids=ids.substring(0,ids.length()-1);
                        itr=k.iterator();
                        while (itr.hasNext()){
                            String key=(String)itr.next();
                            try{
                                new DataOutputStream(((Socket)clientColl.get(key)).getOutputStream()).writeUTF(":;.,/="+ids);
                            }catch(Exception ex){
                                clientColl.remove(key);
                                msgBox.append(key+":removed!");
                            }
                        }
                        
                    }catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    
            
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        sStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        msgBox.setColumns(20);
        msgBox.setRows(5);
        jScrollPane1.setViewportView(msgBox);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Server Status:");

        sStatus.setText("........................................................................");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(sStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgBox;
    private javax.swing.JLabel sStatus;
    // End of variables declaration//GEN-END:variables
}

