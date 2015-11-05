package sat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import sat.env.Environment;
import sat.env.Variable;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.PosLiteral;


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
            System.out.println("SAT solver starts!!!");
            long started = System.nanoTime();
            Environment e = SATSolver.solve(result);
            long time = System.nanoTime();
            long timeTaken= time - started;
            System.out.println("Time:" + timeTaken/1000000.0 + "ms");

            if (e != null){
                BufferedWriter write1 = null;
                try {
                    System.out.println("SATISFIABLE");
                    String DATETIME = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    File resultfile = new File(DATETIME+".txt");

                    write1 = new BufferedWriter(new FileWriter(resultfile));
                    for (int i = 1 ; i <= Parsercnf.NumberOfVariables ; i ++) {
                        Variable key = new Variable(i+"");
                        write1.write(i+":"+e.get(key));
                        write1.newLine();
                    }
                } catch(Exception e1){
                    e1.printStackTrace();
                } finally{
                    write1.close();
                }
            }
            else {
                System.out.println("NOT SATISFIABLE");
            }

        }
        catch(ParseException |IOException e){
            System.out.println(e);
        }
    }


    public void testSATSolver1(){
    	// (a v b)
    	Environment e = SATSolver.solve(makeFm(makeCl(a, b)));
/*    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())  
    			|| Bool.TRUE == e.get(b.getVariable())	);*/
    	
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