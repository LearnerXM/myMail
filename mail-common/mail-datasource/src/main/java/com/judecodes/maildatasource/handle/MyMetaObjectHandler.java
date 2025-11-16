package com.judecodes.maildatasource.handle;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // LocalDateTime 类型
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // deleted / lockVersion 是 Integer/Long 时也可以用 strictInsertFill
        this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
        this.strictInsertFill(metaObject, "lockVersion", Integer.class, 0);
    }

    /**
     * 当没有值的时候再设置属性，如果有值则不设置。主要是方便单元测试
     *
     * @param fieldName
     * @param fieldVal
     * @param metaObject
     */
    public void setFieldValByNameIfNull(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (metaObject.getValue(fieldName) == null) {
            this.setFieldValByName(fieldName, fieldVal, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}