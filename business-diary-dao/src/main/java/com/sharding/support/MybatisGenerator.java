package com.sharding.mapper.support;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.*;

public class MybatisGenerator {

    private static final String MODEL = "model";
    private static final String SERVICE = "service";
    private static final String APP = "app";
    private static final String DAO = "dao";
    private static final String SERVICE_TEMPLATE_PATH = "/templates/my_service.java.ftl";

    private static final String QUERY_TEMPLATE_PATH = "/templates/query.java.ftl";
    private static final String MODEL_TEMPLATE_PATH = "/templates/model.java.ftl";
    private static final String BUILDER_TEMPLATE_PATH = "/templates/builder.java.ftl";
    private static final   String CONTROLLER_TEMPLATE_PATH = "/templates/my_controller.java.ftl";
    private static final  String SERVICE_IMPL_TEMPLATE_PATH = "/templates/my_serviceImpl.java.ftl";
    private static final String XML_TEMPLATE_PATH = "/templates/mapper.xml.ftl";
    /**
     * <p>store
     * 读取控制台内容
     * </p >
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
    public static void main(String[] args) {
        String[] tables = scanner("表名，多个英文逗号分割").split(",");
        String[] models = {"property-proceeds-contract-app", "property-proceeds-contract-service", "property-proceeds-contract-dao", "property-proceeds-contract-model"};
        for (String model : models) {
            shell(model, tables);
        }
    }

    private static void shell(String model, String[] tables) {
        String projectPath = System.getProperty("user.dir");
        File file = new File(model);
        String path = file.getAbsolutePath();
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(path + "/src/main/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        gc.setAuthor("author");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://10.31.117.67:3306/property_proceeds_contract?autoReconnect=true&autoReconnectForPools=true&useServerPrepStmts=true&cachePrepStmts=true&prepStmtCacheSize=256&prepStmtCacheSqlLimit=1024&allowMultiQueries=true&verifyServerCertificate=false&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=CONVERT_TO_NULL");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("dbadmin");
        dsc.setPassword("");

        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 需要生成的表
        strategy.setInclude(tables);
        strategy.setEntityLombokModel(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityColumnConstant(true);
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();

        String parent = "com.property.proceeds.contract";
        pc.setParent(parent);
        mpg.setPackageInfo(pc);

        String myPrefix="";
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>(16);
                //model所在的包
                map.put("PACKAGE_MODEL", parent + ".model");
                //builder
                map.put("PACKAGE_BUILDER", parent + ".builder");
                //query
                map.put("PACKAGE_QUERY", parent + ".model.query");
                //controller包路径（自动生成的默认没有web）
                map.put("PACKAGE_CONTROLLER", parent + ".app.web.controller");
                map.put("REMOVE_PREFIX",myPrefix);
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        //根据不同的项目模块，控制生成对应的文件，已经文件存放的位置.
        if(model.contains(MybatisGenerator.MODEL)){
            //model

            focList.add(new FileOutConfig(MODEL_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    //替换成自己文件存放的位置
                    return projectPath + "/" + model + "/src/main/java/com/property/proceeds/contract/model/" + NamingStrategy.removePrefix(tableInfo.getEntityName(),myPrefix) + "Model" + StringPool.DOT_JAVA;
                }
            });
            //builder`

            focList.add(new FileOutConfig(BUILDER_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + "/" + model + "/src/main/java/com/property/proceeds/contract/builder/" + NamingStrategy.removePrefix(tableInfo.getEntityName(),myPrefix) + "Builder" + StringPool.DOT_JAVA;
                }
            });
            //query

            focList.add(new FileOutConfig(QUERY_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + "/" + model + "/src/main/java/com/property/proceeds/contract/model/query/" + NamingStrategy.removePrefix(tableInfo.getEntityName(),myPrefix) + "Req" + StringPool.DOT_JAVA;
                }
            });
        }
        if(model.contains(SERVICE)){
            //service

            focList.add(new FileOutConfig(SERVICE_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + "/" + model + "/src/main/java/com/property/proceeds/contract/service/" + tableInfo.getServiceName() + StringPool.DOT_JAVA;
                }
            });
            //serviceImpl

            focList.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    return projectPath + "/" + model + "/src/main/java/com/property/proceeds/contract/service/impl/" + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
                }
            });
        }
        if (model.contains(APP)) {
            // 自定义输出配置
            // 自定义配置会被优先输出
            // 如果模板引擎是mapper freemarker

            focList.add(new FileOutConfig(XML_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    //生成文件在项目中位置，会自动生成对应的文件目录
                    return projectPath + "/" + model + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });
            //controller

            focList.add(new FileOutConfig(CONTROLLER_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                    //生成文件在项目中位置，会自动生成对应的文件目录
                    return projectPath + "/" + model + "/src/main/java/com/property/proceeds/contract/web/controller/" + tableInfo.getControllerName() + StringPool.DOT_JAVA;
                }
            });
        }
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig tc = new TemplateConfig();
        if (model.contains(MODEL)) {
            tc.setController(null);
            tc.setService(null);
            tc.setXml(null);
            tc.setServiceImpl(null);
            tc.setMapper(null);
        } else if (model.contains(SERVICE)) {
            tc.setController(null);
            tc.setMapper(null);
            tc.setXml(null);
            tc.setEntity(null);
            //剔除暂时先注释掉
            tc.setService(null);
            tc.setServiceImpl(null);
        } else if (model.contains(APP)) {
            tc.setMapper(null);
            tc.setXml(null);
            tc.setService(null);
            tc.setServiceImpl(null);
            tc.setController(null);
            tc.setEntity(null);
        } else if (model.contains(DAO)) {
            tc.setXml(null);
            tc.setService(null);
            tc.setServiceImpl(null);
            tc.setController(null);
            tc.setEntity(null);
        }


        mpg.setTemplate(tc);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }
}
