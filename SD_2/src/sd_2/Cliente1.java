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
    //                                             Declaração de Variáveis                                              //
    // ---------------------------------------------------------------------------------------------------------------- //
    // Variável para sinalizar quem é o coordenador.
    static boolean coordenador = true;

    // Variável para sinalização do tipo de mensagem enviada
    static String mensagemTransmitida;

    // Variável para informação das portas dos Sockets do TokenRing
    static int portaDesteCliente = 5000;
    static String listaDePortas = "" + portaDesteCliente;
    static String portasAnterioresAEste = listaDePortas;

    // Variável para sinalizar quem fez a requisição do Token
    boolean requestToken = false;

    // Método que inicializa os componentes do Cliente
    public Cliente1()
    {
        initComponents();
    }

    // Função para inicialização do Token, enviando-o para os próximos da lista
    public void startToken()
    {
        try
        {
            System.out.println("Iniciando Token");
            System.out.println(getNextPorta(listaDePortas, "" + portaDesteCliente));
            Socket clntSock2 = new Socket("localhost", getNextPorta(listaDePortas, "" + portaDesteCliente));
            PrintStream ps = new PrintStream(clntSock2.getOutputStream());
            ps.println("token");
            ps.println(listaDePortas);
            clntSock2.close();
        } catch (IOException ex)
        {
            Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Médoto para realizar uma requisição de entrada no TokenRing
    public void requisicaoDeEntrada()
    {
        System.out.println("Fazendo requisição de entrada");
        ServerSocket servSock;
        Socket clntSock;
        Socket clntSock2;
        String ret = "nok";
        do
        {
            try
            {
                clntSock2 = new Socket("localhost", 5000);
                PrintStream ps = new PrintStream(clntSock2.getOutputStream());

                // Envia a mensagem "requisicaoEntrada", juntamente com as portas que estão registradas
                ps.println("requisicaoEntrada");
                ps.println(listaDePortas);
                ps.close();
                clntSock2.close();
                Thread.sleep(50);

                // Abre um Socket para aguardar resposta
                servSock = new ServerSocket(portaDesteCliente);
                clntSock = servSock.accept();
                Scanner res = new Scanner(clntSock.getInputStream());
                ret = res.nextLine();
                clntSock.close();
                servSock.close();
            } catch (ConnectException ex)
            {
            } catch (IOException ex)
            {
                Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (ret.compareTo("ok") != 0);
    }

    // ---------------------------------------------------------------------------------------------------------------- //
    //                                   Configuração dos Botões e Interface Java                                       //
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

    // Aciona a requisição pelo Token
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {
        requestToken = true;
    }

    // Libera o Token, enviando-o para o próximo elemento do TokenRing
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
    {
        try
        {
            // Libera o Token deste elemento
            int proxima = getNextPorta(listaDePortas, "" + portaDesteCliente);
            requestToken = false;
            jTextArea1.setEditable(false);
            System.out.println("Acesso Liberado");
            Socket clntSock2;

            // Busca conectar-se ao próximo elemento
            try
            {
                System.out.println("Proxima Porta é: " + proxima);
                clntSock2 = new Socket("localhost", proxima);
            } catch (ConnectException ex)
            {
                System.out.println("Processo morto! Conectando com o processo seguinte.");
                clntSock2 = new Socket("localhost", getNextPorta(portasAnterioresAEste, "" + proxima));
                listaDePortas = removePortaDaLista(listaDePortas, "" + proxima);
            }

            // Transmite o Token para o próximo elemento
            PrintStream ps = new PrintStream(clntSock2.getOutputStream());
            ps.println("token");
            if (listaDePortas.contains("" + portaDesteCliente))
            {
                ps.println(listaDePortas);
            } else
            {
                ps.println(listaDePortas + "_" + portaDesteCliente);
            }
            clntSock2.close();
        } catch (IOException ex)
        {
            Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------- //
    //                           Funções para recuperar as portas dos elementos do Token Ring                           //
    // ---------------------------------------------------------------------------------------------------------------- //
    private static int getNextPorta(String str1, String str2)
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

    private static int getPreviousPorta(String str1, String str2)
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

    private static String removePortaDaLista(String str1, String str2)
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
            java.util.logging.Logger.getLogger(Cliente1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Cliente1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Cliente1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Cliente1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Declaração das variáveis de leitura e envio de mensagens
        Scanner in;
        PrintStream ps;

        // Declaração do Elemento Cliente 1
        final Cliente1 c = new Cliente1();
        c.jTextArea1.setText("PORTA DESTE CLIENTE: [" + portaDesteCliente + "].\n");

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
            // Abre um ServerSocket com a porta DESTE elemento
            ServerSocket servSock = new ServerSocket(portaDesteCliente);

            // Loop infinito, aguardando a recepção de uma mensagem
            for (;;)
            {
                // Aceita a mensagem e lê o que foi enviado nela
                Socket clntSock = servSock.accept();
                in = new Scanner(clntSock.getInputStream());
                mensagemTransmitida = in.nextLine();

                portasAnterioresAEste = listaDePortas;

                listaDePortas = in.nextLine();

                System.out.println("MENSAGEM: " + mensagemTransmitida);
                System.out.println("PORTA(S): " + listaDePortas);

                /*
                 Verifica o conteúdo da mensagem: É a transmissão do Token, 
                 ou uma requisição de um elemento para juntar-se ao TokenRing
                 */
                if (mensagemTransmitida.compareTo("token") == 0)
                {
                    // O elemento fez a requisição do Token
                    if (c.requestToken)
                    {
                        /*
                         Emite uma mensagem avisando que a requisição foi feita por este elemento
                         e, este, está com o Token.
                         */
                        System.out.println("Acesso Requisitado");
                        c.jTextArea1.setText(c.jTextArea1.getText() + "\nTOKEN requisitado - TOKEN recebido!");
                        c.jTextArea1.setEditable(true);
                    } else
                    {
                        // Caso contrário, o acesso não foi requisitado por este elemento e o envio do Token continua.
                        int proximaPorta = getNextPorta(portasAnterioresAEste, "" + portaDesteCliente);
                        System.out.println("Acesso não Requisitado... Enviando Token para porta: " + proximaPorta);
                        Socket clntSock2 = null;
                        try
                        {
                            clntSock2 = new Socket("localhost", proximaPorta);
                        } catch (ConnectException ex)
                        {
                            System.out.println("Falha no Processo... Conectando com o próximo");
                            clntSock2 = new Socket("localhost", getNextPorta(portasAnterioresAEste, "" + proximaPorta));
                            listaDePortas = removePortaDaLista(listaDePortas, "" + proximaPorta);
                        }
                        ps = new PrintStream(clntSock2.getOutputStream());
                        ps.println("token");

                        /*
                         Verifica se na String há a porta deste elemento.
                         Se sim, envia a string inteira
                         Se não, envia a string de portas somada à porta deste elemento,
                         a fim de fechar o ciclo novamente.
                         */
                        if (listaDePortas.contains("" + portaDesteCliente))
                        {
                            ps.println(listaDePortas);
                        } else
                        {
                            ps.println(listaDePortas + "_" + portaDesteCliente);
                        }
                        ps.close();
                        clntSock2.close();
                    }
                } // Caso a mensagem seja "Olá" e o elemento seja o coordenador
                else if (mensagemTransmitida.compareTo("requisicaoEntrada") == 0 && coordenador)
                {
                    /*
                     Abre o socket para a porta que mandou mensagem desejando
                     se unir ao Token Ring. Este, portanto, envia uma mensagem
                     de confirmação e adiciona a porta enviada pelo requisitante
                     à lista de portas.
                     */
                    Socket clntSock2 = new Socket("localhost", Integer.parseInt(listaDePortas));
                    ps = new PrintStream(clntSock2.getOutputStream());
                    ps.println("ok");
                    listaDePortas = portasAnterioresAEste + "_" + listaDePortas;
                    ps.println(listaDePortas);
                    System.out.println(listaDePortas);
                }
                clntSock.close();
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
