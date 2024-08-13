grammar Minecraft;

@header {package com.strumenta.articles.minecraftdsl.antlr;}

mod: 'mod' name=(NAME | STRING) '(' id=NAME '@' version=STRING ')' '{'
    ('license' ':' license=STRING)?
    block*
'}' EOF;
block: 'block' name=NAME '{' /* reserved for future extensions */ '}';

NAME: NAME_PART ([.] NAME_PART)*;
fragment NAME_PART: [A-Za-z][A-Za-z0-9]*;
STRING: '"' ~('"')* '"';
WS: [ \t\n]+ -> channel(HIDDEN);
