import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FractionalOperationsTest {

    @Test
    public void testValidInput() {
        assertEquals("1_7/8", FractionalOperations.getCalculationResult(new String[]{ "1/2" , "*", "3_3/4"}));  // Example
        assertEquals("3_1/2", FractionalOperations.getCalculationResult(new String[]{ "2_3/8", "+", "9/8"}));   // Example
        assertEquals("11", FractionalOperations.getCalculationResult(new String[]{ "5" , "+", "6"}));           // Whole numbers
        assertEquals("1_1/4", FractionalOperations.getCalculationResult(new String[]{ "1/8" , "+", "9/8"}));    // Add, same denominators with improper fractions
        assertEquals("2/3", FractionalOperations.getCalculationResult(new String[]{ "1/4" , "+", "5/12"}));     // Add, different denominator
        assertEquals("3/7", FractionalOperations.getCalculationResult(new String[]{ "4/7" , "-", "1/7"}));      // Subtract, same denominators
        assertEquals("8/21", FractionalOperations.getCalculationResult(new String[]{ "5/7" , "-", "1/3"}));     // Subtract, different denominators
        assertEquals("1", FractionalOperations.getCalculationResult(new String[]{ "+1/4" , "*", "+4"}));        // Explicit positive input
        assertEquals("-1/3", FractionalOperations.getCalculationResult(new String[]{ "-1/4" , "/", "3/4"}));    // Negative input
        assertEquals("-4", FractionalOperations.getCalculationResult(new String[]{ "-2_1/4" , "-", "1_3/4"}));  // Negative result
        assertEquals("0", FractionalOperations.getCalculationResult(new String[]{ "5/7" , "*", "0"}));          // Multiply by 0
        assertEquals("6/7", FractionalOperations.getCalculationResult(new String[]{ "5/7", "+", "-0_1/7"}));    // 0 in whole part or -0 in whole part
        assertEquals("1_2/7", FractionalOperations.getCalculationResult(new String[]{ "-5/7", "+", "2_0/7"}));  // 0 in numerator part
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput_argLengthNotThree() {
        FractionalOperations.getCalculationResult(new String[]{ "1/2" , "/", "3/4", "5/6"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperand_zeroAsDenominator() {
        FractionalOperations.getCalculationResult(new String[]{ "1/0" , "/", "3/4"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperand_multipleUnderscores() {
        FractionalOperations.getCalculationResult(new String[]{ "2_5_1/0" , "*", "3/4"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperand_multipleSlashes() {
        FractionalOperations.getCalculationResult(new String[]{ "5/1/0" , "/", "1/4"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperand_noNumberBetweenUnderscoreAndSlash() {
        FractionalOperations.getCalculationResult(new String[]{ "3_/5" , "-", "7/4"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperand_invalidCharacter() {
        FractionalOperations.getCalculationResult(new String[]{ "a_1/5" , "-", "3/4"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperator() {
        FractionalOperations.getCalculationResult(new String[]{ "1/2" , "//", "4"});
    }

    @Test(expected = ArithmeticException.class)
    public void testInvalidInput_zeroDivisor() {
        FractionalOperations.getCalculationResult(new String[]{ "1/2" , "/", "0"});
    }

}
