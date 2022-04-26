package Frames;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public class TeacherFrame extends JFrame {
    private final DefaultListModel coursesListModel = new DefaultListModel();
    private final JList courses = new JList(coursesListModel);

    private final DefaultListModel submissionsListModel = new DefaultListModel();
    private final JList submissions = new JList(submissionsListModel);


    public DefaultListModel getCoursesListModel() {
        return coursesListModel;
    }

    public TeacherFrame(String name, BufferedReader in, PrintWriter out){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setTitle("Teacher : " + name);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);
        setVisible(true);

        JScrollPane coursesScrollPane = new JScrollPane();
        coursesScrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"Courses",TitledBorder.LEADING,TitledBorder.TOP,null));
        coursesScrollPane.setBounds(150,50,200,400);
        courses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesScrollPane.setViewportView(courses);
        panel.add(coursesScrollPane);

        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.setBounds(370,175,150,30);
        panel.add(addCourseButton);

        JButton removeCourseButton = new JButton("Remove Course");
        removeCourseButton.setBounds(370,235,150,30);
        panel.add(removeCourseButton);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(370,295,150,30);
        panel.add(editButton);

        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = JOptionPane.showInputDialog("Enter Course Name");
                if (answer != null){
                    out.println("createcourse:"+ answer);
                    out.flush();
                    String respond = null;
                    try {
                        respond = in.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (respond.equals("true"))
                        JOptionPane.showMessageDialog(null,"Course already exists","Error",JOptionPane.ERROR_MESSAGE);
                    else{
                        JOptionPane.showMessageDialog(null,"Course has been created!");
                        coursesListModel.addElement(answer);
                    }
                }
            }
        });
        removeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = courses.getSelectedIndex();
                if (index != -1){
                    int deletingCourse = JOptionPane.showConfirmDialog(null, "Are you sure deleting course");
                    if (deletingCourse == 0){
                        out.println("deletecourse:"+ coursesListModel.get(index).toString());
                        out.flush();
                        JOptionPane.showMessageDialog(null,"Course has been deleted!");
                        coursesListModel.removeElementAt(index);
                    }
                }else
                    JOptionPane.showMessageDialog(null,"You have to select course first","Error",JOptionPane.ERROR_MESSAGE);

            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = courses.getSelectedIndex();
                if (index != -1){
                    EditFrame frame = new EditFrame(coursesListModel.get(index).toString(),in,out);
                    out.println("getquizzes:" + coursesListModel.get(index).toString());
                    out.flush();
                    try {
                        for (String aFile : in.readLine().split(","))
                            frame.getQuizzesListModel().addElement(aFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else
                    JOptionPane.showMessageDialog(null,"You have to select course first","Error",JOptionPane.ERROR_MESSAGE);

            }
        });

        JScrollPane submissionsScrollPane = new JScrollPane();
        submissionsScrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"Submissions",TitledBorder.LEADING,TitledBorder.TOP,null));
        submissionsScrollPane.setBounds(650,50,200,400);
        submissions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        submissionsScrollPane.setViewportView(submissions);
        panel.add(submissionsScrollPane);

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(1500,205,150,30);
        panel.add(changePasswordButton);

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setBounds(1500,295,150,30);
        panel.add(deleteAccountButton);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = JOptionPane.showInputDialog("Enter your new password");
                if (newPassword != null){
                    out.println("changepassword:" + name+"-"+newPassword+"-true");
                    out.flush();
                    JOptionPane.showMessageDialog(null,"Password has been changed!");
                }
            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,"Are you sure deleting account!","Verify",JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION){
                    out.println("deleteaccount:" + name);
                    out.flush();
                    JOptionPane.showMessageDialog(null,"Account has been deleted");
                    System.exit(0);
                }
            }
        });


    }

}
