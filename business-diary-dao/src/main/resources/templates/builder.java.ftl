package ${cfg.PACKAGE_BUILDER};


<#assign removePrefixEntity=entity/>
<#if cfg.REMOVE_PREFIX!?length gt 0>
    <#if entity?starts_with(cfg.REMOVE_PREFIX)>
        <#assign removePrefixEntity=entity?substring(cfg.REMOVE_PREFIX?length)/>
    </#if>
</#if>
import org.mapstruct.Mapper;
import ${cfg.PACKAGE_MODEL}.${removePrefixEntity}Model;
import ${package.Entity}.${entity};

/**
 * <p>
 * ${table.comment!}对应实体和model转换
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper(componentModel="spring")
@SuppressWarnings("all")
public interface ${removePrefixEntity}Builder {
/**
* description功能描述:
*  model转换为entity对象
* @param
* @return:
* @Author:${author}
* @Date: ${.now}
*/
${entity} toEntity(${removePrefixEntity}Model model);
/**
* description功能描述:
*  entity转换为model对象
* @param
* @return:
* @Author:${author}
* @Date: ${.now}
*/
${removePrefixEntity}Model toModel(${entity} entity);
/**
* description功能描述:
*  entity转化成model后，对model进行初始化，比如枚举描述赋值
* @param
* @return:
* @Author:${author}
* @Date: ${.now}
*/
default ${removePrefixEntity}Model modelInit(${removePrefixEntity}Model model){

    return model;
}
/**
* description功能描述:
*  model转换成entity后，对entity进行初始化，比如枚举编码转换等
* @param
* @return:
* @Author:${author}
* @Date: ${.now}
*/
default ${entity} entityInit(${entity} entity){

    return entity;
}
}
