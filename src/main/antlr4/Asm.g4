grammar Asm;

@header {
package hu.progtech.cd2t100.asm;
}

/**
 * Programs must start with 0 or more preprocessor rules.
 * For example:
 * {@code
 * !clampat 9999
 * MOV 1000 ACC
 * }
 */
program
  : preprocessor_rule* program_line*
  ;

/**
 * In a single line you can place a label, an instruction and a comment,
 * strictly in this order. Any of these items can be omitted.
 * The following is perfectly valid:
 * {@code
 * zero: # ACC = 0
 * }
 */
program_line
  : WHITESPACE? (label | instruction)? (WHITESPACE? comment)? NEWLINE
  ;

/**
 * Preprocessor rules must stand on their own optionally followed by
 * a comment.
 */
preprocessor_rule
  : '!' rule_name (WHITESPACE argument)* (WHITESPACE? comment)? NEWLINE
  ;

label
  : LABEL
  ;

comment
  : COMMENT
  ;

/**
 * Lines with both a label and an instruction are handled by this rule.
 * An instruction can have any number of arguments separated by whitespaces.
 */
instruction
  : (label WHITESPACE)? opcode (WHITESPACE argument)*
  ;

/**
 * Arguments, opcodes, labels and preprocessor rules can be formed using the same
 * character set. The use of the following characters are not legal:
 * <ul>
 *  <li>{@code horizontal tabulator}</li>
 *  <li>{@code carriage return}</li>
 *  <li>{@code newline}</li>
 *  <li>{@code space}</li>
 *  <li>{@code !}</li>
 *  <li>{@code :}</li>
 *  <li>{@code @}</li>
 *  <li>{@code .}</li>
 *  <li>{@code #}</li>
 *  <li>{@code ,}</li>
 * </ul>
 */
argument
  : WORD
  ;

opcode
  : WORD
  ;

rule_name
  : WORD
  ;

LABEL
  : WORD ':'
  ;

WORD
  : ~('\t' | '\r' | '\n' | '!' | ':' | '@' | '.' | ' ' | '#' | ',')+
  ;

COMMENT
   : '#' ~('\n' | '\r')*
   ;

WHITESPACE
  : (' ' | '\t')+
  ;

NEWLINE
  :  '\r'? '\n'
  ;
