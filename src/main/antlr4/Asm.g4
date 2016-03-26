grammar Asm;

@header {
package hu.progtech.cd2t100.asm;
}

program
  : preprocessor_rule* program_line*
  ;

program_line
  : WHITESPACE? (label | instruction)? (WHITESPACE? comment)? NEWLINE
  ;

preprocessor_rule
  : '!' rule_name (WHITESPACE argument)* (WHITESPACE? comment)? NEWLINE
  ;

label
  : LABEL
  ;

comment
  : COMMENT
  ;

instruction
  : (label WHITESPACE)? opcode (WHITESPACE argument)*
  ;

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
