package sat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.NegLiteral;
import sat.formula.PosLiteral;

/**
 * Created by SUTD on 4/11/2015.
 */
public class Parsercnf {
    public static Formula Parser(String filelocation)throws IOException, ParseException {
        InputStream fileinput = null;
        fileinput = new FileInputStream(filelocation);
        Scanner scanner = new Scanner(fileinput);
        int NumberOfVariables;
        int NumberOfClauses;

        try{
            String word = scanner.next();
            while(word.equals("c")){
                scanner.nextLine();
                word = scanner.next();
            }
            if (word.equals("p") == false){
                throw new ParseException("Expected p, However " + word + " was found instead",1);
            }
        }
        catch (NoSuchElementException E){
            throw new ParseException("Header not found, Nothing to scan",1);
        }

        try {
            String word = scanner.next();
            if (word.equals("cnf") == false) {
                throw new ParseException("Expected cnf, However " + word + " was found instead", 1);
            }
            NumberOfVariables = scanner.nextInt();
            NumberOfClauses = scanner.nextInt();
        }
        catch(NoSuchElementException e){
            throw new ParseException("Incomplete Header, Nothing to scan",1);
        }

        Formula formula = new Formula();
        Clause clause = new Clause();
        try{
            while (NumberOfClauses>0){
                String literal = scanner.next();

                if (literal.equals("0")){
                    NumberOfClauses--;
                    formula = formula.addClause(clause);
                    clause = new Clause();
                }
                else if ((literal.substring(0,1)).equals("-")){
                    String posnum = literal.substring(1);
                    clause = clause.add(NegLiteral.make(posnum));
                }
                else{
                    clause = clause.add(PosLiteral.make(literal));
                }
            }
        }
        catch(NoSuchElementException e){
            throw new ParseException(
                    "Clauses are missing",1);

        }

        return formula;

    }

}
