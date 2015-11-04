package sat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import sat.env.Bool;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.PosLiteral;

import static junit.framework.Assert.assertTrue;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();


    public static void main(String[] args){
        Formula result;
        try{
            result = Parsercnf.Parser("E:\\Term 6\\2D project\\2D-SAT-solver\\Project-2D-starting\\sampleCNF\\largesat.cnf");
            System.out.println(result);
        }
        catch(ParseException |IOException e){
        }














        String result1 = "satisfiable";
        if (result1.equals("satisfiable")){
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
    	Environment e = SATSolver.solve(makeFm(makeCl(a, b)));
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);
    	
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