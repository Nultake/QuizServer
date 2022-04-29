package Frames;

import DataTypes.Answer;
import DataTypes.Question;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class QuizFrame extends JFrame {

    private ArrayList<Question>  questions;
    private JPanel panel;
    private DefaultListModel questionListModel = new DefaultListModel();
    private JList questionsList = new JList(questionListModel);

    private ArrayList<Answer> answers;
    private ArrayList<String> correctAnswers = new ArrayList<>();
    private String[] chosenAnswers;
    private JRadioButton rdbtnNewRadioButton;
    private JRadioButton rdbtnNewRadioButton_1;
    private JRadioButton rdbtnNewRadioButton_2;
    private JRadioButton rdbtnNewRadioButton_3;
    private JLabel lblNewLabel;
    private JFrame frame;


    public QuizFrame(String name,String courseName,ArrayList<Question> questions, String quizName, boolean shuffle, BufferedReader in, PrintWriter out){
        this.questions = questions;
        this.chosenAnswers = new String[questions.size()];
        frame = this;

        panel = new JPanel();
        panel.setLayout(null);
        setTitle("Quiz : " + quizName);
        setBounds(300,300,1200,550);
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
            Collections.shuffle(x.getAnswers());
            correctAnswers.add(x.getCorrectAnswer().getInfo());
        }
        questionsList.setSelectedIndex(0);

        lblNewLabel = new JLabel(questions.get(0).getInfo());
        lblNewLabel.setBounds(445, 96, 709, 41);
        panel.add(lblNewLabel);

        answers = questions.get(0).getAnswers();


        rdbtnNewRadioButton = new JRadioButton(answers.get(0).getInfo());
        rdbtnNewRadioButton.setBounds(445, 204, 500, 23);
        panel.add(rdbtnNewRadioButton);

        rdbtnNewRadioButton_1 = new JRadioButton(answers.get(1).getInfo());
        rdbtnNewRadioButton_1.setBounds(445, 271, 500, 23);
        panel.add(rdbtnNewRadioButton_1);

        rdbtnNewRadioButton_2 = new JRadioButton(answers.get(2).getInfo());
        rdbtnNewRadioButton_2.setBounds(445, 331, 500, 23);
        panel.add(rdbtnNewRadioButton_2);

        rdbtnNewRadioButton_3 = new JRadioButton(answers.get(3).getInfo());
        rdbtnNewRadioButton_3.setBounds(445, 396, 500, 23);
        panel.add(rdbtnNewRadioButton_3);
        ButtonGroup bg = new ButtonGroup();



        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(1000,204,150,30);
        panel.add(submitButton);


        JButton importButton = new JButton("Import");
        importButton.setBounds(1000,331,150,30);
        panel.add(importButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int score = calculateScore();
                out.println("submit:" + name + "-" + courseName + "-" + quizName + "-" + score + "/" + questions.size()*2);
                out.flush();
                JOptionPane.showMessageDialog(null,"Submission is done ! Your score is : " + score);
                dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
            }
        });
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int reslt = fileChooser.showSaveDialog(null);
                if (reslt == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line =br.readLine();
                        String[] fileAnswers = line.split(",");
                        int index = 0;
                        for (String x : fileAnswers){
                            ArrayList<Answer> answers = questions.get(index).getAnswers();
                            switch (x){
                                case "A":
                                    chosenAnswers[index] = answers.get(0).getInfo();
                                    break;
                                case "B":
                                    chosenAnswers[index] = answers.get(1).getInfo();
                                    break;
                                case "C":
                                    chosenAnswers[index] = answers.get(2).getInfo();
                                    break;
                                case "D":
                                    chosenAnswers[index] = answers.get(3).getInfo();
                                    break;
                            }
                            index++;
                        }
                        int score = calculateScore();
                        out.println("submit:" + name + "-" + courseName + "-" + quizName + "-" + score + "/" + questions.size()*2);
                        out.flush();
                        JOptionPane.showMessageDialog(null,"Submission is done ! Your score is : " + score);
                        dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });



        bg.add(rdbtnNewRadioButton);
        bg.add(rdbtnNewRadioButton_1);
        bg.add(rdbtnNewRadioButton_2);
        bg.add(rdbtnNewRadioButton_3);

        rdbtnNewRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton.isSelected()){
                    int index = questionsList.getSelectedIndex();
                    chosenAnswers[index] = rdbtnNewRadioButton.getText();
                }
            }
        });
        rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton_1.isSelected()){
                    int index = questionsList.getSelectedIndex();
                    chosenAnswers[index] = rdbtnNewRadioButton_1.getText();
                }
            }
        });
        rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton_2.isSelected()){
                    int index = questionsList.getSelectedIndex();
                    chosenAnswers[index] = rdbtnNewRadioButton_2.getText();
                }
            }
        });
        rdbtnNewRadioButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdbtnNewRadioButton_3.isSelected()){
                    int index = questionsList.getSelectedIndex();
                    chosenAnswers[index] = rdbtnNewRadioButton_3.getText();
                }
            }
        });
        setVisible(true);




        questionsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = questionsList.getSelectedIndex();
                if (index != -1){
                    Question question = questions.get(index);
                    answers = question.getAnswers();
                    rdbtnNewRadioButton.setText(answers.get(0).getInfo());
                    rdbtnNewRadioButton.setSelected(false);
                    rdbtnNewRadioButton_1.setText(answers.get(1).getInfo());
                    rdbtnNewRadioButton_1.setSelected(false);
                    rdbtnNewRadioButton_2.setText(answers.get(2).getInfo());
                    rdbtnNewRadioButton_2.setSelected(false);
                    rdbtnNewRadioButton_3.setText(answers.get(3).getInfo());
                    rdbtnNewRadioButton_3.setSelected(false);
                    lblNewLabel.setText(questions.get(index).getInfo());

                }
            }
        });

    }

    public int calculateScore(){
        int score = 0;
        int index = 0;
        for(String x: correctAnswers){
            if (chosenAnswers[index] != null){
                if (chosenAnswers[index].equals(x))
                    score +=2;
            }
            index++;
        }
        return score;
    }

}
