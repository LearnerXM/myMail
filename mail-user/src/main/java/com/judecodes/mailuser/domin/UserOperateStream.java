package com.judecodes.mailuser.domin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户操作流水表
 * </p>
 *
 * @author judecodes
 * @since 2025-11-17
 */
@Getter
@Setter
@ToString
@TableName("tz_user_operate_stream")
@Schema(name = "UserOperateStream", description = "用户操作流水表")
public class UserOperateStream {

    /**
     * 流水ID（自增主键）
     */
    @Schema(description = "流水ID（自增主键）")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 用户ID
     */
    @TableField("user_id")
    @Schema(description = "用户ID")
    private String userId;

    /**
     * 操作类型
     */
    @TableField("type")
    @Schema(description = "操作类型")
    private String type;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    @TableField("operate_time")
    private LocalDateTime operateTime;

    /**
     * 操作参数
     */
    @TableField("param")
    @Schema(description = "操作参数")
    private String param;

    /**
     * 扩展字段
     */
    @Schema(description = "扩展字段")
    @TableField("extend_info")
    private String extendInfo;

    /**
     * 是否逻辑删除，0为未删除，非0为已删除
     */
    @TableField("deleted")
    @Schema(description = "是否逻辑删除，0为未删除，非0为已删除")
    private Integer deleted;

    /**
     * 乐观锁版本号
     */
    @Schema(description = "乐观锁版本号")
    @TableField("lock_version")
    private Integer lockVersion;
}
