package Frames;

import DataTypes.Answer;
import DataTypes.Question;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class StudentFrame extends JFrame {
    private final DefaultListModel coursesListModel = new DefaultListModel();
    private final JList courses = new JList(coursesListModel);

    private final DefaultListModel quizzesListModel = new DefaultListModel();
    private final JList quizzes = new JList(quizzesListModel);

    public StudentFrame(String name, BufferedReader in, PrintWriter out){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setTitle("Student : " + name);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);
        setVisible(true);

        JScrollPane coursesScrollPane = new JScrollPane();
        coursesScrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"Available Courses",TitledBorder.LEADING,TitledBorder.TOP,null));
        coursesScrollPane.setBounds(150,50,200,400);
        courses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        coursesScrollPane.setViewportView(courses);
        panel.add(coursesScrollPane);


        JScrollPane quizzesScrollPane = new JScrollPane();
        quizzesScrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"Available Quizzes",TitledBorder.LEADING,TitledBorder.TOP,null));
        quizzesScrollPane.setBounds(450,50,200,400);
        quizzes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        quizzesScrollPane.setViewportView(quizzes);
        panel.add(quizzesScrollPane);

        JButton takeQuizButton = new JButton("Take Quiz");
        takeQuizButton.setBounds(670,235,150,30);
        panel.add(takeQuizButton);

        courses.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = courses.getSelectedIndex();
                if (index != -1){
                    quizzesListModel.clear();
                    out.println("getquizzes:" + coursesListModel.getElementAt(index));
                    out.flush();
                    try {
                        String respond = in.readLine();
                        String[] quizzes = respond.split(",");
                        for (String x  : quizzes)
                            quizzesListModel.addElement(x);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        takeQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = quizzes.getSelectedIndex();
                if (index != -1){
                    out.println("getquestions:"+coursesListModel.get(courses.getSelectedIndex())+"-"+ quizzesListModel.get(index));
                    out.flush();
                    try {
                        String respond = in.readLine();
                        String[] questions = respond.split("-");
                        boolean shuffle = questions[0].equals("0");
                        ArrayList<Question> questionArrayList = new ArrayList<>();
                        for(int i = 1; i <questions.length; i++){
                            Question question = new Question();
                            ArrayList<Answer> answers = new ArrayList<>();
                            answers.add(new Answer(questions[i].split(",")[1],true));
                            answers.add(new Answer(questions[i].split(",")[2],false));
                            answers.add(new Answer(questions[i].split(",")[3],false));
                            answers.add(new Answer(questions[i].split(",")[4],false));
                            question.setInfo(questions[i].split(",")[0]);
                            question.setAnswers(answers);
                            questionArrayList.add(question);
                        }
                        new QuizFrame(questionArrayList,quizzesListModel.get(index).toString(),shuffle);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    //TODO : Create quiz
                }else
                    JOptionPane.showMessageDialog(null,"You must select quiz first","Error",JOptionPane.ERROR_MESSAGE);

            }
        });

        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(1550,205,150,30);
        panel.add(changePasswordButton);

        JButton deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setBounds(1550,295,150,30);
        panel.add(deleteAccountButton);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = JOptionPane.showInputDialog("Enter your new password");
                if (newPassword != null){
                    out.println("changepassword:" + name+"-"+newPassword+"-false");
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

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(1200,205,150,30);
        panel.add(refreshButton);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quizzesListModel.clear();
                coursesListModel.clear();
                out.println("getcourses");
                out.flush();
                try {
                    for (String aFile : in.readLine().split(","))
                        coursesListModel.addElement(aFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public DefaultListModel getCoursesListModel() {
        return coursesListModel;
    }
}
