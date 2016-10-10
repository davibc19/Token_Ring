package sd_2;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente1 extends javax.swing.JFrame
{

    // ---------------------------------------------------------------------------------------------------------------- //
    //                                             Declaring Variables                                                  //
    // ---------------------------------------------------------------------------------------------------------------- //
    /*
     * Variable to flag who is the coordenator of the Token Ring
     */
    static boolean coordenator = true;

    /*
     * Variable to store the message that will be send
     */
    static String broadcastMessage;

    /*
     * Variable to store the miliseconds that the process will wait before it
     * declares a timeout
     */
    static int TIMEOUT_LIMIT = 10000;

    /*
     * Variables to inform deteails of Socket's Doors that belong to the Token Ring
     */
    // Door's number
    static int thisDoor = 5000;
    // List of Doors stored in the Token Ring
    static String doorList = "" + thisDoor;
    // Previous doors --> variable to aid the passage of the token throught the ring 
    static String listPreviousDoors = doorList;

    /* 
     * Variable to flag which process a Token Request 
     */
    boolean requestToken = false;

    /* 
     * Method that inicialize the interface components of this Client
     */
    public Cliente1()
    {
        initComponents();
    }

    /*
     * This method is responsable to choose one door to restart the token. This will be done like:
     * String "doorList" will be splited;
     * The index of the new splited array will be the identifier of a process;
     * Then, we choose the biggest index and send the "token" message to it, restartint the ring;
     */
    static void bullyElectionAlgorithm(String str1)
    {
        String[] strdiv = str1.split("_");
        if (strdiv.length != 0)
        {
            int doorElected = Integer.parseInt(strdiv[strdiv.length - 1]);
            System.out.println("Porta Escolhida para Reiniciar o Anel: " + doorElected);
            try
            {
                System.out.println("Reiniciando Token");
                Socket clntSock_send = new Socket("localhost", doorElected);
                PrintStream ps = new PrintStream(clntSock_send.getOutputStream());
                ps.println("token");
                ps.println(doorList);
                clntSock_send.close();

            } catch (IOException ex)
            {
                Logger.getLogger(Cliente1.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
     * This method is responsable to check the integrity of the Token Ring
     * It is triggered when a timeout occurs in the main method, after
     * waiting 10 sec for an incoming message that never arrived
     */
    static void checkRingIntegrity() throws IOException
    {
        /* 
         Send messages to previous Socket with the message: "CheckIntegrity".
         If one process does not reply "ok", we might assume that this proccess
         is down/dead. So we start "bully" election code
         */
        System.out.println("Fazendo verificação de integridade do anel");
        ServerSocket servSock = null;
        Socket clntSock_recieve = null;
        Socket clntSock_send = null;
        String reply = "NO ANWSER";
        broadcastMessage = "CheckIntegrity";

        int previousDoor = getPreviousDoor(doorList, "" + thisDoor);
        while (previousDoor != thisDoor)
        {
            try
            {
                clntSock_send = new Socket("localhost", previousDoor);
                PrintStream ps = new PrintStream(clntSock_send.getOutputStream());

                // Envia a mensagem "entryRequirement", juntamente com as portas que estão registradas
                System.out.println("Enviando a mensagem: '" + broadcastMessage + "' para a porta: " + previousDoor);
                ps.println(broadcastMessage);
                ps.println(thisDoor);
                ps.close();
                clntSock_send.close();

                // Abre um Socket para aguardar resposta
                servSock = new ServerSocket(thisDoor);
                servSock.setSoTimeout(TIMEOUT_LIMIT);
                try
                {
                    clntSock_recieve = servSock.accept();
                } catch (java.io.InterruptedIOException e)
                {
                    System.out.println("O processo " + previousDoor + " não está respondendo. Removendo-o do anel...");
                    removeDoorFromList(doorList, "" + previousDoor);
                    System.out.println("Iniciando algoritmo de eleição...");
                    bullyElectionAlgorithm(doorList);
                    clntSock_recieve.close();
                    clntSock_send.close();
                    servSock.close();
                    break;
                }
                Scanner res = new Scanner(clntSock_recieve.getInputStream());
                reply = res.nextLine();

                clntSock_recieve.close();
                clntSock_send.close();
                servSock.close();
                System.out.println(">> O processo " + previousDoor + " respondeu: " + reply);
                previousDoor = getPreviousDoor(doorList, "" + previousDoor);

            } catch (ConnectException ex)
            {
                clntSock_recieve.close();
                clntSock_send.close();
                servSock.close();
                System.out.println("Não foi possível conectar com o processo " + previousDoor + ". Removendo-o do anel...");
                removeDoorFromList(doorList, "" + previousDoor);
                System.out.println("Iniciando algoritmo de eleição...");
                bullyElectionAlgorithm(doorList);
                break;
            } catch (IOException ex)
            {
                System.out.println("[Error IO]: " + ex);
            }
        }
    }

// Function to start the token, passing it to the next process of the list
    public void startToken()
    {
        try
        {
            System.out.println("Iniciando Token");
            System.out.println(getNextDoor(doorList, "" + thisDoor));
            Socket clntSock_send = new Socket("localhost", getNextDoor(doorList, "" + thisDoor));
            PrintStream ps = new PrintStream(clntSock_send.getOutputStream());
            ps.println("token");
            ps.println(doorList);
            clntSock_send.close();

        } catch (IOException ex)
        {
            Logger.getLogger(Cliente1.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Mehtod to require an entry into the Token Ring
    public void entryRequirement()
    {
        System.out.println("Fazendo requisição de entrada");
        ServerSocket servSock;
        Socket clntSock_recieve;
        Socket clntSock_send;
        String reply = "nok";
        do
        {
            try
            {
                clntSock_send = new Socket("localhost", 5000);
                PrintStream ps = new PrintStream(clntSock_send.getOutputStream());

                // Send the message "entryRequirement", along with the door list
                ps.println("entryRequirement");
                ps.println(doorList);
                ps.close();
                clntSock_send.close();
                Thread.sleep(50);

                // Open up a Socket to wait a Reply
                servSock = new ServerSocket(thisDoor);
                servSock.setSoTimeout(10000);
                clntSock_recieve = servSock.accept();
                Scanner res = new Scanner(clntSock_recieve.getInputStream());
                reply = res.nextLine();
                clntSock_recieve.close();
                servSock.close();

            } catch (ConnectException ex)
            {
            } catch (IOException ex)
            {
                Logger.getLogger(Cliente1.class
                        .getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Cliente1.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } while (reply.compareTo("ok") != 0);
    }

    // ---------------------------------------------------------------------------------------------------------------- //
    //                                    Buttons and Interface Java Configuration                                      //
    // ---------------------------------------------------------------------------------------------------------------- //
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("C1");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Quero Usar!");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Liberar!");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    // Trigger the token requirement
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {
        requestToken = true;
    }

    // Release the token, sending it to the next process in the ring
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
    {
        try
        {
            // Realease Token from this process
            int nextDoor = getNextDoor(doorList, "" + thisDoor);
            requestToken = false;
            jTextArea1.setEditable(false);
            System.out.println("Acesso Liberado");
            Socket clntSock_send;

            // Try to connect to the next process
            try
            {
                clntSock_send = new Socket("localhost", nextDoor);
            } catch (ConnectException ex)
            {
                System.out.println("Processo morto! Conectando com o processo seguinte");
                clntSock_send = new Socket("localhost", getNextDoor(listPreviousDoors, "" + nextDoor));
                doorList = removeDoorFromList(doorList, "" + nextDoor);
            }

            // Broadcast Token to the next process
            PrintStream ps = new PrintStream(clntSock_send.getOutputStream());
            ps.println("token");
            if (doorList.contains("" + thisDoor))
            {
                ps.println(doorList);
            } else
            {
                ps.println(doorList + "_" + thisDoor);
            }
            clntSock_send.close();

        } catch (IOException ex)
        {
            Logger.getLogger(Cliente1.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------- //
    //                  Functions to recover the door's number from process that belong to the tonken ring              //
    // ---------------------------------------------------------------------------------------------------------------- //
    /*
     * Method that returns the next door's number in the ring
     */
    private static int getNextDoor(String str1, String str2)
    {
        String[] strdiv = str1.split("_");
        if (strdiv.length != 0)
        {
            for (int i = 0; i < strdiv.length; i++)
            {
                if (strdiv[i].equalsIgnoreCase(str2))
                {
                    if (i == strdiv.length - 1)
                    {
                        return Integer.parseInt(strdiv[0]);
                    } else
                    {
                        return Integer.parseInt(strdiv[i + 1]);
                    }
                }
            }
        }
        return Integer.parseInt(str2);
    }

    /*
     * Method that returns the previous door's number in the ring
     */
    private static int getPreviousDoor(String str1, String str2)
    {
        String[] strdiv = str1.split("_");
        if (strdiv.length != 0)
        {
            for (int i = 0; i < strdiv.length; i++)
            {
                if (strdiv[i].equalsIgnoreCase(str2))
                {
                    if (i == 0)
                    {
                        return Integer.parseInt(strdiv[strdiv.length - 1]);
                    } else
                    {
                        return Integer.parseInt(strdiv[i - 1]);
                    }
                }
            }
        }
        return Integer.parseInt(str2);
    }

    /*
     * Method that removes a door from the ring, and returns the new list of doors
     */
    private static String removeDoorFromList(String str1, String str2)
    {
        if (str1.length() == str2.length() || str1.indexOf(str2) == 0)
        {
            String[] strdiv = str1.split("_");
            return strdiv[1];
        } else
        {
            return (str1.substring(0, str1.indexOf(str2) - 2) + str1.substring(str1.indexOf(str2) + str2.length(), str1.length()));
        }
    }

    // ---------------------------------------------------------------------------------------------------------------- //
    //                                                    MAIN                                                          //
    // ---------------------------------------------------------------------------------------------------------------- //
    public static void main(String args[]) throws IOException
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Cliente1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Cliente1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Cliente1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Cliente1.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Declaring the Read and Write Message Variables
        Scanner in; // Read
        PrintStream ps; // Write

        // Declaring an instance of this class
        final Cliente1 c = new Cliente1();
        // Setting the critic region to show This process's door number
        c.jTextArea1.setText("PORTA DESTE CLIENTE: [" + thisDoor + "].\n");

        // c.setPort(JOptionPane.showInputDialog("Qual número de Porta?"));
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                c.setVisible(true);
            }
        });

        try
        {
            // Open a Server Socket with this process's door
            ServerSocket servSock = new ServerSocket(thisDoor);

            // Infinity Loop, waiting an income message
            for (;;)
            {
                // Setting a Timeout limit to wait for the message
                servSock.setSoTimeout(TIMEOUT_LIMIT);
                Socket clntSock = null;
                try
                {
                    // Accept the incoming message
                    clntSock = servSock.accept();
                    /*
                     * Read the message sent, save the previous Door List in the proper Variable
                     * Also read the door list sent by the clntSock.
                     */
                    in = new Scanner(clntSock.getInputStream());
                    broadcastMessage = in.nextLine();
                    listPreviousDoors = doorList;
                    doorList = in.nextLine();

                    // Showing the message and the doorlist recieved
                    System.out.println("MENSAGEM: " + broadcastMessage);
                    System.out.println("PORTA(S): " + doorList);

                    /*
                     * Check message content. Can be:
                     * Token Transmission;
                     * An Entry Requirement;
                     * An Integrity Check;
                     */
                    if (broadcastMessage.compareTo("token") == 0)
                    {
                        // Check if this process have made the Token Requirement
                        if (c.requestToken)
                        {
                            /*
                             * Show a message in the Critic Area,
                             * warning that an requirement has been made by this process
                             * and that it has the Token under it's possession.
                             */
                            System.out.println("Acesso Requisitado");
                            c.jTextArea1.setText(c.jTextArea1.getText() + "\nTOKEN requisitado - TOKEN recebido!");
                            c.jTextArea1.setEditable(true);
                        } else
                        {
                            /*
                             * Otherwise, the token hasn't been required by this process
                             * Send the token to the next door, keeping the token's cicle
                             */
                            int nextDoor = getNextDoor(listPreviousDoors, "" + thisDoor);
                            System.out.println("Acesso não Requisitado... Enviando Token para porta: " + nextDoor);
                            Socket clntSock_send = null;
                            try
                            {
                                clntSock_send = new Socket("localhost", nextDoor);
                            } catch (ConnectException ex)
                            {
                                System.out.println("Falha no Processo... Conectando com o próximo");
                                clntSock_send = new Socket("localhost", getNextDoor(listPreviousDoors, "" + nextDoor));
                                doorList = removeDoorFromList(doorList, "" + nextDoor);
                            }
                            ps = new PrintStream(clntSock_send.getOutputStream());
                            ps.println("token");

                            /*
                             * Check if this process's door is in the Door List
                             * If it is, the whole list is sent
                             * Otherwise, send the list of doors plus this process's door
                             * so the Cicle can be complete
                             */
                            if (doorList.contains("" + thisDoor))
                            {
                                ps.println(doorList);
                            } else
                            {
                                ps.println(doorList + "_" + thisDoor);
                            }
                            ps.close();
                            clntSock_send.close();
                        }
                    } /*
                     * If some process has sent this process an Entry Requirement, and this process is
                     * a Coordenator, then I might accept it's entry in the ring
                     */ else if (broadcastMessage.compareTo("entryRequirement") == 0 && coordenator)
                    {
                        /*
                         * Open the Socket with Door that sent the Entry Requirement
                         * Then, this process send a message of confirmation and add
                         * the other process's door to the Door List
                         */
                        Socket clntSock_send = new Socket("localhost", Integer.parseInt(doorList));
                        ps = new PrintStream(clntSock_send.getOutputStream());
                        ps.println("ok");
                        doorList = listPreviousDoors + "_" + doorList;
                        ps.println(doorList);
                        System.out.println(doorList);
                    } /*
                     Recieve a check Integrity Message. If I'm still up, i reply if an simple "ok"
                     to the door that asked
                     */ else if (broadcastMessage.compareTo("CheckIntegrity") == 0)
                    {
                        Socket clntSock2 = new Socket("localhost", Integer.parseInt(doorList));
                        System.out.println("Recebendo mensagem CHECK INTEGRITY de: " + doorList);
                        ps = new PrintStream(clntSock2.getOutputStream());
                        ps.println("ok");
                        System.out.println("Respondendo com um 'ok'");
                        doorList = listPreviousDoors;
                    }
                    clntSock.close();
                } /*
                 * This catch is specific to the TimeOut exception. If this happens,
                 * then a Timeout has occured. This could mean that a process in the
                 * Ring has died. So, we trigger a method to check if all processes
                 * are up. If not, this method will treat that problem by an election
                 * algorithm
                 */ catch (java.io.InterruptedIOException e)
                {
                    System.err.println("Esgotou o tempo de espera de 10 segundos! Iniciando"
                            + " verificação do anel");
                    // Close server socket to avoid colision problems
                    servSock.close();

                    // Call the method responsable to check if all process of the ring are still running
                    checkRingIntegrity();

                    // Restart Socket Server
                    servSock = new ServerSocket(thisDoor);
                }
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
}
