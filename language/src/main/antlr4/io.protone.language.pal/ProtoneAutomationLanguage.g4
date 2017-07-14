grammar ProtoneAutomationLanguage;
program : (automation_module)+ ;

automation_module
    : protone_playout_statemant;

protone_playout_statemant:
    protone_playout_basic;
  //protone_playout_time_action|
  //protone_playout_condition_action;


protone_playout_basic : channel channel_id playout playout_id (action_content)? (content)?  (action)?;
//protone_playout_time_action: ;
//protone_playout_condition_action: ;

channel :'CHANNEL';
playout :'PLAYOUT';
action: 'PLAY'| 'PAUSE' | 'STOP';
action_content : 'LOAD' | 'UNLOAD';

channel_id
   :
   ;

playout_id
   :
   ;
content
    :
    ;