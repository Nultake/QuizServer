package Frames;

import DataTypes.Answer;
import DataTypes.Question;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class QuizFrame extends JFrame {

    private ArrayList<Question>  questions;
    private JPanel panel;
    private DefaultListModel questionListModel = new DefaultListModel();
    private JList questionsList = new JList(questionListModel);

    private ArrayList<Answer> answers;
    private JRadioButton rdbtnNewRadioButton;
    private JRadioButton rdbtnNewRadioButton_1;
    private JRadioButton rdbtnNewRadioButton_2;
    private JRadioButton rdbtnNewRadioButton_3;
    private JLabel lblNewLabel;

    private int score = 0;
    private int correctButton = 0;
    private boolean correct = false;

    public QuizFrame(ArrayList<Question> questions,String quizName,boolean shuffle){
        this.questions = questions;

        panel = new JPanel();
        panel.setLayout(null);
        setTitle("Quiz : " + quizName);
        setBounds(300,300,1000,550);
        setContentPane(panel);



        JScrollPane questionsScrollPane = new JScrollPane();
        questionsScrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),"Courses",TitledBorder.LEADING,TitledBorder.TOP,null));
        questionsScrollPane.setBounds(150,50,200,400);
        questionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        questionsScrollPane.setViewportView(questionsList);
        panel.add(questionsScrollPane);
        questionsList.setSelectedIndex(0);

        if (shuffle)
            Collections.shuffle(questions);
        for(Question x : questions){
            questionListModel.addElement("Question");
        }

        lblNewLabel = new JLabel(questions.get(0).getInfo());
        lblNewLabel.setBounds(445, 96, 709, 41);
        panel.add(lblNewLabel);

        answers = questions.get(0).getAnswers();
        Collections.shuffle(answers);

        rdbtnNewRadioButton = new JRadioButton(answers.get(0).getInfo());
        rdbtnNewRadioButton.setBounds(445, 204, 652, 23);
        panel.add(rdbtnNewRadioButton);

        rdbtnNewRadioButton_1 = new JRadioButton(answers.get(1).getInfo());
        rdbtnNewRadioButton_1.setBounds(445, 271, 652, 23);
        panel.add(rdbtnNewRadioButton_1);

        rdbtnNewRadioButton_2 = new JRadioButton(answers.get(2).getInfo());
        rdbtnNewRadioButton_2.setBounds(445, 331, 652, 23);
        panel.add(rdbtnNewRadioButton_2);

        rdbtnNewRadioButton_3 = new JRadioButton(answers.get(3).getInfo());
        rdbtnNewRadioButton_3.setBounds(445, 396, 652, 23);
        panel.add(rdbtnNewRadioButton_3);
        ButtonGroup bg = new ButtonGroup();

        bg.add(rdbtnNewRadioButton);
        bg.add(rdbtnNewRadioButton_1);
        bg.add(rdbtnNewRadioButton_2);
        bg.add(rdbtnNewRadioButton_3);
        setVisible(true);

        rdbtnNewRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton.isSelected()){
                    if (correct){
                        score -=2;
                        correct = false;
                    }

                    if (correctButton == 0){
                        score +=2;
                        correct = true;
                    }

                }
            }
        });
        rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton_1.isSelected()){
                    if (correct){
                        score -=2;
                        correct = false;
                    }

                    if (correctButton == 1){
                        score +=2;
                        correct = true;
                    }

                }
            }
        });
        rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton_2.isSelected()){
                    if (correct){
                        score -=2;
                        correct = false;
                    }

                    if (correctButton == 2){
                        score +=2;
                        correct = true;
                    }

                }
            }
        });
        rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton_3.isSelected()){
                    if (correct){
                        score -=2;
                        correct = false;
                    }

                    if (correctButton == 3){
                        score +=2;
                        correct = true;
                    }

                }
            }
        });


        questionsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = questionsList.getSelectedIndex();
                if (index != -1){
                    Question question = questions.get(index);
                    answers = question.getAnswers();
                    Collections.shuffle(answers);
                    rdbtnNewRadioButton.setText(answers.get(0).getInfo());
                    rdbtnNewRadioButton_1.setText(answers.get(1).getInfo());
                    rdbtnNewRadioButton_2.setText(answers.get(2).getInfo());
                    rdbtnNewRadioButton_3.setText(answers.get(3).getInfo());
                    lblNewLabel.setText(questions.get(index).getInfo());

                    Answer correctAnswer = question.getCorrectAnswer();
                    correctButton = answers.indexOf(correctAnswer);
                }
            }
        });

    }

}
