package Frames;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
        removeQuizButton.setBounds(370,295,150,30);
        panel.add(removeQuizButton);

        addQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String answer = JOptionPane.showInputDialog("Enter Course Name");
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

        JButton editButton = new JButton("Edit");
        editButton.setBounds(870,295,150,30);
        panel.add(editButton);


    }

    public DefaultListModel getQuizzesListModel() {
        return quizzesListModel;
    }

}
