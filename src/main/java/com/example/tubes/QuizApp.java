//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.tubes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class QuizApp extends Application {
    Scene scene1;
    Scene scene2;
    Scene scene3;
    Scene scene4;
    Scene scene5;
    private TextField addQuestion;
    private TextField addAnswer;
    private TextField addPoint;
    private TextField playerName;
    private int currentQuestionIndex = 0;
    private int playerScore = 0;
    private Label playerNameLabel;
    private Label currentQuestionLabel;
    private Stage primaryStage;
    private TextField answerField;
    private List<Quiz> userAnswers = new ArrayList();
    private TableView<Quiz> table = new TableView();
    private ObservableList<Quiz> data = FXCollections.observableArrayList();
    private TableView<Quiz> summaryTable = new TableView();
    private ObservableList<Quiz> summaryData = FXCollections.observableArrayList();
    private File dataFile = new File("quiz_data.txt");
    private File scoreFile = new File("score.txt");

    public QuizApp() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.readDataFromFile();
        GridPane gridPane = new GridPane();
        primaryStage.setTitle("Aplikasi Kuis");
        Label label = new Label("Form Kuis");
        label.setFont(new Font("Arial", 30.0));
        this.addQuestion = new TextField();
        this.addQuestion.setPromptText("(max 100 karakter)");
        this.addQuestion.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 100) {
                this.addQuestion.setText(oldValue);
                this.showAlert("Peringatan", "Pertanyaan tidak boleh lebih dari 100 karakter.");
            }

        });
        this.addAnswer = new TextField();
        this.addAnswer.setPromptText("(max 50 karakter)");
        this.addAnswer.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                this.addAnswer.setText(oldValue);
                this.showAlert("Peringatan", "Jawaban tidak boleh lebih dari 50 karakter.");
            }

        });
        this.addPoint = new TextField();
        this.addPoint.setPromptText("(max 2 digit)");
        this.addPoint.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 2) {
                this.addPoint.setText(oldValue);
                this.showAlert("Peringatan", "Point tidak boleh lebih dari 2 digit.");
            } else if (!newValue.matches("\\d*")) {
                this.addPoint.setText(newValue.replaceAll("[^\\d]", ""));
                this.showAlert("Peringatan", "Point harus berupa angka.");
            } else if (newValue.equals("0")) {
                this.addPoint.setText(oldValue);
                this.showAlert("Peringatan", "Point tidak boleh 0.");
            }

        });
        Button addButton = new Button("Tambah Kuis");
        addButton.setOnAction((e) -> {
            if (this.isInputValid(this.addQuestion, this.addAnswer, this.addPoint)) {
                this.data.add(new Quiz(this.addQuestion.getText(), this.addAnswer.getText(), this.addPoint.getText(), ""));
                this.saveDataToFile();
                this.addQuestion.clear();
                this.addAnswer.clear();
                this.addPoint.clear();
                primaryStage.setScene(this.scene2);
            } else {
                this.showAlert("Peringatan", "Semua kolom harus diisi!");
            }

        });
        Button importButton = new Button("Import Kuis");
        importButton.setOnAction((e) -> {
            this.importQuizData();
        });
        gridPane.setVgap(10.0);
        gridPane.setHgap(10.0);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(label, 0, 0, 2, 1);
        gridPane.add(new Label("Pertanyaan:"), 0, 1);
        gridPane.add(this.addQuestion, 1, 1);
        gridPane.add(new Label("Jawaban:"), 0, 2);
        gridPane.add(this.addAnswer, 1, 2);
        gridPane.add(new Label("Point:"), 0, 3);
        gridPane.add(this.addPoint, 1, 3);
        gridPane.add(addButton, 1, 4);
        this.scene1 = new Scene(gridPane, 350.0, 350.0);
        Label label2 = new Label("Daftar Kuis");
        label2.setFont(new Font("Arial", 30.0));
        TableColumn<Quiz, String> questionCol = this.createEditableColumn("Pertanyaan", "question");
        questionCol.setMinWidth(200.0);
        TableColumn<Quiz, String> answerCol = this.createEditableColumn("Jawaban", "answer");
        answerCol.setMinWidth(200.0);
        TableColumn<Quiz, String> pointCol = this.createEditableColumn("Point", "point");
        pointCol.setMinWidth(50.0);
        this.table.getColumns().addAll(new TableColumn[]{questionCol, answerCol, pointCol});
        Button playButton = new Button("Mulai Kuis");
        playButton.setOnAction((e) -> {
            this.playerName = new TextField();
            this.playerName.setPromptText("(max 20 karakter)");
            this.playerName.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 20) {
                    this.playerName.setText(oldValue);
                    this.showAlert("Peringatan", "Nama Anda tidak boleh lebih dari 20 karakter.");
                }

            });
            Label nameLabel = new Label("Masukkan Nama:");
            VBox playVBox = new VBox(10.0, new Node[]{nameLabel, this.playerName});
            playVBox.setAlignment(Pos.CENTER);
            Button startQuizButton = new Button("Mulai Kuis");
            startQuizButton.setOnAction((event) -> {
                this.startQuiz();
            });
            VBox playButtonVBox = new VBox(10.0, new Node[]{playVBox, startQuizButton});
            playButtonVBox.setAlignment(Pos.CENTER);
            this.scene3 = new Scene(playButtonVBox, 300.0, 200.0);
            primaryStage.setScene(this.scene3);
        });
        Button deleteButton = new Button("Hapus Kuis");
        deleteButton.setOnAction((e) -> {
            this.deleteSelectedQuiz();
        });
        HBox hboxTable = new HBox(10.0, new Node[]{this.table});
        hboxTable.setAlignment(Pos.CENTER);
        HBox hboxButtons = new HBox(10.0, new Node[]{playButton, deleteButton});
        hboxButtons.setAlignment(Pos.CENTER);
        this.table.setItems(this.data);
        Button addData = new Button("Tambah Kuis");
        addData.setOnAction((e) -> {
            primaryStage.setScene(this.scene1);
        });
        VBox vbox = new VBox(10.0, new Node[]{label2, hboxTable, hboxButtons, addData, importButton});
        vbox.setPadding(new Insets(20.0));
        vbox.setAlignment(Pos.CENTER);
        this.scene2 = new Scene(vbox, 600.0, 400.0);
        this.playerNameLabel = new Label();
        this.playerNameLabel.setFont(new Font("Arial", 20.0));
        this.currentQuestionLabel = new Label();
        this.answerField = new TextField();
        this.answerField.setPromptText("Jawaban Anda");
        this.answerField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 50) {
                this.answerField.setText(oldValue);
                this.showAlert("Peringatan", "Jawaban tidak boleh lebih dari 50 karakter.");
            }

        });
        Button submitAnswerButton = new Button("Submit Jawaban");
        submitAnswerButton.setOnAction((e) -> {
            this.submitAnswer(this.answerField.getText());
        });
        Button finishQuizButton = new Button("Selesai Kuis");
        finishQuizButton.setOnAction((e) -> {
            this.finishQuiz();
        });
        VBox quizPlayVBox = new VBox(10.0, new Node[]{this.playerNameLabel, this.currentQuestionLabel, this.answerField, submitAnswerButton, finishQuizButton});
        quizPlayVBox.setAlignment(Pos.CENTER);
        this.scene4 = new Scene(quizPlayVBox, 400.0, 300.0);
        Label summaryLabel = new Label("Ringkasan Kuis");
        summaryLabel.setFont(new Font("Arial", 30.0));
        TableColumn<Quiz, String> summaryQuestionCol = this.createNonEditableColumn("Pertanyaan", "question");
        summaryQuestionCol.setMinWidth(200.0);
        TableColumn<Quiz, String> summaryAnswerCol = this.createNonEditableColumn("Jawaban Benar", "answer");
        summaryAnswerCol.setMinWidth(200.0);
        TableColumn<Quiz, String> summaryUserAnswerCol = this.createNonEditableColumn("Jawaban Anda", "userAnswer");
        summaryUserAnswerCol.setMinWidth(200.0);
        TableColumn<Quiz, String> summaryPointCol = this.createNonEditableColumn("Point", "point");
        summaryPointCol.setMinWidth(50.0);
        this.summaryTable.getColumns().addAll(new TableColumn[]{summaryQuestionCol, summaryAnswerCol, summaryUserAnswerCol, summaryPointCol});
        this.summaryTable.setItems(this.summaryData);
        VBox summaryVBox = new VBox(10.0, new Node[]{summaryLabel, this.summaryTable});
        summaryVBox.setAlignment(Pos.CENTER);
        this.scene5 = new Scene(summaryVBox, 600.0, 400.0);
        primaryStage.setScene(this.scene2);
        primaryStage.show();
    }

    private TableColumn<Quiz, String> createEditableColumn(String title, String propertyName) {
        TableColumn<Quiz, String> column = new TableColumn(title);
        column.setCellValueFactory(new PropertyValueFactory(propertyName));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit((e) -> {
            ((Quiz)e.getTableView().getItems().get(e.getTablePosition().getRow())).question.set((String)e.getNewValue());
            this.saveDataToFile();
        });
        return column;
    }

    private TableColumn<Quiz, String> createNonEditableColumn(String title, String propertyName) {
        TableColumn<Quiz, String> column = new TableColumn(title);
        column.setCellValueFactory(new PropertyValueFactory(propertyName));
        return column;
    }

    private boolean isInputValid(TextField question, TextField answer, TextField point) {
        return !question.getText().isEmpty() && !answer.getText().isEmpty() && !point.getText().isEmpty();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void startQuiz() {
        this.playerScore = 0;
        this.currentQuestionIndex = 0;
        this.playerNameLabel.setText("Pemain: " + this.playerName.getText());
        this.showNextQuestion();
        this.primaryStage.setScene(this.scene4);
    }

    private void showNextQuestion() {
        if (this.currentQuestionIndex < this.data.size()) {
            Quiz currentQuestion = (Quiz)this.data.get(this.currentQuestionIndex);
            this.currentQuestionLabel.setText("Pertanyaan: " + currentQuestion.getQuestion());
            this.answerField.clear();
        } else {
            this.finishQuiz();
        }

    }

    private void submitAnswer(String submittedAnswer) {
        if (this.currentQuestionIndex < this.data.size()) {
            Quiz currentQuestion = (Quiz)this.data.get(this.currentQuestionIndex);
            String correctAnswer = currentQuestion.getAnswer();
            if (submittedAnswer.equalsIgnoreCase(correctAnswer)) {
                int points = Integer.parseInt(currentQuestion.getPoint());
                this.playerScore += points;
                this.showAlert("Benar!", "Jawaban Anda benar! Anda mendapatkan " + points + " poin.");
            } else {
                this.showAlert("Salah", "Maaf, jawaban Anda salah.");
            }

            this.userAnswers.add(new Quiz("", "", "", submittedAnswer));
            ++this.currentQuestionIndex;
            this.showNextQuestion();
        } else {
            this.showAlert("Error", "Tidak ada pertanyaan lagi.");
        }

    }

    private void finishQuiz() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Hasil Kuis");
        alert.setHeaderText((String)null);
        int totalPoints = 0;

        Quiz question;
        String userAnswer;
        for(Iterator var3 = this.data.iterator(); var3.hasNext(); this.summaryData.add(new Quiz(question.getQuestion(), question.getAnswer(), question.getPoint(), userAnswer))) {
            question = (Quiz)var3.next();
            userAnswer = "";
            if (this.userAnswers.size() > 0) {
                userAnswer = ((Quiz)this.userAnswers.get(0)).getUserAnswer();
                this.userAnswers.remove(0);
            }

            if (userAnswer.equalsIgnoreCase(question.getAnswer())) {
                totalPoints += Integer.parseInt(question.getPoint());
            } else {
                userAnswer = "";
            }
        }

        String var10001 = this.playerName.getText();
        alert.setContentText("Pemain: " + var10001 + "\nSkor Anda: " + totalPoints);
        alert.showAndWait();
        this.saveScore(this.playerName.getText(), totalPoints);
        this.showSummary(totalPoints);
    }

    private void saveScore(String playerName, int score) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.scoreFile, true));
            String line = playerName + "," + score;
            writer.write(line);
            writer.newLine();
            writer.close();
        } catch (IOException var5) {
            System.out.println("Error writing to file: " + this.scoreFile.getName());
        }

    }

    private void showSummary(int totalPoints) {
        Label totalPointsLabel = new Label("Total Poin: " + totalPoints);
        totalPointsLabel.setFont(new Font("Arial", 20.0));
        Button exitButton = new Button("Keluar");
        exitButton.setOnAction((e) -> {
            this.primaryStage.close();
        });
        Button restartButton = new Button("Ulangi Kuis");
        restartButton.setOnAction((e) -> {
            this.restartQuiz();
        });
        Button backButton5 = new Button("Kembali");
        backButton5.setOnAction((e) -> {
            this.clearSummaryTable();
            this.table.getItems().clear();
            this.primaryStage.setScene(this.scene2);
        });
        VBox summaryVBox = new VBox(10.0, new Node[]{new Label("Ringkasan Kuis"), this.summaryTable, totalPointsLabel, exitButton, restartButton, backButton5});
        summaryVBox.setAlignment(Pos.CENTER);
        this.scene5 = new Scene(summaryVBox, 600.0, 400.0);
        this.primaryStage.setScene(this.scene5);
    }

    private void restartQuiz() {
        this.userAnswers.clear();
        this.summaryData.clear();
        this.primaryStage.setScene(this.scene2);
    }

    private void readDataFromFile() {
        try {
            Scanner scanner = new Scanner(this.dataFile);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    this.data.add(new Quiz(parts[0], parts[1], parts[2], parts[3]));
                }
            }

            scanner.close();
        } catch (FileNotFoundException var4) {
            System.out.println("File not found: " + this.dataFile.getName());
        }

    }

    private void saveDataToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.dataFile));
            Iterator var2 = this.data.iterator();

            while(var2.hasNext()) {
                Quiz question = (Quiz)var2.next();
                String var10000 = question.getQuestion();
                String line = var10000 + "," + question.getAnswer() + "," + question.getPoint() + "," + question.getUserAnswer();
                writer.write(line);
                writer.newLine();
            }

            writer.close();
        } catch (IOException var5) {
            System.out.println("Error writing to file: " + this.dataFile.getName());
        }

    }

    private void deleteSelectedQuiz() {
        Quiz selectedQuiz = (Quiz)this.table.getSelectionModel().getSelectedItem();
        if (selectedQuiz != null) {
            this.data.remove(selectedQuiz);
            this.saveDataToFile();
            this.showAlert("Berhasil", "Kuis berhasil dihapus.");
        } else {
            this.showAlert("Peringatan", "Pilih kuis yang ingin dihapus.");
        }

    }

    private void importQuizData() {
        try {
            Path path = Paths.get("quiz_data.txt");
            Stream<String> lines = Files.lines(path);
            lines.forEach((line) -> {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    this.data.add(new Quiz(parts[0], parts[1], parts[2], ""));
                }

            });
            lines.close();
        } catch (IOException var3) {
            System.out.println("Error reading from file: " + var3.getMessage());
        }

    }

    private void clearSummaryTable() {
        this.summaryData.clear();
        this.summaryTable.getItems().clear();
    }

    public static class Quiz {
        private final SimpleStringProperty question;
        private final SimpleStringProperty answer;
        private final SimpleStringProperty point;
        private final SimpleStringProperty userAnswer;

        public Quiz(String question, String answer, String point, String userAnswer) {
            this.question = new SimpleStringProperty(question);
            this.answer = new SimpleStringProperty(answer);
            this.point = new SimpleStringProperty(point);
            this.userAnswer = new SimpleStringProperty(userAnswer);
        }

        public String getQuestion() {
            return this.question.get();
        }

        public String getAnswer() {
            return this.answer.get();
        }

        public String getPoint() {
            return this.point.get();
        }

        public String getUserAnswer() {
            return this.userAnswer.get();
        }
    }
}
