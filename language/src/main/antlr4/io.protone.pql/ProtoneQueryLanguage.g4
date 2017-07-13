grammar ProtoneQueryLanguage;
program : (ql_module)+ ;
ql_module
    : cor_module_statemant
    | traffic_module_statemant
    | crm_module_statemant
    | library_module_statemant
    | scheduler_module_statemant
    ;
cor_module_statemant : cor_module cor_entity  (where_clause)? (groupby_clause)? (having_clause)? (orderby_clause)?;

traffic_module_statemant: traffic_module traffic_entity (where_clause)? (groupby_clause)? (having_clause)? (orderby_clause)?
                                                           ;
crm_module_statemant:crm_module crm_entity (where_clause)? (groupby_clause)? (having_clause)? (orderby_clause)?
                                  ;
library_module_statemant :library_module library_entity (where_clause)? (groupby_clause)? (having_clause)? (orderby_clause)?
                                            ;
scheduler_module_statemant :scheduler_module scheduler_entity (where_clause)? (groupby_clause)? (having_clause)? (orderby_clause)?
                                               ;

cor_entity : 'Person' | 'Adress';
traffic_entity : 'Media Plan';
crm_entity :'Customer';
library_entity :'MediaItem';
scheduler_entity :'Events';

cor_module: 'CORE';
traffic_module : 'TRAFFIC';
crm_module :'CRM';
library_module : 'LIBRARY' ;
scheduler_module :'SCHEDULER';



collection_member_declaration
   : 'IN' '(' collection_valued_path_expression ')' ('AS')? IDENTIFICATION_VARIABLE
   ;

single_valued_path_expression
   : state_field_path_expression
   | single_valued_association_path_expression
   ;

state_field_path_expression
   : (IDENTIFICATION_VARIABLE | single_valued_association_path_expression) '.' state_field
   ;

single_valued_association_path_expression
   : IDENTIFICATION_VARIABLE '.' (single_valued_association_field '.')* single_valued_association_field
   ;

collection_valued_path_expression
   : IDENTIFICATION_VARIABLE '.' (single_valued_association_field '.')* collection_valued_association_field
   ;

state_field
   : (embedded_class_state_field '.')* simple_state_field
   ;





select_expression
   : single_valued_path_expression
   | aggregate_expression
   | IDENTIFICATION_VARIABLE
   | 'OBJECT' '(' IDENTIFICATION_VARIABLE ')'
   | constructor_expression
   ;

constructor_expression
   : 'NEW' constructor_name '(' constructor_item (',' constructor_item)* ')'
   ;

constructor_item
   : single_valued_path_expression
   | aggregate_expression
   ;

aggregate_expression
   : ('AVG' | 'MAX' | 'MIN' | 'SUM') '(' ('DISTINCT')? state_field_path_expression ')'
   | 'COUNT' '(' ('DISTINCT')? (IDENTIFICATION_VARIABLE | state_field_path_expression | single_valued_association_path_expression) ')'
   ;

where_clause
   : 'WHERE' conditional_expression
   ;

groupby_clause
   : 'GROUP' 'BY' groupby_item (',' groupby_item)*
   ;

groupby_item
   : single_valued_path_expression
   | IDENTIFICATION_VARIABLE
   ;

having_clause
   : 'HAVING' conditional_expression
   ;

orderby_clause
   : 'ORDER' 'BY' orderby_item (',' orderby_item)*
   ;

orderby_item
   : state_field_path_expression ('ASC' | 'DESC')?
   ;


association_path_expression
   : collection_valued_path_expression
   | single_valued_association_path_expression
   ;




conditional_expression
   : (conditional_term) ('OR' conditional_term)*
   ;

conditional_term
   : (conditional_factor) ('AND' conditional_factor)*
   ;

conditional_factor
   : ('NOT')? conditional_primary
   ;

conditional_primary
   : simple_cond_expression
   | '(' conditional_expression ')'
   ;

simple_cond_expression
   : comparison_expression
   | between_expression
   | like_expression
   | in_expression
   | null_comparison_expression
   | empty_collection_comparison_expression
   | collection_member_expression
   | exists_expression
   ;

between_expression
   : arithmetic_expression ('NOT')? 'BETWEEN' arithmetic_expression 'AND' arithmetic_expression
   | string_expression ('NOT')? 'BETWEEN' string_expression 'AND' string_expression
   | datetime_expression ('NOT')? 'BETWEEN' datetime_expression 'AND' datetime_expression
   ;

in_expression
   : state_field_path_expression ('NOT')? 'IN' '(' (in_item (',' in_item)* | ) ')'
   ;

in_item
   : literal
   | input_parameter
   ;

like_expression
   : string_expression ('NOT')? 'LIKE' pattern_value ('ESCAPE' ESCAPE_CHARACTER)?
   ;

null_comparison_expression
   : (single_valued_path_expression | input_parameter) 'IS' ('NOT')? 'NULL'
   ;

empty_collection_comparison_expression
   : collection_valued_path_expression 'IS' ('NOT')? 'EMPTY'
   ;

collection_member_expression
   : entity_expression ('NOT')? 'MEMBER' ('OF')? collection_valued_path_expression
   ;

exists_expression
   : ('NOT')? 'EXISTS'
   ;

all_or_any_expression
   : ('ALL' | 'ANY' | 'SOME')
   ;

comparison_expression
   : string_expression comparison_operator (string_expression | all_or_any_expression)
   | boolean_expression ('=' | '<>') (boolean_expression | all_or_any_expression)
   | enum_expression ('=' | '<>') (enum_expression | all_or_any_expression)
   | datetime_expression comparison_operator (datetime_expression | all_or_any_expression)
   | entity_expression ('=' | '<>') (entity_expression | all_or_any_expression)
   | arithmetic_expression comparison_operator (arithmetic_expression | all_or_any_expression)
   ;

comparison_operator
   : '='
   | '>'
   | '>='
   | '<'
   | '<='
   | '<>'
   ;

arithmetic_expression : simple_arithmetic_expression;

simple_arithmetic_expression
   : (arithmetic_term) (('+' | '-') arithmetic_term)*
   ;

arithmetic_term
   : (arithmetic_factor) (('*' | '/') arithmetic_factor)*
   ;

arithmetic_factor
   : ('+' | '-')? arithmetic_primary
   ;

arithmetic_primary
   : state_field_path_expression
   | numeric_literal
   | '(' simple_arithmetic_expression ')'
   | input_parameter
   | functions_returning_numerics
   | aggregate_expression
   ;

string_expression : string_primary
   ;

string_primary
   : state_field_path_expression
   | STRINGLITERAL
   | input_parameter
   | aggregate_expression
   ;

datetime_expression : datetime_primary;

datetime_primary
   : state_field_path_expression
   | input_parameter
   | functions_returning_datetime
   | aggregate_expression
   ;

boolean_expression: boolean_primary ;

boolean_primary
   : state_field_path_expression
   | boolean_literal
   | input_parameter
   ;

enum_expression: enum_primary;

enum_primary
   : state_field_path_expression
   | enum_literal
   | input_parameter
   ;

entity_expression
   : single_valued_association_path_expression
   | simple_entity_expression
   ;

simple_entity_expression
   : IDENTIFICATION_VARIABLE
   | input_parameter
   ;

functions_returning_numerics
   : 'LENGTH' '(' string_primary ')'
   | 'LOCATE' '(' string_primary ',' string_primary (',' simple_arithmetic_expression)? ')'
   | 'ABS' '(' simple_arithmetic_expression ')'
   | 'SQRT' '(' simple_arithmetic_expression ')'
   | 'MOD' '(' simple_arithmetic_expression ',' simple_arithmetic_expression ')'
   | 'SIZE' '(' collection_valued_path_expression ')'
   ;

functions_returning_datetime
   : 'CURRENT_DATE'
   | 'CURRENT_TIME'
   | 'CURRENT_TIMESTAMP'
   ;


trim_specification
   : 'LEADING'
   | 'TRAILING'
   | 'BOTH'
   ;

numeric_literal
   :
   ;

pattern_value
   :
   ;

input_parameter
   : '?' INT_NUMERAL
   | ':' IDENTIFICATION_VARIABLE
   ;

literal
   :
   ;

constructor_name
   :
   ;

enum_literal
   :
   ;

boolean_literal
   : 'true'
   | 'false'
   ;

simple_state_field
   :
   ;

embedded_class_state_field
   :
   ;

single_valued_association_field
   :
   ;

collection_valued_association_field
   :
   ;

abstract_schema_name
   :
   ;

IDENTIFICATION_VARIABLE
   : ('a' .. 'z' | 'A' .. 'Z' | '_') ('a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_')*
   ;

CHARACTER
   : '\'' (~ ('\'' | '\\')) '\''
   ;

STRINGLITERAL
   : ('\'' (~ ('\\' | '"'))* '\'')
   ;

ESCAPE_CHARACTER
   : CHARACTER
   ;

WS
   : [ \t\r\n] -> skip
   ;