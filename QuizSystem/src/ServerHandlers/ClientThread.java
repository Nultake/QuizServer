package ServerHandlers;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class ClientThread extends Thread{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    static  File accountsFile = new File("Accounts.txt");


    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);

    }

    @Override
    public void run() {
        while(true) {
            String input = null;
            try {
                input = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (input.startsWith("login")){
                String[] account = input.split(":")[1].split("-");
                boolean[] results = checkAccount(account[0],account[1]);
                out.println(""+results[0] + "," + results[1]);
                out.flush();
            }
            if(input.startsWith("register")){
                String[] account = input.split(":")[1].split("-");
                boolean isUnique = isUsernameUnique(account[0]);
                out.println(""+isUnique);
                out.flush();
                if (isUnique){
                    BufferedWriter bw = null;
                    try{
                        bw = new BufferedWriter(new FileWriter(accountsFile,true));
                        bw.append(input.split(":")[1]);
                        bw.newLine();
                        bw.flush();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
            if (input.startsWith("createcourse")){
                String request = input.split(":")[1];
                boolean courseExists = checkCourseExists(request);
                if (!courseExists){
                    File file = new File("Courses/" + request);
                    file.mkdirs();
                }
                out.println(""+courseExists);
                out.flush();
            }
            if (input.startsWith("deletecourse")){
                String request = input.split(":")[1];
                File file = new File("Courses/"+ request);
                deleteDirectory(file);
            }
            if (input.startsWith("getcourses")){
                String respond = "";
                for (File file : new File("Courses").listFiles())
                    respond += file.getPath().replace("Courses\\", "") + ",";
                StringBuffer sb = new StringBuffer(respond);
                if (sb.length() != 0) sb.deleteCharAt(sb.length()-1);
                out.println(sb);
                out.flush();
            }
            if (input.startsWith("createquiz")){
                String request = input.split(":")[1];
                boolean quizExists = checkQuizExists(request.split("-")[0],request.split("-")[1]);
                if (!quizExists){
                    File file = new File("Courses/" + request.split("-")[0]+"/"+request.split("-")[1] + ".txt");
                    BufferedWriter bw = null;
                    try {
                        file.createNewFile();
                        bw = new BufferedWriter(new FileWriter(file,true));
                        bw.append(request.split("-")[2]);
                        bw.newLine();
                        bw.flush();
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                out.println(""+ quizExists);
                out.flush();
            }
            if (input.startsWith("deletequiz")){
                String request = input.split(":")[1];
                File file = new File("Courses/"+request.split("-")[0] + "/" + request.split("-")[1] + ".txt");
                System.out.println(file.delete());
            }
            if (input.startsWith("getquizzes")){
                String request = input.split(":")[1];
                String respond = "";
                for (File file : new File("Courses/" + request).listFiles())
                    respond += file.getPath().replace("Courses\\" + request +"\\", "").replace(".txt","") + ",";
                StringBuffer sb = new StringBuffer(respond);
                if (sb.length() != 0)sb.deleteCharAt(sb.length()-1);
                out.println(sb);
                out.flush();
            }
            if(input.startsWith("createquestion")){
                String request = input.split(":")[1];
                String filePath = "Courses/" + request.split("-")[0];
                String question = request.split("-")[1];
                File file = new File(filePath);
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
                    bw.append(question);
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }if (input.startsWith("getquestionnumber")){
                String request = input.split(":")[1];
                Path path = Paths.get(request);
                try {
                    out.println(Files.lines(path).count()-1);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(input.startsWith("getquestions")){
                String request = input.split(":")[1];
                String filePath = "Courses/" + request.split("-")[0] +"/"+request.split("-")[1] + ".txt";
                File file = new File(filePath);
                String respond = null;
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line = null;
                    while((line = br.readLine()) != null){
                        respond+= line +"-";
                    }
                    StringBuffer sb = new StringBuffer(respond);
                    sb.deleteCharAt(sb.length()-1);
                    out.println(sb);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(input.startsWith("deleteaccount")){
                String request = input.split(":")[1];
                try {
                    removeLine(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input.startsWith("changepassword")){
                String request = input.split(":")[1];
                String username =request.split("-")[0];
                String password =request.split("-")[1];
                String teacher = request.split("-")[2];
                try {
                    updateLine(username,password,teacher);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(input.startsWith("deletequestion")){
                String request = input.split(":")[1];
                String path = "Courses/" + request.split("-")[0] +".txt";
                int index = Integer.parseInt(request.split("-")[1]);
                index++;
                try {
                    removeLineQuestion(path,index);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void removeLineQuestion(String path,int index) throws IOException
    {
        File file = new File(path);
        List<String> out = Files.lines(file.toPath()).collect(Collectors.toList());
        out.remove(index);
        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    public void removeLine(String lineContent) throws IOException
    {
        File file = new File("Accounts.txt");
        List<String> out = Files.lines(file.toPath())
                .filter(line -> !line.startsWith(lineContent+"-"))
                .collect(Collectors.toList());
        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    public void updateLine(String lineContent,String newPassword,String teacher) throws IOException
    {
        File file = new File("Accounts.txt");
        List<String> out = Files.lines(file.toPath())
                .filter(line -> !line.startsWith(lineContent+"-"))
                .collect(Collectors.toList());
        out.add(lineContent+"-"+newPassword+"-"+teacher);
        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
    private boolean checkQuizExists(String course,String name){
        File file = new File("Courses/"+course+"/"+name);
        if (file.exists())
            return true;
        return false;
    }
    private boolean checkCourseExists(String course){
        File file = new File("Courses/"+course);
        if (file.exists())
            return true;
        return false;
    }
    private boolean isUsernameUnique(String username){
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(accountsFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String next;
        try {
            while ((next = br.readLine()) != null) {
                String account[] = next.split("-");
                if (username.equals(account[0]))
                    return false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }
    private boolean[] checkAccount(String username,String password){
        boolean found = false;
        boolean teacher = false;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(accountsFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String next;
        try {
            while ((next = br.readLine()) != null) {
                String account[] = next.split("-");
                if (username.equals(account[0]) && password.equals(account[1])){
                    found = true;
                    if (account[2].equals("true"))
                        teacher = true;
                    break;
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return new boolean[]{found, teacher};
    }
}
