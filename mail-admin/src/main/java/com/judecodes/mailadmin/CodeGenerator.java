package com.judecodes.mailadmin;



import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/mail?useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";
        // 当前这个 CodeGenerator 模块的根路径
        String projectPath = "D:\\CodeData\\myProject\\myMail\\mail-admin";
        // mapper xml 输出路径（在当前模块的 resources 下）
        String mapperXmlPath = projectPath + "/src/main/resources/mapper";


        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("judecodes") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                // 包配置
                .packageConfig(builder -> builder
                        // 生成出来的代码包名：com.judecodes.codegenerator.XXX
                        .parent("com.judecodes.mailadmin")
                        .entity("domain.entity")
                        .mapper("infrastructure.mapper")
                        .service("domain.service")
                        .serviceImpl("domain.service.impl")
                        .controller("controller")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, mapperXmlPath))
                )
                // 策略配置
                .strategyConfig(builder -> builder
                        // 只生成 tz_user 表
                        .addInclude("ums_role_resource_relation")
                        // 去掉表前缀 tz_，生成的实体名会叫 User
                        .addTablePrefix("ums_")

                        // 实体策略
                        .entityBuilder()
                        .enableLombok()                // 使用 Lombok
                        .disableSerialVersionUID()     // 去掉 serialVersionUID
                        .enableTableFieldAnnotation()  // 字段加 @TableField 注解
                        .build()

                        // Mapper 策略
                        .mapperBuilder()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .build()

                        // Service 策略
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        .build()

                        // Controller 策略
                        .controllerBuilder()
                        .enableRestStyle() // @RestController
                        .build()
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
