package ${cfg.PACKAGE_MODEL};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * ${table.comment!}对应model
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@ApiModel(value = "${table.comment!} model对象")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
<#assign removePrefixEntity=entity/>
<#if cfg.REMOVE_PREFIX!?length gt 0>
<#if entity?starts_with(cfg.REMOVE_PREFIX)>
<#assign removePrefixEntity=entity?substring(cfg.REMOVE_PREFIX?length)/>
</#if>
</#if>
public class ${removePrefixEntity}Model implements Serializable {

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
            <#-->
            //${field.comment} ${field.comment!?index_of("-")}
            <#if field.comment!?index_of("-") gt 0>
            //${field.comment!?substring(0,field.comment!?index_of("-"))}
            //${field.comment!?substring(field.comment!?index_of("-")+1,field.comment!?length)}
            </#if>
            <-->
        <#if field.comment!?index_of("-") gt 0>
    @ApiModelProperty(value = "${field.comment!?substring(0,field.comment!?index_of("-"))}", example= "${field.comment!?substring(field.comment!?index_of("-")+1,field.comment!?length)}")
        <#else>
    @ApiModelProperty(value ="${field.comment}", example= "<#if field.propertyType == "Boolean">true<#elseif field.propertyType == "BigDecimal">0.0</#if>")
        </#if>
    <#else>
    @ApiModelProperty(value ="${field.propertyType}", example= "")
    </#if>
    <#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.name>
    @Version
    </#if>
    <#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
    @TableLogic
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }
    <#if entityBuilderModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if entityBuilderModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if entityColumnConstant>
<#--    <#list table.fields as field>-->
<#--    public static final String ${field.name?upper_case} = "${field.name}";-->

<#--    </#list>-->
</#if>

}
