package sat;

import immutable.EmptyImList;
import immutable.ImList;
import sat.env.Bool;
import sat.env.Environment;
import sat.env.Variable;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.PosLiteral;

/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     * 
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {

        ImList<Clause> clauses = formula.getClauses();
        return solve(clauses, new Environment());
    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     * 
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        //if the formula is empty, it is trivially satisfiable
        if (clauses.isEmpty()) return env;

        Clause shortest_clause = clauses.first();
        for (Clause aClause:clauses) {
            if (aClause.isEmpty()) return null; //if a clause is empty, problem is unsatisfiable
            if (aClause.size() < shortest_clause.size())
                shortest_clause = aClause;      //find the shortest clause
        }

        Literal l = shortest_clause.chooseLiteral(); //choose a literal from the clause
        Variable v = l.getVariable();

        //if shortest clause contains 1 literal, set the literal to be true and reduce formula size
        if (shortest_clause.isUnit()) {
            //if the literal is positive, set the variable to be TRUE, else set it to FALSE
            if (PosLiteral.make(v).equals(l)) env = env.put(v, Bool.TRUE);
            else env = env.put(v, Bool.FALSE);
            return solve(substitute(clauses, l), env);

        } else {  //if the clause contains multiple literals
            //set one literal l to be TRUE
            if (PosLiteral.make(v).equals(l)) env = env.put(v, Bool.TRUE);
            else env = env.put(v, Bool.FALSE);
            Environment newEnv = solve(substitute(clauses, l), env);
            //if this makes the formula unsatisfiable, try setting literal l to FALSE instead
            if (newEnv == null) {
                if (PosLiteral.make(v).equals(l)) env = env.put(v, Bool.FALSE);
                else env = env.put(v, Bool.TRUE);
                return solve(substitute(clauses, l.getNegation()), env);
            } else return newEnv;

        }
    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     * 
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
            Literal l) {
        ImList<Clause> newClauses = new EmptyImList<Clause>();
        for (Clause aClause:clauses) {
            Clause newClause = aClause.reduce(l);
            if (newClause!= null)
                newClauses = newClauses.add(newClause);
        }
        return newClauses;
    }

}
