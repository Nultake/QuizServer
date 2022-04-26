package Frames;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EditFrame extends JFrame {

    private final DefaultListModel quizzesListModel = new DefaultListModel();
    private final JList quizzes = new JList(quizzesListModel);

    private final DefaultListModel questionsListModel = new DefaultListModel();
    private final JList questions = new JList(questionsListModel);

    public EditFrame(String courseName,BufferedReader in, PrintWriter out){

        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setTitle("Course : " + courseName);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);
        setVisible(true);

        JScrollPane quizzesScrollPane = new JScrollPane();
        quizzesScrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"Quizzes",TitledBorder.LEADING,TitledBorder.TOP,null));
        quizzesScrollPane.setBounds(150,50,200,400);
        quizzes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        quizzesScrollPane.setViewportView(quizzes);
        panel.add(quizzesScrollPane);

        JButton addQuizButton = new JButton("Add Quiz");
        addQuizButton.setBounds(370,205,150,30);
        panel.add(addQuizButton);

        JButton removeQuizButton = new JButton("Remove Quiz");
        removeQuizButton.setBounds(370,325,150,30);
        panel.add(removeQuizButton);

        JButton addQuizByFileButton = new JButton("Add Quiz By File");
        addQuizByFileButton.setBounds(370,265,150,30);
        panel.add(addQuizByFileButton);

        addQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = JOptionPane.showInputDialog("Enter Course Name");
                if (answer != null){
                    int confirmDialog = JOptionPane.showConfirmDialog(null, "Will questions be shown randomized ?", "Choose", JOptionPane.YES_NO_OPTION);

                    out.println("createquiz:"+ courseName+ "-"+ answer+"-"+confirmDialog);
                    out.flush();
                    String respond = null;
                    try {
                        respond = in.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (respond.equals("true"))
                        JOptionPane.showMessageDialog(null,"Quiz already exists","Error",JOptionPane.ERROR_MESSAGE);
                    else{
                        JOptionPane.showMessageDialog(null,"Quiz has been created!");
                        quizzesListModel.addElement(answer);
                    }
                }
            }
        });

        removeQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = quizzes.getSelectedIndex();
                if (index != -1){
                    int choice = JOptionPane.showConfirmDialog(null,"Are you sure deleting quiz","Verify",JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION){
                        out.println("deletequiz:" + courseName+"-" + quizzesListModel.get(index).toString());
                        out.flush();
                        JOptionPane.showMessageDialog(null,"Quiz has been deleted!");
                        quizzesListModel.removeElementAt(index);
                    }
                }else
                    JOptionPane.showMessageDialog(null,"You must select quiz","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        addQuizByFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int reslt = fileChooser.showSaveDialog(null);
                if (reslt == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    try {
                        Files.copy(file.toPath(), Paths.get("Courses\\"+ courseName+"\\" + file.getName()));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });



        JScrollPane questionsScrollPane = new JScrollPane();
        questionsScrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"Questions",TitledBorder.LEADING,TitledBorder.TOP,null));
        questionsScrollPane.setBounds(650,50,200,400);
        questions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionsScrollPane.setViewportView(questions);
        panel.add(questionsScrollPane);

        JButton addQuestionButton = new JButton("Add Question");
        addQuestionButton.setBounds(870,175,150,30);
        panel.add(addQuestionButton);

        JButton removeQuestionButton = new JButton("Remove Question");
        removeQuestionButton.setBounds(870,235,150,30);
        panel.add(removeQuestionButton);


        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quizIndex = quizzes.getSelectedIndex();
                if (quizIndex != -1){
                    String question = JOptionPane.showInputDialog("Enter Question");
                    String correctAnswer = null;
                    String wrongAnswer_1= null;
                    String wrongAnswer_2= null;
                    String wrongAnswer_3= null;
                    if (question != null) correctAnswer = JOptionPane.showInputDialog("Enter Correct Answer");
                    if (correctAnswer != null) wrongAnswer_1 = JOptionPane.showInputDialog("Enter Wrong Answer#1");
                    if (wrongAnswer_1 != null) wrongAnswer_2 = JOptionPane.showInputDialog("Enter Wrong Answer#2");
                    if (wrongAnswer_2 != null) wrongAnswer_3 = JOptionPane.showInputDialog("Enter Wrong Answer#3");
                    if (wrongAnswer_3 != null){
                        out.println("createquestion:"+courseName + "/" + quizzesListModel.get(quizIndex).toString() + ".txt-"+question+","+correctAnswer+","+wrongAnswer_1+","+wrongAnswer_2+","+wrongAnswer_3);
                        out.flush();
                        questionsListModel.addElement("Question");
                        JOptionPane.showMessageDialog(null,"Question has been created!");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"You must select a quiz to add question","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = questions.getSelectedIndex();
                if (index != -1){
                    int result = JOptionPane.showConfirmDialog(null,"are you sure deleting question","Verify",JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION){
                        out.println("deletequestion:"+ courseName+"/"+ quizzesListModel.getElementAt(index).toString()+"-"+index);
                        out.flush();
                        JOptionPane.showMessageDialog(null,"Question has been deleted!");
                        questionsListModel.removeElementAt(index);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"You must select a question to remove","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        quizzes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
             if(quizzes.getSelectedIndex()!= -1){
                 questionsListModel.clear();
                 out.println("getquestionnumber:Courses/"+ courseName+"/"+quizzesListModel.getElementAt(quizzes.getSelectedIndex()).toString() + ".txt");
                 out.flush();
                 try {
                     String respond = in.readLine();
                     int number = Integer.parseInt(respond);
                     for (int i = 0; i<number;i++)
                         questionsListModel.addElement("Question");

                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
             }
             }
        });

    }

    public DefaultListModel getQuizzesListModel() {
        return quizzesListModel;
    }

}
