package Frames;

import DataTypes.Answer;
import DataTypes.Question;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class QuizFrame extends JFrame {

    private ArrayList<Question>  questions;
    private JPanel panel;

    public QuizFrame(ArrayList<Question> questions,String quizName,boolean shuffle){
        this.questions = questions;

        panel = new JPanel();
        panel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setTitle("Quiz : " + quizName);
        setBounds(300,300,1000,550);

        if (shuffle)
            Collections.shuffle(questions);
        for(Question x : questions){
            displayQuestion(x,questions.indexOf(x));
        }
        add(scrollPane,BorderLayout.CENTER);
        setVisible(true);

    }

    public void displayQuestion(Question question,int multiplier){

        JLabel lblNewLabel = new JLabel(question.getInfo());
        lblNewLabel.setBounds(145, 96+(multiplier*550), 709, 41);
        panel.add(lblNewLabel);

        ArrayList<Answer> answers = question.getAnswers();
        Collections.shuffle(answers);

        JRadioButton rdbtnNewRadioButton = new JRadioButton(answers.get(0).getInfo());
        rdbtnNewRadioButton.setBounds(145, 204+(multiplier*550), 652, 23);
        panel.add(rdbtnNewRadioButton);

        JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(answers.get(1).getInfo());
        rdbtnNewRadioButton_1.setBounds(145, 271+(multiplier*550), 652, 23);
        panel.add(rdbtnNewRadioButton_1);

        JRadioButton rdbtnNewRadioButton_2 = new JRadioButton(answers.get(2).getInfo());
        rdbtnNewRadioButton_2.setBounds(145, 331+(multiplier*550), 652, 23);
        panel.add(rdbtnNewRadioButton_2);

        JRadioButton rdbtnNewRadioButton_3 = new JRadioButton(answers.get(3).getInfo());
        rdbtnNewRadioButton_3.setBounds(145, 396+(multiplier*550), 652, 23);
        panel.add(rdbtnNewRadioButton_3);
        ButtonGroup bg = new ButtonGroup();

        bg.add(rdbtnNewRadioButton);
        bg.add(rdbtnNewRadioButton_1);
        bg.add(rdbtnNewRadioButton_2);
        bg.add(rdbtnNewRadioButton_3);
    }
}
