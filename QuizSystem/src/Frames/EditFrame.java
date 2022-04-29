package Frames;

import DataTypes.Answer;
import DataTypes.Question;
import DataTypes.Quiz;

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
import java.util.ArrayList;

public class EditFrame extends JFrame {

    private final DefaultListModel quizzesListModel = new DefaultListModel();
    private final JList quizzes = new JList(quizzesListModel);

    private final DefaultListModel questionsListModel = new DefaultListModel();
    private final JList questions = new JList(questionsListModel);

    private ArrayList<Question> questionsArrayList;

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
                        System.out.println("deletequiz:" + courseName+"-" + quizzesListModel.getElementAt(index).toString());
                        out.println("deletequiz:" + courseName+"-" + quizzesListModel.getElementAt(index).toString());
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
                    quizzesListModel.addElement(file.getName().replace(".txt",""));
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
                        System.out.println(index);
                        out.println("deletequestion:"+ courseName+"/"+ quizzesListModel.get(quizzes.getSelectedIndex()).toString()+"-"+index);
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
                 out.println("getquestions:" + courseName + "-" + quizzesListModel.get(quizzes.getSelectedIndex()));
                 out.flush();
                 try {
                     String respond = in.readLine();
                     String[] questions = respond.split("-");
                     ArrayList<Question> questionArrayList = new ArrayList<>();
                     for (int i = 1; i < questions.length; i++) {
                         Question question = new Question();
                         ArrayList<Answer> answers = new ArrayList<>();
                         answers.add(new Answer(questions[i].split(",")[1], true));
                         answers.add(new Answer(questions[i].split(",")[2], false));
                         answers.add(new Answer(questions[i].split(",")[3], false));
                         answers.add(new Answer(questions[i].split(",")[4], false));
                         question.setInfo(questions[i].split(",")[0]);
                         question.setAnswers(answers);
                         questionArrayList.add(question);
                     }
                     setQuestionsArrayList(questionArrayList);
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
             }
             }
        });
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(1200,205,150,30);
        panel.add(refreshButton);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionsListModel.clear();
                quizzesListModel.clear();
                out.println("getquizzes:" + courseName);
                out.flush();
                try {
                    for (String aFile : in.readLine().split(","))
                        quizzesListModel.addElement(aFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JLabel questionLabel = new JLabel();
        questionLabel.setBounds(150,550,600,30);
        panel.add(questionLabel);

        JLabel answerLabel = new JLabel();
        answerLabel.setBounds(150,610,600,30);
        panel.add(answerLabel);

        JLabel answerLabel_1 = new JLabel();
        answerLabel_1.setBounds(150,670,600,30);
        panel.add(answerLabel_1);

        JLabel answerLabel_2 = new JLabel();
        answerLabel_2.setBounds(150,730,600,30);
        panel.add(answerLabel_2);

        JLabel answerLabel_3 = new JLabel();
        answerLabel_3.setBounds(150,790,600,30);
        panel.add(answerLabel_3);

        questions.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int index = questions.getSelectedIndex();
                if (index != -1) {
                    Question a = getQuestionsArrayList().get(index);
                    questionLabel.setText(a.getInfo());
                    answerLabel.setText(a.getAnswers().get(0).getInfo());
                    answerLabel_1.setText(a.getAnswers().get(1).getInfo());
                    answerLabel_2.setText(a.getAnswers().get(2).getInfo());
                    answerLabel_3.setText(a.getAnswers().get(3).getInfo());
                }
            }
        });

    }

    public ArrayList<Question> getQuestionsArrayList() {
        return questionsArrayList;
    }

    public void setQuestionsArrayList(ArrayList<Question> questionsArrayList) {
        this.questionsArrayList = questionsArrayList;
    }

    public DefaultListModel getQuizzesListModel() {
        return quizzesListModel;
    }

}
