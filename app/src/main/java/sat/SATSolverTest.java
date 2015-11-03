package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/

import android.renderscript.ScriptGroup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

import immutable.EmptyImList;
import immutable.ImList;
import sat.env.*;
import sat.formula.*;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();


    public static void main(String[] args)throws IOException, ParseException {
        InputStream is = null;
        is = new FileInputStream("C:\\Users\\SUTD\\Desktop\\Project-2D-starting\\sampleCNF\\largeSat.cnf");
        Scanner scanner = new Scanner(is);
        int numberofvariables;
        int numberofclauses;

        try{
            String tok = scanner.next();
            while(tok.equals("c")){
                scanner.nextLine();
                tok = scanner.next();
            }
            if (!tok.equals("p")){
                throw new ParseException("Expected p but " + tok + " was found",1);
            }
        }catch (NoSuchElementException E){
            throw new ParseException("Header not found",1);
        }
        try {
            String tok1 = scanner.next();
            if (!tok1.equals("cnf")) {
                throw new ParseException("Expected cnf but " + tok1 + " was found", 1);
            }
            numberofvariables = scanner.nextInt();
            numberofclauses = scanner.nextInt();
        }
        catch(NoSuchElementException e){
            throw new ParseException("Incomplete Header",1);
        }
        ImList<ImList<Integer>> l1 = new EmptyImList<ImList<Integer>>();
        ImList<Integer> l = new EmptyImList<>();
        try{
            while (numberofclauses>0){
                int literal = scanner.nextInt();
                l = l.add(literal);
                if (literal == 0){
                    numberofclauses--;
                    Clause c = makeCl(l);

                }

            }
        }
        catch(NoSuchElementException e){
            throw new ParseException(
                    "Clauses are missing",1);

        }
        System.out.println(l);









        String result = "satisfiable";
        if (result.equals("satisfiable")){
            BufferedWriter writer = null;
            try {
                //create a temporary file
                String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                File logFile = new File(timeLog);

                // This will output the full path where the file will be written to...
                System.out.println(logFile.getCanonicalPath());

                writer = new BufferedWriter(new FileWriter(logFile));
                writer.write("variable : assignment");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // Close the writer regardless of what happens...
                    writer.close();
                } catch (Exception e) {
                }
            }

        }
    }
    // TODO: add the main method that reads the .cnf file and calls SATSolver.solve to determine the satisfiability

	
    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);
/*
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);
    	
*/    	
    }
    
    
    public void testSATSolver2(){
    	// (~a)
    	Environment e = SATSolver.solve(makeFm(makeCl(na)));
/*
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/    	
    }
    
    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }
    
    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }
    
    
    
}