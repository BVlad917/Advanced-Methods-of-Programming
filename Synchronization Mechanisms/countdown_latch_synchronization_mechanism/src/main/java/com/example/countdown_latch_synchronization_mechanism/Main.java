package com.example.countdown_latch_synchronization_mechanism;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.countdown_latch_synchronization_mechanism.Controller.ListOfProgramsController;
import com.example.countdown_latch_synchronization_mechanism.Model.Expression.*;
import com.example.countdown_latch_synchronization_mechanism.Model.Statement.*;
import com.example.countdown_latch_synchronization_mechanism.Model.Type.BoolType;
import com.example.countdown_latch_synchronization_mechanism.Model.Type.IntType;
import com.example.countdown_latch_synchronization_mechanism.Model.Type.RefType;
import com.example.countdown_latch_synchronization_mechanism.Model.Type.StringType;
import com.example.countdown_latch_synchronization_mechanism.Model.Value.BoolValue;
import com.example.countdown_latch_synchronization_mechanism.Model.Value.IntValue;
import com.example.countdown_latch_synchronization_mechanism.Model.Value.StringValue;

import java.io.IOException;

public class Main extends Application {

    ListOfProgramsController listOfProgramsController;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("list-of-programs.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            this.listOfProgramsController = fxmlLoader.getController();
            this.addExamplesToController();
            this.listOfProgramsController.setMainStage(stage);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    private void addExamplesToController() {
        this.listOfProgramsController.addStatement(getEx1());
        this.listOfProgramsController.addStatement(getEx2());
        this.listOfProgramsController.addStatement(getEx3());
        this.listOfProgramsController.addStatement(getEx4());
        this.listOfProgramsController.addStatement(getEx5());
        this.listOfProgramsController.addStatement(getEx6());
        this.listOfProgramsController.addStatement(getEx7());
        this.listOfProgramsController.addStatement(getEx8());
        this.listOfProgramsController.addStatement(getEx9());
        this.listOfProgramsController.addStatement(getEx10());
        this.listOfProgramsController.addStatement(getEx11());
        this.listOfProgramsController.addStatement(getEx12());
        this.listOfProgramsController.addStatement(getEx13());
        this.listOfProgramsController.addStatement(getEx14());

    }

    private static IStatement getEx1() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
    }

    private static IStatement getEx2() {
        return new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                new ArithmeticExpression(new ValueExpression(new IntValue(3)),
                                        new ValueExpression(new IntValue(5)), "*"), "+")),
                                new CompoundStatement(new AssignStatement("b",
                                        new ArithmeticExpression(new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)), "+")),
                                        new PrintStatement(new VariableExpression("b"))))));
    }

    private static IStatement getEx3() {
        return new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v"))))));
    }

    private static IStatement getEx4() {
        return new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new openRFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new closeRFile(new VariableExpression("varf"))))))))));
    }

    private static IStatement getEx5() {
        return new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test_empty.in"))),
                        new CompoundStatement(new openRFile(new VariableExpression("varf")),
                                new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new closeRFile(new VariableExpression("varf"))))))))));
    }

    private static IStatement getEx6() {
        return new PrintStatement(new RelationalExpression(
                new ArithmeticExpression(new ValueExpression(new IntValue(2)), new ValueExpression(new IntValue(3)), "+"),
                new ValueExpression(new IntValue(1)), "<"));
    }

    private static IStatement getEx7() {
        return new PrintStatement(new RelationalExpression(new ArithmeticExpression(
                new ValueExpression(new IntValue(51)), new ValueExpression(new IntValue(3)), "/"),
                new ArithmeticExpression(new ValueExpression(new IntValue(15)), new ValueExpression(new IntValue(2)), "+"), "=="));
    }

    private static IStatement getEx8() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStmt("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStmt("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));
    }

    private static IStatement getEx9() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStmt("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStmt("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new readFromHeap(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(new readFromHeap(new readFromHeap(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), "+")))))));
    }

    private static IStatement getEx10() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStmt("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new readFromHeap(new VariableExpression("v"))),
                                new CompoundStatement(new writeToHeap("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new readFromHeap(new VariableExpression("v")), new ValueExpression(new IntValue(5)), "+"))))));
    }

    private static IStatement getEx11() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                new CompoundStatement(new NewStmt("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(new NewStmt("a", new VariableExpression("v")),
                                        new CompoundStatement(new NewStmt("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new readFromHeap(new readFromHeap(new VariableExpression("a")))))))));
    }

    private static IStatement getEx12() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), "-")))),
                                new PrintStatement(new VariableExpression("v")))));
    }

    private static IStatement getEx13() {
        return new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewStmt("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new forkStatement(new CompoundStatement(new writeToHeap("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new readFromHeap(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new readFromHeap(new VariableExpression("a")))))))));
    }

    private static IStatement getEx14() {
        return new CompoundStatement(new VariableDeclarationStatement("v1", new RefType(new IntType())), new CompoundStatement(new VariableDeclarationStatement("v2", new RefType(new IntType())), new CompoundStatement(new VariableDeclarationStatement("v3", new RefType(new IntType())), new CompoundStatement(new VariableDeclarationStatement("cnt", new IntType()),
                new CompoundStatement(new NewStmt("v1", new ValueExpression(new IntValue(2))), new CompoundStatement(new NewStmt("v2", new ValueExpression(new IntValue(3))), new CompoundStatement(new NewStmt("v3", new ValueExpression(new IntValue(4))), new CompoundStatement(new newLatchStmt("cnt", new readFromHeap(new VariableExpression("v2"))),
                        new CompoundStatement(new forkStatement(new CompoundStatement(new writeToHeap("v1", new ArithmeticExpression(new readFromHeap(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), "*")), new CompoundStatement(new PrintStatement(new readFromHeap(new VariableExpression("v1"))), new CompoundStatement(new countDownStmt("cnt"),
                                new forkStatement(new CompoundStatement(new writeToHeap("v2", new ArithmeticExpression(new readFromHeap(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), "*")), new CompoundStatement(new PrintStatement(new readFromHeap(new VariableExpression("v2"))), new CompoundStatement(new countDownStmt("cnt"),
                                        new forkStatement(new CompoundStatement(new writeToHeap("v3", new ArithmeticExpression(new readFromHeap(new VariableExpression("v3")), new ValueExpression(new IntValue(10)), "*")), new CompoundStatement(new PrintStatement(new readFromHeap(new VariableExpression("v3"))), new countDownStmt("cnt")))))))))))),
                                new CompoundStatement(new awaitStmt("cnt"), new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new CompoundStatement(new countDownStmt("cnt"), new PrintStatement(new ValueExpression(new IntValue(100)))))))))))))));
    }

    private static IStatement makeProgram(IStatement[] statements) {
        IStatement finalStatement = statements[statements.length - 1];
        for (int i = statements.length - 2; i >= 0; i--) {
            finalStatement = new CompoundStatement(statements[i], finalStatement);
        }
        return finalStatement;
    }
}