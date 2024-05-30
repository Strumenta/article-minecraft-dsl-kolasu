grammar Minecraft;

@header {package com.strumenta.articles.minecraftdsl.antlr;}

mod: 'mod' name=(NAME | STRING) '(' id=NAME '@' version=STRING ')' '{'
    ('license' ':' license=STRING)?
'}' EOF;

NAME: [A-Za-z]([A-Za-z.]*[A-Za-z])?;
STRING: '"' ~('"')* '"';
WS: [ \t\n]+ -> channel(HIDDEN);
