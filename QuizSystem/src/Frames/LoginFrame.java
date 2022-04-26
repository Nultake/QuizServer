package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginFrame extends JFrame {


    private JPanel panel;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private BufferedReader in;
    private PrintWriter out;

    public LoginFrame(BufferedReader in,PrintWriter out){
        this.in = in;
        this.out = out;
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750,600);
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, (int) (size.getHeight()/2 - getHeight()/2));
        panel = new JPanel();
        add(panel);
        panel.setLayout(null);


        initComponents();
        setVisible(true);

    }
    private void initComponents(){
        final JLabel USERNAME_LABEL = new JLabel("Username : ");
        final JLabel PASSWORD_LABEL = new JLabel("Password : ");
        final JLabel SPLITTER_LABEL = new JLabel("----------------------OR----------------------");
        final JCheckBox teacherCheckBox = new JCheckBox("I am teacher (Only for register)");

        USERNAME_LABEL.setBounds(179,132,72,14);

        PASSWORD_LABEL.setBounds(179,194,72,14);

        SPLITTER_LABEL.setBounds(280,347,200,14);

        panel.add(USERNAME_LABEL);
        panel.add(PASSWORD_LABEL);
        panel.add(SPLITTER_LABEL);

        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();

        usernameTextField.setBounds(295,129,154,20);

        passwordTextField.setBounds(298,191,154,20);

        teacherCheckBox.setBounds(280,260,200,14);


        panel.add(usernameTextField);
        panel.add(passwordTextField);
        panel.add(teacherCheckBox);


        JButton loginButton = new JButton("Login");
        loginButton.setBounds(329,290,89,23);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(329,392,89,23);
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usernameTextField.getText().equals("") && passwordTextField.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Username or Password cannot be empty","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                out.println("login:"+ usernameTextField.getText()+"-"+passwordTextField.getText());
                out.flush();
                try {
                    String respond = in.readLine();
                    if (respond.startsWith("true")){
                        if (respond.split(",")[1].equals("true")){
                            TeacherFrame frame = new TeacherFrame(usernameTextField.getText(),in,out);
                            out.println("getcourses");
                            out.flush();
                            for (String aFile : in.readLine().split(","))
                                frame.getCoursesListModel().addElement(aFile);

                        }
                        else{
                            StudentFrame frame = new StudentFrame(usernameTextField.getText(),in,out);
                            out.println("getcourses");
                            out.flush();
                            for (String aFile : in.readLine().split(","))
                                frame.getCoursesListModel().addElement(aFile);

                        }
                        setVisible(false);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"Username and password does not match","Error",JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usernameTextField.getText().equals("") && passwordTextField.getText().equals(""))
                    JOptionPane.showMessageDialog(null,"Username or Password cannot be empty");
                out.println("register:" + usernameTextField.getText()+"-"+passwordTextField.getText()+"-"+teacherCheckBox.isSelected());
                out.flush();
                try{
                    if(in.readLine().equals("true"))
                        JOptionPane.showMessageDialog(null,"Registered successfully");
                    else
                        JOptionPane.showMessageDialog(null,"Username already exists","Error",JOptionPane.ERROR_MESSAGE);
                }catch (IOException exception){
                    exception.printStackTrace();
                }

            }
        });
    }

}
