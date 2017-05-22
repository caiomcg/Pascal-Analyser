package sample.PLA;

import sample.utils.TableData;

import java.util.ArrayList;

/**
 * Created by caiomcg on 16/05/17.
 */
public class PSA implements Analyser {
    private int index;
    private ArrayList<TableData> res;

    public PSA(ArrayList<TableData> res) {
        this.index = 0;
        this.res = res;
    }

    @Override
    public ArrayList<TableData> analyze() throws RuntimeException {
        if (!validateProgram()) { // Validate program ? ;
            throw new RuntimeException("Failed at line: " + res.get(index).getLine() + " - bad token: " + res.get(index).getToken());
        }

        if (!recursiveAnalysis()) {
            throw new RuntimeException("Failed at line: " + res.get(index).getLine() + " - bad token: " + res.get(index).getToken());
        }

        return res;
    }

    private boolean recursiveAnalysis() {
        System.out.println("PRE VAR--------------");
        if (res.get(index).getToken().equals("var")) {
            moveStackReference();
            if (!validateVariableList()) {
                return false;
            }
        }

        System.out.println("PRE PROCEDURE---------------");
        if (res.get(index).getToken().equals("procedure")) {
            moveStackReference();
            if (!validateProcedure()) {
                System.out.println("NOPE");
                return false;
            }
            return recursiveAnalysis();
        }

        System.out.println("PRE BODY-----------------");
        if (res.get(index).getToken().equals("begin")) {
            moveStackReference();
            if (!validateBody()) {
                System.out.println("DEU AGUIA");
                return false;
            } else if (res.get(index).getToken().equals(";")) {
                moveStackReference();
                return recursiveAnalysis(); //It is the end of a procedure
            } else if (res.get(index).getToken().equals(".")) {
                return true; //The end of the program
            }
        }
        System.out.println("LEAVING");
        return false;
    }

    private boolean validateBody() {
        //VALIDATE THE ENTIRE BODY
        if (res.get(index).getToken().equals("end")) { //FINISHES THE BODY! - THE VALIDATOR FOR THIS PROCEDURE
            moveStackReference();
            return true;
        }
        return true; //RETURN FALSE!!
    }

    private boolean validateProgram() {
        if (res.get(index).getToken().equals("program")) {
            moveStackReference();
            System.out.println("Found program");
            if (res.get(index).getClassification().equals("Identifier")) {
                moveStackReference();
                System.out.println("Found identifier");
                if (res.get(index).getToken().equals(";")) {
                    moveStackReference();
                    System.out.println("Found ;");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validateVariableList() {
        System.out.println("VVL - "+ res.get(index).toString());
        if(res.get(index).getClassification().equals("Keyword"))
            return true;

        if (consumeVariables()) {
            if (res.get(index).getClassification().equals("Keyword")) {
                moveStackReference();
                if (res.get(index).getToken().equals(";")) {
                    moveStackReference();
                    return validateVariableList();
                }
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean consumeVariables() {
        System.out.println("Validating for " + res.get(index).getToken());
        if (res.get(index).getClassification().equals("Identifier")) {
            moveStackReference();
            if (res.get(index).getToken().equals(",")) {
                moveStackReference();
                return consumeVariables();
            } else if (res.get(index).getToken().equals(":")) {
                System.out.println("::::::::::::");
                moveStackReference();
                return true;
            }
        }
        return false;
    }

    private boolean validateProcedure() {
        System.out.println("Validating Procedure - " + res.get(index).toString());

        if (res.get(index).getClassification().equals("Identifier")) {
            moveStackReference();
            System.out.println("Found identifier");
            if (res.get(index).getToken().equals("(")) {
                moveStackReference();
                System.out.println("Found Parentheses");
                while (true) {
                    if (consumeVariables()) {
                        if (res.get(index).getClassification().equals("Keyword")) {
                            moveStackReference();
                            System.out.println("IN - " + res.get(index).toString());
                            if (res.get(index).getToken().equals(";")) {
                                moveStackReference();
                                System.out.println("Continuing");
                                continue;
                            }
                            if (res.get(index).getToken().equals(")")) {
                                moveStackReference();
                                System.out.println("ON CLOSE");
                                if (res.get(index).getToken().equals(";")) {
                                    moveStackReference();
                                    System.out.println("ON ;");
                                    return true;
                                }
                                return false;
                            }
                            System.out.println("SKIPPED");
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void moveStackReference() {
        index++;
    }
}














