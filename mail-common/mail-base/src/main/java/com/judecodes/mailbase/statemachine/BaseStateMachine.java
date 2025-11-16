package com.judecodes.mailbase.statemachine;

import com.judecodes.mailbase.exception.BizException;

import java.util.HashMap;
import java.util.Map;

import static com.judecodes.mailbase.exception.BizErrorCode.STATE_MACHINE_TRANSITION_FAILED;

public class BaseStateMachine<STATE, EVENT> implements StateMachine<STATE, EVENT>{

    // 状态转移表：当前状态 -> (事件 -> 新状态)
    private final Map<String,STATE> stateTransitions = new HashMap<>();
    /**
     * 注册一条状态迁移：
     * 当前状态 state + 事件 event -> 迁移到 newState
     */
    public void put(STATE state, EVENT event, STATE newState){
        String key = buildKey(state, event);
        stateTransitions.put(key, newState);

    }
    /**
     * 根据当前状态和事件，计算下一个状态
     */
    @Override
    public STATE transition(STATE state, EVENT event) {
        String key = buildKey(state, event);
        STATE newState = stateTransitions.get(key);
        if (newState == null) {
            throw new BizException("state = " + state + " , event = " + event,STATE_MACHINE_TRANSITION_FAILED);
        }
        return newState;
    }

    /**
     * 生成状态 + 事件 的唯一 key
     */
    private String buildKey(STATE state, EVENT event) {
        return state + "->" + event;
    }
}
